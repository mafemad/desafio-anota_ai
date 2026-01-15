# 🛒 Desafio Anota AI - Catalog API

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk" alt="Java">
  <img src="https://img.shields.io/badge/Spring_Boot-3.5.7-green?style=for-the-badge&logo=spring" alt="Spring Boot">
  <img src="https://img.shields.io/badge/MongoDB-Database-47A248?style=for-the-badge&logo=mongodb" alt="MongoDB">
  <img src="https://img.shields.io/badge/AWS-SNS%20|%20SQS%20|%20Lambda%20|%20S3-FF9900?style=for-the-badge&logo=amazonaws" alt="AWS">
</p>

## 📋 Sobre o Projeto

API REST desenvolvida em **Spring Boot** para gerenciamento de catálogo de produtos e categorias, inspirada no desafio técnico da **Anota AI**. A aplicação implementa uma arquitetura orientada a eventos utilizando serviços da AWS para processamento assíncrono e armazenamento.

### 🎯 Objetivo

O sistema permite que donos de estabelecimentos (owners) gerenciem seus produtos e categorias de forma simples e eficiente. Toda alteração no catálogo é publicada em um tópico SNS, processada por uma fila SQS, consumida por uma função Lambda e finalmente armazenada em um bucket S3, garantindo um catálogo sempre atualizado e de fácil acesso.

## 🏗️ Arquitetura

```
┌─────────────────┐     ┌─────────────┐     ┌─────────────┐     ┌─────────────┐     ┌─────────────┐
│   Spring Boot   │────▶│   AWS SNS   │────▶│   AWS SQS   │────▶│ AWS Lambda  │────▶│   AWS S3    │
│   (API REST)    │     │   (Topic)   │     │   (Queue)   │     │  (Consumer) │     │  (Storage)  │
└─────────────────┘     └─────────────┘     └─────────────┘     └─────────────┘     └─────────────┘
        │
        ▼
┌─────────────────┐
│    MongoDB      │
│   (Database)    │
└─────────────────┘
```

### Fluxo de Dados

1. **API REST (Spring Boot)**: Recebe requisições HTTP para CRUD de produtos e categorias
2. **MongoDB**: Armazena os dados de forma persistente
3. **AWS SNS**: Recebe mensagens de eventos (criação, atualização, exclusão)
4. **AWS SQS**: Enfileira as mensagens para processamento assíncrono
5. **AWS Lambda**: Consome as mensagens da fila e processa os dados
6. **AWS S3**: Armazena o catálogo atualizado como arquivo JSON

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Versão | Descrição |
|------------|--------|-----------|
| **Java** | 21 | Linguagem de programação |
| **Spring Boot** | 3.5.7 | Framework principal |
| **Spring Data MongoDB** | - | Integração com MongoDB |
| **Spring Validation** | - | Validação de dados |
| **AWS SDK** | 2.41.7 | SDK para integração com AWS |
| **Lombok** | - | Redução de boilerplate |
| **MongoDB** | - | Banco de dados NoSQL |

## 📦 Requisitos

### Pré-requisitos

- **Java 21** ou superior
- **Maven 3.8+**
- **MongoDB** (local ou Atlas)
- **Conta AWS** com os seguintes serviços configurados:
  - SNS (Simple Notification Service)
  - SQS (Simple Queue Service)
  - Lambda
  - S3 (Simple Storage Service)

### Variáveis de Ambiente

Configure as seguintes variáveis de ambiente antes de executar a aplicação:

| Variável | Descrição | Exemplo |
|----------|-----------|---------|
| `AWS_ACCESS_KEY_ID` | Access Key da AWS | `AKIAIOSFODNN7EXAMPLE` |
| `AWS_SECRET_ACCESS_KEY` | Secret Key da AWS | `wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY` |
| `AWS_REGION` | Região da AWS | `us-east-2` |
| `AWS_SNS_TOPIC_ARN` | ARN do tópico SNS | `arn:aws:sns:us-east-2:123456789:catalog-events` |

## 🚀 Como Executar

### 1. Clone o repositório

```bash
git clone https://github.com/seu-usuario/desafio-anota-ai.git
cd desafio-anota-ai
```

### 2. Configure as variáveis de ambiente

**Windows (PowerShell):**
```powershell
$env:AWS_ACCESS_KEY_ID="sua-access-key"
$env:AWS_SECRET_ACCESS_KEY="sua-secret-key"
$env:AWS_REGION="us-east-2"
$env:AWS_SNS_TOPIC_ARN="arn:aws:sns:us-east-2:123456789:catalog-events"
```

