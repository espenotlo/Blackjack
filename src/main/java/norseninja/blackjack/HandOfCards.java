package norseninja.blackjack;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * A hand of Playing Cards, to be used by the GameClient class.
 * This class handles hand analytics and can do the following:
 *  - add cards to hand
 *  - remove hand
 *  - sum card face values
 *  - count hearts
 *  - check if the queen of spades is in hand
 *  - check if there are at least five cards of the same suit in the hand.
 */
public class HandOfCards {
    private final ArrayList<PlayingCard> cards;
    private final Predicate<PlayingCard> isHeart = c -> c.getSuit() == 'H';


    /**
     * Creates a new, empty hand of cards.
     */
    public HandOfCards() {
        cards = new ArrayList<>();
    }

    /**
     * Adds a card given as parameter to the hand of cards.
     * @param card {@code PlayingCard} to be added to hand.
     */
    public void addCard(PlayingCard card) throws NullPointerException {
        if (null != card) {
            cards.add(card);
        } else {
            throw new NullPointerException();
        }
    }

    /**
     * Checks and returns true if the hand qualifies for a flush (5 cards of same suit).
     * @return {@code true} if 5 cards are of the same suit.
     * @throws NoSuchElementException if hand is empty.
     */
    public boolean checkIfFlush() throws NoSuchElementException {
        HashMap<Character, Integer> suits = (HashMap<Character, Integer>) cards.stream()
                .collect(Collectors.toMap(PlayingCard::getSuit, e -> 1, Integer::sum));

        Optional<Map.Entry<Character, Integer>> maxEntry = suits.entrySet()
                .stream().max(Map.Entry.comparingByValue());

        return (maxEntry.get().getValue() > 4);
    }

    /**
     * Returns the sum of all the card faces in the hand.
     * @return {@code int} sum of all card faces.
     */
    public int sumCardValue() {
        return cards.stream().map(PlayingCard::getFace).reduce(0, Integer::sum);
    }

    public int getNumberOfAces() {
        int numberOfAces = 0;
        for (Integer i : getCardValues()) {
            if (i == 1) {
                numberOfAces += 1;
            }
        }
        return numberOfAces;
    }

    public ArrayList<Integer> getCardValues() {
        ArrayList<Integer> cardValues = new ArrayList<>();
        cards.forEach(c -> {
            cardValues.add(c.getFace());
        });
        return cardValues;
    }

    /**
     * Returns a String of all hearts in the hand.
     * @return {@code String} of all hearts in the hand.
     */
    public String getHeartsAsString() {
        StringBuilder sb = new StringBuilder();
        cards.stream().filter(isHeart).forEach(c -> sb.append(c.getAsString()).append(" "));
        if (sb.length() < 2) {
            sb.append("No Hearts");
        }

        return sb.toString().trim();
    }

    /**
     * Returns true if Queen of Spades is in the hand.
     * @return {@code true} if Queen of Spades is in the hand.
     */
    public boolean hasQueenOfSpades() {
        return (cards.stream().filter(c -> c.getSuit() == 'S').anyMatch(c -> c.getFace() == 12));
    }

    /**
     * Returns a String of all the cards in the hand.
     * @return {@code String} of all the cards in the hand.
     */
    public String getHandAsString() {
        StringBuilder sb = new StringBuilder();
        cards.forEach(c -> sb.append(c.getAsString()).append(" "));

        return sb.toString().trim();
    }

    /**
     * Removes all the cards from the hand.
     */
    public void shuffle() {
        cards.clear();
    }
}
