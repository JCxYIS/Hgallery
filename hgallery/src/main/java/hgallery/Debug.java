package hgallery;

/**
 * 方便用，不然每次打System.out.println有夠累
 */
public class Debug 
{
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static enum  ConsoleColor  {RESET,       BLACK,        RED,          GREEN,        YELLOW,       BLUE,         PURPLE,       CYAN,         WHITE};
    private static final String[] c = {"\u001B[0m", "\u001B[30m", "\u001B[31m", "\u001B[32m", "\u001B[33m", "\u001B[34m", "\u001B[35m", "\u001B[36m", "\u001B[37m"};


    /**
    * 方便用，不然每次打System.out.println有夠累
    */
    public static void Log(Object log)
    {
        System.out.println(log);
    }

    public static void Log(Object log, ConsoleColor color)
    {
        System.out.println(c[color.ordinal()] + log + c[0]);
    }
}