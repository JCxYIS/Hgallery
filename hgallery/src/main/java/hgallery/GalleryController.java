package hgallery;

import java.io.File;
import java.util.ArrayList;

import com.github.ttdyce.model.Comic;

import hgallery.Debug.ConsoleColor;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.TilePane;

public class GalleryController 
{
    @FXML private ScrollPane sp;
    @FXML private TilePane container;

    private ArrayList<Node> albums = new ArrayList<Node>();
    private File[] albumDirPaths = {};
    private Comic[] honPaths = {};
    private boolean shouldBlur;


    public void Set(File[] albumPaths, boolean shouldblur)
    {
        albumDirPaths = albumPaths;
        this.shouldBlur = shouldblur;        
    }
    public void Set(Comic[] hPaths, boolean shouldblur)
    {
        honPaths = hPaths;
        this.shouldBlur = shouldblur;        
    }

    @FXML
    private void initialize()
    {        
        Platform.runLater(new Runnable() 
        {
            @Override 
            public void run() 
            {
                Debug.Log("具現化GALLERY");
                Print();
                Resize();    
            }
        });
        //new Thread(task).start();
    }

    /**
     * 製作GALLERY清單
     */
    public void Print()
    {
        Debug.Log("製作GALLERY清單...", ConsoleColor.GREEN);

        // remove all child first
        for (var g : albums)
        {
            container.getChildren().remove(g);   
        }
        albums.clear();        

        // print it
        for(int i = 0; i < albumDirPaths.length; i++)
        {
            try
            {               
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("_GalleryFraction.fxml"));
                Node newItem = fxmlLoader.load();
                GalleryFractionController g =  fxmlLoader.getController();
                g.Set( albumDirPaths[i], shouldBlur, this);
                albums.add(newItem);
            }
            catch (Exception e)
            {
                Debug.Log("無法製作一個GALLERY清單("+albumDirPaths[i].getName()+")"+e, ConsoleColor.RED);
            }
        }
        for(int i = 0; i <honPaths.length; i++)
        {
            try
            {               
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("_GalleryFraction.fxml"));
                Node newItem = fxmlLoader.load();
                GalleryFractionController g =  fxmlLoader.getController();
                g.Set( honPaths[i], shouldBlur, this);
                albums.add(newItem);
            }
            catch (Exception e)
            {
                Debug.Log("無法製作一個GALLERY清單("+honPaths[i].getTitle()+")"+e, ConsoleColor.RED);
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
        Debug.Log("重新計算GALLERY視窗：寬度="+w+" | 高度="+h);

        if(w == 0 && h == 0)
        {
            Platform.runLater(new Runnable() 
            {
                @Override 
                public void run() 
                {
                    Resize();    
                }
            });
        }

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