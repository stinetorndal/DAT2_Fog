package services;

import app.services.PdfService;
import io.javalin.http.SinglePageHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PdfServiceTest {

    @Test
    void testPdfGeneratesFromHtml (){
        //Arrange
        PdfService pdfService = new PdfService();
        //Test-html for at se om der kommer noget tekst på pdf fra html
        String mockHtml = "<html><body><h1>Fog Carport</h1><p>Width: 400cm</p></body></html>";
        //Act
        byte[] pdfBytes = pdfService.generatePdfFromHtml(mockHtml);

        //Assert
        //Check om der kommer data retur
        assertNotNull(pdfBytes);
        assertTrue(pdfBytes.length > 0);

        //Teknisk check, en pdf-fil starter altid med %PDF = 4 karakterer
        String pdfHeader = new String (pdfBytes,0,4);
        assertEquals("%PDF", pdfHeader);
        System.out.println("PDF kompileret korrekt. Filstørrelse= " + pdfBytes.length + " bytes");

    }
}
