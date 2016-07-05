/* GSC
 * GSC es una aplicación que permite la creación de supuestos contables 
 * personalizados y los resuelve de forma automática.
 * Copyright (C) 2016 Noelia Manso & Luis R. Izquierdo
 *
 * This file is part of GSC.
 *
 * GSC is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * GSC is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with GSC.  If not, see <http://www.gnu.org/licenses/>.
 */

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
