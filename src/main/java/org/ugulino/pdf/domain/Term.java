package org.ugulino.pdf.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Term {
    @JsonProperty("proposalNumber")
    private Integer proposalNumber;

    @JsonProperty("chassiNumber")
    private String chassiNumber;

    @JsonProperty("plate")
    private String plate;

    @JsonProperty("model")
    private String model;

    @JsonProperty("brand")
    private String brand;

    @JsonProperty("yearManufacture")
    private Integer yearManufacture;

    @JsonProperty("modelYear")
    private Integer modelYear;

    @JsonProperty("customer")
    private Customer customer;

    @JsonProperty("color")
    private String color;

    @JsonProperty("fuelOption")
    private String fuelOption;

    public Term() {
    }
}