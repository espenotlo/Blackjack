package norseninja;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import norseninja.blackjack.DeckOfCards;
import norseninja.blackjack.HandOfCards;

public class GameClient {
    private final DeckOfCards deck = new DeckOfCards();
    private final HandOfCards playerHand = new HandOfCards();
    private final HandOfCards dealerHand = new HandOfCards();
    public Button drawButton;
    public Button holdButton;
    public Button newGameButton;
    public TilePane playerCardImagePane;
    public TilePane dealerCardImagePane;
    public TextArea log;
    public TextArea playerHandValueArea;
    public TextArea dealerHandValueArea;

    public void newGameButtonClicked() {
        deck.shuffle();
        playerHand.shuffle();
        dealerHand.shuffle();
        deck.dealHand(2).forEach(playerHand::addCard);
        log.setText("DRAW OR HOLD");
        updateImages();
        updateTextAreas();
        drawButton.setDisable(false);
        holdButton.setDisable(false);
    }

    public void drawButtonClicked() {
        deck.dealHand(1).forEach(playerHand::addCard);
        if (sumCardValues(playerHand) > 21) {
            log.setText("BUSTED");
            drawButton.setDisable(true);
            holdButton.setDisable(true);
        }
        updateImages();
        updateTextAreas();
    }

    public void HoldButtonClicked() {
        while(sumCardValues(dealerHand) < 18) {
            deck.dealHand(1).forEach(dealerHand::addCard);
            updateImages();
            updateTextAreas();
        }
        if (sumCardValues(dealerHand) >= sumCardValues(playerHand) && sumCardValues(dealerHand) < 22) {
            log.setText("DEALER WINS");
        } else if (sumCardValues(dealerHand) > 21){
            log.setText("DEALER BUSTS. YOU WIN!");
        } else {
            log.setText("YOU WIN!");
        }
        drawButton.setDisable(true);
        holdButton.setDisable(true);
    }

    private void updateImages() {
        String[] cardList = playerHand.getHandAsString().split(" ");
            ArrayList<String> cards = new ArrayList<>(Arrays.asList(cardList));
            playerCardImagePane.getChildren().clear();
        if (!cardList[0].equals("")) {
            cards.forEach(c -> {
                ImageView imageView = new ImageView("norseninja/cards/" + c + ".png");
                imageView.setPreserveRatio(true);
                imageView.setFitWidth(96);
                playerCardImagePane.getChildren().add(imageView);
            });
        }

        cardList = dealerHand.getHandAsString().split(" ");
            cards = new ArrayList<>(Arrays.asList(cardList));
            dealerCardImagePane.getChildren().clear();
        if (!cardList[0].equals("")) {
            cards.forEach(c -> {
                ImageView imageView = new ImageView("norseninja/cards/" + c + ".png");
                imageView.setPreserveRatio(true);
                imageView.setFitWidth(96);
                dealerCardImagePane.getChildren().add(imageView);
            });
        }

    }

    private void updateTextAreas() {
        playerHandValueArea.setText("" + sumCardValues(playerHand));
        dealerHandValueArea.setText("" + sumCardValues(dealerHand));
    }

    private int sumCardValues(HandOfCards hand) {
        int handSum = 0;
        if (hand.getCardValues().size() > 0) {
            for ( Integer i : hand.getCardValues()) {
                if (i > 10) {
                    handSum += 10;
                } else if (i == 1) {
                    handSum += 11;
                } else {
                    handSum += i;
                }
            }
            int acesConverted = 0;
            while (handSum > 21 && acesConverted < hand.getNumberOfAces()) {
                handSum -= 10;
                acesConverted += 1;
            }
        }
        return handSum;
    }
}
