package edu.upb.lp.progra.Ajedrez;

public class AjedrezGame {
	private AjedrezUI ui;
	private PiezaDeAjedrez[][] tablero = new PiezaDeAjedrez[8][8];
	private int verticalSeleccionado = -1;
	private int horizontalSeleccionado = -1;
	private boolean[][] casillasQueAmenazanLasBlancas = new boolean[8][8];
	private boolean[][] casillasQueAmenazanLasNegras = new boolean[8][8];
	private boolean iniciarJuego = false;
	private boolean esTurnoDeLasBlancas = true;
	private String nombreBlancas;
	private String nombreNegras;

	public AjedrezGame(AjedrezUI ui) {
		this.ui = ui;
	}

	public void crearTablero() {
		for (int h = 0; h < 8; h++) {
			tablero[1][h] = new Peon(this, false);
		}
		for (int h = 0; h < 8; h++) {
			tablero[6][h] = new Peon(this, true);
		}
		this.tablero[0][4] = new Rey(this, false);
		this.tablero[7][4] = new Rey(this, true);
		this.tablero[0][0] = new Torre(this, false);
		this.tablero[0][7] = new Torre(this, false);
		this.tablero[7][0] = new Torre(this, true);
		this.tablero[7][7] = new Torre(this, true);
		this.tablero[0][2] = new Alfil(this, false);
		this.tablero[0][5] = new Alfil(this, false);
		this.tablero[7][2] = new Alfil(this, true);
		this.tablero[7][5] = new Alfil(this, true);
		this.tablero[0][3] = new Reina(this, false);
		this.tablero[7][3] = new Reina(this, true);
		this.tablero[0][1] = new Caballo(this, false);
		this.tablero[0][6] = new Caballo(this, false);
		this.tablero[7][1] = new Caballo(this, true);
		this.tablero[7][6] = new Caballo(this, true);
	}

	public void borrarTablero() {
		for (int v = 0; v < 8; v++) {
			for (int h = 0; h < 8; h++) {
				tablero[v][h] = null;
			}
		}
		this.nombreBlancas = null;
	}

	public void click(int v, int h) {
		if (this.iniciarJuego) {
			if (verticalSeleccionado == -1 && horizontalSeleccionado == -1) {
				// No he seleccionado nada
				this.seleccionarCasilla(v, h);
			} else {
				if (this.verticalSeleccionado == v && this.horizontalSeleccionado == h) {
					this.dibujarCasilla(v, h);
					this.verticalSeleccionado = -1;
					this.horizontalSeleccionado = -1;
				} else if (this.tablero[this.verticalSeleccionado][this.horizontalSeleccionado] == null) {
					this.seleccionarCasilla(v, h);
				} else {
					// Intentar mover la pieza
					boolean haMovido = false;
					if (this.esTurnoDeLasBlancas == this.tablero[this.verticalSeleccionado][this.horizontalSeleccionado]
							.getEsBlanco()) {
						haMovido = this.tablero[this.verticalSeleccionado][this.horizontalSeleccionado].mover(v, h,
								verticalSeleccionado, horizontalSeleccionado);
						this.ui.cambiarTurnoEnTexto(esTurnoDeLasBlancas);
					}
					if (!haMovido) {
						this.seleccionarCasilla(v, h);
					}
				}
			}
		}
	}

	public void seleccionarCasilla(int v, int h) {
		if (this.verticalSeleccionado != -1 && this.horizontalSeleccionado != -1) {
			this.dibujarCasilla(this.verticalSeleccionado, this.horizontalSeleccionado);
		}
		if (tablero[v][h] == null) {
			this.ui.dibujarCasillaVaciaSeleccionada(v, h);
		} else {
			this.ui.dibujarPiezaSeleccionada(v, h, tablero[v][h].getNombreDeImagen());
		}
		this.verticalSeleccionado = v;
		this.horizontalSeleccionado = h;
	}

	public void dibujarTablero() {
		for (int v = 0; v < 8; v++) {
			for (int h = 0; h < 8; h++) {
				this.dibujarCasilla(v, h);
			}
		}
	}

	public void dibujarCasilla(int v, int h) {
		if (tablero[v][h] == null) {
			this.ui.dibujarCasillaVacia(v, h);
		} else {
			this.ui.dibujarPieza(v, h, tablero[v][h].getNombreDeImagen());
		}

	}

	public void moverOComerPieza(int v, int h, int vertical, int horizontal) {
		if (tablero[v][h] instanceof Rey
				&& tablero[vertical][horizontal].getEsBlanco() != tablero[v][h].getEsBlanco()) {
			this.ui.mostrarComentario("¡Fin del juego!");
			this.borrarTablero();
			this.dibujarTablero();

		} else {

			// PiezaDeAjedrez piezaEnvh = this.tablero[v][h];
			PiezaDeAjedrez piezaEnVerticalHorizontal = this.tablero[vertical][horizontal];
			this.tablero[v][h] = this.tablero[vertical][horizontal];
			this.tablero[vertical][horizontal] = null;
			// if (!this.estaEnJaque(piezaEnVerticalHorizontal.getEsBlanco())) {
			verticalSeleccionado = -1;
			horizontalSeleccionado = -1;
			this.cambiarImagen(v, h, vertical, horizontal);
			this.cambiarTurno();
			// if (jaqueMate(!piezaEnVerticalHorizontal.getEsBlanco())) {
			// this.ui.mostrarComentario("Jaque Mate");
			// }
			if (this.estaEnJaque(!piezaEnVerticalHorizontal.getEsBlanco())) {
				this.ui.mostrarComentario("¡Jaque!");
			}

			// } else {
			// this.tablero[v][h] = piezaEnvh;
			// this.tablero[vertical][horizontal] = piezaEnVerticalHorizontal;
			// }
		}
	}

