package hgallery.Settings;

import java.io.IOException;
import java.util.ArrayList;

import com.github.ttdyce.model.Comic;
import com.github.ttdyce.model.api.NHAPI;

import hgallery.App;
import hgallery.Debug;
import hgallery.GalleryController;
import hgallery.MessageBoxController;
import hgallery.NHAPI.NhentaiUtility;
import javafx.application.Platform;

public class RuntimeSettings 
{
	private static ArrayList<Comic> readLater = new ArrayList<>();

	
	public static void AddReadLater(Comic comic)
	{
		readLater.add(comic);
		RefreshSettingReadLaterList();
	}
	public static void RemoveReadLater(Comic comic)
	{
		readLater.remove(comic);
		RefreshSettingReadLaterList();
	}
	public static boolean ContainsReadLater(Comic comic)
	{
		return readLater.contains(comic);
	}
	public static void RefreshSettingReadLaterList()
	{
		ArrayList<Integer> hids = new ArrayList<>();
		for (Comic c : readLater) 
		{
			hids.add(c.getId());
		}
		SettingManager.settings.readLaterIDs = hids.toArray(Integer[]::new);
	}



	public static Comic[] GetReadLater()
	{
		return readLater.toArray(Comic[]::new);
	}
	/**
	 * Check initial run
	 */
	public static Comic[] GetReadLater(GalleryController gc)
	{
		if(readLater.size() == 0 && SettingManager.settings.readLaterIDs.length > 0)
		{
			var m = MessageBoxController.CreateProgressBarBox("請稍後", "正在重建稍後閱讀清單");
			m.ToggleConfirmButton(false);
			StartFetchReadLaterThread(SettingManager.settings.readLaterIDs, m, gc);
		}
		return readLater.toArray(Comic[]::new);
	}
	public static Thread StartFetchReadLaterThread(Integer[] hIdArr, MessageBoxController messageBoxController, GalleryController galleryController)
    {        
        Thread t = new Thread(new Runnable()
        {
            @Override
            public void run() 
            {                
				NHAPI api = new NHAPI();   
                // main loopp
                for(int i = 0; i < hIdArr.length; i++)
                {                 
					// dl
					try 
					{
						var c = NhentaiUtility.GetComicByID(api, hIdArr[i]);
						readLater.add(c);
					} catch (IOException e1) {

					}                      
					
					// check if program has terminated
                    try 
                    {
					    Thread.sleep(1);
                    } catch (InterruptedException e) 
                    {
                        Debug.Log("FetchReadLater DL Interupted: Exception");
                        return;
					}
				}

				Platform.runLater(new Runnable() 
        		{
        		    @Override 
        		    public void run() 
        		    {
						messageBoxController.Close();
						galleryController.Set(GetReadLater(), true);
						galleryController.Print();
        		        Debug.Log("Read Later Fetch Done！");   
        		    }
        		});
            }
        }) ;  
        t.setName("FetchReadLater DL Thread"); 
        t.start(); 
        App.runningThreads.add(t);
        return t;
    }
}