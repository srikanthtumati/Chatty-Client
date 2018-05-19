import javafx.application.Platform;
import javafx.scene.control.TextArea;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChattyClientThread implements Runnable{
    private Socket client;
    private BufferedReader in;
    private TextArea console;
    public ChattyClientThread(Socket client, TextArea console){
        this.console=console;
        try {
            this.client=client;
            this.in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        }
        catch (UnknownHostException ex){
            System.err.println("Error: Unknown Host!");
        }
        catch (IOException ex){
            System.err.println("Error: port must be numerical!");
        }
    }
    public void run(){
        while (true){
            try {
                String serverResponse = this.in.readLine();
                if (serverResponse!=null)
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            console.appendText(serverResponse + "\n");

                        }
                    });
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
    }
}
