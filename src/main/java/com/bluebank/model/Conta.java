package com.bluebank.model;


import javax.persistence.*;


@Entity
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idConta;
    private int conta;
    private int agencia;
    private double saldo;
    private double limiteCredito;
    private String tipoConta;
    
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    public Conta(){

    }

    public Conta(int idConta, int conta, int agencia, double saldo, double limiteCredito, String tipoConta, Cliente cliente) {
        this.idConta = idConta;
        this.conta = conta;
        this.agencia = agencia;
        this.saldo = saldo;
        this.limiteCredito = limiteCredito;
        this.tipoConta = tipoConta;
        this.cliente = cliente;
    }

    public int getIdConta() {
        return idConta;
    }

    public void setIdConta(int idConta) {
        this.idConta = idConta;
    }

    public int getConta() {
        return conta;
    }

    public void setConta(int conta) {
        this.conta = conta;
    }

    public int getAgencia() {
        return agencia;
    }

    public void setAgencia(int agencia) {
        this.agencia = agencia;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double getLimiteCredito() {
        return limiteCredito;
    }

    public void setLimiteCredito(double limiteCredito) {
        this.limiteCredito = limiteCredito;
    }

    public String getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(String tipoConta) {
        this.tipoConta = tipoConta;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
