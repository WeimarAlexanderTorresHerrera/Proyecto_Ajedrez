package edu.upb.lp.progra.Ajedrez;

public class Caballo extends PiezaDeAjedrez {
	private AjedrezGame game;
	private boolean esBlanco;

	public Caballo(AjedrezGame game, boolean esBlanco) {
		this.game = game;
		this.esBlanco = esBlanco;
	}

	public boolean mover(int v, int h, int vertical, int horizontal) {
			boolean puedoMoverme = true;
			if (((Math.abs(vertical - v)) + (Math.abs(h - horizontal)) == 3)
					&& ((h != horizontal) || (v != vertical))) {
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

	public String getNombreDeImagen() {
		if (esBlanco) {
			return "caballoblanco";
		} else {
			return "caballonegro";
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
