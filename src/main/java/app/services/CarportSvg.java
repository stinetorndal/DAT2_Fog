package app.services;

import app.entities.Material;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.services.Calculator;

import java.util.List;

public class CarportSvg {


    private int length;
    private int width;
    private Svg carportSvg;
    private Calculator calculator = new Calculator(length, width);

    public CarportSvg(int length, int width, ConnectionPool connectionPool) throws DatabaseException {
        this.length = length;
        this.width = width;
        this.carportSvg = new Svg(0, 0, "0 0 " + length + " "+ width, "50%");
        addPosts(connectionPool);
        addBeams(connectionPool);
        addRafters(connectionPool);
    }

    // Tilføjer stolper.
    private void addPosts(ConnectionPool connectionPool) throws DatabaseException {
        List<Material> posts = calculator.calculatePosts(length, connectionPool);

        int postLength = 10;
        int postWidth = 10;

        // Yderste sæt stolper i højre side.
        carportSvg.addRectangle(length - 20, 20, postLength, postWidth);
        carportSvg.addRectangle(length - 20, width - postWidth - 20, postLength, postWidth);

        // Yderste sæt stolper i venstre side.
        carportSvg.addRectangle(100, 20, postLength, postWidth);
        carportSvg.addRectangle(100, width - postWidth - 20, postLength, postWidth);

        int outerPosts = 4;
        while (outerPosts > 0) {
            posts.removeFirst();
            outerPosts --;
        }

        int remainingPostsPerSide = posts.size() / 2;

        int space = (length - 120) / (remainingPostsPerSide + 1);

        while (remainingPostsPerSide > 0) {
            carportSvg.addRectangle(100 + space, 20, postLength, postWidth);
            carportSvg.addRectangle(100 + space, width -postWidth - 20, postLength, postWidth);
            space += space;
            remainingPostsPerSide --;
        }
    }

    // Tilføjer remme.
    private void addBeams(ConnectionPool connectionPool) throws DatabaseException {
        int beamWidth = 5;

        carportSvg.addRectangle(0, 20, beamWidth, length);
        carportSvg.addRectangle(0, width - 20 - beamWidth, beamWidth, length);
    }

    // Tilføjer spær.
    private void addRafters(ConnectionPool connectionPool) throws DatabaseException {
        List<Material> rafts = calculator.calculateRafts(length, width, connectionPool);

        int xPosition = 0;
        int raftWidth = 5;

        if(width > 300) {
            int raftLength = width * 2;

            int remainingRafts = rafts.size() / 2;
            int spacing = (length - raftWidth) / (remainingRafts - 1);

            while (remainingRafts > 0) {
                carportSvg.addRectangle(xPosition, 0, raftLength, raftWidth);
                xPosition += spacing;
                remainingRafts--;
            }
        } else {
            int raftLength = width;
            int spacing = (length - raftWidth) / (rafts.size() - 1);
            for (Material raft : rafts) {
                carportSvg.addRectangle(xPosition, 0, raftLength, raftWidth);
                xPosition += spacing;
            }
        }
    }

    @Override
    public String toString() {
        return carportSvg.toString();
    }
}