**Linux/Mac:**
```bash
export AWS_ACCESS_KEY_ID="sua-access-key"
export AWS_SECRET_ACCESS_KEY="sua-secret-key"
export AWS_REGION="us-east-2"
export AWS_SNS_TOPIC_ARN="arn:aws:sns:us-east-2:123456789:catalog-events"
```

### 3. Configure o MongoDB

Certifique-se de que o MongoDB está rodando localmente na porta padrão (27017) ou configure a connection string no `application.properties`.

### 4. Execute a aplicação

**Usando Maven Wrapper:**
```bash
./mvnw spring-boot:run
```

**Windows:**
```cmd
mvnw.cmd spring-boot:run
```

**Usando Maven instalado:**
```bash
mvn spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

## 📍 Mapeamento de Rotas

### 📁 Categories

| Método | Endpoint | Descrição | Request Body | Response |
|--------|----------|-----------|--------------|----------|
| `POST` | `/api/categories` | Criar categoria | `CreateCategoryDTO` | `201 Created` |
| `GET` | `/api/categories` | Listar todas | - | `200 OK` |
| `GET` | `/api/categories/{id}` | Buscar por ID | - | `200 OK` |
| `PATCH` | `/api/categories/{id}` | Atualizar | `UpdateCategoryDTO` | `200 OK` |
| `DELETE` | `/api/categories/{id}` | Deletar | - | `204 No Content` |

#### Criar Categoria
```http
POST /api/categories
Content-Type: application/json

{
  "title": "Eletrônicos",
  "description": "Produtos eletrônicos em geral",
  "ownerId": "owner-123"
}
```

**Response (201 Created):**
```json
{
  "id": "64f8a1b2c3d4e5f6a7b8c9d0",
  "title": "Eletrônicos",
  "description": "Produtos eletrônicos em geral",
  "ownerId": "owner-123"
}
```

#### Atualizar Categoria
```http
PATCH /api/categories/{id}
Content-Type: application/json

{
  "title": "Eletrônicos e Acessórios",
  "description": "Nova descrição"
}
```

---

### 📦 Products

| Método | Endpoint | Descrição | Request Body | Response |
|--------|----------|-----------|--------------|----------|
| `POST` | `/api/products` | Criar produto | `CreateProductDTO` | `201 Created` |
| `GET` | `/api/products` | Listar todos | - | `200 OK` |
| `GET` | `/api/products/{id}` | Buscar por ID | - | `200 OK` |
| `PATCH` | `/api/products/{id}` | Atualizar | `UpdateProductDTO` | `200 OK` |
| `DELETE` | `/api/products/{id}` | Deletar | - | `204 No Content` |

#### Criar Produto
```http
POST /api/products
Content-Type: application/json

{
  "title": "Smartphone Samsung",
  "description": "Galaxy S24 Ultra 256GB",
  "price": 5999,
  "ownerId": "owner-123",
  "categoryId": "64f8a1b2c3d4e5f6a7b8c9d0"
}
```

**Response (201 Created):**
```json
{
  "id": "64f8b2c3d4e5f6a7b8c9d0e1",
  "title": "Smartphone Samsung",
  "description": "Galaxy S24 Ultra 256GB",
  "price": 5999,
  "ownerId": "owner-123",
  "category": "64f8a1b2c3d4e5f6a7b8c9d0"
}
```

#### Atualizar Produto
```http
PATCH /api/products/{id}
Content-Type: application/json

{
  "title": "Smartphone Samsung Galaxy",
  "description": "Nova descrição",
  "price": 5499,
  "categoryId": "64f8a1b2c3d4e5f6a7b8c9d0"
}
```

---

## 📝 DTOs (Data Transfer Objects)

### CreateCategoryDTO
| Campo | Tipo | Obrigatório | Validação |
|-------|------|-------------|-----------|
| `title` | String | ✅ | @NotBlank |
| `description` | String | ✅ | @NotBlank |
| `ownerId` | String | ✅ | @NotBlank |

### UpdateCategoryDTO
| Campo | Tipo | Obrigatório |
|-------|------|-------------|
| `title` | String | ❌ |
| `description` | String | ❌ |

### CreateProductDTO
| Campo | Tipo | Obrigatório | Validação |
|-------|------|-------------|-----------|
| `title` | String | ✅ | @NotBlank |
| `description` | String | ✅ | @NotBlank |
| `price` | Integer | ✅ | @Min(1) |
| `ownerId` | String | ✅ | @NotBlank |
| `categoryId` | String | ✅ | @NotBlank |

### UpdateProductDTO
| Campo | Tipo | Obrigatório |
|-------|------|-------------|
| `title` | String | ❌ |
| `description` | String | ❌ |
| `price` | Integer | ❌ |
| `categoryId` | String | ❌ |

---

## ☁️ Configuração AWS

### 1. SNS (Simple Notification Service)

Crie um tópico SNS para receber os eventos do catálogo:

```bash
aws sns create-topic --name catalog-events
```

### 2. SQS (Simple Queue Service)

Crie uma fila SQS e inscreva-a no tópico SNS:

```bash
# Criar fila
aws sqs create-queue --queue-name catalog-queue

