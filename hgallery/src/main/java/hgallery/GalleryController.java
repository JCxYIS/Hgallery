package hgallery;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
    @FXML private ScrollPane sp;
    @FXML private GridPane grid;

    private ArrayList<Node> albums = new ArrayList<Node>();
    private double lastWidth = 0, lastHeight = 0;


    @FXML
    private void initialize()
    {
        Print();
    }

    /**
     * 製作GALLERY清單
     */
    private void Print()
    {
        Debug.Log("製作GALLERY清單...", ConsoleColor.GREEN);

        int albumCount = 10;
        for(int i = 0; i < albumCount; i++)
        {
            try
            {
                Node newItem = FXMLLoader.load(getClass().getResource("_GalleryFraction.fxml"));
                albums.add(newItem);
            }
            catch (IOException e)
            {
                Debug.Log(e, ConsoleColor.RED);
            }
        }
    }

    /**
     * 重新調整GALLERY大小
     */
    @FXML
    private void Resize()
    {
        // grid: Fit the parent
        var w = sp.getWidth();
        var h = sp.getHeight();
        grid.setPrefSize(w, h);

        // calc each row can fit in how many hentais
        double parentw = grid.getWidth();
        double childw = albums.get(0).getBoundsInLocal().getWidth();
        int hCapicity = (int)(parentw / childw) + 1;
        Debug.Log(parentw + " / "+ childw + " = " + hCapicity);
 
        // determine if we need to resize
        if(lastHeight != grid.getHeight() && lastWidth != grid.getWidth())
        {
            lastHeight = grid.getHeight();
            lastWidth = grid.getWidth();
            Debug.Log("重新調整GALLERY大小...", ConsoleColor.GREEN); 
        }
        else
        {
            return;
        }
        

        for(int i = 0; i < albums.size(); i++)
        {
            int col = i%hCapicity;
            int row = i/hCapicity;     
            
            // new row
            if(col == 0 && row != 0)
            {
                grid.setPrefHeight(grid.getPrefHeight() + 320); // 300 + gap
                grid.getRowConstraints().add(new RowConstraints(300)); // column is 300 wide
                //Debug.Log(grid.getPrefHeight());
            }

            //
            try
            {
                grid.add(albums.get(i), col, row);
            }
            catch (Exception e)
            {
                Debug.Log(""+e.getMessage(), ConsoleColor.RED);
            }
        }
    }




}