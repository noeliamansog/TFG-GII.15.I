
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class CompraMercaderias extends Asiento{
	
	private ArrayList <Cuenta> cuentasMercaderias;
	private ArrayList<Anotacion> listaDebe1;
	private ArrayList<Anotacion> listaHaber1;
	private ArrayList<Anotacion> listaDebe2;
	private ArrayList<Anotacion> listaDebe3;
	private ArrayList<Anotacion> listaHaber2;

	public CompraMercaderias(String enunciado, Date fecha, int[] inputs, HashMap<Integer, Cuenta> todasCuentas) {
		super(enunciado, fecha, inputs, todasCuentas);

		
		inputs[0] =10000;	 //importe 
		inputs[1]=12;		 //IVA
		
		
		enunciado= fecha+"La empresa compra mercaderías por un importe de " +inputs[0]+ "€ más un " +inputs[1]+ "% de IVA."
				 + "[Se acuerda que el pago se realice en 30 días, se paga al contado]. \n";
		
		
		/* 400. Proveedores. 
		 * 600. Compra de mercaderías.
		 * 572. Bancos e instituciones de crédito c/c vista, euros. 
		 * 472. H.P. IVA Soportado*/
		
		
		// DEBE:
		// 600 Compra de mercaderias
		// 472 H.P IVA Soportado
		// 400 Proveedores
		
		//HABER:
		//400 Proveedores
		//570 Caja, euros
		
		//Cuenta1
		listaDebe1.add(new Anotacion(fecha, "", (inputs[0])));
		Cuenta cuenta1 = new Cuenta(600, "Compra de mercaderias", listaDebe1, null, (inputs[0])-0);
		cuentasMercaderias.add(cuenta1);
		
		//Cuenta2
		listaDebe2.add(new Anotacion(fecha, "", (inputs[1])));
		Cuenta cuenta2 = new Cuenta(472, "H.P IVA Soportado", listaDebe2, null, (inputs[1])-0);
		cuentasMercaderias.add(cuenta2);

						
		//Cuenta3
		listaHaber1.add(new Anotacion(fecha, "", (inputs[0])));
		Cuenta cuenta3 = new Cuenta(400, "Proveedores", null, listaHaber1, 0-(inputs[0]));
		cuentasMercaderias.add(cuenta3);
				
				
		// DESPUES DE 30 DIAS (Se salda la deuda con los proveedores):
		//Cuenta4
		listaDebe3.add(new Anotacion(fecha, "", (inputs[0])));
		Cuenta cuenta4 = new Cuenta(400, "Proveedores", listaDebe3, null, (inputs[0])-0);
		cuentasMercaderias.add(cuenta4);
				
		//Cuenta5
		listaHaber2.add(new Anotacion(fecha, "", (inputs[0])));		
		Cuenta cuenta5 = new Cuenta(572, "Bancos e instituciones de crédito c/c vista, euros", null, listaHaber2, 0-(inputs[0]));
		cuentasMercaderias.add(cuenta5);
		
		
		contabilizar(cuentasMercaderias);
		
	}

}
