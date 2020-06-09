package hgallery;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import hgallery.Debug.ConsoleColor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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



    /**
     * 設定這個按鈕
     * @param galleryPath
     * @param thumbPicPath
     * @throws MalformedURLException
     */
    public void Set(File galleryPath, File thumbPicPath) throws MalformedURLException
    {
        // set 
        galleryDirPath = galleryPath;

        // set button label
        label.setText(galleryDirPath.getName());

        // set button thumbnail
        String localUrl = thumbPicPath.toURI().toURL().toString();
        //Debug.Log(localUrl);
        img.setImage(new Image(localUrl, true));
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
            pvc.Set(galleryDirPath);
            Scene scene = new Scene(root);

            // set stage
            Stage stage = new Stage();
            stage.setTitle("Hgallery Viewer: "+galleryDirPath.getName());
            stage.setScene(scene);
            stage.show();
            // Hide this current window (if this is what you want)
            //((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) 
        {
            Debug.Log(e.getStackTrace(), ConsoleColor.RED);
        }
    }
}