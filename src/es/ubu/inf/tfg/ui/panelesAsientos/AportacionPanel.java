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
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import es.ubu.inf.tfg.asientosContables.AportacionInicial;
import es.ubu.inf.tfg.main.Main;
import es.ubu.inf.tfg.ui.AsientoPanel;


public class AportacionPanel extends AsientoPanel<AportacionInicial> {

	private static final long serialVersionUID = -4728031249878756673L;
	private JButton borrarButton;
	private JButton mostrarButton;
	private JDateChooser calendario;
	private JTextField socios;
	private JTextField importe;
	public static AportacionInicial aportacionInicial;
	
	
	
	public AportacionPanel(){
		this.nombre = "AportaciónInicial";

		inicializaPanel("Aportación inicial");	
		
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
		mainPanel.add(new JLabel("Cada uno de los"));
		
		this.socios = new JTextField(2);
		mainPanel.add(this.socios);
		mainPanel.add(new JLabel ("socios fundadores realiza una"));
		
		mainPanel.add(new JLabel ("aportación inicial en efectivo a favor de la empresa por importe de"));
		
		this.importe = new JTextField(6);
		mainPanel.add(this.importe);

		mainPanel.add(new JLabel("€. Cada socio tiene una acción. La empresa ingresa el dinero"));
		mainPanel.add(new JLabel("recibido en su cuenta corriente."));
		
	}

	private class BotonMostrarActionListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			Calendar fecha = Calendar.getInstance();
			Main.numeroSocios = 0;
			double aportacion = 0;
			boolean ok = true;
			
			//Recojo fecha
			Date f = calendario.getDate();
			if (f == null){
				JOptionPane.showMessageDialog(null, "Introduce la fecha correctamente");
				ok = false;
			}
			if (ok){
				fecha.setTime(f);
				if(fecha.get(Calendar.YEAR) != Main.anoInicial){
					JOptionPane.showMessageDialog(null, "La aportación inicial tiene que hacerse en el 1º año: "+Main.anoInicial);
					ok=false;
				}
			}
			
			
			//Recojo numSocios
			String numSoc = socios.getText();
			if("".equals(numSoc)){
				JOptionPane.showMessageDialog(null, "Introduce el número de socios correctamente");
				ok = false;
			}else{
				try{	
					Main.numeroSocios = Double.parseDouble(numSoc);
				}catch (Exception e){
					JOptionPane.showMessageDialog(null, "Introduce el número de socios correctamente");
					ok = false;
				}
			}
			
			//Recojo aportación
			String ap = importe.getText();
			if("".equals(ap)){
				JOptionPane.showMessageDialog(null, "Introduce la aportación inicial correctamente");
				ok = false;
			}else{
				try{	
					aportacion = Double.parseDouble(ap);
				}catch (Exception e){
					JOptionPane.showMessageDialog(null, "Introduce la aportación inicial correctamente");
					ok = false;
				}
			}
			
	
			if(ok){
				double [] inputsAportacion = {Main.numeroSocios, aportacion};
				aportacionInicial = new AportacionInicial(fecha, inputsAportacion, Main.enunciadoConCuentas);
				Main.aportacionEjecutada = true;
				Main.fechaAportacion = fecha;
				añadeEnunciado(aportacionInicial.enunciados);
				Main.ejecucionAlgunAsiento = true;
				borrarButton.setEnabled(false);
				mostrarButton.setEnabled(false);
				mostrarVista();
			}		
		}
	}
	
}

