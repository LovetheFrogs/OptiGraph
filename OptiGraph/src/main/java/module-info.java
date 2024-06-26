module org.lovethefrogs.optigraph {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens org.lovethefrogs.optigraph to javafx.fxml;
    exports org.lovethefrogs.optigraph;
    exports org.lovethefrogs.optigraph.controller;
    exports org.lovethefrogs.optigraph.model;
    opens org.lovethefrogs.optigraph.controller to javafx.fxml;
    exports org.lovethefrogs.optigraph.utils;
    opens org.lovethefrogs.optigraph.utils to javafx.fxml;
}