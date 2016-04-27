import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CompraIntangibleNoAmortizable extends Asiento {

	public CompraIntangibleNoAmortizable(Calendar fecha, int[] inputs) {
		
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
	
		enunciado = formateador.format(fecha.getTime())+"La empresa compra a una Administración Pública el derecho de explotación de un terreno "
		+ "por valor de " + inputs[0] + ". Se paga al contado. \n"
		+ "CUENTAS PGC: 202. Concesiones administrativas. 572. Bancos e instituciones de crédito c/c vista, euros.\n";
		
		System.out.println(enunciado);

		
		dameCuenta(202).añadirDebe(new Anotacion(fecha, "Concesiones administrativas", inputs[0]));
		dameCuenta(527).añadirHaber(new Anotacion(fecha, "Bancos Administración Pública", inputs[0]));
		
	}

}
