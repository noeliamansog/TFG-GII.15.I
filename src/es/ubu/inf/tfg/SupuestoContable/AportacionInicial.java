import java.util.ArrayList;
import java.util.Date;

public class AportacionInicial extends Asiento {
	
	private ArrayList <Cuenta> cuentasAportacion;

	public AportacionInicial(String enunciado, Date fecha, int [] inputs, ArrayList<Cuenta> todasCuentas) {
		super(enunciado, fecha, inputs, todasCuentas);
	
		fecha = new java.util.Date();
		inputs[0]=3; 		//nº socios
		inputs[1]=1000; 	//Aportación de cada socio
		enunciado = fecha+"Cada uno de los " +inputs[0]+ " socios fundadores realiza una aportación inicial en "
    			+ "efectivo a favor de la empresa por importe de " +inputs[1]+ "€ (" +(inputs[0])*(inputs[1])+ "€ en total). "
    			+ "Cada socio tiene una acción. La empresa ingresa el dinero recibido en su cuenta corriente. \n"
    			+ "CUENTAS PGC: 100. Capital social; 572. Bancos e instituciones de crédito c/c vista, euros. \n"
    			+ "Valor nominal de todas las acciones = "+inputs[1]+".\n";
		

		cuentasAportacion.add(new Cuenta (100, "CapitalSocial",(inputs[0])*(inputs[1]), false, fecha));
		cuentasAportacion.add(new Cuenta (572, "Bancos",(inputs[0])*(inputs[1]), true, fecha));
	 
		
		contabilizar(todasCuentas, cuentasAportacion);
		
	}
}
