package hgallery.Settings;

import java.io.Serializable;

public class Settings implements Serializable
{
    private static final long serialVersionUID = 3L;

    public String galleryPath = "";
    public String hentaiPath = "";   
    public String pswEncrypted = "";

    /**
     * Default Values
     */
    public Settings()
    {
        galleryPath = SettingManager.saveDir + "\\Gallery";
        hentaiPath = SettingManager.saveDir + "\\Hentai";
        pswEncrypted = "";
    }
    
    @Override
    public String toString()
    {
        return "galleryPath="+galleryPath+"\n"+
               "hentaiPath="+hentaiPath +"\n"+
               "pswEncrypted="+pswEncrypted+"\n";
    }
}