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
* Open/closed
    * Level could be more closed
    * Entities could be a bit more closed - HitBox is protected, should be private
    * Otherwise pretty closed
    * Scroller is very open/closed - it provides enough basic scroller functions that 
* Data driven -  customize level generation, textures, level sequence, player type, scroller type
    * Things that should have been data-driven: health/damage, power-ups, movement speed, jump speed, gravity factor

####APIs and use cases: 5 min - Mike and Alex
* IEntity - the basic functions it provides to create any game object for the platformer API
    * Use case: Its implementation into the Moveable interface, which itself is used to provide movement capability on top of basic entity properties to the Player and Enemy
* Scroller - three main methods - getScoreFromScroll(), scroll(level, player), reset()
    * Use case: ManualScroller and AutoScroller

####Two designs (MVC and entity hierarchy): 5 min - Mike and Edem
* Entity hierarchy - originally it was more abstraction/inheritance based, with a single Entity class being the superclass of all other entity classes on top of a lot of other abstractions. By the end, the Entity superclass turned into the IEntity interface, and there was less of a hierarchy overall and more reliance on interfaces to give each type of entity its necessary properties.

####Teamwork and Conclusion: 5 min - all of us
