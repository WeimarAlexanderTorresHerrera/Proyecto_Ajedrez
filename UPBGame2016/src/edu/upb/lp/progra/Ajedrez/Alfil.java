package edu.upb.lp.progra.Ajedrez;

public class Alfil extends PiezaDeAjedrez {
	private AjedrezGame game;
	private boolean esBlanco;

	public Alfil(AjedrezGame game, boolean esBlanco) {
		this.game = game;
		this.esBlanco = esBlanco;
	}

	public boolean mover(int v, int h, int vertical, int horizontal) {
			boolean puedoMoverme = true;
			if (v + h == vertical + horizontal || v - h == vertical - horizontal) {
				if (!this.game.puedeMoverseDiagonal(v, h, vertical, horizontal)) {
					puedoMoverme = false;
				}
				if (this.game.hayPieza(esBlanco, v, h)) {
					puedoMoverme = false;
				}
			} else {
				puedoMoverme = false;
			}
			if (puedoMoverme) {
				this.game.moverOComerPieza(v, h, vertical, horizontal);
				return true;
			} else {
				return false;
			}
	}

	public boolean getEsBlanco() {
		if (esBlanco) {
			return true;
		} else {
			return false;
		}
	}

	public String getNombreDeImagen() {
		if (esBlanco) {
			return "alfilblanco";
		} else {
			return "alfilnegro";
		}
	}
}
