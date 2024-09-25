package com.test.junit_test.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.test.junit_test.model.Pessoa;
import com.test.junit_test.service.PessoaService;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.Collections;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PessoaControllerTest {

  // @InjectMocks é usado para instanciar o objeto testado automaticamente e injetar nele todas as dependências anotadas @Mock ou @Spy
  @InjectMocks
  private PessoaController pessoaController;

  // @Mock é usado para criação de simulação das classes que estão dentro da classe testada pelo @InjectMocks
  @Mock
  private PessoaService pessoaService;

  private MockMvc mockMvcPessoaController;

  private Pessoa pessoa, pessoaErro;

  private MockMultipartFile file;

  // ---------------------------------- SETUP DO TESTE -----------------------------------
  
  // O @BeforeEach é chamado antes de executar o teste e é usado para definir o parâmetros e mockar os objetos de teste
  @BeforeEach
  public void setup() {

    // Criação do Mock Mvc do PessoaController
    this.mockMvcPessoaController = MockMvcBuilders.standaloneSetup(this.pessoaController)
        .alwaysDo(print())
        .build();

    // Criação dos STUBS do arquivo, pessoa correta e pessoa com erro
    this.file = new MockMultipartFile(
        "file",
        "hello.txt",
        MediaType.TEXT_PLAIN_VALUE,
        "Hello, World!".getBytes());

    this.pessoa = new Pessoa("João", "12358569852", "Desenvolvedor", 30, "Sao Paulo", "Rua das Cruzes", 54);
    this.pessoaErro = new Pessoa("João", null, "Desenvolvedor", 30, "Sao Paulo", "Rua das Cruzes", 54);
  }

  // -------------------------------- TESTES DE SUCESSO ---------------------------------

  @Test
  public void deveRealizarUploadDocumentosComSucesso() throws Exception {
    
    // ----------------------------- RESULTADO ESPERADO ---------------------------

    // [when] Atribui o resultado esperado ao executar o método uploadDocument
    // Se espera o retorno da resposta de sucesso
    
    when(this.pessoaService.uploadDocument(this.file))
      .thenReturn(ResponseEntity.ok("Documento Carregado Com Sucesso!"))
      .thenCallRealMethod();
    
    // ------------------------------ CHAMADA DO MÉTODO -----------------------------------

    // Faz a chamada no endpoint do controlador com os parãmetros
    this.mockMvcPessoaController.perform(multipart("/pessoa/upload-documento")
        .file(this.file)
        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();


    // --------------------------------- VERIFICAÇÕES ------------------------------------
    // [verify] verifica se o método do service foi chamado uma vez no controller
    verify(this.pessoaService).uploadDocument(this.file);
    // [verifyNoMoreInteractions] verifica se o método do service não foi chamado mais de uma vez
    verifyNoMoreInteractions(this.pessoaService);
  }

  @Test
  public void deveBuscarPessoaPorCPFComSucesso() throws Exception {
    // ----------------------------- RESULTADO ESPERADO ---------------------------

    // [when] Atribui o resultado esperado ao executar o método findPessoa
    // Se espera que o objeto retornado seja o mock do objeto this.pessoa
    when(this.pessoaService.buscaPessoasPorCpf(this.pessoa.getCpf()))
      .thenReturn(Collections.singletonList(this.pessoa))
      .thenCallRealMethod();

    // ------------------------------ CHAMADA DO MÉTODO -----------------------------------

    // Faz a chamado do método do service com o parametro do CPF
    this.mockMvcPessoaController.perform(get("/pessoa/buscar-por-cpf")
        .contentType(MediaType.APPLICATION_JSON)
        .param("cpf",Optional.ofNullable(this.pessoa).map(Pessoa::getCpf).orElse(null)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();

    // --------------------------------- VERIFICAÇÕES ------------------------------------

    // [verify] verifica se o método do service foi chamado uma vez no controller
    verify(this.pessoaService).buscaPessoasPorCpf(this.pessoa.getCpf());
    // [verifyNoMoreInteractions] verifica se o método do service não foi chamado mais de uma vez
    verifyNoMoreInteractions(this.pessoaService);
  }

  // --------------------------------- TESTES DE ERROS ---------------------------------

  @Test
  public void deveRetornarErroCasoNaoPassadosParametrosObrigatorios() throws Exception {

    // ------------------------------ CHAMADA DO MÉTODO -----------------------------------

    // Faz a chamado do método do service com o parametro do CPF nulo
    this.mockMvcPessoaController.perform(get("/pessoa/buscar-por-cpf")
        .contentType(MediaType.APPLICATION_JSON)
        .param("cpf",Optional.ofNullable(this.pessoaErro).map(Pessoa::getCpf).orElse(null)))
        .andExpect(MockMvcResultMatchers.status().is4xxClientError())
        .andReturn();

    // [verifyNoMoreInteractions] verifica se o método do repository não foi chamado mais de uma vez
    verifyNoInteractions(this.pessoaService);

  }

}
