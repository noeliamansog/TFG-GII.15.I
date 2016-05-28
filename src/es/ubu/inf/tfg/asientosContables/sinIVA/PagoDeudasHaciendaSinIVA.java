package es.ubu.inf.tfg.asientosContables.sinIVA;
import java.util.Calendar;

import es.ubu.inf.tfg.otrasCosas.*;

public class PagoDeudasHaciendaSinIVA extends Asiento {
	
	public PagoDeudasHaciendaSinIVA(Calendar f, double [] i, boolean enunciadoCuentas) {
		fecha =f;
		inputs=i;	
	}
	
	public void generar(Calendar f, double[] inputs){
		String enunciado1 = " Se saldan todas las deudas con Hacienda.\n";
		if (enunciadoCuentas){
			enunciado1 = enunciado1 + "CUENTAS PGC: 473.H.P retenciones y pagos a cuenta; 572. Bancos e instituciones de crédito c/c vista, euros;"
					+"4751. H.P acreedor por retenciones practicas; 4752. H.P acreedora por impuesto sobre sociedades \n";
		}
		
		enunciados.add(new Enunciado(fecha, enunciado1));	
				
		double saldo473 = dameCuenta(473).getSaldo(fecha);
		dameCuenta(473).añadirHaber(new Anotacion(fecha, "H.P retenciones y pagos a cuenta", saldo473, damePrioridad(473)));
		
		double saldo4751 = dameCuenta(4751).getSaldo(fecha);
		dameCuenta(4751).añadirDebe(new Anotacion(fecha, "H.P acreedor por retenciones practicas", saldo4751, damePrioridad(4751)));
		
		double saldo4752 = dameCuenta(4752).getSaldo(fecha);
		dameCuenta(4752).añadirDebe(new Anotacion(fecha, "H.P acreedora por impuesto sobre sociedades", saldo4752, damePrioridad(4752)));
	
		//Lo que debo a hacienda
		double saldo = (saldo4751 + saldo4752) - saldo473;
		dameCuenta(572).añadirHaber(new Anotacion(fecha, "Bancos hacienda", saldo, damePrioridad(572)));
	
	}
}