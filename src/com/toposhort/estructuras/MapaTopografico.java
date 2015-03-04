package com.toposhort.estructuras;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;



import com.toposhort.algoritmos.ComparadorNodoBase;
import com.toposhort.algoritmos.Dijkstra;
import com.toposhort.algoritmos.IteradorViajante;
import com.toposhort.algoritmos.Viajante;
import com.toposhort.funciones.Observable;
import com.toposhort.terreno.DatosGenerador;
import com.toposhort.terreno.GeneradorTerreno;
import com.toposhort.terreno.Pincel;


public class MapaTopografico extends Observable<MapaTopograficoObserver> {
	private Nodo[][] mapa;
	private int anchura;
	private int altura;
	Random aleatorio;
	
	private HashMap<Coordenada,String> wayPoints = new HashMap<Coordenada,String>();
	private List<List<Nodo>> caminos = new ArrayList<List<Nodo>>();
	
	private Integer[][] matrizAdyacencia;

	
	//genera el mapa en sí, siguiendo los ajustes dados por "dg"
	public void generaMapa(DatosGenerador dg) {
		wayPoints.clear();
		caminos.clear();
		
		Float [][] mapaDeAlturas = GeneradorTerreno.generadorDiamante(dg);
		this.anchura = dg.getAnchuraMapa();
		this.altura = dg.getAlturaMapa();
		mapa = new Nodo[anchura][altura];
		
		for (int i = 0; i < anchura; i++)
			for (int j = 0; j < altura; j++)
				mapa[i][j] = new Nodo(mapaDeAlturas[i][j], new Coordenada(i,j));
		
		

		this.onCaminosBorrados();
		this.onTodosLosWayPointBorrados();
		this.onActualizacionMapa();
	}
	
	//modifica el terreno sobre la coordenada centro aplicando el pincel
	public void modificarTerreno(Pincel pincel, Coordenada centro) {
		assert pincel.radio > 0 : "No valen radios negativos o cero";
		Coordenada comienzo = centro.derivaX(-pincel.radio).derivaY(-pincel.radio);
		Coordenada termino = centro.derivaX(pincel.radio).derivaY(pincel.radio);
		
		for (int i = comienzo.x; i <= termino.x; i++)
			for (int j = comienzo.y; j <= termino.y; j++) {
				if (i < 0 || j < 0 || i >= anchura || j >= altura)
					continue;
				mapa[i][j].altura = pincel.nuevoValor(new Coordenada(i-centro.x,j-centro.y), mapa[i][j].altura);
			}
		
		this.onActualizacionTerreno(new Coordenada(Math.max(0, comienzo.x), Math.max(0, comienzo.y)),
									new Coordenada(Math.min(anchura-1, termino.x), Math.min(altura-1, termino.y)));
	}
	
	//genera los caminos entre waypoints utilizando el comparador enviado para calcular costes
	//si algun coste es infinito, no se considerará el camino correspondiente
	public void generarCaminos(ComparadorNodoBase comparador) {
		Dijkstra landRover = new Dijkstra();
		List<Coordenada> wayPoints = this.getWayPoints();
		Coordenada[] coordenadas = wayPoints.toArray(new Coordenada[wayPoints.size()]);
		System.out.println("Generando caminos...");
		List<List<Nodo>> lista = new ArrayList<List<Nodo>>();
		for (int i = 0; i < coordenadas.length; i++) {
			Coordenada a = coordenadas[i];
			for (int j = i+1; j < coordenadas.length; j++) {
				Coordenada b = coordenadas[j];
				if (!a.equals(b)) {
					landRover.setComienzo(a);
					landRover.setTermino(b);
					List<Nodo> camino = landRover.caminoMinimo(this, comparador);
					if (camino == null || camino.size() == 0)
						continue;
					lista.add(camino);
					System.out.println("Camino creado entre: " + a.toString() + " y " + b.toString());
				}
			}
		}
		this.addMultiplesCaminos(lista);
		System.out.println("Caminos generados");
	}

