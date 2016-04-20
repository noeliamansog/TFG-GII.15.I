package es.ubu.inf.tfg.SupuestoContable;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
 
public class SupuestoContable {
 
    public static int año = 2016;
    public static int impuestoSociedad = (int)(Math.random()*(30-25+1)+25);
    public static int IVA = (int)(Math.random()*25 + 1);
    
    //Numero de asientos de cada tipo: 
    //1ºAÑO
    public static int Nprestamo = 1;
    public static int NcompraMaterialNoAmortizable1 = 1;
    public static int NcompraMaterialAmortizable1 = 1;
    public static int NcompraIntangibleNoAmortizable1 = 1;
    public static int NcompraIntangibleAmortizable1 = 1;
    public static int NcompraMercaderias1 = 1;
    public static int NventaClientes1 = 1;
    public static int Npresupuesto = 1;
    public static int NnuevosSocios = 1;
    //2ºAÑO
    public static int Nintereses = 1;
    public static int Ninventario = 1;
    public static int NrenovacionInversiones = 1;
    /*public static int NcompraMaterialNoAmortizable2 = 1;
    public static int NcompraMaterialAmortizable2 = 1;
    public static int NcompraIntangibleNoAmortizable2 = 1;
    public static int NcompraIntangibleAmortizable2 = 1;*/
    public static int NcompraMercaderias2 = 1;
    public static int NventaClientes2 = 1;
    
    

	public static void main(String[] args) throws IOException {
        String ruta = "/Users/noelia/Desktop/enunciado.txt";
        File enunciado = new File(ruta);
        BufferedWriter bw = new BufferedWriter(new FileWriter(enunciado));
        
        //numeroAsientos(bw, NaportacionInicial, aportacionInicial());
        
        //1ºAÑO
        bw.write("1/1/"+año+": \n--------- \n");
        bw.write(aportacionInicial() + "\n");
        
        for (int i=0; i<Nprestamo; i++){
        	bw.write(prestamo() + "\n");}
        for (int i=0; i<NcompraMaterialNoAmortizable1; i++){
        	bw.write(compraMaterialNoAmortizable() + "\n");}
        for (int i=0; i<NcompraMaterialAmortizable1; i++){
        	bw.write(compraMaterialAmortizable() + "\n");}
        for (int i=0; i<NcompraIntangibleNoAmortizable1; i++){
        	bw.write(compraIntangibleNoAmortizable() + "\n");}
        for (int i=0; i<NcompraIntangibleAmortizable1; i++){
        	bw.write(compraIntangibleAmortizable() + "\n");}
        for (int i=0; i<NcompraMercaderias1; i++){
        	bw.write(compraMercaderias() + "\n");}
        for (int i=0; i<NventaClientes1; i++){
        	bw.write(ventaClientes() + "\n");}
        for (int i=0; i<Npresupuesto; i++){
        	bw.write(presupuesto() + "\n");}
        for (int i=0; i<NnuevosSocios; i++){
        	bw.write(nuevosSocios() + "\n");}
        
        bw.write(sueldo() + "\n");
        bw.write(deudasPrimerAno());    
        bw.write(cierre() + "\n");
        
        //2ºAÑO
        bw.write("1/1/"+(año+1)+": \n--------- \n");
        bw.write(comienzoAno() + "\n");
        bw.write(dividendos() + "\n");
        /*for (int i=0; i<NcompraMaterialNoAmortizable2; i++){
        	bw.write(compraMaterialNoAmortizable() + "\n");}
        for (int i=0; i<NcompraMaterialAmortizable2; i++){
        	bw.write(compraMaterialAmortizable() + "\n");}
        for (int i=0; i<NcompraIntangibleNoAmortizable2; i++){
        	bw.write(compraIntangibleNoAmortizable() + "\n");}
        for (int i=0; i<NcompraIntangibleAmortizable2; i++){
        	bw.write(compraIntangibleAmortizable() + "\n");}*/
        for (int i=0; i<NcompraMercaderias2; i++){
        	bw.write(compraMercaderias() + "\n");}
        for (int i=0; i<NventaClientes2; i++){
        	bw.write(ventaClientes() + "\n");}
        for (int i=0; i<Nintereses; i++){
        	bw.write(intereses() + "\n");}
        bw.write(sueldo() + "\n");
        for (int i=0; i<Ninventario; i++){
        	bw.write(inventario() + "\n");}
        for (int i=0; i<NrenovacionInversiones; i++){
        	bw.write(renovacionInversiones() + "\n");}
        bw.write(deudasSegundoAno());
        bw.write(cierre() + "\n");
        
        bw.write(enunciado() + "\n");
        

        bw.close();
        
    }
	
