package edu.upb.lp.progra.Ajedrez;

public class Torre extends PiezaDeAjedrez {
	private AjedrezGame game;
	private boolean esBlanco;
	private boolean seHaMovidoEnroque = false;

	public Torre(AjedrezGame game, boolean esBlanco) {
		this.game = game;
		this.esBlanco = esBlanco;
	}

	public boolean mover(int v, int h, int vertical, int horizontal) {
			boolean puedoMoverme = true;
			if (vertical == v || horizontal == h) {
				if (!this.game.puedeMoverseLineaRecta(v, h, vertical, horizontal)) {
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
				seHaMovidoEnroque = false;
				return true;
			} else {
				return false;
			}
	}
	
	public boolean getSeHaMovidoEnroque(){
		 return seHaMovidoEnroque;
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
			return "torreblanca";
		} else {
			return "torrenegra";
		}
	}
}