	//genera la matriz de adyacencia de los waypoints, utilizando para calcular costes el comparadorNodo
	public void generaMatrizDeAdyacencia(ComparadorNodoBase comparadorNodo) {
		List<Coordenada> coordenadas = this.getWayPoints();
		if (coordenadas == null || coordenadas.isEmpty())
			return;
		
		int tamano = coordenadas.size();
		
		Integer[][] matrizAdyacencia = new Integer[tamano][tamano];
		for (int i = 0; i < tamano; i++)
			for (int j = 0; j < tamano; j++) 
				if (i != j)
					matrizAdyacencia[i][j] = -1;
		
				else
					matrizAdyacencia[i][j] = 0;
		
		
		
		ComparadorCoordenadas comparadorCoord = new ComparadorCoordenadas();
		
		
		Collections.sort(coordenadas, comparadorCoord);
		
		for (List<Nodo> l: caminos) {
			if (l == null || l.size() == 0)
				continue;
			Nodo inicio = l.get(0);
			Nodo fin = l.get(l.size()-1);
			
			int a = Collections.binarySearch(coordenadas, inicio.posicion, comparadorCoord);
			int b = Collections.binarySearch(coordenadas, fin.posicion, comparadorCoord);
			if (a < 0 || b < 0)
				continue;
			
			float coste = getCosteDeCamino(l, comparadorNodo);
			if (coste == Float.POSITIVE_INFINITY)
				coste = -1;
			matrizAdyacencia[a][b] = (int) coste;
			matrizAdyacencia[b][a] = (int) coste;
		}
		
		this.matrizAdyacencia = matrizAdyacencia;
		this.onMatrizAdyacenciaCreada(this.matrizAdyacencia);
	}
	
	//resuelve el algoritmo del viajante sobre la matriz de adyacencia, si existe
	public void resuelveViajante() {
		if (this.matrizAdyacencia == null)
			return;
		List<List<Nodo>> caminoViajante = new ArrayList<List<Nodo>>();
		Viajante viajante = new Viajante();
		IteradorViajante it = viajante.resuelveViajanteFuerzaBruta(matrizAdyacencia);
		System.out.println(viajante.getDatosAlgoritmo());
		
		List<Coordenada> wp = this.getWayPoints();
		Collections.sort(wp, new ComparadorCoordenadas());
		
		for (int i = 0; i < it.tamano; i++) {
			Coordenada inicio = wp.get(it.mejorLista[i]);
			Coordenada fin = wp.get(it.mejorLista[(i+1)%it.tamano]);
			
			for (List<Nodo> l: this.caminos) {
				Coordenada inicioC = l.get(0).posicion;
				Coordenada finC = l.get(l.size()-1).posicion;
				if ((inicioC.equals(inicio) && finC.equals(fin)) || (inicioC.equals(fin) && finC.equals(inicio))) 
					caminoViajante.add(l);
			}
				
		}
		
		this.onViajanteResuelto(caminoViajante, it);
	}
	



	public Nodo getNodo(int i, int j) {
		if (i < 0 || j < 0 || i >= this.anchura || j >= this.altura)
			return null;
		return mapa[i][j];
	}
	
	public Nodo getNodo(Coordenada posicion) {
		return getNodo(posicion.x,posicion.y);
	}
	
	
	public static List<Coordenada> getCoordenadasDeCamino(List<Nodo> lista) {
		if (lista == null)
			return null;
		Iterator<Nodo> it = lista.iterator();
		ArrayList<Coordenada> camino = new ArrayList<Coordenada>();
		
		while(it.hasNext())
			camino.add(it.next().posicion);
		
		return camino;
	}
	
	public static Float getCosteDeCamino(List<Nodo> lista, ComparadorNodoBase c) {
		if (lista == null)
			return 0f;
		Iterator<Nodo> it = lista.iterator();
		if (!it.hasNext())
			return 0f;
		
		float coste = 0f;
		Nodo actual = it.next();
		while (it.hasNext()) {
			Nodo siguiente = it.next();
			coste += c.costeEntreNodos(actual, siguiente);
			actual = siguiente;
		}
		return coste;
	}

	
	public int getAltura() {
		return this.altura;
	}
	
