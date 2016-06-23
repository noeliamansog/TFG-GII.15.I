package es.ubu.inf.tfg.asientosContables;
import java.util.Calendar;

import es.ubu.inf.tfg.doc.Anotacion;
import es.ubu.inf.tfg.doc.Asiento;
import es.ubu.inf.tfg.doc.Enunciado;

/** 
 * Clase CompraMaterialNoAmortizable que implementa el asiento contable de una compra de material no amortizable.
 * 
 * @author Noelia Manso García
 */
public class CompraMaterialNoAmortizable extends Asiento {
	
	/**
	 * Gestiona las cuentas contables y el enunciado al realizar una compra de material no amortizable.
	 * @param f fecha en la que se realiza la compra
	 * @param i lista de parametros que el usuario introduce para que sea un asiento contable personalizado.
	 * @param enunciadoCuentas booleano que indica si el usuario desea que en el enunciado 
	 * 						   aparezca el nombre de las cuentas que se usan del PGC.
	 */
	public CompraMaterialNoAmortizable(Calendar f, double[] i, boolean enunciadoCuentas) {
		fecha =f;
		inputs=i;
		String compra=null;
		
		
		if (inputs[0]==0){
			compra = "un solar";
		}else{
			compra = "un local donde instalar la oficina";
		}
		
		String enunciado1 = " La empresa compra " +compra+ " por importe de " +inputs[1]+ "€. "
     			+ "Este activo no es amortizable. Se abonan " +inputs[2]+ "€ mediante transferencia y "
     			+ "quedan " +(inputs[1]-inputs[2])+ "€ pendiente de pago. Se acuerda que la deuda se pagará "
     			+ "en " +(int)inputs[3]+ " meses. \n";
		
		if (inputs[0]==0){
			if (enunciadoCuentas){
				enunciado1 = enunciado1 + "CUENTAS PGC: 210. Terrenos y bienes naturales; 572. Bancos e instituciones de crédito c/c vista, euros;";							
			}
			dameCuenta(210).añadirDebe(new Anotacion(fecha, "Terrenos y bienes naturales", inputs[1], damePrioridad(210)));
			dameCuenta(572).añadirHaber(new Anotacion(fecha, "Terrenos y bienes naturales", inputs[2], damePrioridad(572)));
		}else{
			if (enunciadoCuentas){
				enunciado1 = enunciado1 + "CUENTAS PGC: 211. Construcciones; 572. Bancos e instituciones de crédito c/c vista, euros;";	
			}
			dameCuenta(211).añadirDebe(new Anotacion(fecha, "Construcciones", inputs[1], damePrioridad(211)));
			dameCuenta(572).añadirHaber(new Anotacion(fecha, "Construcciones", inputs[2], damePrioridad(572)));
		}
		
		if (inputs[3]<12){
			if (enunciadoCuentas){
				enunciado1 = enunciado1 + " 523. Proveedores de inmovilizado a corto plazo. \n";
			}
			dameCuenta(523).añadirHaber(new Anotacion(fecha, "Proveedores inmovilizado material", (inputs[1]-inputs[2]),damePrioridad(523)));
		}else{
			if (enunciadoCuentas){
				enunciado1 = enunciado1 + " 173. Proveedores de inmovilizado a largo plazo. \n";
			}
			dameCuenta(173).añadirHaber(new Anotacion(fecha, "Proveedores inmovilizado material", (inputs[1]-inputs[2]), damePrioridad(173)));
		}
		
		enunciados.add(new Enunciado(fecha, enunciado1));
		
			
		//SE SALDAN LAS DEUDAS CON LOS VENDEDORES "Y" DIAS DESPUES
		Calendar fechaDeudas = (Calendar)fecha.clone();
		fechaDeudas.add(Calendar.MONTH, (int) +inputs[3]);
		
		String enunciado2 = " Se salda la deuda con los vendedores de " +compra+ ".\n";
		if (enunciadoCuentas){
			enunciado2 = enunciado2 + "CUENTAS PGC: 572. Bancos e instituciones de crédito c/c vista, euros.";
		}
		
		enunciados.add(new Enunciado(fechaDeudas, enunciado2));
		
		dameCuenta(572).añadirHaber(new Anotacion(fechaDeudas, "Deuda con proveedores de inmovilizado material", (inputs[1]-inputs[2]), damePrioridad(572)));
		if (inputs[3]<12){
			dameCuenta(523).añadirDebe(new Anotacion(fechaDeudas, "Deuda con proveedores de inmovilizado material", (inputs[1]-inputs[2]),damePrioridad(523)));
			enunciado2 = enunciado2 +"523. Proveedores de inmovilizado a c/p. \n";
		}else{
			dameCuenta(173).añadirDebe(new Anotacion(fechaDeudas, "Deuda con proveedores de inmovilizado material", (inputs[1]-inputs[2]), damePrioridad(173)));
			enunciado2 = enunciado2 +"173. Proveedores de inmovilizado a largo plazo. \n";
		}
	}
}