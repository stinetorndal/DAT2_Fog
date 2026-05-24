package app.util;

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


    public void calculateCarport(Inquiry inquiry, ConnectionPool connectionPool) throws DatabaseException {
        List<Material> bom = new ArrayList<>();

        // Tilføjer stolpe-objekter til stykliste.
        bom.addAll(calculatePosts(inquiry, connectionPool));

        // Tilføjer rem-objekter til stykliste.
        bom.addAll(calculateRem(inquiry, connectionPool));

        // Til
    }

    public List<Material> calculatePosts(Inquiry inquiry, ConnectionPool connectionPool) throws DatabaseException {
        List<Material> carportPosts = new ArrayList<>();
        Material post = materialService.getMaterialsByCategory(MaterialCategory.POST, connectionPool).getFirst();

        double postsOneSide = (inquiry.getCarportLength() - frontudhæng - bagudhæng) / ((max_afstand - min_afstand) + stolpebredde);
        int totalAmountPosts = (int) Math.ceil(postsOneSide) * 2;

        while (totalAmountPosts > 0) {
            carportPosts.add(post);
            totalAmountPosts--;
        }
        return carportPosts;
    }

    public List<Material> calculateRem(Inquiry inquiry, ConnectionPool connectionPool) throws DatabaseException {
        List<Material> carportBeams = new ArrayList<>();

        int carportLength = inquiry.getCarportLength();
        Material longestBeam = getLongestBeam(connectionPool);
        int longestBeamLength = longestBeam.getLength();
        int remainingBeamLength = carportLength - longestBeamLength;

        carportBeams.add(longestBeam);
        carportBeams.add(longestBeam);

        if (remainingBeamLength != 0) {
            for (Material m : materialService.getBeams(connectionPool)) {
                if (m.getLength() >= remainingBeamLength) {
                    carportBeams.add(m);
                    carportBeams.add(m);
                }
            }
            return carportBeams;
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

    public List<Material> calculateRafts(Inquiry inquiry, ConnectionPool connectionPool) throws DatabaseException {
        List<Material> carportRafts = new ArrayList<>();
        Material raft = materialService.getMaterialsByCategory(MaterialCategory.RAFT, connectionPool).getFirst();

        double NumberOfRafts = inquiry.getCarportLength() / raftSpace;
        int totalAmountRafts = (int) Math.ceil(NumberOfRafts);

        int carportWidth = inquiry.getCarportWidth();
        if (carportWidth > 300) {
            totalAmountRafts = totalAmountRafts * 2;
        }

        while (totalAmountRafts > 0) {
            carportRafts.add(raft);
            totalAmountRafts--;
        }

        return carportRafts;
    }

}