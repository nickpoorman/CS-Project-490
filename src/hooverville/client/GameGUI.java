package hooverville.client;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.text.DefaultCaret;

public class GameGUI extends JFrame implements ActionListener, KeyListener {

	JTextArea console;
	JTextField input;
	BorderLayout layout;
	JScrollPane consoleScrollPane;
	Client client;
	List<String> history;
	int historyPointer = -1;
	int totalLines = 0;
	int MAX_LINES = 4;

	public GameGUI() {
		super("CSC490 - Game Design");
		initLookAndFeel();
		int inset = 100;
		//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		//        setBounds(inset, i nset,
		//                screenSize.width - inset * 2,
		//                screenSize.height - inset * 2);

		Dimension newDimension = new Dimension(600, 700);
		setBounds(inset, inset, (int) newDimension.getWidth(), (int) newDimension.getHeight());

		setResizable(true);
		Container contentPane = this.getContentPane();
		layout = new BorderLayout();
		getContentPane().setLayout(layout);
		this.setPreferredSize(new Dimension(600, 700));
		createComponents();
		initComponents();
		addComponents();
		createClient();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				input.requestFocus();
			}
		});
	}

	public void createClient() {
		client = new Client();
		client.setGui(this);
		client.start();
	}

	public void addToScreen(String text) {
		if(totalLines >= 500){
			console.setText("");
			totalLines = 0;
		}
		console.append(text + "\n");
		totalLines++;	
		console.setCaretPosition(console.getText().length());
	}

	public void createComponents() {
		console = new JTextArea();
		input = new JTextField();
		consoleScrollPane = new JScrollPane(console);

		console.setEditable(false);
		input.addKeyListener(this);

		history = new ArrayList<String>();
		DefaultCaret caret = (DefaultCaret)console.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

	}

	public void addComponents() {
		this.add(consoleScrollPane, BorderLayout.CENTER);
		this.add(input, BorderLayout.SOUTH);
	}

	public void initComponents() {

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

	}

	//Quit the application.
	protected void quit() {
		System.exit(0);
	}

	private static void createAndShowGUI() {
		//Make sure we have nice window decorations.
		//   JFrame.setDefaultLookAndFeelDecorated(true);

		//Create and set up the window.
		GameGUI frame = new GameGUI();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Display the window.
		frame.setVisible(true);
	}

	public static void main(String[] args) {

		javax.swing.SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				createAndShowGUI();
			}
		});

	}

	private static void initLookAndFeel() {
		try {
			UIManager.setLookAndFeel("org.jvnet.substance.skin.SubstanceRavenGraphiteLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	private void sendToServer(String s) {
		client.getOut().println(s);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		//System.out.println("KEY: " + key);
		// TODO Auto-generated method stub
		if (key == KeyEvent.VK_ENTER) {
			//send the message to the server
			if (!input.getText().equals("")) {
				String text = input.getText();
//				if(text.equals("clear")){
//					console.setText("");
//					this.totalLines = 0;
//				}
				sendToServer(text);
				//clear it
				addToScreen(">" + text);
				//console.append(">" + text + "\n");
				history.add(0, new String(text));
				input.setText("");
			}
		} else if (key == KeyEvent.VK_UP) {
			if (historyPointer < 0) historyPointer = -1;
			historyPointer++;
			if (historyPointer == history.size()) {
				historyPointer--;
			}
			input.setText(history.get(historyPointer));
		} else if (key == KeyEvent.VK_DOWN) {
			historyPointer--;
			if (historyPointer < 0) {
				input.setText("");
			} else {
				input.setText(history.get(historyPointer));
			}
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
