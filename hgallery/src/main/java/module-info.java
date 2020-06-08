module com.JCxYIS.hgallery 
{
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens hgallery to javafx.fxml;
    exports hgallery;
}