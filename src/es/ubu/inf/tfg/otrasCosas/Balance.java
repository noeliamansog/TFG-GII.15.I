package es.ubu.inf.tfg.otrasCosas;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

public class Balance extends Asiento{
	static ArrayList<Anotacion> activo = new ArrayList<Anotacion>();
	static ArrayList<Anotacion> pnPasivo = new ArrayList<Anotacion>();
	
	
	public Balance(){
		
		Iterator<Integer> it = todasCuentas.keySet().iterator();
		while(it.hasNext()){
		  Integer key = (Integer) it.next();
		  Cuenta cuenta = todasCuentas.get(key);

		  if(cuenta.getPrioridad()!=0){
			  if (!(cuenta.debe.isEmpty())){
				  for(int i=0; i<cuenta.debe.size(); i++){
					  activo.add(cuenta.debe.get(i));
				  }
			  }
			  if (!(cuenta.haber.isEmpty())){
				  for(int i=0; i<cuenta.haber.size(); i++){
					  pnPasivo.add(cuenta.haber.get(i));
					  
				  }
			  }
		  }
		}
	}
	
	public void imprimeBalance(){
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
		
		/* IMPRIME POR PANTALLA 
		System.out.println("\n\n BALANCE: \n");
		System.out.println("ACTIVO:");
		if (!(activo.isEmpty())){
			for(int i=0; i<activo.size(); i++){
				Calendar fecha= activo.get(i).fecha;
				System.out.println(formateador.format(fecha.getTime()) +" \t"+ activo.get(i).nombre +" \t\t\t\t"+ activo.get(i).cantidad);
			}
		}
		System.out.println("\n");
		System.out.println("PATRIMONIO NETO Y PASIVO:");
		if (!(pnPasivo.isEmpty())){
			for(int i=0; i<pnPasivo.size(); i++){
				Calendar fecha= pnPasivo.get(i).fecha;
				System.out.println(formateador.format(fecha.getTime()) +" \t"+ pnPasivo.get(i).nombre +" \t\t\t\t"+ pnPasivo.get(i).cantidad);
		  }
		}*/
	}

}
