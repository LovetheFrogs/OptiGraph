package org.lovethefrogs.optigraph.utils;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import org.lovethefrogs.optigraph.NodeItemController;
import org.lovethefrogs.optigraph.model.Node;

public class NodeCellFactory implements Callback<ListView<Node>, ListCell<Node>> {
    @Override
    public ListCell<Node> call(ListView<Node> param) {
        return new NodeItemController();
    }
}
