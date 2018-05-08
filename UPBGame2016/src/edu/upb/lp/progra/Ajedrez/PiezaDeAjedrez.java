package edu.upb.lp.progra.Ajedrez;

public class PiezaDeAjedrez {
	private boolean seHaMovidoEnroque;

	public String getNombreDeImagen() {
		return "colors_blue";
	}

	public boolean getEsBlanco() {
		return false;
	}
	
	public boolean getSeHaMovidoEnroque(){
		 return seHaMovidoEnroque;
	 }

	public boolean mover(int v, int h, int vertical, int horizontal) {
		return false;
	}
}
