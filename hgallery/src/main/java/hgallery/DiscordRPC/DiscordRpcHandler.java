package hgallery.DiscordRPC;

import hgallery.Debug;
import hgallery.Debug.ConsoleColor;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;

public class DiscordRpcHandler 
{
    private static Thread updThread = null;


    public static void Init()
    {
        //
        if(updThread != null)
        {
            Debug.Log("A discord update loop thread is running! Now abort.", ConsoleColor.YELLOW);
            return;
        }
        
        // coonect param
        Debug.Log("Discord連接中...", ConsoleColor.CYAN);
        DiscordEventHandlers handler = new DiscordEventHandlers.Builder()
            .setReadyEventHandler((user) -> 
            {
                Debug.Log("Discord已連接：連接至" + user.username + "#" + user.discriminator + "", ConsoleColor.CYAN);
                NewPresence("Idle", "");
            })
            .setErroredEventHandler((i,s)->
            {
                Debug.Log("Discord錯誤：" + i + "；" + s, ConsoleColor.RED);
            })
            .setDisconnectedEventHandler((i, s)->
            {
                Debug.Log("Discord中斷連線：" + i + "；" + s, ConsoleColor.CYAN);
            })
            .build();

        // real connect
        DiscordRPC.discordInitialize("720145909077377086", handler, true);
        DiscordRPC.discordRegister("720145909077377086", "");

        // set update loop
        updThread = new Thread(new Runnable()
        {
            @Override
            public void run() 
            {
                while(true) 
                {
                    try 
                    {
						Thread.sleep(5000);
                    } catch (InterruptedException e) 
                    {
                        Debug.Log("Discord update loop End.", ConsoleColor.CYAN);
                        return;
					}
                    //Debug.Log("Discord update loop: "+Thread.currentThread().getName(), ConsoleColor.CYAN);
                    DiscordRPC.discordRunCallbacks();
                }
            }
        }) ;  
        updThread.setName("Discord update loop"); 
        updThread.start();       
    }

    public static void NewPresence(String state, String detail)
    {
        NewPresence(state, detail, -1);
    }
    public static void NewPresence(String state, String detail, long setTimestamp)
    {
        DiscordRichPresence rich = new DiscordRichPresence.Builder(state).setDetails(detail).build();        
        if(setTimestamp != -1)
            rich.startTimestamp = setTimestamp;
        rich.largeImageKey = "icon";
        DiscordRPC.discordUpdatePresence(rich);
        Debug.Log("Discord設定狀態：" + state + " | " + detail, ConsoleColor.CYAN);
    }

    public static void Stop()
    {
        updThread.interrupt();
        DiscordRPC.discordShutdown();
    }
}