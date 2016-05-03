
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Cierre extends Asiento{

	public Cierre(Calendar fecha, int[] inputs) {
		
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
		Calendar fechaFinAno = (Calendar)fecha.clone();
		fechaFinAno.set(fechaFinAno.get(Calendar.YEAR), 11, 31);
		
		enunciado = formateador.format(fechaFinAno.getTime())+" Se cierra el ejercicio (contabilizando las amortizaciones pertinentes "
				+ " y realizando la liquidación del IVA), y deja a deber a Hacienda el impuesto de sociedades " +inputs[0]+ "% del beneficio).\n"
		 		+ "CUENTAS PGC: 477. H.P. IVA repercutido. 4700. H.P. deudor por IVA. "
		 		+ "472. H.P. IVA soportado. 4750 H.P Acreedor por IVA. \n";
		
		System.out.println(enunciado);
		
		//EN ESTA CLASE TENGO QUE CREAR EL BALANCE Y LA CUENTA DE RESELUTADO.. BLABLABALBLA

		
		dameCuenta(477).añadirDebe(new Anotacion(fechaFinAno, "H.P. IVA repercutido ", inputs[0]));
		dameCuenta(4700).añadirDebe(new Anotacion(fechaFinAno, "H.P. deudor por IVA ", inputs[0]));
		
		dameCuenta(472).añadirHaber(new Anotacion(fechaFinAno, "H.P. IVA soportado ", inputs[0]));
		dameCuenta(4750).añadirHaber(new Anotacion(fechaFinAno, "H.P Acreedor por IVA", inputs[0]));
		
	}
	

}
