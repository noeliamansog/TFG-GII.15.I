package es.ubu.inf.tfg.asientosContables.sinRetenciones;
import java.util.Calendar;

import es.ubu.inf.tfg.doc.*;

/** 
 * Clase DividendosSinRetenciones que implementa el asiento contable de dividendos sin retenciones.
 *
 * @author Noelia Manso García
 */
public class DividendosSinRetenciones extends Asiento {
	
	/**
	 * Gestiona las cuentas contables y el enunciado al realizar dividendos sin retenciones.
	 * @param f fecha en la que se realiza los dividendos.
	 * @param i lista de parametros que el usuario introduce para que sea un asiento contable personalizado.
	 * @param enunciadoCuentas booleano que indica si el usuario desea que en el enunciado 
	 * 						   aparezca el nombre de las cuentas que se usan del PGC.
	 */
	public DividendosSinRetenciones(Calendar f, double [] i, boolean enunciadoCuentas) {
		fecha =f;
		inputs=i;

		String enunciado1 = " Se decide repartir dividendos por valor del " +inputs[0]+ "% del resultado del "
				+ "ejercicio anterior \n";
		if (enunciadoCuentas){
			enunciado1 = enunciado1 + "CUENTAS PGC: 572. Bancos e instituciones de crédito c/c vista, euros; 129. Resultados del ejercicio;"
									+ "12. Resultados pendientes de aplicación.\n";
		}
		
		enunciados.add(new Enunciado(fecha, enunciado1));
		
		double saldo12 = dameCuenta(12).getSaldo(fecha);
		dameCuenta(12).añadirDebe(new Anotacion(fecha, "Reparto de dividendos",saldo12, damePrioridad(12)));
		
		Calendar fechaAñoAnterior = (Calendar)fecha.clone();
		fechaAñoAnterior.add(Calendar.YEAR, -1);
		
		dameCuenta(112).añadirHaber(new Anotacion(fecha, "Beneficios retenidos del año: "+fechaAñoAnterior.get(Calendar.YEAR), ((100-inputs[0])*saldo12)/100, damePrioridad(112)));
		dameCuenta(572).añadirHaber(new Anotacion(fecha, "Reparto dividendos", (inputs[0]/100)*saldo12, damePrioridad(572)));
		
	}
}