	public void cambiarImagen(int v, int h, int vertical, int horizontal) {
		this.ui.dibujarCasillaVacia(vertical, horizontal);
		this.ui.dibujarPieza(v, h, tablero[v][h].getNombreDeImagen());
	}

	public void mostrarComentario(String mensaje) {
		this.ui.mostrarComentario(mensaje);
	}

	public void casillasQueAmenazanLasBlancas() {
		for (int v = 0; v < 8; v++) {
			for (int h = 0; h < 8; h++) {
				this.casillasQueAmenazanLasBlancas[v][h] = false;
			}
		}
		for (int v = 0; v < 8; v++) {
			for (int h = 0; h < 8; h++) {
				if (this.tablero[v][h] instanceof Peon && this.tablero[v][h].getEsBlanco()) {
					if (v != 7) {
						if (h == 0) {
							this.casillasQueAmenazanLasBlancas[v - 1][h + 1] = true;
						} else if (h == 7) {
							this.casillasQueAmenazanLasBlancas[v - 1][h - 1] = true;
						} else {
							this.casillasQueAmenazanLasBlancas[v - 1][h + 1] = true;
							this.casillasQueAmenazanLasBlancas[v - 1][h - 1] = true;
						}
					}
				} else if (this.tablero[v][h] instanceof Rey && this.tablero[v][h].getEsBlanco()) {
					if (h > 0) {
						if (v > 0) {
							this.casillasQueAmenazanLasBlancas[v - 1][h - 1] = true;
						}
						this.casillasQueAmenazanLasBlancas[v][h - 1] = true;
						if (v < 7) {
							this.casillasQueAmenazanLasBlancas[v + 1][h - 1] = true;
						}
					}
					if (v > 0) {
						this.casillasQueAmenazanLasBlancas[v - 1][h] = true;
					}
					if (v < 7) {
						this.casillasQueAmenazanLasBlancas[v + 1][h] = true;
					}
					if (h < 7) {
						if (v > 0) {
							this.casillasQueAmenazanLasBlancas[v - 1][h + 1] = true;
						}
						this.casillasQueAmenazanLasBlancas[v][h + 1] = true;
						if (v < 7) {
							this.casillasQueAmenazanLasBlancas[v + 1][h + 1] = true;
						}
					}
				} else if (this.tablero[v][h] instanceof Torre && this.tablero[v][h].getEsBlanco()) {
					if (v != 7) {
						for (int ver = v + 1; ver < 8
								&& (this.tablero[ver][h] == null || (this.tablero[ver][h] instanceof Rey
										&& !this.tablero[ver][h].getEsBlanco())); ver++) {
							this.casillasQueAmenazanLasBlancas[ver][h] = true;
						}
					}
					if (v != 0) {
						for (int ver = v - 1; ver > -1
								&& (this.tablero[ver][h] == null || (this.tablero[ver][h] instanceof Rey
										&& !this.tablero[ver][h].getEsBlanco())); ver--) {
							this.casillasQueAmenazanLasBlancas[ver][h] = true;
						}
					}
					if (h != 7) {
						for (int hor = h + 1; hor < 8
								&& (this.tablero[v][hor] == null || (this.tablero[v][hor] instanceof Rey
										&& !this.tablero[v][hor].getEsBlanco())); hor++) {
							this.casillasQueAmenazanLasBlancas[v][hor] = true;
						}
					}
					if (h != 0) {
						for (int hor = h - 1; hor > -1
								&& (this.tablero[v][hor] == null || (this.tablero[v][hor] instanceof Rey
										&& !this.tablero[v][hor].getEsBlanco())); hor--) {
							this.casillasQueAmenazanLasBlancas[v][hor] = true;
						}
					}
				} else if (this.tablero[v][h] instanceof Alfil && this.tablero[v][h].getEsBlanco()) {
					boolean encontroUnaCasillaConPieza = false;
					for (int j = 1, ver = v - 1; v != 0 && ver > -1; ver--) {
						int i = ver;
						for (int hor = h - j; h != 0 && hor > -1 && i == ver; hor--) {
							i--;
							j++;
							if ((this.tablero[ver][hor] == null
									|| (this.tablero[ver][hor] instanceof Rey && !this.tablero[ver][hor].getEsBlanco()))
									&& !encontroUnaCasillaConPieza) {
								this.casillasQueAmenazanLasBlancas[ver][hor] = true;
							} else {
								encontroUnaCasillaConPieza = true;
							}
						}
					}
					encontroUnaCasillaConPieza = false;
					for (int ver = v - 1, j = 1; v != 0 && ver > -1; ver--) {
						int i = ver;
						for (int hor = h + j; h != 7 && hor < 8 && i == ver; hor++) {
							i--;
							j++;
							if ((this.tablero[ver][hor] == null
									|| (this.tablero[ver][hor] instanceof Rey && !this.tablero[ver][hor].getEsBlanco()))
									&& !encontroUnaCasillaConPieza) {
								this.casillasQueAmenazanLasBlancas[ver][hor] = true;
							} else {
								encontroUnaCasillaConPieza = true;
							}
						}

					}
					encontroUnaCasillaConPieza = false;
					for (int ver = v + 1, j = 1; v != 7 && ver < 8; ver++) {
						int i = ver;
						for (int hor = h - j; h != 0 && hor > -1 && i == ver; hor--) {
							i--;
							j++;
							if ((this.tablero[ver][hor] == null
									|| (this.tablero[ver][hor] instanceof Rey && !this.tablero[ver][hor].getEsBlanco()))
									&& !encontroUnaCasillaConPieza) {
								this.casillasQueAmenazanLasBlancas[ver][hor] = true;
							} else {
								encontroUnaCasillaConPieza = true;
							}
						}
					}
					encontroUnaCasillaConPieza = false;
					for (int ver = v + 1, j = 1; v != 7 && ver < 8; ver++) {
						int i = ver;
						for (int hor = h + j; h != 7 && hor < 8 && i == ver; hor++) {
							i--;
							j++;
							if ((this.tablero[ver][hor] == null
									|| (this.tablero[ver][hor] instanceof Rey && !this.tablero[ver][hor].getEsBlanco()))
									&& !encontroUnaCasillaConPieza) {
								this.casillasQueAmenazanLasBlancas[ver][hor] = true;
							} else {
								encontroUnaCasillaConPieza = true;
							}
						}
					}
				} else if (this.tablero[v][h] instanceof Reina && this.tablero[v][h].getEsBlanco()) {
					if (v != 7) {
						for (int ver = v + 1; ver < 8
								&& (this.tablero[ver][h] == null || (this.tablero[ver][h] instanceof Rey
										&& !this.tablero[ver][h].getEsBlanco())); ver++) {
							this.casillasQueAmenazanLasBlancas[ver][h] = true;
						}
					}
					if (v != 0) {
						for (int ver = v - 1; ver > -1
								&& (this.tablero[ver][h] == null || (this.tablero[ver][h] instanceof Rey
										&& !this.tablero[ver][h].getEsBlanco())); ver--) {
							this.casillasQueAmenazanLasBlancas[ver][h] = true;
						}
					}
					if (h != 7) {
						for (int hor = h + 1; hor < 8
								&& (this.tablero[v][hor] == null || (this.tablero[v][hor] instanceof Rey
										&& !this.tablero[v][hor].getEsBlanco())); hor++) {
							this.casillasQueAmenazanLasBlancas[v][hor] = true;
						}
					}
					if (h != 0) {
						for (int hor = h - 1; hor > -1
								&& (this.tablero[v][hor] == null || (this.tablero[v][hor] instanceof Rey
										&& !this.tablero[v][hor].getEsBlanco())); hor--) {
							this.casillasQueAmenazanLasBlancas[v][hor] = true;
						}
					}
					boolean encontroUnaCasillaConPieza = false;
					for (int j = 1, ver = v - 1; v != 0 && ver > -1; ver--) {
						int i = ver;
						for (int hor = h - j; h != 0 && hor > -1 && i == ver; hor--) {
							i--;
							j++;
							if ((this.tablero[ver][hor] == null
									|| (this.tablero[ver][hor] instanceof Rey && !this.tablero[ver][hor].getEsBlanco()))
									&& !encontroUnaCasillaConPieza) {
								this.casillasQueAmenazanLasBlancas[ver][hor] = true;
							} else {
								encontroUnaCasillaConPieza = true;
							}
						}
					}
					encontroUnaCasillaConPieza = false;
					for (int ver = v - 1, j = 1; v != 0 && ver > -1; ver--) {
						int i = ver;
						for (int hor = h + j; h != 7 && hor < 8 && i == ver; hor++) {
							i--;
							j++;
							if ((this.tablero[ver][hor] == null
									|| (this.tablero[ver][hor] instanceof Rey && !this.tablero[ver][hor].getEsBlanco()))
									&& !encontroUnaCasillaConPieza) {
								this.casillasQueAmenazanLasBlancas[ver][hor] = true;
							} else {
								encontroUnaCasillaConPieza = true;
							}
						}

					}
					encontroUnaCasillaConPieza = false;
					for (int ver = v + 1, j = 1; v != 7 && ver < 8; ver++) {
						int i = ver;
						for (int hor = h - j; h != 0 && hor > -1 && i == ver; hor--) {
							i--;
							j++;
							if ((this.tablero[ver][hor] == null
									|| (this.tablero[ver][hor] instanceof Rey && !this.tablero[ver][hor].getEsBlanco()))
									&& !encontroUnaCasillaConPieza) {
								this.casillasQueAmenazanLasBlancas[ver][hor] = true;
							} else {
								encontroUnaCasillaConPieza = true;
							}
						}
					}
					encontroUnaCasillaConPieza = false;
					for (int ver = v + 1, j = 1; v != 7 && ver < 8; ver++) {
						int i = ver;
						for (int hor = h + j; h != 7 && hor < 8 && i == ver; hor++) {
							i--;
							j++;
							if ((this.tablero[ver][hor] == null
									|| (this.tablero[ver][hor] instanceof Rey && !this.tablero[ver][hor].getEsBlanco()))
									&& !encontroUnaCasillaConPieza) {
								this.casillasQueAmenazanLasBlancas[ver][hor] = true;
							} else {
								encontroUnaCasillaConPieza = true;
							}
						}
					}
				} else if (this.tablero[v][h] instanceof Caballo && this.tablero[v][h].getEsBlanco()) {
					for (int ver = 0; ver < 8; ver++) {
						for (int hor = 0; hor < 8; hor++) {
							if (((Math.abs(v - ver)) + (Math.abs(h - hor)) == 3) && ((h != hor) && (v != ver))) {
								this.casillasQueAmenazanLasBlancas[ver][hor] = true;
							}
						}
					}
				}
			}
		}
	}

