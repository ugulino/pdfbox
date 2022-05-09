package org.ugulino.pdf;

import java.io.*;
import java.util.List;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

public class PdfTerm {

    private static final String IN_PDF_PATH = "src/main/resources/laudo.pdf";
    private static final String IN_PDF_PATH2 = "src/main/resources/laudoForm.pdf";
    private static final String IN_PDF_FORM_PATH = "src/main/resources/termoavaliacaoveicular.pdf";

    private static void addBlankLine(PDPageContentStream content, int lines) throws IOException {
        for (int i = 0; i < lines; i++) content.newLine();
    }

    private static PDPage addPage(PDDocument document) {
        PDPage page = new PDPage();
        document.addPage(page);
        return page;
    }

    private static void setDocumentInformation(PDDocument document, String title, String author) {
        PDDocumentInformation docInformation = new PDDocumentInformation();
        docInformation.setTitle(title);
        docInformation.setAuthor(author);
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        docInformation.setCreationDate(calendar);

        document.setDocumentInformation(docInformation);
    }

    private static void addContentDocument(PDDocument document, PDPage page, Term term) throws IOException  {
        try (PDPageContentStream content = new PDPageContentStream(document, page)) {
            PDType1Font pdfFont = PDType1Font.COURIER;
            content.beginText();
            content.setLeading(14.5f);

            content.newLineAtOffset(25, 700);
            content.setFont(pdfFont, 28);
            content.setLeading(14.5f);
            String title = "Termo de Avaliação de Veículo";
            content.showText(title);

            content.setFont(pdfFont, 12);
            addBlankLine(content, 2);
            String chassi = "Número Chassi:  ";
            content.showText(chassi.concat(term.getNumeroChassi()));

            addBlankLine(content, 1);
            String proposta = "Nº  Proposta: ".concat(term.getNumeroProposta().toString());
            content.showText(proposta);

            addBlankLine(content, 1);
            String placa = "Placa:  ".concat(term.getPlaca());
            content.showText(placa);

            addBlankLine(content, 1);
            String anoModelo = "Ano Modelo:  ".concat(term.getAnoModelo().toString());
            content.showText(anoModelo);

            addBlankLine(content, 1);
            String modelo = "Modelo:  ".concat(term.getModelo());
            content.showText(modelo);

            content.endText();

            setDocumentInformation(document,"Laudo Veicular", "Itaú Unibanco");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createTermPDF(List<Term> terms) {
        try {
            PDDocument pdfDocument = PDDocument.load(new File(IN_PDF_FORM_PATH));
            PDDocumentCatalog docCatalog = pdfDocument.getDocumentCatalog();
            PDAcroForm acroForm = docCatalog.getAcroForm();

            if (acroForm != null)
            {
                PDField nomeCliente = (PDField) acroForm.getField( "NomeCliente" );
                nomeCliente.setValue("Fabiano Ugulino");

                PDField email = (PDField) acroForm.getField( "Email" );
                email.setValue("fabiano@email.com.br");

                PDField cpf = (PDField) acroForm.getField( "Cpf" );
                cpf.setValue("1234567890");

                PDField telefone = (PDField) acroForm.getField( "Telefone" );
                telefone.setValue("84991015656");

                PDField chassi = (PDField) acroForm.getField( "Chassi" );
                chassi.setValue("125SD656EER95WW1VSUI1");

                PDField cor = (PDField) acroForm.getField( "Cor" );
                chassi.setValue("Branco");
            }
            acroForm.flatten();

            pdfDocument.save(IN_PDF_PATH2);
            pdfDocument.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        try (PDDocument document = new PDDocument()) {
            for (Term term : terms) {
                PDPage page = addPage(document);
                addContentDocument(document, page, term);
            }
            document.save(IN_PDF_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
