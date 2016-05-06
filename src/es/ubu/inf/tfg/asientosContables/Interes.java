package es.ubu.inf.tfg.asientosContables;
import java.util.Calendar;

import es.ubu.inf.tfg.otrasCosas.Anotacion;
import es.ubu.inf.tfg.otrasCosas.Asiento;
import es.ubu.inf.tfg.otrasCosas.Enunciado;

public class Interes extends Asiento{

	public Interes(Calendar f, int[] i) {
		fecha =f;
		inputs=i;

		String enunciado1 = " La empresa recibe " +inputs[0]+ "€ por intereses devengados en la cuenta corriente "
				+ "durante este año. \n"
				+ "CUENTAS PGC: 572. Bancos e instituciones de crédito c/c vista, euros; 473. Hacienda Pública, retenciones y pagos a cuenta;"
				+ "769. Otros ingresos financieros.\n";
		
		enunciados.add(new Enunciado(fecha, enunciado1));

		dameCuenta(572).añadirDebe(new Anotacion(fecha, "Intereses devengados", inputs[0]));
		dameCuenta(473).añadirDebe(new Anotacion(fecha, "Hacienda Pública", inputs[0]));
		dameCuenta(769).añadirHaber(new Anotacion(fecha, "Otros ingresos financieros", inputs[0]));
		
	}
}
