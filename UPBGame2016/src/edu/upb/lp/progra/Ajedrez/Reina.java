package edu.upb.lp.progra.Ajedrez;

public class Reina extends PiezaDeAjedrez {
	private AjedrezGame game;
	private boolean esBlanco;

	public Reina(AjedrezGame game, boolean esBlanco) {
		this.game = game;
		this.esBlanco = esBlanco;
	}

	public boolean mover(int v, int h, int vertical, int horizontal) {
		boolean puedoMoverme = true;
		if (vertical == v || horizontal == h || v + h == vertical + horizontal || v - h == vertical - horizontal) {
			if (vertical == v || horizontal == h) {
				if (!this.game.puedeMoverseLineaRecta(v, h, vertical, horizontal)) {
					puedoMoverme = false;
				}
			}
			if (v + h == vertical + horizontal || v - h == vertical - horizontal) {
				if (!this.game.puedeMoverseDiagonal(v, h, vertical, horizontal)) {
					puedoMoverme = false;
				}
			}
		} else {
			puedoMoverme = false;
		}
		if (this.game.hayPieza(esBlanco, v, h)) {
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
			return "reinablanca";
		} else {
			return "reinanegra";
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
