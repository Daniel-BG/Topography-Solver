package com.toposhort.terreno;

import java.util.ArrayList;
import java.util.Random;

public class ReductorExponencial extends ReductorAbstracto{

	@Override
	public Float reduceDos(Float a, Float b, InformadorTerreno info, Random r) {
		return (a+b)/2.0f+2.0f*(0.5f-r.nextFloat())*(info.desplazamientoBase >> (info.iteracion/2));
	}

	@Override
	public Float reduceMultiple(ArrayList<Float> valores, InformadorTerreno info, Random r) {
		if (valores == null || valores.isEmpty())
			return 0f;
		float altura = 0;
		for (Float f: valores)
			altura += f;
		altura += 2.0f*(0.5f-r.nextFloat())*(info.desplazamientoBase >> info.iteracion);
		return altura/(float)valores.size();
	}
}
