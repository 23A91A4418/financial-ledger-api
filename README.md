 Financial Ledger API — Double Entry Accounting System
A production-grade financial ledger API implementing double-entry bookkeeping, immutable transactions, accurate balance calculation, and safe concurrent transfers using Spring Boot + PostgreSQL.
________________________________________
🚀 Features Implemented
✔ Create accounts
✔ Credit & debit transactions
✔ Atomic money transfers (debit + credit)
✔ Immutable ledger entries
✔ Accurate balance computed from ledger
✔ Full transaction history per account
✔ Swagger Documentation
✔ PostgreSQL persistence
✔ Transactional integrity (@Transactional)
________________________________________
📁 Project Folder Structure
financial-ledger-api/
├── src/main/java/com/ledger/
│   ├── controller/
│   ├── service/
│   ├── repository/
│   ├── model/
│   ├── dto/
│   ├── exception/
│   └── FinancialLedgerApiApplication.java
├── src/main/resources/
│   ├── application.properties
├── pom.xml
________________________________________
🛠 Tech Stack
•	Java 17
•	Spring Boot 3
•	Spring Data JPA
•	PostgreSQL
•	Lombok
•	Swagger (SpringDoc OpenAPI)
________________________________________
🔧 API Endpoints Overview
Method	Endpoint	Description
POST	/accounts	Create a new account
GET	/accounts	Get all accounts
GET	/accounts/{id}/balance	Get computed balance
GET	/accounts/{id}/transactions	Get account history
POST	/transactions	Create debit/credit
POST	/transactions/transfer	Transfer between accounts
GET	/transactions	Get all transactions
GET	/ledger	Ledger entries (immutable)
 

________________________________________
📌 How the Ledger System Works
✅ Double Entry Bookkeeping
Every transaction creates ledger entries:
•	Credit transaction → credit amount
•	Debit transaction → debit amount
✅ Ledger Is Immutable
Once written, entries are never edited or deleted
 .
________________________________________
🧪 API Usage Examples 
1️⃣ Create Account
Request
POST /accounts
{
  "name": "Account A",
  "balance": 0
}
 ________________________________________
2️⃣ Credit Transaction
Request
POST /transactions
{
  "accountId": 1,
  "amount": 1000,
  "type": "credit"
}
 ________________________________________
3️⃣ Debit Transaction
Request
POST /transactions
{
  "accountId": 1,
  "amount": 200,
  "type": "debit"
}
 ________________________________________
4️⃣ Check Balance
Request
GET /accounts/1/balance
 ________________________________________
5️⃣ Money Transfer
Request
POST /transactions/transfer
{
  "fromAccountId": 1,
  "toAccountId": 2,
  "amount": 100
}
 ________________________________________
6️⃣ Transaction History
GET /accounts/1/transactions
 ________________________________________
Diagrams
📌 1. High-Level Architecture
+-------------------+         +------------------------+        +---------------------+
|                   | HTTP    |                        |        |                     |
|     CLIENT        +-------->+   SPRING CONTROLLERS   +------->+     SERVICES        |
| (Postman / Curl)  | Request | (Account, Transaction) | Calls  | (Business Logic)    |
|                   |         |                        |        |                     |
+-------------------+         +-----------+------------+        +----------+----------+
                                            |                              |
                                            | JPA Repositories             |
                                            v                              v
                                 +---------------------+        +----------------------+
                                 | AccountRepository   |        | LedgerRepository     |
                                 | TransactionRepo     |        | TransactionRepo      |
                                 +---------------------+        +----------------------+
                                             \                          /
                                              \                        /
                                               \                      /
                                                \                    /
                                                 v                  v
                                          +--------------------------------+
                                          |          POSTGRESQL DB         |
                                          | Accounts | Transactions | Ledger |
                                          +--------------------------------+





📌 2. Data Flow (Credit / Debit)
User           Controller             Service                Repositories          DB
 | POST /transfer   |                     |                      |
 |------------------>|                     |                      |
 |                   | validate request    |                      |
 |                   |-------------------->|                      |
 |                   |                     | find sender account |
 |                   |                     |--------------------->|
 |                   |                    |<---------------------|
 |                   |                   	| check balance        |
 |                   |                   	| find receiver acct   |
 |                   |                   	|---------------------->|
 |                   |                   	|<----------------------|
 |                   |                   	| update balances      |
 |                   |                   	| save ledger entries  |
 |                   |<--------------------|                      |
 |   Transfer OK     |                     |                      |
📌 3. Double-Entry Ledger Diagram
| LedgerEntry ID | Account | Debit | Credit | Transaction ID |
|----------------|---------|--------|--------|----------------|
| 1              | 1       | 0      | 500    | 10             |
| 2              | 1       | 200    | 0      | 11             |
| 3 (Sender)     | 1       | 100    | 0      | 12             |
| 4 (Receiver)   | 2       | 0      | 100    | 12             |
________________________________________
Expected Outcomes 
✔ Fully functional REST API
✔ Atomic transfers
✔ Immutable ledger
✔ Safe concurrency via @Transactional
✔ Negative balances prevented
✔ Balance derived from ledger entries
✔ Complete history available
✔ Production-ready structure
________________________________________
▶️ How to Run
1. Start PostgreSQL
Create database:
CREATE DATABASE ledger;
2. Update application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/ledger
spring.datasource.username=postgres
spring.datasource.password=admin123
spring.jpa.hibernate.ddl-auto=update
3. Run the project
mvn spring-boot:run
4. Open Swagger
http://localhost:8080/swagger-ui/index.html
________________________________________
📘 Conclusion
This project demonstrates:
•	Financial-grade ledger design
•	Strong transactional consistency
•	Clean and scalable architecture
•	Correct application of double-entry bookkeeping

