package app.services;

import app.entities.Inquiry;
import app.entities.Material;
import app.enums.MaterialCategory;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.services.MaterialService;

import java.util.ArrayList;
import java.util.List;


public class Calculator {

    MaterialService materialService = new MaterialService();
    private double raftSpace = 55;
    private double frontudhæng = 100;
    private double bagudhæng = 20;
    private double stolpebredde = 10;
    private double max_afstand = 300;
    private double min_afstand = 150;

    private int length;
    private int width;

    public Calculator(int length, int width) {
        this.length = length;
        this.width = width;
    }


    public List<Material> calculatePosts(int length, ConnectionPool connectionPool) throws DatabaseException {
        List<Material> carportPosts = new ArrayList<>();
        Material post = materialService.getMaterialsByCategory(MaterialCategory.POST, connectionPool).getFirst();

        double postsOneSide = (length - frontudhæng - bagudhæng) / ((max_afstand - min_afstand) + stolpebredde);
        int totalAmountPosts = (int) Math.ceil(postsOneSide) * 2;

        while (totalAmountPosts > 0) {
            carportPosts.add(post);
            totalAmountPosts--;
        }
        return carportPosts;
    }

    public List<Material> calculateBeams(int length, ConnectionPool connectionPool) throws DatabaseException {
        List<Material> carportBeams = new ArrayList<>();

        Material longestBeam = getLongestBeam(connectionPool);
        int longestBeamLength = longestBeam.getLength();
        int remainingBeamLength = length - longestBeamLength;

        carportBeams.add(longestBeam);
        carportBeams.add(longestBeam);

        if (remainingBeamLength != 0) {
            for (Material m : materialService.getMaterialsByCategory(MaterialCategory.BEAM, connectionPool)) {
                if (m.getLength() >= remainingBeamLength) {
                    carportBeams.add(m);
                    carportBeams.add(m);
                    //Vigtigt da løkken kører videre og bliver ved med at tilføje. Indsat efter test
                    break;
                }
            }
            //kommenteret ud efter at have kørt test. Den tilføjet et ekstra sæt
            //return carportBeams;
        }
        return carportBeams;
    }

    private Material getLongestBeam(ConnectionPool connectionPool) throws DatabaseException {
        Material longestBeam = materialService.getMaterialsByCategory(MaterialCategory.BEAM, connectionPool).getFirst();
        for (Material m : materialService.getMaterialsByCategory(MaterialCategory.BEAM, connectionPool)) {
            if (m.getLength() > longestBeam.getLength()) {
                longestBeam = m;
            }
        }
        return longestBeam; // (longest tree)
    }

    public List<Material> calculateRafts(int length, int width, ConnectionPool connectionPool) throws DatabaseException {
        List<Material> carportRafts = new ArrayList<>();
        Material raft = materialService.getMaterialsByCategory(MaterialCategory.RAFT, connectionPool).getFirst();

        double NumberOfRafts = length / raftSpace;
        int totalAmountRafts = (int) Math.ceil(NumberOfRafts);

        if (width > 300) {
            totalAmountRafts = totalAmountRafts * 2;
        }

        while (totalAmountRafts > 0) {
            carportRafts.add(raft);
            totalAmountRafts--;
        }

        return carportRafts;
    }
}