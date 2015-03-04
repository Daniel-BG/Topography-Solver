package com.toposhort.terreno;

import java.util.ArrayList;
import java.util.Random;

import com.toposhort.estructuras.Coordenada;

public class GeneradorTerreno {
	
	
	public static Float[][] generadorDiamante(DatosGenerador dg) {
		//comparamos que las alturas sean coherentes
		assert dg.getAlturaMaxima() >= dg.getAlturaMinima() : "Las alturas máxima debe ser mayor que la mínima";
		//comparamos que el desplazamiento base sea positivo
		assert dg.getDesplazamientoBase() >= 0: "El desplazamiento base debe ser positivo";
		//miramos que las dimensiones sean lo suficientemente grandes
		assert dg.getAnchuraMapa() > 1 && dg.getAlturaMapa() > 1 && dg.getDesplazamientoBase() >= 0: "Dimensión muy pequeña o negativa";
				
		Float[][] mapa = new Float[dg.getAnchuraMapa()][dg.getAlturaMapa()];
		
		int diferencia = dg.getAlturaMaxima()-dg.getAlturaMinima()+1;
		Random aleatorio = new Random(dg.getSemilla());
		
		//iniciamos las cuatro esquinas para el algoritmo del diamante
		mapa[0][0] = (float) (aleatorio.nextInt(diferencia)+dg.getAlturaMinima());
		mapa[dg.getAnchuraMapa()-1][0] =(float) (aleatorio.nextInt(diferencia)+dg.getAlturaMinima());
		mapa[0][dg.getAlturaMapa()-1] = (float) (aleatorio.nextInt(diferencia)+dg.getAlturaMinima());
		mapa[dg.getAnchuraMapa()-1][dg.getAlturaMapa()-1] = (float) (aleatorio.nextInt(diferencia)+dg.getAlturaMinima());
		
		InformadorTerreno info = new InformadorTerreno(
				new Coordenada(0,0), new Coordenada(dg.getAnchuraMapa()-1,dg.getAlturaMapa()-1), 
				0, dg.getDesplazamientoBase(), dg.getReductor());
		siguienteIteracion(info, aleatorio,mapa);
		
		return mapa;
	}
	
	private static void siguienteIteracion(InformadorTerreno info, Random r, Float[][] mapa) {
		if (info.comienzo.noExistenPuntosEntre(info.termino))
			return;
		
		//vemos los nodos de las esquinas. la primera letra indica si es inferior(i) o superior(s)
		//y la segunda si es izquierdo (I) o derecho (D)
		Coordenada centro = new Coordenada((info.termino.x+info.comienzo.x)/2,(info.termino.y+info.comienzo.y)/2);
		Float iI = mapa[info.comienzo.x][info.comienzo.y];
		Float iD = mapa[info.termino.x][info.comienzo.y];
		Float sI = mapa[info.comienzo.x][info.termino.y];
		Float sD = mapa[info.termino.x][info.termino.y]; 
		
		if (mapa[info.comienzo.x][centro.y] == null)
			mapa[info.comienzo.x][centro.y] = info.reductor.reduceDos(iI,sI,info,r);
		if (mapa[centro.x][info.comienzo.y] == null)
			mapa[centro.x][info.comienzo.y] = info.reductor.reduceDos(iI,iD,info,r);
		if (mapa[info.termino.x][centro.y] == null)
			mapa[info.termino.x][centro.y] = info.reductor.reduceDos(iD,sD,info,r);
		if (mapa[centro.x][info.termino.y] == null)
			mapa[centro.x][info.termino.y] = info.reductor.reduceDos(sI,sD,info,r);
		
		if (mapa[centro.x][centro.y] == null) {
			ArrayList<Float> lista = new ArrayList<Float>();
			lista.add(mapa[info.comienzo.x][centro.y]);
			lista.add(mapa[centro.x][info.comienzo.y]);
			lista.add(mapa[info.termino.x][centro.y]);
			lista.add(mapa[centro.x][info.termino.y]);
			mapa[centro.x][centro.y] = info.reductor.reduceMultiple(lista,info,r);
		}
		
		siguienteIteracion(info.derivaTermino(centro).siguienteIt(),r,mapa);
		siguienteIteracion(info.derivaComienzo(centro).siguienteIt(),r,mapa);
		siguienteIteracion(info.derivaComienzo(new Coordenada(centro.x,info.comienzo.y))
							   .derivaTermino(new Coordenada(info.termino.x,centro.y))
							   .siguienteIt(),r,mapa);
		siguienteIteracion(info.derivaComienzo(new Coordenada(info.comienzo.x,centro.y))
							   .derivaTermino(new Coordenada(centro.x,info.termino.y))
							   .siguienteIt(),r,mapa);
	}
	
}
