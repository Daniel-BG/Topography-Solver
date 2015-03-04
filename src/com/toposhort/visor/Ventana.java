package com.toposhort.visor;

import javax.swing.JFrame;



public class Ventana extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6593325672951680333L;

	public Ventana(VisorMapa m) {
		this.setSize(916,638);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(m);
		this.setVisible(true);
		this.setTitle("Visor del mapa");
	}
}
