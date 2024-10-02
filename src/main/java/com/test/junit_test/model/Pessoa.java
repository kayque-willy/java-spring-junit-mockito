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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((nome == null) ? 0 : nome.hashCode());
    result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
    result = prime * result + ((profissao == null) ? 0 : profissao.hashCode());
    result = prime * result + ((idade == null) ? 0 : idade.hashCode());
    result = prime * result + ((cidade == null) ? 0 : cidade.hashCode());
    result = prime * result + ((rua == null) ? 0 : rua.hashCode());
    result = prime * result + ((numero == null) ? 0 : numero.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Pessoa other = (Pessoa) obj;
    if (nome == null) {
      if (other.nome != null)
        return false;
    } else if (!nome.equals(other.nome))
      return false;
    if (cpf == null) {
      if (other.cpf != null)
        return false;
    } else if (!cpf.equals(other.cpf))
      return false;
    if (profissao == null) {
      if (other.profissao != null)
        return false;
    } else if (!profissao.equals(other.profissao))
      return false;
    if (idade == null) {
      if (other.idade != null)
        return false;
    } else if (!idade.equals(other.idade))
      return false;
    if (cidade == null) {
      if (other.cidade != null)
        return false;
    } else if (!cidade.equals(other.cidade))
      return false;
    if (rua == null) {
      if (other.rua != null)
        return false;
    } else if (!rua.equals(other.rua))
      return false;
    if (numero == null) {
      if (other.numero != null)
        return false;
    } else if (!numero.equals(other.numero))
      return false;
    return true;
  }

}
