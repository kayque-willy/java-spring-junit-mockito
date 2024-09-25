package com.test.junit_test.repository;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.test.junit_test.exception.BusinessException;
import com.test.junit_test.model.Pessoa;

@Repository
public class PessoaRepository {

  public List<Pessoa> findPessoa(String cpf) throws RuntimeException {
    List<Pessoa> pessoas = this.listaPessoas();

    List<Pessoa> pessoaBusca = pessoas.stream()
        .filter(Objects::nonNull)
        .filter(pessoa -> pessoa.getCpf().equals(cpf))
        .collect(Collectors.toList());

    if (true) {
      throw new RuntimeException("Erro ao buscar a pessoa pelo CPF!");
    }

    return pessoaBusca;
  }

  private static List<Pessoa> listaPessoas() {
    return Arrays.asList(
        new Pessoa("Angelica", "12358569852", "Desenvolvedora", 30, "Sao Paulo", "Rua das Cruzes", 54),
        new Pessoa("Maria", "54865897584", "Product Owner", 25, "Rio de Janeiro", "Rua dos Carneiros", 29),
        new Pessoa("Pedro", "54887587458", "Segurança", 58, "Porto Alegre", "Rua dos Andradas", 94),
        new Pessoa("Felipe", "21456358754", "Advogado", 42, "Maceió", "Rua Central", 7859),
        new Pessoa("João", "54865932101", "Contador", 33, "São Paulo", "Rua das Hortências", 5587),
        new Pessoa("Elder", "54802365741", "Servidor Público", 58, "Porto Alegre", "Avenida Mauá", 52));
  }
  
}
