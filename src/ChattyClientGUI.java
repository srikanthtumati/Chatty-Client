import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;

public class ChattyClientGUI extends Application{
    public static void main (String[] args){
        Application.launch();
    }

    public void start(Stage stage){
        BorderPane borderPane = new BorderPane();
        Button button = new Button("Connect!");
        borderPane.setBottom(button);
        borderPane.setLeft(buildConnections());
        borderPane.setCenter(buildChat());
        Label status = new Label("Disconnected!");
        borderPane.setTop(status);
        borderPane.setAlignment(status, Pos.CENTER);
        button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        button.setOnAction(event -> init(borderPane));
        stage.setScene(new Scene(borderPane));
        stage.show();
    }

    public GridPane buildConnections(){
        GridPane gridPane = new GridPane();

        gridPane.add(new Label("Address: "), 0, 0);
        gridPane.add(new TextField(), 1, 0);
        gridPane.add(new Label("Port: "), 0, 1);
        gridPane.add(new TextField(), 1, 1);
        return gridPane;
    }

    public TextArea buildChat(){
        TextArea console = new TextArea();
        console.setWrapText(false);
        console.setEditable(false);
        return console;
    }

    public void init(BorderPane borderPane){
        ((TextField)((GridPane) borderPane.getLeft()).getChildren().get(1)).setEditable(false);
        ((TextField)((GridPane) borderPane.getLeft()).getChildren().get(3)).setEditable(false);
        try {
            ChattyClient client = new ChattyClient(((TextField) ((GridPane) borderPane.getLeft()).getChildren().get(1)).getText(), ((TextField) ((GridPane) borderPane.getLeft()).getChildren().get(3)).getText(), borderPane);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    ((Label)borderPane.getTop()).setText("Connected!");
                    TextField userinput = new TextField();
                    userinput.setOnAction(event -> client.transfer(userinput));
                    borderPane.setBottom(userinput);
                    userinput.requestFocus();
                }
            });
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
