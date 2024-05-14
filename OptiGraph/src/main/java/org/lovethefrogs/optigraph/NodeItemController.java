package org.lovethefrogs.optigraph;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import org.lovethefrogs.optigraph.model.Node;

import java.io.IOException;

public class NodeItemController extends ListCell<Node> {
    @FXML
    private Label nameLabel;
    @FXML
    private Label coordsLabel;
    @FXML
    private Label idLabel;
    private int id;
    public NodeItemController() {
        loadFXML();
    }

    private void loadFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("node-cell.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void updateItem(Node item, boolean empty) {
        super.updateItem(item, empty);

        if(empty || item == null) {
            setText(null);
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }
        else {
            this.id = item.getId();
            nameLabel.setText(item.getName());
            coordsLabel.setText("x: " + item.getCoords().getX() + " y: " + item.getCoords().getY());
            idLabel.setText("id: " + id);

            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }
    }
}
