# Security-jwt

# Sessão

[Introdução](#introdução)

[Tecnologis utilizadas](#tecnologis-utilizadas)

[Requisitos](#requisitos)

[Configuração do Ambiente](#configuração-do-ambiente)

[Serviços Necessários](#serviços-necessários)

[Endpoints](#endpoints)

[Conclusão](#conclusão)

# Introdução
O aplicativo de segurança é uma plataforma desenvolvida para gerenciar autenticação, autorização e controle de acesso em sistemas web. Ele oferece funcionalidades para registro de usuários, autenticação baseada em tokens JWT, controle de permissões de acesso e envio de e-mails de verificação.

# Tecnologis Utilizadas
As principais tecnologias utilizadas incluem Spring Boot, Spring Security, Hibernate, Thymeleaf, e JavaMailSender.

# Requisitos
JDK 21
Docker
MySQL

# Configuração do Ambiente
1. Clone o repositório do aplicativo do GitHub:
```Git Clone
git clone https://github.com/theodoro/security-app.git
```

2. Entre no diretório em `security\src\main\resources\docker`, nele contem o arquivo `docker-compose.yml` no qual possui MySQL, PHPadmin, Mail-dev, abra o terminal e coloque o comando:
```Docker
docker-compose up --build
```

3. Compile o aplicativo usando o Maven:
```Maven
mvn clean install
```

4. Execute:
```Maven
java -jar target/security-app.jar
```

# Serviços Necessários

|  Serviço   |         Portas        |
|------------|-----------------------|
| PhpMyadmin | http://localhost:80   |
| MailDev    | http://localhost:1080 |

# Endpoints

RoleController
|            Endpoint            | Método |                  Descrição                  |
|--------------------------------|--------|---------------------------------------------|
| /api/v1/role                   |  GET   | Retorna todas as regras cadastradas.        |
| /api/v1/token/{id}             |  GET   | Retorna a regra do id informado.            |
| /api/v1/token                  |  POST  | Cadastra uma nova Regra na aplicação        |


AuthenticationController
|            Endpoint            | Método |                  Descrição                  |
|--------------------------------|--------|---------------------------------------------|
| /api/v1/auth/authenticate	     |  POST  | Autentica no sistema retornando um Token.   |
| /api/v1/auth/refresh-token     |  POST  | Gera um novo Token.                         |

AccountController
|            Endpoint            | Método |                  Descrição                  |
|--------------------------------|--------|---------------------------------------------|
| /api/v1/account/register       |  POST  | Registra um novo usuário no aplicação.      |
| /api/v1/account                |  GET   | Retorna todos os usuário cadastrados.       |
| /api/v1/account/{id}           |  GET   | Retorna o usuário do id informado.          |

MailController
|                    Endpoint                  | Método |                  Descrição                  |
|----------------------------------------------|--------|---------------------------------------------|
| /api/v1/mail/activate-account?token={token}  |  GET   | Ativa a conta do usuário via E-mail, usando MailDev([Documentação](https://github.com/maildev/maildev)) |
| /api/v1/mail/send-token-email                |  POST  | Reenvia o token de ativação para o E-mail, usando MailDev([Documentação](https://github.com/maildev/maildev)) |

TokenController
|            Endpoint            | Método |                  Descrição                  |
|--------------------------------|--------|---------------------------------------------|
| /api/v1/token                  |  GET  | Retorna todos os tokens cadastrados.        |
| /api/v1/token/{id}             |  GET   | Retorna o token do id informado.            |

# Conclusão
Este guia fornece instruções claras sobre como configurar e usar o aplicativo de segurança. Siga as etapas fornecidas para começar a aproveitar todas as funcionalidades oferecidas pelo aplicativo.
