
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.Color;

/**
 * this section is for choosing the list of players
 * @author ashka
 *
 */
public class GamePlay {

	private JPanel contentPane;
	private JFrame frame = new JFrame();
	private JPanel panel;
	private String st;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new GamePlay();
	}

	/**
	 * Create the frame.
	 */
	public GamePlay() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 450, 474);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		frame.setLocationRelativeTo(null);

		Informations obj = new Informations();

		JButton btnStart = new JButton("START");
		btnStart.setBackground(Color.YELLOW);
		btnStart.setFocusable(false);
		btnStart.setFont(new Font("Tahoma", Font.PLAIN, 19));
		btnStart.setBounds(149, 358, 115, 44);
		contentPane.add(btnStart);

		panel = new JPanel();
		panel.setBackground(Color.BLACK);
		panel.setBounds(15, 16, 398, 334);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));

		ButtonGroup bg = new ButtonGroup();

		for (int i = 0; i < Informations.list.size(); i++) {
			JRadioButton rb = new JRadioButton(Informations.list.get(i));
			rb.setFont(new Font("Tahoma", Font.PLAIN, 19));
			rb.setFocusable(false);
			rb.setBackground(Color.BLACK);
			rb.setForeground(Color.YELLOW);
			rb.setBounds(168, 116, 155, 29);
			bg.add(rb);
			panel.add(rb);
			rb.addActionListener(new ActionListener() {//if we choose a one member
				public void actionPerformed(ActionEvent arg0) {

					st = rb.getText();

					btnStart.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							frame.setVisible(false);

							try {
								obj.setName(obj.getName());
								Client.main(null);//run the first client
								obj.setName(st);
								Client2.main(null);//run the secound client
							} catch (Exception e) {
								e.printStackTrace();
							}

						}
					});

				}
			});
		}

		frame.setVisible(true);
	}

}
