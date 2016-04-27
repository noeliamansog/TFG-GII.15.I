
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class CompraMatrialNoAmortizable extends Asiento {
	
	private ArrayList <Cuenta> cuentasCompraMaterialNoAmortizable = new ArrayList<Cuenta>();
	private ArrayList<Anotacion> listaDebe1 = new ArrayList<Anotacion>();
	private ArrayList<Anotacion> listaHaber1 = new ArrayList<Anotacion>();
	private ArrayList<Anotacion> listaHaber2 = new ArrayList<Anotacion>();
	private ArrayList<Anotacion> listaDebe2 = new ArrayList<Anotacion>();
	private ArrayList<Anotacion> listaHaber3 = new ArrayList<Anotacion>();
	
	public CompraMatrialNoAmortizable(String enunciado, Date fecha, int[] inputs, HashMap<Integer, Cuenta> todasCuentas) {
		super(enunciado, fecha, inputs, todasCuentas);
		
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
		/*inputs[0]=1; 		//[Z = un solar, un local donde instalar la oficina]
		inputs[1]=100000; 	//Importe compra [X = 50000-150000] 
		inputs[2]=50000;	//Importe abonado (<inputs[1])
		inputs[3]= 3; 		//Meses [W = 3, 6, 9, 18, 20] */
		
		enunciado = formateador.format(fecha)+" La empresa compra " +inputs[0]+ " por importe de " +inputs[1]+ "€. "
     			+ "Este activo no es amortizable. Se abonan " +inputs[2]+ "€ mediante transferencia y "
     			+ "quedan " +(inputs[1]-inputs[2])+ "€ pendiente de pago. Se acuerda que la deuda se pagará "
     			+ "en " +inputs[3]+ " meses. \n";
		
		System.out.println(enunciado);
		
		//Cuenta1
		listaDebe1.add(new Anotacion(fecha, "", inputs[1]));
		if (inputs[0]==1){
			Cuenta cuenta1 = new Cuenta(210, "Terrenos y bienes naturales", listaDebe1, null, (inputs[1])-0);
			cuentasCompraMaterialNoAmortizable.add(cuenta1);
		}else{
			Cuenta cuenta1 = new Cuenta(211, "Construcciones", listaDebe1, null, (inputs[1])-0);
			cuentasCompraMaterialNoAmortizable.add(cuenta1);
		}
		
		
		//Cuenta2
		listaHaber1.add(new Anotacion(fecha, "", inputs[1]));		
		Cuenta cuenta2 = new Cuenta(572, "Bancos e instituciones de crédito c/c vista, euros", null, listaHaber1, 0-(inputs[1]));
		cuentasCompraMaterialNoAmortizable.add(cuenta2);
				
				
		//Cuenta3
		listaHaber2.add(new Anotacion(fecha, "", (inputs[1]-inputs[2])));
		if (inputs[3]<12){
			Cuenta cuenta3 = new Cuenta(523, " Proveedores de inmovilizado a corto plazo (PASIVO CORRIENTE)", null, listaHaber2, 0-((inputs[1]-inputs[2])));
			cuentasCompraMaterialNoAmortizable.add(cuenta3);
		}else{
			Cuenta cuenta3 = new Cuenta(173, "Proveedores de inmovilizado a largo plazo (PASIVO NO CORRIENTE)", null, listaHaber2, 0-((inputs[1]-inputs[2])));
			cuentasCompraMaterialNoAmortizable.add(cuenta3);
		}
		
		
		// DESPUES DE "Y" DIAS (Se salda la deuda con los proveedores):
		//Cuenta4
		listaDebe2.add(new Anotacion(fecha, "", (inputs[1]-inputs[2])));
		Cuenta cuenta4 = new Cuenta(400, "Proveedores", listaDebe2, null, (inputs[1]-inputs[2])-0);
		cuentasCompraMaterialNoAmortizable.add(cuenta4);
		
		//Cuenta5
		listaHaber3.add(new Anotacion(fecha, "", (inputs[1]-inputs[2])));		
		Cuenta cuenta5 = new Cuenta(572, "Bancos e instituciones de crédito c/c vista, euros", null, listaHaber3, 0-(inputs[1]-inputs[2]));
		cuentasCompraMaterialNoAmortizable.add(cuenta5);
		
		
		contabilizar(cuentasCompraMaterialNoAmortizable);
				
	
	}

}
