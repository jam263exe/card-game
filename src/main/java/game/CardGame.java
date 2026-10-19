package game;
import cards.Deck;
import java.lang.Thread;
import java.util.concurrent.atomic.AtomicBoolean;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;
import cards.Card;
import cards.Pack;
import cards.exceptions.InvalidPackException;
import cards.exceptions.PackInitializationError;

public class CardGame {

    private final AtomicBoolean gameOver = new AtomicBoolean(false);
    // Number of players (referred to as n in comments)
    private final int numPlayers;
    // Initial collection of all 8n cards
    private Pack pack;
    private Thread[] threads;
    private Player[] players;
    private final Deck[] decks;


    public CardGame(int numPlayers, String path) throws
            FileNotFoundException,
            PackInitializationError,
            InvalidPackException
    {
        this.numPlayers = numPlayers;
        this.pack = new Pack(numPlayers, path);
        this.decks = new Deck[numPlayers];
        this.threads = new Thread[numPlayers];
        this.initializeThreads();
    }

    static void main(String[] args) throws
            FileNotFoundException,
            PackInitializationError,
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
            System.out.println("Please enter location of pack to load: ");
            String packLocation = cli.nextLine();
            try {
                game = new CardGame(players, "pack-test.txt");
                packLoader = true;
            
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
        for (int i = 0; i < this.numPlayers; i++) {
            int discardDeckIdx = -1;
            int drawDeckIdx = -1;

            if (i == 0) {
                drawDeckIdx = 0;
                discardDeckIdx = this.numPlayers - 1;
            }
            else if (i == this.numPlayers - 1) {
                drawDeckIdx = 0;
                discardDeckIdx = this.numPlayers - 2;
            } else {
                drawDeckIdx = i;
                discardDeckIdx = i - 1;
            }

            this.threads[i] = new Thread(new Player(
                    i,
                    this.decks[drawDeckIdx],
                    this.decks[discardDeckIdx],
                    this.gameOver
            ));
        }
    }

    /**
     * Give remaining cards to decks in round-robin fashion AFTER
     * giving cards to players.
     */
    private void initializeDecks() {
        // Deal remaining 4n cards to decks in round-robin
        int remainingCards = this.numPlayers * 4;
        for (int i = 0; i < remainingCards; i++) {
            int deckIndex = i % this.numPlayers;
            this.decks[deckIndex].addCard(this.pack.drawCard());
        }
    }

    public void startGame() {
        this.initializeDecks();

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
