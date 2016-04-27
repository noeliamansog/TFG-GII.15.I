
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Prestamo extends Asiento {
	
	private ArrayList <Cuenta> cuentasPrestamo = new ArrayList<Cuenta>();;
	private ArrayList<Anotacion> listaDebe1 = new ArrayList<Anotacion>();;
	private ArrayList<Anotacion> listaHaber1 = new ArrayList<Anotacion>();
	private ArrayList<Anotacion> listaDebe2 = new ArrayList<Anotacion>();
	private ArrayList<Anotacion> listaDebe3 = new ArrayList<Anotacion>();
	private ArrayList<Anotacion> listaHaber2 = new ArrayList<Anotacion>();

	public Prestamo(String enunciado, Date fecha, int [] inputs, HashMap<Integer, Cuenta> todasCuentas) {
		super(enunciado, fecha, inputs, todasCuentas);
	
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
		/*inputs[0]=1000; 		//importePrestamo [50000-150000]
		inputs[1]=1; 			//tipo de Prestamo [amortización o pago]
		inputs[2]=1;			//[mensuales o anuales]
		inputs[3]=5;			//años de prestamo [10-20]
		inputs[4]=12;			//interes del prestamo [1-10]%
		inputs[5]=1;			//[mes o año]*/
		enunciado = formateador.format(fecha)+"La empresa obtiene un préstamo por importe de " +inputs[0]+ "€, que se ingresa en la cuenta "
    			+ "corriente de la que es titular la empresa. El préstamo se devolverá en cuotas de " +inputs[1]+" "
    			+inputs[2]+ "constantes, en " +inputs[3]+ " años, a un tipo de interés fijo del " +inputs[4]+ "%."
    			+ "El primer pago se realizará al cabo de un "+inputs[5]+" desde la concesión del prestamo. \n"
    			+ "CUENTAS PGC: 17. Deudas a largo plazo por préstamos recibidos y otros conceptos; 572. Bancos "
    			+ "e instituciones de crédito c/c vista, euros; 662. Intereses de deudas";
		
		System.out.println(enunciado);
		
		//CUANDO ME DAN EL PRESTAMO:
		//Cuenta 1
		listaDebe1.add(new Anotacion(fecha, "", inputs[0]));
		Cuenta cuenta1 = new Cuenta(572, "Bancos e instituciones de crédito c/c vista, euros", listaDebe1, null, (inputs[0])-0);
		cuentasPrestamo.add(cuenta1);
		
		//Cuenta 2
		listaHaber1.add(new Anotacion(fecha, "", inputs[0]));
		Cuenta cuenta2 = new Cuenta(17, "Deudas a largo plazo por préstamos recibidos y otros conceptos", null, listaHaber1, 0-(inputs[0]));
		cuentasPrestamo.add(cuenta2);
		
		//PAGO DE UNA CUOTA:
		// ¡¡¡¡¡Calcular segun el tipo de prestamo (amortización o pago) la cuota a pagar por año!!!!!
		//Cuenta3
		listaDebe2.add(new Anotacion(fecha, "", inputs[0]));
		Cuenta cuenta3 = new Cuenta(17, "Deudas a largo plazo por préstamos recibidos y otros conceptos", listaDebe2, null, (inputs[0])-0);
		cuentasPrestamo.add(cuenta3);
		
		//Cuenta4
		listaDebe3.add(new Anotacion(fecha, "", inputs[4]));
		Cuenta cuenta4 = new Cuenta(662, "Intereses de deudas", listaDebe3, null, (inputs[4])-0);
		cuentasPrestamo.add(cuenta4);
		
		//Cuenta5
		listaHaber2.add(new Anotacion(fecha, "", inputs[0]));
		Cuenta cuenta5 = new Cuenta(17, "Bancos e instituciones de crédito c/c vista, euros", null, listaHaber2, 0-(inputs[0]));
		cuentasPrestamo.add(cuenta5);
		

		
		contabilizar(cuentasPrestamo);
		
	}
}
