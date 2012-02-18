import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;


public class ChatClient {
	
	Socket socket;
	ChatRoomClient GUIClient;
	
	public ChatClient(ChatRoomClient client) {
		this.GUIClient = client;
		connectToServer();
		receiveMessage();
	}
	
	public void connectToServer() {
		while(socket == null) {
			try {
				socket = new Socket("127.0.0.1", 9999);
				GUIClient.appendText("connected: server");
			} catch (UnknownHostException ex) {
				System.err.println(ex);
				
			} catch (IOException ex) {
				GUIClient.appendText("cannot connect, retry...");
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					//System.err.println(e);
				}
			}
		}
	}
	
	public void sendMessage(String str) {
		try {
			
			OutputStream os = socket.getOutputStream();
			OutputStreamWriter isw = new OutputStreamWriter(os);
			BufferedWriter bw = new BufferedWriter(isw);
			
			String msg = GUIClient.getName() + ": "+ str;
			
			bw.write(msg + '\n');
			GUIClient.appendText(msg);
			bw.flush();
			
			GUIClient.clearTextField();
			
		} catch (IOException ex) {
			System.err.println(ex);
		}
	}
	
	public void receiveMessage() {
		ClientReceiveThread clientreceiveThread = new ClientReceiveThread(this);
		Thread thread = new Thread(clientreceiveThread);
		thread.start();
	}
	
	public static void main(String[] args) {
		//(new ChatClient()).run();
	}
}
