final
====

This project implements a player for multiple related games.

Names: 
* Alex Lu
* Mike Garey
* Edem Ahorlu
* Ryan Krakower

### Timeline

Start Date: 10/23/2020

Finish Date: 11/18/2020

Hours Spent:

Alex Lu:

Mike Garey:

Edem Ahorlu: ~35

Ryan Krakower: ~35


### Primary Roles

Alex Lu:

Mike Garey:

Edem Ahorlu: 
My role as a teammate is working on the frontend of the game.
I worked with Alex Lu, another frontend team member, to implement
various features including textures, splash screens, display enemies,
power ups, and scores among others.

Ryan Krakower:
My role was primarily working on the backend with Mike.
Together, we implemented the entity hierarchy (entities are the building blocks
that make up our levels) and wrote the Level class. 
I was responsible for the physics of entities, making the HitBox class and collision mechanics. 
I also wrote several of the entity classes, such as Player and Enemy. 
Additionally, I added the Doodle Jump and Flappy Bird texture packs.


### Resources Used
* StackOverflow
* w3schools
* geeksforgeeks
* Oracle


### Running the Program

Main class: Main.java

Data files needed: 
* resources package
* data package


Features implemented:
* Run 3 functionally different working and playable versions of a scrolling platformer game and two mods of the game:
    - Mario Bros, Mario Infinity (mod)
    - Doodle Jump, Doodle Jump 2 (mod)
    - Flappy Bird
* Enable the player easily switch between all versions of the game
* Implemented Power ups
* Implemented enemies/oppositions to inhibit the player
* Implemented Splash Screens with images
* Display scores
* Keep track of high scores on a leaderboard
* Added different texture packs, which can each be applied to any of the games
* Display caught exceptions
* Switch between different languages
* Switch between different menu/background appearance modes (default, dark, light)
* Allow the user to save progress on game
* Allow the user to set key controls for the game


### Notes/Assumptions

Assumptions or Simplifications:

-Every entity is the same size and shape (a square). This assumption made it easier to 
read/write levels and to make HitBoxes and collision mechanics. 
The only downside is that it limits our options for resizing entities.

-The game scene is always the same size (you can technically resize the window, 
but the scene size will not change, so there's no point)

Interesting data files:

Known Bugs:
-If the player is moving too fast, he may go straight through a block.
-Sometimes collision mechanics do not work correctly if the player
approaches a block from a corner (he may appear to go through the block).
-Enemies appear to vibrate when they are at the same x-coordinate as the player.

Extra credit:
* Trump textures



### Impressions

