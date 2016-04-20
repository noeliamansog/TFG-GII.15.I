package es.ubu.inf.tfg.SupuestoContable;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Solucion {
	
	public static void main(String[] args) throws IOException {
        String ruta = "/Users/noelia/Desktop/solución.txt";
        File enunciado = new File(ruta);
        BufferedWriter bw = new BufferedWriter(new FileWriter(enunciado));
        

        bw.write("\n SOLUCIÓN: \n");
        
        bw.close();
	}
}
	
