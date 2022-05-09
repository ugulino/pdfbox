package org.ugulino.pdf;

public class Term {
    private Integer numeroProposta;
    private String numeroChassi;
    private String placa;
    private String modelo;
    private String fabricante;
    private Integer anoFabricacao;
    private Integer anoModelo;
    private Customer customer;

    public Term(Customer customer, Integer numeroProposta, String numeroChassi, String placa, String modelo, String fabricante, Integer anoFabricacao, Integer anoModelo) {
        this.customer = customer;
        this.numeroProposta = numeroProposta;
        this.numeroChassi = numeroChassi;
        this.placa =  placa;
        this.modelo = modelo;
        this.fabricante = fabricante;
        this.anoFabricacao = anoFabricacao;
        this.anoModelo = anoModelo;
    }

    public Integer getNumeroProposta() {
        return numeroProposta;
    }

    public void setNumeroProposta(Integer numeroProposta) {
        this.numeroProposta = numeroProposta;
    }

    public String getNumeroChassi() {
        return numeroChassi;
    }

    public void setNumeroChassi(String numeroChassi) {
        this.numeroChassi = numeroChassi;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public Integer getAnoFabricacao() {
        return anoFabricacao;
    }

    public void setAnoFabricacao(Integer anoFabricacao) {
        this.anoFabricacao = anoFabricacao;
    }

    public Integer getAnoModelo() {
        return anoModelo;
    }

    public void setAnoModelo(Integer anoModelo) {
        this.anoModelo = anoModelo;
    }
}
