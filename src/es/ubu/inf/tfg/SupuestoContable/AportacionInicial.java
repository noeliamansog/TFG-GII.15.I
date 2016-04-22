import java.util.Date;
import java.util.HashMap;




public class AportacionInicial {
	static Asiento<AportacionInicial> aportacionInicial;
	static Date fecha = new java.util.Date();
	static int nSocios = aportacionInicial.getSocios();
	static int aportacion = aportacionInicial.getAportacion();
	static int aportacionTotal = nSocios*aportacion;
	
	
	

	public static void main(String args[]) {
		HashMap<Integer,String> cuentas = new HashMap<Integer, String>();
		cuentas.put(100, "CapitalSocial");
		cuentas.put(572, "Bancos");
		
		
	}
	
	
	 //String[] inputs = {"2016-01-01","3","10000","30000"}; //Año, nºsocios, aportación por socio, aportación total;
	//String[][] cuentas = {{"100", "Capital Social"}, {"572", "Bancos"}};
	//List<List<String>> cantidad = new ArrayList<List<String>>();
	
	
    public static String enunciado () {        
    	return fecha+"Cada uno de los " +nSocios+ " socios fundadores realiza una aportación inicial en "
    			+ "efectivo a favor de la empresa por importe de " +aportacion+ "€ (" +aportacionTotal+ "€ en total). "
    			+ "Cada socio tiene una acción. La empresa ingresa el dinero recibido en su cuenta corriente. \n"
    			+ "CUENTAS PGC: 100. Capital social; 572. Bancos e instituciones de crédito c/c vista, euros. \n"
    			+ "Valor nominal de todas las acciones = "+aportacion+".\n";
    }
    
    public void contabilizar(HashMap<Integer, String> cuentas){
    	//Recorrer el hashMap comprobando si existe
    	
    	
    	
    	
    }
    
	
}
