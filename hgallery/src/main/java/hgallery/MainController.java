package hgallery;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import hgallery.Debug.ConsoleColor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class MainController implements Initializable
{
    public static MainController instance;

    

    @FXML
    private Button butt_gallery;

    @FXML
    private Button butt_search;

    @FXML
    private Button butt_trash;

    @FXML
    private Button butt_setting;

    @FXML
    private Button butt_safebox;

    @FXML
    private Button butt_hentai;

    @FXML
    private Label title;

    @FXML
    private AnchorPane container;





    /**
     * 設定上面的標題
     */
    public void SetTitle(String s) 
    {
        title.setText(s);
    }

    /**
     * 處理點擊左邊選單按鈕
     */
    @FXML
    public void HandleMenuButtonOnclick(ActionEvent ev) throws IOException
    {
        // init
        var sauce = ev.getSource();
        Debug.Log("點擊選單："+sauce, ConsoleColor.BLUE);

        // destroy all old stuffs
        container.getChildren().clear();

        // func enum..
        if(sauce == butt_gallery)
        {
            SetTitle("我的相簿");
            var a = LoadFXML("_GalleryFraction.fxml");
            container.getChildren().add((Node) a);    
        }
        else if(sauce == butt_search)
        {
            // TODO search
            SetTitle("搜尋");
        }
        else if(sauce == butt_trash)
        {
            // TODO trash
            SetTitle("垃圾桶");
        }
        else if(sauce == butt_setting)
        {
            // TODO config
            SetTitle("設定");
        }
        else if(sauce == butt_safebox)
        {
            // TODO 保險箱
            SetTitle("保險箱");
        }
        else if(sauce == butt_hentai)
        {
            // TODO Hentai
            SetTitle("危險的地方");
        }
        
    }

    private Object LoadFXML(String s)
    {
        try
        {
            return FXMLLoader.load(getClass().getResource(s));
        }
        catch (Exception e)
        {
            Debug.Log(e, ConsoleColor.RED);
            return null;
        }
    }


    public void initialize(URL location, ResourceBundle resources) 
    {
        instance = this;
        // SetTitle("fuck");
    }
    
}
