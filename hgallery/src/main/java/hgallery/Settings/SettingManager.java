package hgallery.Settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import hgallery.Debug;
import hgallery.Debug.ConsoleColor;

public class SettingManager  
{
    public static Settings settings;
    public static String saveDir;

    public static void Load()
    {
        // init savePath
        saveDir = System.getProperty("user.home") + "\\hgallery";
        File directory = new File(saveDir);
        if ( !directory.exists() )
        {
            directory.mkdirs();
        }

        // load save
        try
        {
            FileInputStream fis = new FileInputStream(saveDir+"\\save.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            settings = (Settings)ois.readObject();
            ois.close();

            Debug.Log("歡迎來到Hgallery！存檔的位置是 "+saveDir, ConsoleColor.GREEN);
            Debug.Log(settings.toString());
        }
        catch (IOException e)
        {
            settings = new Settings();
            Debug.Log("讀取存檔失敗！"+e.getStackTrace(), ConsoleColor.RED);
        }
        catch(ClassNotFoundException e)
        {
            settings = new Settings();
            Debug.Log("讀取存檔失敗！"+e.getStackTrace(), ConsoleColor.RED);
        }
        
    }

    public static void Save()
    {
        try
        {
            FileOutputStream fos = new FileOutputStream(saveDir+"\\save.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(settings);
            oos.flush();
            oos.close();
            fos.flush();
            fos.close();
            Debug.Log("感謝使用Hgallery！已存檔於 "+saveDir+"\\save.txt", ConsoleColor.GREEN);
        }
        catch(IOException e) 
        {
            Debug.Log("寫入存檔失敗！"+e.getStackTrace(), ConsoleColor.RED);
        }
    }
}