	/*private static void numeroAsientos (BufferedWriter bw, int nAsientos, String tipoAsiento) throws IOException {
		for (int i=0; i<nAsientos; i++){
			bw.write(tipoAsiento + "\n");
		}
	}*/

    
    public static String aportacionInicial () {
    	int numSocios = (int)(Math.random()*(10-2+1)+2);
        int aporteInicial = (int)(Math.random()*(50000-5000+1)+5000);
        int aporteTotal = numSocios*aporteInicial;
        
    	return "Cada uno de los " +numSocios+ " socios fundadores realiza una aportación inicial en "
    			+ "efectivo a favor de la empresa por importe de " +aporteInicial+ "€ (" +aporteTotal+ "€ en total). "
    			+ "Cada socio tiene una acción. La empresa ingresa el dinero recibido en su cuenta corriente. \n";
    }
   
    public static String prestamo () { 	
        int prestamo = (int)(Math.random()*(150000-50000+1)+50000);
        String tipoPrestamo = (Math.random() < 0.5) ? (String) "amortización" : (String) "pago";
        int añosPrestamo = (int)(Math.random()*(20-10+1)+10);
        int interesPrestamo = (int)(Math.random()*10 + 1);
    	return "La empresa obtiene un préstamo por importe de " +prestamo+ "€, que se ingresa en la cuenta "
    			+ "corriente de la que es titular la empresa. El préstamo se devolverá en cuotas de " +tipoPrestamo+ " anuales "
    			+ "constantes, en " +añosPrestamo+ " años, a un tipo de interés fijo del " +interesPrestamo+ "% anual. El primer pago "
    			+ "se realizará el 31 de diciembre de "+año+". \n";
    }
    
    public static String compraMaterialNoAmortizable () {
    	String materialNoAmortizable = (Math.random() < 0.5) ? (String) "un solar" : (String) "un local donde instalar la oficina";
        int importeMaterialNoAmortizable = (int)(Math.random()*(150000-50000+1)+50000);
        int abonoMaterialNoAmortizable = (int)(Math.random()*((importeMaterialNoAmortizable-1)-10000+1)+10000);
        int pendienteMaterialNoAmortizable = importeMaterialNoAmortizable-abonoMaterialNoAmortizable;
		int numero = (int) (Math.random() * 5) + 1;
		int mesesMaterialNoAmortizable = 0;
		switch(numero){
		case 1: mesesMaterialNoAmortizable = 3;break;
		case 2: mesesMaterialNoAmortizable = 6; break;
		case 3:mesesMaterialNoAmortizable = 9; break;
		case 4: mesesMaterialNoAmortizable = 18; break;
		case 5:mesesMaterialNoAmortizable = 20;break;
		}   
        
    	return "La empresa compra " +materialNoAmortizable+ " por importe de " +importeMaterialNoAmortizable+ "€. "
    			+ "Este activo no es amortizable. Se abonan " +abonoMaterialNoAmortizable+ "€ mediante transferencia y "
    			+ "quedan " +pendienteMaterialNoAmortizable+ "€ pendiente de pago. Se acuerda que la deuda se pagará "
    			+ "en " +mesesMaterialNoAmortizable+ " meses. \n";
    }
    
    
    
