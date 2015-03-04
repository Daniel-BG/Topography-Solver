package com.toposhort.terreno;

import java.util.ArrayList;
import java.util.Random;

public abstract class ReductorAbstracto {
	
	public abstract Float reduceDos(Float a, Float b, InformadorTerreno info, Random r);
	
	
	public abstract Float reduceMultiple(ArrayList<Float> valores, InformadorTerreno info, Random r);
}
