module com.JCxYIS.hgallery {
    requires javafx.controls;
    requires javafx.fxml;

    opens hgallery to javafx.fxml;
    exports hgallery;
}