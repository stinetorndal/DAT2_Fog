package app.services;

public class CarportSvg {

    private int length;
    private int width;
    private Svg carportSvg;

    public CarportSvg(int length, int width) {
        this.length = length;
        this.width = width;
        //TODO måske skal carportSvg bruge variabler? Lige nu er viewbox jo f.eks. sat til én carport-størrelse.
        this.carportSvg = new Svg(0, 0, "0 0 855 690", "100%");
        addPosts();
        addBeams();
        addRafters();

    }

    // Tilføjer stolper.
    private void addPosts() {

    }
    // Tilføjer remme.
    private void addBeams() {

    }

    // Tilføjer spær.
    private void addRafters() {
        // Her skal der være et for-loop, som tilføjer spær.
        // Metoden skal vide hvor mange spær, der skal være.
        // Skal måske have int quantity som parameter?
        // Et eller andet sted skal der være en udregning, som beregner afstanden mellem spærene.
    }

    @Override
    public String toString() {
        return carportSvg.toString();
    }
}
