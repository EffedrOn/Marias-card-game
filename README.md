# 🂡 Marias Card Game – Java CLI

This is a command-line implementation of the traditional **Marias card game**, developed in **Java** using **Maven**. The game simulates a multiplayer card game (including bot players), based on Bohemian/German card decks.

Players take turns playing cards, forming tricks, and competing in teams. The game includes basic bot logic, multi-round scoring, and a simple CLI interface for interaction.

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

## Gameplay Example

Below is an example of what a typical game round looks like in the terminal:

[INFO] Human player created
[INFO] Bot1 player created
[INFO] Bot2 player created
[INFO] Human player entered the game
[INFO] Bot1 player entered the game
[INFO] Bot2 player entered the game
[INFO] Table created
[INFO] Deck created
[INFO] End of Table initialization
---------------------------------------------------
Starting a new game round...
---------------------------------------------------
[INFO] Dealing Cards
> Choose trump: 
| 0 : 10❤️ | 1 : A🍃 | 2 : 10⚫ | 3 : 8🌰 | 4 : D🍃 | 5 : 10🌰 | 6 : 7❤️ |
> Choose card index to pick the trump: 
0 
> Your hand (choose 2 cards to throw away): 
| 0 : 10❤️ | 1 : A🍃 | 2 : 10⚫ | 3 : 8🌰 | 4 : D🍃 | 5 : 10🌰 | 6 : 7❤️ | 7 :  9⚫ | 8 : 10🍃 | 9 :  7⚫ | 10 : K❤️ | 11 :  8⚫ |
> Enter index of the FIRST card to throw away: 
3
> Enter index of the SECOND card to throw away: 
6
[INFO] Two cards have been thrown away
---------------------------------------------------
[INFO] Trump suit is: ❤️
Your hand:
| 0 : 10❤️ | 1 : A🍃 | 2 : 10⚫ | 3 : D🍃 | 4 : 10🌰 | 5 :  9⚫ | 6 : 10🍃 | 7 :  7⚫ | 8 : K❤️ | 9 :  8⚫ |
> Choose card index to play: 
6
Human played: 10🍃
Bot1 played: 8🍃
Bot2 played: 9🍃
[INFO] Human wins the trick!
[INFO] Trick value: 10
---------------------------------------------------
[INFO] Trump suit is: ❤️
Your hand:
| 0 : 10❤️ | 1 : A🍃 | 2 : 10⚫ | 3 : D🍃 | 4 : 10🌰 | 5 :  9⚫ | 6 :  7⚫ | 7 : K❤️ | 8 :  8⚫ |
> Choose card index to play: 
0
Human played: 10❤️
Bot1 played: H❤️
Bot2 played: A❤️
[INFO] Bot2 wins the trick!
[INFO] Trick value: 20
.
.
.
.
.
.
---------------------------------------------------
Bot2 played: A🌰
[INFO] Trump suit is: ❤️
Your hand:
| 0 :  8⚫ |
> Choose card index to play: 
0
Human played: 8⚫
Bot1 played: K🌰
[INFO] Bot2 wins the trick!
[INFO] Trick value: 10
[INFO] Bonus: Last trick! 10 points awarded to Bot2
---------------------------------------------------
--------------------End of game--------------------
[INFO] Human score: 30
[INFO] Bot1 score: 20
[INFO] Bot2 score: 40
[INFO] Team players Bot1 and Bot2 scored: 60
---------------------Team won!---------------------
[INFO] Human bank: 16 cents
[INFO] Bot1 bank: 22 cents
[INFO] Bot2 bank: 22 cents
---------------------------------------------------
> Do you want to play another round? (y/n) : 
y
---------------------------------------------------
Starting a new game round...
---------------------------------------------------
[INFO] Dealing Cards
[INFO] Bot1 is choosing the trump...
[INFO] Bot1 chose 🍃 as trump.
[INFO] Bot1 threw away two cards.
---------------------------------------------------
Bot1 played: 9🍃
Bot2 played: D🍃
[INFO] Trump suit is: 🍃
Your hand:
| 0 : K🍃 | 1 :  H⚫ | 2 :  A⚫ | 3 : 10🍃 | 4 : 7🍃 | 5 : 10⚫ | 6 :  9⚫ | 7 : 7❤️ | 8 : 10❤️ | 9 : K🌰 |
> Choose card index to play: 
