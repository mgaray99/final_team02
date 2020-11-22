# Names of all people who worked on the project
  * Alex Lu
  * Mike Garay
  * Edem Ahorlu
  * Ryan Krakower

# Each person's role in developing the project

Edem Ahorlu: 
My role as a teammate is working on the frontend of the game.
I worked with Alex Lu, another frontend team member, to implement
various features including textures, splash screens, display enemies,
power ups, and scores among others.

Mike Garay:
My role was primarily working on the backend with Ryan.
Together, we implemented the entity hierarchy (entities are the building blocks
that make up our levels) and wrote the Level class. 
I was responsible for making the various interfaces used by the entities,
including power-up logic. I also worked with Alex to make LevelLoader and EntityFactory
to load in levels and create entities through reflection. Additionally,
I wrote basic game configuration and made the api package storing all of our interfaces for
the project.

Ryan Krakower:
My role was primarily working on the backend with Mike.
Together, we implemented the entity hierarchy (entities are the building blocks
that make up our levels) and wrote the Level class. 
I was responsible for the physics of entities, making the HitBox class and collision mechanics. 
I also wrote several of the entity classes, such as Player and Enemy. 
Additionally, I added the Doodle Jump and Flappy Bird texture packs.

Alex Lu: 
I worked on the front end of the game with Edem Ahorlu. While he primarily
handled the stylistic elements of the front end (textures, display enemies,
splash screens and css) I worked more on the integration of front end and back
end. I also did a lot of work with controller and controller testing, and built 
the automatic level generation and scroller packages as well as their associated tests


# What are the project's design goals, specifically what kinds of new features did you want to make easy to add

Our project had three main design goals. First, we wanted our design to be data driven. We wanted
nothing to be hardcoded into our program, with all configuration for levels and stylesheets 
configured through .properties files. Please see below for an example .properties file that 
configures a level:

![hello](propertiesshot.png)

As one can see, we achieved our goal of having the config file take data inputs to build
a game rather than having the data hardcoded in. This data driven approach makes it easy
for us to build different types of levels for our three game types by changing the level structure 
file, scroller, automatic level generation configuration, gametextures and allowed key controls.

A second major design goal was substitutability, especially at runtime. We wanted the user to be 
able to change most of the interactive experience that they received when running our program.
All text displayed to the screen is derived from .properties language files, including error
messages, which display in different languages if necessary. The user can change this language file
that determines text as well as the stylesheet and texture pack to any valid substitute by clicking
buttons in the UI. Additionally, the user can rebind keys as they desire during game time.

Finally, we wanted our design to be extensible. We built the Scroller and Entity inheritance
hierarchies in ways that allow for the creation of new scroller classes with different behavior
or new Entity subclasses that acted or appeared differently. This is an example of our adherence 
to the open-closed principle, as we wanted those inheritance hierarchies to be open to 
extension, without needing to be changed.

# Describe the high-level design of your project, focusing on the purpose and interaction of the core classes

Our project is built around the model-view-controller pattern, with the model, view and 
controller packages being the three top level packages in src. 

First, in examining controller, the core class is GameController. Many of the other classes
in the controller package create objects that are contained in GameController's root node. 
To discuss the most important contributors, GameController uses ButtonBuilder to create buttons
for interactivity and it contains OptionsSelector objects which serve as visually robust ComboBoxes
which allow the user to choose from a list of choices. GameController reads the inputs from these
objects and then calls the dispatchEvent() method to alert the rest of the program that an event
has occurred.

Next, in examining view, the core class is GameView, which contains a GameModel object as instance
data and updates it every fraction of a second. During these updates it also uses Texturer to update
the position and existence of all textures on the screen. GameView also contains a list of different 
GameScene subclasses, all of which are contained in the scenes package and represent one type of
scene (i.e. menu scene, play game scene change key bindings scene, etc.) that the user might be
looking at at any given time. 

GameView receives the inputs from GameController by listening to the GameController in the current 
scene via the observer pattern. These can include key inputs, which it passes to the KeyInputter 
class to pass into the model, or button pushes, or selections on an OptionsSelector. Button pushes 
and selections on an OptionsSelector pass through the performReflection() method to be translated 
into method calls (i.e. each message passed to GameView by GameController has a String that will
result in a method call) that do things such as changing the GameScene on display or changing a
stylesheet or resource bundle.

Finally, in model there are five packages, scroll, entity, configuration, autogenerator and score.
The entity package contains the configuration for different Entity types like Player and Enemy, the
configuration class is responsible for translating a game .properties file like the one shown above 
into Level and GameModel objects, the scroll class scrolls the level by moving entity positions, 
the autogenerator package generates new chunks of levels to be explored during game time (i.e. 
infinite level generation for Doodle Jump) and the score package contains classes to keep track of 
high scores.

One primary classes in model, Level, represents an instance of the level that the player is on, 
and contains the controlled player object, which moves in response to key inputs, as well as a list
of entities. GameModel, on the other hand, is a forward facing class that receives update commands
from view, and also uses the configuration package to tell the view what to display (i.e. 
stylesheet, texture pack) based on the .properties file that configured the game being played.

# What assumptions or decisions were made to simplify your project's design, especially those that affected adding required features
   * We put a lot of our data files in the "resources" package instead of in "data"
   * We decided to place some of our test files in this package as well in order to circumvent issues
  related to not being able to locate test files if they weren't in the folder where the classes
  expected to find its files (i.e. a Texturer looks for textures in the 
  /src/resources/images/gameTextures folder)
   * Every entity is the same size and shape (a square). This assumption made it easier to 
  read/write levels and to make HitBoxes and collision mechanics. 
  The only downside is that it limits our options for resizing entities.
  * The game scene is always the same size.


# address significant differences between the original plan and the final version of the project
Our final version of the project was much like we had planned originally with the only significant differences
being additional features implemented. The features which made the final version of our project significantly
different from the original plan are giving the user the ability to set the keys to control characters in each version
of the game and letting the user apply different textures to game.


# describe, in detail, how to add new features to your project, especially ones you were not able to complete by the deadline

### Adding new texture packs:
 1. Create a new package inside src/resources/images/gametextures.
 2. Add images (square images are better) to use for textures.
 3. Add a properties files to src/resources/images/texturefiles. The name of this file
 will be displayed to the user when prompted to select a texture for the game.
 4. Change the value of the texture keys in the properties file to point to the appropriate images.
  
 here is an example:
 
 ```properties
Block=src/resources/images/gametextures/mario/barrierblockbrown.png
Enemy=src/resources/images/gametextures/mario/beetle.png
Player=src/resources/images/gametextures/mario/luigi.png
PowerUpBlock=src/resources/images/gametextures/mario/powerupblock.jpeg
PowerUp=src/resources/images/gametextures/mario/powerup.png
Goal=src/resources/images/gametextures/mario/goal.png
```

In our program's current form, only these six keys are necessary 
to fully texture the game levels. If a texture
is missing, that entity will appear as a black square in View.

### Adding new Entity types
This is a bit tricker. Because we planned on making a few additional types of bricks, I will use 
a new brick type as an example.
* Adding a breakable brick: 
1. Make a new class, called BreakableBlock, that extends the appropriate class (in this case, Block) 
and implements any necessary interfaces (in this case, IDamageable).
2. Program
3. Make 
    
### Adding new game types