    public static String compraMaterialAmortizable () {
    	String materialAmortizable = null;
    	int numero = (int) (Math.random() * 5) + 1;
		switch(numero){
    	case 1: materialAmortizable = "maquinaria"; break;
    	case 2: materialAmortizable = "mobiliario de oficina"; break;
    	case 3: materialAmortizable = "un ordenador"; break;
    	case 4: materialAmortizable = "un coche"; break;
    	case 5: materialAmortizable = "una furgoneta";break;
    	}
        int valorMaterialAmortizable = (int)(Math.random()*(25000-5000+1)+5000);
        int diasPagoMaterialAmortizable =  (int)(Math.random()*(90-60+1)+60);
        int amortizacionMaterialAmortizable = (int)(Math.random()*(10-4+1)+4);
    	return "La empresa compra " +materialAmortizable+ " por valor de " +valorMaterialAmortizable+ "€. "
    			+ "El importe de la compra se abonará a los " +diasPagoMaterialAmortizable+ " días. La compra "
    			+ "(" +materialAmortizable+ ") se amortiza linealmente en " +amortizacionMaterialAmortizable+ " años.\n";
    }
    
    
    public static String compraIntangibleNoAmortizable () {
    	return "La empresa compra a una Administración Pública el derecho de explotación de un terreno. "
    			+ "Se paga al contado. \n";
    }
    
    
    ///????????????????????? 2 o 1??????
    public static String compraIntangibleAmortizable () {
    	int valorIntangibleAmortizable = (int)(Math.random()*(5000-1000+1)+1000);
        int diasPagoIntangibleAmortizable1 =  (int)(Math.random()*(90-60+1)+60);
        int amortizacionIntangibleAmortizable1 = (int)(Math.random()*(5-2+1)+2);
        int pagoLogotipo = (int)(Math.random()*(5000-1000+1)+1000);
    	int diasPagoIntangibleAmortizable2 =  (int)(Math.random()*(90-60+1)+60);
    	int amortizacionIntangibleAmortizable2 = (int)(Math.random()*(5-2+1)+2);
    	return "La empresa compra una aplicación informática por valor de " +valorIntangibleAmortizable+ "€. "
    	+ "El importe de la compra se abonará a los " +diasPagoIntangibleAmortizable1+ " días. El software se amortiza linealmente "
    	+ "en " +amortizacionIntangibleAmortizable1+ " años. La empresa adquiere el derecho a usar un logotipo, por lo cual paga " +pagoLogotipo+ "€. "
    	+ "El importe de la compra se abonará a los " +diasPagoIntangibleAmortizable2+ " días. La propiedad industrial se amortiza linealmente "
    	+ "en " +amortizacionIntangibleAmortizable2+ " años.\n";
    }
    
    
    /// IVA?
    public static String compraMercaderias () {
    	int mercaderias = (int)(Math.random()*(25000-5000+1)+5000);
    	int IVAMercaderias =  (int)(Math.random()*25 + 1);
    	String st = "La empresa compra mercaderías por un importe de " +mercaderias+ "€ más un " +IVAMercaderias+ "% de IVA."
    	+ "[Se acuerda que el pago se realice en 30 días, se paga al contado]. \n";

    	return st;	
    }
    
    
    // IVA?
    // Aleatorio uno o otro?
    public static String ventaClientes () {
    	int ventaMercaderias = (int)(Math.random()*(20000-5000+1)+5000);
    	int IVAVentaMercaderias =  (int)(Math.random()*25 + 1);
    	int ventaCliente = (int)(Math.random()*(25000-5000+1)+5000);
    	int IVAVentaCliente =  (int)(Math.random()*25 + 1);
    	return "La empresa efectúa una venta de mercaderías por importe de " +ventaMercaderias+ "€ más un " +IVAVentaMercaderias+ "% de IVA. " 
    	+ "[Se acuerda que el cliente pague en 30 días, el cliente paga al contado]. \n"
    	+ "La empresa entrega un proyecto a un cliente, por el cual factura " +ventaCliente+ "€ más un " +IVAVentaCliente+ "% de IVA."
    	+ "[Se acuerda que el cliente pague en 30 días, el cliente paga al contado]. \n";
    }
    
    
    public static String presupuesto () {
    	return "Un cliente le solicita a la empresa que le facilite un presupuesto detallado para estudiar "
    			+ "si le interesa realizar un pedido. \n";	
    }
    
    public static String nuevosSocios () {
    	int aportacionNuevoSocio = (int)(Math.random()*(10000-1000+1)+1000);
    	return  "Se incorpora un nuevo socio a la empresa, el cual aporta " +aportacionNuevoSocio+ "€ en efectivo "
    			+ "(los cuales se ingresan en cuenta corriente) a cambio de una acción de nueva emisión. \n";
    }
    
    
    // ¿Se contrata a un ingeniero informatico?
    public static String sueldo () {    
    	int empleados = (int)(Math.random()*(3-2+1)+2);
    	int sueldoBruto = (int)(Math.random()*(30000-20000+1)+20000);
        int cotizacion = (int)(Math.random()*(100000-7000+1)+7000);
        int porcentajeIRPF = (int)(Math.random()*(30-10+1)+10);
        int porcentajeSS = (int)(Math.random()*(8-3+1)+3);
        int sueldoaInformatico = (int)(Math.random()*(80000-30000+1)+30000);
        int porcentajeTiempo = (int)(Math.random()*(90-10+1)+10);
    	return "La empresa paga a cada uno de sus " +empleados+ " empleados " +sueldoBruto+ "€ de sueldo bruto al año. "
    			+ "Por cada uno de sus empleados la empresa cotiza " +cotizacion+ "€ a la Seguridad Social (cuota patronal), "
    			+ "que pagará al año que viene. Se retiene el " +porcentajeIRPF+ "% del salario bruto por I.R.P.F. y "
    			+ "el " +porcentajeSS+ "% del salario bruto en concepto de Seguridad Social a cargo del trabajador (cuota obrera). \n"
    			+ "Se contrata a un ingeniero informático cuyo sueldo bruto anual es " +sueldoaInformatico+ "€. El informático dedica "
    			+ "el " +porcentajeTiempo+ "% de su tiempo a desarrollar un proyecto de investigación con altas probabilidades "
    			+ "de generar ganancias en un futuro. Contabilizar primero el gasto, y luego la activación de la parte correspondiente. \n";
    }
    
