import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Game extends JFrame implements Runnable, ActionListener, KeyListener {
	Image gameBG;
	Image character;
	Image imgBomb;
	Image imgHP;
	Image endBG;
	Image buffImage;
	Graphics buff;
	JPanel endPanel;
	JButton btnBack = new JButton(new ImageIcon("./images/imgBack.png"));
	int character_x = 275, character_y = 600;
	int cBomb, cHP;
	boolean keyLeft = false;
	boolean keyRight = false;
	ArrayList<Drop_Bomb> lBomb = new ArrayList<Drop_Bomb>();
	ArrayList<Drop_HP> lHP = new ArrayList<Drop_HP>();
	int delay = 0;
	int score = 100;
	int gamestage = 1;
	Sound soundBG = new Sound();
	Sound soundItem = new Sound();
	Drop_Bomb drop_Bomb;
	Drop_HP drop_HP;
	Main main;
	TimerTask timertask = new TimerTask() {
		@Override
		public void run() {
			gamestage++;
			try {
				gameBG = ImageIO.read(new File("./images/gameBG.png"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	public Game() {
		soundBG.sound("gameBGM.wav", true);
		Timer timer = new Timer(); // 게임 타이머 선언
		timer.scheduleAtFixedRate(timertask, 85000, 55000);
		try {
			character = new ImageIcon("./images/character.png").getImage();
			imgBomb = new ImageIcon("./images/imgBomb.png").getImage();
			imgHP = new ImageIcon("./images/imgHP.png").getImage();
			gameBG = ImageIO.read(new File("./images/gameBG.png"));
			endBG = ImageIO.read(new File("./images/endBG.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 엔딩 패널
		endPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(endBG, 0, 0, null);
				g.setFont(new Font("Default", Font.BOLD, 30));
				g.drawString("점수 : " + score, 240, 300);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		endPanel.setLayout(null);
		endPanel.setSize(100, 100);
		btnBack.setBounds(251, 550, 108, 50);
		btnBack.addActionListener(this);
		btnBack.setContentAreaFilled(false);
		btnBack.setBorderPainted(false);

		endPanel.add(btnBack);

		// JFrame 설정
		setTitle("Avoidung_Bomb");
		addKeyListener(this);
		setSize(600, 700);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(!false);
	}

	@Override
	public void run() {
		try {
			while (true) {
				keyProcess();
				switch (gamestage) {

				case 1:
					cBomb++;
					if (cBomb % 25 == 0)
						drop_Bomb();
					break;
				case 2:
					cBomb++;
					cHP++;
					if (cBomb % 15 == 0)
						drop_Bomb();
					if (cHP % 50 == 0)
						drop_HP();
					break;
				case 3:
					delay++;
					if (delay == 200) {
						getContentPane().removeAll();
						getContentPane().add(endPanel);
						endPanel.repaint();
						revalidate();
						main.game_stop();
					}
					break;
				}
				repaint();
				Thread.sleep(10);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 아이템 드랍
	public void drop_Bomb() {
		drop_Bomb = new Drop_Bomb((int) (Math.random() * 550), 0);
		lBomb.add(drop_Bomb);
	}

	public void drop_HP() {
		drop_HP = new Drop_HP((int) (Math.random() * 550), 0);
		lHP.add(drop_HP);
	}

	// 아이템 그리기
	public void Draw_Bomb() {
		for (int i = 0; i < lBomb.size(); ++i) {
			drop_Bomb = lBomb.get(i);
			buff.drawImage(imgBomb, drop_Bomb.x, drop_Bomb.y, this);
			drop_Bomb.drop_bomb();

			if (drop_Bomb.y > 700) {
				lBomb.remove(i);
			} else if (Crash(character_x, character_y, drop_Bomb.x, drop_Bomb.y, character, imgBomb)) {
				soundItem.sound("crash.wav", false);
				lBomb.remove(i);
				score -= 5;
			}
		}
	}

	public void Draw_HP() {
		for (int i = 0; i < lHP.size(); ++i) {
			drop_HP = lHP.get(i);
			buff.drawImage(imgHP, drop_HP.x, drop_HP.y, this);
			drop_HP.drop_hp();

			if (drop_Bomb.y > 700) {
				lHP.remove(i);
			} else if (Crash(character_x, character_y, drop_HP.x, drop_HP.y, character, imgHP)) {
				soundItem.sound("crash.wav", false);
				lHP.remove(i);
				score += 2;
			}
		}
	}

	// 버퍼 그리기
	public void paint(Graphics g) {
		buffImage = createImage(600, 700);
		buff = buffImage.getGraphics();
		buff.drawImage(gameBG, 0, 0, this);
		buff.drawImage(character, character_x, character_y, this);
		buff.setFont(new Font("defalut", Font.BOLD, 30));
		buff.setColor(Color.RED);
		buff.drawString("점수 :" + score, 450, 60);
		Draw_Bomb();
		Draw_HP();
		g.drawImage(buffImage, 0, 0, this);
	}

	public void update(Graphics g) {
		paint(g);
	}

	// 출동 방정식
	private boolean Crash(int character_x2, int character_y2, int x, int y, Image item, Image image) {
		boolean check = false;
		if (Math.abs((character_x2 + item.getWidth(null) / 2)
				- (x + image.getWidth(null) / 2)) < (image.getWidth(null) / 2 + item.getWidth(null) / 2)
				&& Math.abs((character_y2 + item.getHeight(null) / 2)
						- (y + image.getHeight(null) / 2)) < (image.getHeight(null) / 2 + item.getHeight(null) / 2)) {
			check = true;
		} else {
			check = false;
		}
		return check;
	}

	// 방향키 조작 정의
	public void keyProcess() {
		if (keyLeft == true && character_x > 0)
			character_x -= 5;
		if (keyRight == true && character_x < 550)
			character_x += 5;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyPressed = e.getKeyCode();
		switch (keyPressed) {
		case KeyEvent.VK_LEFT:
			keyLeft = true;
			break;
		case KeyEvent.VK_RIGHT:
			keyRight = true;
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyReleased = e.getKeyCode();
		switch (keyReleased) {
		case KeyEvent.VK_LEFT:
			keyLeft = false;
			break;
		case KeyEvent.VK_RIGHT:
			keyRight = false;
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@SuppressWarnings("unused")
	@Override
	public void actionPerformed(ActionEvent e) {
		this.dispose();
		soundBG.sound_stop();
		Main main = new Main();
	}

}
