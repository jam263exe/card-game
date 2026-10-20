<h1>Read Me</h1>
<br>
<h2>Explanation of Program</h2>
<p>This is a multi-threaded Java simulation of a card game. n Players act concurentlly on the same ring topolgy of Cards and Decks. Each Player aims to collect a hand of four cards of the same denomination by discarding a random unprefered card. The game prioritises thread safety and atomicity during concurrent tasks using ReentrantLock and ArrayDeque.</p>

<br>

<h2> Prerequisites </h2>
<ul>
    <li> Java Development kit version 27 </li>
    <li> JUnit 5 </li>
    <li> Maven </li>
</ul>

<br>

<h2>Programme Structure</h2>
<ul>
    <li> game/ contains the core execution of the program: CardGame controller and Player thread implementation. </li>
    <li> cards/ contains the data structures: Card, Deck, Hand and Pack </li>
    <li> cards/exceptions/ contains custom runtime exceptions: InvalidPackException, PackInitializationError, InvalidCardException, GameAlreadyWonException.</li>
</ul>

<br>

<h2> How to Compile </h2>
<p> To compile the project, open terminal and navigate to the root directory of the source code and compile all the packages. </p>
<p>Once compiled, run the game using the main controller class, CardGame. 'java game.CardGame'</p>
<p>The user will then be prompted for two inputs: the number of players in the game (n) and the location of the pack file.</p>

<br>

<h2>Requirements</h2>
<p> The game requires a plain text file containing 8n rows. Each row must have a single non-negative integer which is imported as a card. If the file contains negative numbers, strings etc the game will throw and exception</p>
<p>The game logs the actions of every thread to maintain tracking of the concurrency. It generates one playerX_output.txt per player where it logs the inital hand, every draw and discard action and wins. It also logs deckX_output.txt per deck. This logs the remaining contents of the deck after the game ends.</p>