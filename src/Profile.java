
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.ImageIcon;

public class Profile {

	private JPanel contentPane;
	private JFrame frame = new JFrame();
	private JTextField textField;
	private JPasswordField passwordField;
	private String name;
	private String matches;
	private String wins;
	private String looses;
	private String password;
	private boolean f;
	private boolean h;

	/**
	 * Launch the application.
	 * 
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		new Profile();
	}

	/**
	 * Create the frame.
	 * 
	 * @throws IOException
	 */
	public Profile() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 450, 543);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		frame.setLocationRelativeTo(null);

		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 19));
		textField.setBackground(Color.YELLOW);
		textField.setBounds(146, 294, 191, 26);
		contentPane.add(textField);
		textField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBackground(Color.YELLOW);
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 19));
		passwordField.setBounds(146, 336, 191, 26);
		contentPane.add(passwordField);

		JLabel lblUserName = new JLabel("user name");
		lblUserName.setForeground(Color.YELLOW);
		lblUserName.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblUserName.setBounds(36, 297, 116, 20);
		contentPane.add(lblUserName);

		JLabel lblPassword = new JLabel("password");
		lblPassword.setForeground(Color.YELLOW);
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblPassword.setBounds(36, 339, 95, 20);
		contentPane.add(lblPassword);

		JLabel label = new JLabel("");
		label.setForeground(Color.RED);
		label.setBackground(Color.RED);
		label.setFont(new Font("Tahoma", Font.PLAIN, 19));
		label.setBounds(36, 378, 377, 20);
		contentPane.add(label);

		JButton btnLogin = new JButton("login");// with this button if we have accout from last play we can login
		String dir= System.getProperty("user.dir");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				f = false;
				name = textField.getText();
				password = new String(passwordField.getPassword());

				/**
				 * in this part we get the informations from txt file
				 */
				String st = "";
				File file = new File(dir+"/Profile.txt");
				try {
					BufferedReader br = new BufferedReader(new FileReader(file));
					while (st != null) {
						st = br.readLine();
						if (st.equals(name) && br.readLine().substring(name.length() + 11).equals(password)) {
							matches = br.readLine().substring(name.length() + 10);
							wins = br.readLine().substring(name.length() + 7);
							looses = br.readLine().substring(name.length() + 9);
							f = true;// this flag indicate that the password and name is correct to send them to
										// informations constructor
						}
					}
					br.close();
				} catch (Exception e) {
					e.getMessage();
				}
				if (f == true) {
					Informations obj = new Informations(name, password, matches, wins, looses, false);
					frame.setVisible(false);
					JoinHost.main(null);
				} else
					label.setText("user name or password is incorrect");
			}
		});
		btnLogin.setBackground(Color.YELLOW);
		btnLogin.setFocusable(false);
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 19));
		btnLogin.setBounds(248, 427, 107, 44);
		contentPane.add(btnLogin);

		JButton btnSignUp = new JButton("sign up");//with this button we can regestration for first time
		btnSignUp.setBackground(Color.YELLOW);
		btnSignUp.setFocusable(false);
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				h = false;
				name = textField.getText();
				password = new String(passwordField.getPassword());
				String st = "";
				/**
				 * write our new contact informations in txt file
				 */
				
				File file = new File(dir+"/Profile.txt");
				try {
					BufferedReader br = new BufferedReader(new FileReader(file));
					while (true) {
						st = br.readLine();
						if (st.equals(name)) {
							label.setText("the account has already exist try annother ");
							h = true;
							break;
						}
					}
				} catch (Exception e) {
					e.getMessage();
				}
				if (h == false) {
					matches = "0";
					wins = "0";
					looses = "0";
					Informations obj = new Informations(name, password, matches, wins, looses, true);
					frame.setVisible(false);
					JoinHost.main(null);
				}
			}
		});
		btnSignUp.setFont(new Font("Tahoma", Font.PLAIN, 19));
		btnSignUp.setBounds(61, 427, 115, 44);
		contentPane.add(btnSignUp);

		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon(dir+"\\2.jpg"));
		label_1.setBounds(79, 16, 276, 235);
		contentPane.add(label_1);

		frame.setVisible(true);
	}
}
