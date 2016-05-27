package es.ubu.inf.tfg.otrasCosas;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class Cuenta implements Comparable<Cuenta>{
	
	public int codigo = 0;
	public String nombre;
	public int prioridad;
	public ArrayList<Anotacion> debe = new ArrayList<Anotacion>();
	public ArrayList<Anotacion> haber = new ArrayList<Anotacion>();
	public HashMap<Integer, Boolean> saldoDebeMenosHaber = new HashMap<Integer, Boolean>();
	
	public Cuenta(int codigo, String nombre, int prioridad){
		this.codigo = codigo;
		this.nombre = nombre;
		this.prioridad = prioridad;
		inicializarSaldoDebeMenosHaber();
	}

	public void añadirDebe(Anotacion anotacion){
		debe.add(anotacion);	
	}
	
	public void añadirHaber(Anotacion anotacion){
		haber.add(anotacion);	
	}
	
	public double getSaldo(Calendar fecha){
		double contDebe = 0;
		double contHaber = 0;
		
		for( int i = 0 ; i < debe.size() ; i++ ){
			if(debe.get(i).fecha.before(fecha)){
				contDebe += debe.get(i).cantidad;
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
		saldoDebeMenosHaber.put(477, false);

		saldoDebeMenosHaber.put(523, false);
		saldoDebeMenosHaber.put(572, true);

		saldoDebeMenosHaber.put(600, true);
		saldoDebeMenosHaber.put(610, true);
		saldoDebeMenosHaber.put(630, true);
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
		saldoDebeMenosHaber.put(4752, false);
	}


	@Override
	public int compareTo(Cuenta cuenta) {
		int resultado = 0;
		if (prioridad >0){
			if (this.prioridad < cuenta.prioridad){
				resultado = -1;
			}
			if (this.prioridad > cuenta.prioridad){
				resultado = 1;
			}
		}else{
			if (this.prioridad < cuenta.prioridad){
				resultado = 1;
			}
			if (this.prioridad > cuenta.prioridad){
				resultado = -1;
			}
		}
		return resultado;
	}
}