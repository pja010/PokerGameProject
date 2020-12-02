# CSCI 205 - Software Engineering and Design
Bucknell University
Lewisburg, PA

CSCI 205 – Software Engineering and Design Fall 2020

Teamwork with Gitlab and IntelliJ 3
### Course Info
Instructor: Brian King
Semester: Fall 2020
## Team Information
Team Name: Four of a Kind
Callie Valenti- Scrum Master
Lindsay Knupp- Project Owner
Per Astrom - Team Member
Guillermo Torres - Team Member
## Project Information
Final Project- Poker Game
## Presentation Video URL
https://mediaspace.bucknell.edu/media/t/1_rmap6rb2
## Libraries
Scene Builder
https://gluonhq.com/products/scene-builder/
Version n: 11.0
JavaFX
https://openjfx.io/
Version 15
## Project Summary
For our project, we decided to create a poker game that focused on networking and GUI development. The GUI displays a table that is the same for all players as well as a bar on the bottom that is specific to each player. Additionally, on the right hand side, the GUI displays the ranking of different poker hands. The table updates each time a player makes a move for all the players. It keeps track of the minimum bet amount, the pot amount, the cards displayed, and the moves of each player within a betting round. It also displays the winner of the round at the end. At the top it lets each player know whose turn it is as well so that they know when they need to set their action.
The bottom pane of the GUI displays the unique bar for each player and has buttons for bet, check, and fold as well as a TextField for the user to enter their bet amount in. Additionally, it lets the user know what turn number they currently are. This value changes for each round to simulate how the position of each player in the betting structure would change based on the changing of a dealer in in person poker. This is also where the player’s two personal cards are displayed as well as their current amount of chips. The cards update after each complete round is finished and the chips are updated every time the player completes a turn.
The game is set up currently to function with players having multiple turns for each betting round so that the players who initially bet lower than the eventual minimum bet of the round can decide if they will add more money or fold. After the first round of betting is over, the first three table cards are shown to the players. Then, another round of betting before the forth card and another round of betting before the fifth. There is one final round of betting after the last table card is shown before a winner is declared. This is shown in the action text for that player. The pot is then added to that player’s chips amount and set to zero so that a new round can start. The entire game is reset, cards changed, and table updated for all of the players. The terminal prompts each player when they need to enter their action and they enter ‘Go’ into the terminal to let it know they are finished with their turn.
To run the game, GameFlowNetworking must be run 5 times with the host connecting first. Then, the 4 players connect and the game begins! Currently, the game loads properly most of the times it is run. However, sometimes it does not start up properly and all the instances of the game running have to be terminated and restarted.
## Package Structure
We used two different packages to hold our code outside of the main one. All of the general classes we needed to run the game are in the main package while the GUI related code is in the pokergamemvc package and the networking code is in the networking package. The main class that is used to run the game, GameFlowNetworking, is in the networking package.
