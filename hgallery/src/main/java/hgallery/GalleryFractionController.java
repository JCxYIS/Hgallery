package hgallery;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import com.github.ttdyce.model.Comic;

import hgallery.AlbumReader.AlbumFileReader;
import hgallery.Debug.ConsoleColor;
import hgallery.DiscordRPC.DiscordRpcHandler;
import hgallery.File.HentaiDownloader;
import hgallery.Settings.RuntimeSettings;
import hgallery.Settings.SettingManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class GalleryFractionController 
{
    @FXML private ImageView img;
    @FXML private Label label;

    /**
     * 相簿的資料夾位置
     */
    private File galleryDirPath = null; // path
    private Comic hon = null; // or hon

    private boolean shouldBlur;
    private GalleryController galleryController;



    /**
     * 設定這個按鈕
     * @throws MalformedURLException
     */
    public void Set(File galleryPath, boolean shouldblur, GalleryController gc) throws MalformedURLException 
    {
        // set 
        galleryDirPath = galleryPath;

        // 
        label.setText(galleryDirPath.getName());
        img.setImage(new Image( AlbumFileReader.GetThumbnailPath(galleryPath), 700, 0, true, true, true));

        //
        this.shouldBlur = shouldblur;
        if(shouldblur)
            img.setEffect(new GaussianBlur(SettingManager.settings.blur));
        
        galleryController = gc;
    }
    public void Set(Comic hon, boolean shouldblur, GalleryController gc)
    {
        // set
        this.hon = hon;
        label.setText(hon.getTitle().toString());
        img.setImage(new Image( AlbumFileReader.GetHonThumbnailPath(hon), 700, 0, true, true, true));

        //
        this.shouldBlur = shouldblur;
        if(shouldblur)
            img.setEffect(new GaussianBlur(SettingManager.settings.blur));

        galleryController = gc;
    }



    @FXML 
    private void onClick(MouseEvent event)
    {
        // 左鍵
        if(event.getButton() == MouseButton.PRIMARY)
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
                stage.setScene(scene);
                stage.getIcons().add(new Image(App.class.getResourceAsStream("images/Hg.png"))); 
                stage.setOnCloseRequest( (e)->
                {
                    DiscordRpcHandler.NewPresence("Idle","");
                    PageViewController.isShowing = false;
                });
                PageViewController.isShowing = true;
                stage.show();

                if(galleryDirPath != null)
                {
                    stage.setTitle("Hgallery Viewer: "+galleryDirPath.getName());
                    pvc.Set(stage, galleryDirPath, shouldBlur);
                }
                else
                {
                    stage.setTitle("Hgallery Viewer: "+hon.getTitle());
                    pvc.Set(stage, hon, shouldBlur);
                }
                // Hide this current window (if this is what you want)
                //((Node)(event.getSource())).getScene().getWindow().hide();
            }
            catch (IOException e) 
            {
                Debug.Log(e, ConsoleColor.RED);
            }
        }
        // 右鍵
        else if(event.getButton() == MouseButton.SECONDARY)
        {
            if(hon == null)
            {
                Debug.Log("無法加入稍後觀看：不是線上本子");
                return;
            }
            else
            {
                if(RuntimeSettings.ContainsReadLater(hon))
                {
                    MessageBoxController.CreateMessageBox("閱畢", "想要從稍後觀看移除？\n"+hon.getTitle(), ()->
                    {
                        RuntimeSettings.RemoveReadLater(hon);                        
                        galleryController.Set(RuntimeSettings.GetReadLater(), false);
                        galleryController.Print();
                        MessageBoxController.CreateMessageBox("移除完成", "休息一下吧！\n");                        
                    }, true);
                }
                else
                {
                    MessageBoxController.CreateMessageBox("準備你的衛生紙", "想要加入稍後觀看？\n"+hon.getTitle(), ()->
                    {
                        RuntimeSettings.AddReadLater(hon);
                        MessageBoxController.CreateMessageBox("加入完成", "準備好再開車，安全上路！\n注意：稍後觀看內容再關閉程式後會清空！");
                    }, true);
                }
            }
        }
        // 中鍵
        else if(event.getButton() == MouseButton.MIDDLE)
        {
            if(hon == null)
            {
                Debug.Log("無法儲存線上本子：不是線上本子");
                return;
            }
            else
            {
                MessageBoxController.CreateMessageBox("AWSL", "想要把這本儲存起來嗎？\n"+hon.getTitle(), ()->
                {
                    HentaiDownloader.DownloadHentai(hon);                    
                }, true);
            }
        }
    }
}