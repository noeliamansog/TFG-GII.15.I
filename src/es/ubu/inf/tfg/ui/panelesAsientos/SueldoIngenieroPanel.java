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

import es.ubu.inf.tfg.asientosContables.SueldoIngeniero;
import es.ubu.inf.tfg.ui.AsientoPanel;
import es.ubu.inf.tfg.main.Main;

public class SueldoIngenieroPanel extends AsientoPanel {

	private static final long serialVersionUID = -1805230103073818602L;

	private JButton borrarButton;
	private JButton mostrarButton;
	private JDateChooser calendario;
	private JTextField sueldo;
	private JTextField tiempo;
	public static SueldoIngeniero sueldoIngeniero;
	
	
	public SueldoIngenieroPanel(){
		this.nombre ="SueldoIngeniero";
		
		inicializaPanel("Sueldo ingeniero informático");	
		
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
		mainPanel.add(new JLabel(" Se contrata a un ingeniero informático cuyo sueldo"));
		mainPanel.add(new JLabel("bruto anual es"));
		
		this.sueldo = new JTextField(6);
		mainPanel.add(this.sueldo);
		
		mainPanel.add(new JLabel("€. El informático dedica el")); 

		this.tiempo = new JTextField(2);
		mainPanel.add(this.tiempo);
		
		mainPanel.add(new JLabel("% de su tiempo a"));
		mainPanel.add(new JLabel("desarrollar un proyecto de investigación con altas probabilidades de generar"));
		mainPanel.add(new JLabel("ganancias en un futuro. Contabilizar primero el gasto, y luego la activación"));
		mainPanel.add(new JLabel("de la parte correspondiente.")); 

	}
	
	private class BotonMostrarActionListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			Calendar fecha = Calendar.getInstance();
			double sueldoIng = 0;
			double porcentajeTiempo = 0;
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
					sueldoIng = Double.parseDouble(s);	
				}catch (Exception e){
					JOptionPane.showMessageDialog(null, "Introduce eel sueldo correctamente");
					ok = false;
				}
			}
			
			//Porcentaje de tiempo
			String t = tiempo.getText();
			if("".equals(t)){
				JOptionPane.showMessageDialog(null, "Introduce el % del tiempo correctamente");
				ok = false;
			}else{
				try{
					porcentajeTiempo = Double.parseDouble(t);	
					if(porcentajeTiempo<0 || porcentajeTiempo>100){
						JOptionPane.showMessageDialog(null, "El % del tiempo del ingeniero debe estar entre 0 y 100");
						ok=false;
					}
				}catch (Exception e){
					JOptionPane.showMessageDialog(null, "Introduce el % del tiempo correctamente");
					ok = false;
				}
			}
		
				
			if(ok){
				double [] inputsSueldoIngeniero = {sueldoIng, porcentajeTiempo};
				sueldoIngeniero = new SueldoIngeniero(fecha, inputsSueldoIngeniero, Main.enunciadoConCuentas);
				añadeEnunciado(sueldoIngeniero.enunciados);
				Main.ejecucionAlgunAsiento = true;
				borrarButton.setEnabled(false);
				mostrarButton.setEnabled(false);
				mostrarVista();
			}
		}
	}
}
