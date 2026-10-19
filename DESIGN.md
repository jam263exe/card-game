<h1> Design Rationale </h1>
<h2> Key Architecture of Code </h2>

<p> CardGame is responsible for the main game logic and management. It parses the input data, validates the data, initializes the ring topology and manages the thread execution. 
Other classes do what they should do and nothing more. This keeps everything clear, secure and encapsulated.
</p>

<p> Player is the main work horse of the card game. It implements runnable and manages its own localized game loop. It interacts with shared resources like Deck and Hand. </p>

<p> Deck is a thread safe container for Card objects to be stored and allows access concurrently by Players. </p>

<p> Hand encapsulates the logic of holding a player's cards, verifying wins and selecting non preferred cards to discard. </p>

<p> Card is immutable. It holds value and cardId preventing accidental change across threads. </p>

<h2> Concurrency Decisions and Thread Safety </h2>

<p> The main challenge we faced during this specification was ensuring drawing a card from the left deck and discarding it to the right deck was on singluar atomic action. </p>

<p> We started by using a LinkedBlockingQueue because it handles the thread safety using put() and take() easily. However this didnt guarantee that draw and discard would happen together without issues. So instead we are using ArrayDeque and ReentrantLock. This allows a Player thread to aquire access to their left and right decks at the same time before modifying either and ensuring atomic actions.</p>

<p> We also use AtomicBoolean across all threads. This ensures once one player thread wins, all other threads see this and can exit easily. </p>

<h2> Object Orientated Design </h2>

<p> To satisfy the requirement that a game must not stagnate, the Hand class uses Random to select from all non preferred cards. </p>

<p> Using encapsulation, we also created custom runtime exceptions to implement fail solutions and communicate them effectively. </p>

<p> We have sepereated the Hand and Deck classes as the Deck requires FIFO and a Hand requires random access. </p>

<h2>Known Limitations</h2>

<p>Because adjacent players share decks, which lock twice per turn, at a large game e.g. n = 10000, these locks may lead to bottleneck and performance could drop dramatically.</p>



