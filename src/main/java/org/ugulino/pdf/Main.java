package org.ugulino.pdf;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ugulino.pdf.domain.Customer;
import org.ugulino.pdf.domain.Term;
import org.ugulino.pdf.interfaces.PdfService;
import org.ugulino.pdf.service.PdfTermService;

public class Main {
    public static void main(String[] args) {

        try {
            Customer customer = new Customer();
            customer.setName("Fabiano Ugulino");
            customer.setCpf("01334657422");
            customer.setPhone("84991015656");
            customer.setEmail("fabiano@email.com");

            Term termHb20s = new Term();
            termHb20s.setCustomer(customer);
            termHb20s.setProposalNumber(4585565);
            termHb20s.setChassiNumber("12312QWQ991231A");
            termHb20s.setPlate("1231SFD");
            termHb20s.setModel("HB20s");
            termHb20s.setBrand("HYUNDAI");
            termHb20s.setYearManufacture(2021);
            termHb20s.setModelYear(2022);
            termHb20s.setColor("Preto");
            termHb20s.setFuelOption("Flex");

            PdfService pdf = new PdfTermService();

            ObjectMapper mapper = new ObjectMapper();
            String termJson = mapper.writeValueAsString(termHb20s);

            String base64String = pdf.getPdfToBase64(termJson);
            System.out.println(base64String);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
