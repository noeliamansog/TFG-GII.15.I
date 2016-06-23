package es.ubu.inf.tfg.doc;

import java.util.Calendar;

/**
 * La clase Anotación implementa las anotaciones que puede tener una cuenta.
 * 
 * @author Noelia Manso García
 */
public class Anotacion  implements Comparable<Anotacion>{

	/**
	 * Fecha de la anotación.
	 */
	public Calendar fecha;
	/**
	 * Nombre de la anotación.
	 */
	public String nombre;
	/**
	 * Cantidad de la anotación.
	 */
	public double cantidad;
	/**
	 * Prioridad de la anotación.
	 */
	public int prioridad;
	
	/**
	 * Constructor de la clase Anotación que inicializa las variables.
	 * @param fecha Fecha de la anotación.
	 * @param nombre Nombre de la anotación.
	 * @param cantidad Cantidad de la anotación.
	 * @param prioridad Prioridad de la anotación.
	 */
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
