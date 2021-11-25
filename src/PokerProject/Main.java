
package PokerProject;



import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;




public class Main extends Application {



    //  GLOBAL ELEMENTS
    static Boolean[] discard = new Boolean[5]; // array of booleans to keep track of which cards have been discarded
    static ArrayList<Card> cardList = new ArrayList<>(); // arraylist to keep track of all cards
    static ImageView[] imagesDisplayed = new ImageView[5]; // array of imageviews to keep track of the images displayed
    static Card[] currentHand = new Card[5]; // array of cards to keep track of the current hand
    static int bank = 200; // starting bank
    static int bet = 0; // current bet
    String winnerhand = "hello"; // string to keep track of the winner's hand



    //********************************************MAIN-METHOD***************************************************
    @SuppressWarnings("DuplicatedCode")
    @Override
    public void start(Stage primaryStage) {
        setDeck();


        // *************************************INITIAL-HAND***************************************************
        for (int i = 0; i < 5; ++i) { // loop to create the imageviews for the cards
            currentHand[i] = cardList.remove(i); // removes the card from the deck and adds it to the current hand
            imagesDisplayed[i] = currentHand[i].getCardImageView(); // adds the imageview to the array
        }
        for (int i = 0; i < 5; ++i) {
            discard[i] = false;
        }
        // ************************************END-INITIAL-HAND***************************************************




        //************************************LAYOUT-AREAS-ELEMENTS**************************************************
        Pane root = new VBox(); // root pane
        HBox cardHBox = new HBox(); // card box
        HBox DrwDlBtns = new HBox(); // draw and deal buttons box
        HBox betbox = new HBox(); // bet box
        HBox bankbox = new HBox(); // bank box
        HBox win = new HBox(); // win box
        HBox DiscardHBox = new HBox(); // discard box
        //*********************************END-LAYOUT-AREAS-ELEMENTS**************************************************





        //************************************TEXT-ELEMENTS**************************************************
        Text banktext = new Text("Bank: "); // text for bank
        Text bankamount = new Text("$" + bank); // text for bank amount
        Text winning = new Text("Winning Hand:        "); // text for winning hand
        Text winhand = new Text(winnerhand); // text for winning hand element
        TextField betuse = new TextField(); // textfield for bet amount
        Text betamount = new Text("Bet:        "); // text for bet amount
        //*********************************END-TEXT-ELEMENTS**************************************************






        // **********************************BET-TEXT*******************************************
        TextField tfBetAmount = new TextField(); // textfield for bet amount




        // **********************************END-BET-TEXT*******************************************








		//************************************DEAL-BUTTON***********************************************

        Button dealBtn = new Button("Deal"); // deal button
        dealBtn.setFont(Font.font("Arial")); // sets the font of the button
        dealBtn.setMinHeight(50);
        dealBtn.setMinWidth(125);
        dealBtn.setOnAction(e -> { // action for deal button
            bank -= bet; // subtracts the bet from the bank
            bankamount.setText("$" + (bank)); // sets the bank amount to the new bank amount

            for (int i = 0; i < 5; ++i)  // loop to remove the cards from the current hand
                discard[i] = false; // sets the discard array to true
            System.out.println(cardList.size()); // prints the size of the deck DEBUGGING


            for (int i = 0; i < 5; ++i) {

                imagesDisplayed[i].setImage(new Image("/card/b2fv.png")); // sets the image to the back of the card

                //Animation to take cards out
                PathTransition out = new PathTransition(); // path transition for the card
                out.setNode(imagesDisplayed[i]); // sets the node to the imageview
                out.setPath(new Line(50, 150, 50, 1000)); // sets the path to the line
                out.setDuration(Duration.millis(750)); // sets the duration to 750 milliseconds
                out.play(); // plays the animation


                currentHand[i] = cardList.remove(i); // removes the card from the deck
                System.out.println(currentHand[i].getCardValue() + " " + currentHand[i].getCardFace()); //used in testing


                final int temp = i; // temp variable to hold the index of the card
                out.setOnFinished(f -> { // action for when the animation is finished
                    imagesDisplayed[temp].setImage(currentHand[temp].getCardImage()); // sets the image to the new card
                    PathTransition in = new PathTransition(); // path transition for the card
                    in.setNode(imagesDisplayed[temp]); // sets the node to the imageview
                    in.setPath(new Line(50, 0, 50, 75)); // sets the path to the line
                    in.setDuration(Duration.millis(750)); // sets the duration to 750 milliseconds
                    in.play(); // plays the animation
                    // calls isWinningHand() on finish of animation
                    out.setOnFinished(q -> isWinningHand()); // action for when the animation is finished
                });

            }

            if (cardList.size() < 8) { // if the deck is less than 8 cards
                setDeck(); // calls the setDeck method
            }
            isWinningHand(); // calls the winning hand method
            bankamount.setText("$" + bank); // sets the bank amount to the new bank amount
            System.out.println(bank); // prints the new bank amount DEBUGGING
        });


		// *******************************END-DEAL-BUTTON*********************************************










        //**********************************DRAW-BUTTON***********************************************
        Button drawBtn = new Button("Draw"); // draw button
        drawBtn.setFont(Font.font("Arial")); // sets the font of the button
        drawBtn.setMinHeight(50); // set min height
        drawBtn.setMinWidth(125); // set min width
        drawBtn.setOnAction(e -> { // action for draw button
            if (bank <= 0) {
                System.exit(0);
            }
            bet = Integer.parseInt(tfBetAmount.getText());

            bank -= bet; // subtracts the bet from the bank
            bankamount.setText("$" + (bank)); // sets the bank amount to the new bank amount

            System.out.println(cardList.size()); /// prints the size of the deck DEBUGGING
            System.out.println("Bet Amount: " + bet); // prints the bet amount DEBUGGING

            for (int i = 0; i < 5; ++i) {   // loop to remove the cards from the current hand

                if (discard[i]) { // if the card is discarded


                    PathTransition out = new PathTransition(); // path transition for the card
                    out.setNode(imagesDisplayed[i]); // sets the node to the imageview
                    out.setPath(new Line(50, 150, 50, 1000)); // sets the path to the line
                    out.setDuration(Duration.millis(750)); // sets the duration to 750 milliseconds
                    out.play(); // plays the animation


                    // draws new cards
                    currentHand[i] = cardList.remove(i); // removes the card from the deck
                    System.out.println(currentHand[i].getCardValue() + " " + currentHand[i].getCardFace());


                    final int temp = i; // temp variable to hold the index of the card
                    out.setOnFinished(f -> { // action for when the animation is finished
                        imagesDisplayed[temp].setImage(currentHand[temp].getCardImage()); // sets the image to the new card
                        PathTransition in = new PathTransition(); // path transition for the card
                        in.setNode(imagesDisplayed[temp]); // sets the node to the imageview
                        in.setPath(new Line(50, 0, 50, 75)); // sets the path to the line
                        in.setDuration(Duration.millis(750)); // sets the duration to 750 milliseconds
                        in.play(); // plays the animation

                        out.setOnFinished(q -> isWinningHand()); // action for when the animation is finished
                    });
                    //Assigns discarded cards to true so that they can be discarded when deck is reshuffled.
                    discard[i] = false;
                }

                if (cardList.size() < 8) { // if the deck is less than 8 cards
                    setDeck(); // calls the setDeck method
                }
            }
            isWinningHand(); // calls the winning hand method
            bankamount.setText("$" + bank); // sets the bank amount to the new bank amount
            System.out.println(bank); // prints the new bank amount DEBUGGING
            winhand.setText(""); // sets the winhand text to nothing


        });
        //**********************************END-DRAW-BUTTON***********************************************








        //*************************************DISCARD-BUTTONS-ACTIONS***************************************************
        Button Discardbtn0 = new Button("Discard"); // Discard button
        Button Discardbtn1 = new Button("Discard"); // Discard button
        Button Discardbtn2 = new Button("Discard"); // Discard button
        Button Discardbtn3 = new Button("Discard"); // Discard button
        Button Discardbtn4 = new Button("Discard"); // Discard button
        Discardbtn0.setMinHeight(50); // set min height
        Discardbtn0.setMinWidth(75); // set min width
        Discardbtn1.setMinHeight(50);
        Discardbtn1.setMinWidth(75);
        Discardbtn2.setMinHeight(50);
        Discardbtn2.setMinWidth(75);
        Discardbtn3.setMinHeight(50);
        Discardbtn3.setMinWidth(75);
        Discardbtn4.setMinHeight(50);
        Discardbtn4.setMinWidth(75);
        DiscardHBox.setAlignment(Pos.CENTER); // set alignment
        DiscardHBox.setSpacing(25); // set spacing

        Discardbtn0.setOnMouseClicked(e -> { // action for button 0
            if (!discard[0]) { // if card is discarded
                imagesDisplayed[0].setImage(new Image("/card/b2fv.png")); // set image to back of card
                discard[0] = true;
            } else { // if card is not discarded
                discard[0] = false; // set card to be discarded
                imagesDisplayed[0].setImage(currentHand[0].getCardImage()); // set image to card
            }
            System.out.println(discard[0]); // print discard status
        });

        Discardbtn1.setOnMouseClicked(e -> { // action for button 1
            if (!discard[1]) { // if card is discarded
                imagesDisplayed[1].setImage(new Image("/card/b2fv.png")); // set image to back of card
                discard[1] = true; // set card to not be discarded
            } else { // if card is not discarded
                discard[1] = false; // set card to be discarded
                imagesDisplayed[1].setImage(currentHand[1].getCardImage()); // set image to card
            }
        });
        Discardbtn2.setOnMouseClicked(e -> { // action for button 2
            if (!discard[2]) { // if card is discarded
                imagesDisplayed[2].setImage(new Image("card/b2fv.png")); // set image to back of card
                discard[2] = true; // set card to not be discarded
            } else { // if card is not discarded
                discard[2] = false; // set card to be discarded
                imagesDisplayed[2].setImage(currentHand[2].getCardImage()); // set image to card
            }
        });
        Discardbtn3.setOnMouseClicked(e -> { // action for button 3
            if (!discard[3]) { // if Discard button 3 is true
                imagesDisplayed[3].setImage(new Image("card/b2fv.png")); // set image to back of card
                discard[3] = true; // set Discard button 3 to false
            } else { // if Discard button 3 is false
                discard[3] = false; // set Discard button 3 to true
                imagesDisplayed[3].setImage(currentHand[3].getCardImage()); // set image to card
            }
        });
        Discardbtn4.setOnMouseClicked(e -> { // action for button 4
            if (!discard[4]) { // if Discard button 4 is true
                imagesDisplayed[4].setImage(new Image("card/b2fv.png")); // set image to back of card
                discard[4] = true; // set Discard button 4 to false
            } else { // if Discard button 4 is false
                discard[4] = false; // set button 4 to true
                imagesDisplayed[4].setImage(currentHand[4].getCardImage()); // set image to card
            }
        });
        //*****************************END-DISCARD-BUTTONS-ACTIONS******************************************************







        //**********************ADD-ALL-COMPONENTS-TO-ROOT**************************************
        DiscardHBox.getChildren().addAll(Discardbtn0, Discardbtn1, Discardbtn2, Discardbtn3, Discardbtn4);
        betbox.getChildren().addAll(betamount, tfBetAmount); // adds the radio bet buttons to the bet box
        bankbox.getChildren().addAll(banktext, bankamount); // adds bank children to bank box
        win.getChildren().addAll(winning, winhand); // adds winning children to win box
        cardHBox.getChildren().addAll( // adds card children to card box
                imagesDisplayed[0],
                imagesDisplayed[1],
                imagesDisplayed[2],
                imagesDisplayed[3],
                imagesDisplayed[4]
        );
        for (int i = 0; i < 5; ++i) { //Sets the dimensions of the images
                imagesDisplayed[i].fitHeightProperty().bind(root.heightProperty().subtract(150));
                imagesDisplayed[i].fitWidthProperty().bind(root.widthProperty().divide(5));
        }


        DrwDlBtns.setAlignment(Pos.CENTER); // sets the alignment of the draw buttons
        DrwDlBtns.getChildren().addAll(drawBtn, dealBtn); // adds the draw and deal buttons to the draw and deal buttons box
        DrwDlBtns.setSpacing(50); // sets the spacing of the draw and deal buttons box
        root.getChildren().addAll(cardHBox, DiscardHBox, DrwDlBtns, bankbox, betbox); // adds all the children to the root
        //***********************END-ADD-ALL-COMPONENTS-TO-ROOT**********************************




        //****************************DISPLAY-WINDOW*******************************************
        Scene scene = new Scene(root, 500, 300); // sets the scene
        primaryStage.setScene(scene); // displays the scene
        primaryStage.setTitle("POKER PROJECT"); // sets the title
        primaryStage.show(); // displays the stage
        //***************************END-DISPLAY-WINDOW****************************************
    }
    //********************************END-MAIN-METHOD******************************************












