
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class CompraIntangibleAmortizable extends Asiento{

	private ArrayList <Cuenta> cuentasIntangibleAmortizable = new ArrayList<Cuenta>();
	private ArrayList<Anotacion> listaDebe1 = new ArrayList<Anotacion>();
	private ArrayList<Anotacion> listaDebe2 = new ArrayList<Anotacion>();
	private ArrayList<Anotacion> listaHaber1 = new ArrayList<Anotacion>();
	private ArrayList<Anotacion> listaDebe3 = new ArrayList<Anotacion>();
	private ArrayList<Anotacion> listaHaber2 = new ArrayList<Anotacion>();
	private ArrayList<Anotacion> listaDebe4 = new ArrayList<Anotacion>();
	private ArrayList<Anotacion> listaHaber3 = new ArrayList<Anotacion>();
	
	public CompraIntangibleAmortizable(String enunciado, Date fecha, int[] inputs, HashMap<Integer, Cuenta> todasCuentas) {
		super(enunciado, fecha, inputs, todasCuentas);
		
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
		/*inputs[0]=3000; 	//importe aplicación [X = 1000-5000]
		inputs[1]=80;		//se abona a los [Y = 60,90] días
		inputs[2]=3;		//Años amortización [W = 2-5]
		inputs[3]=3000;		//importe logotipo [X = 1000-5000] 
		inputs[4]=80;		//se abona a los [Y = 60,90] días
		inputs[5]=3;		//Años amortización [W = 2-5]*/
		
		
		enunciado = formateador.format(fecha)+"La empresa compra una aplicación informática por valor de " +inputs[0]+ "€. "
				 + "El importe de la compra se abonará a los " +inputs[1]+ " días. El software se amortiza linealmente "
				 + "en " +inputs[2]+ " años.\n"
				 + "La empresa adquiere el derecho a usar un logotipo, por lo cual paga " +inputs[3]+ "€. "
				 + "El importe de la compra se abonará a los " +inputs[4]+ " días. La propiedad industrial se amortiza linealmente "
				 + "en " +inputs[5]+ " años.\n";
	
		System.out.println(enunciado);
		
		//Cuenta1
		listaDebe1.add(new Anotacion(fecha, "", (inputs[0])));
		Cuenta cuenta1 = new Cuenta(206, "Aplicaciones informáticas", listaDebe1, null, (inputs[0])-0);
		cuentasIntangibleAmortizable.add(cuenta1);
	
	
		//Cuenta2
		listaDebe2.add(new Anotacion(fecha, "", (inputs[3])));
		Cuenta cuenta2 = new Cuenta(203, "Propiedad industrial", listaDebe2, null, (inputs[3])-0);
		cuentasIntangibleAmortizable.add(cuenta2);
	
	
		//Cuenta3
		listaHaber1.add(new Anotacion(fecha, "Proveedores Aplicaciones informáticas", (inputs[0])));
		listaHaber1.add(new Anotacion(fecha, "Proveedores Propiedad Industrial", (inputs[3])));
		Cuenta cuenta3 = new Cuenta(400, "Proveedores", null, listaHaber1, 0-(inputs[0]+inputs[3]));
		cuentasIntangibleAmortizable.add(cuenta3);
	
	
	
		// DESPUES DE "Y" DIAS (Se salda la deuda con los proveedores):
		//Cuenta4
		listaDebe3.add(new Anotacion(fecha, "Proveedores App informáticas", (inputs[0])));
		listaDebe3.add(new Anotacion(fecha, "Proveedores Propiedad Industrial", (inputs[3])));
		Cuenta cuenta4 = new Cuenta(400, "Proveedores", listaDebe3, null, (inputs[0]+inputs[3]-0));
		cuentasIntangibleAmortizable.add(cuenta4);
	
			
		//Cuenta5
		listaHaber2.add(new Anotacion(fecha, "Bancos App informáticas", (inputs[0])));	
		listaHaber2.add(new Anotacion(fecha, "Bancos Propiedad Industrial", (inputs[3])));
		Cuenta cuenta5 = new Cuenta(572, "Bancos e instituciones de crédito c/c vista, euros", null, listaHaber2, 0-(inputs[0]+inputs[3]));
		cuentasIntangibleAmortizable.add(cuenta5);
	
	
		//AL FINAL DEL AÑO (AMORTIZACIÓN):
		//Cuenta6
		listaDebe4.add(new Anotacion(fecha, "Amortización App informática", (inputs[0]/inputs[2])));
		listaDebe4.add(new Anotacion(fecha, "Amortización Propiedad Industrial", (inputs[3]/inputs[5])));
		Cuenta cuenta6 = new Cuenta(680, "Amortización del inmovilizado intangible", listaDebe3, null, ((inputs[0]/inputs[2])+(inputs[3]/inputs[5]))-0);
		cuentasIntangibleAmortizable.add(cuenta6);
	
		//Cuenta7
		listaHaber3.add(new Anotacion(fecha, "Amortización App informática", (inputs[0]/inputs[2])));
		listaHaber3.add(new Anotacion(fecha, "Amortización Propiedad Industrial", (inputs[3]/inputs[5])));
		Cuenta cuenta7 = new Cuenta(280, "Amortización acumulada del inmovilizado intangible", null, listaHaber3, ((inputs[0]/inputs[2])+(inputs[3]/inputs[5]))-0);
		cuentasIntangibleAmortizable.add(cuenta7);
	
	
	
		contabilizar(cuentasIntangibleAmortizable);
	}
	
	
}
