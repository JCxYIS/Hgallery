package hgallery.AlbumReader;

import java.io.File;

import hgallery.Debug;
import hgallery.Debug.ConsoleColor;
import hgallery.File.CreateThumbnail;
import hgallery.Settings.SettingManager;
import hgallery.Settings.Settings;

public class AlbumReader 
{

    public AlbumReader()
    {

    }

    public void Read()
    {
        
    }

    /**
     * 取得縮圖連結。沒有的話會建一個：失敗會return null
     */
    public static String GetThumbnailPath(String albumName) 
    {
        File f = new File(SettingManager.settings.galleryPath + "\\_thumbnails\\"+albumName+".jpg");

        if(f.exists()) // return it
        {
            return f.getAbsolutePath();
        }
        else // create it
        {
            try
            {
                File album = new File( SettingManager.settings.galleryPath+"\\"+albumName );
                File picToMakeAlbum = album.listFiles()[0];

                Debug.Log("正在製作縮圖："+albumName+" 的 "+picToMakeAlbum.getName(), ConsoleColor.PURPLE);
                CreateThumbnail.CreateThumbnail(picToMakeAlbum.getAbsolutePath(), f.getAbsolutePath());
                return f.getAbsolutePath();
            }
            catch (Exception e)
            {
                Debug.Log("製作縮圖發生錯誤："+e, ConsoleColor.RED);
                return null;
            }
            
        }
    }
}