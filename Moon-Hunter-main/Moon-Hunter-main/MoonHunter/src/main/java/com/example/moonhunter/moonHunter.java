package com.example.moonhunter;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Scanner;

import static java.lang.Math.random;

public class moonHunter extends Application {
    // Creating needed panes and boxes...
    Pane pane = new StackPane();
    VBox start = new VBox();
    VBox versionBox = new VBox();
    VBox scoreBox = new VBox();
    VBox remainingBox = new VBox();
    HBox restartMenu = new HBox();
    Scene scene = new Scene(pane, 1000, 900);


    // images...
    Image img1 = new Image(moonHunter.class.getResource("startButton.png").toString());
    Image img2 = new Image(moonHunter.class.getResource("Quit.png").toString());
    Image img3 = new Image(moonHunter.class.getResource("tryAgain.png").toString());
    Image img4 = new Image(moonHunter.class.getResource("title.png").toString());
    Image img5 = new Image(moonHunter.class.getResource("moon1.png").toString());
    Image img6 = new Image(moonHunter.class.getResource("moon2.png").toString());
    Image img7 = new Image(moonHunter.class.getResource("moon3.png").toString());
    Image img8 = new Image(moonHunter.class.getResource("moon4.png").toString());
    Image img9 = new Image(moonHunter.class.getResource("moon5.png").toString());
    Image img10 = new Image(moonHunter.class.getResource("Rules.png").toString());
    Image img11 = new Image(moonHunter.class.getResource("space.jpg").toString());


    ImageView startButtonBackground = new ImageView(img1);
    ImageView quitButtonBackground = new ImageView(img2);
    ImageView restartButtonBackground = new ImageView(img3);

    ImageView title = new ImageView(img4);
    ImageView body = new ImageView(img5);
    ImageView body1 = new ImageView(img6);
    ImageView body2 = new ImageView(img7);
    ImageView body3 = new ImageView(img8);
    ImageView body4 = new ImageView(img9);
    ImageView rulesScreen = new ImageView(img10);
    ImageView backGround1 = new ImageView(img11);

    // the height and the width of the buttons as a global variables...
    int width = 300, height = 105;
    int score = 0, remaining = 30;
    Moon moon = new Moon();
    ArrayList<Integer> ranks = new ArrayList<>();
    int[] TopRanks = new int[5];
    double y = 25, y1 = 25, y2 = 25, y3 = 25, y4 = 25;


    // Texts and buttons...
    Text version = new Text(5, 10, "V 1.03");
    Text scoreText = new Text(5, 10, "Score: " + score);
    Text remainingText = new Text(5, 10, "Remaining: " + remaining);
    Text top = new Text(225, 100, "Top 5: ");
    Button startButton = new Button("", startButtonBackground);
    Button quitButton = new Button("", quitButtonBackground);
    Button restartButton = new Button("", restartButtonBackground);


    // and finally opening the file...
    File file = new File("Scores.txt");


