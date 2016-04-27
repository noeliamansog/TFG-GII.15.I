import java.util.Calendar;

public class Asiento extends SupuestoContable {
	protected String enunciado;
	protected Calendar fecha;
	protected int [] inputs;

	
	public Cuenta dameCuenta(int codigo){
		Cuenta cuenta;
		if(todasCuentas.containsKey(codigo)){
			cuenta = todasCuentas.get(codigo);
		}else{
			cuenta = new Cuenta(codigo, todosNombresCuentas.get(codigo));
			todasCuentas.put(codigo, cuenta);		
		}
		return cuenta;
	}
}
