/**
 * @author XUAN HUY PHAM - 000899551
 */
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.Random;

public class Main extends Application {
    private WeirdCreature weirdCreature;
    private Label instructionLabel;
    private Label foodEatenLabel;
    private Label targetFullnessLabel;
    private Button feedCarrotButton;
    private Button offerFriesButton;
    private Button feedSteakButton;
    private Button offerBeerButton;
    private Button newGameButton;
    private Canvas canvas;
    private GraphicsContext gc;
    private Circle creatureCircle;
    private boolean reachedMaxFullnessWithCarrot;

    @Override
    public void start(Stage primaryStage) {
        //the labels and buttons needed for the game
        //the instruction displayed before the game starts
        instructionLabel = new Label("----------RULE----------\n" + "The unidentified creature is hungry now and you need to feed it \n " +
                "It has a fullness limit randomly change between 90 and 100 every game \n" +
                "If you feed the creature exactly its limit, you win and the creature evolves.\n" +
                "If your feeding exceeds its limit, for example its limit is 95 but you feed it 96, you lose. \n");
        //displaying the current fullness
        foodEatenLabel = new Label("Rabbit's Fullness: 0");
        //displaying the creature's limit
        targetFullnessLabel = new Label("Target Fullness: 0");
        //button to feed creature with carrot and +1
        feedCarrotButton = new Button("Feed Carrot +1");
        feedCarrotButton.setOnAction(e -> {
            weirdCreature.feedCarrot();
            updateCarrotsEatenLabel();
            checkWinCondition();
            drawCreature(); });
        feedCarrotButton.setDisable(true);//the button is disabled before starting a new game


        //button to feed creature with fries and +2
        offerFriesButton = new Button("Offer Mac's Fries +2");
        offerFriesButton.setOnAction(e -> {
            weirdCreature.offerFries();
            updateCarrotsEatenLabel();
            checkWinCondition();
            drawCreature(); });
        offerFriesButton.setDisable(true);//the button is disabled before starting a new game

        //button to feed creature with steak and +3
        feedSteakButton = new Button("Feed Steak +3");
        feedSteakButton.setOnAction(e -> {
            weirdCreature.feedSteak();
            updateCarrotsEatenLabel();
            checkWinCondition();
            drawCreature(); });
        feedSteakButton.setDisable(true);//the button is disabled before starting a new game

        //button to feed creature with beer and +5
        offerBeerButton = new Button("Offer Beer +5");
        offerBeerButton.setOnAction(e -> {
            weirdCreature.offerBeer();
            updateCarrotsEatenLabel();
            checkWinCondition();
            drawCreature(); });
        offerBeerButton.setDisable(true);//the button is disabled before starting a new game

        //start a new game button
        newGameButton = new Button("Start a New Game");
        newGameButton.setOnAction(e -> startNewGame());

        //set up canvas
        canvas = new Canvas(500, 400);
        gc = canvas.getGraphicsContext2D();

        //draw the original creature
        creatureCircle = new Circle(250, 250, 100, Color.WHITESMOKE);

        //Creates a new VBox layout container with a spacing of 10 pixels between its children.
        // The spacing determines the vertical gap between each child element.
        VBox root = new VBox(10);
        //Sets padding around the VBox container. The Insets object defines the size of the padding for each side
        // (top, right, bottom, left). In this case, the padding is set to 20 pixels on all sides.
        root.setPadding(new Insets(20));
        //Adds multiple UI elements to the VBox container.
        // The addAll(...) method takes a variable number of arguments
        // and adds them to the list of children in the order they are provided.
        root.getChildren().addAll(instructionLabel, foodEatenLabel, targetFullnessLabel, feedCarrotButton,
                offerFriesButton, feedSteakButton, offerBeerButton, newGameButton, canvas);

        //setting title
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Feeding Game");
        primaryStage.show();
    }

    private void startNewGame() {
        //Generates a random number between 90 and 100 (inclusive) to determine the target fullness for the new game.
        int targetFullness = new Random().nextInt(11) + 90;
        weirdCreature = new WeirdCreature(targetFullness);
        //Creates a string variable gameInstructions that contains the game instructions, which will be displayed in the instruction label.
        String gameInstructions = "If you feed the creature what it needs, you win and it evolves.\n" +
                "Otherwise, you lose.";
        //Sets the text of label to display the function of that button.
        instructionLabel.setText(gameInstructions);
        foodEatenLabel.setText("Creature's Fullness: 0");
        targetFullnessLabel.setText(""); // Empty label during the game
        //Enables the feed carrot, offer fries, feed steak, and offer beer buttons,
        // allowing the player to interact with them during the game.
        feedCarrotButton.setDisable(false);
        offerFriesButton.setDisable(false);
        feedSteakButton.setDisable(false);
        offerBeerButton.setDisable(false);
        //Disables the new game button, preventing the player from starting a new game while the current game is in progress.
        newGameButton.setDisable(true);
        //the maximum fullness condition has not been reached with a carrot in the new game.
        reachedMaxFullnessWithCarrot = false;
        drawCreature();
    }

