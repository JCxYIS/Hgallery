package hgallery;

import java.io.File;
import java.net.MalformedURLException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GalleryFractionController 
{
    @FXML private ImageView img;
    @FXML private Label label;

    /**
     * 相簿的資料夾位置
     */
    private File galleryDirPath;

    public void Set(File galleryDirPath, File thumbPicPath) throws MalformedURLException
    {
        // set 
        this.galleryDirPath = galleryDirPath;

        // set label
        label.setText(galleryDirPath.getName());

        // set image
        String localUrl = thumbPicPath.toURI().toURL().toString();
        //Debug.Log(localUrl);
        img.setImage(new Image(localUrl, true));
    }
}