import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;



public class AportacionInicial extends Asiento {


	public AportacionInicial(String enunciado, Date fecha, int [] inputs, ArrayList<Cuenta> cuentas, ArrayList<Array[]> cantidad, Asiento aportacionInicial, HashMap<Integer,String> todasCuentas) {
		super(enunciado, fecha, inputs, cuentas, cantidad, todasCuentas);
			
		fecha = new java.util.Date();
		inputs[0]=3; 		//nº socios
		inputs[1]=1000; 	//Aportación de cada socio
		enunciado = fecha+"Cada uno de los " +inputs[0]+ " socios fundadores realiza una aportación inicial en "
    			+ "efectivo a favor de la empresa por importe de " +inputs[1]+ "€ (" +(inputs[0])*(inputs[1])+ "€ en total). "
    			+ "Cada socio tiene una acción. La empresa ingresa el dinero recibido en su cuenta corriente. \n"
    			+ "CUENTAS PGC: 100. Capital social; 572. Bancos e instituciones de crédito c/c vista, euros. \n"
    			+ "Valor nominal de todas las acciones = "+inputs[1]+".\n";
		


		cuentas.add(new Cuenta(100, "CapitalSocial",(inputs[0])*(inputs[1]), false, fecha));
		cuentas.add(new Cuenta(572, "Bancos",(inputs[0])*(inputs[1]), true, fecha));
		
		
		contabilizar(cuentas, todasCuentas);
		
	}
		
		/*
		Asiento aportacionInicial = new Asiento(enunciado, fecha, inputs, cuentas, cantidad, todasCuentas);
		this.aportacionInicial = aportacionInicial;
		aportacionInicial.fecha = new java.util.Date();
		aportacionInicial.inputs[0]=3; 		//nº socios
		aportacionInicial.inputs[1]=1000; 	//Aportación de cada socio
		
		aportacionInicial.enunciado = fecha+"Cada uno de los " +inputs[0]+ " socios fundadores realiza una aportación inicial en "
    			+ "efectivo a favor de la empresa por importe de " +inputs[1]+ "€ (" +(inputs[0])*(inputs[1])+ "€ en total). "
    			+ "Cada socio tiene una acción. La empresa ingresa el dinero recibido en su cuenta corriente. \n"
    			+ "CUENTAS PGC: 100. Capital social; 572. Bancos e instituciones de crédito c/c vista, euros. \n"
    			+ "Valor nominal de todas las acciones = "+inputs[1]+".\n";
    	*/
	
	
}


