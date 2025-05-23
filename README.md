# 🂡 Marias Card Game – Java CLI

This is a command-line implementation of the traditional **Marias card game**, developed in **Java** using **Maven**. The game simulates a multiplayer card game (including bot players), based on Bohemian/German card decks.

Players take turns playing cards, forming tricks, and competing in teams. The game includes smart bot logic, multi-round scoring, and a simple CLI interface for interaction.

## 📁 Project Structure

- `src/main/java/com/github/EffedrOn/Marias/` – Game logic, players, controller, and deck handling
- `src/test/java/com/github/EffedrOn/Marias/` – JUnit test classes for unit testing
- `pom.xml` – Maven build configuration
- `README.md` – Project documentation

## How to Run

```bash
# Compile the project
mvn compile

# Run the CLI game
mvn exec:java
