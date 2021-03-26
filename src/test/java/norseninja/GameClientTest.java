package norseninja;

import norseninja.blackjack.DeckOfCards;
import norseninja.blackjack.HandOfCards;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameClientTest {

    @Test
    void newGameButtonClicked() {
        DeckOfCards deck = new DeckOfCards();
        HandOfCards playerHand = new HandOfCards();
        HandOfCards dealerHand = new HandOfCards();

        deck.shuffle();
        playerHand.shuffle();
        dealerHand.shuffle();

        deck.dealHand(2).forEach(playerHand::addCard);
        String[] cardList = playerHand.getHandAsString().split(" ");
        assertEquals(2, cardList.length);
        cardList = dealerHand.getHandAsString().split(" ");
        assertEquals("", cardList[0]);
    }
}