package org.lovethefrogs.optigraph.controller;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.lovethefrogs.optigraph.model.Graph;
import org.lovethefrogs.optigraph.model.Node;
import org.lovethefrogs.optigraph.utils.NodeCellFactory;

public class HomeController {
    private Graph graph = new Graph();
    @FXML
    private TextField nameInput;
    @FXML
    private TextField xCoordInput;
    @FXML
    private TextField yCoordInput;
    @FXML
    private ListView<Node> nodeList;
    @FXML
    private TextField idInput;

    @FXML
    private void initialize() {
        nodeList.setCellFactory(new NodeCellFactory());
        nodeList.setPrefHeight(100000);
    }

    @FXML
    protected void onAddNodeButtonClick() {
        String name = nameInput.getText();
        try {
            int x = Integer.parseInt(xCoordInput.getText());
            int y = Integer.parseInt(yCoordInput.getText());
            graph.addNode(name, x, y);
            nodeList.getItems().add(graph.getNodes().get(graph.getNodes().size() - 1));
        } catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input data");
            alert.setContentText("The values for the coordinates must be numbers.");
            alert.showAndWait();
        }
    }

    @FXML
    protected void onDeleteButtonClick() {
        try {
            int id = Integer.parseInt(idInput.getText());
            nodeList.getItems().remove(graph.removeNode(graph.getNodes().get(id)));
        } catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input data");
            alert.setContentText("The value for the id must be a number.");
            alert.showAndWait();
        }
    }


}