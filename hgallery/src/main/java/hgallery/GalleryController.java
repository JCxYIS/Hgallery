package hgallery;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import hgallery.Debug.ConsoleColor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;

public class GalleryController 
{
    @FXML private GridPane grid;


    @FXML
    private void initialize()
    {
        Resize();
    }

    /**
     * 重新製作大小
     */
    @FXML
    private void Resize()
    {
        Debug.Log("RESIZING GALLERY...");
        int albumCount = 41;
        
        for(int i = 0; i < albumCount; i++)
        {
            int col = i%3;
            int row = i/3;            
            
            // new row
            if(col == 0 && row != 0)
            {
                grid.setPrefHeight(grid.getPrefHeight() + 320); // gap
                grid.getRowConstraints().add(new RowConstraints(300)); // column is 200 wide
                //Debug.Log(grid.getPrefHeight());
            }

            try
            {
                Node newItem = FXMLLoader.load(getClass().getResource("_GalleryFraction.fxml"));
                grid.add(newItem, col, row);
            }
            catch (IOException e)
            {
                Debug.Log(e, ConsoleColor.RED);
            }
        }
    }
}