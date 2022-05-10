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
                setPdfFormValues(json, acroForm);
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

    private void setPdfFormValues(JSONObject json, PDAcroForm acroForm)  {
        json.keys().forEachRemaining(j ->
        {
            if (json.get(j).getClass().getTypeName().contains("JSONObject")) {
                setPdfFormValues((JSONObject) json.get(j), acroForm);
            } else {
                System.out.println(json.get(j));
                PDField field = (PDField) acroForm.getField(j);
                if  (field != null) try {
                    field.setValue(json.get(j).toString());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
