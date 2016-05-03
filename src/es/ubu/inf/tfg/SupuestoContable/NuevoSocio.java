import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NuevoSocio extends Asiento {

	public NuevoSocio(Calendar fecha, int[] inputs) {

		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");

		enunciado = formateador.format(fecha.getTime())+"Se incorpora un nuevo socio a la empresa, el cual aporta " +inputs[0]+ "€ en efectivo "
				 + "(los cuales se ingresan en cuenta corriente) a cambio de una acción de nueva emisión. \n"
				 + "CUENTAS PGC:\n";
		
		System.out.println(enunciado);
		
	}

}
