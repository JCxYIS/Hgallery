package hgallery;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class GalleryFractionController 
{
    @FXML private ImageView img;
    @FXML private Label label;

    public void Set(String labelName, String thumbPicPath)
    {
        label.setText(labelName);
        // TODO: img
    }
}