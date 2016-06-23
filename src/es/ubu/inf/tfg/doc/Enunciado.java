package es.ubu.inf.tfg.doc;

import java.util.Calendar;

/**
 * Clase Enunciado implementa el enunciado que tienen los supuestos contables.
 * 
 * @author Noelia Manso García
 *
 */
public class Enunciado implements Comparable<Enunciado>{
	
	/**
	 * Fecha del enunciado en la que se crea el asiento contable.
	 */
	public Calendar fecha;
	/**
	 * Enunciado del asiento contable.
	 */
	public String enunciado;
	
    
    /**
     * Constructor de la clase Enunciado que inicializa las variables necesarias.
     * @param fecha Fecha del enunciado en la que se crea el asiento contable.
     * @param enunciado Enunciado del asiento contable.
     */
    public Enunciado (Calendar fecha, String enunciado) {
        this.fecha = fecha;
        this.enunciado = enunciado;
    }
    
    /**
     * Función que devuelve la fecha del enunciado en la que se crea el asiento contable.
     * @return Fecha del enunciado.
     */
    public Calendar getFecha() {
        return fecha;
    }

    /**
     * Función que devuelve el enunciado de un asiento contable.
     * @return Enunciado del asiento contable.
     */
    public String getEnunciado() {
        return enunciado;
    }

	@Override
	public int compareTo(Enunciado o) {
		return this.fecha.compareTo(o.fecha);
	}
}