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

    public void Set(String labelName, String thumbPicPath) throws MalformedURLException
    {
        // set label
        label.setText(labelName);

        // set image
        File file = new File(thumbPicPath);
        String localUrl = file.toURI().toURL().toString();
        Debug.Log(localUrl);
        img.setImage(new Image(localUrl));
    }
}