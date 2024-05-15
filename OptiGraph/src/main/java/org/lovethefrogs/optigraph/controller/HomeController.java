package org.lovethefrogs.optigraph.controller;

import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import org.lovethefrogs.optigraph.model.Graph;
import org.lovethefrogs.optigraph.model.Node;
import org.lovethefrogs.optigraph.utils.NodeCellFactory;

import java.util.ArrayList;

public class HomeController {
    private static double PANE_WIDTH;
    private static double PANE_HEIGHT;
    private static int MAX_COORD = 1;
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
    private Pane graphPane;
    @FXML
    private Button plotButton;

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
            int aux = Math.max(Math.abs(x), Math.abs(y));
            if (aux > MAX_COORD) MAX_COORD = aux;
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

    @FXML
    protected void onPlotButtonClick() {
        PANE_HEIGHT = graphPane.getHeight();
        PANE_WIDTH = graphPane.getWidth();
        ArrayList<Circle> nodes = new ArrayList<>();
        graphPane.getChildren().clear();
        ArrayList<Text> nodeLabels = new ArrayList<>();
        for (Node node : graph.getNodeList()) {
            Circle circle = generateCircle(node.getCoords().getX(), node.getCoords().getY());
            circle.setFill(Color.BLACK);
            nodes.add(circle);

            Text nodeName = new Text(node.getName());
            nodeName.setX(circle.getCenterX() - nodeName.getLayoutBounds().getWidth() / 2);
            nodeName.setY(circle.getCenterY() - 10);
            nodeLabels.add(nodeName);
        }
        for (Circle circle : nodes) graphPane.getChildren().add(circle);
        for (Text text : nodeLabels) graphPane.getChildren().add(text);
    }

    public static Circle generateCircle(double x, double y) {
        double scaledX = (x / (MAX_COORD * 1.10)) * (PANE_WIDTH / 2);
        double scaledY = (y / (MAX_COORD * 1.10)) * (PANE_HEIGHT / 2);
        double mappedX = scaledX + (PANE_WIDTH / 2);
        double mappedY = (PANE_HEIGHT / 2) - scaledY;
        return new Circle(mappedX, mappedY, 5);
    }

}