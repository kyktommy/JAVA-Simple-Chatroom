import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;


public class ChatServer {
	
	ServerSocket serverSocket;
	Collection<Socket> sockets;
	ChatRoom chatRoom;
	
	public ChatServer(ChatRoom chatRoom) {
		try {
			serverSocket = new ServerSocket(9999);
			this.chatRoom = chatRoom;
			sockets = new ArrayList<Socket>();
		} catch(IOException ex) {
			System.err.println(ex);
		}
	}
	
	public void addSocket(Socket socket) {
		this.sockets.add(socket);
	}
	
	public void removeSocket(Socket socket) {
		this.sockets.remove(socket);
	}
	
	public void broadcast(Socket socket, String str) {
		for(Socket s : sockets) {
			//ignore sender
			if(s == socket) continue;
			
			//send to other
			PrintWriter out;
			try {
				out = new PrintWriter(s.getOutputStream(), true );
				out.println(str);
			} catch (IOException ex) {
				System.err.println(ex);
			}
		}
	}
	
	public void run() {
		try {
			for(;;) {
			
				Socket socket = serverSocket.accept();
				
				//new connection
				this.addSocket(socket);
				
				String clientInfo;
				clientInfo = socket.getInetAddress().getHostAddress() + ":" + socket.getPort();
				chatRoom.appendToTextArea("Socket connected: " + socket.getInetAddress().getHostAddress() + ":" + socket.getPort());
				
				//create new thread for new socket
				Connection connection = new Connection(socket, this);
				Thread thread = new Thread(connection);
				thread.start();	
				
			}
			
		} catch(IOException ex) {
			System.err.println(ex);
		} finally {
			
		}
	}
	
	
	public static void main(String[] args) {
		//(new ChatServer()).run();
	}
}
