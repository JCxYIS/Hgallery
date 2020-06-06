package hgallery;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import hgallery.Debug.ConsoleColor;
import hgallery.Encryption.Encryption;
import hgallery.Settings.SettingManager;
import hgallery.Settings.Settings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;

public class SettingController 
{
    @FXML private ScrollPane scp;

    @FXML private TextField tf_galleryPath;
    @FXML private TextField tf_hentaiPath;

    @FXML private PasswordField pf_oldpsw;
    @FXML private PasswordField pf_newpsw;

    @FXML private Label lab_status;



    @FXML
    private void initialize() 
    {
        lab_status.setText("");
        tf_galleryPath.setText(SettingManager.settings.galleryPath);
        tf_hentaiPath.setText(SettingManager.settings.hentaiPath);

        if(SettingManager.settings.pswEncrypted.equals(""))
        {
            pf_oldpsw.setDisable(true);
            pf_oldpsw.setPromptText("你不用輸入舊密碼！請直接設定新密碼！");
        }

        Debug.Log("已開啟設定面板", ConsoleColor.GREEN);
    }

    @FXML
    private void SaveSettings() throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        String output = "";

        SettingManager.settings.galleryPath = tf_galleryPath.getText();
        SettingManager.settings.hentaiPath = tf_hentaiPath.getText();

        // wanna set new psw
        if(pf_newpsw.getText().length() > 0 || SettingManager.settings.pswEncrypted.equals("") )
        {
            // check psw correct or not
            if( SettingManager.settings.pswEncrypted.equals("") || 
                Encryption.validatePassword(pf_oldpsw.getText(), SettingManager.settings.pswEncrypted) )
            {
                var np = Encryption.generateStorngPasswordHash( pf_newpsw.getText() );
                SettingManager.settings.pswEncrypted = np;
                pf_oldpsw.setStyle("-fx-background-color : #80ffb7");

                output += "已儲存新密碼！\n";
                Debug.Log("密碼對了！儲存新密碼(加密後)："+np, ConsoleColor.GREEN);
            }
            else
            {
                pf_oldpsw.setStyle("-fx-background-color : #ff9e9e");
                Debug.Log("密碼不對！不會儲存新密碼", ConsoleColor.GREEN);
                
                lab_status.setText("密碼不對喔！\n設定不會被儲存。");
                return;
            }
        }        

        output += "已儲存設定！";
        lab_status.setText(output);
        Debug.Log("已儲存設定！", ConsoleColor.GREEN);
    }
}