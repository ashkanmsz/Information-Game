
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Color;

/**
 * 
 * this page show the information of player that is login or sign up 
 *
 */

public class JoinHost {

	private JPanel contentPane;
	private JFrame frame = new JFrame();
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		new JoinHost();
	}

	/**
	 * Create the frame.
	 */
	public JoinHost() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 450, 526);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		frame.setLocationRelativeTo(null);

		Informations obj = new Informations();//this is a object from informations class

		JButton btnHost = new JButton("Host");
		btnHost.setBackground(Color.YELLOW);
		btnHost.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {//if you select this button your name add to players arraylist
				
				Informations.list.add(obj.getName());
				frame.setVisible(false);
				try {
					Profile.main(null);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		btnHost.setFocusable(false);
		btnHost.setFont(new Font("Tahoma", Font.PLAIN, 19));
		btnHost.setBounds(242, 389, 146, 40);
		contentPane.add(btnHost);

		JButton btnJoin = new JButton("join\r\n");//if you select this button you were sent to players list page 
		btnJoin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				GamePlay.main(null);
			}
		});
		btnJoin.setBackground(Color.YELLOW);
		btnJoin.setFocusable(false);
		btnJoin.setFont(new Font("Tahoma", Font.PLAIN, 19));
		btnJoin.setBounds(43, 389, 146, 40);
		contentPane.add(btnJoin);

		JLabel lblName = new JLabel("name");
		lblName.setForeground(Color.YELLOW);
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 50));
		lblName.setBounds(15, 16, 203, 71);
		contentPane.add(lblName);
		lblName.setText(obj.getName());
		

		JLabel lblMatches = new JLabel("matches");
		lblMatches.setForeground(Color.YELLOW);
		lblMatches.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblMatches.setBounds(41, 128, 98, 33);
		contentPane.add(lblMatches);

		JLabel lblWins = new JLabel("wins");
		lblWins.setForeground(Color.YELLOW);
		lblWins.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblWins.setBounds(41, 188, 69, 20);
		contentPane.add(lblWins);

		JLabel lblLooses = new JLabel("looses");
		lblLooses.setForeground(Color.YELLOW);
		lblLooses.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblLooses.setBounds(41, 252, 69, 20);
		contentPane.add(lblLooses);

		JLabel label_1 = new JLabel("");
		label_1.setForeground(Color.YELLOW);
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 19));
		label_1.setBounds(173, 133, 69, 28);
		contentPane.add(label_1);
		label_1.setText(obj.getMatches());

		JLabel label_2 = new JLabel("");
		label_2.setForeground(Color.YELLOW);
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 19));
		label_2.setBounds(173, 188, 69, 33);
		contentPane.add(label_2);
		label_2.setText(obj.getWins());

		JLabel label_3 = new JLabel("");
		label_3.setForeground(Color.YELLOW);
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 19));
		label_3.setBounds(173, 251, 69, 33);
		contentPane.add(label_3);
		label_3.setText(obj.getLooses());

		frame.setVisible(true);
	}
}
