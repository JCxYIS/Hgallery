package hgallery;

import java.io.File;
import java.io.FileFilter;
import java.lang.ModuleLayer.Controller;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import hgallery.AlbumReader.AlbumFileReader;
import hgallery.Debug.ConsoleColor;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class PageViewController {
    @FXML
    private AnchorPane ap;
    @FXML
    private Button butt_r;
    @FXML
    private Button butt_l;
    @FXML
    private ImageView img;

    /**
     * 圖片檔案s
     */
    private File[] files;
    /**
     * 這可能不是實際頁數，是檔案的次序。
     */
    private int page;

    /**
     * 設定這個view的參數s
     */
    public void Set(File galleryDir) 
    {
        files = galleryDir.listFiles(new FileFilter()
        {
            @Override
            public boolean accept(File pathname) 
            {                
                return AlbumFileReader.IsValidPicType(pathname);
            }
        });
        page = 0;
    }

    @FXML
    private void initialize() 
    {
        Platform.runLater(new Runnable()
        {
            @Override
            public void run() 
            {
                Debug.Log("具現化Page View");
                Resize();
                SwitchPage();
            }
        });
    }

    @FXML
    public void Resize() 
    {
        var w = ap.getWidth();
        butt_l.setPrefWidth(w / 2);
        butt_r.setPrefWidth(w / 2);
    }

    @FXML
    private void NextPage() 
    {
        if(page < files.length-1)
            page++;
        SwitchPage();
    }

    @FXML
    private void PrevPage() 
    {
        if(page > 0)
            page--;
        SwitchPage();
    }

    private void SwitchPage() 
    {
        Debug.Log("Page："+page);

        // set url
        try
        {
            String picPath = files[page].toURI().toURL().toString();
            img.setImage(new Image(picPath, true));
        }
        catch (Exception e)
        {
            Debug.Log("無法顯示圖片："+e, ConsoleColor.RED);
        }
    }

}