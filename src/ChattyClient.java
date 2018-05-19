import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.TextFlow;

import java.io.*;
import java.net.Socket;

public class ChattyClient {
    private Socket client;
    private BufferedReader in;
    private BufferedWriter out;

    public ChattyClient(String host, String port, TextArea console) throws IOException{

            this.client = new Socket(host, Integer.parseInt(port));
            Thread chattyClientThread = new Thread(new ChattyClientThread(this.client, console));
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
            catch (IOException ex){
                ex.printStackTrace();
            }
    }
}
