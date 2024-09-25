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

import com.test.junit_test.model.Pessoa;
import com.test.junit_test.service.PessoaService;

@RestController
@RequestMapping(value = "/pessoa/", produces = { "application/json" })
public class PessoaController {

  @Autowired
  private PessoaService pessoaService;

  @GetMapping("buscar-por-cpf")
  public ResponseEntity<List<Pessoa>> buscaDadosProfissionais(@RequestParam("cpf") String cpf) throws Exception {
    return ResponseEntity.ok(this.pessoaService.buscaPessoasPorCpf(cpf));
  }

  @PostMapping(value = "upload-documento", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<String> uploadDocuments(@RequestPart MultipartFile file) {
    return this.pessoaService.uploadDocument(file);
  }

}