    @Override
    public void start(Stage stage) {
        // setting the background image
        backGround1.setFitHeight(900);
        backGround1.setFitWidth(1000);
        pane.getChildren().add(backGround1);


        // then designing the game title and aligned it in the middle
        title.setFitHeight(130);
        title.setFitWidth(750);
        restartMenu.setSpacing(4.5);


        // the version designing
        version.setFill(Color.WHITE);
        version.setStyle("-fx-font: 15 arial;");
        version.setVisible(true);


        // the score designing
        scoreText.setFill(Color.WHITE);
        scoreText.setStyle("-fx-font: 25 arial;");
        scoreText.setVisible(true);


        // the remaining designing
        remainingText.setFill(Color.WHITE);
        remainingText.setStyle("-fx-font: 25 arial;");
        remainingText.setVisible(true);

        // then add the remaining text to the box
        remainingBox.getChildren().add(remainingText);
        remainingBox.setAlignment(Pos.BASELINE_RIGHT);

        // the score also
        scoreBox.getChildren().add(scoreText);
        scoreBox.setAlignment(Pos.BASELINE_LEFT);

        // adding the version into the pane to make it visible
        versionBox.getChildren().add(version);
        versionBox.setAlignment(Pos.BOTTOM_LEFT);

        // setting the restartButton functionality
        restartButton.setOnAction(e -> {
            try {
                restarting(getVbox(TopRanks,false));
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
        restartButton.setOpacity(0.5);
        restartButton.setAlignment(Pos.CENTER_LEFT);
        restartButton.setOnMouseExited(e -> buttonHoveringOut(restartButton));
        restartButton.setOnMouseEntered(e -> buttonHoveringIn(restartButton));


        restartMenu.setVisible(false);
        restartMenu.setAlignment(Pos.CENTER);

        restartButtonBackground.setFitWidth(width - 50);
        restartButtonBackground.setFitHeight(height - 15);
        restartMenu.getChildren().add(restartButton);


        // adding everything to the main pane
        pane.getChildren().addAll(versionBox);
        start.getChildren().addAll(title, startButton, quitButton);
        mainMenu();
        pane.getChildren().add(start);

        // setting the stage settings
        stage.setTitle("Moon Hunter");
        stage.setScene(scene);
        stage.initStyle(StageStyle.DECORATED);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public void mainMenu() {
        title.setVisible(true);

        // adding both buttons to the needed width and height
        startButtonBackground.setFitWidth(width);
        startButtonBackground.setFitHeight(height);
        //.................................
        quitButtonBackground.setFitWidth(width - 50);
        quitButtonBackground.setFitHeight(height - 15);

        // setting the START button to be more active to the mouse and what to do when clicked
        startButton.setOnAction(e -> rules());
        startButton.setOpacity(0.5);
        startButton.setVisible(true);
        startButton.setAlignment(Pos.CENTER);
        startButton.setOnMouseExited(e -> buttonHoveringOut(startButton));
        startButton.setOnMouseEntered(e -> buttonHoveringIn(startButton));


        // setting the QUIT button to be more active to the mouse and what to do when clicked
        quitButton.setOnAction(e -> Platform.exit());
        quitButton.setOpacity(0.5);
        quitButton.setVisible(true);
        quitButton.setAlignment(Pos.CENTER);
        quitButton.setOnMouseExited(e -> buttonHoveringOut(quitButton));
        quitButton.setOnMouseEntered(e -> buttonHoveringIn(quitButton));

        // aligning the start menu
        start.setSpacing(55);
        start.setVisible(true);
        start.setOpacity(0.5);
        start.setAlignment(Pos.CENTER);


    }


    public void buttonHoveringOut(Button button) {
        // this function will make the button smaller and dimmer if the mouse did not enter its area.
        button.setOpacity(0.5);

        startButtonBackground.setFitHeight(height);
        startButtonBackground.setFitWidth(width);
    }


    public void buttonHoveringIn(Button button) {
        // this function will make the button bigger and lighter if the mouse did enter its area.
        button.setOpacity(1.5);

        startButtonBackground.setFitHeight(height + 10);
        startButtonBackground.setFitWidth(width + 20);
    }


    public VBox getVbox(int[] top5 , boolean winning) throws FileNotFoundException {
        Label Score;

        // decorating the top5 menu...
        VBox vBox = new VBox(5);
        vBox.setAlignment(Pos.CENTER);


        Label Top5 = new Label("TOP 5");
        Top5.setTextFill(Color.WHITE);
        Top5.setStyle("-fx-font: 50 arial;");

        if (winning){
            Score = new Label("Congratulations you won!\n\tYour score is: "+score);
        }else {
            Score = new Label("Your score is: " + score);
        }
        Score.setAlignment(Pos.CENTER);
        Score.setTextFill(Color.WHITE);
        Score.setStyle("-fx-font: 30 arial;");

        vBox.getChildren().addAll(Top5, Score);


        // reading the file to get all the scores
        Scanner input = new Scanner(file);
        while (input.hasNext()) {
            int next = input.nextInt();
            if (!ranks.contains(next)) {
                ranks.add(next);
            }
        }


        // sorting all the scores in the file
        ranksSorting(ranks);


        // taking the top5 only
        for (int i = 0; i < 5; i++) {
            try {
                TopRanks[i] = ranks.get(i);
            } catch (Exception ex) {
                TopRanks[i] = 0;
            }
        }


        int order = 1; // to mark the position

        // displaying each score in a Vbox
        for (int element : top5) {
            Label label = new Label(order + "- " + element);
            label.setTextFill(Color.WHITE);
            label.setStyle("-fx-font: 25 arial;");
            vBox.getChildren().add(label);
            order++;
        }

        // to avoid adding the same quitButton that will result in an error
        if (!restartMenu.getChildren().contains(quitButton)) {
            restartMenu.getChildren().add(quitButton);
        }

        // to start the game again
        restartButton.setOnAction(e -> restarting(vBox));
        vBox.getChildren().addAll(restartMenu);
        restartMenu.setVisible(true);
        vBox.setVisible(true);
        vBox.setSpacing(45);
        return vBox;
    }

    public ArrayList<Integer> ranksSorting(ArrayList<Integer> ranks) {
        // sorting the scores from the high to the low ones

        Collections.sort(ranks, Collections.reverseOrder());
        return ranks;
    }

    public void addToFile(int score) throws IOException {
        // this function will add the score to the file

        FileWriter writer = new FileWriter(file, true);
        PrintWriter writer1 = new PrintWriter(writer);
        if (score != 0){
            writer1.println(score);
        }
        writer1.close();

    }




    public class Moon extends Pane {
        // crating the animation of the moon sliding down
        private Timeline animation, animation1, animation2, animation3, animation4;

        // these counters should indicate the number of times the object is clicked
        private int count = 0;
        private int count1 = 0;
        private int count2 = 0;
        private int count3 = 0;
        private int count4 = 0;
        private int totalCount = 0;

        public Moon() {
            // this constructor will set the moon to the start position then add it to the pane
            arrange(body,75);

            arrange(body1,65);

            arrange(body2,55);

            arrange(body3,45);

            arrange(body4,35);


            getChildren().addAll(body, body1, body2, body3, body4);

        }


        public void arrange(ImageView body , int size){
            // this function should arrange the Image it gets
            body.setFitWidth(size);
            body.setFitHeight(size);
            body.setViewOrder(0);
            body.setX(random() * 850);
            body.setY(25);
            body.setVisible(true);
        }

        public void pause() {
            animation4.pause();
            animation3.pause();
            animation2.pause();
            animation1.pause();
            animation.pause();
        }


        public void play() {
            scoreText.setText("Score: " + score);
            remainingText.setText("Remaining: " + (remaining));
            scoreBox.setVisible(true);
            remainingBox.setVisible(true);

            body.setVisible(true);
            body1.setVisible(true);
            body2.setVisible(true);
            body3.setVisible(true);
            body4.setVisible(true);


            count = 0;
            count1 = 0;
            count2 = 0;
            count3 = 0;
            count4 = 0;
            totalCount = 0;

            animation = new Timeline(
                    new KeyFrame(Duration.millis(18), e -> {
                        try {
                            moveBall();
                        } catch (FileNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                    }));
            animation.setCycleCount(Timeline.INDEFINITE);
            animation.play();


            animation1 = new Timeline(
                    new KeyFrame(Duration.millis(16), e -> {
                        try {
                            moveBall1();
                        } catch (FileNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                    }));
            animation1.setCycleCount(Timeline.INDEFINITE);
            animation1.play();

            animation2 = new Timeline(
                    new KeyFrame(Duration.millis(14), e -> {
                        try {
                            moveBall2();
                        } catch (FileNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                    }));
            animation2.setCycleCount(Timeline.INDEFINITE);
            animation2.play();

            animation3 = new Timeline(
                    new KeyFrame(Duration.millis(12), e -> {
                        try {
                            moveBall3();
                        } catch (FileNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                    }));
            animation3.setCycleCount(Timeline.INDEFINITE);
            animation3.play();

            animation4 = new Timeline(
                    new KeyFrame(Duration.millis(10), e -> {
                        try {
                            moveBall4();
                        } catch (FileNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                    }));
            animation4.setCycleCount(Timeline.INDEFINITE);
            animation4.play();

        }

        public void increaseSpeed(Timeline animation) {
            // this increase is a bit slow just to make the game more fun to play
            animation.setRate(animation.getRate() + 0.6);
        }


        private void moveBall() throws FileNotFoundException {

            body.setOnMouseClicked(e -> {
                if (e.getButton() == MouseButton.PRIMARY) {
                    score += 1;
                    y = 25;
                    count++;
                    remove(body,animation);
                }

            });

            // if the moon was clicked 6 times it will disappear
            if (count == 6) {
                animation.pause();
                body.setVisible(false);
            }

            if (body.getY() > 820) /* if the moon touched the border */ {
                end(false);
            } else if (totalCount == 30) {
                end(true);
            }

            // increasing the Y means moving downward
            body.setY(y++);


        }


        private void moveBall1() throws FileNotFoundException {

            body1.setOnMouseClicked(e -> {
                if (e.getButton() == MouseButton.PRIMARY) {

                    score += 2;
                    y1 = 25;
                    count1++;
                    remove(body1,animation1);
                }

            });

            if (count1 == 6) {
                animation1.pause();
                body1.setVisible(false);
            }

            if (body1.getY() > 830) /* if the moon touched the border */ {
                end(false);
            } else if (totalCount == 30) {
                end(true);
            }
            // increasing the Y means moving downward
            body1.setY(y1++);

        }

        private void moveBall2() throws FileNotFoundException {

            body2.setOnMouseClicked(e -> {
                if (e.getButton() == MouseButton.PRIMARY) {
                    score += 3;
                    y2 = 25;
                    count2++;
                    remove(body2,animation2);
                }

            });


            if (count2 == 6) {
                animation2.pause();
                body2.setVisible(false);
            }

            if (body2.getY() > 840) /* if the moon touched the border */ {
                end(false);
            } else if (totalCount == 30) {
                end(true);
            }
            // increasing the Y means moving downward
            body2.setY(y2++);


        }

        private void moveBall3() throws FileNotFoundException {

            body3.setOnMouseClicked(e -> {
                if (e.getButton() == MouseButton.PRIMARY) {
                    score += 4;
                    y3 = 25;
                    count3++;
                    remove(body3,animation3);
                }

            });

            if (count3 == 6) {
                animation3.pause();
                body3.setVisible(false);
            }

            if (body3.getY() > 850) /* if the moon touched the border */ {
                end(false);
            } else if (totalCount == 30) {
                end(true);
            }
            // increasing the Y means moving downward
            body3.setY(y3++);


        }


        private void moveBall4() throws FileNotFoundException {

            body4.setOnMouseClicked(e -> {
                if (e.getButton() == MouseButton.PRIMARY) {
                    score += 5;
                    y4 = 25;
                    count4++;
                    remove(body4,animation4);
                }

            });


            if (count4 == 6) {
                animation4.pause();
                body4.setVisible(false);
            }

            if (body4.getY() > 860) /* if the moon touched the border */ {
                end(false);
            } else if (totalCount == 30) {
                end(true);
            }

            // increasing the Y means moving downward
            body4.setY(y4++);


        }

        private void remove(ImageView body,Timeline animation){
            totalCount++;
            scoreText.setText("Score: " + score);
            remainingText.setText("Remaining: " + (remaining - totalCount));
            body.setX(random() * 850);
            body.setY(25);
            increaseSpeed(animation);
        }


        private void end(boolean winning) throws FileNotFoundException {
            // set the Y to the start
            body.setVisible(false);
            body1.setVisible(false);
            body2.setVisible(false);
            body3.setVisible(false);
            body4.setVisible(false);
            scoreBox.setVisible(false);
            remainingBox.setVisible(false);
            try {
                addToFile(score);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            pane.getChildren().add(getVbox(TopRanks,winning));
            score = 0;
            pause();
        }
    }

    public void rules() {
        start.setVisible(false);
        rulesScreen.setVisible(true);
        rulesScreen.setFitHeight(pane.getHeight());
        rulesScreen.setFitWidth(pane.getWidth());
        rulesScreen.setOpacity(0.9);
        pane.getChildren().add(rulesScreen);

        rulesScreen.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                rulesScreen.setVisible(false);
                starting();
            }
        });

    }

    public void starting() {
        // when the game start the start screen disappear and the moon start the animation
        moon.play();
        pane.getChildren().addAll(scoreBox, remainingBox, moon);
    }

    public void restarting(VBox vBox) {
        vBox.setVisible(false);
        restartMenu.setVisible(false);
        remaining = 30;
        y = 25;
        y1 = 25;
        y2 = 25;
        y3 = 25;
        y4 = 25;
        body.setY(25);
        body1.setY(25);
        body2.setY(25);
        body3.setY(25);
        body4.setY(25);
        moon.play();
    }


}