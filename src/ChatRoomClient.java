import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class ChatRoomClient extends JFrame implements ActionListener {
	
	ChatClient chatClient;
	Container cpane;
	JTextArea chatArea;
	JTextField messageBox;
	JButton sendBtn;
	String name;
	
	public ChatRoomClient() {
		super("chat Client lololol");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.cpane = this.getContentPane();
		this.cpane.setPreferredSize(new Dimension(400,400));
		this.cpane.setLayout(new FlowLayout());
		
		this.chatArea = new JTextArea(20, 30);
		JScrollPane scrollPane = new JScrollPane(chatArea);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.cpane.add(scrollPane);
		
		Container messagePane = new Container();
		messagePane.setLayout(new FlowLayout());
		this.messageBox = new JTextField();
		this.messageBox.setColumns(30);
		this.messageBox.addActionListener(this);
		messagePane.add(this.messageBox);
		
		this.cpane.add(messagePane);
		
		this.pack();
		this.setVisible(true);
		this.chatClient = new ChatClient(this);
		
		//request Name
		requestName();
	}
	
	public void requestName(){
		while(name == null) {
			String result = JOptionPane.showInputDialog("what is your name");
			if(result == null) continue;
			name = result;
		}
	}
	
	public String getName() {
		return name;
	}
	
	public void appendText(String str) {
		this.chatArea.append(str + '\n');
	}
	
	public void clearTextField() {
		this.messageBox.setText("");
	}
	
	public static void main(String[] args) {
		ChatRoomClient client = new ChatRoomClient();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == this.messageBox) {
			//send msg
			String text = this.messageBox.getText();
			this.chatClient.sendMessage(text);
		}
	}
}
