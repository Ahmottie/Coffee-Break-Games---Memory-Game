package seda_project.control_alt_defeat.gamebox.Memory.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import seda_project.control_alt_defeat.gamebox.Memory.Configuration;
import seda_project.control_alt_defeat.gamebox.Memory.ViewStack;

import java.util.ArrayList;
import java.util.Collections;

public class GameScreen {
    ViewStack vS;
    @FXML
    private VBox header;

    @FXML
    private Label sboardP1,sboardP2,sboardScoreP1,sboardScoreP2,activePlayerLabel,turnStatusLabel;

    @FXML
    private AnchorPane gamePane;

    //TODO game Start

    @FXML
    private void onExitGameAction(){
        try{
            vS.emtyStack();
            String address = "/Views/Memory/MemoryMenu.fxml";

            FXMLLoader loader = new FXMLLoader(Configuration.class.getResource(address));
            Parent root = loader.load();
            MemoryMenu controller = loader.getController();

            vS.addFxmlLoaders(address);
            controller.handViewStack(vS);

            Scene newScene = new Scene(root, 800, 600);
            Stage stage = (Stage) header.getScene().getWindow();
            stage.setScene(newScene);
            stage.show();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void handViewStack(ViewStack vS) {
        this.vS = vS;
    }

    public void passMemoryData(String player1, String player2, int tupleSize, int deckSize){
        sboardP1.setText(player1);
        sboardP2.setText(player2);

        createBoard(tupleSize,deckSize);
    }

    private void createBoard(int tupleSize, int deckSize) {
        GridPane playingGrid  = new GridPane();

        playingGrid.setHgap(10);
        playingGrid.setVgap(10);
        playingGrid.setPadding(new Insets(10));

        int col = (int)Math.sqrt(deckSize);
        int row = (int) deckSize/col;

        ArrayList<Integer> positions  = new ArrayList<Integer>();
        int repeats = deckSize/tupleSize;
        for (int i = 0; i < repeats; i++) {
            for (int j = 0; j < tupleSize; j++){
                positions.add(i);
            }
        }

        Collections.shuffle(positions);

        for (int i = 0; i < col; i++) {
            for (int j = 0; j < row; j++) {
                //TODO Each Cell is a Memory Card
                //TODO Create Card Amir
                /**
                Card cell = new Card(i,j,positions.get(3*i+j));
                cell.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); // fill cell
                playingGrid.add(cell, j, i);

                GridPane.setHgrow(cell,Priority.ALWAYS);
                GridPane.setVgrow(cell,Priority.ALWAYS);
                 **/
            }
        }

        gamePane.getChildren().add(playingGrid);
        AnchorPane.setBottomAnchor(playingGrid,20.0);
        AnchorPane.setTopAnchor(playingGrid,20.0);
        AnchorPane.setLeftAnchor(playingGrid,20.0);
        AnchorPane.setRightAnchor(playingGrid,20.0);
    }

    public void setStatusLabel(String text, boolean error){
        if (error){
            turnStatusLabel.getStyleClass().clear();
            turnStatusLabel.getStyleClass().add("box");
            turnStatusLabel.getStyleClass().add("error");
            turnStatusLabel.setText(text);
        }
        else{
            turnStatusLabel.getStyleClass().clear();
            turnStatusLabel.getStyleClass().add("box");
            turnStatusLabel.getStyleClass().add("ready");
            turnStatusLabel.setText(text);
        }
    }

    public void setActivePlayerLabel(String name){
        activePlayerLabel.setText(name);
    }

    public void changePoints(String player,  int points){
        int current;
        if (player.equals(sboardP1.getText())){
            current = Integer.parseInt(sboardScoreP1.getText());
            current += points;
            sboardScoreP1.setText(current+"");
        }
        else if (player.equals(sboardP2.getText())){
            current = Integer.parseInt(sboardScoreP2.getText());
            current += points;
            sboardScoreP2.setText(current+"");
        }
        else {
            System.err.println("This player does not exist!");
        }
    }
}
