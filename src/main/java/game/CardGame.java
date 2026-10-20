package game;
import cards.Deck;
import java.lang.Thread;
import java.util.concurrent.atomic.AtomicBoolean;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import cards.Pack;
import cards.exceptions.InvalidPackException;
import cards.exceptions.PackInitializationException;

public class CardGame {

    private final AtomicBoolean gameOver = new AtomicBoolean(false);
    // Number of players (referred to as n in comments)
    private final int numPlayers;
    // Initial collection of all 8n cards
    private final Pack pack;
    private Thread[] threads;
    // This array stores the players during initialization
    // So that threads can be created with correct players
    private Player[] players;
    // MUST BE POPULATED INITIALLY BEFORE PLAYERS ARRAY
    private Deck[] decks;

    /**
     * Initialization MUST happen in the following order:
     * The pack of cards is created and loaded from the file.
     * Next the decks array is populated with empty decks.
     * Now that we have a load of empty decks, we can add the
     * players to the players array and pass the references to
     * the actual deck objects as parameters into the players'
     * draw and discard decks.
     * Then players need to be dealt their cards from the pack
     * in a round-robin fashion.
     * The decks are then given cards in the same way.
     * Finally, the threads containing the players can be created.
     * @param numPlayers
     * @param path
     * @throws FileNotFoundException
     * @throws PackInitializationException
     * @throws InvalidPackException
     */
    public CardGame(int numPlayers, String path) throws
            FileNotFoundException,
            PackInitializationException,
            InvalidPackException
    {
        this.numPlayers = numPlayers;
        this.pack = new Pack(numPlayers, path);

        this.initializeDecks();
        this.initializePlayers();
        this.dealToPlayers();
        this.dealToDecks();
        this.initializeThreads();
    }

    private void initializeDecks() {
        this.decks = new Deck[numPlayers];
        for (int i=0; i<numPlayers; i++) {
            this.decks[i] = new Deck(i);
        }
    }

    private void initializePlayers() {
        this.players = new Player[numPlayers];
        for (int i = 0; i < this.numPlayers; i++) {
            // Player i draws from deck i, and discards to the previous deck circularly
            int drawDeckIdx = i;
            int discardDeckIdx = (i - 1 + this.numPlayers) % this.numPlayers;

            // Add the players to the initial players array
            this.players[i] = new Player(
                    i,
                    decks[drawDeckIdx],
                    decks[discardDeckIdx],
                    this.gameOver
            );
        }
    }

    /**
     * Deal cards to players in round-robin fashion.
     */
    private void dealToPlayers() {
        for (int i=0; i<this.numPlayers*4; i++) {
            this.players[i%this.numPlayers].acceptCard(pack.drawCard());
        }
    }

    /**
     * Deal cards to decks in round-robin fashion.
     */
    private void dealToDecks() {
        for (int i=0; i<this.numPlayers*4; i++) {
            this.decks[i%this.numPlayers].addCard(pack.drawCard());
        }
    }

    public static void main(String[] args) throws
            FileNotFoundException,
            PackInitializationException,
            InvalidPackException
    {
        int players = 0;

        // Input number of players request
        Scanner cli = new Scanner(System.in);
        while (players < 1) {
            System.out.print("Please enter the number of players: ");
            if (cli.hasNextInt()) {
                players = cli.nextInt();
                if (players < 1) {
                    System.out.println("Number of players must be positive integer.");
                }
            } else {
                System.out.println("Invalid input. Please enter a positive integer.");
                cli.next(); // Clear the invalid input
            }
        }

        CardGame game;

        // Input pack location request
        boolean packLoader = false;
        while (!packLoader) {
            cli.nextLine();
            System.out.println("Please enter location of pack to load: ");
            String packLocation = cli.nextLine();
            try {
                game = new CardGame(players, packLocation);
                packLoader = true;
                game.startGame();

            } catch (Exception e) {
                System.out.println(e.getMessage() + "Please try again.");
            }
        }
        cli.close();

    }

    /**
     * Add the threads to the threads array and initialize
     * the players with the correct draw and discard decks.
     */
    private void initializeThreads() {
        this.threads = new Thread[this.numPlayers];
        for (int i=0; i<this.numPlayers; i++) {
            this.threads[i] = new Thread(this.players[i]);
        }
    }

    public void startGame() {
        // Start all player threads
        for (Thread thread : this.threads) {
            thread.start();
        }

        // Wait for all players threads to finish
        for (Thread thread : this.threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        this.printFinalDecks(); // Print final deck contents
    }

    /**
     * Print final deck files for each player
     */
    public void printFinalDecks() {
        for (Deck deck : this.decks) {
            try {
                String fileName = "deck" + deck.getDeckId() + "_output.txt";
                FileWriter writer = new FileWriter(fileName);
                writer.write("deck" + deck.getDeckId() + " contents: " + deck.toString() + "\n");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
