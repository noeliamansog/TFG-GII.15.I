import java.util.ArrayList;

public class Cuenta {
	
	int codigo = 0;
	String nombre;
	ArrayList<Anotacion> deber;
	ArrayList<Anotacion> haber;
	int saldo;
	
	public Cuenta(int codigo, String nombre, ArrayList<Anotacion> deber, ArrayList<Anotacion> haber, int saldo){
		this.codigo = codigo;
		this.nombre = nombre;
		this.deber = deber;
		this.haber = haber;
		this.saldo = saldo;
	}
	
}
