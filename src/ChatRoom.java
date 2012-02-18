import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class ChatRoom extends JFrame implements ActionListener{
	
	ChatServer chatServer;
	Container cpane;
	JTextArea chatArea;
	JTextField messageField;
	String name;
	
	public ChatRoom() {
		super("Chat room Server");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//init
		chatServer = new ChatServer(this);
		name = "Server";
		
		//GUI
		cpane = this.getContentPane();
		cpane.setLayout(new FlowLayout());
		cpane.setPreferredSize(new Dimension(400, 400));
		
		chatArea = new JTextArea(20, 30);
		chatArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(chatArea);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		cpane.add(scrollPane);
		
		this.messageField = new JTextField(30);
		this.messageField.addActionListener(this);
		cpane.add(this.messageField);
		
		this.pack();
	}
	
	public void run() {
		this.chatServer.run();
	}
	
	public void appendToTextArea(String str) {
		this.chatArea.append(str + '\n');
	}
	
	public static void main(String[] args) {
		ChatRoom chatroom = new ChatRoom();
		chatroom.setVisible(true);
		chatroom.run();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == this.messageField) {
			String str = name + ": " + this.messageField.getText();
			this.appendToTextArea(str);
			chatServer.broadcast(null, str);
		}
		
	}	
}
