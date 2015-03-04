package com.toposhort.algoritmos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import com.toposhort.estructuras.Coordenada;
import com.toposhort.estructuras.MapaTopografico;
import com.toposhort.estructuras.Nodo;

public class Dijkstra {
	
	private Coordenada comienzo = new Coordenada(0,0);
	private Coordenada termino = new Coordenada(0,0);
	
	private NodoDijkstra[][] nodos;
	private ArrayList<NodoDijkstra> nodosUsados = new ArrayList<NodoDijkstra>();
	
	public void setComienzo(Coordenada c) {
		this.comienzo = c;
	}
	
	public void setTermino(Coordenada c) {
		this.termino = c;
	}
	
	//algoritmo de Dijstra
	//La primera llamada crea una matriz auxiliar para la resolucion. Llamadas posteriores
	//reaprovechan esa matriz asumiendo que mapa no ha cambiado. Si se cambia, utilizar un
	//nuevo Dijkstra
	public List<Nodo> caminoMinimo(MapaTopografico mapa, ComparadorNodoBase comparador) {
		//miramos que las coordenadas estén en el mapa
		Coordenada iI = new Coordenada(0,0);
		Coordenada sD = new Coordenada(mapa.getAnchura()-1,mapa.getAltura()-1);
		assert !comienzo.equals(termino) && comienzo.entre(iI,sD) && termino.entre(iI, sD) : "Coordenadas fuera de límites";
		//cola de prioridad
		PriorityQueue<NodoDijkstra> cola = new PriorityQueue<NodoDijkstra>(10,this.new ComparadorNodoDijkstra());
		

		//creamos e inicializamos el primer nodo de la matriz de nodos (grafo) para dijkstra
		if (nodos == null)
			nodos = this.generaMapa(mapa.getAnchura(), mapa.getAltura(), mapa);
		else
			Dijkstra.limpiaNodos(nodosUsados);
		
			
		nodos[comienzo.x][comienzo.y].marca = true;
		nodos[comienzo.x][comienzo.y].coste = 0f;
		cola.add(nodos[comienzo.x][comienzo.y]);
		nodosUsados.add(nodos[comienzo.x][comienzo.y]);
		
		//algoritmo de Dijstra: Cogemos el nodo más prioritario y actualizamos el
		//valor de los adyacentes.
		NodoDijkstra actual = null;
		while (!cola.isEmpty()) {
			actual = cola.poll();
			if (actual.posicion.equals(termino)) {
				ArrayList<Nodo> lista = new ArrayList<Nodo>();
				while (actual != null) {
					lista.add(actual.nodoAsociado);
					actual = actual.anterior;
				}
				return lista;
			}
	
			actualizaValor(actual,actual.abajo,cola,comparador,nodosUsados);
			actualizaValor(actual,actual.arriba,cola,comparador,nodosUsados);
			actualizaValor(actual,actual.derecha,cola,comparador,nodosUsados);
			actualizaValor(actual,actual.izquierda,cola,comparador,nodosUsados);
			
		}
		System.out.println("No se ha encontrado camino mínimo entre " + comienzo.toString() + " y " + termino.toString());
		return new ArrayList<Nodo>();
	}
	
	private static void limpiaNodos(ArrayList<NodoDijkstra> nodos) {
		for (NodoDijkstra n: nodos) {
			n.coste = Float.POSITIVE_INFINITY;
			n.marca = false;
			n.anterior = null;
		}
		nodos.clear();
	}

	//actualizamos el valor de un nodo, y si no está marcado, lo añadimos
	private void actualizaValor(NodoDijkstra actual, NodoDijkstra siguiente, PriorityQueue<NodoDijkstra> cola, ComparadorNodoBase c, ArrayList<NodoDijkstra> nodosUsados) {
		if (siguiente != null) {
			Float costeArista = c.costeEntreNodos(actual.nodoAsociado, siguiente.nodoAsociado);
			if (siguiente.coste > actual.coste+costeArista) {
				if (!siguiente.marca) {
					cola.add(siguiente);
					nodosUsados.add(siguiente);
					siguiente.marca = true;
				}
				siguiente.anterior = actual;
				siguiente.coste = actual.coste+costeArista;
			}
		}
	}
	

	
	//generamos la matriz de nodos para dijkstra
	public NodoDijkstra[][] generaMapa(int anchura, int altura, MapaTopografico m) {
		NodoDijkstra[][] mapa = new NodoDijkstra[anchura][altura];
		for (int i = 0; i < anchura; i++)
			for (int j = 0; j < altura; j++) 
				mapa[i][j] = this.new NodoDijkstra(new Coordenada(i,j), Float.POSITIVE_INFINITY,m.getNodo(i, j));
		
		for (int i = 0; i < anchura; i++) 
			for (int j = 0; j < altura; j++) {
				if (i != 0)
					mapa[i][j].abajo = mapa[i-1][j];
				if (i != anchura-1)
					mapa[i][j].arriba = mapa[i+1][j];
				if (j != 0)
					mapa[i][j].izquierda = mapa[i][j-1];
				if (j != altura-1)
					mapa[i][j].derecha = mapa[i][j+1];
				mapa[i][j].posicion = m.getNodo(i, j).posicion;
			}
		
		return mapa;
	}
	
	private class NodoDijkstra {
		public Coordenada posicion;
		public float coste;
		public NodoDijkstra anterior, arriba, abajo, izquierda, derecha;
		public Nodo nodoAsociado;
		public boolean marca = false;
		
		public NodoDijkstra(Coordenada posicion, float coste, Nodo nodoAsociado) {
			this.posicion = posicion;
			this.coste = coste;
			this.nodoAsociado = nodoAsociado;
		}
	}
	
	private class ComparadorNodoDijkstra implements Comparator<NodoDijkstra> {
		@Override
		public int compare(NodoDijkstra arg0, NodoDijkstra arg1) {
			Float a = arg0.coste, b = arg1.coste;
			return a.compareTo(b);
		}
	}
}

