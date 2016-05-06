package es.ubu.inf.tfg.otrasCosas;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Enunciado implements Comparable<Enunciado>{
	
	private Calendar fecha;
	private String enunciado;
	
    
    SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
    
    public String toString() {
        return this.formateador.format(fecha.getTime()) + " " + this.enunciado;
    }
    
   public Enunciado (Calendar fecha, String enunciado) {
        this.fecha = fecha;
        this.enunciado = enunciado;
    }

    public Calendar getFecha() {
        return fecha;
    }

    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

	@Override
	public int compareTo(Enunciado o) {
		return this.fecha.compareTo(o.fecha);
	}


}