	public void casillasQueAmenazanLasNegras() {
		for (int v = 0; v < 8; v++) {
			for (int h = 0; h < 8; h++) {
				this.casillasQueAmenazanLasNegras[v][h] = false;
			}
		}
		for (int v = 0; v < 8; v++) {
			for (int h = 0; h < 8; h++) {
				if (this.tablero[v][h] instanceof Peon && !this.tablero[v][h].getEsBlanco()) {
					if (v != 7) {
						if (h == 0) {
							this.casillasQueAmenazanLasNegras[v + 1][h + 1] = true;
						} else if (h == 7) {
							this.casillasQueAmenazanLasNegras[v + 1][h - 1] = true;
						} else {
							this.casillasQueAmenazanLasNegras[v + 1][h + 1] = true;
							this.casillasQueAmenazanLasNegras[v + 1][h - 1] = true;
						}
					}
				} else if (this.tablero[v][h] instanceof Rey && !this.tablero[v][h].getEsBlanco()) {
					if (h > 0) {
						if (v > 0) {
							this.casillasQueAmenazanLasNegras[v - 1][h - 1] = true;
						}
						this.casillasQueAmenazanLasNegras[v][h - 1] = true;
						if (v < 7) {
							this.casillasQueAmenazanLasNegras[v + 1][h - 1] = true;
						}
					}
					if (v > 0) {
						this.casillasQueAmenazanLasNegras[v - 1][h] = true;
					}
					if (v < 7) {
						this.casillasQueAmenazanLasNegras[v + 1][h] = true;
					}
					if (h < 7) {
						if (v > 0) {
							this.casillasQueAmenazanLasNegras[v - 1][h + 1] = true;
						}
						this.casillasQueAmenazanLasNegras[v][h + 1] = true;
						if (v < 7) {
							this.casillasQueAmenazanLasNegras[v + 1][h + 1] = true;
						}
					}
				} else if (this.tablero[v][h] instanceof Torre && !this.tablero[v][h].getEsBlanco()) {
					if (v != 7) {
						for (int ver = v + 1; ver < 8
								&& (this.tablero[ver][h] == null || (this.tablero[ver][h] instanceof Rey
										&& this.tablero[ver][h].getEsBlanco())); ver++) {
							this.casillasQueAmenazanLasNegras[ver][h] = true;
						}
					}
					if (v != 0) {
						for (int ver = v - 1; ver > -1
								&& (this.tablero[ver][h] == null || (this.tablero[ver][h] instanceof Rey
										&& this.tablero[ver][h].getEsBlanco())); ver--) {
							this.casillasQueAmenazanLasNegras[ver][h] = true;
						}
					}
					if (h != 7) {
						for (int hor = h + 1; hor < 8
								&& (this.tablero[v][hor] == null || (this.tablero[v][hor] instanceof Rey
										&& this.tablero[v][hor].getEsBlanco())); hor++) {
							this.casillasQueAmenazanLasNegras[v][hor] = true;
						}
					}
					if (h != 0) {
						for (int hor = h - 1; hor > -1
								&& (this.tablero[v][hor] == null || (this.tablero[v][hor] instanceof Rey
										&& this.tablero[v][hor].getEsBlanco())); hor--) {
							this.casillasQueAmenazanLasNegras[v][hor] = true;
						}
					}
				} else if (this.tablero[v][h] instanceof Alfil && !this.tablero[v][h].getEsBlanco()) {
					boolean encontroUnaCasillaConPieza = false;
					for (int j = 1, ver = v - 1; v != 0 && ver > -1; ver--) {
						int i = ver;
						for (int hor = h - j; h != 0 && hor > -1 && i == ver; hor--) {
							i--;
							j++;
							if ((this.tablero[ver][hor] == null
									|| (this.tablero[ver][hor] instanceof Rey && this.tablero[ver][hor].getEsBlanco()))
									&& !encontroUnaCasillaConPieza) {
								this.casillasQueAmenazanLasNegras[ver][hor] = true;
							} else {
								encontroUnaCasillaConPieza = true;
							}
						}
					}
					encontroUnaCasillaConPieza = false;
					for (int ver = v - 1, j = 1; v != 0 && ver > -1; ver--) {
						int i = ver;
						for (int hor = h + j; h != 7 && hor < 8 && i == ver; hor++) {
							i--;
							j++;
							if ((this.tablero[ver][hor] == null
									|| (this.tablero[ver][hor] instanceof Rey && this.tablero[ver][hor].getEsBlanco()))
									&& !encontroUnaCasillaConPieza) {
								this.casillasQueAmenazanLasNegras[ver][hor] = true;
							} else {
								encontroUnaCasillaConPieza = true;
							}
						}

					}
					encontroUnaCasillaConPieza = false;
					for (int ver = v + 1, j = 1; v != 7 && ver < 8; ver++) {
						int i = ver;
						for (int hor = h - j; h != 0 && hor > -1 && i == ver; hor--) {
							i--;
							j++;
							if ((this.tablero[ver][hor] == null
									|| (this.tablero[ver][hor] instanceof Rey && this.tablero[ver][hor].getEsBlanco()))
									&& !encontroUnaCasillaConPieza) {
								this.casillasQueAmenazanLasNegras[ver][hor] = true;
							} else {
								encontroUnaCasillaConPieza = true;
							}
						}
					}
					encontroUnaCasillaConPieza = false;
					for (int ver = v + 1, j = 1; v != 7 && ver < 8; ver++) {
						int i = ver;
						for (int hor = h + j; h != 7 && hor < 8 && i == ver; hor++) {
							i--;
							j++;
							if ((this.tablero[ver][hor] == null
									|| (this.tablero[ver][hor] instanceof Rey && this.tablero[ver][hor].getEsBlanco()))
									&& !encontroUnaCasillaConPieza) {
								this.casillasQueAmenazanLasNegras[ver][hor] = true;
							} else {
								encontroUnaCasillaConPieza = true;
							}
						}
					}
				} else if (this.tablero[v][h] instanceof Reina && !this.tablero[v][h].getEsBlanco()) {
					if (v != 7) {
						for (int ver = v + 1; ver < 8
								&& (this.tablero[ver][h] == null || (this.tablero[ver][h] instanceof Rey
										&& this.tablero[ver][h].getEsBlanco())); ver++) {
							this.casillasQueAmenazanLasNegras[ver][h] = true;
						}
					}
					if (v != 0) {
						for (int ver = v - 1; ver > -1
								&& (this.tablero[ver][h] == null || (this.tablero[ver][h] instanceof Rey
										&& this.tablero[ver][h].getEsBlanco())); ver--) {
							this.casillasQueAmenazanLasNegras[ver][h] = true;
						}
					}
					if (h != 7) {
						for (int hor = h + 1; hor < 8
								&& (this.tablero[v][hor] == null || (this.tablero[v][hor] instanceof Rey
										&& this.tablero[v][hor].getEsBlanco())); hor++) {
							this.casillasQueAmenazanLasNegras[v][hor] = true;
						}
					}
					if (h != 0) {
						for (int hor = h - 1; hor > -1
								&& (this.tablero[v][hor] == null || (this.tablero[v][hor] instanceof Rey
										&& this.tablero[v][hor].getEsBlanco())); hor--) {
							this.casillasQueAmenazanLasNegras[v][hor] = true;
						}
					}
					boolean encontroUnaCasillaConPieza = false;
					for (int j = 1, ver = v - 1; v != 0 && ver > -1; ver--) {
						int i = ver;
						for (int hor = h - j; h != 0 && hor > -1 && i == ver; hor--) {
							i--;
							j++;
							if ((this.tablero[ver][hor] == null
									|| (this.tablero[ver][hor] instanceof Rey && this.tablero[ver][hor].getEsBlanco()))
									&& !encontroUnaCasillaConPieza) {
								this.casillasQueAmenazanLasNegras[ver][hor] = true;
							} else {
								encontroUnaCasillaConPieza = true;
							}
						}
					}
					encontroUnaCasillaConPieza = false;
					for (int ver = v - 1, j = 1; v != 0 && ver > -1; ver--) {
						int i = ver;
						for (int hor = h + j; h != 7 && hor < 8 && i == ver; hor++) {
							i--;
							j++;
							if ((this.tablero[ver][hor] == null
									|| (this.tablero[ver][hor] instanceof Rey && this.tablero[ver][hor].getEsBlanco()))
									&& !encontroUnaCasillaConPieza) {
								this.casillasQueAmenazanLasNegras[ver][hor] = true;
							} else {
								encontroUnaCasillaConPieza = true;
							}
						}

					}
					encontroUnaCasillaConPieza = false;
					for (int ver = v + 1, j = 1; v != 7 && ver < 8; ver++) {
						int i = ver;
						for (int hor = h - j; h != 0 && hor > -1 && i == ver; hor--) {
							i--;
							j++;
							if ((this.tablero[ver][hor] == null
									|| (this.tablero[ver][hor] instanceof Rey && this.tablero[ver][hor].getEsBlanco()))
									&& !encontroUnaCasillaConPieza) {
								this.casillasQueAmenazanLasNegras[ver][hor] = true;
							} else {
								encontroUnaCasillaConPieza = true;
							}
						}
					}
					encontroUnaCasillaConPieza = false;
					for (int ver = v + 1, j = 1; v != 7 && ver < 8; ver++) {
						int i = ver;
						for (int hor = h + j; h != 7 && hor < 8 && i == ver; hor++) {
							i--;
							j++;
							if ((this.tablero[ver][hor] == null
									|| (this.tablero[ver][hor] instanceof Rey && this.tablero[ver][hor].getEsBlanco()))
									&& !encontroUnaCasillaConPieza) {
								this.casillasQueAmenazanLasNegras[ver][hor] = true;
							} else {
								encontroUnaCasillaConPieza = true;
							}
						}
					}
				} else if (this.tablero[v][h] instanceof Caballo && !this.tablero[v][h].getEsBlanco()) {
					for (int ver = 0; ver < 8; ver++) {
						for (int hor = 0; hor < 8; hor++) {
							if (((Math.abs(v - ver)) + (Math.abs(h - hor)) == 3) && ((h != hor) && (v != ver))) {
								this.casillasQueAmenazanLasNegras[ver][hor] = true;
							}
						}
					}
				}
			}
		}

	}

