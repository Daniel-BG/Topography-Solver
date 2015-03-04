package com.toposhort.visor;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

public class ModificadorImagen {

	public static void copiarImagen(BufferedImage origen, BufferedImage destino) {
		assert origen.getWidth() == destino.getWidth() && origen.getHeight() == destino.getHeight();
		int anchura = origen.getWidth();
		int altura = destino.getHeight();
		for (int i = 0; i < anchura; i++) 
			for (int j = 0; j < altura; j++)
				destino.setRGB(i, j, origen.getRGB(i, j));
	}
	
	public static void superponerCirculo(int diametro, BufferedImage fondo, int x, int y, int color) {
		Ellipse2D.Float circle = new Ellipse2D.Float(0,0, diametro, diametro);
		BufferedImage overlay = new BufferedImage(diametro,diametro, BufferedImage.TYPE_INT_ARGB);
		Graphics2D gOver = (Graphics2D) overlay.getGraphics();
		
		((Graphics2D) gOver).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gOver.setBackground(new Color(255,255,255,0));
		gOver.clearRect(0, 0, diametro,diametro);
		gOver.setColor(new Color(color));
		gOver.fill(circle);
		
		
		//((Graphics2D) fondo.getGraphics()).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		fondo.getGraphics().drawImage(overlay, x-diametro/2, y-diametro/2, diametro,diametro, null);
		gOver.dispose();
	}
	
	public static void superponerCruz(BufferedImage fondo, int x, int y, int color) {	
		try{
			fondo.setRGB(x, y, color);
			fondo.setRGB(x-1, y, ModificadorImagen.mediaDeColor(color, fondo.getRGB(x-1, y)));
			fondo.setRGB(x+1, y, ModificadorImagen.mediaDeColor(color, fondo.getRGB(x+1, y)));
			fondo.setRGB(x, y+1, ModificadorImagen.mediaDeColor(color, fondo.getRGB(x, y+1)));
			fondo.setRGB(x, y-1, ModificadorImagen.mediaDeColor(color, fondo.getRGB(x, y-1)));
		} catch (ArrayIndexOutOfBoundsException e) {}
	}
	
	public static int mediaDeColor(int color1, int color2) {
		int alpha = ((color1 >>> 24) + (color2 >>> 24))/2;
		int red = (((color1 >> 16) & 0xff) + ((color2 >> 16) & 0xff))/2;
		int green = (((color1 >> 8) & 0xff) + ((color2 >> 8) & 0xff))/2;
		int blue = ((color1 & 0xff) + (color2 & 0xff))/2;
		return (alpha << 24) + (red << 16) + (green << 8) + (blue);
	}
}
