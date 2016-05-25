package es.ubu.inf.tfg.otrasCosas;

import java.util.Calendar;

public class Anotacion  implements Comparable<Anotacion>{

	public Calendar fecha;
	public String nombre;
	public double cantidad;
	public int prioridad;
	
	public Anotacion (Calendar fecha, String nombre, double cantidad, int prioridad){
		this.fecha = fecha;
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.prioridad = prioridad;
	}	

	@Override
	public int compareTo(Anotacion anotacion) {
		int resultado = 0;
		if(this.fecha.compareTo(anotacion.fecha)!=0){
			resultado = this.fecha.compareTo(anotacion.fecha);
		}else{
			if (this.prioridad < anotacion.prioridad) {
	            resultado = -1;
	        }
	        if (this.prioridad > anotacion.prioridad) {
	            resultado = 1;
	        }
		}
		return resultado;
	}
}
