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
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import es.ubu.inf.tfg.asientosContables.sinRetenciones.InteresSinRetenciones;
import es.ubu.inf.tfg.ui.AsientoPanel;
import es.ubu.inf.tfg.main.Main;

public class InteresSinRetPanel extends AsientoPanel<InteresSinRetenciones> {

	private static final long serialVersionUID = -1805230103073818602L;

	private JButton borrarButton;
	private JButton mostrarButton;
	private JDateChooser calendario;
	private JTextField ingreso;
	public static InteresSinRetenciones interesesSinRetenciones;
	
	
	public InteresSinRetPanel(){
		this.nombre = "InteresesSinRetenciones";
		
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
		mainPanel.add(new JLabel("intereses devengados en la cuenta corriente durante este año."));
	}
	
	private class BotonMostrarActionListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			Calendar fecha = Calendar.getInstance();
			double ingresoIntereses = 0;
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
				
			if(ok){
				double [] inputsInteresesSinR = {ingresoIntereses};
				interesesSinRetenciones = new InteresSinRetenciones(fecha, inputsInteresesSinR, Main.enunciadoConCuentas);
				añadeEnunciado(interesesSinRetenciones.enunciados);
				Main.ejecucionAlgunAsiento = true;
				borrarButton.setEnabled(false);
				mostrarButton.setEnabled(false);
				mostrarVista();
			}
		}
	}
}
