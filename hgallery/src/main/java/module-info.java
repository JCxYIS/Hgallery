module com.JCxYIS.hgallery {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.JCxYIS.hgallery to javafx.fxml;
    exports com.JCxYIS.hgallery;
}