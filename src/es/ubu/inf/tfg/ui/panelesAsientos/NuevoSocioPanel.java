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

import es.ubu.inf.tfg.asientosContables.NuevoSocio;
import es.ubu.inf.tfg.doc.AsientoPanel;
import es.ubu.inf.tfg.main.Main;

/**
 * Clase NuevoSocioPanel que implementa lo que debe aparecer en el panel para el asiento NuevoSocio.
 * 
 * @author Noelia Manso García
 */
public class NuevoSocioPanel extends AsientoPanel {

	private static final long serialVersionUID = -1805230103073818602L;

	private JButton borrarButton;
	private JButton mostrarButton;
	private JDateChooser calendario;
	private JTextField aporte;
	/**
	 * Supuesto contable nuevoSocio.
	 */
	public static NuevoSocio nuevoSocio;
	
	/**
	 * Constructor de la clase NuevoSocioPanel que genera lo que debe aparecer en el panel para 
	 * el asiento NuevoSocio.
	 */
	public NuevoSocioPanel(){
		this.nombre = "NuevoSocio";
		
		inicializaPanel("Nuevo socio");	
		
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
		mainPanel.add(new JLabel(" Se incorpora un nuevo socio a la empresa, el cual "));
		mainPanel.add(new JLabel("aporta"));

		this.aporte = new JTextField(6);
		mainPanel.add(this.aporte);
		
		mainPanel.add(new JLabel("€ en efectivo (los cuales se ingresan en cuenta corriente)"));
		mainPanel.add(new JLabel("a cambio de una acción de nueva emisión."));

	}
	
	/**
	 * Clase BotonMostrarActionListener para controlar los datos que introduce el usuario.
	 * 
	 * @author Noelia Manso García.
	 */
	private class BotonMostrarActionListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			Calendar fecha = Calendar.getInstance();
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
				if(fecha.before(Main.fechaAportacion)){
					JOptionPane.showMessageDialog(null, "La fecha en la que se incorpora un nuevo socio tiene que ser igual o superior que la de la aportación inicial");
					ok=false;
				}
			}
			
			//Aportación
			String a = aporte.getText();
			if("".equals(a)){
				JOptionPane.showMessageDialog(null, "Introduce la aportación correctamente");
				ok = false;
			}else{
				try{
					aportacion = Double.parseDouble(a);	
				}catch (Exception e){
					JOptionPane.showMessageDialog(null, "Introduce la aportación correctamente");
					ok = false;
				}
			}
				
			if(ok){
				double numAcciones = Main.numeroSocios;
				double [] inputsNuevoSocio = {aportacion, numAcciones};
				nuevoSocio = new NuevoSocio(fecha, inputsNuevoSocio, Main.enunciadoConCuentas);
				añadeEnunciado(nuevoSocio.enunciados);
				Main.ejecucionAlgunAsiento = true;
				borrarButton.setEnabled(false);
				mostrarButton.setEnabled(false);
				mostrarVista();
			}
		}
	}
}
