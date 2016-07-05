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