package es.ubu.inf.tfg.ui.panelesAsientos;

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

import es.ubu.inf.tfg.asientosContables.Interes;
import es.ubu.inf.tfg.ui.AsientoPanel;
import es.ubu.inf.tfg.ui.Main;

public class InteresPanel extends AsientoPanel<Interes> {

	//private static final Logger log = LoggerFactory.getLogger(AportacionPanel.class);
	private static final long serialVersionUID = -1805230103073818602L;
	
	private JButton borrarButton;
	private JButton mostrarButton;
	private JDateChooser calendario;
	private JTextField ingreso;
	private JTextField porcentaje;
	public static Interes intereses;
	
	
	public InteresPanel(Main main, JPanel contenedor, int numero) {

		this.main = main;
		this.contenedorPanel = contenedor;
		this.numero = numero;

		inicializaPanel("Intereses");	
		
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
		mainPanel.add(new JLabel(" La empresa obtiene un ingreso de"));
		
		this.ingreso = new JTextField(6);
		mainPanel.add(this.ingreso);
		
		mainPanel.add(new JLabel("€ por"));
		mainPanel.add(new JLabel("intereses devengados en la cuenta corriente durante este año, de los cuales"));
		mainPanel.add(new JLabel("cobra el"));
		
		this.porcentaje = new JTextField(2);
		mainPanel.add(this.porcentaje);
		
		mainPanel.add(new JLabel("% (el resto lo retienen)."));
	}
	
	private class BotonMostrarActionListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			Calendar fecha = Calendar.getInstance();
			double ingresoIntereses = 0;
			double porcentajeCobrado = 0;
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
			
			//Ingreso de los intereses
			String i = ingreso.getText();
			if("".equals(i)){
				JOptionPane.showMessageDialog(null, "Introduce el importe del ingreso correctamente");
				ok = false;
			}else{
				try{
					ingresoIntereses = Double.parseDouble(i);	
				}catch (Exception e){
					JOptionPane.showMessageDialog(null, "Introduce el importe del ingreso correctamente");
					ok = false;
				}
			}
			//Porcentaje que se cobra
			String p = porcentaje.getText();
			if("".equals(p)){
				JOptionPane.showMessageDialog(null, "Introduce el % de los ingresos que se cobra correctamente");
				ok = false;
			}else{
				try{
					porcentajeCobrado = Double.parseDouble(p);
					if(porcentajeCobrado<0 || porcentajeCobrado>100){
						JOptionPane.showMessageDialog(null, "El % de los intereses debe estar entre 0 y 100");
						ok=false;
					}
				}catch (Exception e){
					JOptionPane.showMessageDialog(null, "Introduce el % de los ingresos que se cobra correctamente");
					ok = false;
				}
			}
				
			if(ok){
				double [] inputsIntereses = {ingresoIntereses, porcentajeCobrado};
				intereses = new Interes(fecha, inputsIntereses, Main.enunciadoConCuentas);
				
				mostrarVista();
			}
		}
	}
}
