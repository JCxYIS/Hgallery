package hgallery;

import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.util.ArrayList;

import com.github.ttdyce.model.Comic;

import hgallery.AlbumReader.AlbumFileReader;
import hgallery.Debug.ConsoleColor;
import hgallery.DiscordRPC.DiscordRpcHandler;
import hgallery.Settings.SettingManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PageViewController {
    @FXML private AnchorPane ap;
    @FXML private Button butt_overlay;
    @FXML private ImageView img;
    @FXML private ImageView icon_l, icon_r;
    @FXML private Label lab_page, lab_name, lab_status;

    /**
     * My stage
     */
    private Stage stage;
    /**
     * 圖片網址s
     */
    private String[] picPaths = {};
    /**
     * 要模糊？
     */
    private boolean blur = false;
    /**
     * 這可能不是實際頁數，是圖片檔案的次序。
     */
    private int page = 0;
    /**
     * 開始閱讀的UNIX時間
     */
    private long startTime = 0;
    /**
     * 
     */
    private String albumName = "";
    /**
     * 圖片縮放係數
     */
    private double scaleMultipler = 1;
    /**
     * 圖片偏移
     */
    private double deltaX = 0, deltaY = 0;
    /**
     * 如果有拖移事件，表示拖移量；否則為-1
     */
    private double dragStartX = -1, dragStartY = -1, dragPrevX = -1, dragPrevY = -1;



    /**
     * 設定這個view的參數s
     * @throws MalformedURLException
     */
    public void Set(Stage stage, File galleryDir, boolean shouldblur) throws MalformedURLException 
    {
        File[] files = galleryDir.listFiles(new FileFilter()
        {
            @Override
            public boolean accept(File pathname) 
            {                
                return AlbumFileReader.IsValidPicType(pathname);
            }
        });
        ArrayList<String> tmp = new ArrayList<>();
        for (File f : files) 
        {
             tmp.add( f.toURI().toURL().toString() );
        }
        picPaths = tmp.toArray(String[]::new);

        albumName = galleryDir.getName();
        blur = shouldblur;
        this.stage = stage;
        startTime = System.currentTimeMillis();
    }

    public void Set(Stage stage, Comic hon, boolean shouldblur) 
    {
        ArrayList<String> tmp = new ArrayList<>();
        for(int i = 0; i < hon.getNumOfPages(); i++)
        {
            tmp.add(AlbumFileReader.GetHonImagePath(hon, i+1));
        }
        picPaths = tmp.toArray(String[]::new);

        albumName = hon.getTitle().toString();
        blur = shouldblur;
        this.stage = stage;
        startTime = System.currentTimeMillis();
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
        img.setTranslateX(deltaX);
        img.setTranslateY(deltaY);
    }

    
    private void NextPage() 
    {
        if(page < picPaths.length-1)
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
        lab_page.setText( (page+1)+" / "+(picPaths.length) );
        lab_name.setText( albumName );

        // set url
        try
        {
            Image i = new Image(picPaths[page], 0, 1800, true, true, true);
            img.setImage(i);

            // blur?
            if(blur)
                img.setEffect(new GaussianBlur(SettingManager.settings.blur));
            else
                img.setEffect(new GaussianBlur(0));

            Debug.Log("Page："+page+" | "+picPaths.length, ConsoleColor.PURPLE);
        }
        catch (Exception e)
        {
            Debug.Log("無法顯示圖片："+e, ConsoleColor.RED);
            lab_status.setText("無法顯示");
        }
        
        // set discord
        DiscordRpcHandler.NewPresence( "Reading "+albumName, "Page "+(page+1)+" / "+(picPaths.length) , startTime);

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
            deltaX = 0;
            deltaY = 0;
        }

        Resize();
    }


    @FXML
    private void onKeyPressed(KeyEvent event)
    {
        var k = event.getCode();

        if(k == KeyCode.EQUALS || k == KeyCode.PLUS || k == KeyCode.ADD)
        {
            scaleMultipler += 0.25;
            Debug.Log("PLUS | ScaleMultipler="+scaleMultipler, ConsoleColor.BLUE);
        }
        if(k == KeyCode.MINUS || k == KeyCode.SUBTRACT)
        {
            if(scaleMultipler > 0.25)
                scaleMultipler -= 0.25;
            Debug.Log("MINUS | ScaleMultipler="+scaleMultipler, ConsoleColor.BLUE);
        }

        if(k == KeyCode.UP)
        {
            deltaY += 30;
            Debug.Log("UP", ConsoleColor.BLUE);
        }
        if(k == KeyCode.DOWN)
        {
            deltaY -= 30;
            Debug.Log("DOWN", ConsoleColor.BLUE);
        }
        if(k == KeyCode.LEFT)
        {
            PrevPage();
            Debug.Log("LEFT", ConsoleColor.BLUE);
        }
        if(k == KeyCode.RIGHT)
        {
            NextPage();
            Debug.Log("RIGHT", ConsoleColor.BLUE);
        }

        if(k == KeyCode.ESCAPE)
        {
            stage.close();
            Debug.Log("ESC | QUIT", ConsoleColor.BLUE);
        }
        if(k == KeyCode.F11)
        {
            stage.setFullScreen(!stage.isFullScreen());;
        }

        Resize();
    }


    @FXML
    private void onDragStart(MouseEvent event)
    {
        dragStartX = event.getSceneX();
        dragStartY = event.getSceneY();
        dragPrevX = dragStartX;
        dragPrevY = dragStartY;
        event.setDragDetect(true);
        Debug.Log("Drag Start");
    }

    @FXML
    private void onDragContinue(MouseEvent event)
    {
        double x = event.getSceneX();
        double y = event.getSceneY();

        double dx = x - dragPrevX;
        double dy = y - dragPrevY;

        deltaX += dx;
        deltaY += dy;

        dragPrevX = x;
        dragPrevY = y;

        Resize(); 
        Debug.Log("Drag Continue! dx="+dx+" dy="+dy, ConsoleColor.BLUE);
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
        dragPrevX = -1;
        dragPrevY = -1;

        if(dx*dx + dy*dy <= 1)
        {
            double leftPa = x / ap.getWidth();
            if(leftPa <= 0.5)
                PrevPage();
            else
                NextPage();
            Debug.Log("Click! 觸碰之左至右百分比："+leftPa, ConsoleColor.BLUE);
        }
        else
        {
            Debug.Log( "DragEnd delta=("+dx+", "+dy+")", ConsoleColor.BLUE);
        }
        
        
    }

}