import java.util.ArrayList;
import java.util.Date;

public class Asiento {
	protected String enunciado;
	protected Date fecha;
	protected int [] inputs; //A veces será nº otras veces sera string
	protected ArrayList<Cuenta> cuentas;

	
	public Asiento (String enunciado, Date fecha, int [] inputs, ArrayList<Cuenta> cuentas){
		this.enunciado = enunciado;
		this.inputs =inputs;
		this.cuentas = cuentas;
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
	
	public void contabilizar(ArrayList<Cuenta> todasCuentas, ArrayList<Cuenta> cuentasConcretas){
		for(int i=0; i<todasCuentas.size(); i++) {
			for(int j=0; j<cuentasConcretas.size(); j++) {
				if (todasCuentas.get(i).codigo==cuentasConcretas.get(j).codigo) {
					//Si ya tiene la cuenta que hago
				}else{
					todasCuentas.add(cuentasConcretas.get(j));
				}
			}
		}
	}
	public void debeHaber(ArrayList<Cuenta> todasCuentas, ArrayList<Cuenta> deber, ArrayList<Cuenta> haber){
		for(int i=0; i<todasCuentas.size(); i++) {
			if(todasCuentas.get(i).debe==true){
				deber.add(todasCuentas.get(i));
			}else{
				haber.add(todasCuentas.get(i));			
			}
		}
		
	}

}
