package org.ugulino.pdf;

import java.util.ArrayList;

import org.ugulino.pdf.PdfTermForm;


public class Main {
    public static void main(String[] args) {

        try {
            Customer customer = new Customer();
            /*customer.name = "Fabiano Ugulino";
            customer.cpf = "01334657422";
            customer.phone = "84994035613";
            customer.email = "fabiano@email.com";*/

            Term termHb20 = new Term(
                    customer,
                    4585565,
                    "12312QWQ991231A",
                    "1231SFD",
                    "HB20s",
                    "HYUNDAI",
                    2021,
                    2022);

            Term termHb20s = new Term(
                     customer,
                    1332256,
                    "123126WQ491131B",
                    "2331GTD",
                    "HB20",
                    "HYUNDAI",
                    2020,
                    2021);

            ArrayList<Term> terms = new ArrayList<Term>();
            terms.add(termHb20);
            terms.add(termHb20s);

            PdfTermForm pdf = new PdfTermForm();
            pdf.createTermPDF(termHb20s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
