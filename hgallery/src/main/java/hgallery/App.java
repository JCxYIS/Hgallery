package hgallery;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

import hgallery.Debug.ConsoleColor;
import hgallery.Settings.SettingManager;
import hgallery.Settings.Settings;

/**
 * JavaFX App
 */
public class App extends Application 
{
    public static final String version = "v.0.18";


    private static Scene scene;


    public static void main(String[] args) 
    {
        SettingManager.Load();
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException 
    {
        Debug.Log("Starting...", ConsoleColor.GREEN);

        // load fxml
        scene = new Scene( LoadFXML("Main") );

        // set icon
        stage.getIcons().add(new Image(App.class.getResourceAsStream("images/Hg.png"))); 
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