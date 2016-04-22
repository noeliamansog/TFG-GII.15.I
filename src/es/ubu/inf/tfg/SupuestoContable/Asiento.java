import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class Asiento {
	protected String enunciado;
	protected Date fecha;
	protected int [] inputs; //A veces será nº otras veces sera string
	protected ArrayList<Cuenta> cuentas;
	protected ArrayList<Array[]> cantidad = new ArrayList<Array[]>();
	HashMap<Integer,String> todasCuentas = new HashMap<Integer, String>();
	
	
	public Asiento (String enunciado, Date fecha, int [] inputs, ArrayList<Cuenta> cuentas, ArrayList<Array[]> cantidad, HashMap<Integer,String> todasCuentas){
		this.enunciado = enunciado;
		this.inputs =inputs;
		this.cuentas = cuentas;
		this.cantidad = cantidad;
		this.todasCuentas=todasCuentas;
	}
	
	public String getEnunciado() { 
		return enunciado;  
	}
	public Date getFecha() { 
		return fecha;  
	}
	public int [] getInputs(){
		return inputs;
	}
	public ArrayList<Cuenta> getcuentas(){
		return cuentas;
	}
	public ArrayList<Array[]> getCantidad(){
		return cantidad;
	}
	public HashMap<Integer,String> getTodasCuentas(){
		return todasCuentas;
	}
	
	public void contabilizar(ArrayList<Cuenta> cuentas, HashMap<Integer,String> todasCuentas){
		for(int i=0; i<cuentas.size(); i++) {
			if ((todasCuentas.containsKey(cuentas.get(i).codigo))) {
				//Si ya tiene la cuenta que hago?
			}else{
				todasCuentas.put(cuentas.get(i).codigo, cuentas.get(i).nombre);
			}
		}
		
	}

}
