package com.test.junit_test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.test.junit_test.exception.BusinessException;
import com.test.junit_test.model.Pessoa;
import com.test.junit_test.service.PessoaService;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(value = "/pessoa/", produces = { "application/json" })
public class PessoaController {

  @Autowired
  private PessoaService pessoaService;

  @PostMapping("adicionar-pessoa")
  public ResponseEntity<Pessoa> adicionarPessoa(@RequestBody Pessoa pessoa) {
    try{
      return ResponseEntity.ok(this.pessoaService.adicionarPessoa(pessoa));
    }catch(Exception e){
      throw new BusinessException("Erro ao adicionar pessoa", e);
    }
  }

  @GetMapping("buscar-por-cpf")
  public ResponseEntity<List<Pessoa>> buscarPessoaPorCPF(@RequestParam("cpf") String cpf) {
    try{
      return ResponseEntity.ok(this.pessoaService.buscaPessoasPorCpf(cpf));
    }catch(Exception e){
      throw new BusinessException("CPF é obrigatório", e);
    }
  }


  @GetMapping("listar")
  public ResponseEntity<List<Pessoa>> listarPessoas() {
    try{
      return ResponseEntity.ok(this.pessoaService.listarPessoas());
    }catch(Exception e){
      throw new BusinessException("CPF é obrigatório", e);
    }
  }


  @PostMapping(value = "upload-documento", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<String> uploadDocument(@RequestPart MultipartFile file) {
    try{
      return this.pessoaService.uploadDocument(file);
    }catch(Exception e){
      throw new BusinessException("Erro ao fazer upload do documento", e);
    }
  }

}
