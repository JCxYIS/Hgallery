package hgallery;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import hgallery.Debug.ConsoleColor;
import hgallery.File.FileOperate;
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
    private Button butt_import;

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

    @FXML
    private Label lab_ver;





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
            var l = SetFXML("_GalleryView.fxml");
            var ct = (GalleryController)l.getController();
            ct.Set(FileOperate.GetGallerys(), false);
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
        else if(sauce == butt_import)
        {
            // TODO import
            SetTitle("匯入");            
        }
        else if(sauce == butt_setting)
        {
            SetTitle("設定");
            SetFXML("_SettingView.fxml");
           
        }
        else if(sauce == butt_safebox)
        {
            SetTitle("保險箱");
            var l = SetFXML("_GalleryView.fxml");
            var ct = (GalleryController)l.getController();
            ct.Set(FileOperate.GetHentais(), true);
        }
        else if(sauce == butt_hentai)
        {
            // TODO Hentai
            SetTitle("高速公路");
        }
    }

    /**
     * 把某個FXML設為container內的東東
     */
    private FXMLLoader SetFXML(String s)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(s));
            Node gal = loader.load();
            container.getChildren().add(gal);
            ControllerUtility.FitAnchorPlane(gal);
            return loader;
        }
        catch (IOException e)
        {
            Debug.Log(e.getStackTrace(), ConsoleColor.RED);
            return null;
        }
        
        // make the new item fit to the screen
        
    }

  

    public static AnchorPane GetContainer()
    {
        return instance.container;
    }


    public void initialize(URL location, ResourceBundle resources) 
    {
        instance = this;
        lab_ver.setText("JCxYIS  |  "+App.version);
        // SetTitle("fuck");
    }
    
}
