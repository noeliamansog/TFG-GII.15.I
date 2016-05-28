package es.ubu.inf.tfg.asientosContables;
import java.util.Calendar;

import es.ubu.inf.tfg.otrasCosas.Anotacion;
import es.ubu.inf.tfg.otrasCosas.Asiento;
import es.ubu.inf.tfg.otrasCosas.Enunciado;

public class SueldoIngeniero extends Asiento {

	public SueldoIngeniero(Calendar f, double[] i, boolean enunciadoCuentas) {
		fecha =f;
		inputs=i;
		nombre = "sueldoIngeniero";
	}
	
	public void generar(Calendar f, double[] inputs){
		String enunciado1 = " Se contrata a un ingeniero informático cuyo sueldo bruto anual es " +inputs[0]+ "€. El informático dedica "
				 + "el " +inputs[1]+ "% de su tiempo a desarrollar un proyecto de investigación con altas probabilidades "
				 + "de generar ganancias en un futuro. Contabilizar primero el gasto, y luego la activación de la parte correspondiente. \n";
			if (enunciadoCuentas){
				enunciado1 = enunciado1 + "CUENTAS PGC: 640. Sueldo y salario; 572. Bancos e instituciones de crédito c/c vista, euros. "
										+ "200.Gastos en investigación; 730 Trabajos realizados para el inmovilizado intangible.\n";
			}
		
			enunciados.add(new Enunciado(fecha, enunciado1));
			
			dameCuenta(640).añadirDebe(new Anotacion(fecha, "Sueldos Ingeniero informático", inputs[0], damePrioridad(640)));
			dameCuenta(572).añadirHaber(new Anotacion(fecha, "Ingeniero informático", inputs[0], damePrioridad(572)));
			dameCuenta(200).añadirDebe(new Anotacion(fecha, "Gasto en investigacion", (inputs[1]*inputs[0])/100, damePrioridad(200)));
			dameCuenta(730).añadirHaber(new Anotacion(fecha, "Trabajos realizados para el inmovilizado intangible", (inputs[1]*inputs[0])/100, damePrioridad(730)));
		
	}
}
