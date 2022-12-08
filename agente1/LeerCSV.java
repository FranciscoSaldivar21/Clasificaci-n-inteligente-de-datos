package examples.agente1;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class LeerCSV{
    public ArrayList<ArrayList<String>> leer(){
        ArrayList<ArrayList<String>> datos = new ArrayList<ArrayList<String>>();
        Path filePath = Paths.get("C:/jade/src/examples/agente1/50_Startups.csv");
        try {
            BufferedReader br = Files.newBufferedReader(filePath);
            String linea;
            while((linea = br.readLine()) != null){
                String[] datosDeLinea = linea.split(",");
                ArrayList<String> datosTmp = new ArrayList<String>();
                for(String dato : datosDeLinea){
                    datosTmp.add(dato);
                }
                datos.add(datosTmp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(datos);
        return datos;
    }
}