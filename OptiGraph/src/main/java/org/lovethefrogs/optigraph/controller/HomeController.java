package org.lovethefrogs.optigraph.controller;


import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import org.lovethefrogs.optigraph.model.Config;
import org.lovethefrogs.optigraph.model.Graph;
import org.lovethefrogs.optigraph.model.Node;
import org.lovethefrogs.optigraph.utils.NodeCellFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeController {
    private static double PANE_WIDTH;
    private static double PANE_HEIGHT;
    private static int MAX_COORD = 1;
    private Graph graph = new Graph();
    private Config config = new Config();
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
    private ProgressBar progressBar;
    @FXML
    private Label progressLabel;

    @FXML
    private void initialize() throws IOException, ClassNotFoundException {
        nodeList.setCellFactory(new NodeCellFactory());
        nodeList.setPrefHeight(100000);
        config.load();
        if (config.getFile() != null) {
            loadData(config.getFile());
            MAX_COORD = config.getMax();
        }
        bindWindowClose();
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
        } catch (NumberFormatException e) {
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
        } catch(NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input data");
            alert.setContentText("The value for the id must be a number.");
            alert.showAndWait();
        }
    }

    @FXML
    protected void onPlotButtonClick() {
        Task task = new Task<Void>() {
            @Override
            public Void call() {
                int aux = 2 * (graph.getNodeCount() - 1) + graph.getNodeCount();
                ArrayList<List<Integer>> result;
                Platform.runLater(() -> progressLabel.setText("Calculating result"));
                if (config.isDijkstra()) result = graph.dijkstra(progress -> {
                        updateProgress(progress, aux);
                    });
                else result = graph.prim(progress -> {
                        updateProgress(progress, aux);
                    });

                int step = graph.getNodeCount() - 1;
                updateProgress(step, aux);
                PANE_HEIGHT = graphPane.getHeight();
                PANE_WIDTH = graphPane.getWidth();

                Platform.runLater(() -> {
                    graphPane.getChildren().clear();
                    progressLabel.setText("Plotting nodes");
                });
                for (Node node : graph.getNodeList()) {
                    Platform.runLater(() -> drawCircles(node));
                    step++;
                    updateProgress(step, aux);
                }
                Platform.runLater(() -> progressLabel.setText("Plotting edges"));
                HashMap<Integer, Node> dict = graph.getNodes();
                for (List<Integer> e : result) {
                    Node a = dict.get(e.get(0));
                    Node b = dict.get(e.get(1));
                    Platform.runLater(() -> {
                                Line edge = generateEdge(a.getCoords().getX(), a.getCoords().getY(), b.getCoords().getX(), b.getCoords().getY());
                                graphPane.getChildren().add(edge);
                    });
                    step++;
                    updateProgress(step, aux);
                }
                updateProgress(0, aux);
                Platform.runLater(() -> progressLabel.setText(""));
                return null;
            }
        };
        if (!graph.isEmpty()) {
            progressBar.progressProperty().bind(task.progressProperty());
            new Thread(task).start();
        }
    }

    @FXML
    protected void createNewFile() {
        graph = new Graph();
        graphPane.getChildren().clear();
        nodeList.getItems().clear();
        config.setFile(null);
    }

    @FXML
    protected void saveFile() throws IOException {
        File savesDirectory = Paths.get(System.getProperty("user.dir"), "saves").toFile();
        if (!savesDirectory.exists()) Files.createDirectories(Paths.get(System.getProperty("user.dir"), "saves"));

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save state");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Data files (*.dat)", "*.dat"));
        fileChooser.setInitialDirectory(savesDirectory);
        Window mainWindow = graphPane.getScene().getWindow();
        File file = fileChooser.showSaveDialog(mainWindow);
        if (file != null) {
            if (!file.getName().endsWith(".dat")) {
                file = new File(file.getParent(), file.getName() + ".dat");
            }
            saveData(file);
            config.setFile(file);
            config.setMax(MAX_COORD);
        }
    }

    @FXML
    protected void openFile() throws IOException, ClassNotFoundException {
        File savesDirectory = Paths.get(System.getProperty("user.dir"), "saves").toFile();
        if (!savesDirectory.exists()) Files.createDirectories(Paths.get(System.getProperty("user.dir"), "saves"));
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open state");
        fileChooser.setInitialDirectory(savesDirectory);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Data Files (*.dat)", "*.dat"));

        File file = fileChooser.showOpenDialog(graphPane.getScene().getWindow());

        if (file != null) {
            loadData(file);
            config.setFile(file);
            config.setMax(MAX_COORD);
        }
    }

    @FXML
    protected void quitProgram() throws IOException {
        Stage stage = (Stage) graphPane.getScene().getWindow();
        stage.close();
        if (config.getFile() != null) saveData(config.getFile());
        config.save();
    }

    @FXML
    protected void changeMode() {
        config.setDijkstra(!config.isDijkstra());
    }

    private static Circle generateCircle(double x, double y) {
        double scaledX = (x / (MAX_COORD * 1.10)) * (PANE_WIDTH / 2);
        double scaledY = (y / (MAX_COORD * 1.10)) * (PANE_HEIGHT / 2);
        double mappedX = scaledX + (PANE_WIDTH / 2);
        double mappedY = (PANE_HEIGHT / 2) - scaledY;
        return new Circle(mappedX, mappedY, 5);
    }

    private static Line generateEdge(double x1, double y1, double x2, double y2) {
        double scaledX1 = (x1 / (MAX_COORD * 1.10)) * (PANE_WIDTH / 2);
        double scaledY1 = (y1 / (MAX_COORD * 1.10)) * (PANE_HEIGHT / 2);
        double scaledX2 = (x2 / (MAX_COORD * 1.10)) * (PANE_WIDTH / 2);
        double scaledY2 = (y2 / (MAX_COORD * 1.10)) * (PANE_HEIGHT / 2);
        double mappedX1 = scaledX1 + (PANE_WIDTH / 2);
        double mappedY1 = (PANE_HEIGHT / 2) - scaledY1;
        double mappedX2 = scaledX2 + (PANE_WIDTH / 2);
        double mappedY2 = (PANE_HEIGHT / 2) - scaledY2;

        return new Line(mappedX1, mappedY1, mappedX2, mappedY2);
    }

    private void saveData(File file) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
        out.writeObject(graph);
        out.close();
    }

    private void loadData(File file) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
        graph = (Graph) in.readObject();
        in.close();
        for (Node node : graph.getNodeList()) nodeList.getItems().add(node);
    }

    private void bindWindowClose() {
        graphPane.sceneProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Stage stage = (Stage) newValue.getWindow();
                stage.setOnCloseRequest(event -> {
                    try {
                        quitProgram();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        });
    }

    private void drawCircles(Node node) {
        Circle circle = generateCircle(node.getCoords().getX(), node.getCoords().getY());
        if (node.isCenter()) {
            circle.setFill(Color.RED);
        } else {
            circle.setFill(Color.BLACK);
        }
        graphPane.getChildren().add(circle);

        Text nodeName = new Text(node.getName());
        nodeName.setX(circle.getCenterX() - nodeName.getLayoutBounds().getWidth() / 2);
        nodeName.setY(circle.getCenterY() - 10);
        graphPane.getChildren().add(nodeName);
    }
}