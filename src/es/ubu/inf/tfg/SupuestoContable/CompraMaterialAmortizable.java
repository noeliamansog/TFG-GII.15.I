
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class CompraMaterialAmortizable extends Asiento{
	
	private ArrayList <Cuenta> cuentasMaterialAmortizable = new ArrayList<Cuenta>();
	private ArrayList<Anotacion> listaDebe1 = new ArrayList<Anotacion>();
	private ArrayList<Anotacion> listaHaber1 = new ArrayList<Anotacion>();
	private ArrayList<Anotacion> listaDebe2 = new ArrayList<Anotacion>();
	private ArrayList<Anotacion> listaHaber2 = new ArrayList<Anotacion>();
	private ArrayList<Anotacion> listaDebe3 = new ArrayList<Anotacion>();
	private ArrayList<Anotacion> listaHaber3 = new ArrayList<Anotacion>();
	
	public CompraMaterialAmortizable(String enunciado, Date fecha, int[] inputs, HashMap<Integer, Cuenta> todasCuentas) {
		super(enunciado, fecha, inputs, todasCuentas);
		
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
		/*inputs[0]= 1; 		//[Z = maquinaria, Mobiliario, Equipos para procesos de información, Elementos de transporte]
		inputs[1]= 10000; 	//Importe material [X = 5000-25000] 
		inputs[2]= 80; 		//Se abona en [Y = 60,90] dias
		inputs[3]=5;		//Se amortiza en [W = 4-10] años */
		
		
		enunciado = formateador.format(fecha)+" La empresa compra " +inputs[0]+ " por valor de " +inputs[1]+ "€. "
				 + "El importe de la compra se abonará a los " +inputs[2]+ " días. La compra "
				 + "(" +inputs[0]+ ") se amortiza linealmente en " +inputs[3]+ " años.\n";
		
		System.out.println(enunciado);
		
		//Cuenta1
		listaDebe1.add(new Anotacion(fecha, "", (inputs[1])));
		switch (inputs[0]){
		case 0: Cuenta cuenta1a = new Cuenta(213, "Maquinaria", listaDebe1, null, (inputs[1])-0);
				cuentasMaterialAmortizable.add(cuenta1a);
				break;
		case 1: Cuenta cuenta1b = new Cuenta(216, "Mobiliario", listaDebe1, null, (inputs[1])-0); 
				cuentasMaterialAmortizable.add(cuenta1b);
				break;
		case 2: Cuenta cuenta1c = new Cuenta(217, "Equipos para procesos de información", listaDebe1, null, (inputs[1])-0); 
				cuentasMaterialAmortizable.add(cuenta1c);
				break;
		case 3: Cuenta cuenta1d = new Cuenta(218, "Elementos de transporte", listaDebe1, null, (inputs[1])-0); 
				cuentasMaterialAmortizable.add(cuenta1d);
				break;
		}
				
		//Cuenta2
		listaHaber1.add(new Anotacion(fecha, "", (inputs[1])));
		Cuenta cuenta2 = new Cuenta(400, "Proveedores", null, listaHaber1, 0-(inputs[1]));
		cuentasMaterialAmortizable.add(cuenta2);
		
		
		// DESPUES DE "Y" DIAS (Se salda la deuda con los proveedores):
		//Cuenta3
		listaDebe2.add(new Anotacion(fecha, "", (inputs[1])));
		Cuenta cuenta3 = new Cuenta(400, "Proveedores", listaDebe2, null, (inputs[1])-0);
		cuentasMaterialAmortizable.add(cuenta3);
		
		//Cuenta4
		listaHaber2.add(new Anotacion(fecha, "", (inputs[1])));		
		Cuenta cuenta4 = new Cuenta(572, "Bancos e instituciones de crédito c/c vista, euros", null, listaHaber2, 0-(inputs[1]));
		cuentasMaterialAmortizable.add(cuenta4);
		
		
		//AL FINAL DEL AÑO (AMORTIZACIÓN):
		//Cuenta5
		listaDebe3.add(new Anotacion(fecha, "", (inputs[1]/inputs[3])));
		Cuenta cuenta5 = new Cuenta(681, "Amortización del inmovilizado material", listaDebe3, null, (inputs[1]/inputs[3])-0);
		cuentasMaterialAmortizable.add(cuenta5);
		
		//Cuenta6
		listaHaber3.add(new Anotacion(fecha, "", (inputs[1]/inputs[3])));		
		Cuenta cuenta6 = new Cuenta(281, "Amortización acumulada del inmovilizado material", null, listaHaber3, 0-(inputs[1]/inputs[3]));
		cuentasMaterialAmortizable.add(cuenta6);
		
		
		contabilizar(cuentasMaterialAmortizable);

	}

}
