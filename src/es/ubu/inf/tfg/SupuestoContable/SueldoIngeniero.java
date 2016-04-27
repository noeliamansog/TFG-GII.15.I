import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SueldoIngeniero extends Asiento {

	public SueldoIngeniero(Calendar fecha, int[] inputs) {
	
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");

		enunciado = formateador.format(fecha.getTime())+" Se contrata a un ingeniero informático cuyo sueldo bruto anual es " +inputs[0]+ "€. El informático dedica "
			 + "el " +inputs[1]+ "% de su tiempo a desarrollar un proyecto de investigación con altas probabilidades "
			 + "de generar ganancias en un futuro. Contabilizar primero el gasto, y luego la activación de la parte correspondiente. \n"
 			 + "CUENTAS PGC: 640. Sueldo y salario; 572. Bancos e instituciones de crédito c/c vista, euros. "
 			 + "200.Gastos en investigación; 730 Trabajos realizados para el inmovilizado intangible.\n";
	
	System.out.println(enunciado);	
	
	dameCuenta(640).añadirDebe(new Anotacion(fecha, "Sueldos Ingeniero informático", inputs[0]));
	
	dameCuenta(572).añadirHaber(new Anotacion(fecha, "Bancos ingeniero", inputs[0]));

	dameCuenta(200).añadirDebe(new Anotacion(fecha, "Gasto en investigacion", inputs[1]*inputs[0]));
	
	dameCuenta(730).añadirHaber(new Anotacion(fecha, "Trabajos realizados para el inmovilizado intangible", inputs[1]*inputs[0]));
		
	}
}
