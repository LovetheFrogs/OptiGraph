module org.lovethefrogs.optigraph {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens org.lovethefrogs.optigraph to javafx.fxml;
    exports org.lovethefrogs.optigraph;
    exports org.lovethefrogs.optigraph.controller;
    opens org.lovethefrogs.optigraph.controller to javafx.fxml;
}