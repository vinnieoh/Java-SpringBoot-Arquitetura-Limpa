# Java SpringBoot Arquitetura Limpa: 💰 Desafio PicPay Simplificado - Backend

Este projeto é uma implementação do [desafio backend do PicPay](https://github.com/PicPay/picpay-desafio-backend), construída com **Java 21** e **Spring Boot**, seguindo a **Arquitetura Limpa** (_Clean Architecture_).  

## 🏗️ **Sobre o Projeto**
A API permite **transferências financeiras** entre usuários, garantindo:
✔ **Autenticação JWT** para segurança  
✔ **Validação de saldo antes da transação**  
✔ **Autorização de transações via serviço externo**  
✔ **Notificações de pagamento ao recebedor**  
✔ **Modelo transacional** para evitar inconsistências  

Este projeto segue a **Arquitetura Limpa**, garantindo:
- **Separação de responsabilidades** entre camadas (`domain`, `application`, `infrastructure`, `adapters`)
- **Código modular e testável**, facilitando manutenção e evolução
- **Baixo acoplamento**, permitindo troca de tecnologias sem afetar a lógica de negócio

---

## 🏗️ **Tecnologias Utilizadas**
- **Java 21** com **Spring Boot**
- **Arquitetura Limpa** (_Clean Architecture_)
- **Spring Security** (Autenticação e JWT)
- **Spring Data JPA** (Persistência)
- **PostgreSQL** (Banco de Dados)
- **Docker & Docker Compose** (Containerização)
- **Feign Client** (Integração com serviços externos)
- **Swagger** (Documentação da API)

---

## 🚀 **Como Executar o Projeto**
### 📌 **1. Clonar o Repositório**
```bash
git clone https://github.com/seu-repositorio/picpay-backend.git
cd picpay-backend
```

### 📌 **2. Gerar as Chaves JWT**
A aplicação utiliza autenticação JWT com **chaves pública e privada**.  
Para gerar essas chaves, basta rodar o seguinte script:

```bash
chmod +x ./scripts/generate-ssh-keys.sh
./scripts/generate-ssh-keys.sh
```

Isso criará as chaves na pasta `./keys/`.

### 📌 **3. Criar o Arquivo `.env`**
Crie um arquivo `.env` na raiz do projeto e configure as variáveis de ambiente:

```env
# DATABASE
DB_HOST=localhost
DB_PORT=5432
DB_NAME=java
DB_USERNAME=postgres
DB_PASSWORD=root12345

# SERVER
SERVER_PORT=8080
```

### 📌 **4. Subir a API e o Banco com Docker**
O `docker-compose` já contém **API** + **Banco de Dados**.  
Para rodar tudo, basta usar:

```bash
docker-compose up -d
```

Isso iniciará:
- PostgreSQL na porta **5432**
- API Spring Boot na porta **8080**

### 📌 **5. Rodar Manualmente (se necessário)**
Caso não use Docker, a API pode ser iniciada manualmente com:

```bash
./mvnw spring-boot:run
```
A API estará disponível em **`http://localhost:8080`**.

---

## 📌 **Usuários de Teste**
Para facilitar os testes, aqui estão alguns usuários pré-cadastrados:

### ✅ **Usuários COMUNS (podem enviar dinheiro)**
```json
[
  {
    "fullName": "João Silva",
    "document": "123.456.789-09",
    "email": "joao.silva@example.com",
    "password": "senha123",
    "userType": "COMUM"
  },
  {
    "fullName": "Maria Oliveira",
    "document": "529.982.247-25",
    "email": "maria.oliveira@example.com",
    "password": "senha123",
    "userType": "COMUM"
  },
  {
    "fullName": "Carlos Santos",
    "document": "111.444.777-35",
    "email": "carlos.santos@example.com",
    "password": "senha123",
    "userType": "COMUM"
  },
]
```
### ✅ **Usuários LOGISTA (não podem enviar dinheiro)**
```json
[
  {
    "fullName": "Loja Exemplo 1",
    "document": "12.345.678/0001-95",
    "email": "loja1@example.com",
    "password": "senha123",
    "userType": "LOGISTA"
  },
  {
    "fullName": "Supermercado Exemplo 2",
    "document": "04.252.011/0001-10",
    "email": "supermercado2@example.com",
    "password": "senha123",
    "userType": "LOGISTA"
  },
  {
    "fullName": "Empresa Exemplo 3",
    "document": "20.392.287/0001-80",
    "email": "empresa3@example.com",
    "password": "senha123",
    "userType": "LOGISTA"
  }
]



```

---

## 📌 **Endpoints da API**
A API segue os padrões REST e os endpoints principais são:

### 🔐 **1. Autenticação**
**Rota:** `POST /api/v1/auth/login`  
**Corpo da requisição:**
```json
{
  "email": "joao.silva@example.com",
  "password": "senha123"
}
```
**Resposta:**
```json
{
  "token": "eyJhbGciOi...",
  "expiresIn": 86400
}
```

---

### 👤 **2. Criar Usuário**
**Rota:** `POST /api/v1/users`  
**Corpo da requisição:**
```json
{
  "fullName": "Novo Usuário",
  "document": "987.654.321-00",
  "email": "novo.usuario@example.com",
  "password": "novaSenha123",
  "userType": "COMUM"
}
```
**Resposta:** `201 Created`

---

### 💰 **3. Consultar Saldo da Carteira**
**Rota:** `GET /api/v1/wallet`  
**Headers:**
```http
Authorization: Bearer SEU_TOKEN_JWT
```
**Resposta:**
```json
{
  "walletId": "123e4567-e89b-12d3-a456-426614174000",
  "userId": "e2f2b256-1c3e-4d78-840e-c128abe918bf",
  "balance": 1000.00
}
```

---

### 📥 **4. Depositar Dinheiro na Carteira**
**Rota:** `POST /api/v1/wallet/deposit?amount=500`  
**Headers:**
```http
Authorization: Bearer SEU_TOKEN_JWT
```
**Resposta:**
```
Depósito de R$ 500 realizado com sucesso!
```

---

### 📤 **5. Transferir Dinheiro**
**Rota:** `POST /api/v1/transfer`  
**Headers:**
```http
Authorization: Bearer SEU_TOKEN_JWT
```
**Corpo da requisição:**
```json
{
  "value": 100.0,
  "payeeDocument": "529.982.247-25"
}
```
**Resposta:** `200 OK`
```
Transferência de R$ 100 para Maria Oliveira realizada com sucesso!
```

---

## 📌 **Regras de Negócio**
✅ Usuários **COMUNS** podem enviar dinheiro.  
❌ Usuários **LOJISTAS** **NÃO podem** enviar dinheiro.  
✅ Antes da transferência, verificamos se há **saldo suficiente**.  
✅ A transferência **só acontece se o serviço externo autorizar**.  
✅ Após a transferência, o **recebedor recebe uma notificação**.  
✅ Se houver erro, o dinheiro é **estornado automaticamente**.

---

## 📌 **Ferramentas Úteis**
### 🔍 **Testar API com cURL**
```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
     -H "Content-Type: application/json" \
     -d '{"email": "joao.silva@example.com", "password": "senha123"}'
```

### 📄 **Acessar Swagger**
```bash
http://localhost:8080/swagger-ui.html
```

---

## 📜 **Licença**
Este projeto está licenciado sob a **MIT License**.

Para mais detalhes, veja o arquivo [LICENSE](./LICENSE).
