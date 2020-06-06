package hgallery.Settings;

import java.io.Serializable;

import hgallery.Debug;
import hgallery.Debug.ConsoleColor;

public class Settings implements Serializable
{
    private static final String serialVersionUID = "save_v.1.0";

    public String galleryPath = "/gallery";
    public String hentaiPath = "/hon";   
    
    @Override
    public String toString()
    {
        return "galleryPath="+galleryPath+"\n"+
                "hentaiPath="+hentaiPath;
    }
}