
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class CompraIntangibleNoAmortizable extends Asiento {
	
	private ArrayList <Cuenta> cuentasIntangibleNolAmortizable = new ArrayList<Cuenta>();;
	private ArrayList<Anotacion> listaDebe = new ArrayList<Anotacion>();
	private ArrayList<Anotacion> listaHaber = new ArrayList<Anotacion>();

	public CompraIntangibleNoAmortizable(String enunciado, Date fecha, int[] inputs, HashMap<Integer, Cuenta> todasCuentas) {
		super(enunciado, fecha, inputs, todasCuentas);
		
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
		//inputs[0]=1000;   //Valor de la administración pública
	
		enunciado = formateador.format(fecha)+"La empresa compra a una Administración Pública el derecho de explotación de un terreno. "
		+ "Se paga al contado. \n";
		
		System.out.println(enunciado);

		/*Cuenta 1
		listaDebe.add(new Anotacion(fecha, "", (inputs[0])));
		Cuenta cuenta1 = new Cuenta(202, "Concesiones administrativas", listaDebe, null, (inputs[0])-0);
		cuentasIntangibleNolAmortizable.add(cuenta1);
		
		
		//Cuenta 2
		listaHaber.add(new Anotacion(fecha, "", (inputs[0])));	
		Cuenta cuenta2 = new Cuenta(527, "Bancos e instituciones de crédito c/c vista, euros", null, listaHaber, 0-(inputs[0]));
		cuentasIntangibleNolAmortizable.add(cuenta2);
	
		
		contabilizar(cuentasIntangibleNolAmortizable);*/
		
	}

}
