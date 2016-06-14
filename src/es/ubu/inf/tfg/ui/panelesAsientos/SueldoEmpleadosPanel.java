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

import es.ubu.inf.tfg.asientosContables.SueldosEmpleados;
import es.ubu.inf.tfg.ui.AsientoPanel;
import es.ubu.inf.tfg.main.Main;

public class SueldoEmpleadosPanel extends AsientoPanel {

	private static final long serialVersionUID = -1805230103073818602L;

	private JButton borrarButton;
	private JButton mostrarButton;
	private JDateChooser calendario;
	private JTextField sueldo;
	private JTextField cotizacion;
	private JTextField retencion1;
	private JTextField retencion2;
	public static SueldosEmpleados sueldoEmpleado;
	
	
	public SueldoEmpleadosPanel(){
		this.nombre = "SueldosYSalarios";
		
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
		mainPanel.add(new JLabel(" La empresa paga a cada uno de sus "+(int)Main.numeroSocios+" empleados"));
		
		this.sueldo = new JTextField(6);
		mainPanel.add(this.sueldo);
		
		mainPanel.add(new JLabel("€ de sueldo bruto al año. Por cada uno de sus empleados la"));
		mainPanel.add(new JLabel("empresa cotiza"));
		
		this.cotizacion = new JTextField(6);
		mainPanel.add(this.cotizacion);
		
		mainPanel.add(new JLabel("€ a la Seguridad Social (cuota patronal), que"));
		mainPanel.add(new JLabel("pagará al año que viene. Se retiene el"));
		
		this.retencion1 = new JTextField(2);
		mainPanel.add(this.retencion1);
		
		mainPanel.add(new JLabel("% del salario bruto por I.R.P.F. y el"));
		
		this.retencion2 = new JTextField(2);
		mainPanel.add(this.retencion2);
		
		mainPanel.add(new JLabel("% del salario bruto en concepto de Seguridad Social a cargo del"));
		mainPanel.add(new JLabel("trabajador (cuota obrera)."));
	
	}
	private class BotonMostrarActionListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			Calendar fecha = Calendar.getInstance();
			double sueldoEmpleados = 0;
			double cotizacionSS = 0;
			double retencionesIRPF = 0;
			double retencionesSS = 0;
			boolean ok = true;
			
			//Recojo fecha
			Date f = calendario.getDate();
			if (f == null){
				JOptionPane.showMessageDialog(null, "Introduce la fecha correctamente");
				ok = false;
			}
			if (ok){
				fecha.setTime(f);
				if(fecha.before(Main.fechaAportacion)){
					JOptionPane.showMessageDialog(null, "La fecha en la que se realizan los sueldos y salarios tiene que ser igual o superior que la de la aportación inicial");
					ok=false;
				}
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
			
			//Retenciones IRPF
			String r1 = retencion1.getText();
			if("".equals(r1)){
				JOptionPane.showMessageDialog(null, "Introduce el % del IRPF correctamente");
				ok = false;
			}else{
				try{
					retencionesIRPF = Double.parseDouble(r1);
					if(retencionesIRPF<0 || retencionesIRPF>100){
						JOptionPane.showMessageDialog(null, "El % de la rentención del IRPF debe estar entre 0 y 100");
						ok=false;
					}
				}catch (Exception e){
					JOptionPane.showMessageDialog(null, "Introduce el % del IRPF correctamente");
					ok = false;
				}
			}
			
			//Retenciones SS
			String r2 = retencion2.getText();
			if("".equals(r2)){
				JOptionPane.showMessageDialog(null, "Introduce el % de la SS correctamente");
				ok = false;
			}else{
				try{
					retencionesSS = Double.parseDouble(r2);	
					if(retencionesSS<0 || retencionesSS>100){
						JOptionPane.showMessageDialog(null, "El % de la rentención de la SS debe estar entre 0 y 100");
						ok=false;
					}
				}catch (Exception e){
					JOptionPane.showMessageDialog(null, "Introduce el % de la SS correctamente");
					ok = false;
				}
			}
		
				
			if(ok){
				double [] inputsSueldoEmpleado = {Main.numeroSocios, sueldoEmpleados, cotizacionSS, retencionesIRPF, retencionesSS};
				sueldoEmpleado = new SueldosEmpleados(fecha, inputsSueldoEmpleado, Main.enunciadoConCuentas);
				añadeEnunciado(sueldoEmpleado.enunciados);
				Main.ejecucionAlgunAsiento = true;
				borrarButton.setEnabled(false);
				mostrarButton.setEnabled(false);
				mostrarVista();
			}
		}
	}
}
