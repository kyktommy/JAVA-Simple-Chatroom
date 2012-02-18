import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * class: client receive thread
 * when message come from server, display message to GUI
 */

public class ClientReceiveThread implements Runnable{
	
	ChatClient chatClient;
	
	public ClientReceiveThread(ChatClient chatClient) {
		this.chatClient = chatClient;
	}
	
	public void run() {
		try {
		BufferedReader br = new BufferedReader(new InputStreamReader(chatClient.socket.getInputStream()));
			for(;;) {
				String str = br.readLine();
				if(str == null) continue;
				chatClient.GUIClient.appendText(str);
			}
		} catch(IOException ex){
			System.err.println(ex);
		}
	}
}
