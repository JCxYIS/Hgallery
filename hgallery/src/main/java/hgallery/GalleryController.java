package hgallery;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import hgallery.AlbumReader.AlbumReader;
import hgallery.Debug.ConsoleColor;
import hgallery.File.FileOperate;
import javafx.application.Platform;
import javafx.concurrent.Task;
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
import javafx.scene.layout.TilePane;

public class GalleryController 
{
    @FXML private ScrollPane sp;
    @FXML private TilePane container;

    private ArrayList<Node> albums = new ArrayList<Node>();


    @FXML
    private void initialize()
    {
        Print();

        Platform.runLater(new Runnable() 
        {
            @Override 
            public void run() 
            {
                Debug.Log("具現化GALLERY");
                Resize();    
            }
        });
        //new Thread(task).start();
    }

    /**
     * 製作GALLERY清單
     */
    private void Print()
    {
        Debug.Log("製作GALLERY清單...", ConsoleColor.GREEN);

        // get directories
        File[] dirs = FileOperate.GetGallerys();

        // print it
        for(int i = 0; i < dirs.length; i++)
        {
            try
            {
                // special folder! skip
                if(dirs[i].getName().equals("_thumbnails"))
                    continue;

                // make fraction (node)
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("_GalleryFraction.fxml"));
                Node newItem = fxmlLoader.load();
                
                // Set properties of this album
                GalleryFractionController c = fxmlLoader.getController();
                c.Set( dirs[i], AlbumReader.GetThumbnailPath(dirs[i]) );

                // add.
                albums.add(newItem);
            }
            catch (Exception e)
            {
                Debug.Log(e, ConsoleColor.RED);
            }
        }

        // 讓每個album放進container
        for(int i = 0; i < albums.size(); i++)
        {
            try
            {
                //grid.add(albums.get(i), col, row);
                container.getChildren().add(albums.get(i));
            }
            catch (Exception e)
            {
                Debug.Log(""+e.getMessage(), ConsoleColor.RED);
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
        container.setPrefSize(w, h);
        Debug.Log("重新計算GALLERY視窗：寬度"+w);

        /* TILEPANE 好好用
        // calc each row can fit in how many hentais
        double parentw = w;
        double childw = 200; //albums.get(0).getBoundsInLocal().getWidth();
        int hCapicity = (int)(parentw / childw) + 1;
        Debug.Log(parentw + " / "+ childw + " = " + hCapicity);
 
        // determine if we need to resize
        if(lastHeight != container.getHeight() && lastWidth != container.getWidth())
        {
            lastHeight = container.getHeight();
            lastWidth = container.getWidth();
            Debug.Log("重新調整GALLERY大小...", ConsoleColor.GREEN); 
        }
        else
        {
            return;
        }
        */
        
        // 加起來
        
    }




}