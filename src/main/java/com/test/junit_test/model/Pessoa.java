package com.test.junit_test.model;

public class Pessoa {

  private String nome;
  private String cpf;
  private String profissao;
  private Integer idade;
  private String cidade;
  private String rua;
  private Integer numero;

  public Pessoa() {}

  public Pessoa(String nome, String cpf, String profissao, Integer idade, String cidade, String rua, Integer numero) {
    this.nome = nome;
    this.cpf = cpf;
    this.profissao = profissao;
    this.idade = idade;
    this.cidade = cidade;
    this.rua = rua;
    this.numero = numero;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getCpf() {
    return cpf;
  }

  public void setCpf(String cpf) {
    this.cpf = cpf;
  }

  public String getProfissao() {
    return profissao;
  }

  public void setProfissao(String profissao) {
    this.profissao = profissao;
  }

  public Integer getIdade() {
    return idade;
  }

  public void setIdade(Integer idade) {
    this.idade = idade;
  }

  public String getCidade() {
    return cidade;
  }

  public void setCidade(String cidade) {
    this.cidade = cidade;
  }

  public String getRua() {
    return rua;
  }

  public void setRua(String rua) {
    this.rua = rua;
  }

  public Integer getNumero() {
    return numero;
  }

  public void setNumero(Integer numero) {
    this.numero = numero;
  }

  
  



}
