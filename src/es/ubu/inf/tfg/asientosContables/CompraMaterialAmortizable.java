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

package es.ubu.inf.tfg.asientosContables;
import java.util.Calendar;

import es.ubu.inf.tfg.doc.Anotacion;
import es.ubu.inf.tfg.doc.Asiento;
import es.ubu.inf.tfg.doc.Enunciado;
	
/** 
 * Clase CompraMaterialAmortizable que implementa el asiento contable de una compra de material amortizable.
 *
 * @author Noelia Manso García
 */
public class CompraMaterialAmortizable extends Asiento{

		/**
		 * Gestiona las cuentas contables y el enunciado al realizar una compra de material amortizable.
		 * @param f fecha en la que se realiza la compra
		 * @param i lista de parametros que el usuario introduce para que sea un asiento contable personalizado.
		 * @param enunciadoCuentas booleano que indica si el usuario desea que en el enunciado 
		 * 						   aparezca el nombre de las cuentas que se usan del PGC.
		 */
	public CompraMaterialAmortizable(Calendar f, double[] i, boolean enunciadoCuentas) {
		fecha =f;
		inputs=i;
		String compra = null;
		
		int com = (int)inputs[0];
		
		switch (com){
			case 0: compra = "maquinaria"; break;
			case 1: compra = "mobiliario de oficina"; break;
			case 2:	compra = "equipos para procesos de información"; break;
			case 3: compra = "un elemento de transporte"; break;
		}
		
		String enunciado1 = " La empresa compra " +compra+ " por valor de " +inputs[1]+ "€. "
				 + "El importe de la compra se abonará a los " +(int)inputs[2]+ " días. La compra "
				 + "se amortiza linealmente en " +(int)inputs[3]+ " años.\n";
		
		switch (com){
			case 0: 
					if(enunciadoCuentas){
						enunciado1 = enunciado1 + "CUENTAS PGC: 213. Maquinaria; 400. Proveedores.\n";
					}
					dameCuenta(213).añadirDebe(new Anotacion(fecha, "Maquinaria", inputs[1], damePrioridad(213)));
					dameCuenta(400).añadirHaber(new Anotacion(fecha, "Maquinaria", inputs[1], damePrioridad(400)));	
					break;
			case 1: 
					if(enunciadoCuentas){ 
						enunciado1 = enunciado1 + "CUENTAS PGC: 216. Mobiliario; 400. Proveedores.\n"; 
					}
					dameCuenta(216).añadirDebe(new Anotacion(fecha, "Mobiliario", inputs[1], damePrioridad(216))); 
					dameCuenta(400).añadirHaber(new Anotacion(fecha, "Mobiliario", inputs[1], damePrioridad(400)));	
					break;
			case 2:  
					if(enunciadoCuentas){
						enunciado1 = enunciado1 + "CUENTAS PGC: 217. Equipos para procesos de información; 400. Proveedores.\n";
					}
					dameCuenta(217).añadirDebe(new Anotacion(fecha, "Equipos para procesos de información", inputs[1], damePrioridad(217))); 
					dameCuenta(400).añadirHaber(new Anotacion(fecha, "Equipos para procesos de información", inputs[1], damePrioridad(400)));
					break;
			case 3:  
					if(enunciadoCuentas){
						enunciado1 = enunciado1 + "CUENTAS PGC: 218. Elementos de transporte; 400. Proveedores.\n";
					}
					dameCuenta(218).añadirDebe(new Anotacion(fecha, "Elementos de transporte", inputs[1], damePrioridad(218)));
					dameCuenta(400).añadirHaber(new Anotacion(fecha, "Elementos de transporte", inputs[1], damePrioridad(400)));
					break;
		}
		
		enunciados.add(new Enunciado(fecha, enunciado1));
	
		//SE SALDAN LAS DEUDAS CON LOS PROVEEDORES "Y" DIAS DESPUES
		Calendar fechaDeudas = (Calendar)fecha.clone();
		fechaDeudas.add(Calendar.DAY_OF_YEAR, (int) +inputs[2]);
		
		String enunciado2 = " Se salda la deuda con los proveedores de " +compra+ ".\n";
		 
		if(enunciadoCuentas){
			enunciado2 = enunciado2 + "CUENTAS PGC: 400. Proveedores; 572. Bancos e instituciones de crédito c/c vista, euros. \n";
		}
		
		enunciados.add(new Enunciado(fechaDeudas, enunciado2));
		
		dameCuenta(400).añadirDebe(new Anotacion(fechaDeudas, "Deuda con proveedores de inmovilizado material amortizable", inputs[1], damePrioridad(400)));
		dameCuenta(572).añadirHaber(new Anotacion(fechaDeudas, "Deuda con proveedores de inmovilizado material amortizable", inputs[1], damePrioridad(572)));
		

		
		//SE AÑADE LA AMORTIZACIÓN AL FINAL DEL AÑO:				
		String enunciado3;
		
		for (int j=0; j<inputs[3]; j++){
			Calendar fechaAmortizacion = (Calendar)fecha.clone();
			fechaAmortizacion.set(fechaAmortizacion.get(Calendar.YEAR), 11, 31);
			fechaAmortizacion.add(Calendar.YEAR, +j);
			
			enunciado3 = " Se añade la amortización de "+compra+" al final del año.\n";
			 
			if(enunciadoCuentas){
				enunciado3 = enunciado3 +"CUENTAS PGC: 281. Amortización acumulada del inmovilizado material; 681. Amortización del inmovilizado material.\n";
			}
			
			enunciados.add(new Enunciado(fechaAmortizacion, enunciado3));
			
			dameCuenta(681).añadirDebe(new Anotacion(fechaAmortizacion, "Amortización inmovilizado material", (inputs[1]/inputs[3]), damePrioridad(681)));
			dameCuenta(281).añadirHaber(new Anotacion(fechaAmortizacion, "Amortización acumulada del inmovilizado material", (inputs[1]/inputs[3]), damePrioridad(281)));		
			
		}	
	}
}
