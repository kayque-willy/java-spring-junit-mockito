
# Testes Automatizados - Java Spring Boot (JUnit5 e Mockito)

Esse projeto é uma demonstração de testes automatizados em Java Spring com JUnit5 e Mockito. Para isso, foram utilizadas três classes:

- PessoaController
- PessoaService
- PessoaRepository

São demonstrados os **testes unitários** para a classe **PessoaService** com mock da classe PessoaRepository. E são demonstrados os **testes de integração** para a classe **PessoaController** para avaliar todas as camadas de arquitetura do proejto.
  
## Testes unitários da classe Pessoa Service

### Testes de Sucesso
 1. Deve buscar a pessoa com sucesso  
 2. Deve buscar a pessoa pelo CPF com sucesso  
 3. Deve receber o arquivo e fazer o upload com sucesso

### Testes de Erro
 1. Não deve chamar o repository caso o CPF seja nulo 
 2. Deve acionar exception quando o repository falhar 
 3. Não deve fazer upload caso o arquivo seja nulo

## Testes de integração da classe Pessoa Controller
  
### Testes de Sucesso
 1. Deve adicionar a pessoa com sucesso 
 2. Deve buscar a pessoa pelo CPF com sucesso 
 3. Deve retornar a lista de todas as pessoas 
 4. Deve realizar o upload do documento com sucesso

### Testes de Erro
 1. Deve retornar erro caso não seja passado os parâmetros obrigatórios
    (CPF) ao buscar a pessoa