	public boolean hayPieza(boolean esBlanco, int v, int h) {
		if (tablero[v][h] == null) {
			return false;
		} else {
			return tablero[v][h].getEsBlanco() == esBlanco;
		}
	}

	public boolean hayAmenaza(boolean esBlanco, int v, int h) {
		if (esBlanco) {
			casillasQueAmenazanLasNegras();
			return this.casillasQueAmenazanLasNegras[v][h];
		} else {
			casillasQueAmenazanLasBlancas();
			return this.casillasQueAmenazanLasBlancas[v][h];
		}
	}

	public boolean hayPieza(int v, int h) {
		if (tablero[v][h] == null) {
			return false;
		} else {
			return true;
		}
	}

	public boolean puedeComerPeon(int v, int h, boolean esBlanco) {
		if (tablero[v][h] == null) {
			return false;
		} else if (tablero[v][h].getEsBlanco() == esBlanco) {
			return false;
		} else {
			return true;
		}
	}

	public void iniciarJuego() {
		this.iniciarJuego = true;
		this.esTurnoDeLasBlancas = true;
	}

	public void cambiarTurno() {
		this.esTurnoDeLasBlancas = !this.esTurnoDeLasBlancas;
	}

	public boolean puedeMoverseLineaRecta(int v, int h, int vertical, int horizontal) {
		boolean puedeMoverse = true;
		if (vertical < v) {
			for (int ver = vertical + 1; ver < v; ver++) {
				if (this.tablero[ver][h] != null) {
					puedeMoverse = false;
				}
			}
		} else {
			for (int ver = vertical - 1; ver > v; ver--) {
				if (this.tablero[ver][h] != null) {
					puedeMoverse = false;
				}
			}
		}
		if (horizontal < h) {
			for (int hor = horizontal + 1; hor < h; hor++) {
				if (this.tablero[v][hor] != null) {
					puedeMoverse = false;
				}
			}
		} else {
			for (int hor = horizontal - 1; hor > h; hor--) {
				if (this.tablero[v][hor] != null) {
					puedeMoverse = false;
				}
			}
		}
		if (puedeMoverse) {
			return true;
		} else {
			return false;
		}
	}

