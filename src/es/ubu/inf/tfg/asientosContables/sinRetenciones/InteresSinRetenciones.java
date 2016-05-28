package es.ubu.inf.tfg.asientosContables.sinRetenciones;
import java.util.Calendar;

import es.ubu.inf.tfg.otrasCosas.Anotacion;
import es.ubu.inf.tfg.otrasCosas.Asiento;
import es.ubu.inf.tfg.otrasCosas.Enunciado;

public class InteresSinRetenciones extends Asiento{

	public InteresSinRetenciones(Calendar f, double[] i, boolean enunciadoCuentas) {
		fecha =f;
		inputs=i;

		String enunciado1 = " La empresa obtiene un ingreso de " +inputs[0]+ "€ por intereses devengados en la cuenta corriente "
				+ "durante este año. \n";
		if (enunciadoCuentas){
			enunciado1 = enunciado1 + "CUENTAS PGC: 572. Bancos e instituciones de crédito c/c vista, euros; 769. Otros ingresos financieros.\n";
		}
		
		enunciados.add(new Enunciado(fecha, enunciado1));

		dameCuenta(572).añadirDebe(new Anotacion(fecha, "Intereses devengados", inputs[0], damePrioridad(572)));
		dameCuenta(769).añadirHaber(new Anotacion(fecha, "Otros ingresos financieros", inputs[0], damePrioridad(769)));
		
	}
}
