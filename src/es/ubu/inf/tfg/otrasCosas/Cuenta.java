package es.ubu.inf.tfg.otrasCosas;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class Cuenta {
	
	int codigo = 0;
	String nombre;
	ArrayList<Anotacion> debe = new ArrayList<Anotacion>();
	ArrayList<Anotacion> haber = new ArrayList<Anotacion>();
	protected HashMap<Integer, Boolean> saldoDebeMenosHaber = new HashMap<Integer, Boolean>();
	protected HashMap<Integer, Integer> prioridades = new HashMap<Integer, Integer>();
	
	
	public Cuenta(int codigo, String nombre){
		this.codigo = codigo;
		this.nombre = nombre;
		inicializarPrioridades();
		inicializarSaldoDebeMenosHaber();
	}
	
	public int getPrioridad(){
		return prioridades.get(codigo);
	}
	
	public void añadirDebe(Anotacion anotacion){
		debe.add(anotacion);	
	}
	
	public void añadirHaber(Anotacion anotacion){
		haber.add(anotacion);	
	}
	
	public int getSaldo(Calendar fecha){
		int contDebe = 0;
		int contHaber = 0;
		
		for( int i = 0 ; i < debe.size() ; i++ ){
			if(debe.get(i).fecha.before(fecha)){
				contDebe += debe.get(i).cantidad;
				System.out.println("AnotaciónDebe con fecha guay");
			}
		}
		for( int i = 0 ; i < haber.size() ; i++ ){
			if(haber.get(i).fecha.before(fecha)){
				contHaber += haber.get(i).cantidad;
			}
		}
		if (saldoDebeMenosHaber.get(codigo)==true){
			return contDebe-contHaber;
		}else{
			return contHaber-contDebe;
		}
	}
	public void inicializarPrioridades() {
		// INICIALIZAMOS PRIORIDADES CON EL CODIGO DE LA CUENTA Y SUS PRIORIDADES
		prioridades.put(12, -11);

		prioridades.put(100, -1);
		prioridades.put(110, -7);
		prioridades.put(112, -6);
		prioridades.put(129, -10);
		prioridades.put(170, -21);
		prioridades.put(173, -23);

		prioridades.put(200, 13);
		prioridades.put(202, 12);
		prioridades.put(203, 11);
		prioridades.put(206, 14);
		prioridades.put(210, 1);
		prioridades.put(211, 2);
		prioridades.put(213, 4);
		prioridades.put(216, 6);
		prioridades.put(217, 8);
		prioridades.put(218, 5);
		prioridades.put(280, 20);
		prioridades.put(281, 10);
		
		prioridades.put(300, 35);

		prioridades.put(400, -40);
		prioridades.put(430, 42);
		prioridades.put(472, 0);
		prioridades.put(473, 48);
		prioridades.put(476, -50);
		prioridades.put(477, 0);

		prioridades.put(523, -35);
		prioridades.put(572, 59);

		prioridades.put(600, 0);
		prioridades.put(610, 0);
		prioridades.put(640, 0);
		prioridades.put(642, 0);
		prioridades.put(662, 0);
		prioridades.put(680, 0);
		prioridades.put(681, 0);

		prioridades.put(700, 0);
		prioridades.put(705, 0);
		prioridades.put(730, 0);
		prioridades.put(769, 0);

		prioridades.put(4700, 45);
		prioridades.put(4751, -51);
		prioridades.put(4750, -52);
		
	}

	
	public void inicializarSaldoDebeMenosHaber(){
		// INICIALIZAMOS LAS CUENTAS CON UN BOOLEANO PARA SABER SI ESTÁN A LA IZQUIERDA O A LA DERECHA DE LA CUENTA
		saldoDebeMenosHaber.put(12, false);
		
		saldoDebeMenosHaber.put(100, false);
		saldoDebeMenosHaber.put(110, false);
		saldoDebeMenosHaber.put(112, false);
		saldoDebeMenosHaber.put(129, false);
		saldoDebeMenosHaber.put(170, false);
		saldoDebeMenosHaber.put(173, false);

		saldoDebeMenosHaber.put(200, true);
		saldoDebeMenosHaber.put(202, true);
		saldoDebeMenosHaber.put(203, true);
		saldoDebeMenosHaber.put(206, true);
		saldoDebeMenosHaber.put(210, true);
		saldoDebeMenosHaber.put(211, true);
		saldoDebeMenosHaber.put(213, true);
		saldoDebeMenosHaber.put(216, true);
		saldoDebeMenosHaber.put(217, true);
		saldoDebeMenosHaber.put(218, true);
		saldoDebeMenosHaber.put(280, true);
		saldoDebeMenosHaber.put(281, true);
		
		saldoDebeMenosHaber.put(300, true);

		saldoDebeMenosHaber.put(400, false);
		saldoDebeMenosHaber.put(430, true);
		saldoDebeMenosHaber.put(472, true);
		saldoDebeMenosHaber.put(473, true);
		saldoDebeMenosHaber.put(476, false);
		saldoDebeMenosHaber.put(477, true);

		saldoDebeMenosHaber.put(523, false);
		saldoDebeMenosHaber.put(572, true);

		saldoDebeMenosHaber.put(600, true);
		saldoDebeMenosHaber.put(610, true);
		saldoDebeMenosHaber.put(640, true);
		saldoDebeMenosHaber.put(642, true);
		saldoDebeMenosHaber.put(662, true);
		saldoDebeMenosHaber.put(680, true);
		saldoDebeMenosHaber.put(681, true);

		saldoDebeMenosHaber.put(700, true);
		saldoDebeMenosHaber.put(705, true);
		saldoDebeMenosHaber.put(730, true);
		saldoDebeMenosHaber.put(769, true);

		saldoDebeMenosHaber.put(4700, true);
		saldoDebeMenosHaber.put(4751, false);
		saldoDebeMenosHaber.put(4750, false);
	}
	
}
