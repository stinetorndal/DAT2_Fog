package app.util;

import app.entities.Inquiry;

public class Calculator {

    //Hent kundes valgte mål-objekt
    //Hent liste med carport-længder
    //Hent liste med carport-bredder
    //Hent liste med træ-længder - skal først bruges til remme (til remme og spær)
    private double carportL1 = 540;
    private double carportL2 = 600;
    private double carportL3 = 720;
    private double carportB1 = 300;
    private double carportB2 = 600;

    private double spærlængde1 = 540;
    private double spærlængde2 = 360;
    private double spærlængde3 = 300;

    private double frontudhæng = 100;
    private double bagudhæng = 20;
    private double stolpebredde = 10;
    private double max_afstand = 300;
    private double min_afstand = 150;

    //Vi vælger til beregning: Længde 720, bredde 300 til test

    public int calculatePosts(Inquiry inquiry) {
        double postsOneSide = (inquiry.getCarportLength() - frontudhæng - bagudhæng) / ((max_afstand - min_afstand) + stolpebredde);

        return (int) Math.ceil(postsOneSide) * 2;
    }

    public void calculateRem(Inquiry inquiry) {
        //totallængde - vælg altid længste først
        //Hvad er der tilbage totallængde - længste rem (540)
        //Match med passende længde i rem-varianterne

        //int carportLength = inquiry.getCarportLength();
        //int totalTræ = Tag først den længste fra ArrayList.
        //if (carportLength > totalTræ) {
        //Så træk dem fra hinanden og gør noget mere
         //else : (så er længden på rem og carport den samme)


        //int carportLength = inquiry.getCarportLength();
        //int længsteTræ = Tag først den længste fra ArrayList.
        //int rest = carportLength - længsteTræ;
        //int remEnSide = længsteTræ;
        //if (rest != 0) {
        //for (Material m : listeMedLængder) {
        //     if (m.getLength() >= rest) {
        //          remEnSide += m;
        //return remEnside;
        //else return remEnSide; TODO VI SKAL HUSKE AT GANGE MED TO!!! OGSÅ OVENOVER!!
        //TODO den skal vist returnerer en liste af materiale-objekter.


        //etStykkeTræ = listeMedLængder.getFirst();
        //310
        // [200,250,300,350,400]
        //if (etStykkeTræ < rest) {
        //kør videre
        //else if (etStykkeTræ > rest): tag det næste tal der er =>
        //else det er = en længde: STOP
        //for (længder l : listeMedLængder) {
        //if (l >= rest

        //Hvis rest ikke er lig med nul, tag rest, loop igennem liste (som er sorteret), hvis træ er lig med eller længere end rest, læg træ til variabel der holder på rem til én side

    }

}
