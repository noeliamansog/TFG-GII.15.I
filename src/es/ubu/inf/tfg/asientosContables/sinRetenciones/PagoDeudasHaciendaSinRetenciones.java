package es.ubu.inf.tfg.asientosContables.sinRetenciones;
import java.util.Calendar;

import es.ubu.inf.tfg.doc.*;

public class PagoDeudasHaciendaSinRetenciones extends Asiento {
	
	public PagoDeudasHaciendaSinRetenciones(Calendar f, double [] i, boolean enunciadoCuentas) {
		fecha =f;
		inputs=i;

		String enunciado1 = " Se saldan todas las deudas con Hacienda.\n";
		if (enunciadoCuentas){
			enunciado1 = enunciado1 + "CUENTAS PGC: 572. Bancos e instituciones de crédito c/c vista, euros;"
					+" 4700. H.P Deudor por IVA; 4750. H.P Acreedor por IVA; \n";
		}
		
		enunciados.add(new Enunciado(fecha, enunciado1));	
				
		double saldo4700 = dameCuenta(4700).getSaldo(fecha);
		dameCuenta(4700).añadirHaber(new Anotacion(fecha, "H.P Deudor por IVA", saldo4700, damePrioridad(4700)));
		
		double saldo4750 = dameCuenta(4750).getSaldo(fecha);
		dameCuenta(4750).añadirDebe(new Anotacion(fecha, "H.P Acreedor por IVA", saldo4750, damePrioridad(4750)));
		
		//Lo que debo a hacienda
		double saldo = saldo4750 - saldo4700;
		dameCuenta(572).añadirHaber(new Anotacion(fecha, "Bancos hacienda", saldo, damePrioridad(572)));
		
	}
}