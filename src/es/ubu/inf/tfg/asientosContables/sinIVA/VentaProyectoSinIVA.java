package es.ubu.inf.tfg.asientosContables.sinIVA;
import java.util.Calendar;

import es.ubu.inf.tfg.doc.Anotacion;
import es.ubu.inf.tfg.doc.Asiento;
import es.ubu.inf.tfg.doc.Enunciado;

/** 
 * Clase VentaProyectoSinIVA que implementa el asiento contable de una venta de un proyecto sin IVA.
 *
 * @author Noelia Manso García
 */
public class VentaProyectoSinIVA extends Asiento {

	/**
	 * Gestiona las cuentas contables y el enunciado al realizar una venta de un proyecto sin IVA.
	 * @param f fecha en la que se realiza la venta.
	 * @param i lista de parametros que el usuario introduce para que sea un asiento contable personalizado.
	 * @param enunciadoCuentas booleano que indica si el usuario desea que en el enunciado 
	 * 						   aparezca el nombre de las cuentas que se usan del PGC.
	 */
	public VentaProyectoSinIVA(Calendar f, double[] i, boolean enunciadoCuentas) {
		fecha =f;
		inputs=i;
		
		String enunciado1 = null;
		if((int)inputs[1]>0){
			enunciado1 = " La empresa entrega un proyecto a un cliente, por el cual factura " +inputs[0]+ "€.  "
				+ "Se acuerda que el cliente pague en " +(int)inputs[1]+ " días. \n";		
		}
		if((int)inputs[1]==0){
			enunciado1 = " La empresa entrega un proyecto a un cliente, por el cual factura " +inputs[0]+ "€. "
					+ "Se paga al contado. \n";
		}
		if (enunciadoCuentas){
			enunciado1 = enunciado1+ "CUENTAS PGC: 430. Clientes; 705. Prestaciones de servicios.\n";  
		}
		enunciados.add(new Enunciado(fecha, enunciado1));
		
		dameCuenta(430).añadirDebe(new Anotacion(fecha, "Clientes proyecto", inputs[0], damePrioridad(430)));
		dameCuenta(705).añadirHaber(new Anotacion(fecha, "Prestaciones de servicios", inputs[0], damePrioridad(705)));
			
		if((int)inputs[1]>0){
			//SE SALDAN LAS DEUDAS CON LA EMPRESA "Y" DIAS DESPUES:
			Calendar fechaDeudas = (Calendar)fecha.clone();
			fechaDeudas.add(Calendar.DAY_OF_YEAR, (int) +inputs[1]);
		
			String enunciado2 = " Los clientes saldan su deuda del proyecto con la empresa.\n";
			if (enunciadoCuentas){
				enunciado2 = enunciado2 + "CUENTAS PGC: 430. Clientes; 572. Bancos e instituciones de crédito c/c vista, euros.\n";
			}
			
			enunciados.add(new Enunciado(fechaDeudas, enunciado2));

			dameCuenta(572).añadirDebe(new Anotacion(fechaDeudas, "Clientes por prestaciones de servicios", inputs[0], damePrioridad(572)));
			dameCuenta(430).añadirHaber(new Anotacion(fechaDeudas, "Prestaciones de servicios", inputs[0], damePrioridad(430)));	
		}
	}
}
