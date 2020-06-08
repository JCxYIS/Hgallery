## JAFAFX TASK
- main
```java
 Task<Void> progressTask = new Task<Void>()
{
 
    @Override
    protected void succeeded() {
        super.succeeded(); 
        updateMessage("Succeeded");
    }
 
    @Override
    protected void cancelled() {
        super.cancelled(); 
        updateMessage("Cancelled");
    }
 
    @Override
    protected void failed() {
        super.failed(); 
        updateMessage("Failed");
    }
 
    @Override
    protected Void call() throws Exception {
        for(int i = 0; i < 100; i++){
            Thread.sleep(50);
            updateProgress(i + 1, 100);
            updateMessage("Loading..." + (i + 1) + "%");
        }
        updateMessage("Finish");
        return null;
    }
};
```

- Call:
```java
new Thread(task).start();
```


## want update UI?
```java
Platform.runLater(new Runnable() 
{
    @Override 
    public void run() 
    {
        // blah blah blah UI   
    }
});
```