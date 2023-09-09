
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JLabel;
/**
 * this is a group game for game lovers
 * @author ashkan
 *@since 5/6/2018
 *@version 1.1.2 
 */
public class gui  {

	private JPanel contentPane;
	private JFrame frame=new JFrame();

	/**this class run the server for conecting the clients 
	 * Launch the application.
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		new gui();
		Server.main(null);
	}

	/**
	 * Create the frame.
	 */
	
	public gui() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 450, 402);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		frame.setLocationRelativeTo(null);
		
		JButton btnStart = new JButton("START!!!!!!!");//with this butoon we send to profie page
		btnStart.setBounds(134, 252, 156, 53);
		btnStart.setFocusable(false);
		btnStart.setBackground(Color.YELLOW);
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				frame.setVisible(false);
				try {
					Profile.main(null);
				
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		});
		contentPane.setLayout(null);
		btnStart.setFont(new Font("Tahoma", Font.PLAIN, 19));
		contentPane.add(btnStart);
		
		JLabel lblWelcomeToName = new JLabel("welcome to name & lastname game !\r\n");
		lblWelcomeToName.setForeground(Color.YELLOW);
		lblWelcomeToName.setFont(new Font("Segoe UI", Font.ITALIC, 21));
		lblWelcomeToName.setBounds(50, 39, 378, 53);
		contentPane.add(lblWelcomeToName);
		
	
		
		
		frame.setVisible(true);
	}
}
