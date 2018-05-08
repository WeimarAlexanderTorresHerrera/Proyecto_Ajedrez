package edu.upb.lp.progra.Ajedrez;

public class Rey extends PiezaDeAjedrez {
	private AjedrezGame game;
	private boolean esBlanco;
	private boolean seHaMovidoEnroque = false;

	public Rey(AjedrezGame game, boolean esBlanco) {
		this.game = game;
		this.esBlanco = esBlanco;
	}

	// public boolean mover(int v, int h, int verticalOrigen, int
	// horizontalOrigen) {
	// boolean puedoMoverme = true;
	// // Verificar que la casilla de destino esta a distancia 1
	// if (Math.abs(verticalOrigen - v) > 1 || Math.abs(horizontalOrigen - h) >
	// 1
	// || (verticalOrigen == v && horizontalOrigen == h)) {
	// puedoMoverme = false;
	// }
	// // Verificar que en la casilla de destino no hay una pieza de mi mismo
	// // color
	// if (game.hayPieza(esBlanco, v, h)) {
	// puedoMoverme = false;
	// }
	// // Verificar que la casilla de destino no esta amenazada por una casilla
	// // de distinto color
	// if (game.hayAmenaza(esBlanco, v, h)) {
	// puedoMoverme = false;
	// }
	// // Mover
	// if (puedoMoverme) {
	// this.game.moverOComerPieza(v, h, verticalOrigen, horizontalOrigen);
	// return true;
	// } else {
	// return false;
	// }
	// }

	public boolean mover(int v, int h, int verticalOrigen, int horizontalOrigen) {
		boolean puedoMoverme = true;
		boolean puedoHacerEnroque = false;
		// Verificar que la casilla de destino esta a distancia 1
		if (Math.abs(verticalOrigen - v) > 1 || Math.abs(horizontalOrigen - h) > 1
				|| (verticalOrigen == v && horizontalOrigen == h)) {
			puedoMoverme = false;
		}
		if ((horizontalOrigen - h == 2 || h - horizontalOrigen == 2) && (verticalOrigen == 0 || verticalOrigen == 7)) {
			puedoMoverme = true;
			puedoHacerEnroque = true;
		}

		// Verificar que en la casilla de destino no hay una pieza de mi mismo
		// color
		if (game.hayPieza(esBlanco, v, h)) {
			puedoMoverme = false;

		}
		// Verificar que la casilla de destino no esta amenazada por una casilla
		// de distinto color
		// if (game.hayAmenaza(esBlanco, v, h)) {
		// puedoMoverme = false;
		// }
		// Mover
		if (puedoMoverme) {
			if (puedoHacerEnroque) {
				game.hacerEnroque(verticalOrigen, horizontalOrigen, h);
				seHaMovidoEnroque = true;
			} else {
				this.game.moverOComerPieza(v, h, verticalOrigen, horizontalOrigen);
				seHaMovidoEnroque = true;
			}
			return true;
		} else {
			return false;
		}
	}

	public String getNombreDeImagen() {
		if (esBlanco) {
			return "reyblanco";
		} else {
			return "reynegro";
		}
	}

	public boolean getEsBlanco() {
		if (esBlanco) {
			return true;
		} else {
			return false;
		}
	}

	public boolean getSeHaMovidoEnroque() {
		return seHaMovidoEnroque;
	}

}
