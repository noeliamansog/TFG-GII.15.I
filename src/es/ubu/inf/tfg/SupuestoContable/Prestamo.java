import java.util.Date;
import java.util.HashMap;


public class Prestamo {
	static Asiento<Prestamo> prestamo;
	static Date fecha = new java.util.Date();
	static int importe = prestamo.getPrestamo();
	static String tipoPrestamo = prestamo.getTipoPrestamo();
	static String mensualAnual;
	static String mesAno;
	static int añosPrestamo = prestamo.getAñosPrestamo();
	static int interesPrestamo = prestamo.getInteresPrestamo();
	
	public static void main(String args[]) {
		
		if(prestamo.getMesual() == true){
			mensualAnual = "mensual";
			mesAno = "mes";
		}else{
			mensualAnual="anual";
			mesAno ="año";
		}
		
		
		HashMap<Integer,String> cuentas = new HashMap<Integer, String>();
		cuentas.put(17, "Deudas");
		cuentas.put(572, "Bancos");
		cuentas.put(662, "Intereses");
		
	}
	
	public static String prestamo () { 	
    	return fecha+"La empresa obtiene un préstamo por importe de " +importe+ "€, que se ingresa en la cuenta "
    			+ "corriente de la que es titular la empresa. El préstamo se devolverá en cuotas de " +tipoPrestamo+" "
    			+mensualAnual+ "constantes, en " +añosPrestamo+ " años, a un tipo de interés fijo del " +interesPrestamo+ "%."
    			+ "El primer pago se realizará al cabo de un "+mesAno+" desde la concesión del prestamo. \n"
    			+ "CUENTAS PGC: 17. Deudas a largo plazo por préstamos recibidos y otros conceptos; 572. Bancos "
    			+ "e instituciones de crédito c/c vista, euros; 662. Intereses de deudas";
    }

}
