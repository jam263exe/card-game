<h1>Card Game Docs</h1>
<h2>Implementation Strategy</h2>
<h3>CardGame Structure</h3>
<p>
The threads, hands, and decks are stored in arrays. Players
are instantiated within the threads. Awareness of players'
positions relative to the hands and decks is provided by
playerId, deckId, and handId (indexed from 0.)
</p>
<p>
For a game 
with 5 or more players, playerId 3 would be associated with 
handId 3 and deckIds 2 and 4. In the edge case that the game 
had 4 players, playerId 3 would be associated with handId 3 
and deckIds 0 and 2. playerId 0 would be associated with handId
0, and deckIds 3 and 1. This is to preserve the ring structure.
</p>
<p>
All of this game logic is managed by the CardGame class. Other
classes do what they are meant to do and nothing more. For
example, the Player class does store a Hand - its sole purpose
is to act upon the hand with gameplay logic in the Player class.
This keeps everything clear, seperated and encapsulated.
</p>
<h3>Player Threads</h3>
<p>
Each player has their own thread so the simulation can run
asynchronously.
</p>
<h3>Thread Safety</h3>
<p>
The only resources that multiple players (at most 2, due to the
ring structure of the game) can try and access at once are the
Deck objects. The specification requires that discarding and 
taking a card must be treated as one atomic action. To acheive
this, the player's thread first locks Deck i and then locks Deck
i+1 (or deck 0 if at the end of the list to preserve the ring 
structure), and only after this may take and withdraw cards.
After this, the lock is released on both decks and any other
players that may have been trying to access them can.
</p>
<p>
It is impossible for one player to try and access
another player's hand in this structure - only the cards
to the immediate left and right of a player are accessable.
Because of this, there is no reason to implement more than
basic thread safety strategies for this class.
</p>
<h2>Classes</h2>
<h3>Card</h3>
<p>
This class just stores the actual value of the card, and a
cardId. The intention is for the cardId to make tracking and
manipulating the exchange of cards between hands and decks 
easier in the main game loop.
</p>
<h3>Deck</h3>
<p>
The deck stores cards in an ArrayDeque data structure. This 
is essentially a two-tailed queue, which perfectly mirrors how
a deck of cards functions in this game in O(1) time complexity
(not that it matters much at all for this project.) 
</p>
<p>
Each deck has a deckId, which is used throughout the game 
simulation so that players know exactly where they are 
drawing cards from and discarding them to.
</p>
<h3>Hand</h3>
<p>
Cards in the hand are stored in an ArrayList structure. This
differs to the Deck class, as players need to be able to access
all cards at once and operate on cards in different places than
just the tail ends of the hand. 
</p>
<p>
Each hand has a handId for the same reasons as the Deck class.
</p>