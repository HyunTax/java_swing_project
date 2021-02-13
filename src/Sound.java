import java.io.BufferedInputStream;
import java.io.FileInputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	Clip clip;

	public void sound(String file, Boolean Loop) {
		try {
			AudioInputStream ais = AudioSystem
					.getAudioInputStream(new BufferedInputStream(new FileInputStream("./sounds/" + file)));
			clip = AudioSystem.getClip();
			clip.open(ais);
			clip.start();
			if (Loop)
				clip.loop(-1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sound_stop() {
		clip.stop();
		clip.close();
	}
}
