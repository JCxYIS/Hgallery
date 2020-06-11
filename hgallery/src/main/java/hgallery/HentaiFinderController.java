package hgallery;

import java.util.ArrayList;

import com.github.ttdyce.model.Comic;
import com.github.ttdyce.model.api.NHAPI;
import com.github.ttdyce.model.api.ResponseCallback;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import hgallery.Debug.ConsoleColor;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class HentaiFinderController 
{
    @FXML TextField textField;
    @FXML AnchorPane container;

    @FXML
    private void initialize()
    {
        Platform.runLater(new Runnable() 
        {
            @Override
            public void run() 
            {
                textField.requestFocus();
            }
        });
    }

    @FXML
    private void Execute()
    {
        // get variable
        String attempt = textField.getText();
        int honId = -1;
        try
        {
            honId = Integer.parseInt(attempt);
        }
        catch (Exception e)
        {
            // not a number
        }
        NHAPI api = new NHAPI();
        ArrayList<Comic> hlist = new ArrayList<>();

        
        // Set ResponceHandler
        ResponseCallback comicReturnCallback = new ResponseCallback() 
        {
            @Override
            public void onReponse(String response) 
            {
                Debug.Log("Response:\n"+response);
                JsonObject object = new JsonParser().parse(response).getAsJsonObject();
                Gson gson = new Gson();
                Comic comic = gson.fromJson(object, Comic.class);
                hlist.add(comic);
            }
        };
        ResponseCallback comicListReturnCallback = new ResponseCallback() 
        {
            @Override
            public void onReponse(String response) 
            {
                Debug.Log("Response:\n"+response);
                JsonArray array = new JsonParser().parse(response).getAsJsonArray();
                Gson gson = new Gson();
                
                for (JsonElement jsonElement : array) 
                {
                    Comic c = gson.fromJson(jsonElement, Comic.class);
                    hlist.add(c);
                }
            }
        }; 


        // Let's handle this
        if(honId != -1)
        { // is 車牌
            try
            {
                api.getComic(honId, comicReturnCallback);
            }
            catch (Exception e)
            {
                MessageBoxController.CreateMessageBox("錯誤", "無法解析車牌"+honId);
                Debug.Log("無法解析車牌"+honId+"："+e, ConsoleColor.RED);
            }
        }
        else
        {
            try
            {
                api.getComicList(attempt, true, comicListReturnCallback);
            }
            catch (Exception e)
            {
                MessageBoxController.CreateMessageBox("找不到結果", "找不到 "+attempt);
                Debug.Log("無法解析車名："+attempt+"："+e, ConsoleColor.RED);
            }
        }

        // DEBUG: print them!
        for (Comic comic : hlist) 
        {
            Debug.Log( "搜尋：得到本子："+comic.getId()+" | "+comic.getTitle(), ConsoleColor.GREEN);
        }//316376 cover: jpg

        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("_GalleryView.fxml"));
            Node gal = loader.load();
            container.getChildren().add(gal);
            ControllerUtility.FitAnchorPlane(gal);
            var ct = (GalleryController)loader.getController();
            ct.Set( hlist.toArray(Comic[]::new) , true);
        }
        catch (Exception e)
        {
            Debug.Log("[HENTAIFINDER]列印GALLERY錯誤："+e, ConsoleColor.RED);
        }
             
    }
}