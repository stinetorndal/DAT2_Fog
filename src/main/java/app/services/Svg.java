package app.services;

public class Svg {

    // Er static, fordi der kun må findes én.
    // Starttekst til svg-tegning.
    private static final String SVG_TEMPLATE = "<svg version=\"1.1\" x=\"%d\" y=\"%d\" viewBox=\"%s\" width=\"%s\"" +
            " preserveAspectRatio=\"xMinYMin\">";

    // Starttekst til pilehoveder (skal aldrig ændres).
    private static final String SVG_ARROW_DEFS = "<defs>\n <marker\n id=\"beginArrow\"\n markerWidth=\"12\"\n " +
            "markerHeight=\"12\"\n refX=\"0\"\n refY=\"6\"\n orient=\"auto\">\n <path d=\"M0,6 L12,0 L12,12 L0,6\" " +
            "style=\"fill: #000000;\" />\n </marker>\n <marker\n id=\"endArrow\"\n markerWidth=\"12\"\n markerHeight=\"12\"\n " +
            "refX=\"12\"\n refY=\"6\"\n orient=\"auto\">\n <path d=\"M0,0 L12,6 L0,12 L0,0 \" style=\"fill: #000000;\" />\n " +
            "</marker>\n </defs>";

    private static final String SVG_RECT_TEMPLATE = "<rect x=\"%d\" y=\"%d\" height=\"%f\" width=\"%f\" style=\"%s\"/>";
    private static final String SVG_LINE_TEMPLATE = "<line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\"\n style=\"%s\"/>";
    private static final String SVG_TEXT_TEMPLATE = "<text style=\"text-anchor: middle\" transform=\"translate(%d,%d) " +
            "rotate(%d)\">%s</text>";

    private StringBuilder svg = new StringBuilder();

    public Svg(int x, int y, String viewBox, String width) {
        svg.append(String.format(SVG_TEMPLATE, x, y, viewBox, width));
        // Sætter starttekst til pilehoveder ind i svg'en, så der er gjort klar til eventuelle pile.
        svg.append(SVG_ARROW_DEFS);
    }

    public void addRectangle(int x, int y, double length, double width) {
        svg.append(String.format(SVG_RECT_TEMPLATE, x, y, length, width, "stroke: #000000; fill: none;"));
    }

    public void addLine(int x1, int y1, int x2, int y2, String style) {
        svg.append(String.format(SVG_LINE_TEMPLATE, x1, y1, x2, y2, style));
    }

    //TODO Kan det være, at style ikke skal med som parameter her?
    public void addArrow(int x1, int y1, int x2, int y2) {
        svg.append(String.format(SVG_LINE_TEMPLATE, x1, y1, x2, y2, "stroke: #006600;\n " +
                "marker-start: url(#beginArrow);\n marker-end: url(#endArrow);"));
    }

    public void addText(int x, int y, int rotation, String text) {
        svg.append(String.format(SVG_TEXT_TEMPLATE, x, y, rotation, text));
    }

    // Sætter en indre svg ind i en ydre svg.
    public void addSvg(Svg innerSvg) {
        svg.append(innerSvg.toString());
    }

    @Override
    public String toString() {
        return svg.append("</svg>").toString();
    }
}
