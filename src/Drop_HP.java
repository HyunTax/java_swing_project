
public class Drop_HP {
	int x, y;

	Drop_HP(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void drop_hp() {
		y += 6;
	}
}
