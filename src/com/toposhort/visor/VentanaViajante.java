package com.toposhort.visor;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.toposhort.algoritmos.IteradorViajante;
import com.toposhort.controlador.Controlador;
import com.toposhort.estructuras.Coordenada;
import com.toposhort.estructuras.MapaTopograficoObserver;
import com.toposhort.estructuras.Nodo;

public class VentanaViajante extends JFrame implements MapaTopograficoObserver{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2831807641963856259L;
	
	private JScrollPane jsp = new JScrollPane();
	private JLabel label = new JLabel("Sin procesar");

	public VentanaViajante(final Controlador c) {
		this.setTitle("Algoritmo del viajante");
		this.setSize(300,288);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(1,1));
		
		this.setJMenuBar(new JMenuBar(){

			/**
			 * 
			 */
			private static final long serialVersionUID = -2764068728877392939L;
			{
				this.add(label);
			}

			
		});

		this.add(jsp);
		
		
		//pack();
		setLocation(916,350);
		//setLocationRelativeTo(null);
		setVisible(true);
	}

	@Override
	public void CartografiaActualizada(Nodo[][] mapa, int anchura, int altura) {}
	@Override
	public void CaminoCreado(List<Nodo> camino) {}
	@Override
	public void WaypointCreado(Coordenada c, String datos) {}
	@Override
	public void WaypointBorrado(Coordenada c) {}
	@Override
	public void CaminosBorrados() {}
	@Override
	public void WayPointsBorrados() {}
	@Override
	
	
	public void MatrizAdyacenciaCreada(Integer[][] matriz) {
		if (matriz == null || matriz.length <= 1)
			return;
	
		String[] columnas = new String[matriz.length];
		for (int i = 0; i < matriz.length; i++)
			columnas[i] = Integer.toString(i);
		this.remove(jsp);
		jsp = new JScrollPane(new JTable(matriz,columnas));
		this.add(jsp);
		label.setText("Sin procesar");
		this.revalidate();
		this.repaint();
	}

	@Override
	public void ViajanteResuelto(List<List<Nodo>> lista, IteradorViajante iv) {
		if (iv.mejorSolucion == Integer.MAX_VALUE) {
			label.setText("Sin solución");
			return;
		}
			
		String text = "";
		for (int i: iv.mejorLista)
			text += i + "/";
		text += "=/" + iv.mejorSolucion;
		label.setText(text);
	}

	@Override
	public void MultiplesCaminosCreados(List<List<Nodo>> lista) {}

	@Override
	public void TerrenoActualizado(Nodo[][] mapaTopografico, Coordenada coordenada, Coordenada coordenada2) {}

	@Override
	public void AlturaSeleccionada(float altura) {}

	
	
}
