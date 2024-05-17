package org.lovethefrogs.optigraph.model;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Config implements Serializable {
    private boolean dijkstra;
    private File file;
    private int max;

    public Config() {
        this.dijkstra = true;
        this.file = null;
        this.max = 0;
    }

    public boolean isDijkstra() {
        return dijkstra;
    }

    public void setDijkstra(boolean dijkstra) {
        this.dijkstra = dijkstra;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void save() throws IOException {
        if (!Paths.get(System.getProperty("user.dir"), "config.dat").toFile().exists()) Files.createFile(Paths.get(System.getProperty("user.dir"), "config.dat"));
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(Paths.get(System.getProperty("user.dir"), "config.dat").toFile()));
        out.writeObject(this);
        out.close();
    }

    public void load() throws IOException, ClassNotFoundException {
        if (Paths.get(System.getProperty("user.dir"), "config.dat").toFile().exists()) {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(Paths.get(System.getProperty("user.dir"), "config.dat").toFile()));
            Config aux = (Config) in.readObject();
            this.dijkstra = aux.isDijkstra();
            this.file = aux.getFile();
            this.max = aux.getMax();
            in.close();
        }
    }
}
