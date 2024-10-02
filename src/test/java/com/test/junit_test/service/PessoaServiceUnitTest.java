package com.test.junit_test.service;

import java.util.Collections;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import com.test.junit_test.exception.BusinessException;
import com.test.junit_test.model.Pessoa;
import com.test.junit_test.repository.PessoaRepository;

/* 

Esta classe representa o TESTE UNITÁRIO da pessoaService
e, portanto, não pode depender de implementações de outras classes, sendo que elas devem ser mockadas como a pessoaRepository

Padrão de teste AAA
- Arrange 
- Act
- Assert 

*/

@ExtendWith(MockitoExtension.class)
public class PessoaServiceUnitTest {

  // @Spy é usado para criar uma instância de espionagem. 
  // @InjectMocks é usado para instanciar o objeto testado automaticamente e injetar nele todas as dependências anotadas @Mock ou @Spy
  @Spy
  @InjectMocks
  private PessoaService pessoaService;
 
  // @Mock é usado para criação de simulação das classes que estão dentro da classe testada pelo @InjectMocks
  @Mock
  private PessoaRepository pessoaRepository;

  // @Captor é usado para verificar como foram passados os parãmetros de um objeto
  @Captor
  ArgumentCaptor<Pessoa> pessoaArgumentCaptor;

  @Captor
  ArgumentCaptor<String> pessoaCPFArgumentCaptor;

  // STUBS representam os objetos dos parâmetros
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
  @DisplayName("Deve buscar a pessoa com sucesso")
  public void deveBuscarPessoaComSucesso(){

    // ----------------------------- RESULTADO ESPERADO [Arrange]---------------------------

    // [when] Atribui o resultado esperado ao executar o método findPessoa do pessoaRepository
    // Utiliza o argument captor para verificar qual foi o objeto passado para o repository
    Mockito.when(this.pessoaRepository.findPessoa(this.pessoaArgumentCaptor.capture()))
      .thenReturn(Collections.singletonList(this.pessoa))
      .thenCallRealMethod();
  
    // ------------------------------ CHAMADA DO MÉTODO [Act] -----------------------------------

    // Faz a chamado do método do service com o parametro do CPF
    List<Pessoa> pessoas = this.pessoaService.buscarPessoa(this.pessoa);

    // --------------------------------- VERIFICAÇÕES [Assert] ------------------------------------

    // Verifica se houve resultado na busca
    Assertions.assertNotNull(pessoas);
    // Verifica se o objeto capturado no parâmetro do repository é igual ao objeto passado para o service
    Assertions.assertEquals(this.pessoa, this.pessoaArgumentCaptor.getValue());
    // [assertEquals] verifica se o objeto retornado corresponde ao objeto this.pessoa mockado
    Assertions.assertEquals(Collections.singletonList(this.pessoa), pessoas);
    // [verify] verifica se o método do repository foi chamado dentro do service
    Mockito.verify(this.pessoaRepository, Mockito.times(1)).findPessoa(this.pessoa);
    // [verifyNoMoreInteractions] verifica se o método do repository não foi chamado mais de uma vez
    Mockito.verifyNoMoreInteractions(this.pessoaRepository);
  }

  @Test
  @DisplayName("Deve buscar a pessoa pelo CPF com sucesso")
  public void deveBuscarPessoaPeloCPFComSucesso(){

    // ----------------------------- RESULTADO ESPERADO [Arrange]---------------------------

    // [when] Atribui o resultado esperado ao executar o método findPessoa do pessoaRepository
    Mockito.when(this.pessoaRepository.findPessoaPorCpf(this.pessoaCPFArgumentCaptor.capture()))
      .thenReturn(Collections.singletonList(this.pessoa))
      .thenCallRealMethod();
  
    // ------------------------------ CHAMADA DO MÉTODO [Act] -----------------------------------

    // Faz a chamado do método do service com o parametro do CPF
    List<Pessoa> pessoas = this.pessoaService.buscaPessoasPorCpf(this.pessoa.getCpf());

    // --------------------------------- VERIFICAÇÕES [Assert] ------------------------------------

    // Verifica se houve resultado na busca
    Assertions.assertNotNull(pessoas);
    // [assertEquals] verifica se o objeto retornado corresponde ao objeto this.pessoa mockado
    Assertions.assertEquals(Collections.singletonList(this.pessoa), pessoas);
    // Verifica se o parâmetro do CPF capturado no repository é igual ao do objeto passado para o service
    Assertions.assertEquals(this.pessoa.getCpf(), this.pessoaCPFArgumentCaptor.getValue());
    // [verify] verifica se o método do repository foi chamado dentro do service
    Mockito.verify(this.pessoaRepository, Mockito.times(1)).findPessoaPorCpf(this.pessoa.getCpf());
    // [verifyNoMoreInteractions] verifica se o método do repository não foi chamado mais de uma vez
    Mockito.verifyNoMoreInteractions(this.pessoaRepository);
  }

