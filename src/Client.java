
import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ScrollPaneConstants;

/**
 * 
 * this class run the client
 *
 */
public class Client {

	private static String name;
	private static int matches;
	private static int wins;
	private static int looses;
	private static String password;
	private JFrame frame = new JFrame();
	private JPanel contentPane;
	private static JTable jtable;
	private static BufferedReader in;
	private static PrintWriter out;
	private static Socket socket;
	private static int counter;
	private static char c;
	private static boolean f = false;
	private static boolean h = false;
	static int interval;
	static Timer timer;
	private JLabel timeLabel;
	private DefaultTableModel model = new DefaultTableModel(
			new Object[][] { { "", "", "", "", "", null, null }, { "", "", "", "", "", null, null },
					{ "", "", "", "", "", null, null }, { "", "", "", "", "", null, null },
					{ "", "", "", "", "", null, null }, },
			new String[] { "name", "last name", "country", "animal", "fruits", "points", "letter" });


	/**
	 * Launch the application.
	 * 
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		socket = new Socket("127.0.0.1", 9000);
		Client client = new Client();
		client.new execution(socket);

	}

	/**
	 * Create the frame.
	 * 
	 * @throws Exception
	 */

	public Client() throws Exception {

		frame.setBackground(Color.BLACK);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 655, 470);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);

		Informations obj = new Informations();// this is object from information class
		String dir;
		dir = System.getProperty("user.dir");
		String read = "";
		File file = new File(dir+"/Profile.txt");
		/**
		 * in this section we get the information of player
		 */
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			while (read != null) {
				read = br.readLine();
				if (read.equals(obj.getName())) {
					name = read;
					password = br.readLine().substring(obj.getName().length() + 11);
					matches = Integer.parseInt(br.readLine().substring(obj.getName().length() + 10));
					wins = Integer.parseInt(br.readLine().substring(obj.getName().length() + 7));
					looses = Integer.parseInt(br.readLine().substring(obj.getName().length() + 9));

				}
			}
			br.close();
		} catch (Exception e) {
			e.getMessage();
		}

		frame.setTitle(obj.getName());// set the title from plyaer name

		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		panel.setBounds(0, 0, 633, 414);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(15, 16, 603, 144);
		panel.add(scrollPane);

		jtable = new JTable(model);
		scrollPane.setViewportView(jtable);
		jtable.setBackground(Color.BLACK);
		jtable.setForeground(Color.YELLOW);
		jtable.setFont(new Font("Tahoma", Font.PLAIN, 17));
		jtable.setColumnSelectionAllowed(true);
		jtable.setCellSelectionEnabled(true);

		JButton btnStart = new JButton("New Round");// this duty of this button is additon the mount of column
		btnStart.setForeground(Color.YELLOW);
		btnStart.setBackground(Color.BLACK);
		btnStart.setFocusable(false);
		btnStart.setFont(new Font("Tahoma", Font.PLAIN, 19));

		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				counter++;
				if (counter == 5) {// if column raise to 5 means its last round
					int a = 0;
					String total;
					f = true;
					for (int i = 0; i < 5; i++) {
						total = (String) jtable.getValueAt(i, 5);
						a += Integer.parseInt(total);
					}
					out.println("the game is over player1" + a);// send to server that player 1 finish the game

					String st = "";

					try {
						st = in.readLine();
					} catch (IOException e) {
						e.printStackTrace();
					}

				} else
					out.println("new round");// if the round isn't last send massage of it to server
			}
		});
		btnStart.setBounds(468, 325, 150, 41);
		panel.add(btnStart);

		JButton btnStop = new JButton("STOP");
		btnStop.setForeground(Color.YELLOW);
		btnStop.setBackground(Color.BLACK);
		btnStop.setFocusable(false);

		btnStop.addActionListener(new ActionListener() {// with this button the game will be stop
			public void actionPerformed(ActionEvent arg0) {
				String Data;
				
				for (int j = 0; j < 5; j++) {// in this loop we check that if words first digit is equal with given
												// letter
					Data = (String) jtable.getValueAt(counter, j);
					if (Data.equals("") || (Data.charAt(0) != c && Data.charAt(0) != (char) (c + 32)))
						f = true;
				}
				if (f == false) {// after checking, we should send the information to server
					timer.cancel();
					for (int j = 0; j < 5; j++) {
						Data = (String) jtable.getValueAt(counter, j);
						out.println(jtable.getColumnName(j));
						out.println(Data);
					}
					out.println("i am winner :D ");// now winner player declar that he finnished the game
				}
			}
		});
		btnStop.setFont(new Font("Tahoma", Font.PLAIN, 19));
		btnStop.setBounds(468, 261, 150, 41);
		panel.add(btnStop);

		JButton btnLetter = new JButton("LETTER");// with this button we determine our random letter
		btnLetter.setForeground(Color.YELLOW);
		btnLetter.setBackground(Color.BLACK);
		btnLetter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				Random input = new Random();
				int memory = 65  + input.nextInt(26)  ;
				c = (char) (memory);
				model.setValueAt(c, counter, 6);// set the letter in its column
				out.println("letter");
				out.println(c);
				/**
				 * this section is for timer
				 */
				int delay = 1000;
				int period = 1000;
				timer = new Timer();
				interval = 180;
				timeLabel.setText(String.valueOf(interval));
				out.println("time" + timeLabel.getText());
				timer.scheduleAtFixedRate(new TimerTask() {
					public void run() {
						timeLabel.setText(String.valueOf(setInterval()));
						if (timeLabel.getText().equals("0"))// if the time is over the game will be finished
							khakTooSareGavetoon();// this function get the information with out pressing the stop button
						out.println("time" + timeLabel.getText());
					}
				}, delay, period);
			}
		});
		btnLetter.setFont(new Font("Tahoma", Font.PLAIN, 19));
		btnLetter.setFocusable(false);
		btnLetter.setBounds(468, 204, 150, 41);
		panel.add(btnLetter);

		timeLabel = new JLabel();
		timeLabel.setForeground(Color.YELLOW);
		timeLabel.setFont(new Font("Tahoma", Font.PLAIN, 83));
		timeLabel.setBounds(23, 204, 229, 162);
		panel.add(timeLabel);

		frame.setVisible(true);
	}

	public class execution extends Thread {
		private Socket socket;

		public execution(Socket socket) {
			this.socket = socket;
			start();
		}

		public void run() {
			try {

				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));// setting the input socket
				out = new PrintWriter(socket.getOutputStream(), true);// setting the output socket

				while (true) {

					String s = in.readLine();

					if (s.startsWith("time"))
						timeLabel.setText(s.substring(4));

					if (s.startsWith("LETTER"))
						model.setValueAt(s.substring(6), counter, 6);

					if (s.equals("winner stopped")) {
						String Data;
						for (int j = 0; j < 5; j++) {
							Data = (String) jtable.getValueAt(counter, j);
							out.println(jtable.getColumnName(j));
							out.println(Data);
						}
						out.println("points");
					}

					if (s.equals("new round"))
						counter++;

					if (s.startsWith("Secound Score"))
						model.setValueAt(s.substring(13), counter, 5);

					if (s.startsWith("Winner Score"))
						model.setValueAt(s.substring(12), counter, 5);

					if (s.equals("the game is over")) {// in this part we addition the points toghther
						int a = 0;
						String total;
						f = true;
						for (int i = 0; i < 5; i++) {
							total = (String) jtable.getValueAt(i, 5);
							a += Integer.parseInt(total);
						}
						out.println("the game is over player2" + a);
						out.println("conclusion");

					}

					if (s.startsWith("conclusion: ")) {// this is last part of client that show the conclusion

						matches++;
						String st = s.substring(12);
						System.out.println(st);
						if (st.equals("you win the game"))
							wins++;
						if (st.equals("you loose the game"))
							looses++;

						// JOptionPane.showMessageDialog(frame, st);

						/**
						 * in this section append the new information of each player
						 */
						try {
							String dir;
							dir = System.getProperty("user.dir");
							FileWriter fw = new FileWriter(dir+"/Profile.txt",true);
							BufferedWriter bw = new BufferedWriter(fw);
							PrintWriter out = new PrintWriter(bw);
							out.println(name);
							out.println(name + "password : " + password);
							out.println(name + "matches : " + matches);
							out.println(name + "wins : " + wins);
							out.println(name + "looses : " + looses);
							out.println("*************");
							out.close();

						} catch (IOException e) {
							e.printStackTrace();
						}
						frame.setVisible(false);
						break;
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					in.close();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
	}

	/**
	 * this function send the information of current row
	 */
	public static void khakTooSareGavetoon() {
		String Data;
		for (int j = 0; j < 5; j++) {
			Data = (String) jtable.getValueAt(counter, j);
			out.println(jtable.getColumnName(j));
			out.println(Data);
		}
		out.println("i am winner :D ");

	}

	/**
	 * this function is for over time that will cancel the timer
	 * 
	 * @return
	 */

	private static final int setInterval() {
		if (interval == 1)
			timer.cancel();
		return --interval;
	}
}