    /**
     * This method is to check condition if player win or loose base on the comparison between current and target fullness
     */
    private void checkWinCondition() {
        int targetFullness = weirdCreature.getTargetFullness();
        int currentFullness = weirdCreature.getCurrentFullness();

        // Checks if the current fullness is greater than or equal to the target fullness.
        // If the condition is true, it proceeds with the code
        if (currentFullness >= targetFullness) {
            if (currentFullness > targetFullness) {//the creature has been overfed.
                instructionLabel.setText("\nTOO FULL. NOW YOU ARE A BEAUTIFUL TOMATO \n");
                gc.setFill(Color.RED);
            } else { //player has successfully fed the creature to its exact target fullness.
                instructionLabel.setText("\nWOW YOU WON!!! NOW YOU ARE AN UGLY BUNNY \n");
                gc.setFill(Color.PINK);
            }

            //when the current fullness of the creature reaches or exceeds the target fullness,
            //these buttons will be disabled. no more interaction until starting a new game
            feedCarrotButton.setDisable(true);
            offerFriesButton.setDisable(true);
            feedSteakButton.setDisable(true);
            offerBeerButton.setDisable(true);
            newGameButton.setDisable(false);
            reachedMaxFullnessWithCarrot = true;
            targetFullnessLabel.setText("Target Fullness: " + targetFullness); // Show the target fullness/create's limit
        }
    }

    /**
     * thiss method is to visually draw the original creature base on its current fullness. start at 0
     * its size will change based on its fullness percent
     */
    private void drawCreature() {
        //clears the entire canvas by filling it with a transparent color,
        // effectively erasing any previous drawings
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        //ddefine the dimensions of the progress bar that represents the creature's fullness.
        double barWidth = 150;
        double barHeight = 20;
        // Retrieves the current fullness value of the creature from the weirdCreature object.
        double fullness = weirdCreature.getCurrentFullness();
        //Calculates the maximum width of the progress bar based on the current fullness relative
        // to the target fullness, it determines how much of the progress bar should be filled
        double maxWidth = barWidth * fullness / weirdCreature.getTargetFullness();

        //visually design the progress bar
        gc.setFill(Color.GRAY);//empty bar
        gc.fillRect(0, 0, barWidth, barHeight);
        gc.setFill(Color.HOTPINK);
        gc.fillRect(0, 0, maxWidth, barHeight);

        //calculates the size of the creature's circle based on its fullness,
        // the size is increased by adding the current fullness to a base size of 100.
        double size = 100 + fullness;
        //sets the radius of the creatureCircle to the calculated size
        //this determines the size of the creature's body, the more creawtureCircle is, the bigger the creature is
        creatureCircle.setRadius(size);


        //change color of the creature base on the condition
        if (reachedMaxFullnessWithCarrot) {
            //if currentfullness exceeds the limit, creature turns into red, otherwwise, it turns pink
            if (weirdCreature.getCurrentFullness() > weirdCreature.getTargetFullness()) {
                gc.setFill(Color.RED);
            } else {
                gc.setFill(Color.PINK);
            }
        } else {
            gc.setFill(Color.WHITESMOKE);//its original color
        }

        // Set the stroke color to black and visually draw the oval/creature
        gc.setStroke(Color.BLACK);
        gc.strokeOval(creatureCircle.getCenterX() - size / 2, creatureCircle.getCenterY() - size / 2, size, size);
        gc.fillOval(creatureCircle.getCenterX() - size / 2, creatureCircle.getCenterY() - size / 2, size, size);

        // Draw eyes of the creature
        double eyeRadius = size * 0.1; // adjust the eye size as desired
        double eyeOffsetX = size * 0.2; // adjust the eye position as desired
        double eyeOffsetY = size * 0.2; // adjust the eye position as desired
        double leftEyeX = creatureCircle.getCenterX() - size / 1.8 + eyeOffsetX;
        double leftEyeY = creatureCircle.getCenterY() - size / 2 + eyeOffsetY;
        double rightEyeX = creatureCircle.getCenterX() + size / 2 - eyeOffsetX;
        double rightEyeY = creatureCircle.getCenterY() - size / 2 + eyeOffsetY;
        gc.setFill(Color.BLACK);
        gc.fillOval(leftEyeX, leftEyeY, eyeRadius, eyeRadius);
        gc.fillOval(rightEyeX, rightEyeY, eyeRadius, eyeRadius);


        // If player wins (current=target), creature will evolve and have a new appearance
        if (weirdCreature.getCurrentFullness() == weirdCreature.getTargetFullness()) {
            // Draw a mouth of rabbit (won), with teeth
            gc.setFill(Color.BLACK);
            gc.fillOval(225, 270, 50, 30);
            gc.setFill(Color.WHITE);
            gc.fillRect(238,270,10,15);
            gc.fillRect(251,270,10,15);
            //tiny nose (won)
            gc.setFill(Color.BLACK);
            gc.fillOval(242,240,15,8);
            //blush (won)
            gc.setFill(Color.HOTPINK);
            gc.fillOval(180,240,30,30);
            gc.fillOval(290,240,30,30);
            //ears (won)
            gc.setFill(Color.PINK);
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(1.0);
            gc.strokeOval(180,50,30,120);
            gc.strokeOval(290,50,30,120);
            gc.fillOval(180,50,30,120);
            gc.fillOval(290,50,30,120);
            //collar (won)
            gc.setFill(Color.BLACK);
            gc.fillRect(230,345,40,15);

        }

        //the creature will have another difference look when player looses
        if (weirdCreature.getCurrentFullness() > weirdCreature.getTargetFullness()) {
            //i don't know how to call this part of the tomato, knob??? maybe
            gc.setFill(Color.GREEN);
            gc.fillOval(240,115,20,40);
            gc.fillOval(220,135,30,20);
            gc.fillOval(250,135,30,20);
            gc.setFill(Color.BLACK);
            gc.fillOval(225, 270, 50, 30);
            gc.setFill(Color.WHITE);
            gc.fillRect(238,270,10,15);
            gc.fillRect(251,270,10,15);
        }
    }
    private void updateCarrotsEatenLabel() {//updating the label that displays the creature's fullness on the user interface
        // sets the text of the foodEatenLabel to a string that concatenated with the current fullness value
        //obtained from the weirdCreature object.
        foodEatenLabel.setText("Creature's Fullness: " + weirdCreature.getCurrentFullness());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
