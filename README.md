# Project: Humans vs Goblins

This Java project contains the necessary code to play
"Humans vs Goblins," an engaging turn-based
strategy game where players control
characters represented as objects, including 
land, goblins, and humans. It implements
a grid-based game world using UTF characters
(emojis) to visually represent players and 
goblins.

The game features turn-based orthgonal 
movement, and battles are initiated when 
a human and goblin collide, utilizing
random math for combat outcomes.

## Key Features
### Object-Oriented Design
* Game entities (`Land`, `Goblin`, `Human`) are represented as objects.
* Objects' `toString()` methods provide meaningful representations.
### Grid-based Game World
* A grid serves as the game world.
* UTF characters are utilized to visually represent players, goblins, and land.
### Turn-Based Movement
* The game implements one-step movement turns between `Human`s and `Goblin`s within the grid.
* `Human`s are controlled by the user, while `Goblin`s move randomly.
### Combat Mechanism
* Battles are triggered whenever a `Human` and `Goblin` collide.
* Outcomes are determined through `Random` `Math` operations simulating two six-sided dice (`2`-`12`).

### Game Execution
* The game can be run from the project's `Main` class.
* For a better user experience, it is recommended to be
played in a native CLI as IDE terminals won't implement certain visual changes.

#### Have fun! :)