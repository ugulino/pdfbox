package org.ugulino.pdf;

import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class PdfTermForm {

    private static final String  SOURCE_PATH = "src/main/resources/Termo.pdf";
    private static final String FILE_IMAGE_PATH = "src/main/resources/Hb20s.png";

    public void createTermPDF(Term term) {
        try {
            PDDocument pdfDocument = PDDocument.load(new File(SOURCE_PATH));
            PDDocumentCatalog docCatalog = pdfDocument.getDocumentCatalog();
            PDAcroForm acroForm = docCatalog.getAcroForm();

            if (acroForm != null)
            {
                PDField customerName = (PDField) acroForm.getField( "NomeCliente" );
                customerName.setValue("Fabiano Ugulino");

                PDField email = (PDField) acroForm.getField( "Email" );
                email.setValue("fabiano@email.com.br");

                PDField cpf = (PDField) acroForm.getField( "Cpf" );
                cpf.setValue("1234567890");

                PDField phone = (PDField) acroForm.getField( "Telefone" );
                phone.setValue("84991015656");

                PDField chassi = (PDField) acroForm.getField( "Chassi" );
                chassi.setValue("125SD656EER95WW1VSUI1");

                PDField color = (PDField) acroForm.getField( "Cor" );
                color.setValue("Branco");

                PDField model = (PDField) acroForm.getField( "Modelo" );
                model.setValue("HB20s");

                PDField brand = (PDField) acroForm.getField( "Marca" );
                brand.setValue("Hyundai");

                PDField yearManufacture = (PDField) acroForm.getField( "AnoFabricacao" );
                yearManufacture.setValue("2021");

                PDField modelYear = (PDField) acroForm.getField( "AnoModelo" );
                modelYear.setValue("2022");

                PDField plate = (PDField) acroForm.getField( "Placa" );
                plate.setValue("ABC-1234");

                PDField fuelOption = (PDField) acroForm.getField( "Combustivel" );
                fuelOption.setValue("Gasolina/Álcool");

                PDImageXObject image = PDImageXObject.createFromFile(FILE_IMAGE_PATH, pdfDocument);
                drawImageField(pdfDocument, "imagem", image);
            }
            acroForm.flatten();

            ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
            pdfDocument.save(pdfStream);
            pdfDocument.close();

            String base64String = Base64.getEncoder().encodeToString(pdfStream.toByteArray());
            System.out.println(base64String);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawImageField(PDDocument document, String name, PDImageXObject image)
            throws IOException {

        PDAcroForm acroForm = document.getDocumentCatalog().getAcroForm();
        PDField field = acroForm.getField(name);
        if (field != null) {
            PDRectangle rectangle = getImageFieldArea(field);
            float size = rectangle.getHeight();
            float x = rectangle.getLowerLeftX();
            float y = rectangle.getLowerLeftY();

            try (PDPageContentStream contentStream = new PDPageContentStream(document,
                    document.getPage(0), PDPageContentStream.AppendMode.APPEND, true)) {
                contentStream.drawImage(image, x, y, size, size);
            }
        }
    }

    private PDRectangle getImageFieldArea(PDField field) {
        COSDictionary fieldDict = field.getCOSObject();
        COSArray fieldAreaArray = (COSArray) fieldDict.getDictionaryObject(COSName.RECT);
        return new PDRectangle(fieldAreaArray);
    }
}
