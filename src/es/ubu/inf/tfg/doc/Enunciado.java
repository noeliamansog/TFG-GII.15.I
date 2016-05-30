package es.ubu.inf.tfg.doc;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Enunciado implements Comparable<Enunciado>{
	
	public Calendar fecha;
	public String enunciado;
	
    SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
    
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