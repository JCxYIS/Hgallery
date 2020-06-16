package hgallery.File;

import java.io.File;
import java.io.FilenameFilter;

import hgallery.Debug;
import hgallery.Debug.ConsoleColor;
import hgallery.Settings.SettingManager;

public class FileOperate 
{
    /**
     * 回傳所有GALLERY的資料夾
     */
    public static File[] GetGallerys()
    {
        return GetAlbums(SettingManager.settings.galleryPath);
    }

    /**
     * 回傳所有HENTAI的資料夾
     */
    public static File[] GetHentais()
    {
        return GetAlbums(SettingManager.settings.hentaiPath);
    }


    public static String ToValidFileName(String rawFileName)
    {
        final char[] ILLEGAL_CHARACTERS = { '/', '\n', '\r', '\t', '\0', '\f', '`', '?', '*', '\\', '<', '>', '|', '\"', ':' };
        for(int i = 0; i < rawFileName.length(); i++) 
        {
            if(rawFileName[i] == )
        }
    }

    /**
     * 
     */
    private static File[] GetAlbums(String albumPath)
    {
        // 
        File galleryPath = new File(albumPath);

        // create folder
        if(!galleryPath.exists())
        {
            galleryPath.mkdirs();
            Debug.Log("資料夾不存在，建立 "+galleryPath, ConsoleColor.YELLOW);
        }

        // Get dir paths
        File[] directories = galleryPath.listFiles(new FilenameFilter() 
        {
            @Override
            public boolean accept(File current, String name) 
            {
                return new File(current, name).isDirectory();
            }
        });
    
        // return
        //System.out.println("所求的資料夾："+Arrays.toString(directories));
        return directories; 
    }

    



}