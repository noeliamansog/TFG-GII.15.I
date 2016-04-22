import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Asiento {
	protected String enunciado;
	protected Date fecha;
	protected int [] inputs;
	HashMap<Integer, Cuenta> todasCuentas;

	
	public Asiento (String enunciado, Date fecha, int [] inputs, HashMap<Integer, Cuenta> todasCuentas){
		this.enunciado = enunciado;
		this.inputs =inputs;
		this.todasCuentas = todasCuentas;
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
	public HashMap<Integer, Cuenta> getTodasCuentas(){
		return todasCuentas;
	}
	
	public void contabilizar(ArrayList<Cuenta> cuentas){
		Cuenta cuenta = null;
		for(int i=0; i<cuentas.size(); i++) {
			cuenta = cuentas.get(i);
			if(!(todasCuentas.containsKey(cuenta.codigo))){
				todasCuentas.put(cuenta.codigo,cuenta);
			}else{
				todasCuentas.get(cuenta.codigo).deber.addAll(cuenta.deber);
				todasCuentas.get(cuenta.codigo).haber.addAll(cuenta.haber);
			}
		}
	}
}
