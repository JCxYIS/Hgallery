package hgallery.AlbumReader;

import java.io.File;
import java.net.MalformedURLException;

import com.github.ttdyce.model.Comic;

import hgallery.Debug;
import hgallery.Debug.ConsoleColor;

public class AlbumFileReader {

    public AlbumFileReader() {

    }

    public static boolean IsValidPicType(File file) {
        String extension = "";
        String path = file.getAbsolutePath();
        int i = path.lastIndexOf('.');
        if (i > 0) {
            extension = path.substring(i + 1);
        }

        extension = extension.toLowerCase();
        // Debug.Log(extension);
        return extension.equals("png") || extension.equals("jpg") || extension.equals("jpeg") || extension.equals("gif")
                || extension.equals("bmp");
    }

    /**
     * 取得縮圖連結。沒有的話會建一個：失敗會return null
     * 
     * @throws MalformedURLException
     */
    public static String GetThumbnailPath(File albumDirPath) throws MalformedURLException 
    {
        for ( File f : albumDirPath.listFiles() ) 
        {
            if(IsValidPicType(f))
                return f.toURI().toURL().toString();    
        }
        return null;

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

    public static String GetHonImage(Comic hon, int pageStartWith1) 
    {
        //https://i.nhentai.net/galleries/1656422/3.jpg
        String ty = hon.getPageTypes()[pageStartWith1-1];
        String ext = GetHonExt(ty);
        return "https://i.nhentai.net/galleries/"+hon.getMid()+"/"+pageStartWith1+"."+ext;
    } 
    public static String GetHonThumbnail(Comic hon)
    {
        // https://t5.nhentai.net/galleries/1656422/cover.jpg
        return "https://t5.nhentai.net/galleries/"+hon.getMid()+"/cover.jpg";
    }

    /**
     * return ```jpg/png/gif``` by ```j/p/g```
     */
    private static String GetHonExt(String pictype)
    {
        if(pictype.equals("j"))
            return "jpg";
        if(pictype.equals("p"))
            return "png";
        if(pictype.equals("g"))
            return "gif";

        Debug.Log("奇怪的檔案類型出現了："+pictype, ConsoleColor.RED);
        return null;
    }

}