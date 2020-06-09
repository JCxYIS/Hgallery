package hgallery;

import java.io.File;
import java.io.FileFilter;

import hgallery.AlbumReader.AlbumFileReader;
import hgallery.Debug.ConsoleColor;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;

public class PageViewController {
    @FXML private AnchorPane ap;
    @FXML private Button butt_overlay;
    @FXML private ImageView img;
    @FXML private ImageView icon_l, icon_r;
    @FXML private Label lab_page, lab_name, lab_status;

    /**
     * 圖片檔案s
     */
    private File[] files;
    /**
     * 這可能不是實際頁數，是圖片檔案的次序。
     */
    private int page = 0;
    /**
     * 
     */
    private String albumName = "";
    /**
     * 圖片縮放係數
     */
    private double scaleMultipler = 1;
    /**
     * 圖片 Y 偏移
     */
    private double deltaY = 1;
    /**
     * 如果有拖移事件，表示拖移量；否則為-1
     */
    private double dragStartX = -1, dragStartY = -1;



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
        albumName = galleryDir.getName();
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
        var h = ap.getHeight();
        
        icon_l.setLayoutY(h/2 - 30);
        icon_r.setLayoutY(h/2 - 30);

        img.setFitWidth (w - 60);
        img.setFitHeight(h - 60);

        img.setScaleX(scaleMultipler);
        img.setScaleY(scaleMultipler);
        img.setTranslateY(deltaY);
    }

    
    private void NextPage() 
    {
        if(page < files.length-1)
        {
            page++;
            SwitchPage();
        }
    }
    private void PrevPage() 
    {
        if(page > 0)
        {
            page--;
            SwitchPage();
        }
    }

    private void SwitchPage() 
    {
        // scale & delta
        deltaY = 0;
        Resize();

        // texts
        lab_status.setText("");
        lab_page.setText( (page+1)+" / "+(files.length) );
        lab_name.setText( albumName );

        // set url
        try
        {
            String picPath = files[page].toURI().toURL().toString();
            Image i = new Image(picPath, 0, 1800, true, true, true);
            img.setImage(i);

            img.setEffect(new GaussianBlur(60));

            Debug.Log("Page："+page+" | "+picPath);
        }
        catch (Exception e)
        {
            Debug.Log("無法顯示圖片："+e, ConsoleColor.RED);
            lab_status.setText("無法顯示");
        }
        
    }

    @FXML 
    private void onScroll(ScrollEvent event)
    {
        double scrollAmount = event.getDeltaY();

        if(event.isControlDown())
        {
            scaleMultipler += 0.004 * scrollAmount;
            Debug.Log("CTRL + onScroll | ScaleMultipler="+scaleMultipler, ConsoleColor.BLUE);
        }
        else
        {
            deltaY += 1 * scrollAmount;
            Debug.Log("onScroll | deltaY="+deltaY, ConsoleColor.BLUE);
        }

        // reset
        if(scaleMultipler <= 0)
        {
            scaleMultipler = 1;
            deltaY = 0;
        }

        Resize();
    }

    
    private boolean isDragging()
    {
        return dragStartX!=-1 || dragStartY!=-1;
    }

    @FXML
    private void onDragStart(MouseEvent event)
    {
        dragStartX = event.getSceneX();
        dragStartY = event.getSceneY();
        event.setDragDetect(true);
        Debug.Log( "Drag Start" );
    }

    @FXML
    private void onDragEnd(MouseEvent event)
    {
        double x = event.getSceneX();
        double y = event.getSceneY();
        double dx = x - dragStartX;
        double dy = y - dragStartY;
        dragStartX = -1;
        dragStartY = -1;

        if(dx*dx + dy*dy <= 1)
        {
            double leftPa = x / ap.getWidth();
            if(leftPa <= 0.5)
                PrevPage();
            else
                NextPage();
            Debug.Log("Click!"+leftPa);
        }
        else
        {
            Debug.Log( "DragEnd delta=("+dx+", "+dy+")" );
        }
        
        
    }

}