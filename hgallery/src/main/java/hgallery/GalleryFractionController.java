package hgallery;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import com.github.ttdyce.model.Comic;

import hgallery.AlbumReader.AlbumFileReader;
import hgallery.Debug.ConsoleColor;
import hgallery.DiscordRPC.DiscordRpcHandler;
import hgallery.File.FileOperate;
import hgallery.Settings.SettingManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class GalleryFractionController 
{
    @FXML private ImageView img;
    @FXML private Label label;

    /**
     * 相簿的資料夾位置
     */
    private File galleryDirPath;

    private boolean shouldBlur;



    /**
     * 設定這個按鈕
     * @throws MalformedURLException
     */
    public void Set(File galleryPath, boolean shouldblur) throws MalformedURLException 
    {
        // set 
        galleryDirPath = galleryPath;

        // 
        label.setText(galleryDirPath.getName());
        img.setImage(new Image( AlbumFileReader.GetThumbnailPath(galleryPath), 0, 400, true, true, true));

        //
        this.shouldBlur = shouldblur;
        if(shouldblur)
            img.setEffect(new GaussianBlur(SettingManager.settings.blur));
    }
    public void Set(Comic hon, boolean shouldblur)
    {
        // set
        label.setText(hon.getTitle().toString());
        img.setImage(new Image( AlbumFileReader.GetHonThumbnail(hon), 0, 400, true, true, true));

        //
        this.shouldBlur = shouldblur;
        if(shouldblur)
            img.setEffect(new GaussianBlur(SettingManager.settings.blur));
    }



    @FXML 
    private void onClick()
    {
        Debug.Log("點擊相簿 圖庫位置："+galleryDirPath, ConsoleColor.BLUE);

        // open new window
        try 
        { 
            // set scene
            Parent root;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("_PageView.fxml"));
            root = loader.load();
            PageViewController pvc = loader.getController();

            Scene scene = new Scene(root);

            // set stage
            Stage stage = new Stage();
            stage.setTitle("Hgallery Viewer: "+galleryDirPath.getName());
            stage.setScene(scene);
            stage.getIcons().add(new Image(App.class.getResourceAsStream("images/Hg.png"))); 
            stage.setOnCloseRequest( (e)->
            {
                DiscordRpcHandler.NewPresence("Idle","");
            });
            stage.show();

            pvc.Set(stage, galleryDirPath, shouldBlur);
            // Hide this current window (if this is what you want)
            //((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) 
        {
            Debug.Log(e.getStackTrace(), ConsoleColor.RED);
        }
    }
}