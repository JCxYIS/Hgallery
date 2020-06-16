package hgallery.NHAPI;

import java.io.IOException;
import java.util.ArrayList;

import com.github.ttdyce.model.Comic;
import com.github.ttdyce.model.api.NHAPI;
import com.github.ttdyce.model.api.ResponseCallback;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class NhentaiUtility 
{
    public static ArrayList<Comic> GetListByName(final NHAPI api, final String query) throws IOException
    {
        ArrayList<Comic> hlist = new ArrayList<>();

        final ResponseCallback comicListReturnCallback = new ResponseCallback() 
        {
            @Override
            public void onReponse(final String response) 
            {
                //Debug.Log("Response:\n"+response);
                final JsonArray array = new JsonParser().parse(response).getAsJsonArray();
                final Gson gson = new Gson();
                
                for (final JsonElement jsonElement : array) 
                {
                    final Comic c = gson.fromJson(jsonElement, Comic.class);
                    hlist.add(c);
                }
            }
        }; 
        api.getComicList(query, true, comicListReturnCallback);
        return hlist;
    }

    public static Comic GetComicByID(final NHAPI api, final int id) throws IOException
    {
        ArrayList<Comic> hlist = new ArrayList<>();

        final ResponseCallback comicReturnCallback = new ResponseCallback() 
        {          
            @Override
            public void onReponse(final String response) 
            {
                //Debug.Log("Response:\n"+response);
                JsonObject object = new JsonParser().parse(response).getAsJsonObject();
                Gson gson = new Gson();
                hlist.add(gson.fromJson(object, Comic.class));
            }            
        };
        api.getComic(id, comicReturnCallback);
        return hlist.get(0);
    }
}