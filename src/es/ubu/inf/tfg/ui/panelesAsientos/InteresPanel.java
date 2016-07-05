/* GSC
 * GSC es una aplicación que permite la creación de supuestos contables 
 * personalizados y los resuelve de forma automática.
 * Copyright (C) 2016 Noelia Manso & Luis R. Izquierdo
 *
 * This file is part of GSC.
 *
 * GSC is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * GSC is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with GSC.  If not, see <http://www.gnu.org/licenses/>.
 */

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

import es.ubu.inf.tfg.asientosContables.Interes;
import es.ubu.inf.tfg.doc.AsientoPanel;
import es.ubu.inf.tfg.main.Main;

/**
 * Clase InteresPanel que implementa lo que debe aparecer en el panel para el asiento Intereses.
 * 
 * @author Noelia Manso García
 */
public class InteresPanel extends AsientoPanel {

	private static final long serialVersionUID = -1805230103073818602L;
	
	private JButton borrarButton;
	private JButton mostrarButton;
	private JDateChooser calendario;
	private JTextField ingreso;
	private JTextField porcentaje;
	/**
	 * Asiento contable Intereses.
	 */
	public static Interes intereses;
	
	/**
	 * Constructor de la clase InteresPanel que genera lo que debe aparecer en el panel para 
	 * el asiento Intereses.
	 */
	public InteresPanel(){
		this.nombre = "Interes";
		
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
		this.calendario.setDate(fechaMinima.getTime());
		mainPanel.add(calendario);
		
		//CON RETENCIONES
		if(Main.conRetenciones){
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
			
		//SIN RETENCIONES	
		}else{
			//Texto
			mainPanel.add(new JLabel(" La empresa obtiene un ingreso de"));
			
			this.ingreso = new JTextField(6);
			mainPanel.add(this.ingreso);
			
			mainPanel.add(new JLabel("€ por"));
			mainPanel.add(new JLabel("intereses devengados en la cuenta corriente durante este año."));
		}
	}
	
	/**
	 * Clase BotonMostrarActionListener para controlar los datos que introduce el usuario.
	 * 
	 * @author Noelia Manso García.
	 */
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
			if(Main.conRetenciones){
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
			}
				
			if(ok){
				//CON RETENCIONES
				if(Main.conRetenciones){
					double [] inputsIntereses = {ingresoIntereses, porcentajeCobrado};
					intereses = new Interes(fecha, inputsIntereses, Main.enunciadoConCuentas);
				}else{
					double [] inputsInteresesSinR = {ingresoIntereses};
					intereses = new Interes(fecha, inputsInteresesSinR, Main.enunciadoConCuentas);
				}
				
				añadeEnunciado(intereses.enunciados);
				Main.ejecucionAlgunAsiento = true;
				borrarButton.setEnabled(false);
				mostrarButton.setEnabled(false);
				mostrarVista();
			}
		}
	}
}
