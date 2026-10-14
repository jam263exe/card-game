<h1>Card Game Docs</h1>
<h3>
Methodology
</h3>
<p>
We have 2n decks of cards (where n refers to the number of players).
Players do NOT have a Deck object inherently associated with them -
we use a ring topology of 8 decks of 4 cards, with a different player 
associated with every other deck just through their player and deck
ids via the CardGame class. This means we can treat players'
hands and adjacent decks in the same way, with Player objects only
purpose to be the drivers of the game simulation.
</p>