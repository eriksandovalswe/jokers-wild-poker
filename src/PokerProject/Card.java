/*
POKER PROJECT
TAVIN, ERIK, JOE
COSC 1174
NOVEMBER 23, 2021
FALL SEMESTER
 */

package PokerProject;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Card {

    private int cardValue; // 0-12
    private String cardFace;
    private final ImageView cardImageView; // imageView for card image
    private final Image cardImage; // image of card

    public Card(int i) { // constructor for card
        if (i <= 52) { // if i is less than 52
            cardValue = ((i) % 13); // cardValue is the remainder of i divided by 13
            if (cardValue == 0) // if cardValue is 0
                cardValue = 13; // cardValue is 13
            // switch statement for cardFace
            // if i is less than 13
            // if i is less than 26
            // if i is less than 39
            // if i is less than 52
            switch ((i - 1) / 13) {
                case 0:
                    cardFace = "Spades";
                    break;
                case 1:
                    cardFace = "Hearts";
                    break;
                case 2:
                    cardFace = "Diamonds";
                    break;
                case 3:
                    cardFace = "Clubs";
                    break;
            }

        }

        cardImageView = new ImageView(new Image("/card/" + i + ".png")); // sets the imageview to the image
        cardImage = new Image("/card/" + i + ".png"); // sets the image to the imageview
    }

    public int getCardValue() {
        return cardValue;
    } // returns cardValue

    public String getCardFace() {
        return cardFace;
    } // returns cardFace

    public Image getCardImage() {
        return cardImage;
    } // returns cardImage

    public ImageView getCardImageView() {
        return cardImageView;
    } // returns cardImageView

    public boolean isAce() {
        return cardValue == 1;
    } // returns true if cardValue is 1
}
