package es.ubu.inf.tfg.asientosContables;
import java.util.Calendar;

import es.ubu.inf.tfg.otrasCosas.Anotacion;
import es.ubu.inf.tfg.otrasCosas.Asiento;
import es.ubu.inf.tfg.otrasCosas.Enunciado;

public class CompraIntangibleNoAmortizable extends Asiento {

	public CompraIntangibleNoAmortizable(Calendar f, double[] i) {
		fecha =f;
		inputs=i;
		
	
		String enunciado1 = " La empresa compra a una Administración Pública el derecho de explotación de un terreno "
		+ "por valor de " + inputs[0] + "€. Se paga al contado. \n"
		+ "CUENTAS PGC: 202. Concesiones administrativas; 572. Bancos e instituciones de crédito c/c vista, euros.\n";
		
		enunciados.add(new Enunciado(fecha, enunciado1));
		
		dameCuenta(202).añadirDebe(new Anotacion(fecha, "Concesiones administrativas", inputs[0], damePrioridad(202)));
		dameCuenta(572).añadirHaber(new Anotacion(fecha, "Bancos Administración Pública", inputs[0], damePrioridad(572)));
		
	}

}