  @Test
  @DisplayName("Deve receber o arquivo e fazer o upload com sucesso")
  public void deveReceberArquivoEFazerUploadComSucesso() {
    // ----------------------------- RESULTADO ESPERADO [Arrange] ---------------------------

    // [when] Atribui o resultado esperado ao executar o método uploadDocument 
    // when(this.pessoaService.uploadDocument(this.file))
    //     .thenReturn(ResponseEntity.ok("Arquivo carregado " + this.file.getName()))
    //     .thenCallRealMethod();

    // ------------------------------ CHAMADA DO MÉTODO [Act] -----------------------------------

    // Faz a chamado do método do service com o parametro do CPF
    ResponseEntity responseService = this.pessoaService.uploadDocument(this.file);

    // --------------------------------- VERIFICAÇÕES [Assert] ------------------------------------

    // Verifica se foi retornado a response
    Assertions.assertNotNull(responseService);
    // [assertEquals] verifica se o objeto retornado corresponde ao objeto
    // this.pessoa mockado
    Assertions.assertEquals(responseService, ResponseEntity.ok("Arquivo carregado " + this.file.getName()));
    // [verify] verifica se o método do service foi chamado
    Mockito.verify(this.pessoaService, Mockito.times(1)).uploadDocument(this.file);
    // [verifyNoMoreInteractions] verifica se o método do repository não foi chamado
    // mais de uma vez
    Mockito.verifyNoMoreInteractions(this.pessoaService);
  }

  // --------------------------------- TESTES DE ERROS ---------------------------------

  @Test
  @DisplayName("Não deve chamar o repository caso o CPF seja nulo")
  public void naoDeveChamaroRepositoryCasoCPFNulo() {

    // ------------------------- CHAMADA DE MÉTODO PARA LANÇAR EXCEÇÃO [Act] -------------------------

    // [assertThrows] Verifica se é lançado exceção ao chamar o método ao lançar o CPF como null
    final BusinessException e = Assertions.assertThrows(BusinessException.class, () -> {
      // Faza a chamada do método com o CPF nulo para gerar a exceção
      this.pessoaService.buscaPessoasPorCpf(null);
    });

    // ------------------------------------ VERIFICAÇÕES [Assert] --------------------------------------

    // Verifica se foi lançado a exeção
    Assertions.assertNotNull(e);
    // Verifica se a mensagem da exceção esta correta
    MatcherAssert.assertThat(e.getMessage(), CoreMatchers.is("Erro ao buscar a pessoa por CPF: null"));
    // Verifica se existe a causa na exeção
    Assertions.assertNotNull(e.getCause());
    // Verifica se a mensagem da causa está correta
    MatcherAssert.assertThat(e.getCause().getMessage(), CoreMatchers.is("CPF é obrigatório!"));

    // [verifyNoMoreInteractions] verifica se o método do repository não foi chamado mais de uma vez dentro do service
    Mockito.verifyNoInteractions(this.pessoaRepository);

  }

  @Test
  @DisplayName("Deve acionar exception quando o repository falhar")
  public void deveAcionarExceptionQuandoRepositoryFalhar() {

    // Define um CPF inválido
    String cpfInvalido =  "0000";

    // ----------------------------- RESULTADO ESPERADO [Arrange] ---------------------------

    // [When] Simula o lançamento de uma exceção pelo método do repository
    Mockito.when(this.pessoaRepository.findPessoaPorCpf(cpfInvalido))
    //   .thenThrow(new RuntimeException("Erro ao buscar a pessoa pelo CPF!"))
      .thenCallRealMethod();

    // ------------------------- CHAMADA DE MÉTODO PARA LANÇAR EXCEÇÃO [Act] -------------------------

    final BusinessException e = Assertions.assertThrows(BusinessException.class, () -> {
      this.pessoaService.buscaPessoasPorCpf(cpfInvalido);
    });

    // ------------------------------------ VERIFICAÇÕES [Assert] --------------------------------------

    // Verifica se foi lançado a exeção
    Assertions.assertNotNull(e);
    // Verifica se a mensagem da exceção esta correta
    MatcherAssert.assertThat(e.getMessage(), CoreMatchers.is("Erro ao buscar a pessoa por CPF: " + cpfInvalido));
    // Verifica se a causa da exceção lançada é RuntimeException
    MatcherAssert.assertThat(e.getCause().getClass(), CoreMatchers.is(RuntimeException.class));
    // Verifica se a mensagem da causa esta correta
    MatcherAssert.assertThat(e.getCause().getMessage(), CoreMatchers.is("Erro ao buscar a pessoa pelo CPF!"));

    // [verify] verifica se o método do repository foi chamado dentro do service
    Mockito.verify(this.pessoaRepository, Mockito.times(1)).findPessoaPorCpf(cpfInvalido);
    // [verifyNoMoreInteractions] verifica se o método do repository não foi chamado mais de uma vez dentro do service
    Mockito.verifyNoMoreInteractions(this.pessoaRepository);
  }

  @Test
  @DisplayName("Não deve fazer upload caso o arquivo seja nulo")
  public void naoDeveFazerUploadCasoArquivoNulo() {

    // ------------------------- CHAMADA DE MÉTODO PARA LANÇAR EXCEÇÃO [Act] -------------------------

    // [assertThrows] Verifica se é lançado exceção ao chamar o método ao lançar o file como null
    final RuntimeException e = Assertions.assertThrows(RuntimeException.class, () -> {
      // Faza a chamada do método com o arquivo nulo para gerar a exceção
      this.pessoaService.uploadDocument(null);
    });

    // ------------------------------------ VERIFICAÇÕES [Assert] --------------------------------------

    // Verifica se foi lançado a exeção
    Assertions.assertNotNull(e);
    // Verifica se a mensagem da exceção esta correta
    MatcherAssert.assertThat(e.getMessage(), CoreMatchers.is("Erro ao carregar arquivo"));
    // Verifica se existe a causa na exeção
    Assertions.assertNotNull(e.getCause());
    // Verifica se a mensagem da causa está correta
    MatcherAssert.assertThat(e.getCause().getMessage(), CoreMatchers.is("arquivo é obrigatório!"));

    // [verify] verifica se o método do service foi chamado 
    Mockito.verify(this.pessoaService, Mockito.times(1)).uploadDocument(null);
  }
  
}
