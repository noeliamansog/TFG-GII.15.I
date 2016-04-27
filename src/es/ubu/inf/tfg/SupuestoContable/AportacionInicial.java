
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class AportacionInicial extends Asiento {
	
	private ArrayList <Cuenta> cuentasAportacion = new ArrayList<Cuenta>();
	private ArrayList<Anotacion> listaDebe = new ArrayList<Anotacion>();
	private ArrayList<Anotacion> listaHaber = new ArrayList<Anotacion>();

	public AportacionInicial(String enunciado, Date fecha, int [] inputs, HashMap<Integer, Cuenta> todasCuentas) {
		super(enunciado, fecha, inputs, todasCuentas);
		
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
		//inputs[0]=3; 		//nº socios [X = 2-10] 
		//inputs[1]=1000; 	//Aportación de cada socio [Y = 5000-50000] 
		enunciado = formateador.format(fecha)+" Cada uno de los " +inputs[0]+ " socios fundadores realiza una aportación inicial en "
    			+ "efectivo a favor de la empresa por importe de " +inputs[1]+ "€ (" +(inputs[0])*(inputs[1])+ "€ en total). "
    			+ "Cada socio tiene una acción. La empresa ingresa el dinero recibido en su cuenta corriente. \n"
    			+ "CUENTAS PGC: 100. Capital social; 572. Bancos e instituciones de crédito c/c vista, euros. \n"
    			+ "Valor nominal de todas las acciones = "+inputs[1]+".\n";
		
		System.out.println(enunciado);

		//Cuenta 1
		listaDebe.add(new Anotacion(fecha, "", (inputs[0]*inputs[1])));
		Cuenta cuenta1 = new Cuenta(527, "Bancos e instituciones de crédito c/c vista, euros", listaDebe, null, (inputs[0]*inputs[1])-0);
		cuentasAportacion.add(cuenta1);
		
		
		//Cuenta 2
		listaHaber.add(new Anotacion(fecha, "", (inputs[0]*inputs[1])));	
		Cuenta cuenta2 = new Cuenta(100, "Capital Social", null, listaHaber, 0-(inputs[0]*inputs[1]));
		cuentasAportacion.add(cuenta2);
		
	
		contabilizar(cuentasAportacion);
		
	}
}
