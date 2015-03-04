package com.toposhort.algoritmos;

public class Viajante {
	
	private long nodosExplorados = 0, numeroPodas = 0;
	
	//algoritmo del viajante por fuerza bruta (para asegurar solucion optima);
	public IteradorViajante resuelveViajanteFuerzaBruta(Integer[][] matrizAdyacencia) {
		int tamano = matrizAdyacencia.length;
		IteradorViajante it = new IteradorViajante(tamano);
		

		if (Viajante.esCoherente(matrizAdyacencia)) {
			it.preparaPoda(matrizAdyacencia);
			recursionViajante(it,matrizAdyacencia);
		}
		
		return it;
	}

	//algoritmo recursivo de ramificacion y poda
	private void recursionViajante(IteradorViajante it, Integer[][] matriz) {
		//si podamos, volvemos
		if (it.poda())  {
			this.numeroPodas++;
			return;
		}
		else
			this.nodosExplorados++;
		
		//si es solucion, volvemos tras mejorar el resultado
		if (it.esSolucion()) {
			it.mejoraSolucion(matriz);
			return;
		}

		//para todos los nodos posibles, los marcamos, ejecutamos recursión, y desmarcamos
		for (int i = 0; i < it.tamano; i++) {
			if (!it.marcas[i]) {
				it.marcas[i] = true;
				int fuente = it.listaActual[it.iteracion-1];
				int costeOrig = it.costeAcumulado;
				it.costeAcumulado += matriz[fuente][i];
				it.listaActual[it.iteracion] = i;
				it.iteracion++;
				
				recursionViajante(it,matriz);
				
				it.iteracion--;
				it.costeAcumulado = costeOrig;
				it.marcas[i] = false;
			}
		}		
	}
	
	private static boolean esCoherente(Integer[][] matrizAdyacencia) {
		int tamano = matrizAdyacencia.length;
		//miramos que todos los nodos tengan al menos dos caminos conectados con coste < Infinito
		for (int i = 0; i < tamano; i++) {
			int cuenta = 0;
			for (int j = 0; j < tamano; j++) 
				if (j != i) {
					if (matrizAdyacencia[i][j] != -1)
						cuenta++;
					if (cuenta > 1)
						break;
				}
			if (cuenta < 2)
				return false;
		}
			
		//miramos que el conjunto alcanzable desde el primer nodo sea el total
		boolean[] alcanzables = new boolean[tamano];
		alcanzables[0] = true;
		for (int i = 0; i < tamano; i++)
			for (int j = 0; j < tamano; j++)
				if (matrizAdyacencia[i][j] != -1 && alcanzables[i])
					alcanzables[j] = true;
		for (int i = 0; i < tamano; i++)
			if (!alcanzables[i])
				return false;
		
		//si no, procedemos a intentar resolverlo
		return true;
	}
	
	public String getDatosAlgoritmo() {
		return "Nodos explorados: " + this.nodosExplorados + "\nPodas realizadas: " + this.numeroPodas;
	}
}


