package tars;

import com.guigarage.flatterfx.FlatterFX;
import com.guigarage.flatterfx.skin.FlatterTextAreaSkin;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Created by tim on 06.01.16.
 */
public class Main extends Application{
    public static Stage stage;
    public static String title = "";
    public static String path = "";
    public static void main(String [] args){
       if(!path.equals("") || path != null) {
           try {
               path = args[0];
               title = "TextZilla - [" + path + "]";
           }catch (Exception ex){
               title = "TextZilla";
           }
        }
        else{
            title = "TextZilla";
        }
        launch(args);
    }
    public void start(Stage primStage){

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/Root.fxml"));
        AnchorPane pane;
        stage = primStage;
        stage.getIcons().add(new Image("file://resources/redstar.jpg"));
        try{
            pane = (AnchorPane) loader.load();
        }catch (Exception ex){
            pane = new AnchorPane();
            pane.getChildren().add(new TextArea(ex.getMessage()));
        }
        stage.setScene(new Scene(pane));
        stage.setTitle(title);
        stage.show();

    }
}
