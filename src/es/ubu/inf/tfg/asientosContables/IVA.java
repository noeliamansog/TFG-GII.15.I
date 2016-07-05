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
 * Clase IVA que implementa los calculos necesarios en las cuentas contables para un supuesto contable con IVA.
 * 
 * @author Noelia Manso García
 */
public class IVA extends Asiento{

	/**
	 * Gestiona las cuentas contables y el enunciado cuando el supuesto contable tiene IV.
	 * @param f fecha en la que se realiza el supuesto contable con IVA.
	 * @param i en este caso, la lista de parametros que el usuario introduce es null.
	 * @param enunciadoCuentas booleano que indica si el usuario desea que en el enunciado 
	 * 						   aparezca el nombre de las cuentas que se usan del PGC.
	 */
	public IVA(Calendar f, double[] i, boolean enunciadoCuentas) {
		fecha = f;
		
		double saldoIVARepercutido;
		double saldoIVASoportado;
		
		Calendar fechaFinAno = (Calendar)fecha.clone();
		fechaFinAno.set(fechaFinAno.get(Calendar.YEAR), 11, 31);
		
		String enunciado1 = " Se realiza la liquidación del IVA.\n";
		if (enunciadoCuentas){
			enunciado1 = enunciado1 + "477. H.P. IVA repercutido. 4700. H.P. deudor por IVA. "
		 	  						+ "472. H.P. IVA soportado. 4750 H.P Acreedor por IVA. \n";
		}

		enunciados.add(new Enunciado(fechaFinAno, enunciado1));
		
		saldoIVARepercutido = dameCuenta(477).getSaldo(fechaFinAno);
		saldoIVASoportado = dameCuenta(472).getSaldo(fechaFinAno);
		
		dameCuenta(477).añadirDebe(new Anotacion(fechaFinAno, "H.P. IVA repercutido ", saldoIVARepercutido, damePrioridad(477)));
		dameCuenta(472).añadirHaber(new Anotacion(fechaFinAno, "H.P. IVA soportado ", saldoIVASoportado, damePrioridad(472)));
		
		if(saldoIVARepercutido > saldoIVASoportado){
			dameCuenta(4750).añadirHaber(new Anotacion(fechaFinAno, "H.P Acreedor por IVA", saldoIVARepercutido-saldoIVASoportado, damePrioridad(4750)));
			
		}else{
			dameCuenta(4700).añadirDebe(new Anotacion(fechaFinAno, "H.P. deudor por IVA ", saldoIVASoportado-saldoIVARepercutido, damePrioridad(4700)));	
		}
	}
}
