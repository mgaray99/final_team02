##Outline
Time: 30 min

####Intro: 1 min - all of us
* Introduction

####Feature run-through: 4 min - Edem and Ryan
* Boot up game, play Mario. 
* Play doodle jump
* Flappy bird
* Mario infinity 
* Doodle jump 2
* Texture packs 
* Controls
* High scores
* Appearance 
* Language
* Save game and load game

####Data files run through: 5 min - Alex
* Show marioinfinity.properties
* Discuss each key in the file
    * level - data package, csv file
    * keys - resources package, .properties file (demonstrate changes for this file)
        * Switch default key binding
        * Switch key binding to various “bad” configs
    * textures - resources package, .properties file (demonstrate changes for this file)
        * Switch textures to good texture path
        * Switch textures to “bad” texture paths
    * scroller - specifies the type of scroller
    * autofile - resources package, .xml file
        * Switch autogeneration type
    * player - specifies the type of player object
    * leaderboard - resources package, .csv file
    * nextfile - resources package, .properties file

####Design goals / Open/closed / data driven: 5 min - Ryan and Mike
* Level’s use of instanceof vs polymorphism 
    * An alternative would have been to have most methods present in the main IEntity interface, but then that would lead to a lot of unnecessary methods present in classes that don’t need them, which left our hands tied. We decided the best course of action was using instanceof and referring to the interfaces, which would easily support extension.

```java
public class Level {
/* constructor and other methods */
 /**
   * Removes an entity from the level by removing it from all lists of entities.
   * @param entity to be removed.
   */
  @Override
  public void removeEntity(IEntity entity) {
    if(entity != null){
      this.entityList.remove(entity);
    }
    if (entity instanceof Block) {
      this.blockList.remove(entity);
    }
    if (entity instanceof Enemy) {
      this.enemyList.remove(entity);
      score+=ENEMY_SCORE;
    }
    if (entity instanceof Player) {
      this.playerList.remove(entity);
    }
    /* more cases */
  }
}
```

* Open/closed
    * Level and LevelLoader could be more closed - there are a lot of getter methods to access copies of lists
    * Entities could be a bit more closed - instead of accessing the hitboxes directly using a public method (getHitbox), we should have only referred directly to the positional and dimensional values of the hitbox
    * Scroller is open/closed - you just implement Scroller into a new Scroller class, add it to the ScrollerFactory, and then you can use it via the configuration value without having to modify the original Scroller interface at all
    * ITexturer is open/closed - you just implement ITexturer into a new Texturer class and then replace the existing texturer in GameView and you won’t have to modify any of the original functionality of ITexturer
* Data driven
    * Custom level generation, textures, level sequence, player type, scroller type
    * Things that should have been data-driven: health/damage, power-ups, movement speed, jump speed, gravity factor

![hello](propertiesshot.png)

####APIs and use cases: 5 min - Mike and Alex
* IEntity - the basic functions it provides to create any game object for the platformer API
    * Use case: Its implementation into the Moveable interface, which itself is used to provide movement capability on top of basic entity properties to the Player and Enemy

```java
/**
 * An interface used as the basis for all entities in a level
 * @author Mike Garay and Ryan Krakower
 */
public interface IEntity {

    /**
     * Obtains the stored Hitbox instance of the entity
     * @apiNote : This should be amended to return an IHitbox instance, not a Hitbox instance
     * @return The Hitbox instance stored in the entity instance
     */
    HitBox getHitBox();

    /**
     * A generic handler for checking any collisions between this entity and another entity
     * Being a handler, it should contain any logic for when there are collisions present
     * @param entity The other entity to check for any possible collisions with
     */
    void checkCollision(IEntity entity);

   /* other methods */

    /**
     * Obtains a String representing the stored type of this entity
     * This should usually obtain the name of the implementing class
     * and can be used to avoid instanceof/downcasting if not absolutely required
     * @return A String representing the type of this entity
     */
    String getType();
}
```

* Scroller - three main methods - getScoreFromScroll(), scroll(level, player), reset()
    * Use case: ManualScroller and AutoScroller
    
```java
/**
 * The Scroller interface
 */
public interface Scroller {
    int NUM_BLOCKS = 15;

    /**
     * Scrolls the level, perhaps based on the actions of player
     * @param level the Level object to be scrolled
     * @param player the Player object whose actions might determine the scrolling of level
     */
    void scroll(Level level, Player player);

    /**
     * Resets the scroller
     */
    void reset();

    /**
     * Returns an integer value which should be added to the user's score due to survival of
     * the last scroll
     * @return an integer value representing the score gained by the user from the last scroll
     */
    int getScoreFromScroll();
}
```

```java
public class AutoScroller implements Scroller
{
  /* constructor and other methods */

  /**
   * Scrolls the list of Entitiies
   * @param level the level to be scrolled
   * @param player the player of the level
   */
  @Override
  public void scroll(Level level, Player player) {
    level.translateAllEntities(xScroll, yScroll);

    if (playerScrolls) {
      scrollPlayer(player);
    }
  }

  /**
   * Resets the scroller
   */
  @Override
  public void reset() {
    //DO NOTHING
  }


  /**
   * Returns an integer value which should be added to the user's score due to survival of
   * the last scroll
   * @return SCORE_FROM_SCROLL
   */
  @Override
  public int getScoreFromScroll() {
    return SCORE_FROM_SCROLL;
  }
}
```

####Two designs (MVC and entity hierarchy): 5 min - Mike and Edem

* Model-view-controller was something that remained consistent since the beginning of the project - we wanted to make sure the user only had direct access to the controller, which would handle manipulating model, which would handle updating what was seen by the user in view. It also made it simpler to divide up the work for the project, as 2 people would work on view/controller while 2 people worked on model.
* Entity hierarchy - originally it was more abstraction/inheritance based, with a single Entity class being the superclass of all other entity classes on top of a lot of other abstractions. By the end, the Entity superclass turned into the IEntity interface, and there was less of a hierarchy overall and more reliance on interfaces to give each type of entity its necessary properties.

![hello](entityhierarchy.png)

####Teamwork

* Completed vs wireframe
    * Breaking the project into smaller milestones increased productivity since our project was organized to implement the most relevant features in each milestone without having to deal with the pressure of thinking about the complete implementation of the project
    * It allowed for flexibility and adaptability since we were able to take feedback from each milestone and better design  features we implemented that 
    * Helped track progress as we proceeded with the project
    * It made identifying code errors/bugs easier since each milestone had specific features that needed to be implemented
* Something each team member learned about managing large project
    * Better grasp of git and gitlab and learnt how to manage merge conflicts better
    * Learn how to respect each other’s opinions and respond to constructive feedback
    * Large projects can be better managed when broken down into smaller chunks
    * Managing large projects requires increased collaboration since different members work individually on different aspects of the project which are supposed to be integrated
* One thing we each learned about positive team culture
    * Created an environment where everybody can express their views without fear of any form of judgement
    * Fostered collaboration since team members were ready to help each other
    * Encouraged positivity which helped us accomplish all goals we set at the beginning of the project
    * Fostered a social community where we got to interact and talk about stuff outside the project: including recommendations for movies and tv shows, and helping other team members on programming stuff outside class
* Revisit team contract, one thing we each learned about communicating and solving problems 
    * Problems between team members could be easily resolved by allowing the parties involved voice out their complaints with other team members mediating.
    * Conflicting team decisions were easily resolved by analyzing the pros and cons of each idea and voting the best idea
    * Respecting and listening to each other fostered positive team work