package game;
import cards.Deck;
import java.lang.Thread;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.smartcardio.Card;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;

public class CardGame {

    private final AtomicBoolean gameOver = new AtomicBoolean(false);
    // Number of players (referred to as n in comments)
    private final int numPlayers;
    // Initial collection of all 8n cards
    private Deck pack;
    private Thread[] threads;
    private Player[] players;
    private Deck[] decks;

    public CardGame(int numPlayers) {
        this.numPlayers = numPlayers;
        this.threads = new Thread[numPlayers];
        this.players = new Player[numPlayers];
        this.decks = new Deck[numPlayers];
    }

    private void initializeThreads() {
        this.threads = new Thread[this.numPlayers];
        for (int i = 0; i < this.numPlayers; i++) {
            this.threads[i] = new Thread(this.players[i]);
        }
    }

    /**
     * Give cards to players in a round-robin fashion.
     */
    private void initializePlayers() {
        //Ensure all decks are created so we can pass them to players
        for (int i = 0; i < this.numPlayers; i++) {
            this.decks[i] = new Deck(i + 1);
        }

        //Create players and assign their left (draw) and right (discard) decks
        for (int i = 0; i < this.numPlayers; i++) {
            Deck drawDeck = this.decks[i];
            Deck discardDeck = this.decks[(i + 1) % this.numPlayers]; 
            this.players[i] = new Player(i + 1, drawDeck, discardDeck, this.gameOver);
        }

        // Deal first 4 cards to each player in round-robin
        for (int round = 0; round < 4; round++) {
            for (int i = 0; i < this.numPlayers; i++) {
                this.players[i].acceptCard(this.pack.drawCard());
            }
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

    /**
     * Discard and take must be treated as ONE atomic action according
     * to the spec (annoyingly) so we manage both from this function.
     * Access to the deck and hand in question needs to be blocked for
     * other threads while both actions occur.
     */
    private void swapCards() {

    }

     /**
     * Takes a fileName string and opens it using {@link java.io.FileDescriptor}
     * and reads it line by line using {@link java.util.Scanner} 
     * Each line it checks if int is negative and throws IllegalArgumentException if it is.
     * It converts valid ints to card objects giving unique cardIds using a counter
     * and adds them to the pack. It then checke if 8n cards were loaded. 
     * if not error thrown.
     */

    public void loadPack(String fileName) throws FileNotFoundException {
        this.pack = new Deck(0);
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);
        int expectedCards = 8 * this.numPlayers;
        int cardCounter = 0;

        while (scanner.hasNextInt()) {
            int value = scanner.nextInt();
            if (value < 0) {
                scanner.close();
                throw new IllegalArgumentException("Card values cant be negative");
            }

            //assigns cardId
            this.pack.addCard(new Card(value, cardCounter));
            cardCounter++;
        }
        scanner.close();

        if (cardCounter != expectedCards) {
            throw new IllegalArgumentException("Wrong number of cards in pack.");        }
    }

    public void startGame() {
        this.initializeDecks();
        this.initializePlayers();
        this.initializeThreads();

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

    // Print final deck files for each player
        private void printFinalDecks() {
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
