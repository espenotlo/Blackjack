package norseninja.blackjack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

/**
 * A deck of cards to be used by the GameClient class.
 * This class handles shuffling and the drawing of cards from the deck.
 */
public class DeckOfCards {
    private final ArrayList<PlayingCard> deck;
    private final static char[] suits = "SHCD".toCharArray();
    private final Random r;

    /**
     * Creates a new Deck with 52 playing cards.
     */
    public DeckOfCards() {
        this.deck = new ArrayList<>();
        this.r = new Random();
        shuffle();
    }

    /**
     * Returns all the cards in the deck in an ArrayList.
     * @return {@code ArrayList<PlayingCard>} of all cards in the deck.
     */
    public ArrayList<PlayingCard> getDeck() {
        return this.deck;
    }

    /**
     * Restores the card deck to 52 cards and shuffles it.
     */
    public void shuffle() {
        this.deck.clear();
        while (this.deck.size() < 52) {
            char suit = suits[r.nextInt(suits.length)];
            int face = r.nextInt(13) + 1;
            boolean cardExists = false;
            for (PlayingCard card : deck) {
                if (card.getAsString().equals(String.format("%s%s", suit, face))) {
                    cardExists = true;
                }
            }
            if (!cardExists) {
                deck.add(new PlayingCard(suit, face));
            }
        }
    }

    /**
     * Draws and returns a number of cards given as parameter in an ArrayList of cards.
     * Throws IllegalArgumentException if no cards remain in the deck.
     * @param n the {@code int} number of cards to be drawn.
     * @return {@code ArrayList<PlayingCard>} of the drawn cards.
     * @throws IllegalArgumentException
     *          if trying to draw more cards than the deck contains.
     */
    public Collection<PlayingCard> dealHand(int n) throws IllegalArgumentException {
        ArrayList<PlayingCard> cardsDrawn;
        if (n <= deck.size()) {
            cardsDrawn = new ArrayList<>();
            for (int numberOfCardsDrawn = 0; numberOfCardsDrawn < n; numberOfCardsDrawn++) {
                PlayingCard cardDrawn = deck.get(r.nextInt(deck.size()));
                deck.remove(cardDrawn);
                cardsDrawn.add(cardDrawn);
            }
        } else {
            throw new IllegalArgumentException();
        }
        return cardsDrawn;
    }
}
