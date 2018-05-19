import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class ChattyClient {
    private BorderPane gui;
    private Socket client;
    private BufferedReader in;
    private BufferedWriter out;

    public ChattyClient(String host, String port, BorderPane gui) throws IOException{
            this.gui=gui;
            this.client = new Socket(host, Integer.parseInt(port));
            Thread chattyClientThread = new Thread(new ChattyClientThread(this.client, ((TextArea)gui.getCenter())));
            chattyClientThread.start();
            this.out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

    }

    public void transfer(TextField userinput){
        this.in  = new BufferedReader(new StringReader(userinput.getText()));
            try {
                String command = in.readLine();
                if (command != null) {
                    out.write(command + "\n");
                    out.flush();
                }
                userinput.setText("");
            }
            catch (SocketException ex){
                ((Label)gui.getTop()).setText("Disconnected!");
            }
            catch (IOException ex){
                ex.printStackTrace();
            }
    }
}
