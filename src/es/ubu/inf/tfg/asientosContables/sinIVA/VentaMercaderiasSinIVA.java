package es.ubu.inf.tfg.asientosContables.sinIVA;
import java.util.Calendar;

import es.ubu.inf.tfg.otrasCosas.Anotacion;
import es.ubu.inf.tfg.otrasCosas.Asiento;
import es.ubu.inf.tfg.otrasCosas.Enunciado;

public class VentaMercaderiasSinIVA extends Asiento {

	public VentaMercaderiasSinIVA(Calendar f, double[] i, boolean enunciadoCuentas) {
		fecha =f;
		inputs=i;
		
		String enunciado1 = " La empresa efectúa una venta de mercaderías por importe de " +inputs[0]+ "€. " 
				 + "Se acuerda que el cliente pague en " +(int)inputs[1]+ " días. El cliente paga al contado. \n";
		if (enunciadoCuentas){
			enunciado1 = enunciado1 + "CUENTAS PGC: 430. Clientes; 700. Venta de mercaderías. \n";  
		}
		
		enunciados.add(new Enunciado(fecha, enunciado1));

		dameCuenta(430).añadirDebe(new Anotacion(fecha, "Clientes mercaderías", inputs[0], damePrioridad(430)));
		dameCuenta(700).añadirHaber(new Anotacion(fecha, "Ventas mercaderías", inputs[0], damePrioridad(700)));
			
		
		//SE SALDAN LAS DEUDAS CON LA EMPRESA "Y" DIAS DESPUES:
		Calendar fechaDeudas = (Calendar)fecha.clone();
		fechaDeudas.add(Calendar.DAY_OF_YEAR, (int) +inputs[1]);
		
		String enunciado2 = " Los clientes saldan su deuda de mercaderías con la empresa.\n";
		if (enunciadoCuentas){
			enunciado2 = enunciado2 + "CUENTAS PGC: 430. Clientes; 572. Bancos e instituciones de crédito c/c vista, euros.\n";
		}
		
		enunciados.add(new Enunciado(fechaDeudas, enunciado2));

		dameCuenta(572).añadirDebe(new Anotacion(fechaDeudas, "Clientes por venta de mercaderias", inputs[0], damePrioridad(572)));
		dameCuenta(430).añadirHaber(new Anotacion(fechaDeudas, "Venta de mercaderias", inputs[0], damePrioridad(430)));
	}
}
