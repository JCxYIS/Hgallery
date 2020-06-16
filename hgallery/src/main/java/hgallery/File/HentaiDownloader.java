package hgallery.File;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import com.github.ttdyce.model.Comic;

import hgallery.App;
import hgallery.Debug;
import hgallery.MessageBoxController;
import hgallery.AlbumReader.AlbumFileReader;
import hgallery.Debug.ConsoleColor;
import hgallery.Settings.SettingManager;
import javafx.application.Platform;
import javafx.concurrent.Task;

public class HentaiDownloader 
{
    private static Task<Object> mainTask;
    private static MessageBoxController progressBox;

    public static void DownloadHentai(Comic hon)
    {
        if(mainTask != null && mainTask.isRunning())
        {
            MessageBoxController.CreateMessageBox("稍安勿躁", "還在下載其他本。請稍安勿躁，待會再試。");
            Debug.Log("還在載本中", ConsoleColor.YELLOW);
            return;
        }
        else
        {
            mainTask = MainTask(hon);                  
            progressBox = MessageBoxController.CreateProgressBarBox("下載中", "你可以放心關閉此視窗，完成後會通知你！");
            progressBox.progressBar.progressProperty().bind(mainTask.progressProperty());

            Thread t = new Thread(mainTask);
            App.runningThreads.add(t);
            t.start();
        }       
    }

    private static void DownloadHentaiComplete(Comic hon)
    {
        mainTask = null;
        Platform.runLater(new Runnable() 
        {
            @Override 
            public void run() 
            {
                progressBox.Close();
                MessageBoxController.CreateMessageBox("下載完成！", "下載 "+hon.getTitle().toString()+" 完成，請至保險箱查收！");   
            }
        });
        
    }

    // 參考：https://www.yiibai.com/javafx/javafx_progressbar.html
    private static Task<Object> MainTask(Comic hon) 
    {
        return new Task<Object>() 
        {
            @Override
            protected Object call() throws Exception 
            {
                // vars
                String hname = hon.getTitle().toString();
                String outDir =  SettingManager.settings.hentaiPath + "\\" + FileOperate.ToValidFileName(hname);
                new File(outDir).mkdirs();

                // iterate the hons
                for (int i = 1; i <= hon.getNumOfPages(); i++) 
                {
                    String inurl  = AlbumFileReader.GetHonImagePath(hon, i);
                    String[] tmp = inurl.split("/");
                    String outurl = outDir + "\\"+ tmp[tmp.length-1];
                    // DL
                    try(BufferedInputStream in = new BufferedInputStream(new URL(inurl).openStream());
                        FileOutputStream fileOutputStream = new FileOutputStream(outurl)) 
                    {
                        byte[] dataBuffer = new byte[1024];
                        int bytesRead;               
                        while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) 
                        {
                            fileOutputStream.write(dataBuffer, 0, bytesRead);                            
                        }
                    } 
                    catch (IOException e) 
                    {
                        Debug.Log(e, ConsoleColor.RED);
                    }

                    updateProgress(i, hon.getNumOfPages());
                    Debug.Log("已下載本子："+i+" / "+hon.getNumOfPages());
                }
                DownloadHentaiComplete(hon);
                return true;
            }
        };
    }

}