	public boolean puedeMoverseDiagonal(int v, int h, int vertical, int horizontal) {
		boolean puedeMoverse = true;
		if (v < vertical && h < horizontal) {
			int j = 1;
			for (int ver = vertical - 1; ver > v; ver--) {
				int i = ver;
				for (int hor = horizontal - j; hor > h && i == ver; hor--) {
					i--;
					j++;
					if (this.tablero[ver][hor] != null) {
						puedeMoverse = false;
					}
				}
			}
		} else if (v < vertical && h > horizontal) {
			int j = 1;
			for (int ver = vertical - 1; ver > v; ver--) {
				int i = ver;
				for (int hor = horizontal + j; hor < h && i == ver; hor++) {
					i--;
					j++;
					if (this.tablero[ver][hor] != null) {
						puedeMoverse = false;
					}
				}

			}
		} else if (v > vertical && h < horizontal) {
			int j = 1;
			for (int ver = vertical + 1; ver < v; ver++) {
				int i = ver;
				for (int hor = horizontal - j; hor > h && i == ver; hor--) {
					i--;
					j++;
					if (this.tablero[ver][hor] != null) {
						puedeMoverse = false;
					}
				}
			}
		} else {
			int j = 1;
			for (int ver = vertical + 1; ver < v; ver++) {
				int i = ver;
				for (int hor = horizontal + j; hor < h && i == ver; hor++) {
					i--;
					j++;
					if (this.tablero[ver][hor] != null) {
						puedeMoverse = false;
					}
				}
			}
		}
		if (puedeMoverse) {
			return true;
		} else {
			return false;
		}
	}

