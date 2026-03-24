import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/*
 * Project Title: Console Blackjack Trainer
 * Student Name: Joel
 * Date: [MM/DD/YYYY]
 * Description: A simple Java console Blackjack game that uses variables,
 * control structures, methods, arrays/lists, and basic error handling.
 */
public class App {
    // Slide Ref 01: Variables - class fields to store game state.
    private static List<String> deck = new ArrayList<>();
    // Slide Ref 02: ArrayList - stores player's cards.
    private static List<String> playerHand = new ArrayList<>();
    // Slide Ref 03: ArrayList - stores dealer's cards.
    private static List<String> dealerHand = new ArrayList<>();
    // Slide Ref 04: Scanner - gets input from user.
    private static final Scanner input = new Scanner(System.in);

    /**
     * Starts the program and keeps showing the main menu until the user exits.
     */
    public static void main(String[] args) {
        // Slide Ref 05: Output - print welcome text to console.
        System.out.println("=== Console Blackjack Trainer ===");
        System.out.println("Simple game for Java practice.");

        // Slide Ref 06: while loop - repeats menu until user chooses exit.
        boolean running = true;
        while (running) {
            int choice = readMenuChoice();
            // Slide Ref 07: if-else - choose action based on menu choice.
            if (choice == 1) {
                playRound();
            } else if (choice == 2) {
                printRules();
            } else if (choice == 3) {
                running = false;
                System.out.println("Goodbye.");
            } else {
                System.out.println("Please enter 1, 2, or 3.");
            }
        }

        input.close();
    }

    /**
     * Shows the menu and safely reads a number using try-catch.
     */
    private static int readMenuChoice() {
        System.out.println();
        System.out.println("1) Play a round");
        System.out.println("2) Show rules");
        System.out.println("3) Exit");
        System.out.print("Choose an option: ");

        try {
            // Slide Ref 08: Exception handling - parse number with try-catch.
            String line = input.nextLine().trim();
            return Integer.parseInt(line);
        } catch (NumberFormatException ex) {
            System.out.println("Invalid number. Try again.");
            return -1;
        }
    }

    /**
     * Prints basic blackjack rules in simple language.
     */
    private static void printRules() {
        System.out.println();
        System.out.println("Rules:");
        System.out.println("- Number cards keep their value.");
        System.out.println("- J, Q, K are worth 10.");
        System.out.println("- Ace can be 11 or 1.");
        System.out.println("- Dealer keeps drawing until at least 17.");
    }

    /**
     * Runs one full blackjack round.
     */
    private static void playRound() {
        setupRound();
        showHands(true);

        // Slide Ref 09: while loop - player's turn continues until stand or bust.
        boolean playerTurn = true;
        while (playerTurn) {
            if (handValue(playerHand) > 21) {
                // Slide Ref 10: if statement - bust check.
                System.out.println("You busted. Dealer wins this round.");
                return;
            }

            String action = askPlayerAction();
            // Slide Ref 11: if-else chain - hit or stand decision.
            if ("hit".equals(action)) {
                String card = drawCard();
                playerHand.add(card);
                System.out.println("You drew: " + card);
                showHands(true);
            } else if ("stand".equals(action)) {
                playerTurn = false;
            } else {
                System.out.println("Type 'hit' or 'stand'.");
            }
        }

        dealerTurn();
        showHands(false);
        announceWinner();
    }

    /**
     * Prepares deck and deals two cards to player and dealer.
     */
    private static void setupRound() {
        buildDeck();
        shuffleDeck();
        playerHand.clear();
        dealerHand.clear();

        // Slide Ref 12: method calls - reuse drawCard logic.
        playerHand.add(drawCard());
        dealerHand.add(drawCard());
        playerHand.add(drawCard());
        dealerHand.add(drawCard());

        System.out.println();
        System.out.println("--- New Round ---");
    }

    /**
     * Asks player for hit or stand.
     */
    private static String askPlayerAction() {
        System.out.print("Choose action (hit/stand): ");
        // Slide Ref 13: String methods - trim and lowercase input.
        return input.nextLine().trim().toLowerCase();
    }

    /**
     * Dealer draws cards until hand value is at least 17.
     */
    private static void dealerTurn() {
        System.out.println("Dealer reveals hidden card: " + dealerHand.get(1));
        // Slide Ref 14: while loop - dealer rule in blackjack.
        while (handValue(dealerHand) < 17) {
            String card = drawCard();
            dealerHand.add(card);
            System.out.println("Dealer draws: " + card);
        }
    }

    /**
     * Displays hands, with option to hide dealer's second card.
     */
    private static void showHands(boolean hideDealerCard) {
        // Slide Ref 15: output formatting with helper methods.
        System.out.println("Your hand: " + formatHand(playerHand) + " (value: " + handValue(playerHand) + ")");

        if (hideDealerCard) {
            // Slide Ref 16: conditional display logic for hidden card.
            System.out.println("Dealer hand: " + dealerHand.get(0) + ", [hidden]");
        } else {
            System.out.println("Dealer hand: " + formatHand(dealerHand) + " (value: " + handValue(dealerHand) + ")");
        }
    }

    /**
     * Joins cards in a hand into one readable string.
     */
    private static String formatHand(List<String> hand) {
        return String.join(", ", hand);
    }

    /**
     * Prints result after player and dealer finish their turns.
     */
    private static void announceWinner() {
        int playerScore = handValue(playerHand);
        int dealerScore = handValue(dealerHand);

        // Slide Ref 17: nested if-else logic to decide winner.
        if (dealerScore > 21) {
            System.out.println("Dealer busted. You win!");
        } else if (playerScore > dealerScore) {
            System.out.println("You win!");
        } else if (playerScore < dealerScore) {
            System.out.println("Dealer wins.");
        } else {
            System.out.println("Tie game (push).");
        }
    }

    /**
     * Creates one standard 52-card deck using arrays and loops.
     */
    private static void buildDeck() {
        deck.clear();
        // Slide Ref 18: arrays store fixed suit/rank values.
        String[] suits = {"H", "D", "C", "S"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

        // Slide Ref 19: for loop - build all card combinations.
        for (String suit : suits) {
            for (String rank : ranks) {
                deck.add(rank + suit);
            }
        }
    }

    /**
     * Shuffles the current deck.
     */
    private static void shuffleDeck() {
        Collections.shuffle(deck);
    }

    /**
     * Removes and returns one card from the top of the deck.
     */
    private static String drawCard() {
        return deck.remove(deck.size() - 1);
    }

    /**
     * Calculates total value of a hand with Ace adjustment.
     */
    private static int handValue(List<String> hand) {
        int total = 0;
        int aces = 0;

        // Slide Ref 20: for loop processes every card in hand.
        for (String card : hand) {
            String rank = card.substring(0, card.length() - 1);

            if ("A".equals(rank)) {
                total += 11;
                aces++;
            } else if ("K".equals(rank) || "Q".equals(rank) || "J".equals(rank)) {
                total += 10;
            } else {
                total += Integer.parseInt(rank);
            }
        }

        while (total > 21 && aces > 0) {
            total -= 10;
            aces--;
        }

        return total;
    }
}
