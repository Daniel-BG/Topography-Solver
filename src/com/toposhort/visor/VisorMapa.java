package com.toposhort.visor;


import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.List;

import javax.swing.JPanel;

import com.toposhort.Colores;
import com.toposhort.algoritmos.IteradorViajante;
import com.toposhort.controlador.Controlador;
import com.toposhort.estructuras.Coordenada;
import com.toposhort.estructuras.MapaTopografico;
import com.toposhort.estructuras.MapaTopograficoObserver;
import com.toposhort.estructuras.Nodo;
import com.toposhort.funciones.Matematica;


public class VisorMapa extends JPanel implements MapaTopograficoObserver{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2134385901159738249L;
	
	{		
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				Coordenada coord = Matematica.getCoordenadaDesdeEventoDeRaton(arg0, anchura, altura, getWidth(), getHeight());

				if (coord.x >= anchura || coord.y >= altura || coord.x < 0 || coord.y < 0)
					return;

				if (arg0.getButton() == MouseEvent.BUTTON1) 
					if (arg0.isShiftDown())
						controlador.modificaTerreno(coord, false);
					else if (arg0.isControlDown())
						controlador.setCursor(coord);
					else
						controlador.setWayPoint(coord);
				
				if (arg0.getButton() == MouseEvent.BUTTON3) 
					if (arg0.isShiftDown())
						controlador.modificaTerreno(coord, true);
					else
						controlador.removeWayPoint(coord);
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {}
			@Override
			public void mouseReleased(MouseEvent arg0) {}
		});
	}
	
	private Controlador controlador;
	float ultimaAltura = 0;
	
	public VisorMapa(Controlador c) {
		this.controlador = c;
	}
	
	BufferedImage mapa;
	HashSet<Coordenada> wayPoints = new HashSet<Coordenada>();
	HashSet<Coordenada> pathPoints = new HashSet<Coordenada>();
	HashSet<Coordenada> viajantePoints = new HashSet<Coordenada>();
	
	private int altura;
	private int anchura;
	
	public void repintarMapa(Nodo[][] mapaTopografico, int anchura, int altura) {
		mapa = new BufferedImage(anchura,altura, BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < anchura; i++)
			for (int j = 0; j < altura; j++) 
				mapa.setRGB(i, j, mapaTopografico[i][j].getColor());
		cambio = true;
		repaint();
	}
	
	public void repintarSeccion(Nodo[][] mapaTopografico, Coordenada inicio, Coordenada termino) {
		for (int i = inicio.x; i <= termino.x; i++)
			for (int j = inicio.y; j <= termino.y; j++) 
				mapa.setRGB(i, j, mapaTopografico[i][j].getColor());
	
		repaint();
	}
	
	private boolean cambio = false;
	
	BufferedImage capa;
	
	@Override
	public void paint(Graphics g) {
		if (mapa == null)
			return;
		if (capa == null || cambio) {
			cambio = false;
			capa = new BufferedImage(mapa.getWidth(),mapa.getHeight(),BufferedImage.TYPE_INT_ARGB);
			
			for (Coordenada c: pathPoints)
				ModificadorImagen.superponerCruz(capa, c.x, c.y, Colores.colorCamino());
			
			for (Coordenada c: viajantePoints)
				ModificadorImagen.superponerCruz(capa, c.x, c.y, Colores.colorViajante());
			
			for (Coordenada c: wayPoints)
				ModificadorImagen.superponerCirculo(10, capa, c.x, c.y, Colores.colorWayPoint());
		}
		
		g.drawImage(mapa, 0, 0, getWidth(), getHeight(), null);
		g.drawImage(capa, 0, 0, getWidth(), getHeight(), null);
	}
	
	

	

	@Override
	public void CartografiaActualizada(Nodo[][] mapaTopografico, int anchura, int altura) {
		this.altura = altura;
		this.anchura = anchura;
		this.repintarMapa(mapaTopografico, anchura, altura);		
	}
	
	@Override
	public void TerrenoActualizado(Nodo[][] mapaTopografico, Coordenada coordenada, Coordenada coordenada2) {
		this.repintarSeccion(mapaTopografico, coordenada, coordenada2);
	}

	@Override
	public void CaminoCreado(List<Nodo> camino) {
		pathPoints.addAll(MapaTopografico.getCoordenadasDeCamino(camino));
		cambio = true;
		this.repaint();
	}

	@Override
	public void MultiplesCaminosCreados(List<List<Nodo>> lista) {
		for (List<Nodo> l : lista)
			pathPoints.addAll(MapaTopografico.getCoordenadasDeCamino(l));
		cambio = true;
		this.repaint();
	}
	
	@Override
	public void WaypointCreado(Coordenada c, String datos) {
		wayPoints.add(c);
		cambio = true;
		this.repaint();
	}

	@Override
	public void WaypointBorrado(Coordenada c) {
		wayPoints.remove(c);
		cambio = true;
		this.repaint();
	}

	@Override
	public void CaminosBorrados() {
		pathPoints.clear();
		viajantePoints.clear();
		cambio = true;
		this.repaint();
	}

	@Override
	public void WayPointsBorrados() {
		cambio = true;
		wayPoints.clear();
	}

	@Override
	public void MatrizAdyacenciaCreada(Integer[][] matriz) {}

	@Override
	public void ViajanteResuelto(List<List<Nodo>> lista, IteradorViajante iv) {
		this.viajantePoints.clear();
		for (List<Nodo> l: lista)
			this.viajantePoints.addAll(MapaTopografico.getCoordenadasDeCamino(l));
		cambio = true;
		this.repaint();
	}

	@Override
	public void AlturaSeleccionada(float altura) {
		System.out.println("La altura es: " + altura);
	}




	
}