	public boolean estaEnJaque(boolean esBlanco) {
		this.casillasQueAmenazanLasBlancas();
		this.casillasQueAmenazanLasNegras();
		for (int v = 0; v < 8; v++) {
			for (int h = 0; h < 8; h++) {
				if (this.tablero[v][h] instanceof Rey && this.tablero[v][h].getEsBlanco()
						&& this.casillasQueAmenazanLasNegras[v][h] && esBlanco) {
					return true;
				}
				if (this.tablero[v][h] instanceof Rey && !this.tablero[v][h].getEsBlanco()
						&& this.casillasQueAmenazanLasBlancas[v][h] && !esBlanco) {
					return true;
				}
			}
		}
		return false;
	}

	public void hacerEnroque(int verticalOrigen, int horizontalOrigen, int h) {
		boolean puedoHacerEnroque = true;
		this.casillasQueAmenazanLasBlancas();
		this.casillasQueAmenazanLasNegras();
		// Enroque corto
		if (horizontalOrigen < h) {

			if (tablero[verticalOrigen][horizontalOrigen].getEsBlanco() && verticalOrigen == 7) {
				// Rey blanco
				for (int hor = 5; hor < 7; hor++) {
					if (tablero[7][hor] != null) {
						puedoHacerEnroque = false;
					}
				}
				if (tablero[verticalOrigen][horizontalOrigen].getSeHaMovidoEnroque()
						|| tablero[verticalOrigen][7].getSeHaMovidoEnroque()) {
					puedoHacerEnroque = false;
				}
				if (casillasQueAmenazanLasNegras[verticalOrigen][horizontalOrigen + 1]) {
					puedoHacerEnroque = false;
				}
			} else {
				// Rey negro
				for (int hor = 5; hor < 7; hor++) {
					if (tablero[0][hor] != null) {
						puedoHacerEnroque = false;

					}
				}
				if (tablero[verticalOrigen][horizontalOrigen].getSeHaMovidoEnroque()
						|| tablero[verticalOrigen][7].getSeHaMovidoEnroque()) {
					puedoHacerEnroque = false;
				}
				if (casillasQueAmenazanLasBlancas[verticalOrigen][horizontalOrigen + 1]) {
					puedoHacerEnroque = false;
				}
			}
			if (puedoHacerEnroque) {
				this.moverOComerPieza(verticalOrigen, 5, verticalOrigen, 7);
				this.moverOComerPieza(verticalOrigen, 6, verticalOrigen, horizontalOrigen);
				this.cambiarTurno();
			}
		} else {
			// Enroque largo

			if (tablero[verticalOrigen][horizontalOrigen].getEsBlanco() && verticalOrigen == 7) {
				for (int hor = 3; hor > 0; hor--) {
					if (tablero[7][hor] != null) {
						puedoHacerEnroque = false;
					}
				}
				if (tablero[verticalOrigen][horizontalOrigen].getSeHaMovidoEnroque()
						|| tablero[verticalOrigen][0].getSeHaMovidoEnroque()) {
					puedoHacerEnroque = false;
				}
				if (casillasQueAmenazanLasNegras[verticalOrigen][horizontalOrigen - 1]) {
					puedoHacerEnroque = false;
				}

			} else if (!tablero[verticalOrigen][horizontalOrigen].getEsBlanco() && verticalOrigen == 0) {
				// Rey negro
				for (int hor = 3; hor > 0; hor--) {
					if (tablero[0][hor] != null) {
						puedoHacerEnroque = false;
					}
				}
				if (tablero[verticalOrigen][horizontalOrigen].getSeHaMovidoEnroque()
						|| tablero[verticalOrigen][0].getSeHaMovidoEnroque()) {
					puedoHacerEnroque = false;
				}
				if (casillasQueAmenazanLasBlancas[verticalOrigen][horizontalOrigen - 1]) {
					puedoHacerEnroque = false;
				}

			}
			if (puedoHacerEnroque) {
				this.moverOComerPieza(verticalOrigen, 3, verticalOrigen, 0);
				this.moverOComerPieza(verticalOrigen, 2, verticalOrigen, horizontalOrigen);
				this.cambiarTurno();
			}

		}
	}

