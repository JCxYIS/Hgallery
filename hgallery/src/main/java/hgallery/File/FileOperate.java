package hgallery.File;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;

import hgallery.Debug;
import hgallery.Debug.ConsoleColor;
import hgallery.Settings.SettingManager;

public class FileOperate 
{
    public static String[] GetGallerys()
    {
        File galleryPath = new File(SettingManager.settings.galleryPath);

        // create folder
        if(!galleryPath.exists())
        {
            galleryPath.mkdirs();
            Debug.Log("資料夾不存在，建立 "+galleryPath, ConsoleColor.YELLOW);
        }

        // Get dir paths
        String[] directories = galleryPath.list(new FilenameFilter() 
        {
            @Override
            public boolean accept(File current, String name) 
            {
                return new File(current, name).isDirectory();
            }
        });
    
        // return
        System.out.println("所求的資料夾："+Arrays.toString(directories));
        return directories; 
    }
}