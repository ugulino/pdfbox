package org.ugulino.pdf.service;

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
import org.json.JSONObject;
import org.ugulino.pdf.interfaces.PdfService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

import static org.ugulino.pdf.constants.Constants.FILE_IMAGE_PATH;
import static org.ugulino.pdf.constants.Constants.SOURCE_PATH;

public class PdfTermService implements PdfService {

    public String getPdfToBase64(String data) {
        String base64String = "";

        JSONObject json = new JSONObject(data);

        try {
            PDDocument pdfDocument = PDDocument.load(new File(SOURCE_PATH));
            PDDocumentCatalog docCatalog = pdfDocument.getDocumentCatalog();
            PDAcroForm acroForm = docCatalog.getAcroForm();

            if (acroForm != null)
            {
                PDField customerName = (PDField) acroForm.getField( "NomeCliente" );
                customerName.setValue(json.getJSONObject("customer").get("name").toString());

                PDField email = (PDField) acroForm.getField( "Email" );
                email.setValue(json.getJSONObject("customer").get("email").toString());

                PDField cpf = (PDField) acroForm.getField( "Cpf" );
                cpf.setValue(json.getJSONObject("customer").get("cpf").toString());

                PDField phone = (PDField) acroForm.getField( "Telefone" );
                phone.setValue(json.getJSONObject("customer").get("phone").toString());

                PDField chassi = (PDField) acroForm.getField( "Chassi" );
                chassi.setValue(json.get("chassiNumber").toString());

                PDField color = (PDField) acroForm.getField( "Cor" );
                color.setValue(json.get("color").toString());

                PDField model = (PDField) acroForm.getField( "Modelo" );
                model.setValue(json.get("model").toString());

                PDField brand = (PDField) acroForm.getField( "Marca" );
                brand.setValue(json.get("brand").toString());

                PDField yearManufacture = (PDField) acroForm.getField( "AnoFabricacao" );
                yearManufacture.setValue(json.get("yearManufacture").toString());

                PDField modelYear = (PDField) acroForm.getField( "AnoModelo" );
                modelYear.setValue(json.get("modelYear").toString());

                PDField plate = (PDField) acroForm.getField( "Placa" );
                plate.setValue(json.get("plate").toString());

                PDField fuelOption = (PDField) acroForm.getField( "Combustivel" );
                fuelOption.setValue(json.get("fuelOption").toString());

                PDImageXObject image = PDImageXObject.createFromFile(FILE_IMAGE_PATH, pdfDocument);
                drawImageField(pdfDocument, "imagem", image);
            }
            acroForm.flatten();

            ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
            pdfDocument.save(pdfStream);
            pdfDocument.close();

            base64String = Base64.getEncoder().encodeToString(pdfStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64String;
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