    public static String deudasPrimerAno (){
    	String st = "";
    	if (Nprestamo>=1){
    		st = "La empresa paga la cuota correspondiente del préstamo. \n \n";
    	}
    	if (NcompraMaterialNoAmortizable1 >=1 || NcompraMaterialAmortizable1 >=1 || NcompraIntangibleNoAmortizable1 >=1 ||
    			NcompraMercaderias1 >=1){
    		st += "Se saldan las deudas con los proveedores. \n \n";
    	}
    	if (NventaClientes1 >=1){
    		st += "Los clientes saldan la deuda con la empresa. \n \n";
    	}
 	
    	return st;
    }
    
    public static String cierre () {
  
    	return "Se cierra el ejercicio (contabilizando las amortizaciones pertinentes y realizando la liquidación del IVA), "
    			+ "y deja a deber a Hacienda el impuesto de sociedades " +impuestoSociedad+ "% del beneficio).\n";
    }
    
    public static String intereses () {
    	int importeInteres = (int)(Math.random()*200 + 1);
    	return  "La empresa recibe " +importeInteres+ "€ por intereses devengados en la cuenta corriente "
    			+ "durante este año. \n";
    }
    
    
    public static String comienzoAno () {
    	return  "Se paga la deuda con Hacienda y con la Seguridad Social. \n";
    }
    
    public static String dividendos () {
    	int porcentajeDividendos = (int)(Math.random()*(30-5+1)+5);
        int porcentajeRetencion = (int)(Math.random()*100 + 1);
    	return  "Se decide repartir dividendos por valor del " +porcentajeDividendos+ "% del resultado del "
    			+ "ejercicio anterior (sobre los cuales se practica una retención del " +porcentajeRetencion+ "%). "
    			+ "El resto se lleva a Reserva Legal. \n";
    }

    public static String inventario () {
    	int costes = (int)(Math.random()*(200000-150000+1)+150000);
    	return  "Se hace inventario de mercaderías. Se estima un valor de coste de " +costes+ "€.\n";
    }
    
    public static String renovacionInversiones (){	
    	int ventaInversion = (int)(Math.random()*(3000-500+1)+500);
        int compraInversion = (int)(Math.random()*(50000-10000+1)+10000);
    	return "Se renueva ______ al final del año. El antiguo se vende por " +ventaInversion+ "€. El nuevo "
    			+ "cuesta " +compraInversion+ "€. \n";
    }
    
    public static String deudasSegundoAno (){
    	String st = "";
    	/*Si aún queda prestamo por pagar... if (Nprestamo>=1){
    		st = "La empresa paga la cuota correspondiente del préstamo. \n \n";
    	}*/
    	if (/*NcompraMaterialNoAmortizable2 >=1 || NcompraMaterialAmortizable2 >=1 || NcompraIntangibleNoAmortizable2 >=1 ||
    			*/NcompraMercaderias2 >=1){
    		st += "Se saldan las deudas con los proveedores. \n \n";
    	}
    	if (NventaClientes2>=1){
    		st += "Los clientes saldan la deuda con la empresa. \n \n";
    	}
 	
    	return st;
    }
    
   
    
    public static String enunciado (){
    	return "\n\n\n\nSe pide: \n"
    			+ "  a) Indique claramente cómo afectaría a cada una de las cuentas contables cada una de las "
    			+ "transacciones económicas de la empresa, elaborando al mismo tiempo el balance de situación, "
    			+ "la cuenta de pérdidas y ganancias y el estado de flujos de tesorería de cada año. El impuesto "
    			+ "de sociedades es el " +impuestoSociedad+ "% del beneficio y el IVA es " +IVA+ "%.\n"
    			+ "  b) Calcular la rentabilidad económica y la rentabilidad financiera de cada año y comprobar "
    			+ "que se cumple la ecuación que las liga. \n"
    			+ "  c) Calcular y comentar el Fondo de Maniobra al final de cada año.\n"
    			+ "  d) Analizar la liquidez de la empresa mediante ratios para cada uno de los años.\n"
    			+ "  e) Analizar el endeudamiento de la empresa mediante ratios para cada uno de los años.\n"
    			+ "  f) Indique cuál es el valor nominal, el valor contable y el valor de emisión de la nueva "
    			+ "acción emitida el 1/1/" +año+ ". ¿Cuál sería el valor de mercado de las acciones? \n";
    }
    

}