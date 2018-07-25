package com.controle.vpstec.controle.control;

public class Produto {
    private String descricao;
    private int cod;
    private double valor;
    private int quantidade;
    private double custo;

    public Produto(){

    }
    public Produto(String descricao, int cod, double valor, int quantidade, double custo) {
        this.descricao = descricao;
        this.cod = cod;
        this.valor = valor;
        this.quantidade = quantidade;
        this.custo = custo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getCusto() {
        return custo;
    }

    public void setCusto(double custo) {
        this.custo = custo;
    }
}
