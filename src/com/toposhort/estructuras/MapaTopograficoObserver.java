package com.toposhort.estructuras;

import java.util.List;

import com.toposhort.algoritmos.IteradorViajante;

public interface MapaTopograficoObserver {
	
	
	public void CartografiaActualizada(Nodo[][] mapa, int anchura, int altura);
	
	public void CaminoCreado(List<Nodo> camino);
	
	public void WaypointCreado(Coordenada c, String datos);
	
	public void WaypointBorrado(Coordenada c);
	
	public void CaminosBorrados();

	public void WayPointsBorrados();

	public void MatrizAdyacenciaCreada(Integer[][] matriz);

	public void ViajanteResuelto(List<List<Nodo>> caminoViajante, IteradorViajante iv);

	public void MultiplesCaminosCreados(List<List<Nodo>> lista);

	public void TerrenoActualizado(Nodo[][] mapaTopografico, Coordenada coordenada,
			Coordenada coordenada2);

	public void AlturaSeleccionada(float altura);


}
