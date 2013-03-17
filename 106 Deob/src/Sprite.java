import java.awt.Component;

public class Sprite extends Surface {
	public Sprite(int width, int height, int l, Component component) {
		super(width, height, l, component);
	}

	public void jg(int j, int k, int l, int i1, int j1, int k1, int l1) {
		if (j1 >= 40000) {
			reference.zm(j, k, l, i1, j1 - 40000, k1, l1);
			return;
		}
		if (j1 >= 20000) {
			reference.en(j, k, l, i1, j1 - 20000, k1, l1);
			return;
		}
		if (j1 >= 5000) {
			reference.vl(j, k, l, i1, j1 - 5000, k1, l1);
			return;
		} else {
			super.uf(j, k, l, i1, j1);
			return;
		}
	}

	public Client reference;
}