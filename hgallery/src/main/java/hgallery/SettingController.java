package hgallery;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import hgallery.Debug.ConsoleColor;
import hgallery.Encryption.Encryption;
import hgallery.Settings.SettingManager;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class SettingController
{
    @FXML private ScrollPane sp;
    @FXML private AnchorPane ap;

    @FXML private TextField tf_galleryPath;
    @FXML private TextField tf_hentaiPath;
    @FXML private TextField tf_blur;

    @FXML private PasswordField pf_oldpsw;
    @FXML private PasswordField pf_newpsw;


    @FXML
    private void initialize() 
    {
        // init params
        tf_galleryPath.setText(SettingManager.settings.galleryPath);
        tf_hentaiPath.setText(SettingManager.settings.hentaiPath);
        tf_blur.setText(""+SettingManager.settings.blur);

        //
        if (SettingManager.settings.pswEncrypted.equals("")) {
            pf_oldpsw.setDisable(true);
            pf_oldpsw.setPromptText("你不用輸入舊密碼！請直接設定新密碼！");
        }

        Debug.Log("已開啟設定面板", ConsoleColor.GREEN);
    }

    @FXML
    private void RefreshSize()
    {
        // set size
        var w = sp.getWidth();
        var h = sp.getHeight();
        ap.setPrefSize(w, h);
        //Debug.Log(w+ ", "+ h);
    }


    @FXML
    private void SaveSettings() throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        String output = "";        

        // save normal stuffs
        SettingManager.settings.galleryPath = tf_galleryPath.getText();
        SettingManager.settings.hentaiPath = tf_hentaiPath.getText();

        // save blur
        try 
        {
            SettingManager.settings.blur = Integer.parseInt(tf_blur.getText());

            if(SettingManager.settings.blur > 100 || SettingManager.settings.blur < 0)
            {
                MessageBoxController.CreateMessageBox("參數錯誤", "解析「模糊度」發生錯誤：「模糊度」應該要大於0，小於100");
                return;
            }
        }
        catch (Exception e)
        {
            MessageBoxController.CreateMessageBox("參數錯誤", "解析「模糊度」發生錯誤：輸入可能不是數字");
            Debug.Log("解析「模糊度」發生錯誤：輸入可能不是數字"+e, ConsoleColor.RED);
            return;
        }

        // wanna set new psw
        try
        {
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
                    MessageBoxController.CreateMessageBox("密碼錯誤", "密碼不對喔！\n設定不會被儲存。");
                    return;
                }
            }  
        }   
        catch (Exception e)
        {
            MessageBoxController.CreateMessageBox("錯誤", "解析密碼時發生錯誤");
            Debug.Log("解析密碼發生錯誤"+e, ConsoleColor.RED);
            return;
        }   

        output += "已儲存設定！";
        MessageBoxController.CreateMessageBox("太棒了！", output);
        Debug.Log("已儲存設定！", ConsoleColor.GREEN);
    }
}