package hgallery;

import hgallery.Debug.ConsoleColor;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class ControllerUtility 
{
    /**
     * 讓某個子物件貼齊母Anchor Plane
     * @param node gimme ur child
     */
    public static void FitAnchorPlane(Node node)
    {
        // make the new item fit to the screen
        try
        {
            AnchorPane.setTopAnchor(node, 0.0);
            AnchorPane.setLeftAnchor(node, 0.0);
            AnchorPane.setRightAnchor(node, 0.0); 
            AnchorPane.setBottomAnchor(node, 0.0); 
        }
        catch (Exception e)
        {
            Debug.Log("無法使子物件貼合母Anchor Plane！"+e, ConsoleColor.RED);
        }
    }
}