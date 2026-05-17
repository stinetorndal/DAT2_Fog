package app.services;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import java.io.ByteArrayOutputStream;

public class PdfService {

    // Konverterer html til pdf byte array
    public byte[] generatePdfFromHtml (String htmlContent) {
        ByteArrayOutputStream output =  new ByteArrayOutputStream();

        try {
            PdfRendererBuilder pdfRendererBuilder = new PdfRendererBuilder();
            // læg HTML ind i pdf
            pdfRendererBuilder.withHtmlContent(htmlContent, null);
            //læg pdf ind i byte array
            pdfRendererBuilder.toStream(output);
            //Kør
            pdfRendererBuilder.run();
        } catch (Exception e){
            System.err.println("Fejl da HTML skulle konverteres til PDF" + e.getMessage());
        }
        // returnerer rå-byte klar til vedhæftning
        return output.toByteArray();
    }


}