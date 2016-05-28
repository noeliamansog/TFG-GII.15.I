package es.ubu.inf.tfg.asientosContables.sinRetenciones;
import java.util.Calendar;

import es.ubu.inf.tfg.otrasCosas.*;

public class DividendosSinRetenciones extends Asiento {
	
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
		
		double resultado = dameCuenta(12).getSaldo(fecha);
		dameCuenta(12).añadirDebe(new Anotacion(fecha, "Reparto de dividendos",resultado, damePrioridad(12)));
		
		Calendar fechaAñoAnterior = (Calendar)fecha.clone();
		fechaAñoAnterior.add(Calendar.YEAR, -1);
		
		dameCuenta(112).añadirHaber(new Anotacion(fecha, "Beneficios retenidos del año: "+fechaAñoAnterior.get(Calendar.YEAR), ((100-inputs[0])*resultado)/100, damePrioridad(112)));
		dameCuenta(572).añadirHaber(new Anotacion(fecha, "Reparto dividendos", (inputs[0]/100)*resultado, damePrioridad(572)));
		
	}
}