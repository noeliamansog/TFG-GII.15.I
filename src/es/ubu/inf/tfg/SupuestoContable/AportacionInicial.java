import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AportacionInicial extends Asiento {
	
	public AportacionInicial(Calendar fecha, int [] inputs) {
		
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");

		enunciado = formateador.format(fecha.getTime())+" Cada uno de los " +inputs[0]+ " socios fundadores realiza una aportación inicial en "
    			+ "efectivo a favor de la empresa por importe de " +inputs[1]+ "€ (" +(inputs[0])*(inputs[1])+ "€ en total). "
    			+ "Cada socio tiene una acción. La empresa ingresa el dinero recibido en su cuenta corriente. \n"
    			+ "CUENTAS PGC: 100. Capital social; 572. Bancos e instituciones de crédito c/c vista, euros. \n"
    			+ "Valor nominal de todas las acciones = "+inputs[1]+".\n";
		
		System.out.println(enunciado);

		dameCuenta(527).añadirDebe(new Anotacion(fecha, "Aportacion inicial", (inputs[0]*inputs[1])));
		
		dameCuenta(100).añadirHaber(new Anotacion(fecha, "Aportacion inicial", (inputs[0]*inputs[1])));		
	}
}