package edu.upb.lp.progra.Ajedrez;

import edu.upb.lp.progra.adapterFiles.AndroidGameGUI;
import edu.upb.lp.progra.adapterFiles.UI;

public class AjedrezUI implements UI {
	private AndroidGameGUI gui;
	private AjedrezGame game = new AjedrezGame(this);

	public AjedrezUI(AndroidGameGUI gui) {
		this.gui = gui;
	}

	@Override
	public void onButtonPressed(String name) {
		if (name.equals("Iniciar Juego")) {
			this.game.crearTablero();
			this.game.dibujarTablero();
			this.game.iniciarJuego();
			this.gui.addTextField("Turno", "¡Juegan las blancas!", 16, 40);
			this.gui.askUserText("Por favor introduzca su nombre Negras", new RecibidorDeNombre(this.game));
			this.gui.askUserText("Por favor introduzca su nombre Blancas", new RecibidorDeNombre(this.game));
			this.gui.removeAllButtons();
			this.gui.addButton("Reiniciar Juego", 20, 60);
		} else {
			this.game.borrarTablero();
			this.game.crearTablero();
			this.game.dibujarTablero();
			this.game.iniciarJuego();
			this.gui.addTextField("Turno", "¡Juegan las blancas!", 16, 40);
			this.gui.askUserText("Por favor introduzca su nombre Negras", new RecibidorDeNombre(this.game));
			this.gui.askUserText("Por favor introduzca su nombre Blancas", new RecibidorDeNombre(this.game));
		}
	}

	@Override
	public void onCellPressed(int vertical, int horizontal) {
		this.game.click(vertical, horizontal);

	}

	@Override
	public void initialiseInterface() {
		this.gui.configureGrid(8, 8, 0, 0, 12);
		this.gui.setBottomSectionProportion(0.3);
		this.gui.addButton("Iniciar Juego", 20, 60);
		for (int v = 0; v < 8; v++) {
			for (int h = 0; h < 8; h++) {
				this.dibujarCasillaVacia(v, h);
			}
		}

	}

	public void dibujarCasillaVacia(int v, int h) {
		this.dibujarPieza(v, h, "");
	}

	public void dibujarPieza(int v, int h, String nombreDeImagen) {
		if ((v + h) % 2 == 0) {
			this.gui.setImageOnCell(v, h, nombreDeImagen + "casillablanca");
		} else {
			this.gui.setImageOnCell(v, h, nombreDeImagen + "casillanegra");
		}
	}

	public void dibujarCasillaVaciaSeleccionada(int v, int h) {
		this.dibujarPiezaSeleccionada(v, h, "");
	}

	public void dibujarPiezaSeleccionada(int v, int h, String nombreDeImagen) {
		this.gui.setImageOnCell(v, h, nombreDeImagen + "seleccionado");
	}

	public void mostrarComentario(String mensaje) {
		this.gui.showTemporaryMessage(mensaje, false);
	}

	public void cambiarTurnoEnTexto(boolean esTurnoDeLasBlancas) {
		if (esTurnoDeLasBlancas) {
			this.gui.addTextField("Turno", "¡Juegan las blancas!", 16, 40);
		} else {
			this.gui.addTextField("Turno", "¡Juegan las negras!", 16, 40);
		}

	}

	public void ponerNombreBlancas(String nombreBlancas) {
		this.gui.addTextField("NombreBlancas", "Blancas = " + this.game.getNombreBlancas(), 16, 40);
	}

	public void ponerNombreNegras(String nombreNegras) {
		this.gui.addTextField("NombreNegras", "Negras = " + this.game.getNombreNegras(), 16, 40);
	}
}
