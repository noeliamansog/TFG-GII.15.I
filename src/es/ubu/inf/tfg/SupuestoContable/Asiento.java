

public class Asiento<T> {
	
	private Tipo tipo;
	private T asiento;
	
	//Aportación inicial
	public int socios;
	public int aportacion;
	
	//Prestamo
	public int prestamo;
	public String tipoPrestamo;
	public Boolean mensual = false;
	public int añosPrestamo;
	public int interesPrestamo;
	
	
	private Asiento(T asiento) {
		this.asiento = asiento;
	}
	
	public T getAsiento() {
		return this.asiento;
	}
	
	private enum Tipo {
		APORTACION_INCIAL,
		PRESTAMO,
	}
	public String getTipo() {
		switch (tipo) {
		case APORTACION_INCIAL:
			return "AportacionInicial";
		case PRESTAMO:
			return "Prestamo";
		default:
			throw new UnsupportedOperationException(
					"Argumento tipo no soportado.");
		}
	}

	public int getSocios() {
		return this.socios;
	}
	public int getAportacion(){
		return this.aportacion;
	}
	
	public int getPrestamo(){
		return this.prestamo;
	}
	public String getTipoPrestamo(){
		return this.tipoPrestamo;
	}
	public boolean getMesual(){
		return this.mensual;
	}
	public int getAñosPrestamo(){
		return this.añosPrestamo;
	}
	public int getInteresPrestamo(){
		return this.interesPrestamo;
	}	

}
