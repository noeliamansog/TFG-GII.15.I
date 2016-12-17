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

package es.ubu.inf.tfg.doc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import es.ubu.inf.tfg.main.Main;

/**
 * Clase AsientoPanel implementa el panel de un asiento contable.
 * 
 * @author Noelia Manso García
 */
public class AsientoPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Nombre del panel del asiento contable.
	 */
	protected String nombre;	
	
	protected JPanel contenedorPanel = Main.contenedorPanel;
	protected JPanel actualPanel = this;
	protected JPanel mainPanel;
	protected JPanel ordenPanel;
	protected JPanel mostrarPanel;
	private Border seleccionado;
	private Border noSeleccionado;	
	protected JSlider simbolosSlider;
	protected JSlider estadosSlider;
	protected JLabel estadosEstadoLabel;
	protected JLabel simbolosEstadoLabel;
	
	/**
	 * Lista con los enunciados de todos los asientos contables hasta el momento.
	 */
	public static ArrayList<ArrayList<Enunciado>> listaEnunciados = new ArrayList<ArrayList<Enunciado>>();
	/**
	 * Lista ordenada de los enunciados de todos los asientos contables hasta el momento.
	 */
	public static ArrayList<Enunciado> listaEnunciadosOrdenados = new ArrayList<Enunciado>();
	/**
	 * Lista de los años en que se ejecuta el asiento Dividendos.
	 */
	public static ArrayList<Integer> anoDividendo = new ArrayList<Integer>();
	/**
	 * Último año que afecta al supuesto contable.
	 */
	public static int anoFin=Main.anoInicial;
	

	/**
	 * Función que crea un panel para un asiento contable con el nombre del asiento contable pasado por parámetro.
	 * @param titulo Titulo del panel.
	 */
	public void inicializaPanel(String titulo) {
		this.noSeleccionado = new CompoundBorder(
				new EmptyBorder(5, 2, 15, 2), new TitledBorder(new LineBorder(
						new Color(0, 0, 0), 1, true), titulo, TitledBorder.LEADING, TitledBorder.TOP, null,
						new Color(51, 51, 51)));
		this.seleccionado = new CompoundBorder(new EmptyBorder(5, 2, 15, 2), new TitledBorder(
				new LineBorder(new Color(0, 255, 0), 4, true), titulo, TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(51, 51, 51)));
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setBorder(new EmptyBorder(new Insets(0, 0, 0, 0)));
				
		this.ordenPanel = new JPanel();
		this.ordenPanel.setBorder(new EmptyBorder(new Insets(0, 0, 0, 0)));
		this.ordenPanel.setLayout(new BoxLayout(this.ordenPanel, BoxLayout.Y_AXIS));
		add(this.ordenPanel);

		this.mainPanel = new JPanel();
		this.mainPanel.setBorder(this.noSeleccionado);
		this.mainPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		Dimension dim = new Dimension(510,200) ;
		this.mainPanel.setPreferredSize(dim);
		
		add(this.mainPanel);

		this.mostrarPanel = new JPanel();
		this.mostrarPanel.setBorder(new EmptyBorder(new Insets(13, 0, 16, 0)));
		this.mostrarPanel.setLayout(new BorderLayout());
		add(this.mostrarPanel);
	}

	/**
	 * Cambia el color del borde del panel.
	 */
	public void mostrarVista() {
		mainPanel.setBorder(seleccionado);
	}
	
	/**
	 * Función que va añadiendo en el panel del enunciado el enunciado de cada asiento contable 
	 * que se va ejecutando.
	 * 
	 * @param enunciado Enunciado que se desea añadir al panel.
	 */
	public static void añadeEnunciado (ArrayList<Enunciado> enunciado){	
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
		listaEnunciadosOrdenados = new ArrayList<Enunciado>();
		listaEnunciados.add(enunciado);
		
		Calendar fech;
		String enun;	
		
		//Ordenamos cada enunciado de cada asiento por fecha
		for(int i=0; i<listaEnunciados.size(); i++){
			for(int j=0; j<listaEnunciados.get(i).size(); j++){
				listaEnunciadosOrdenados.add(listaEnunciados.get(i).get(j));
				if(listaEnunciados.get(i).get(j).fecha.get(Calendar.YEAR)>anoFin){
					anoFin = listaEnunciados.get(i).get(j).fecha.get(Calendar.YEAR);
				}
			}
		}
		Collections.sort(listaEnunciadosOrdenados);
		
			
		Calendar fechaCierre = Calendar.getInstance();
		for(int anoActual=Main.anoInicial; anoActual<=anoFin; anoActual++){
			//Inicio año
			Main.textoEnunciado = Main.textoEnunciado +"<b>Se tiene la siguiente información vinculada con la empresa correspondiente al año "+anoActual+":</b><br><br>";
			
			//Asientos
			for(int i=0; i<listaEnunciadosOrdenados.size(); i++){
				if(listaEnunciadosOrdenados.get(i).fecha.get(Calendar.YEAR)==anoActual){
					fech= listaEnunciadosOrdenados.get(i).getFecha();
					enun = listaEnunciadosOrdenados.get(i).getEnunciado();
					Main.textoEnunciado = Main.textoEnunciado + formateador.format(fech.getTime())+ " " + enun + "<br><br>";
				}
			}
			
			//Fin año
			fechaCierre.set(anoActual,11,31);
			if(Main.enunciadoConCuentas){
				if(Main.conRetenciones){
					Main.textoEnunciado = Main.textoEnunciado + formateador.format(fechaCierre.getTime())+" La empresa cierra el ejercicio y deja a deber a Hacienda el impuesto de sociedades ("+Main.impuestoSociedades+"% del beneficio).\nCUENTAS PGC: 129. Resultados del ejercicio; 12. Resultados pendientes de aplicación.<br><br>";
				}else{
					Main.textoEnunciado = Main.textoEnunciado +formateador.format(fechaCierre.getTime())+" La empresa cierra el ejercicio.\nCUENTAS PGC: 129. Resultados del ejercicio; 12. Resultados pendientes de aplicación.<br><br>";
				}
			}else{
				if(Main.conRetenciones){
					Main.textoEnunciado = Main.textoEnunciado +formateador.format(fechaCierre.getTime())+" La empresa cierra el ejercicio y deja a deber a Hacienda el impuesto de sociedades ("+Main.impuestoSociedades+"% del beneficio).<br><br>";	
				}else{
					Main.textoEnunciado = Main.textoEnunciado +formateador.format(fechaCierre.getTime())+" La empresa cierra el ejercicio.<br><br>";
				}
			}	
			
		}
		
		//Imprime enunciado
		Main.textoEnunciado = Main.textoEnunciado +"<br><br>Se pide: <br>"
				 + "  a) Indique claramente cómo afectaría a cada una de las cuentas contables cada una de las "
				 + "transacciones económicas de la empresa, elaborando al mismo tiempo el balance de situación, "
				 + "la cuenta de pérdidas y ganancias y el estado de flujos de tesorería de cada año. ";
		if(Main.conIVA){
			Main.textoEnunciado = Main.textoEnunciado +"El impuesto de sociedades es el "+Main.impuestoSociedades+ "% del beneficio y el IVA es "+Main.IVA+ "%.";
		}else{
			Main.textoEnunciado  = Main.textoEnunciado +"El impuesto de sociedades es el "+Main.impuestoSociedades+ "% del beneficio.";				 
		}
		Main.textoEnunciado = Main.textoEnunciado + "<br>  b) Calcular la rentabilidad económica y la rentabilidad financiera de cada año y comprobar "
				 + "que se cumple la ecuación que las liga. <br>"
				 + "  c) Calcular y comentar el Fondo de Maniobra al final de cada año.<br>"
				 + "  d) Analizar la liquidez de la empresa mediante ratios para cada uno de los años.<br>"
				 + "  e) Analizar el endeudamiento de la empresa mediante ratios para cada uno de los años.<br>";
		
		Main.panelEnunciado.setText(Main.textoEnunciado);
		Main.textoEnunciado =  "<p align=center><font color=#6E6E6E face=impact, serif size=12><b>Enunciado del supuesto contable<br><br></b></p>";	

		Main.anoFinal = fechaCierre.get(Calendar.YEAR);
	}	
	
	
	/**
	 * Clase BotonBorrarActionListener para eliminar un panel del panel de asientos contables.
	 * 
	 * @author Noelia Manso García
	 */
	public class BotonBorrarActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			contenedorPanel.remove(actualPanel);
			contenedorPanel.revalidate();
			if(nombre=="AportaciónInicial"){
				Main.panelAportacion=null;
			}
		}
	}
	
}