package hgallery;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

import hgallery.Debug.ConsoleColor;
import hgallery.DiscordRPC.DiscordRpcHandler;
import hgallery.Settings.SettingManager;

/**
 * JavaFX App
 */
public class App extends Application 
{
    public static boolean isExited = false;
    public static final String version = "v.2.2.1";

    public static ArrayList<Thread> runningThreads = new ArrayList<>();
    private static Scene scene;


    public static void main(String[] args) 
    {
        SettingManager.Load();
        DiscordRpcHandler.Init();
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException 
    {
        Debug.Log("Starting...", ConsoleColor.GREEN);

        // load fxml
        scene = new Scene( LoadFXML("Main") );

        // set icon
        stage.getIcons().add(new Image(App.class.getResourceAsStream("images/HgB.png"))); 
        stage.setTitle("Hgallery");

        // show
        stage.setScene(scene);
        //stage.setResizable(false);
        stage.show();
    }

    @Override
    public void stop()
    {
        Debug.Log("掰掰！", ConsoleColor.GREEN);
        SettingManager.Save();
        DiscordRpcHandler.Stop();
        for (Thread thread : runningThreads) 
        {
            try
            {
                thread.interrupt();
            }
            catch (Exception e)
            {
                
            }
        }
        isExited = true;
    }
    

    /**
     * 讀取fxml
     */
    private static Parent LoadFXML(String fxml) 
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
            return fxmlLoader.load();
        }
        catch (Exception e)
        {
            Debug.Log(e, ConsoleColor.RED);
            Debug.Log("可能是fxml ("+fxml+") 不存在？", ConsoleColor.YELLOW);
            return null;
        }
    }

}