    //**************************************METHODS*******************************************
    public static void isWinningHand() { // checks if the hand is a winning hand

        String hand = CheckHand.checkWinner(currentHand); // checks the hand

        switch(hand) {
            case "RoyalFlush":
                bank += (bet * 800);
                break;
            case "StraightFlush":
                bank += (bet * 200);
                break;
            case "FourOfaKind":
                bank += (bet * 30);
                break;
            case "FullHouse":
                bank += (bet * 20);
                break;
            case "Flush":
                bank += (bet * 10);
                break;
            case "Straight":
                bank += (bet * 7);
                break;
            case "ThreeOfaKind":
                bank += (bet * 5);
                break;
            case "TwoPair":
                bank += (bet * 3);
                break;
        }


        System.out.println("Bank Amount: " + bank); // prints the bank amount
        System.out.println("Bet Amount: " + bet); // prints the bet amount
        System.out.println("Current Hand: " + hand); // prints the current hand

    }

    private static void setDeck() { // sets the deck
        cardList.clear(); // clears the deck
        for (int i = 1; i <= 54; ++i) // adds all cards to the deck
            cardList.add(new Card(i)); // adds card to deck
        shuffleDeck(); // shuffles the deck
        System.out.println("Deck Set"); // prints deck set
    }


    private static void shuffleDeck() { // shuffles the deck
        java.util.Collections.shuffle(cardList); // shuffles the deck
    }
    //**************************************END-METHODS*****************************************
}
    

