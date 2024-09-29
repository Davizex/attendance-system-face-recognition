# Rekome - Sistema de Reconhecimento Facial e Controle de Presença

## Descrição

O **Rekome** é um sistema de reconhecimento facial desenvolvido para controlar a presença em grupos e eventos. Ele utiliza tecnologias modernas, como o **Spring Boot**, **AWS Rekognition** e o banco de dados **MariaDB**, além de recursos de segurança, como autenticação via OAuth2 e integração com AWS S3 para armazenamento de imagens.

- - - 

## Stack de desenvolvimento

| **Camada**                | **Tecnologia/Serviço**                  | **Descrição**                                                                                      |
|---------------------------|-----------------------------------------|----------------------------------------------------------------------------------------------------|
| **Linguagem**              | **Java 21**                             | Linguagem principal usada no desenvolvimento do backend da aplicação.                              |
| **Framework**              | **Spring Boot 3.2.3**                   | Framework para criação de APIs REST, gerenciamento de segurança e configuração de dependências.     |
| **Persistência de Dados**  | **Spring Data JPA**                     | Framework ORM que usa Hibernate para mapeamento objeto-relacional e comunicação com banco de dados. |
| **Banco de Dados**         | **MariaDB**                             | Banco de dados relacional utilizado para armazenar os dados do sistema.                            |
| **Reconhecimento Facial**  | **AWS Rekognition**                     | Serviço da AWS utilizado para reconhecimento facial, integrado à aplicação para validação de presença. |
| **Armazenamento de imagens** | **Amazon S3**                           | Serviço de armazenamento na nuvem da AWS usado para armazenar imagens e outros objetos.             |
| **Segurança**              | **Spring Security**                     | Implementa autenticação e autorização, integrando-se com OAuth2 e proteção de APIs REST.            |
| **Autenticação**           | **OAuth2 Resource Server**    e **JWT**             | Servidor de recursos que valida tokens JWT e garante segurança das APIs REST.                       |
| **Gerenciamento de Dependências** | **Maven**                      | Ferramenta para gerenciamento de dependências e automação de build.                                 |
| **Monitoramento**          | **Spring Boot Actuator**                | Exposição de métricas, saúde da aplicação e endpoints para monitoramento e administração.           |
| **Coleta de Métricas**     | **Prometheus + Micrometer**             | Integração para monitoramento de métricas da aplicação, facilitando análise de desempenho.           |
| **Testes**                 | **JUnit & Spring Security Test**        | Ferramentas de teste unitário e de segurança para validar o comportamento da aplicação.             |
| **Desenvolvimento Rápido** | **Spring Boot DevTools**                | Ferramenta para acelerar o desenvolvimento com reload automático e hot swapping.                    |
| **Armazenamento de Credenciais** | **AWS SDK**                      | SDK para integração com serviços da AWS, como S3 e Rekognition, facilitando operações na nuvem.     |

## Funcionalidades

- **Reconhecimento Facial**: Integração com o serviço AWS Rekognition para identificação facial.
- **Controle de Presença**: Gerenciamento de presença em grupos, utilizando reconhecimento facial.
- **Gerenciamento de Grupos e Usuários**: Ferramentas para criar, editar e gerenciar grupos e usuários.
- **Segurança**: Autenticação via OAuth2 com suporte a recursos de segurança.
- **Observabilidade**: Monitoramento de métricas através do **Prometheus** e endpoints do Spring Boot Actuator.

## Gerando a Imagem Docker

O projeto utiliza o Spring Boot, que possui suporte integrado para a criação de imagens Docker. Para gerar a imagem Docker do projeto, siga as etapas abaixo.

### Pré-requisitos

- **Docker**: Certifique-se de que o Docker está instalado e em execução na sua máquina. Para verificar, execute o seguinte comando:
  ```bash
  docker --version
  ```

- **Maven**: Verifique se você tem o Maven configurado corretamente:
  ```bash
  mvn -version
  ```

### Passo a Passo para Gerar a Imagem Docker

1. **Gerar a Imagem**:
   Navegue até o diretório raiz do projeto e execute o seguinte comando para criar a imagem Docker:

   ```bash
   mvn spring-boot:build-image
   ```

   Esse comando irá:
   - Construir o projeto Spring Boot.
   - Gerar uma imagem Docker baseada no código fonte da aplicação.

### Executando a Imagem Docker

Após a criação da imagem, você pode executar o contêiner com o seguinte comando:

```bash
docker run -p 8080:8080 <nome-da-imagem>
```

Substitua `<nome-da-imagem>` pelo nome da imagem gerada, por exemplo, `mydocker/rekome`.

### Verificando a Imagem

Você pode listar todas as imagens Docker disponíveis com o comando:

```bash
docker images
```

Para verificar se o contêiner está em execução, use:

```bash
docker ps
```

---

---

## Executando Testes

O projeto inclui um conjunto de testes automatizados para garantir a qualidade e a funcionalidade do sistema. Para rodar os testes, utilizamos o Maven como ferramenta de build e gerenciamento de dependências.

### Comando para Executar Testes

Para executar todos os testes do projeto, navegue até o diretório raiz do projeto e execute o seguinte comando:

```bash
mvn test
```

Este comando irá:
- Compilar o projeto.
- Executar os testes unitários e de integração definidos no projeto.

### Gerar Relatório de Cobertura de Testes

Caso o projeto tenha integração com o plugin **JaCoCo** para relatórios de cobertura de testes, você pode gerar esses relatórios executando o seguinte comando:

```bash
mvn test jacoco:report
```

O relatório de cobertura será gerado no diretório `target/site/jacoco/index.html`. Você pode abrir esse arquivo no navegador para visualizar o relatório completo.

### Verificando Resultados dos Testes

Após a execução dos testes, o Maven exibirá no console o resultado da execução, incluindo:
- O número total de testes executados.
- Quantos passaram ou falharam.
- Informações sobre qualquer erro ou falha.

Os resultados detalhados dos testes também podem ser encontrados no diretório `target/surefire-reports`, que contém logs e relatórios dos testes.

---
## Contribuindo

1. Faça um **fork** do repositório.
2. Crie um **branch** para sua feature:
   ```bash
   git checkout -b minha-feature
   ```
3. Faça suas alterações e crie um **commit**:
   ```bash
   git commit -m 'Minha nova feature'
   ```
4. Envie um **pull request** para o repositório original.
