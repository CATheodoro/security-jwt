﻿# security-jwt

### Sessão

[Introdução](#Introdução)

[Tecnologis utilizadas](#tecnologis-utilizadas)

[Requisitos](#requisitos)

[Configuração do Ambiente](#configuração-do-ambiente)

[Endpoints](#endpoints)

[Conclusão](#conclusão)

﻿# Introdução
O aplicativo de segurança é uma plataforma desenvolvida para gerenciar autenticação, autorização e controle de acesso em sistemas web. Ele oferece funcionalidades para registro de usuários, autenticação baseada em tokens JWT, controle de permissões de acesso e envio de e-mails de verificação.

﻿# Tecnologis Utilizadas
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

# Endpoints

UserController
|            Endpoint            | Método |                  Descrição                  |
|--------------------------------|--------|---------------------------------------------|
| /api/v1/auth/register          |  POST  | Registra um novo usuário no sistema.        |
| /api/v1/auth/authenticate	     |  POST  | Autentica no sistema retornando um Token.   |
| /api/v1/auth/refresh-token     |  POST  | Gera um novo Token.                         |
| /api/v1/auth/activate-account  |  GET   | Ativa a conta do usuário via E-mail.        |

UserController
|            Endpoint            | Método |                  Descrição                  |
|--------------------------------|--------|---------------------------------------------|
| /api/v1/user                   |  GET   | Retorna todos os Usuário cadastrados.       |

# Conclusão
Este guia fornece instruções claras sobre como configurar e usar o aplicativo de segurança. Siga as etapas fornecidas para começar a aproveitar todas as funcionalidades oferecidas pelo aplicativo.