	public boolean jaqueMate(boolean esBlanco) {
		boolean casillasDeJaque = true;
		this.casillasQueAmenazanLasBlancas();
		this.casillasQueAmenazanLasNegras();
		for (int v = 0; v < 8; v++) {
			for (int h = 0; h < 8; h++) {
				if (this.tablero[v][h] instanceof Rey && this.tablero[v][h].getEsBlanco()) {
					if (h > 0) {
						if (v > 0) {
							if (!this.casillasQueAmenazanLasNegras[v - 1][h - 1] && this.hayPieza(true, v - 1, h - 1)) {
								casillasQueAmenazanLasNegras[v - 1][h - 1] = this.hayPieza(true, v - 1, h - 1);
							}
						}
						if (!this.casillasQueAmenazanLasNegras[v][h - 1] && this.hayPieza(true, v, h - 1)) {
							casillasQueAmenazanLasNegras[v][h - 1] = this.hayPieza(true, v, h - 1);
						}
						if (v < 7) {
							if (!this.casillasQueAmenazanLasNegras[v + 1][h - 1] && this.hayPieza(true, v + 1, h - 1)) {
								casillasQueAmenazanLasNegras[v + 1][h - 1] = this.hayPieza(true, v + 1, h - 1);
							}
						}
					}
					if (v > 0) {
						if (!this.casillasQueAmenazanLasNegras[v - 1][h] && this.hayPieza(true, v - 1, h)) {
							casillasQueAmenazanLasNegras[v - 1][h] = this.hayPieza(true, v - 1, h);
						}
					}
					if (v < 7) {
						if (!this.casillasQueAmenazanLasNegras[v + 1][h] && this.hayPieza(true, v + 1, h)) {
							casillasQueAmenazanLasNegras[v + 1][h] = this.hayPieza(true, v + 1, h);
						}
					}
					if (h < 7) {
						if (v > 0) {
							if (!this.casillasQueAmenazanLasNegras[v - 1][h + 1] && this.hayPieza(true, v - 1, h + 1)) {
								casillasQueAmenazanLasNegras[v - 1][h + 1] = this.hayPieza(true, v - 1, h + 1);
							}
						}
						if (!this.casillasQueAmenazanLasNegras[v][h + 1] && this.hayPieza(true, v, h + 1)) {
							casillasQueAmenazanLasNegras[v][h + 1] = this.hayPieza(true, v, h + 1);
						}
						if (v < 7) {
							if (!this.casillasQueAmenazanLasNegras[v + 1][h + 1] && this.hayPieza(true, v + 1, h + 1)) {
								casillasQueAmenazanLasNegras[v + 1][h + 1] = this.hayPieza(true, v + 1, h + 1);
							}
						}
					}

					if (h > 0) {
						if (v > 0) {
							casillasDeJaque = casillasDeJaque && this.casillasQueAmenazanLasNegras[v - 1][h - 1];
						}
						casillasDeJaque = casillasDeJaque && this.casillasQueAmenazanLasNegras[v][h - 1];
						if (v < 7) {
							casillasDeJaque = casillasDeJaque && this.casillasQueAmenazanLasNegras[v + 1][h - 1];
						}
					}
					if (v > 0) {
						casillasDeJaque = casillasDeJaque && this.casillasQueAmenazanLasNegras[v - 1][h];
					}
					if (v < 7) {
						casillasDeJaque = casillasDeJaque && this.casillasQueAmenazanLasNegras[v + 1][h];
					}
					if (h < 7) {
						if (v > 0) {
							casillasDeJaque = casillasDeJaque && this.casillasQueAmenazanLasNegras[v - 1][h + 1];
						}
						casillasDeJaque = casillasDeJaque && this.casillasQueAmenazanLasNegras[v][h + 1];
						if (v < 7) {
							casillasDeJaque = casillasDeJaque && this.casillasQueAmenazanLasNegras[v + 1][h + 1];
						}
					}
					if (this.estaEnJaque(esBlanco) && casillasDeJaque) {
						return true;
					}
					casillasDeJaque = true;
				}
				if (this.tablero[v][h] instanceof Rey && !this.tablero[v][h].getEsBlanco()) {
					if (h > 0) {
						if (v > 0) {
							if (!this.casillasQueAmenazanLasBlancas[v - 1][h - 1]
									&& this.hayPieza(false, v - 1, h - 1)) {
								casillasQueAmenazanLasBlancas[v - 1][h - 1] = this.hayPieza(false, v - 1, h - 1);
							}
						}
						if (!this.casillasQueAmenazanLasBlancas[v][h - 1] && this.hayPieza(false, v, h - 1)) {
							casillasQueAmenazanLasBlancas[v][h - 1] = this.hayPieza(false, v, h - 1);
						}
						if (v < 7) {
							if (!this.casillasQueAmenazanLasBlancas[v + 1][h - 1]
									&& this.hayPieza(false, v + 1, h - 1)) {
								casillasQueAmenazanLasBlancas[v + 1][h - 1] = this.hayPieza(false, v + 1, h - 1);
							}
						}
					}
					if (v > 0) {
						if (!this.casillasQueAmenazanLasBlancas[v - 1][h] && this.hayPieza(false, v - 1, h)) {
							casillasQueAmenazanLasBlancas[v - 1][h] = this.hayPieza(false, v - 1, h);
						}
					}
					if (v < 7) {
						if (!this.casillasQueAmenazanLasBlancas[v + 1][h] && this.hayPieza(false, v + 1, h)) {
							casillasQueAmenazanLasBlancas[v + 1][h] = this.hayPieza(false, v + 1, h);
						}
					}
					if (h < 7) {
						if (v > 0) {
							if (!this.casillasQueAmenazanLasBlancas[v - 1][h + 1]
									&& this.hayPieza(false, v - 1, h + 1)) {
								casillasQueAmenazanLasBlancas[v - 1][h + 1] = this.hayPieza(false, v - 1, h + 1);
							}
						}
						if (!this.casillasQueAmenazanLasBlancas[v][h + 1] && this.hayPieza(false, v, h + 1)) {
							casillasQueAmenazanLasBlancas[v][h + 1] = this.hayPieza(false, v, h + 1);
						}
						if (v < 7) {
							if (!this.casillasQueAmenazanLasBlancas[v + 1][h + 1]
									&& this.hayPieza(false, v + 1, h + 1)) {
								casillasQueAmenazanLasBlancas[v + 1][h + 1] = this.hayPieza(false, v + 1, h + 1);
							}
						}
					}

					if (h > 0) {
						if (v > 0) {
							casillasDeJaque = casillasDeJaque && this.casillasQueAmenazanLasBlancas[v - 1][h - 1];
						}
						casillasDeJaque = casillasDeJaque && this.casillasQueAmenazanLasBlancas[v][h - 1];
						if (v < 7) {
							casillasDeJaque = casillasDeJaque && this.casillasQueAmenazanLasBlancas[v + 1][h - 1];
						}
					}
					if (v > 0) {
						casillasDeJaque = casillasDeJaque && this.casillasQueAmenazanLasBlancas[v - 1][h];
					}
					if (v < 7) {
						casillasDeJaque = casillasDeJaque && this.casillasQueAmenazanLasBlancas[v + 1][h];
					}
					if (h < 7) {
						if (v > 0) {
							casillasDeJaque = casillasDeJaque && this.casillasQueAmenazanLasBlancas[v - 1][h + 1];
						}
						casillasDeJaque = casillasDeJaque && this.casillasQueAmenazanLasBlancas[v][h + 1];
						if (v < 7) {
							casillasDeJaque = casillasDeJaque && this.casillasQueAmenazanLasBlancas[v + 1][h + 1];
						}
					}
					if (this.estaEnJaque(esBlanco) && casillasDeJaque) {
						return true;
					}
					casillasDeJaque = true;
				}
			}
		}
		return false;
	}

	public void setNombreJugador(String text) {
		if (nombreBlancas == null) {
			nombreBlancas = text;
			this.ui.ponerNombreBlancas(nombreBlancas);
		} else {
			nombreNegras = text;
			this.ui.ponerNombreNegras(nombreNegras);
		}
	}

	public String getNombreBlancas() {
		return nombreBlancas;
	}

	public String getNombreNegras() {
		return nombreNegras;
	}
}
