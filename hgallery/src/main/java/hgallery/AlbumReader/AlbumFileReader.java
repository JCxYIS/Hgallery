package hgallery.AlbumReader;

import java.io.File;


public class AlbumFileReader 
{

    public AlbumFileReader()
    {
       
    }

    /**
     * 取得縮圖連結。沒有的話會建一個：失敗會return null
     */
    public static File GetThumbnailPath(File albumDirPath) 
    {
        return albumDirPath.listFiles()[0];

        // 現在可以不用，因為JAVAFX的讀圖
        /*
        File f = new File(SettingManager.settings.galleryPath + "\\_thumbnails\\"+albumName+".jpg");

        if(f.exists()) // return it
        {
            //return new File( SettingManager.settings.galleryPath+"\\"+albumName ).listFiles()[0].getAbsolutePath(); // ORIGINAL
            return f; // THUMB
        }
        else // create it
        {
            try
            {
                File album = new File( SettingManager.settings.galleryPath+"\\"+albumName );
                File picToMakeAlbum = album.listFiles()[0];

                Debug.Log("正在製作縮圖："+albumName+" 的 "+picToMakeAlbum.getName(), ConsoleColor.PURPLE);
                CreateThumbnail.CreateThumbnail(picToMakeAlbum.getAbsolutePath(), f.getAbsolutePath());
                return f;
            }
            catch (Exception e)
            {
                Debug.Log("製作縮圖發生錯誤："+e, ConsoleColor.RED);
                return null;
            }
            
        }
        */
    }
}