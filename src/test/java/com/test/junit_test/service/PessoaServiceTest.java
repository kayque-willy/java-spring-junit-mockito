package com.test.junit_test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import com.test.junit_test.exception.BusinessException;
import com.test.junit_test.model.Pessoa;
import com.test.junit_test.repository.PessoaRepository;

@ExtendWith(MockitoExtension.class)
public class PessoaServiceTest {

  // Esta classe representa o teste unitário da pessoaService e, 
  // portanto, não pode depender de implementações de outras classes, sendo que elas devem ser mockadas como a pessoaRepository

  // Padrão de teste AAA
  // - Arrange 
  // - Act
  // - Asset

  // @Spy é usado para criar uma instância de espionagem. 
  // @InjectMocks é usado para instanciar o objeto testado automaticamente e injetar nele todas as dependências anotadas @Mock ou @Spy
  @Spy
  @InjectMocks
  private PessoaService pessoaService;
 
  // @Mock é usado para criação de simulação das classes que estão dentro da classe testada pelo @InjectMocks
  @Mock
  private PessoaRepository pessoaRepository;

  private Pessoa pessoa;

  private MockMultipartFile file;

  // ---------------------------------- SETUP DO TESTE -----------------------------------

  // O @BeforeEach é chamado antes de executar o teste e é usado para definir o parâmetros e mockar os objetos de teste
  @BeforeEach
  @DisplayName("Setup para criação dos STUBS para pessoa e o arquivo")
  public void setup(){
    // Cria o STUB do objeto Pessoa [Arrange]
    this.pessoa = new Pessoa("Angelica", "12358569852", "Desenvolvedora", 30, "Sao Paulo", "Rua das Cruzes", 54);
  
    // Criação dos STUB do arquivo [Arrange]
    this.file = new MockMultipartFile(
        "file",
        "hello.txt",
        MediaType.TEXT_PLAIN_VALUE,
        "Hello, World!".getBytes());
  }

  // -------------------------------- TESTES DE SUCESSO ---------------------------------

  @Test
  @DisplayName("Deve buscar a pessoa pelo CPF com sucesso")
  public void deveBuscarPessoaPeloCPFComSucesso(){

    // ----------------------------- RESULTADO ESPERADO [Arrange]---------------------------

    // [when] Atribui o resultado esperado ao executar o método findPessoa
    // Se espera que o objeto retornado seja o mock do objeto this.pessoa
    when(this.pessoaRepository.findPessoa(this.pessoa.getCpf()))
      .thenReturn(Collections.singletonList(this.pessoa))
      .thenCallRealMethod();
  
    // ------------------------------ CHAMADA DO MÉTODO [Act] -----------------------------------

    // Faz a chamado do método do service com o parametro do CPF
    List<Pessoa> pessoas = this.pessoaService.buscaPessoasPorCpf(this.pessoa.getCpf());

    // --------------------------------- VERIFICAÇÕES [Assert] ------------------------------------

    // [assertEquals] verifica se o objeto retornado corresponde ao objeto this.pessoa mockado
    assertEquals(Collections.singletonList(this.pessoa), pessoas);
    // [verify] verifica se o método do repository foi chamado dentro do service
    verify(this.pessoaRepository).findPessoa(this.pessoa.getCpf());
    // [verifyNoMoreInteractions] verifica se o método do repository não foi chamado mais de uma vez
    verifyNoMoreInteractions(this.pessoaRepository);
  }

  @Test
  @DisplayName("Deve receber o arquivo e fazer o upload com sucesso")
  public void deveReceberArquivoEFazerUploadComSucesso() {
    // ----------------------------- RESULTADO ESPERADO [Arrange] ---------------------------

    // [when] Atribui o resultado esperado ao executar o método findPessoa
    // Se espera que o objeto retornado seja o mock do objeto this.pessoa
    // when(this.pessoaService.uploadDocument(this.file))
    //     .thenReturn(ResponseEntity.ok("Arquivo carregado " + this.file.getName()))
    //     .thenCallRealMethod();

    // ------------------------------ CHAMADA DO MÉTODO [Act] -----------------------------------

    // Faz a chamado do método do service com o parametro do CPF
    ResponseEntity responseService = this.pessoaService.uploadDocument(this.file);

    // --------------------------------- VERIFICAÇÕES [Assert] ------------------------------------

    // [assertEquals] verifica se o objeto retornado corresponde ao objeto
    // this.pessoa mockado
    assertEquals(responseService, ResponseEntity.ok("Arquivo carregado " + this.file.getName()));
    // [verify] verifica se o método do service foi chamado
    verify(this.pessoaService).uploadDocument(this.file);
    // [verifyNoMoreInteractions] verifica se o método do repository não foi chamado
    // mais de uma vez
    verifyNoMoreInteractions(this.pessoaService);
  }

