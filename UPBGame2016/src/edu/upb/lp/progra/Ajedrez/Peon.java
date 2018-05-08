package edu.upb.lp.progra.Ajedrez;

public class Peon extends PiezaDeAjedrez {
	private AjedrezGame game;
	private boolean esBlanco;

	public Peon(AjedrezGame game, boolean esBlanco) {
		this.game = game;
		this.esBlanco = esBlanco;
	}

	public boolean mover(int v, int h, int vertical, int horizontal) {
		if (this.esBlanco) {
			if (vertical == 6 && h == horizontal && v == vertical - 2 && !this.game.hayPieza(v, h)) {
				this.game.moverOComerPieza(v, h, vertical, horizontal);
				return true;
			}
			if (v == vertical - 1 && h == horizontal && !game.hayPieza(v, h)) {
				game.moverOComerPieza(v, h, vertical, horizontal);
				return true;

			} else if (v == vertical - 1 && (h == horizontal - 1 || h == horizontal + 1)
					&& game.puedeComerPeon(v, h, esBlanco)) {
				game.moverOComerPieza(v, h, vertical, horizontal);
				return true;
			} else {
				return false;
			}
		} else {
			if (vertical == 1 && h == horizontal && v == vertical + 2 && !this.game.hayPieza(v, h)) {
				this.game.moverOComerPieza(v, h, vertical, horizontal);
				return true;
			}
			if (v == vertical + 1 && h == horizontal && !game.hayPieza(v, h)) {
				game.moverOComerPieza(v, h, vertical, horizontal);
				return true;
			} else if (v == vertical + 1 && (h == horizontal - 1 || h == horizontal + 1)
					&& game.puedeComerPeon(v, h, esBlanco)) {
				game.moverOComerPieza(v, h, vertical, horizontal);
				return true;
			} else {
				return false;
			}
		}
	}

	public String getNombreDeImagen() {
		if (esBlanco) {
			return "peonblanco";
		} else {
			return "peonnegro";
		}
	}

	public boolean getEsBlanco() {
		if (esBlanco) {
			return true;
		} else {
			return false;
		}
	}

}
