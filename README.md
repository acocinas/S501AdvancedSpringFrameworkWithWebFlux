# 🃏 Blackjack API

A **Reactive Spring Boot WebFlux API** for playing Blackjack, featuring a fully functional game logic, connection with **MongoDB** and **MySQL**, and Swagger documentation.  
Level 1 includes: player management, game creation, play actions, global error handling, ranking, and more!

---

## 🚀 **Endpoints**

| Method | Endpoint                   | Description                                   |
|--------|----------------------------|-----------------------------------------------|
| `POST` | `/game/new`                | Create a new Blackjack game |
| `GET`  | `/game/{id}`               | Get game details by ID |
| `POST` | `/game/{id}/play/hit`      | Player hits (requests card) |
| `POST` | `/game/{id}/play/stand`    | Player stands |
| `DELETE`| `/game/{id}`              | Delete a game |
| `GET`  | `/game/ranking`            | Get player ranking |
| `GET`  | `/players`                 | List all players |
| `POST` | `/players`                 | Create a new player |
| `GET`  | `/players/{id}`            | Get player by ID |
| `PUT`  | `/players/{id}`            | Update player |
| `DELETE`| `/players/{id}`           | Delete player |

---

## 📖 **Game Flow**

<details>
<summary>🃏 Click to expand - How does the game work?</summary>

1️⃣ **Game Creation**: Player selects a bet.  
2️⃣ **Initial Deal**: 2 cards for player and dealer.  
3️⃣ **Blackjack Check**: If player has natural blackjack:
- Win if dealer has no blackjack.
- Draw if dealer also has blackjack.
- Bet settled immediately with payout.  
4️⃣ **Play Phase**: If no blackjack, player can hit or stand.  
5️⃣ **Dealer Turn**: Dealer reveals hand and draws up to 17 or more.  
6️⃣ **Outcome**:
- Player win, dealer win, draw, or player busted.
- Balance updated accordingly.

</details>

---

## 🔒 **Error Handling**
- Global exception handler (`GlobalExceptionHandler`) for clear responses:
  - Player or game not found.
  - Invalid actions or bets.
  - Validation errors.
  - Internal errors.

---

## 📊 **Swagger UI**
📍 Access the API via Swagger:
http://localhost:8080/swagger-ui/index.html 


---

## 🏅 **Player Ranking**
<details>
<summary>📈 Click to expand - How is the ranking calculated?</summary>

- **Total Games**: Number of games played.  
- **Wins**: Number of player wins.  
- **Losses**: Number of player losses.  
- **Draws**: Number of draws.  
- **Win Rate (%)**: Calculated as `(wins / total games) * 100`.  
- **Sorted descending by win rate**.

</details>

---

## 🧰 **Tech Stack**
- 🌐 **Spring Boot WebFlux**
- 🗃 **MongoDB (Reactive)** for games
- 💾 **MySQL (R2DBC)** for players
- 🔎 **Swagger / OpenAPI** for documentation
- 🧪 **JUnit & Mockito** for tests
- 🐘 **Maven** for build management

---

## 📌 **Next Steps**
✅ Level 1: Done!  
🔜 Level 2: Dockerization  
🔜 Level 3: Deployment on Render or GitHub Actions

---

## 📢 **Contributors**
👤 Alfonso Cocinas  
🔗 [GitHub Profile](https://github.com/acocinas)

---