# Inscrever a fila no tópico SNS
aws sns subscribe \
  --topic-arn arn:aws:sns:us-east-2:123456789:catalog-events \
  --protocol sqs \
  --notification-endpoint arn:aws:sqs:us-east-2:123456789:catalog-queue
```

### 3. Lambda

Crie uma função Lambda que será acionada pela fila SQS para processar os eventos e atualizar o S3.

### 4. S3 (Simple Storage Service)

Crie um bucket S3 para armazenar o catálogo em formato JSON:

```bash
aws s3 mb s3://catalog-bucket-anota-ai
```

---

## 📊 Tipos de Eventos SNS

Quando um produto ou categoria é criado/atualizado/deletado, uma mensagem é publicada no SNS com o seguinte formato:

### Evento de Categoria
```json
{
  "id": "64f8a1b2c3d4e5f6a7b8c9d0",
  "title": "Eletrônicos",
  "description": "Produtos eletrônicos",
  "ownerId": "owner-123",
  "type": "category"
}
```

### Evento de Produto
```json
{
  "id": "64f8b2c3d4e5f6a7b8c9d0e1",
  "title": "Smartphone",
  "description": "Galaxy S24",
  "price": 5999,
  "ownerId": "owner-123",
  "categoryId": "64f8a1b2c3d4e5f6a7b8c9d0",
  "type": "product"
}
```

### Evento de Deleção
```json
{
  "id": "64f8a1b2c3d4e5f6a7b8c9d0",
  "type": "delete-category"
}
```

```json
{
  "id": "64f8b2c3d4e5f6a7b8c9d0e1",
  "type": "delete-product"
}
```

---

## 📁 Estrutura do Projeto

```
src/main/java/com/mateus/desafioanotaai/
├── DesafioAnotaAiApplication.java    # Classe principal
├── config/
│   ├── aws/
│   │   └── AwsSnsConfig.java         # Configuração do AWS SNS
│   └── mongo/
│       └── MongoDBConfig.java        # Configuração do MongoDB
├── controller/
│   ├── CategoryController.java       # Endpoints de categorias
│   └── ProductController.java        # Endpoints de produtos
├── domain/
│   ├── category/
│   │   ├── Category.java             # Entidade de categoria
│   │   ├── dto/
│   │   │   ├── CategoryResponseDTO.java
│   │   │   ├── CreateCategoryDTO.java
│   │   │   └── UpdateCategoryDTO.java
│   │   └── exceptions/
│   │       └── CategoryNotFoundException.java
│   └── product/
│       ├── Product.java              # Entidade de produto
│       ├── dto/
│       │   ├── CreateProductDTO.java
│       │   ├── ProductResponseDTO.java
│       │   └── UpdateProductDTO.java
│       └── exceptions/
│           └── ProductNotFoundException.java
├── repositories/
│   ├── CategoryRepository.java       # Repository de categorias
│   └── ProductRepository.java        # Repository de produtos
└── Service/
    ├── CategoryService.java          # Lógica de negócio de categorias
    ├── ProductService.java           # Lógica de negócio de produtos
    └── aws/
        ├── AwsSnsService.java        # Serviço de publicação no SNS
        └── MessageDTO.java           # DTO de mensagem SNS
```

---

## 🧪 Testes

Para executar os testes:

```bash
./mvnw test
```

---

## 🤝 Contribuindo

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/nova-feature`)
3. Commit suas mudanças (`git commit -m 'Adiciona nova feature'`)
4. Push para a branch (`git push origin feature/nova-feature`)
5. Abra um Pull Request

---

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

## 👨‍💻 Autor

Desenvolvido por **Mateus Madeira** como parte do desafio técnico Anota AI.

---
