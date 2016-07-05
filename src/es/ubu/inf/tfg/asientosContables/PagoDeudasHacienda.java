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

import es.ubu.inf.tfg.doc.*;
import es.ubu.inf.tfg.main.Main;

/** 
 * Clase PagoDeudasHacienda que implementa el asiento contable de pago de las deudas a hacienda.
 *
 * @author Noelia Manso García
 */
public class PagoDeudasHacienda extends Asiento {
	
	/**
	 * Gestiona las cuentas contables y el enunciado cuando se paga las deudas a hacienda.
	 * @param f fecha en la que se pagan las deudas a hacienda.
	 * @param i en este caso, la lista de parametros que el usuario introduce es null.
	 * @param enunciadoCuentas booleano que indica si el usuario desea que en el enunciado 
	 * 						   aparezca el nombre de las cuentas que se usan del PGC.
	 */
	public PagoDeudasHacienda(Calendar f, double [] i, boolean enunciadoCuentas) {
		fecha =f;
		Calendar fech = (Calendar) f.clone();
		fech.set(Calendar.DAY_OF_YEAR, 1);
		fech.set(Calendar.MONTH, 6);

		String enunciado1 = " Se saldan todas las deudas con Hacienda.\n";
		if (enunciadoCuentas){
			enunciado1 = enunciado1 + "CUENTAS PGC: 473.H.P retenciones y pagos a cuenta; 572. Bancos e instituciones de crédito c/c vista, euros;"
					+" 4700. H.P Deudor por IVA; 4750. H.P Acreedor por IVA; 4751. H.P acreedor por retenciones practicas;"
					+ "4752. H.P acreedora por impuesto sobre sociedades \n";
		}
		
		enunciados.add(new Enunciado(fech, enunciado1));	
				
		if(Main.conIVA){
			double saldo4700 = dameCuenta(4700).getSaldo(fech);
			if(saldo4700>0){
				dameCuenta(4700).añadirHaber(new Anotacion(fech, "H.P Deudor por IVA", saldo4700, damePrioridad(4700)));
				dameCuenta(572).añadirDebe(new Anotacion(fech, "H.P Deudor por IVA", saldo4700, damePrioridad(572)));
			}
		
			double saldo4750 = dameCuenta(4750).getSaldo(fech);
			if(saldo4750>0){
				dameCuenta(4750).añadirDebe(new Anotacion(fech, "H.P Acreedora por IVA", saldo4750, damePrioridad(4750)));
				dameCuenta(572).añadirHaber(new Anotacion(fech, "H.P Acreedora por IVA", saldo4750, damePrioridad(572)));
			}
		}
		
		if(Main.conRetenciones){
			double saldo473 = dameCuenta(473).getSaldo(fech);
			if(saldo473>0){
				dameCuenta(473).añadirHaber(new Anotacion(fech, "H.P retenciones y pagos a cuenta", saldo473, damePrioridad(473)));
				dameCuenta(572).añadirDebe(new Anotacion(fech, "H.P retenciones y pagos a cuenta", saldo473, damePrioridad(572)));
			}
			double saldo4751 = dameCuenta(4751).getSaldo(fech);
				if(saldo4751>0){
					dameCuenta(4751).añadirDebe(new Anotacion(fech, "H.P acreedora por retenciones practicas", saldo4751, damePrioridad(4751)));
					dameCuenta(572).añadirHaber(new Anotacion(fech, "H.P acreedora por retenciones practicas", saldo4751, damePrioridad(572)));		
				}
		}
		
		double saldo4752 = dameCuenta(4752).getSaldo(fech);
		if(saldo4752>0){
			dameCuenta(4752).añadirDebe(new Anotacion(fech, "H.P acreedora por impuesto sobre sociedades", saldo4752, damePrioridad(4752)));
			dameCuenta(572).añadirHaber(new Anotacion(fech, "H.P acreedora por impuesto sobre sociedades", saldo4752, damePrioridad(572)));
		}	
		
	}
}