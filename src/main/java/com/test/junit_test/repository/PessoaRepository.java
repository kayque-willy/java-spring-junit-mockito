package com.test.junit_test.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.test.junit_test.model.Pessoa;

@Repository
public class PessoaRepository {

  private List<Pessoa> listaPessoas;

  public PessoaRepository(){
    this.listaPessoas = new ArrayList<Pessoa>(Arrays.asList(
      new Pessoa("Angelica", "12358569852", "Desenvolvedora", 30, "Sao Paulo", "Rua das Cruzes", 54),
      new Pessoa("Maria", "54865897584", "Product Owner", 25, "Rio de Janeiro", "Rua dos Carneiros", 29),
      new Pessoa("Pedro", "54887587458", "Segurança", 58, "Porto Alegre", "Rua dos Andradas", 94),
      new Pessoa("Felipe", "21456358754", "Advogado", 42, "Maceió", "Rua Central", 7859),
      new Pessoa("João", "54865932101", "Contador", 33, "São Paulo", "Rua das Hortências", 5587),
      new Pessoa("Elder", "54802365741", "Servidor Público", 58, "Porto Alegre", "Avenida Mauá", 52)));
  }

  public void addPessoa(Pessoa pessoa){
    this.listaPessoas.add(pessoa);
  }

  public void removePessoa(Pessoa pessoa){
    this.listaPessoas.remove(pessoa);
  }

  public List<Pessoa> findPessoa(Pessoa pessoaBusca) throws RuntimeException {
    List<Pessoa> pessoaBuscaResultado = this.listaPessoas.stream()
        .filter(Objects::nonNull)
        .filter(pessoa -> pessoa.getCpf().equals(pessoaBusca.getCpf()))
        .filter(pessoa -> pessoa.getNome().equals(pessoaBusca.getNome()))
        .filter(pessoa -> pessoa.getProfissao().equals(pessoaBusca.getProfissao()))
        .filter(pessoa -> pessoa.getIdade().equals(pessoaBusca.getIdade()))
        .filter(pessoa -> pessoa.getCidade().equals(pessoaBusca.getCidade()))
        .filter(pessoa -> pessoa.getRua().equals(pessoaBusca.getRua()))
        .filter(pessoa -> pessoa.getNumero().equals(pessoaBusca.getNumero()))
        .collect(Collectors.toList());

    if (pessoaBuscaResultado.size() == 0) {
      throw new RuntimeException("Erro ao buscar a pessoa!");
    }

    return pessoaBuscaResultado;
  }

  public List<Pessoa> findPessoaPorCpf(String cpf) throws RuntimeException {
    List<Pessoa> pessoaBusca = this.listaPessoas.stream()
        .filter(Objects::nonNull)
        .filter(pessoa -> pessoa.getCpf().equals(cpf))
        .collect(Collectors.toList());

    if (pessoaBusca.size() == 0) {
      throw new RuntimeException("Erro ao buscar a pessoa pelo CPF!");
    }

    return pessoaBusca;
  }

  public List<Pessoa> listarPessoas() {
    return this.listaPessoas;
  }
  
}
