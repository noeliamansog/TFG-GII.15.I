package es.ubu.inf.tfg.asientosContables;

import java.util.Calendar;

import es.ubu.inf.tfg.otrasCosas.Anotacion;
import es.ubu.inf.tfg.otrasCosas.Asiento;
import es.ubu.inf.tfg.otrasCosas.Enunciado;

public class Cierre extends Asiento{

	public Cierre(Calendar f, double[] i, boolean enunciadoCuentas) {
		fecha = f;
		inputs = i;
		
		Calendar fechaFinAno = (Calendar)fecha.clone();
		fechaFinAno.set(fechaFinAno.get(Calendar.YEAR), 11, 31);
		
		String enunciado1 = " Se cierra el ejercicio (contabilizando las amortizaciones pertinentes "
				+ " y realizando la liquidación del IVA), y deja a deber a Hacienda el impuesto de sociedades " +inputs[0]+ "% del beneficio).\n";
		if (enunciadoCuentas){
			enunciado1 = enunciado1 + "CUENTAS PGC: 129. Resultados del ejercicio; 12. Resultados pendientes de aplicación.\n";
								  /*+ "CUENTAS PGC: 477. H.P. IVA repercutido. 4700. H.P. deudor por IVA. "
		 	  						+ "472. H.P. IVA soportado. 4750 H.P Acreedor por IVA. \n";*/
		}

		enunciados.add(new Enunciado(fechaFinAno, enunciado1));
		
		double resultado = dameCuenta(129).getSaldo(fechaFinAno);
		
		if(resultado>=0){
			dameCuenta(129).añadirDebe(new Anotacion(fechaFinAno, "Fin de año "+fechaFinAno.get(Calendar.YEAR) , resultado, damePrioridad(129)));
			dameCuenta(12).añadirHaber(new Anotacion(fechaFinAno,"Fin de año "+fechaFinAno.get(Calendar.YEAR), resultado, damePrioridad(12)));
		}else{
			dameCuenta(129).añadirHaber(new Anotacion(fechaFinAno, "Fin de año "+fechaFinAno.get(Calendar.YEAR), -resultado, damePrioridad(129)));
			dameCuenta(12).añadirDebe(new Anotacion(fechaFinAno,"Fin de año "+fechaFinAno.get(Calendar.YEAR), -resultado, damePrioridad(12)));
			
		}
		
		double saldoIVARepercutido = dameCuenta(477).getSaldo(fechaFinAno);
		double saldoIVASoportado = dameCuenta(472).getSaldo(fechaFinAno);
		
		dameCuenta(477).añadirDebe(new Anotacion(fechaFinAno, "H.P. IVA repercutido ", saldoIVARepercutido, damePrioridad(477)));
		dameCuenta(472).añadirHaber(new Anotacion(fechaFinAno, "H.P. IVA soportado ", saldoIVASoportado, damePrioridad(472)));
		
		if(saldoIVARepercutido > saldoIVASoportado){
			dameCuenta(4750).añadirHaber(new Anotacion(fechaFinAno, "H.P Acreedor por IVA", saldoIVARepercutido-saldoIVASoportado, damePrioridad(4750)));
			
		}else{
			dameCuenta(4700).añadirDebe(new Anotacion(fechaFinAno, "H.P. deudor por IVA ", saldoIVASoportado-saldoIVARepercutido, damePrioridad(4700)));	
		}
	}

}
