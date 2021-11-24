/*
POKER PROJECT
TAVIN, ERIK, JOE
COSC 1174
NOVEMBER 23, 2021
FALL SEMESTER
 */


package PokerProject;

public class CheckHand { // class for checking hand
    static Card[] hand = new Card[5]; // array for hand
    static boolean Joker = false; // boolean for Joker
    static boolean Joker2 = false; // boolean for Joker2

    static String checkWinner(Card[] newhand) { // checks for winner
        Joker = false; // reset Joker
        Joker2 = false; // reset Joker2
        // for loop to check for jokers
        System.arraycopy(newhand, 0, hand, 0, 5); // copy new hand to hand
        sortCardsByValue(); // sort hand by value
        String winType; // string for win type

        if (isRoyalFlush()) // check for royal flush
            winType = "RoyalFlush"; // set win type
        else if (isStraightFlush()) // check for straight flush
            winType = "StraightFlush"; // set win type
        else if (isFourOfaKind()) // check for four of a kind
            winType = "FourOfaKind"; // set win type
        else if (isFullHouse()) // check for full house
            winType = "FullHouse"; // set win type
        else if (isFlush()) // check for flush
            winType = "Flush"; // set win type
        else if (isStraight()) // check for straight
            winType = "Straight"; // set win type
        else if (isThreeOfaKind()) // check for three of a kind
            winType = "ThreeOfaKind"; // set win type
        else if (isTwoPair()) // check for two pair
            winType = "TwoPair"; // set win type
        else if (isOnePair()) // check for one pair
            winType = "Pair"; // set win type
        else // check for high card
            winType = ""; // set win type

        return winType; // return win type
    }

    static boolean isRoyalFlush() { // checks for royal flush
        if ((hand[1].getCardValue() == 10) && isStraightFlush()) { // checks for 10
            System.out.println("ROYAL FLUSH"); // prints royal flush
            return true; // returns true
        }
        return false; // returns false
    }


    static boolean isStraightFlush() { // checks for straight flush
        if (isFlush() && isStraight()) { // checks for flush and straight
            System.out.println("StraightFlush"); // prints straight flush
            return true; // returns true
        }
        return false; // returns false
    }


    static boolean isFourOfaKind() { // checks for four of a kind
        if (isThreeOfaKind() && Joker) // checks for three of a kind and joker
            return true; // returns true
        if (hand[0].getCardValue() != hand[1].getCardValue()) { // checks for first card
            for (int i = 1; i < 5; ++i) { // for loop to check for four of a kind
                if (hand[i].getCardValue() != hand[i + 1].getCardValue()) // checks for next card
                    return false; // returns false
            }
            System.out.println("FourOfaKind1"); // prints four of a kind
        } else { // checks for second card
            for (int i = 0; i < 4; ++i) { // for loop to check for four of a kind
                if (hand[i].getCardValue() != hand[i + 1].getCardValue()) // checks for next card
                    return false; // returns false
            }
            System.out.println("FourOfaKind2"); // prints four of a kind
        }
        return true; // returns true
    }


    static boolean isFullHouse() { // checks for full house
        if (isThreeOfaKind() && isTwoPair()) { // checks for three of a kind and two pair
            System.out.println("FullHouse"); // prints full house
            return true; // returns true
        }
        return false; // returns false
    }

    // checks if hand is a flush
    static boolean isFlush() { // checks for flush
        String temp = hand[0].getCardFace(); // string for temp
        for (int i = 1; i <= 4; ++i) { // for loop to check for flush
            if (!hand[i].getCardFace().equals(temp)) // if statement to check for flush
                return false;   // returns false
        }
        System.out.println("Flush"); // prints flush
        return true; // returns true
    }

    // checks hand for a straight
    static boolean isStraight() {
        sortCardsByValue(); // sort hand by value
        for (int i = 0; i < 4; ++i) { // for loop to check for straight
            if (!hand[i].isAce()) { // if statement to check for ace
                if (hand[i].getCardValue() != hand[i + 1].getCardValue() - 1) // if statement to check for straight
                    return false; // returns false
            } else if (hand[0].isAce()) // if statement to check for ace
                if (hand[4].getCardValue() != 13) // if statement to check for ace
                    return false; // returns false

        }
        System.out.println("Straight"); // prints straight
        return true; // returns true
    }

    // checks hand for 3 of a kind
    static boolean isThreeOfaKind() {
        if ((hand[0].getCardValue() == hand[1].getCardValue()) && (hand[0].getCardValue() == hand[2].getCardValue())) {
            System.out.println("ThreeOfaKind1"); // prints three of a kind
            return true; // returns true

        } else if ((hand[1].getCardValue() == hand[2].getCardValue()) && (hand[1].getCardValue() == hand[3].getCardValue())) {
            System.out.println("ThreeOfaKind2"); // prints three of a kind
            return true; // returns true

        } else if ((hand[2].getCardValue() == hand[3].getCardValue()) && (hand[2].getCardValue() == hand[4].getCardValue())) {
            System.out.println("ThreeOfaKind3"); // prints three of a kind
            return true; // returns true
        } else
            return false; // returns false
    }

    // checks hand for 2 pair
    static boolean isTwoPair() {
        if (isOnePair() && Joker) // checks for one pair and joker
            return true; // returns true
        // checks for two pair
        if ((hand[0].getCardValue() == hand[1].getCardValue()) && (hand[2].getCardValue() == hand[3].getCardValue())) {
            System.out.println("TwoPair1"); // prints two pair
            return true; // returns true
        }
        // checks for two pair
        if ((hand[1].getCardValue() == hand[2].getCardValue()) && (hand[3].getCardValue() == hand[4].getCardValue())) {
            System.out.println("TwoPair2"); // prints two pair
            return true; // returns true
        }
        // checks for two pair
        if ((hand[0].getCardValue() == hand[1].getCardValue()) && (hand[3].getCardValue() == hand[4].getCardValue())) {
            System.out.println("TwoPair3"); // prints two pair
            return true; // returns true
        }
        return false; // returns false

    }

    // checks for a pair
    static boolean isOnePair() { // checks for one pair
        if (Joker) // if statement to check for joker
            return true;   // returns true
        for (int i = 0; i <= 3; ++i)
            for (int j = i + 1; j <= 4; ++j)
                if (hand[i].getCardValue() == hand[j].getCardValue()) {
                    System.out.println("Pair");
                    return true;
                }
        return false;
    }

    // Sorts the cards by the face value
    static void sortCardsByValue() { // sorts cards by value
        for (int i = 0; i < 5; ++i) // for loop to sort cards
            if (hand[i].getCardValue() == 99) { // if statement to check for joker
                if (!Joker) // if statement to check for joker
                    Joker = true; // sets joker to true
                else //   if statement to check for joker
                    Joker2 = true;  // sets joker2 to true
            }

        for (int i = 0; i < 4; i++) { // for loop to sort cards

            int min_index = i; // sets min index to i
            for (int j = i + 1; j < 5; j++) // for loop to sort cards
                if (hand[j].getCardValue() < hand[min_index].getCardValue()) // if statement to check for min index
                    min_index = j;


            Card temp = hand[min_index];
            hand[min_index] = hand[i];
            hand[i] = temp;
        }

    }


}
