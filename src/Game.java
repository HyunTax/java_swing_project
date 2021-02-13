import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Game extends JFrame implements Runnable, ActionListener, KeyListener {
	Image gameBG;
	Image character;
	Image imgBomb;
	Image imgHP;
	Image endBG;

	int character_x = 275, character_y = 570;
	boolean keyLeft = false;
	boolean keyRight = false;

	public Game() {

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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
