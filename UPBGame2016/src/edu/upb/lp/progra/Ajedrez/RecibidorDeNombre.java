package edu.upb.lp.progra.Ajedrez;

import edu.upb.lp.progra.adapterFiles.TextListener;

public class RecibidorDeNombre implements TextListener {
	private AjedrezGame game;

	public RecibidorDeNombre(AjedrezGame game) {
		this.game = game;
	}

	public void receiveText(String text) {
		game.setNombreJugador(text);
	}
}
