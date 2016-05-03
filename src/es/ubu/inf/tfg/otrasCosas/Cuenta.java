package es.ubu.inf.tfg.otrasCosas;
import java.util.ArrayList;
import java.util.Date;

public class Cuenta {
	
	int codigo = 0;
	String nombre;
	ArrayList<Anotacion> debe = new ArrayList<Anotacion>();
	ArrayList<Anotacion> haber = new ArrayList<Anotacion>();
	int saldo;
	
	
	public Cuenta(int codigo, String nombre){
		this.codigo = codigo;
		this.nombre = nombre;
	}
	
	public void añadirDebe(Anotacion anotacion){
		debe.add(anotacion);	
	}
	
	public void añadirHaber(Anotacion anotacion){
		haber.add(anotacion);	
	}
	
	public int getSaldo(Date fecha){
		//Funcion que coje las cuentas iguales o menores a la fecha pasada y calcula el saldo
		//Saldo = DEBER = HABER??
		return saldo;
		
	}
	
}
