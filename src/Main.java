import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Main extends JFrame implements ActionListener {

	public JPanel mainPanel;
	Sound sound = new Sound();
	Thread game;

	ImageIcon mainBG = new ImageIcon("./images/mainBG.png");

	JButton btnStart = new JButton(new ImageIcon("./images/imgStart.png"));
	JButton btnExit = new JButton(new ImageIcon("./images/imgExit.png"));

	public Main() {
		sound.sound("mainBGM.wav", true);
		mainPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(mainBG.getImage(), 0, 0, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};

		mainPanel.setLayout(null);

		btnStart.setBounds(251, 480, 108, 50);
		btnStart.addActionListener(this);
		btnStart.setContentAreaFilled(false);
		btnStart.setBorderPainted(false);
		btnExit.setBounds(251, 550, 108, 50);
		btnExit.addActionListener(this);
		btnExit.setContentAreaFilled(false);
		btnExit.setBorderPainted(false);

		mainPanel.add(btnStart);
		mainPanel.add(btnExit);

		add(mainPanel);
		setTitle("Avoiding Bomb");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setSize(600, 740);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnStart)) {
			sound.sound_stop();
			Game start = new Game();
			game = new Thread(start);
			game.start();
			dispose();
		} else if (e.getSource().equals(btnExit)) {
			System.exit(0);
		}

	}
	
	public void game_stop() {
		game.interrupt();
	}

}
