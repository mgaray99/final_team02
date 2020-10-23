CRC Cards

Classes: Card class, Game class, Player class, Rule class, Goal class, CardDeck class

Class: Card class
Responsibility: represents a card that the Player has
.getCard(): abstract, returns the card

Class: Rule class extends Card
Responsibility: represents a Rule card
.getRule() - gets the Rule associated with the card

Class: Goal class extends Card
Responsibility: represents a Goal card
.getGoal() - gets the Goal associated with the card

Class: Action class extends Card
Responsibility: represents a Action card
.getAction() - gets the Action associated with the card

Class: CardDeck class
Responsibility: represents a deck of cards
.getCard() - abstract, overriden but returns a card

Class: Game class
Responsibility: runs the game for the user, loops through turns
.getNextAction(): substitutes in either a subclass or a predetermined method that determines what
action the game should perform next in order to give a Player a turn
.checkIfGoalMet(): checks to see if the goal is met, if so ends the game with a win for the player
that won

Class: Theme class
Responsibility: contains different variables (Keeper, Creeper, Goal, Rules) to store the current 
configuration of the game
.getKeepers()
.getCreepers()
.getGoal()
.getRules()

Use Cases:

