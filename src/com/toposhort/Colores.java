package com.toposhort;


public class Colores {
	//color de alto contraste?
	public static boolean CAC = false;
	
	//Formato de los colores: ARGB
	//0xaarrggbb
	//a=alpha: transparencia. 0 es transparente y ff opaco
	//r=red: 0 nada ff rojo vivo
	//g=green b=blue
	
	//Colores "reales"
	
	private static final int[] COLECCION_C_REAL = {
		0x000000,0x000485,0x0044ba,0x006dde,
		0x0082ee,0x0096ff,0x31acff,0x00cdc1,
		0xf2cf63,0x00bd00,0x9500,0x8300,
		0x3C6E00,0xB79D84,0x8c7869,0x604E40,
		0xCE6266,0xf2cf63,0xe8cca5,0xf19f73,
		0xffb538, 0xbf4099,0xD6D6D6,0xD6D6D6
	};
	
	private static final int COLOR_ERROR_REAL = 0xffff0000;
	private static final int COLOR_WAYPOINT_REAL = 0xffffffff;
	private static final int COLOR_CAMINO_REAL = 0xff0080ff;
	private static final int COLOR_VIAJANTE_REAL = 0xffff8000;
	private static final int COLOR_TEXTO_REAL = 0x0;
	
	//Colores "alto contraste"
	private static final int[] COLECCION_C_CAC = {
		0xffffff,0xf0f0f0,0xe0e0e0,0xd0d0d0,
		0xc8c8c8,0xc0c0c0,0xb8b8b8,0xb0b0b0,
		0x707070,0x606060,0x505050,0x404040,
		0x383838,0x303030,0x282828,0x202020,
		0x1b1b1b,0x181818,0x141414,0x101010,
		0x0b0b0b,0x080808,0x040404,0x000000
	};
	
	private static final int COLOR_ERROR_CAC = 0xffff0000;
	private static final int COLOR_WAYPOINT_CAC = 0xffff0000;
	private static final int COLOR_CAMINO_CAC = 0xff0000ff;
	private static final int COLOR_VIAJANTE_CAC = 0xff00ff00;	
	private static final int COLOR_TEXTO_CAC = 0xffff00ff;
	
	//Alturas (no varía si es alto contraste o no)
	private static final int[] COLECCION_ALTURAS = {
		Integer.MIN_VALUE, -6400,-3200,-1600,
		-800,-400,-200,-1,
		0,200,400,600,
		800,1200,1700,2200,
		2700,3200,3700,4200,
		5000,6000,8622,Integer.MAX_VALUE
	};


	
	
	
	public static int colorError() {
		return CAC ? COLOR_ERROR_CAC : COLOR_ERROR_REAL;
	}
	
	public static int colorWayPoint() {
		return CAC ? COLOR_WAYPOINT_CAC : COLOR_WAYPOINT_REAL; 
	}
	
	public static int colorCamino() {
		return CAC ? COLOR_CAMINO_CAC : COLOR_CAMINO_REAL;
	}
	
	public static int colorViajante() {
		return CAC ? COLOR_VIAJANTE_CAC : COLOR_VIAJANTE_REAL;
	}
	
	public static int[] coleccionColores() {
		return CAC ? COLECCION_C_CAC : COLECCION_C_REAL;
	}
	
	public static int[] coleccionAlturas() {
		return COLECCION_ALTURAS;
	}

	public static int colorTexto() {
		return CAC ? COLOR_TEXTO_CAC : COLOR_TEXTO_REAL;
	}
}
