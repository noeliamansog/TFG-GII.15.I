import java.util.Date;

public class Cuenta {
	
	int codigo = 0;
	String nombre;
	int cantidad;
	boolean debe;
	Date fecha;
	
	public Cuenta(int codigo, String nombre, int cantidad, boolean debe, Date fecha){
		this.codigo = codigo;
		this.nombre =nombre;
		this.cantidad = cantidad;
		this.debe = debe;
		this.fecha = fecha;
	}
	
}
