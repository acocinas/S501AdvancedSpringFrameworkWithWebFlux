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

📍 Access the API via Swagger: `https://blackjack-api-latest.onrender.com/swagger-ui.html`
[Direct to Swagger](https://blackjack-api-latest.onrender.com/swagger-ui.html)

## 🏅 Player Ranking

## 🧰 Tech Stack

* 🌐 Spring Boot WebFlux
* 🗃 MongoDB (Reactive) for games
* 💾 MySQL (R2DBC) for players
* 🔎 Swagger / OpenAPI for documentation
* 🧪 JUnit & Mockito for tests
* 🐘 Maven for build management

## 🐳 Docker Image

Docker Hub Image URL:

```
docker.io/acocinas/blackjack-api:latest
```

To pull and run the image locally:

```bash
docker pull acocinas/blackjack-api:latest
docker run -p 8080:8080 acocinas/blackjack-api:latest
```

## 🌐 Render Deployment

Your app is live and accessible at:

```
https://blackjack-api-latest.onrender.com
```

Swagger UI available at:

```
https://blackjack-api-latest.onrender.com/swagger-ui.html
```

<details>
<summary>⚙️ Environment Variables (Click to expand)</summary>

| Variable                     | Value                                                                                                                              |
| ---------------------------- | ---------------------------------------------------------------------------------------------------------------------------------- |
| `SPRING_R2DBC_URL`           | `r2dbc:mysql://root:@mysql:3306/blackjack_db`                                                                                      |
| `SPRING_R2DBC_USERNAME`      | `root`                                                                                                                             |
| `SPRING_R2DBC_PASSWORD`      | *""*                                                                                                               |
| `MYSQL_ALLOW_EMPTY_PASSWORD` | `yes`                                                                                                                              |
| `MYSQL_DATABASE`             | `blackjack_db`                                                                                                                     |
| `SPRING_DATA_MONGODB_URI`    | `mongodb+srv://blackjackuser:blackjackpass@cluster0.efdbcf5.mongodb.net/blackjack_db?retryWrites=true&w=majority&appName=Cluster0` |
| `MONGO_URI`                  | `mongodb+srv://blackjackuser:blackjackpass@cluster0.efdbcf5.mongodb.net/blackjack_db?retryWrites=true&w=majority&appName=Cluster0` |
| `MYSQL_URL`                  | `r2dbc:mysql://root:@mysql:3306/blackjack_db`                                                                                      |
| `MYSQL_USER`                 | `root`                                                                                                                             |
| `MYSQL_PASS`                 | *""*                                                                                                    |

</details>

## 📤 How to Share and Deploy

* Share the Render app URL: `https://blackjack-api-latest.onrender.com`
* Share the Swagger UI URL: `https://blackjack-api-latest.onrender.com/swagger-ui.html`
* Share the Docker Hub Image: `https://hub.docker.com/r/acocinas/blackjack-api`
* Clone or fork the repository from GitHub to set up your own instance.

## 📢 Contributors

👤 Alfonso Cocinas
🔗 [GitHub Profile](https://github.com/acocinas)

**Happy Coding!** 🚀

---

## 📄 License

This project is for educational purposes only. No commercial license is applied.
