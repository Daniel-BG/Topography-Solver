package com.toposhort.visor;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import com.toposhort.algoritmos.ComparadorNodoBase;
import com.toposhort.controlador.Controlador;
import com.toposhort.funciones.Matematica;
import com.toposhort.terreno.DatosGenerador;
import com.toposhort.terreno.Pincel;

public class VentanaOpciones extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1496476698174452448L;

	public VentanaOpciones(final Controlador c) {
		this.setTitle("Ajustes");
		this.setSize(300,350);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(17,2));
		
		JLabel labelDatos = new JLabel("Panel de control:");
		labelDatos.setForeground(Color.ORANGE);
		this.add(labelDatos);
		final JButton jBotonColor = new JButton("Invertir Color");
		jBotonColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				c.invierteColor();
			}
		});
		this.add(jBotonColor);
		
		this.add(new JLabel("Tamaño mapa (x):"));
		final JTextField jtextTamanox = new JTextField();
		jtextTamanox.setText(""+DatosGenerador.ANCHURA_DEFECTO);
		jtextTamanox.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent arg0) {
				int i = Matematica.convertirAEntero(jtextTamanox.getText());
				if (i > 0)
					c.setAnchuraMapa(i);
			}
		});
		this.add(jtextTamanox);
		
		this.add(new JLabel("Tamaño mapa (y):"));
		final JTextField jtextTamanoy = new JTextField();
		jtextTamanoy.setText(""+DatosGenerador.ALTURA_DEFECTO);
		jtextTamanoy.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent arg0) {
				int i = Matematica.convertirAEntero(jtextTamanoy.getText());
				if (i > 0)
					c.setAlturaMapa(i);
			}
		});
		this.add(jtextTamanoy);
		
		this.add(new JLabel("Altura máxima:"));
		final JTextField jtextMaxAltura = new JTextField();
		jtextMaxAltura.setText(""+DatosGenerador.ALTURA_MAXIMA_DEFECTO);
		jtextMaxAltura.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent arg0) {
				int i = Matematica.convertirAEntero(jtextMaxAltura.getText());
				c.setAlturaMax(i);
			}
		});
		this.add(jtextMaxAltura);
		
		this.add(new JLabel("Altura mínima:"));
		final JTextField jtextMinAltura = new JTextField();
		jtextMinAltura.setText(""+DatosGenerador.ALTURA_MINIMA_DEFECTO);
		jtextMinAltura.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent arg0) {
				int i = Matematica.convertirAEntero(jtextMinAltura.getText());
				c.setAlturaMin(i);
			}
		});
		this.add(jtextMinAltura);
		
		this.add(new JLabel("Desviación:"));
		final JTextField jtextDesviacion = new JTextField();
		jtextDesviacion.setText(""+DatosGenerador.DESPLAZAMIENTO_BASE_DEFECTO);
		jtextDesviacion.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent arg0) {
				int i = Matematica.convertirAEntero(jtextDesviacion.getText());
				if (i >= 0)
					c.setDesviacion(i);
			}
		});
		this.add(jtextDesviacion);
		
		this.add(new JLabel("Semilla:"));
		final JTextField jtextSemilla = new JTextField();
		jtextSemilla.setText(""+DatosGenerador.SEMILLA_DEFECTO);
		jtextSemilla.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent arg0) {
				long i = Matematica.convertirALong(jtextSemilla.getText());
				c.setSemilla(i);
			}
		});
		this.add(jtextSemilla);
		
		this.add(new JLabel("Limite pendiente:"));
		final JTextField jtextLimpend = new JTextField();
		jtextLimpend.setText(""+ComparadorNodoBase.LIMITE_PENDIENTE_DEFECTO);
		jtextLimpend.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent arg0) {
				Float i = Matematica.convertirAComaFlotante(jtextLimpend.getText());
				if (i >= 0 && i <= 1)
					c.setPendienteMaxima(i);
			}
		});
		this.add(jtextLimpend);
		
		this.add(new JLabel("Limite altura:"));
		final JTextField jtextLimalt = new JTextField();
		jtextLimalt.setText(""+ComparadorNodoBase.LIMITE_ALTURA_DEFECTO);
		jtextLimalt.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent arg0) {
				int i = Matematica.convertirAEntero(jtextLimalt.getText());
				if (i > 0)
					c.setLimiteAltura(i);
			}
		});
		this.add(jtextLimalt);
		
		this.add(new JLabel("Distancia entre puntos:"));
		final JTextField jtextDistanciaP = new JTextField();
		jtextDistanciaP.setText(""+ComparadorNodoBase.DISTANCIA_ENTRE_PUNTOS_DEFECTO);
		jtextDistanciaP.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent arg0) {
				int i = Matematica.convertirAEntero(jtextDistanciaP.getText());
				if (i > 0)
					c.setDistanciaEntrePuntos(i);
			}
		});
		this.add(jtextDistanciaP);
		
		this.add(new JLabel("Multiplicador barco"));
		final JTextField jtextMultBarco = new JTextField();
		jtextMultBarco.setText(""+ComparadorNodoBase.PENALIZACION_BARCO_DEFECTO);
		jtextMultBarco.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent arg0) {
				float i = Matematica.convertirAComaFlotante(jtextMultBarco.getText());
				if (i > 0)
					c.setPenalizacionBarco(i);
			}
		});
		this.add(jtextMultBarco);
		
		final JCheckBox jCBbarco = new JCheckBox("Permitir barco");
		jCBbarco.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				c.setBarcoDisponieble(jCBbarco.isSelected());
			}
		});
		this.add(jCBbarco);
		
		final JCheckBox jCSemillaAleatoria = new JCheckBox("Semilla aleatoria");
		jCSemillaAleatoria.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				c.setSemillaAleatoria(jCSemillaAleatoria.isSelected());
			}
		});
		this.add(jCSemillaAleatoria);
		
		final JButton jBotonCamino = new JButton("Generar Caminos");
		jBotonCamino.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				c.generarCaminos();
			}
		});
		this.add(jBotonCamino);
		
		final JButton jBotonMapa = new JButton("Generar Mapa");
		jBotonMapa.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				c.regenerar();
			}
		});
		this.add(jBotonMapa);
		
		final JButton jBotonQuitarCamino = new JButton("Borrar Caminos");
		jBotonQuitarCamino.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				c.borraCaminos();
			}
		});
		this.add(jBotonQuitarCamino);
		
		final JButton jBotonQuitarWayPoints = new JButton("Borrar WayPoints");
		jBotonQuitarWayPoints.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				c.borraWayPoints();
			}
		});
		this.add(jBotonQuitarWayPoints);
		
		this.add(new JLabel("Radio pincel:"));
		final JTextField jtextRadio = new JTextField();
		jtextRadio.setText(""+Pincel.RADIO_DEFECTO);
		jtextRadio.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent arg0) {
				int i = Matematica.convertirAEntero(jtextRadio.getText());
				if (i > 0)
					c.setRadio(i);
			}
		});
		this.add(jtextRadio);
		
		this.add(new JLabel("Variacion pincel:"));
		final JTextField jtextVar = new JTextField();
		jtextVar.setText(""+Pincel.VARIACION_DEFECTO);
		jtextVar.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent arg0) {
				Float f = Matematica.convertirAComaFlotante(jtextVar.getText());
				c.setVariacion(f);
			}
		});
		this.add(jtextVar);
		
		final JButton jBotonMatAdy = new JButton("Matriz Adyacencia");
		jBotonMatAdy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				c.generarMatrizDeAdyacencia();
			}
		});
		this.add(jBotonMatAdy);
		
		final JButton jBotonResolverViajante = new JButton("Resolver Viajante");
		jBotonResolverViajante.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				c.calcularViajante();
			}
		});
		this.add(jBotonResolverViajante);
		
		//pack();
		setLocation(916,0);
		//setLocationRelativeTo(null);
		setVisible(true);
	}
}
