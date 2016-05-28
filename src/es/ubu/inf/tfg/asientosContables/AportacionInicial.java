package es.ubu.inf.tfg.asientosContables;
import java.util.Calendar;

import es.ubu.inf.tfg.otrasCosas.*;

public class AportacionInicial extends Asiento {
	
	public AportacionInicial(Calendar f, double [] i, boolean enunciadoCuentas) {
		fecha = f;
		inputs = i;
		nombre = "aportacion";
	}
	
	public void generar(Calendar f, double[] inputs){
		String enunciado1 = " Cada uno de los " +(int)inputs[0]+ " socios fundadores realiza una aportación inicial en "
    			+ "efectivo a favor de la empresa por importe de " +inputs[1]+ "€ (" +inputs[0]*inputs[1]+ "€ en total). "
    			+ "Cada socio tiene una acción. La empresa ingresa el dinero recibido en su cuenta corriente. \n";
		if (enunciadoCuentas){
			enunciado1 = enunciado1 + "CUENTAS PGC: 100. Capital social; 572. Bancos e instituciones de crédito c/c vista, euros. \n";
		}
		
		enunciados.add(new Enunciado(fecha, enunciado1));

		dameCuenta(572).añadirDebe(new Anotacion(fecha, "Aportacion inicial", (inputs[0]*inputs[1]), damePrioridad(572)));	
		dameCuenta(100).añadirHaber(new Anotacion(fecha, "Aportacion inicial", (inputs[0]*inputs[1]), damePrioridad(100)));	
	}
}