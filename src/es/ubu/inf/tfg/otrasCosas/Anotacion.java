package es.ubu.inf.tfg.otrasCosas;

import java.util.Calendar;

public class Anotacion implements Comparable<Anotacion>{

	Calendar fecha;
	String nombre;
	double cantidad;
	
	public Anotacion (Calendar fecha, String nombre, double cantidad){
		this.fecha = fecha;
		this.nombre = nombre;
		this.cantidad = cantidad;
	}

	@Override
	public int compareTo(Anotacion o) {
		return this.fecha.compareTo(o.fecha);
	}
	
}
