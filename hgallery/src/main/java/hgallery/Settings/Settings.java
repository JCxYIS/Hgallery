package hgallery.Settings;

import java.io.Serializable;

public class Settings implements Serializable
{
    private static final long serialVersionUID = 3L;

    public String galleryPath = "/gallery";
    public String hentaiPath = "/hon";   
    public String pswEncrypted = "";
    
    @Override
    public String toString()
    {
        return "galleryPath="+galleryPath+"\n"+
               "hentaiPath="+hentaiPath +"\n"+
               "pswEncrypted="+pswEncrypted+"\n";
    }
}