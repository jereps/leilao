# Sistema de Leilão - YouTan

Este é um sistema de leilão, A aplicação gerencia leilões de itens polimórficos (Veículos e Imóveis), integrando segurança, histórico de lances .

## 🚀 Tecnologias Utilizadas

- **Java 25**: Utilizando as versões mais recentes do ecossistema Java.
- **Spring Boot 4**: Framework base para a construção da API.
- **Maven 3.9.14**: Gerenciador de dependências e automação de build.
- **PostgreSQL 15**: Banco de dados relacional.
- **Flyway**: Gerenciamento de migrações e versionamento de banco de dados.
- **Docker & Docker Compose**: Containerização completa da aplicação e do banco de dados.
- **Hibernate (JPA)**: Mapeamento objeto-relacional com suporte a tipos polimórficos (`@ManyToAny`).
- **Spring Security**: Controle de acesso baseado em Roles.

## 🛠️ Configuração do Banco de Dados

A aplicação utiliza o PostgreSQL dentro de um container Docker. Os dados de conexão padrão definidos no `docker-compose.yml` são:

- **Database**: `mydatabase`
- **User**: `postgres`
- **Password**: `dev_password`
- **Porta**: `5432`

## 👥 Usuários Padrão (Seed)

Ao rodar as migrações iniciais, o sistema é populado com os seguintes usuários para teste:

| Nome | E-mail | Senha | Permissão (Role) |
| :--- | :--- | :--- | :--- |
| **Admin** | `admin@gmail.com` | `admin` | `ROLE_ADMIN` |
| **User** | `user@gmail.com` | `user` | `ROLE_USER` |

## 🐳 Como Executar com Docker

Certifique-se de ter o **Docker** e o **Docker Compose** instalados em sua máquina.

### 1. Build da Imagem
Para compilar a aplicação (usando o estágio de build do Maven no Dockerfile) e criar as imagens:

docker compose up --build -d

### 2. Iniciar os Containers
docker compose up -d

### 3. Verificar Logs
docker compose logs -f app

### 4. Parar o Sistema
docker compose down
