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

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;


    public static void main(String[] args) 
    {
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
        stage.setTitle("hgallery");

        // show
        stage.setScene(scene);
        stage.show();
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