	public int getAnchura() {
		return this.anchura;
	}
	
	public List<Coordenada> getWayPoints() {
		Set<Coordenada> conjuntoCoordenadas = this.wayPoints.keySet();
		Coordenada[] listaWayPoints = conjuntoCoordenadas.toArray(new Coordenada[wayPoints.size()]);
		ArrayList<Coordenada> coordenadas = new ArrayList<Coordenada>();
		for(Coordenada c: listaWayPoints)
			coordenadas.add(c);
		
		return coordenadas;
	}
	
	
	public void addWayPoint(Coordenada c, String datos) {
		this.wayPoints.put(c,datos);
		this.onWayPointCreado(c, datos);
	}
	
	public void borraWayPoint(Coordenada c) {
		this.wayPoints.remove(c);
		this.onWayPointBorrado(c);
	}
	
	public void borraTodosLosWayPoints() {
		this.wayPoints.clear();
		this.caminos.clear();
		this.onTodosLosWayPointBorrados();
		this.onCaminosBorrados();
	}
	
	public void addCamino(List<Nodo> camino) {
		if (camino != null && !camino.isEmpty()) {
			this.caminos.add(camino);
			this.onCaminoCreado(camino);
		}
	}
	
	public void addMultiplesCaminos(List<List<Nodo>> lista) {
		if (lista != null && !lista.isEmpty())
			for (List<Nodo> l : lista) {
				this.caminos.add(l);
			}

		this.onMultiplesCaminosCreados(lista);
	}
	



	public void borraCaminos() {
		for (List<Nodo> ar: this.caminos)
			ar.clear();
		this.caminos.clear();
		this.onCaminosBorrados();
	}
	
	public void peticionActualizacion() {
		this.onActualizacionMapa();
	}

	public void setCursor(Coordenada coord) {
		Nodo n = this.getNodo(coord);
		if (n != null)
			this.onNuevaAlturaSeleccionada(n.altura);
	}

	

	//OBSERVER
	
	private void onActualizacionMapa() {
		for (MapaTopograficoObserver mto: this.observers)
			mto.CartografiaActualizada(mapa, anchura, altura);
	}
	
	private void onWayPointCreado(Coordenada c, String datos) {
		for (MapaTopograficoObserver mto: this.observers)
			mto.WaypointCreado(c, datos);
	}
	
	private void onWayPointBorrado(Coordenada c) {
		for (MapaTopograficoObserver mto: this.observers)
			mto.WaypointBorrado(c);
	}
	
	private void onTodosLosWayPointBorrados() {
		for (MapaTopograficoObserver mto: this.observers)
			mto.WayPointsBorrados();
	}
	
	private void onCaminoCreado(List<Nodo> camino) {
		for (MapaTopograficoObserver mto: this.observers)
			mto.CaminoCreado(camino);
	}
	
	private void onMultiplesCaminosCreados(List<List<Nodo>> lista) {
		for (MapaTopograficoObserver mto: this.observers)
			mto.MultiplesCaminosCreados(lista);
	}
	
	private void onCaminosBorrados() {
		for (MapaTopograficoObserver mto: this.observers)
			mto.CaminosBorrados();
	}
	
	private void onMatrizAdyacenciaCreada(Integer[][] matriz) {
		for (MapaTopograficoObserver mto: this.observers)
			mto.MatrizAdyacenciaCreada(matriz);
	}

	private void onViajanteResuelto(List<List<Nodo>> caminoViajante, IteradorViajante iv) {
		for (MapaTopograficoObserver mto: this.observers)
			mto.ViajanteResuelto(caminoViajante, iv);
	}

	private void onActualizacionTerreno(Coordenada coordenada,Coordenada coordenada2) {
		for (MapaTopograficoObserver mto: this.observers)
			mto.TerrenoActualizado(mapa,coordenada, coordenada2);
	}

	private void onNuevaAlturaSeleccionada(float f) {
		for (MapaTopograficoObserver mto: this.observers)
			mto.AlturaSeleccionada(f);
	}

}
