package app.services;

import app.config.ThymeleafConfig;
import app.entities.Customer;
import app.entities.Inquiry;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import java.io.ByteArrayOutputStream;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.Thymeleaf;
import org.thymeleaf.context.Context;

public class PdfService {

    public byte[] generateInquiryPdf (Customer customer, Inquiry inquiry){
        // Context svarer til Map<>, det er en Thymeleaf-klasse
        //  Den pakker de data, der skal stå i pdf, fx kundeoplysninger
        Context thymeleafVariables =  new Context();
        thymeleafVariables.setVariable("customer", customer);
        thymeleafVariables.setVariable("inquiry", inquiry);

        //Motor fra ThymeleafConfig
        TemplateEngine templateEngine = ThymeleafConfig.templateEngine();


        //Skabelon laves til String
        String htmlContent = templateEngine.process("inquiry_to_pdf", thymeleafVariables);

        //html-Strengen laves til byte-array
        return generatePdfFromHtml(htmlContent);

    }

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