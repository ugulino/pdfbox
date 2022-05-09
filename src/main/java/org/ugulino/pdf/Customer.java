package org.ugulino.pdf;

import lombok.Data;

@Data
public class Customer {
    private String name;
    private String email;
    private String cpf;
    private String phone;

    public Customer(){
    }
}
