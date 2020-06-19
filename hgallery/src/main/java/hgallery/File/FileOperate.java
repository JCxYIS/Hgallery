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

    /**
     * 取代不能當作檔案名稱的字元為'_'
     */
    public static String ToValidFileName(String fileName)
    {
        return fileName.replaceAll("[\\\\/:*?\"<>|]", "_");
    }

    /**
     * 獲取(小寫)副檔名，找不到會回傳空字串。
     */
    public static String GetExtension(String pathOrFileName)
    {
        String ext = "";
        int i = pathOrFileName.lastIndexOf('.');
        if(i > 0)
            ext = pathOrFileName.toLowerCase().substring(i+1);
        return ext;
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