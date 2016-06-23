package es.ubu.inf.tfg.asientosContables;
import java.util.Calendar;

import es.ubu.inf.tfg.doc.Anotacion;
import es.ubu.inf.tfg.doc.Asiento;
import es.ubu.inf.tfg.doc.Enunciado;

/** 
 * Clase Interes que implementa el asiento contable de intereses.
 *
 * @author Noelia Manso García
 */
public class Interes extends Asiento{

	/**
	 * Gestiona las cuentas contables y el enunciado al realizar intereses en el supuesto contable.
	 * @param f fecha en la que se realizan los intereses.
	 * @param i lista de parametros que el usuario introduce para que sea un asiento contable personalizado.
	 * @param enunciadoCuentas booleano que indica si el usuario desea que en el enunciado 
	 * 						   aparezca el nombre de las cuentas que se usan del PGC.
	 */
	public Interes(Calendar f, double[] i, boolean enunciadoCuentas) {
		fecha =f;
		inputs=i;

		String enunciado1 = " La empresa obtiene un ingreso de " +inputs[0]+ "€ por intereses devengados en la cuenta corriente "
				+ "durante este año, de los cuales cobra el "+inputs[1]+"% (el resto lo retienen). \n";
		if (enunciadoCuentas){
			enunciado1 = enunciado1 + "CUENTAS PGC: 572. Bancos e instituciones de crédito c/c vista, euros; 473. Hacienda Pública, retenciones y pagos a cuenta;"
									+ "769. Otros ingresos financieros.\n";
		}
		
		enunciados.add(new Enunciado(fecha, enunciado1));

		dameCuenta(572).añadirDebe(new Anotacion(fecha, "Intereses devengados", inputs[0]*(inputs[1]/100), damePrioridad(572)));
		dameCuenta(473).añadirDebe(new Anotacion(fecha, "Hacienda Pública", inputs[0]*(1- inputs[1]/100), damePrioridad(473)));
		dameCuenta(769).añadirHaber(new Anotacion(fecha, "Otros ingresos financieros", inputs[0], damePrioridad(769)));
		
	}
}
