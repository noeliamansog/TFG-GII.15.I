package es.ubu.inf.tfg.asientosContables;
import java.util.Calendar;

import es.ubu.inf.tfg.doc.Anotacion;
import es.ubu.inf.tfg.doc.Asiento;
import es.ubu.inf.tfg.doc.Enunciado;
import es.ubu.inf.tfg.main.Main;

public class VentaProyecto extends Asiento {

	public VentaProyecto(Calendar f, double[] i, boolean enunciadoCuentas) {
		fecha =f;
		inputs=i;
		
		String enunciado1 = " La empresa entrega un proyecto a un cliente, por el cual factura " +inputs[0]+ "€ "
				+ "más un " +Main.IVA+ "% de IVA. Se acuerda que el cliente pague en " +(int)inputs[1]+ " días. El cliente paga al contado. \n";
		if (enunciadoCuentas){
			enunciado1 = enunciado1+ "CUENTAS PGC: 430. Clientes; 705. Prestaciones de servicios; 477. H.P. IVA Repercutido.\n";  
		}
			
		enunciados.add(new Enunciado(fecha, enunciado1));
		
		dameCuenta(430).añadirDebe(new Anotacion(fecha, "Clientes proyecto", inputs[0]*(1+Main.IVA/100), damePrioridad(430)));
		dameCuenta(705).añadirHaber(new Anotacion(fecha, "Prestaciones de servicios", inputs[0], damePrioridad(705)));
		dameCuenta(477).añadirHaber(new Anotacion(fecha, "IVA proyecto", (inputs[0]*Main.IVA)/100, damePrioridad(477)));
			
		
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
