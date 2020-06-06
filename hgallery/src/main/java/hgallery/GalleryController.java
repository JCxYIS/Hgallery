package hgallery;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class GalleryController implements Initializable
{
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        MainController.instance.SetTitle("相簿");
    }
}