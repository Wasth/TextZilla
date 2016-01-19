package tars.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sun.plugin.javascript.navig.Anchor;
import tars.Main;

import java.io.*;

/**
 * Created by tim on 06.01.16.
 */
public class RootController {
    File file;
    File backupFile;
    static String path;
    @FXML
    TextArea area;
    @FXML
    void initialize(){
        if (!Main.path.equals("")){
            try {
                BufferedReader reader = new BufferedReader(new FileReader(Main.path));
                backupFile = new File(Main.path);

                while(true) {
                    try{

                        String text = reader.readLine();
                        if(text.equals(null)){
                            break;

                        }
                        text = text + "\n";
                        area.appendText(text);
                    }catch (Exception ex){
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
    @FXML
    void menuSave(){
        if(!Main.path.equals("")) {
            File file = new File(Main.path);
            try {
                FileWriter writer = new FileWriter(file);
                backupFile = new File(file.getPath());
                writer.write(area.getText());
                writer.flush();
                writer.close();
            } catch (Exception ex) {

            }
        }else{
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Save file");
            File file = chooser.showSaveDialog(Main.stage);
            Main.path = file.getPath();
            Main.title = "TextZilla - [" + Main.path + "]";
            Main.stage.setTitle("TextZilla - [" + Main.path + "]");
            try {
                FileWriter writer = new FileWriter(file);
                backupFile = new File(file.getPath());
                writer.write(area.getText());
                writer.flush();
                writer.close();
            }catch (Exception ex){

            }
        }
    }
    @FXML
    void menuNew(){
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Create a new file");
        File file = chooser.showSaveDialog(Main.stage);

        try {
            file.createNewFile();
            BufferedReader reader = new BufferedReader(new FileReader(file));
            Main.path = file.getPath();
            Main.title = "TextZilla - [" + Main.path + "]";
            Main.stage.setTitle(Main.title);
            area.clear();
            while(true) {
                try{
                    String text = reader.readLine();
                    if(text.equals(null)){
                        break;

                    }
                    text = text + "\n";
                    area.appendText(text);
                }catch (Exception ex){
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        backupFile = new File(file.getPath());
    }
    @FXML
    void menuOpen(){
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Choose file");
        File file = chooser.showOpenDialog(Main.stage);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            Main.path = file.getPath();
            Main.title = "TextZilla - [" + Main.path + "]";
            Main.stage.setTitle(Main.title);
            area.clear();
            while(true) {
                try{
                    String text = reader.readLine();
                    if(text.equals(null)){
                        break;

                    }
                    text = text + "\n";
                    area.appendText(text);
                }catch (Exception ex){
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        backupFile = new File(file.getPath());
    }
    @FXML
    void menuSaveAs(){
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Save file");
        File file = chooser.showSaveDialog(Main.stage);
        try {
            FileWriter writer = new FileWriter(file);
            backupFile = new File(file.getPath());
            writer.write(area.getText());
            writer.flush();
            writer.close();
        }catch (Exception ex){

        }

    }
    @FXML
    void menuClose(){
        String text = "";
        String  backupText = "";
            text = area.getText();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(backupFile.getAbsoluteFile()));
            String tmp;
            while((tmp = reader.readLine()) != null){
                backupText = backupText + tmp+"\n";
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }

        if(backupText.equals(text)){
            Main.stage.close();
        }
        else{
            Stage dialog = new Stage();
            FlowPane pane = new FlowPane();

            dialog.setTitle("Save changes?");
            dialog.initStyle(StageStyle.UTILITY);
            VBox box = new VBox();
            box.setSpacing(7);
            box.setAlignment(Pos.CENTER);
            HBox buttons = new HBox();
            buttons.setSpacing(7);
            buttons.setAlignment(Pos.CENTER);
            Button okButton = new Button("Save");
            Button noButton = new Button("Don't save");
            Button cancelButton = new Button("Cancel");
            cancelButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    dialog.close();
                }
            });
            noButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
                public void handle(MouseEvent event){
                    Main.stage.close();
                    dialog.close();
                }
            });
            okButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    File file = new File(Main.path);
                    try {
                        FileWriter writer = new FileWriter(file);
                        writer.write(area.getText());
                        writer.flush();
                        writer.close();
                    }catch (Exception ex){

                    }
                    Main.stage.close();
                    dialog.close();
                }
            });
            buttons.getChildren().addAll(okButton, noButton,  cancelButton);
            box.getChildren().addAll(new Label("Save changes in file?"), buttons);
            pane.getChildren().add(box);
            pane.setPrefWidth(250);
            pane.setPrefHeight(70);
            pane.setAlignment(Pos.CENTER);
            Scene scene = new Scene(pane);
            dialog.setScene(scene);
            dialog.show();
        }
    }
    @FXML
    void menuAbout(){
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/About.fxml"));
        AnchorPane pane = null;
        try{
            pane = (AnchorPane) loader.load();
        }catch(Exception ex){
            pane = new AnchorPane();
            ex.printStackTrace();
        }
        stage.setScene(new Scene(pane));
        stage.setTitle("About creator");
        stage.show();
    }
}
