package hgallery.File;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.net.URL;

import com.github.ttdyce.model.Comic;

import hgallery.AlbumReader.AlbumFileReader;
import hgallery.Settings.SettingManager;
import javafx.concurrent.Task;

public class HentaiDownloader 
{
    public static Task mainTask;

    public void DownloadHentai(Comic hon)
    {
        
        progressBar.progressProperty().unbind();
        progressBar.progressProperty().bind(mainTask.progressProperty());
        new Thread(mainTask).start();
    }

    // https://www.yiibai.com/javafx/javafx_progressbar.html
    public Task MainTask(Comic hon) 
    {
        return new Task() 
        {
            @Override
            protected Object call() throws Exception 
            {
                String hname = hon.getTitle().toString();
                String inDir = SettingManager.settings.hentaiPath + "\\" + hon.getTitle().toString();

                for (int i = 1; i <= hon.getNumOfPages(); i++) 
                {
                    String inUrl = AlbumFileReader.GetHonImagePath(hon, i);
                    try(BufferedInputStream in = new BufferedInputStream(new URL(inurl).openStream());
                        FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME)) 
                    {
                        byte dataBuffer[] = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                            fileOutputStream.write(dataBuffer, 0, bytesRead);
                        }
                    } catch (IOException e) {
                        // handle exception
                    }
                    updateProgress(i + 1, 10);
                }
                return true;
            }
        };
    }

}