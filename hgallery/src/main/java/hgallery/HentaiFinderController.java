package hgallery;

import java.util.ArrayList;

import com.github.ttdyce.model.Comic;
import com.github.ttdyce.model.api.NHAPI;

import hgallery.Debug.ConsoleColor;
import hgallery.NHAPI.NhentaiUtility;
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
     

        // Let's handle this
        if(honId != -1)
        { // is 車牌
            try
            {
                hlist.add(NhentaiUtility.GetComicByID(api, honId));
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
                hlist.addAll(NhentaiUtility.GetListByName(api, attempt));
            }
            catch (Exception e)
            {
                MessageBoxController.CreateMessageBox("找不到結果", "無法找到 "+attempt+" 相關的結果");
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