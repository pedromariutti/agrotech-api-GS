# API de Agricultura de Precisão (AgroTech)

API RESTful desenvolvida em Java com Spring Boot para o ecossistema de agricultura inteligente. A plataforma conecta dados de telemetria do solo (via IoT/ESP32) com dados espaciais meteorológicos (NASA/ESA) para o bloqueio preditivo de irrigação e otimização do plantio com foco na economia de água.

## Índice

- [Sobre o Projeto](#sobre-o-projeto)
- [Deploy](#deploy)
- [Configuração do Spring Initializr](#configuração-do-spring-initializr)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Pré-requisitos](#pré-requisitos)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Endpoints da API](#endpoints-da-api)
- [Configuração do Ambiente](#configuração-do-ambiente)
- [Instalação e Execução](#instalação-e-execução)
- [Banco de Dados](#banco-de-dados)
- [Testes](#testes)
- [Documentação da API (Swagger)](#documentação-da-api-swagger)
- [Modelo UML](#modelo-uml)

## Sobre o Projeto

Esta API foi desenvolvida como parte do Challenge para fornecer uma solução tecnológica voltada para a sustentabilidade e automação agrícola. Suas principais funcionalidades incluem:

- **Telemetria IoT (ESP32):** Recepção e registro contínuo em tempo real de níveis de umidade e temperatura do solo diretamente da propriedade.

- **Integração Espacial:** Consumo de dados orbitais de satélites meteorológicos para monitoramento climático e detecção de frentes de chuva por região.

- **Inteligência de Irrigação:** Análise cruzada automatizada. Caso o solo precise de água, mas haja previsão de chuva iminente detectada pelos satélites, o acionamento da rega automática é bloqueado para evitar desperdício de recursos hídricos.

- **Histórico Preditivo:** Armazenamento seguro de todas as telemetrias e dados orbitais no banco de dados Oracle para suporte à emissão futura de alertas climáticos e planejamento de safras.

A aplicação segue os princípios REST e utiliza boas práticas de desenvolvimento como DTOs (Data Transfer Objects) para validação de dados com Bean Validation, inversão de controle e isolamento completo das camadas.

##  Deploy

A API foi devidamente containerizada e hospedada na nuvem através do **Render**. 

Acesse a documentação interativa (Swagger) ao vivo pelo link:
👉 **[ACESSAR O SWAGGER DA API NA NUVEM](https://agrotech-api-gs-java.onrender.com/swagger-ui/index.html)**

*Nota: Por se tratar de um servidor gratuito, a primeira requisição pode levar cerca de 50 segundos para "acordar" a máquina virtual. As requisições subsequentes ocorrerão em tempo real.*

#### Server live

<img width="675" height="905" alt="DeployLive" src="https://github.com/user-attachments/assets/c55309b4-8371-42ed-97fd-db2f9e0e922f" />

#### Swagger live

<img width="1534" height="977" alt="image" src="https://github.com/user-attachments/assets/2e8a093c-5958-4740-884b-aa90255e504e" />



## Configuração do Spring Initializr

O projeto foi gerado utilizando o [Spring Initializr](https://start.spring.io/) com as seguintes configurações:

### Configurações Básicas

| Configuração | Valor Selecionado |
|--------------|-------------------|
| **Project** | Maven |
| **Language** | Java |
| **Spring Boot** | 4.0.6 |
| **Group** | br.com.fiap |
| **Artifact** | agrotech |
| **Package name** | br.com.fiap.agrotech |
| **Packaging** | Jar |
| **Configuration** | Properties |
| **Java** | 21 |

### Dependências Selecionadas

| Dependência | Descrição |
|-------------|-----------|
| **Spring Web** | Criação de aplicações web e APIs RESTful usando Spring MVC com o container embutido Apache Tomcat. |
| **Spring Data JPA** | Persistência de dados em bancos SQL utilizando Java Persistence API (JPA) e Hibernate. |
| **Oracle Driver** | Driver JDBC oficial para fornecer acesso e conectividade ao banco de dados Oracle. |
| **Validation** | Validação de dados de entrada através do Bean Validation com o Hibernate Validator. |
| **Lombok** | Biblioteca de anotações para redução de código boilerplate (Getters, Setters, Construtores). |
| **Springdoc OpenAPI UI** | Geração automatizada da documentação interativa da API através da interface do Swagger. |

### Screenshot da Configuração

<img width="1666" height="879" alt="SpringInitializr" src="https://github.com/user-attachments/assets/7e9ceed8-faaf-4040-80f8-c419d62354e1" />

## Tecnologias Utilizadas

| Tecnologia | Versão | Finalidade |
|------------|--------|-------------|
| Java | 21 | Linguagem de programação (LTS) |
| Spring Boot | 4.0.6 | Framework de desenvolvimento principal |
| Spring Web | - | Criação de endpoints e arquitetura REST |
| Spring Data JPA | - | Abstração de persistência de dados |
| Hibernate | - | Mecanismo de ORM (Object-Relational Mapping) |
| Validation | - | Validação de requisições com Bean Validation |
| Oracle Database | - | Banco de dados relacional em nuvem |
| Lombok | - | Eliminação de código boilerplate em entidades e DTOs |
| Maven | 4.0.0 | Gerenciador de dependências e automação de build |
| Apache Tomcat | - | Servidor web embutido padrão do Spring Boot |
| Springdoc OpenAPI | 2.4.0 | Documentação interativa e ambiente de testes (Swagger) |

## Pré-requisitos

Antes de começar, você vai precisar ter instalado em sua máquina:

- [Java JDK 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html) (obrigatório - versão LTS)
- [Maven 3.6 ou superior](https://maven.apache.org/download.cgi)
- Acesso ao banco de dados Oracle da FIAP (ou outro banco de sua preferência)
- IDE de sua preferência (IntelliJ IDEA, Eclipse, VS Code)

## Estrutura do Projeto

A organização das pastas e pacotes da aplicação segue os padrões recomendados para a arquitetura de projetos Spring Boot.

```text
src/main/java/br/com/fiap/agrotech/
├── controller/          # Controladores REST que expõem as portas de entrada da API (Endpoints)
├── dto/                 # Classes de transferência de dados (Data Transfer Objects) e validações
├── model/               # Entidades de negócio mapeadas como tabelas no banco de dados (JPA)
├── repository/          # Interfaces de persistência e comunicação com o Oracle Database
└── service/             # Camada responsável pelas lógicas corporativas, cruzamento e regras de risco
```

## Endpoints da API

Abaixo estão listadas as rotas disponíveis na aplicação para realizar o CRUD completo de registros e acionar as regras de negócio inteligentes de irrigação.

| Método | Endpoint | Parâmetros Recebidos | Descrição |
| :---: | :--- | :--- | :--- |
| **POST** | `/api/agro/solo` | `Body` (JSON) | Recebe dados telemétricos do solo (ESP32), valida os limites, cruza com dados orbitais de satélite e decide sobre o bloqueio de rega. |
| **GET** | `/api/agro/solo` | - | Retorna a lista completa com todo o histórico de leituras de solo armazenadas no banco Oracle. |
| **GET** | `/api/agro/solo/{id}` | id (Path) | Busca os dados isolados de uma leitura específica do solo através de seu identificador exclusivo. |
| **PUT** | `api/agro/solo/{id}` |  id (Path), `Body` (JSON) | Atualiza as informações de uma telemetria existente e reavalia os critérios de controle agrícola. |
| **DELETE** | `api/agro/solo/{id}` |  id (Path) | Remove de forma definitiva um registro de telemetria do banco de dados relacional. |
| **POST** | `/api/agro/satelite` | `Body` (JSON) | Cadastra previsões e alertas climáticos orbitais simulados provenientes de satélites (NASA/ESA). |
| **GET** | `/api/agro/satelite` | - | Lista todo o histórico de monitoramento orbital e previsões meteorológicas registradas. |

## Configuração do Ambiente

A aplicação está configurada para usar o banco de dados Oracle da FIAP:

```properties
# Configurações do banco de dados Oracle FIAP
spring.datasource.url=jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL
spring.datasource.username=RM75999
spring.datasource.password=150896
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver

# Configurações do JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.database-platform=org.hibernate.dialect.OracleDialect
```

# Verificar versão do Java (deve ser 21)
java -version

# Verificar versão do Maven (deve ser 3.6+)
mvn -version

## Instalação e Execução

### Passo a Passo para Importar e Executar o Projeto

#### Abrir o Projeto no IntelliJ

**Opção A: baixar o zip daqui**
descompactar o arquivo 
File → Open → Selecione a pasta do projeto → OK

**Opção B: Clonar do GitHub**
File → New → Project from Version Control → Git
URL: https://github.com/pedromariutti/agrotech-api-GS.git

#### Aguardar o Download das Dependências

O IntelliJ automaticamente:
- Baixa todas as dependências do Maven (definidas no `pom.xml`)
- Indexa os arquivos do projeto
- Configura o classpath
 **Você verá uma barra de progresso no canto inferior direito**

#### Configurar o Banco de Dados (antes de executar)

**Localize o arquivo de configuração:**
src/main/resources/application.properties

#### por último executar a Aplicação
Navegue até a classe principal:
src/main/java/br/com/fiap/agrotech/AgrotechApplication.java

Clique no ícone ▶️ (Play) ao lado da classe ou do método main

Escolha "Run 'AgrotechApplication.main()'".

O servidor Tomcat iniciará na porta 8080.

Acesse o Swagger em: http://localhost:8080/swagger-ui/index.html

## Banco de Dados

### Sobre o Banco Oracle
Este projeto utiliza **Oracle Database** como sistema de gerenciamento de banco de dados relacional.

### Configuração do Banco (Já Configurado)

#### Conexão ativa com o Oracle Database da FIAP
spring.datasource.url=jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL
spring.datasource.username=RM75999
spring.datasource.password=150896
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver

#### Diretivas ORM de mapeamento automático do Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.database-platform=org.hibernate.dialect.OracleDialect

# Testes
Realizados no Swagger http://localhost:8080/swagger-ui.html
<img width="1419" height="764" alt="Swagger" src="https://github.com/user-attachments/assets/b21e3dae-cf22-4a11-9767-d4bd6f7a8bf4" />

## Cadastrando Previsão de Satélite | metodo POST | http://localhost:8080/api/agro/satelite
<img width="880" height="557" alt="teste1postA" src="https://github.com/user-attachments/assets/6650cd6c-2487-4972-b92f-fbe5a413d60a" />
<img width="880" height="285" alt="teste1postB" src="https://github.com/user-attachments/assets/8ed48789-b6b0-47c2-a104-684a78823457" />

## Enviando Leitura do Sensor / Inteligência de Rega | metodo POST | http://localhost:8080/api/agro/solo
<img width="893" height="585" alt="teste2postA" src="https://github.com/user-attachments/assets/f08544fa-1ca1-4c8c-a606-fcf0cfd51a51" />
<img width="877" height="242" alt="teste2postB" src="https://github.com/user-attachments/assets/819838c6-6ca3-4946-b23a-4312ab3abac9" />

## Alterando Registro telemétrico por ID | Método PUT | http://localhost:8080/api/agro/solo/[ID]
<img width="901" height="585" alt="teste4putA" src="https://github.com/user-attachments/assets/c0e6a20f-40db-407e-a2d2-399788417c01" />
<img width="864" height="243" alt="teste4putB" src="https://github.com/user-attachments/assets/4e9b7f32-412a-4852-bc2c-8d0446570743" />

## Listando Histórico de Telemetria Geral | Método GET | http://localhost:8080/api/agro/solo
<img width="897" height="874" alt="teste3get" src="https://github.com/user-attachments/assets/83c1dd9f-6713-4d00-990d-aa5a40982b1d" />

## Deletando pelo ID | Metodo DELETE | http://localhost:8080/api/agro/solo/[ID]
<img width="902" height="678" alt="teste5delete" src="https://github.com/user-attachments/assets/2c0c9b78-32ed-452a-ab4e-9a41122ac863" />

# Documentação da API (Swagger)
A API possui documentação automatizada, viva e auto-executável gerada através do Springdoc OpenAPI (Swagger).

## Como acessar:
Com a aplicação em execução localmente no seu computador, abra o navegador e acesse a URL: http://localhost:8080/swagger-ui.html

<img width="1419" height="764" alt="Swagger" src="https://github.com/user-attachments/assets/826ed1cd-a711-44b5-a934-4371e47823d8" />

# Modelo UML
Abaixo encontra-se renderizado o diagrama de classes completo que mapeia a arquitetura lógica estruturada para o braço Java da aplicação, detalhando os relacionamentos e fluxos de dados entre Controladores, Serviços, DTOs e Repositórios baseados nas especificações.

```mermaid
classDiagram
    direction TB

    class AgrotechApplication {
        +main(args: String[]) void
    }

    class AgroController {
        -inteligenciaService: AgroInteligenciaService
        -sateliteRepository: PrevisaoSateliteRepository
        +criarLeituraSolo(dto: RegistroSoloDto) ResponseEntity~String~
        +obterTodasLeiturasSolo() ResponseEntity~List~RegistroSolo~~
        +obterLeituraSoloPorId(id: Long) ResponseEntity~RegistroSolo~
        +modificarLeituraSolo(id: Long, dto: RegistroSoloDto) ResponseEntity~RegistroSolo~
        +removerLeituraSolo(id: Long) ResponseEntity~Void~
        +criarPrevisao(dto: PrevisaoSateliteDto) ResponseEntity~PrevisaoSatelite~
        +obterTodasPrevisoes() ResponseEntity~List~PrevisaoSatelite~~
    }

    class AgroInteligenciaService {
        -soloRepository: RegistroSoloRepository
        -sateliteRepository: PrevisaoSateliteRepository
        +salvarRegistroSolo(dto: RegistroSoloDto) String
        +listarTodosRegistrosSolo() List~RegistroSolo~
        +buscarRegistroSoloPorId(id: Long) Optional~RegistroSolo~
        +atualizarRegistroSolo(id: Long, dto: RegistroSoloDto) Optional~RegistroSolo~
        +deletarRegistroSolo(id: Long) boolean
        +salvarPrevisao(dto: PrevisaoSateliteDto) PrevisaoSatelite
        +listarTodasPrevisoes() List~PrevisaoSatelite~
    }

    class RegistroSoloRepository {
        <<interface>>
    }

    class PrevisaoSateliteRepository {
        <<interface>>
        +findFirstByRegiaoOrderByDataPrevisaoDesc(regiao: String) Optional~PrevisaoSatelite~
    }

    class JpaRepository {
        <<interface>>
        +save(entity)
        +findAll()
        +findById(id)
        +deleteById(id)
        +existsById(id) boolean
    }

    class RegistroSolo {
        -id: Long
        -umidade: Double
        -temperatura: Double
        -dataLeitura: LocalDateTime
        -dispositivoId: String
    }

    class PrevisaoSatelite {
        -id: Long
        -regiao: String
        -chuvaIminente: Boolean
        -dataPrevisao: LocalDate
    }

    class RegistroSoloDto {
        -umidade: Double
        -temperatura: Double
        -dispositivoId: String
    }

    class PrevisaoSateliteDto {
        -regiao: String
        -chuvaIminente: Boolean
    }

    %% Relacionamentos e Dependências
    AgroController --> AgroInteligenciaService : usa
    AgroController --> PrevisaoSateliteRepository : usa
    AgroInteligenciaService --> RegistroSoloRepository : usa
    AgroInteligenciaService --> PrevisaoSateliteRepository : usa
    RegistroSoloRepository --|> JpaRepository : extends
    PrevisaoSateliteRepository --|> JpaRepository : extends
    AgroInteligenciaService ..> RegistroSoloDto : consome
    AgroInteligenciaService ..> PrevisaoSateliteDto : consome
    RegistroSoloRepository ..> RegistroSolo : gerencia
    PrevisaoSateliteRepository ..> PrevisaoSatelite : gerencia