  // --------------------------------- TESTES DE ERROS ---------------------------------

  @Test
  @DisplayName("Não deve chamar o repository caso o CPF seja nulo")
  public void naoDeveChamaroRepositoryCasoCPFNulo() {

    // ------------------------- CHAMADA DE MÉTODO PARA LANÇAR EXCEÇÃO [Act] -------------------------

    // [assertThrows] Verifica se é lançado exceção ao chamar o método ao lançar o CPF como null
    final BusinessException e = assertThrows(BusinessException.class, () -> {
      // Faza a chamada do método com o CPF nulo para gerar a exceção
      this.pessoaService.buscaPessoasPorCpf(null);
    });

    // ------------------------------------ VERIFICAÇÕES [Assert] --------------------------------------

    // Verifica se a exeção existe
    assertThat(e, notNullValue());
    // Verifica se a mensagem da exceção esta correta
    assertThat(e.getMessage(), is("Erro ao buscar a pessoa por CPF: null"));
    // Verifica se existe a causa na exeção
    assertThat(e.getCause(), notNullValue());
    // Verifica se a mensagem da causa está correta
    assertThat(e.getCause().getMessage(), is("CPF é obrigatório!"));

    // [verifyNoMoreInteractions] verifica se o método do repository não foi chamado mais de uma vez dentro do service
    verifyNoInteractions(this.pessoaRepository);

  }

  @Test
  @DisplayName("Não deve fazer upload caso o arquivo seja nulo")
  public void naoDeveFazerUploadCasoArquivoNulo() {

    // ------------------------- CHAMADA DE MÉTODO PARA LANÇAR EXCEÇÃO [Act] -------------------------

    // [assertThrows] Verifica se é lançado exceção ao chamar o método ao lançar o file como null
    final RuntimeException e = assertThrows(RuntimeException.class, () -> {
      // Faza a chamada do método com o arquivo nulo para gerar a exceção
      this.pessoaService.uploadDocument(null);
    });

    // ------------------------------------ VERIFICAÇÕES [Assert] --------------------------------------

    // Verifica se a exeção existe
    assertThat(e, notNullValue());
    // Verifica se a mensagem da exceção esta correta
    assertThat(e.getMessage(), is("Erro ao carregar arquivo"));
    // Verifica se existe a causa na exeção
    assertThat(e.getCause(), notNullValue());
    // Verifica se a mensagem da causa está correta
    assertThat(e.getCause().getMessage(), is("arquivo é obrigatório!"));

    // [verify] verifica se o método do service foi chamado 
    verify(this.pessoaService).uploadDocument(null);
  }

  @Test
  @DisplayName("Deve acionar exception quando o repository falhar")
  public void deveAcionarExceptionQuandoRepositoryFalhar() {

    // Define um CPF inválido
    String cpfInvalido =  "0000";

    // ----------------------------- RESULTADO ESPERADO [Arrange] ---------------------------

    // [When] Faz a chamada do método dentro do repository, o método deverá lançar a exeção
    when(this.pessoaRepository.findPessoa(cpfInvalido))
      // .thenThrow(new RuntimeException("Erro ao buscar a pessoa pelo CPF!"))
      .thenCallRealMethod();

    // ------------------------- CHAMADA DE MÉTODO PARA LANÇAR EXCEÇÃO [Act] -------------------------

    final BusinessException e = assertThrows(BusinessException.class, () -> {
      this.pessoaService.buscaPessoasPorCpf(cpfInvalido);
    });

    // ------------------------------------ VERIFICAÇÕES [Assert] --------------------------------------

    // Verifica se a mensagem da exceção esta correta
    assertThat(e.getMessage(),is("Erro ao buscar a pessoa por CPF: " + cpfInvalido));
    // Verifica se a causa da exceção lançada é RuntimeException
    assertThat(e.getCause().getClass(), is(RuntimeException.class));
    // Verifica se a mensagem da causa esta correta
    assertThat(e.getCause().getMessage(), is("Erro ao buscar a pessoa pelo CPF!"));

    // [verify] verifica se o método do repository foi chamado dentro do service
    verify(this.pessoaRepository).findPessoa(cpfInvalido);
    // [verifyNoMoreInteractions] verifica se o método do repository não foi chamado mais de uma vez dentro do service
    verifyNoMoreInteractions(this.pessoaRepository);
  }
  
}
