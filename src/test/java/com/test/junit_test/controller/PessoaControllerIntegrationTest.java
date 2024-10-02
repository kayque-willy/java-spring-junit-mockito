package com.test.junit_test.controller;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.junit_test.model.Pessoa;
import com.test.junit_test.repository.PessoaRepository;
import com.test.junit_test.service.PessoaService;

import java.util.Optional;

/*

Esta classe representa o TESTE DE INTEGRAÇÃO da PessoaController

Padrão de teste AAA
- Arrange 
- Act
- Assert 

*/

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
public class PessoaControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvcPessoaController;

  @Autowired
  private PessoaRepository pessoaRepository;

  @Autowired
  private ObjectMapper objectMapper;

  // @MockBean
  // private PessoaService pessoaService;

  // STUBS representam os objetos dos parâmetros
  private Pessoa pessoa, pessoaErro;

  private MockMultipartFile file;

  // ---------------------------------- SETUP DO TESTE -----------------------------------
  
  // O @BeforeAll é chamado uma vez antes da execução de todos os testes e é usado para definir o parâmetros e mockar os objetos de teste
  @BeforeAll
  @DisplayName("Setup para criação dos STUBS para pessoa e o arquivo")
  public void beforeAll() throws Exception {
    // Criação do STUB da pessoa para o teste de sucesso
    this.pessoa = new Pessoa(
        "Teste",
        "12345678",
        "Desenvolvedor",
        30,
        "Cidade",
        "Nome da Rua",
        54);

    // Salva a pessoa de teste no repositório
    this.pessoaRepository.addPessoa(this.pessoa);
  }

  // O @AfterAll é chamado uma vez após a execução de todos os testes e é usado para remover os parâmetros e objetos de teste
  @AfterAll
  @DisplayName("Setup para remoção dos STUBS para pessoa")
  public void afterAll() throws Exception {
    // Remove a pessoa de teste do repositório
    this.pessoaRepository.removePessoa(this.pessoa);
  }

  // O @BeforeEach é chamado antes de executar cada um dos testes e é usado para definir o parâmetros e mockar os objetos de teste
  @BeforeEach
  @DisplayName("Setup para criação dos STUBS para pessoa e o arquivo")
  public void beforeEach() {

    // Configuração do Mock Mvc do PessoaController para imprimir sempre os retornos [Arrange]

    // this.mockMvcPessoaController = MockMvcBuilders.standaloneSetup(this.pessoaController)
    //     .alwaysDo(MockMvcResultHandlers.print())
    //     .build();

    // Criação do STUB da pessoa para o teste de erro
    this.pessoaErro = new Pessoa(
      "Teste",
      null,
      "Desenvolvedor",
      30,
      "Cidade",
      "Nome da Rua",
      54);

    // Criação do STUB do arquivo, pessoa correta e pessoa com erro  [Arrange]
    this.file = new MockMultipartFile(
      "file",
      "hello.txt",
      MediaType.TEXT_PLAIN_VALUE,
      "Hello, World!".getBytes());
  }

   // O @AfterEach é chamado após a execução de cada um dos testes e é usado para remover os parâmetros e objetos de teste
  @AfterEach
  @DisplayName("Setup para remoção dos STUBS para pessoa")
  public void afterEach() {
    // Remove a pessoa de erro de teste do repositório
    this.pessoaErro = null;
  }

  // -------------------------------- TESTES DE SUCESSO ---------------------------------

  @Test
  @DisplayName("Deve adicionar a pessoa com sucesso")
  public void deveAdicionarPessoaComSucesso() throws Exception{

    // ----------------------------- CRIAÇÃO DO PARÂMETRO [Arrange]---------------------------

    String pessoaRequest = this.objectMapper.writeValueAsString(this.pessoa);

    // ------------------------------ CHAMADA DO MÉTODO [Act] -----------------------------------

    // Faz a chamado do método do controller com o parametro do CPF
    this.mockMvcPessoaController
        .perform(
          MockMvcRequestBuilders.post("/pessoa/adicionar-pessoa")
          .contentType(MediaType.APPLICATION_JSON)
          .characterEncoding("UTF-8")
          .content(pessoaRequest)
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(result -> {
          // ------------------------------------ VERIFICAÇÕES [Assert] --------------------------------------
          // Verifica se existe retorno
          Assertions.assertNotNull(result);
          // Verifica se veio o JSON da resposta
          Assertions.assertNotNull(result.getResponse());
          // Verifica se o status de retorno é 400
          Assertions.assertEquals(result.getResponse().getStatus(), HttpStatus.OK.value());
          // Verifica o objeto salvo corresponde ao objeto enviado
          JSONObject jsonObject = new JSONObject(result.getResponse().getContentAsString());
          Assertions.assertEquals(jsonObject.getString("nome"), this.pessoa.getNome());
          Assertions.assertEquals(jsonObject.getString("cpf"), this.pessoa.getCpf());
          Assertions.assertEquals(jsonObject.getString("profissao"), this.pessoa.getProfissao());
          Assertions.assertEquals(jsonObject.getInt("idade"), this.pessoa.getIdade());
          Assertions.assertEquals(jsonObject.getString("cidade"), this.pessoa.getCidade());
          Assertions.assertEquals(jsonObject.getString("rua"), this.pessoa.getRua());
          Assertions.assertEquals(jsonObject.getInt("numero"), this.pessoa.getNumero());
      })
        .andDo(MockMvcResultHandlers.print())
        .andReturn();
  }

  @Test
  @DisplayName("Deve buscar a pessoa pelo CPF com sucesso")
  public void deveBuscarPessoaPorCPFComSucesso() throws Exception {

    // ----------------------------- CRIAÇÃO DO PARÂMETRO [Arrange]---------------------------

    String cpfRequest = Optional.ofNullable(this.pessoa).map(Pessoa::getCpf).orElse(null);

    // ------------------------------ CHAMADA DO MÉTODO [Act] -----------------------------------

    // Faz a chamado do método do controller com o parametro do CPF
    this.mockMvcPessoaController
        .perform(
          MockMvcRequestBuilders.get("/pessoa/buscar-por-cpf")
          .contentType(MediaType.APPLICATION_JSON)
          .param("cpf", cpfRequest)
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print())
        .andReturn();
  }

  @Test
  @DisplayName("Deve retornar a lista de todas as pessoas")
  public void deveListarPessoasComSucesso() throws Exception {

    // ------------------------------ CHAMADA DO MÉTODO [Act] -----------------------------------

    // Faz a chamado do método do controller
    this.mockMvcPessoaController
        .perform(
          MockMvcRequestBuilders.get("/pessoa/listar")
          .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(result -> {
          // ------------------------------------ VERIFICAÇÕES [Assert] --------------------------------------
          // Verifica se existe retorno
          Assertions.assertNotNull(result);
          // Verifica se veio o JSON da resposta
          Assertions.assertNotNull(result.getResponse());
          // Verifica se o status de retorno é 400
          Assertions.assertEquals(result.getResponse().getStatus(), HttpStatus.OK.value());
          // Verifica se a lista possui resultados
          JSONArray jsonArray = new JSONArray(result.getResponse().getContentAsString());
          Assertions.assertTrue(jsonArray.length() > 0);
      })
        .andDo(MockMvcResultHandlers.print())
        .andReturn();
  }

  @Test
  @DisplayName("Deve realizar o upload do documento com sucesso")
  public void deveRealizarUploadDocumentosComSucesso() throws Exception {

    // ------------------------------ CHAMADA DO MÉTODO [Act] -----------------------------------

    // Faz a chamada no endpoint do controlador com os parãmetros
    this.mockMvcPessoaController
      .perform(
        MockMvcRequestBuilders.multipart("/pessoa/upload-documento")
        .file(this.file)
        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE
      ))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andDo(MockMvcResultHandlers.print())
      .andReturn();
  }

  // --------------------------------- TESTES DE ERROS ---------------------------------

  @Test
  @DisplayName("Deve retornar erro caso não seja passado os parâmetros obrigatórios (CPF) ao buscar a pessoa")
  public void deveRetornarErroCasoNaoPassadosParametrosObrigatoriosAoBuscarPessoa() throws Exception {

    // ----------------------------- CRIAÇÃO DO PARÂMETRO [Arrange]---------------------------

     String cpfRequest = Optional.ofNullable(this.pessoaErro).map(Pessoa::getCpf).orElse(null);

    // ------------------------------ CHAMADA DO MÉTODO [Act] -----------------------------------

    // Faz a chamado do método do service com o parametro do CPF nulo
    this.mockMvcPessoaController
      .perform(
        MockMvcRequestBuilders.get("/pessoa/buscar-por-cpf")
        .contentType(MediaType.APPLICATION_JSON)
        .param("cpf",cpfRequest)
      )
      .andExpect(MockMvcResultMatchers.status().is4xxClientError())
      .andExpect(result -> {
          // ------------------------------------ VERIFICAÇÕES [Assert] --------------------------------------
          // Verifica se existe retorno
          Assertions.assertNotNull(result);
          // Verifica se o retorno é uma Exeption
          Assertions.assertTrue(result.getResolvedException() instanceof Exception);
          // Verifica se o status de retorno é 400
          Assertions.assertEquals(result.getResponse().getStatus(), HttpStatus.BAD_REQUEST.value());
          // Verifica se a mensagem da exceção esta correta
          MatcherAssert.assertThat(result.getResolvedException().getMessage(), CoreMatchers.is("Required request parameter 'cpf' for method parameter type String is not present"));
          // Verifica se existe a causa na exeção
          // Assertions.assertNotNull(result.getResolvedException().getCause());
          // Verifica se a mensagem da causa está correta
          // MatcherAssert.assertThat(result.getResolvedException().getCause().getMessage(), CoreMatchers.is("CPF é obrigatório!"));
      })
      .andDo(MockMvcResultHandlers.print())
      .andReturn();
  }

}
