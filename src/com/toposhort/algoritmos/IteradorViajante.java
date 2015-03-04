package com.toposhort.algoritmos;

import java.util.ArrayList;
import java.util.Collections;

//clase que lleva los datos del algoritmo del viajante
public class IteradorViajante {
	public int[] mejorLista;
	public int[] listaActual;
	public boolean[] marcas;
	public int iteracion;
	public int tamano;
	public int mejorSolucion = Integer.MAX_VALUE;
	public int costeAcumulado = 0;
	
	private float[] ayudantePoda;
	
	public IteradorViajante (int tamano) {
		marcas = new boolean[tamano];
		mejorLista = new int[tamano];
		marcas[0] = true;
		this.iteracion = 1;
		this.tamano = tamano;
		this.listaActual = new int[tamano];
	}
	
	//vemos si se puede mejorar la solucion con el coste acumulado hasta el momento
	//asumimos que todos los nodos han sido visitados
	public void mejoraSolucion(Integer[][] matriz) {
		int costeArista = matriz[listaActual[0]][listaActual[tamano-1]];
		int costeTotal = costeAcumulado + costeArista;
		if (costeTotal >= mejorSolucion || costeArista == -1)
			return;
		mejorSolucion = costeTotal;
		copia(listaActual,mejorLista);
		System.out.println("Nueva mejor solucion: " + costeTotal);
	}
	
	//copiamos una lista a otra. Usado para copiar la mejor lista
	private void copia(int[] fuente, int[] destino) {
		for (int i = 0; i < fuente.length; i++)
			destino[i] = fuente[i];
	}

	//funcion de poda. Podamos si el coste acumulado supera a la mejor solucion
	public boolean poda() {
		return this.costeAcumulado + ayudantePoda[iteracion-1] >= this.mejorSolucion;
	}

	public boolean esSolucion() {
		return this.iteracion >= this.tamano;
	}

	public void preparaPoda(Integer[][] matrizAdyacencia) {
		ArrayList<Integer> valores = new ArrayList<Integer>();
		for (int i = 0; i < tamano; i++)
			for (int j = i+1; j < tamano; j++)
				if (matrizAdyacencia[i][j] != -1)
					valores.add(matrizAdyacencia[i][j]);
		
		Collections.sort(valores);
		
		this.ayudantePoda = new float[tamano];
		for (int i = 0; i < valores.size(); i++) {
			float valor = valores.get(i);
			for (int j = 0; j < tamano-i; j++)
				this.ayudantePoda[j] += valor*(i==0? 2:1);
		}
		
//		this.ayudantePoda = new float[tamano];
//		for (int i = 0; i < tamano; i++) {
//			float min = Float.POSITIVE_INFINITY;
//			for (int j = 0; j < tamano; j++) 
//				if (i != j && matrizAdyacencia[i][j] < min)
//						min = matrizAdyacencia[i][j];
//			for (int j = 0; j < tamano-i; j++)
//				this.ayudantePoda[j] += min*(i==0? 2:1);
//		}
	}
}