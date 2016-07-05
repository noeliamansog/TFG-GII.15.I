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

import es.ubu.inf.tfg.asientosContables.AportacionInicial;
import es.ubu.inf.tfg.doc.AsientoPanel;
import es.ubu.inf.tfg.main.Main;

/**
 * Clase AportacionPanel que implementa lo que debe aparecer en el panel para el asiento AportacionInicial.
 * 
 * @author Noelia Manso García
 */
public class AportacionPanel extends AsientoPanel {

	private static final long serialVersionUID = -4728031249878756673L;
	private JButton borrarButton;
	private JButton mostrarButton;
	private JDateChooser calendario;
	private JTextField socios;
	private JTextField importe;
	
	/**
	 * Asiento contable aportación inicial.
	 */
	public static AportacionInicial aportacionInicial;
	
	
	/**
	 * Constructor de la clase AportacionPanel que genera lo que debe aparecer en el panel para 
	 * el asiento AportacionInicial.
	 */
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
		this.calendario.setDate(fechaMinima.getTime());
		mainPanel.add(calendario);
		

		//Texto
		mainPanel.add(new JLabel("Cada uno de los"));
		
		this.socios = new JTextField(2);
		mainPanel.add(this.socios);
		mainPanel.add(new JLabel ("socios fundadores realiza una"));
		
		mainPanel.add(new JLabel ("aportación inicial en efectivo a favor de la empresa por importe de"));
		
		this.importe = new JTextField(5);
		mainPanel.add(this.importe);

		mainPanel.add(new JLabel("€. Cada socio tiene una acción. La empresa ingresa el dinero recibido en"));
		mainPanel.add(new JLabel("su cuenta corriente."));
		
	}

	/**
	 * Clase BotonMostrarActionListener para controlar los datos que introduce el usuario.
	 * 
	 * @author Noelia Manso García.
	 */
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
					Main.valorNominal = aportacion;
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

