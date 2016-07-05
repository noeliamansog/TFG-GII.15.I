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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Clase Cuenta que implementa una cuenta contable.
 * 
 * @author Noelia Manso García
 */
public class Cuenta implements Comparable<Cuenta>{
	
	/**
	 * Código de la cuenta.
	 */
	public int codigo = 0;
	/**
	 * Nombre de la cuenta.
	 */
	public String nombre;
	/**
	 * Prioridad de la cuenta.
	 */
	public int prioridad;
	/**
	 * Lista de anotaciones que pertenecen al debe (Gastos o Cobros).
	 */
	public ArrayList<Anotacion> debe = new ArrayList<Anotacion>();
	/**
	 * Lista de anotaciones que pertenecen al haber (Ingresos o Pagos).
	 */
	public ArrayList<Anotacion> haber = new ArrayList<Anotacion>();
	/**
	 * HashMap que nos indica si el saldo de la cuenta se calcula restando la cantidad del Debe menos el Haber
	 * o viceversa.
	 */
	public HashMap<Integer, Boolean> saldoDebeMenosHaber = new HashMap<Integer, Boolean>();
	
	/**
	 * Constructor de la clase Cuenta que inicializa las variables necesarias.
	 * 
	 * @param codigo Código de la cuenta.
	 * @param nombre Nombre de la cuenta.
	 * @param prioridad Prioridad de la cuenta.
	 */
	public Cuenta(int codigo, String nombre, int prioridad){
		this.codigo = codigo;
		this.nombre = nombre;
		this.prioridad = prioridad;
		inicializarSaldoDebeMenosHaber();
	}

	/**
	 * Función que añade una anotación en la lista Debe.
	 * 
	 * @param anotacion Anotación que se desea añadir.
	 */
	public void añadirDebe(Anotacion anotacion){
		debe.add(anotacion);	
	}
	
	/**
	 * Función que añade una anotación en la lista Haber.
	 * 
	 * @param anotacion Anotación que se desea añadir.
	 */
	public void añadirHaber(Anotacion anotacion){
		haber.add(anotacion);	
	}
	
	/**
	 * Función que calcula el saldo de una cuenta en una determinada fecha.
	 * @param fecha Fecha en la que se desea calcular el saldo.
	 * @return Saldo de la cuenta.
	 */
	public double getSaldo(Calendar fecha){
		double contDebe = 0;
		double contHaber = 0;
		
		for( int i = 0 ; i < debe.size() ; i++ ){
			if(debe.get(i).fecha.before(fecha)){
				contDebe += debe.get(i).cantidad;
			}
		}
		for( int i = 0 ; i < haber.size() ; i++ ){
			if(haber.get(i).fecha.before(fecha)){
				contHaber += haber.get(i).cantidad;
			}
		}
		if (saldoDebeMenosHaber.get(codigo)==true){
			return contDebe-contHaber;
		}else{
			return contHaber-contDebe;
		}
	}
	
	/**
	* Función que nos informa si existe alguna anotación en el año pasado como parámetro.
	* @param ano Ano en el que se desea comprobar si existe alguna anotación.
	* @return True en caso de que exista alguna anotación, False en caso contrario.
	*/
	public boolean algunaAnotacion(int ano){
	    boolean algunaAnotacion = false;
	    for( int i = 0 ; i < debe.size() ; i++ ){
	        if(debe.get(i).fecha.get(Calendar.YEAR)==ano){
	            algunaAnotacion = true;
	        }
	    }
	    for( int i = 0 ; i < haber.size() ; i++ ){
	        if(haber.get(i).fecha.get(Calendar.YEAR)==ano){
	            algunaAnotacion = true;
	        }
	    }
	    return algunaAnotacion;
	}
	
	/**
	 * Función que inicializa el HashMap saldoDebeMenosHaber para saber si el saldo de la cuenta
	 *  se calcula restando la cantidad del Debe menos el Haber o viceversa.
	 */
	public void inicializarSaldoDebeMenosHaber(){
		// INICIALIZAMOS LAS CUENTAS CON UN BOOLEANO PARA SABER SI ESTÁN A LA IZQUIERDA O A LA DERECHA DE LA CUENTA
		saldoDebeMenosHaber.put(12, false);
		
		saldoDebeMenosHaber.put(100, false);
		saldoDebeMenosHaber.put(110, false);
		saldoDebeMenosHaber.put(112, false);
		saldoDebeMenosHaber.put(129, false);
		saldoDebeMenosHaber.put(170, false);
		saldoDebeMenosHaber.put(173, false);

		saldoDebeMenosHaber.put(200, true);
		saldoDebeMenosHaber.put(202, true);
		saldoDebeMenosHaber.put(203, true);
		saldoDebeMenosHaber.put(206, true);
		saldoDebeMenosHaber.put(210, true);
		saldoDebeMenosHaber.put(211, true);
		saldoDebeMenosHaber.put(213, true);
		saldoDebeMenosHaber.put(216, true);
		saldoDebeMenosHaber.put(217, true);
		saldoDebeMenosHaber.put(218, true);
		saldoDebeMenosHaber.put(280, true);
		saldoDebeMenosHaber.put(281, true);
		
		saldoDebeMenosHaber.put(300, true);

		saldoDebeMenosHaber.put(400, false);
		saldoDebeMenosHaber.put(430, true);
		saldoDebeMenosHaber.put(472, true);
		saldoDebeMenosHaber.put(473, true);
		saldoDebeMenosHaber.put(476, false);
		saldoDebeMenosHaber.put(477, false);

		saldoDebeMenosHaber.put(523, false);
		saldoDebeMenosHaber.put(572, true);

		saldoDebeMenosHaber.put(600, true);
		saldoDebeMenosHaber.put(610, true);
		saldoDebeMenosHaber.put(630, true);
		saldoDebeMenosHaber.put(640, true);
		saldoDebeMenosHaber.put(642, true);
		saldoDebeMenosHaber.put(662, true);
		saldoDebeMenosHaber.put(680, true);
		saldoDebeMenosHaber.put(681, true);

		saldoDebeMenosHaber.put(700, true);
		saldoDebeMenosHaber.put(705, true);
		saldoDebeMenosHaber.put(730, true);
		saldoDebeMenosHaber.put(769, true);

		saldoDebeMenosHaber.put(4700, true);
		saldoDebeMenosHaber.put(4751, false);
		saldoDebeMenosHaber.put(4750, false);
		saldoDebeMenosHaber.put(4752, false);
	}


	@Override
	public int compareTo(Cuenta cuenta) {
		int resultado = 0;
		if (prioridad >0){
			if (this.prioridad < cuenta.prioridad){
				resultado = -1;
			}
			if (this.prioridad > cuenta.prioridad){
				resultado = 1;
			}
		}else{
			if (this.prioridad < cuenta.prioridad){
				resultado = 1;
			}
			if (this.prioridad > cuenta.prioridad){
				resultado = -1;
			}
		}
		return resultado;
	}
}