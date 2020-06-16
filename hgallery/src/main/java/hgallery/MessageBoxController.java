package hgallery;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import hgallery.Debug.ConsoleColor;
import hgallery.Encryption.Encryption;
import hgallery.Interfaces.ButtonPressEvent;
import hgallery.Settings.SettingManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MessageBoxController 
{
    @FXML Pane pane;
    @FXML Label lab_title;
    @FXML Label lab_msg;
    @FXML PasswordField pswField;
    @FXML public ProgressBar progressBar;
    @FXML HBox hbox_buttParent;
    @FXML Button butt_confirm;
    @FXML Button butt_cancel;

    private ButtonPressEvent onConfirmClicked;
    private boolean isPswInput = false;
    private Stage myStage;

    private static MessageBoxController pswInputInstance;

    

    @FXML 
    private void onConfirmClick() throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        if(isPswInput)
        {            
            pswInputInstance = null;
            if( Encryption.validatePassword( pswField.getText(), SettingManager.settings.pswEncrypted ) )
            {
                Debug.Log("密碼正確", ConsoleColor.GREEN);
            }
            else
            {
                Debug.Log("密碼錯誤", ConsoleColor.GREEN);
                CreateMessageBox("密碼錯誤", "爛耶", ()->{CreatePasswordInput(onConfirmClicked);}, false);
                myStage.close();
                return;
            }            
        }
        else
        {
            Debug.Log("確認。", ConsoleColor.BLUE);          
        }

        if(onConfirmClicked != null)
            onConfirmClicked.Action();
        myStage.close();
    }

    @FXML
    private void onCancelClick()
    {
        myStage.close();
        Debug.Log("取消。", ConsoleColor.BLUE);
    }

    public void ToggleConfirmButton(boolean on)
    {
        butt_confirm.setDisable(!on);
    }
    public void SetMessage(String msg)
    {
        lab_msg.setText(msg);
    }


    //--------------------------------------------------------------
    
    public static MessageBoxController CreateMessageBox(String title, String msg)
    {
        return CreateMessageBox(title, msg, null, false);
    }
    public static MessageBoxController CreateMessageBox(String title, String msg, ButtonPressEvent onConfirm, boolean showCancel)
    {
		var c = CreateWindow();
        c.lab_title.setText(title);
        c.lab_msg.setText(msg);

        // remove stuffs
        if(!showCancel)
            c.hbox_buttParent.getChildren().remove(c.butt_cancel);
        c.pane.getChildren().remove(c.pswField);
        c.pane.getChildren().remove(c.progressBar);

        c.onConfirmClicked = onConfirm;
        return c;
    }
    public static MessageBoxController CreateProgressBarBox(String title, String msg)
    {
		var c = CreateWindow();
        c.lab_title.setText(title);
        c.lab_msg.setText(msg);

        // remove stuffs
        c.hbox_buttParent.getChildren().remove(c.butt_cancel);
        c.pane.getChildren().remove(c.pswField);
        return c;
    }
    public static MessageBoxController CreatePasswordInput(ButtonPressEvent onPswMatch)
    {
        if(SettingManager.settings.pswEncrypted.equals(""))
        {
            Debug.Log("密碼未設定", ConsoleColor.GREEN);
            CreateMessageBox("密碼未設定", "請先去設定密碼");
            return null;
        }
        if(pswInputInstance != null)
        {
            pswInputInstance.myStage.close();
            Debug.Log("一個輸入密碼instance存在！關閉他.");
        }

        var c = CreateWindow();
        c.pane.getChildren().remove(c.progressBar);
        c.onConfirmClicked = onPswMatch;
        c.isPswInput = true;
        pswInputInstance = c;
        return c;
    }
    private static MessageBoxController CreateWindow()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(MessageBoxController.class.getResource("_PopUpWindow.fxml"));
            Parent p = loader.load();
            Scene scene = new Scene(p);
            MessageBoxController mbc = loader.getController();            

            Stage stage = new Stage();
            mbc.myStage = stage;
            stage.setTitle("Hgallery Message");
            stage.getIcons().add(new Image(MessageBoxController.class.getResourceAsStream("images/Hg.png"))); 
            stage.setScene(scene);
            stage.show();

            return mbc;
        }
        catch (Exception e)
        {
            Debug.Log("MessageBox error: "+e, ConsoleColor.RED);
            return null;
        }        
    }
}
