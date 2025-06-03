# 🃏 Blackjack API

A Reactive Spring Boot WebFlux API for playing Blackjack, featuring a fully functional game logic, connection with MongoDB and MySQL, and Swagger documentation.
Level 1 includes: player management, game creation, play actions, global error handling, ranking, and more!

<details>
<summary>🚀 Endpoints (Click to expand)</summary>

| Method | Endpoint              | Description                 |
| ------ | --------------------- | --------------------------- |
| POST   | /game/new             | Create a new Blackjack game |
| GET    | /game/{id}            | Get game details by ID      |
| POST   | /game/{id}/play/hit   | Player hits (requests card) |
| POST   | /game/{id}/play/stand | Player stands               |
| DELETE | /game/{id}            | Delete a game               |
| GET    | /game/ranking         | Get player ranking          |
| GET    | /players              | List all players            |
| POST   | /players              | Create a new player         |
| GET    | /players/{id}         | Get player by ID            |
| PUT    | /players/{id}         | Update player               |
| DELETE | /players/{id}         | Delete player               |

</details>

## 📖 Game Flow

## 🔒 Error Handling

Global exception handler (GlobalExceptionHandler) for clear responses:

* Player or game not found.
* Invalid actions or bets.
* Validation errors.
* Internal errors.

## 📊 Swagger UI

📍 Once the containers are running, access the API documentation at:

```
http://localhost:8080/swagger-ui.html
```

## 🏅 Player Ranking

## 🧰 Tech Stack

* 🌐 Spring Boot WebFlux
* 🗃 MongoDB (Reactive) for games
* 💾 MySQL (R2DBC) for players
* 🔎 Swagger / OpenAPI for documentation
* 🧪 JUnit & Mockito for tests
* 🐘 Maven for build management

## 🐳 Docker Setup

### 🔥 Run with Docker Compose

Clone the repository and in the root directory run:

```bash
docker-compose up
```
This will start:

MySQL (on port 3307)

MongoDB (on port 27017)

Blackjack API (on port 8080)

The Blackjack API will be available at http://localhost:8080, and the Swagger UI at http://localhost:8080/swagger-ui.html.

<details>
<summary>⚙️  Docker Compose Environment Variables (Click to expand)</summary>

| Variable                     | Value                                                                                                                              |
| ---------------------------- | ---------------------------------------------------------------------------------------------------------------------------------- |
| `SPRING_R2DBC_URL`           | `r2dbc:mysql://root:@mysql:3306/blackjack_db`                                                                                      |
| `SPRING_R2DBC_USERNAME`      | `root`                                                                                                                             |
| `SPRING_R2DBC_PASSWORD`      | *""*                                                                                                                               |
| `MYSQL_ALLOW_EMPTY_PASSWORD` | `yes`                                                                                                                              |
| `MYSQL_DATABASE`             | `blackjack_db`                                                                                                                     |
| `SPRING_DATA_MONGODB_URI`    | `mongodb://mongo:27017/blackjack_db` |
</details>
### 🛠 Manual Build and Run (Optional)
To build and run the API locally (without Docker Compose):

`docker build -t blackjack-api:latest .`

`docker run -p 8080:8080 blackjack-api:latest`

## 🔐 Using a `.env` File for Local Environment Variables

If you cloned this repository and want to run the application **locally without Docker**, you can use a `.env` file to provide the required environment variables.

### 📝 Steps to Set Up a `.env` File

1️⃣ Create a `.env` file in the project root directory with the following content
<details> <summary>⚙️ Environment Variables .env (Click to expand)</summary>
  
`SPRING_R2DBC_URL=r2dbc:mysql://localhost:3306/blackjack_db`
  
`SPRING_R2DBC_USERNAME=your_user`

`SPRING_R2DBC_PASSWORD=your_password`

`SPRING_DATA_MONGODB_URI=mongodb://localhost:27017/blackjack_db`

Replace your_user with your actual MySQL user your_password with your actual MySQL password (or leave it "" if you have no password).

</details>

2️⃣ Spring Boot reads environment variables but does not automatically load .env files. You need to load it manually before running the app.

<details> <summary>🚀 Load the .env and Run Locally</summary>

For Linux/MacOS:

`export $(grep -v '^#' .env | xargs)`

`./mvnw spring-boot:run`

For Windows (PowerShell):

`Get-Content .env | ForEach-Object {
  if ($_ -match "^\s*([^=]+)\s*=\s*(.*)\s*$") {
    [System.Environment]::SetEnvironmentVariable($matches[1], $matches[2])
  }
}`

`./mvnw spring-boot:run`

This will load the environment variables and run the application with the correct database connections and configuration.
</details>

## ⚠️ Note
This method is only for local development without Docker.

Make sure your MySQL and MongoDB services are running locally with the specified ports and credentials.

## 📢 Contributors

👤 Alfonso Cocinas
🔗 [GitHub Profile](https://github.com/acocinas)

**Happy Coding!** 🚀

---

## 📄 License

This project is for educational purposes only. No commercial license is applied.
