package es.ubu.inf.tfg.ui.panelesAsientos.sinRetenciones;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import es.ubu.inf.tfg.asientosContables.sinRetenciones.SueldosEmpleadosSinRetenciones;
import es.ubu.inf.tfg.ui.AsientoPanel;
import es.ubu.inf.tfg.ui.Main;


public class SueldoEmpleadosSinRetPanel extends AsientoPanel<SueldosEmpleadosSinRetenciones> {

	//private static final Logger log = LoggerFactory.getLogger(AportacionPanel.class);
	private static final long serialVersionUID = -1805230103073818602L;

	private JButton borrarButton;
	private JButton mostrarButton;
	private JDateChooser calendario;
	private JTextField sueldo;
	private JTextField cotizacion;
	public static SueldosEmpleadosSinRetenciones sueldoEmpleadoSinRetenciones;
	
	public SueldoEmpleadosSinRetPanel(Main main, JPanel contenedor, int numero) {

		this.main = main;
		this.contenedorPanel = contenedor;
		this.numero = numero;

		inicializaPanel("Sueldos y salarios");
		
		// Botón -
		this.borrarButton = new JButton("-");
		this.borrarButton.addActionListener(new BotonBorrarActionListener());
		mainPanel.add(this.borrarButton);
		
	
		// Botón >>
		this.mostrarButton = new JButton(">>");
		this.mostrarButton.setMargin(new Insets(0, 1, 0, 1));
		this.mostrarButton.addActionListener(new BotonMostrarActionListener());
		this.mostrarPanel.add(this.mostrarButton, BorderLayout.CENTER);
			
		// Fecha
		this.calendario = new JDateChooser("dd/MM/yyyy", "##/##/####", '_');
		Calendar fechaMinima = Calendar.getInstance();
		fechaMinima.set(Main.anoInicial, 0, 1);
		this.calendario.setMinSelectableDate(fechaMinima.getTime());
		mainPanel.add(calendario);
		

		//Texto
		mainPanel.add(new JLabel(" La empresa paga a cada uno de sus " + (int)Main.numeroSocios +" empleados"));
		
		this.sueldo = new JTextField(6);
		mainPanel.add(this.sueldo);
		
		mainPanel.add(new JLabel("€ de sueldo bruto al año. Por cada uno de sus empleados la"));
		mainPanel.add(new JLabel("empresa cotiza"));
		
		this.cotizacion = new JTextField(6);
		mainPanel.add(this.cotizacion);
		
		mainPanel.add(new JLabel("€ a la Seguridad Social (cuota patronal), que"));
		mainPanel.add(new JLabel("pagará al año que viene."));
	
	}
	
	private class BotonMostrarActionListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			Calendar fecha = Calendar.getInstance();
			double sueldoEmpleados = 0;
			double cotizacionSS = 0;
			boolean ok = true;
			
			//Recojo fecha
			Date f = calendario.getDate();
			if (f == null){
				JOptionPane.showMessageDialog(null, "Introduce la fecha correctamente");
				ok = false;
			}
			if (ok){
				fecha.setTime(f);
			}
			
			//Sueldo
			String s = sueldo.getText();
			if("".equals(s)){
				JOptionPane.showMessageDialog(null, "Introduce el sueldo correctamente");
				ok = false;
			}else{
				try{
					sueldoEmpleados = Double.parseDouble(s);	
				}catch (Exception e){
					JOptionPane.showMessageDialog(null, "Introduce eel sueldo correctamente");
					ok = false;
				}
			}
			
			//Cotizacion
			String c = cotizacion.getText();
			if("".equals(c)){
				JOptionPane.showMessageDialog(null, "Introduce el importe de cotización correctamente");
				ok = false;
			}else{
				try{
					cotizacionSS = Double.parseDouble(c);	
				}catch (Exception e){
					JOptionPane.showMessageDialog(null, "Introduce el importe de cotización correctamente");
					ok = false;
				}
			}
		
				
			if(ok){
				double [] inputsSueldoEmpleadoSinR = {Main.numeroSocios, sueldoEmpleados, cotizacionSS};
				sueldoEmpleadoSinRetenciones = new SueldosEmpleadosSinRetenciones(fecha, inputsSueldoEmpleadoSinR, Main.enunciadoConCuentas);
				
				mostrarVista();
			}
		}
	}
}
