# names of all people who worked on the project
  * Alex Lu
  * Mike Garay
  * Edem Ahorlu
  * Ryan Krakower

# each person's role in developing the project

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


# what are the project's design goals, specifically what kinds of new features did you want to make easy to add



# describe the high-level design of your project, focusing on the purpose and interaction of the core classes




# what assumptions or decisions were made to simplify your project's design, especially those that affected adding required features
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