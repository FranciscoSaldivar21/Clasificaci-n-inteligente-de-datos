package examples.agente3;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.SortOrder;

public class AgenteReceptor extends Agent {
    ArrayList<Performance> performances = new ArrayList<Performance>();
    GUI myGui;
    //Matriz hecha para manipular los datos mas facil
    double[][] matrizDatos;
    double beta_0 = 0, beta_1 = 0;

    //Aprendizaje e iteraciones
    /*double alpha = 0.003;
    int iteraciones = 500; */
    double alpha = 0.1;
    int iteraciones = 50;

    private static final int X_POSITION = 0;
    private static final int Y_POSITION = 1;

    protected void setup() {
        addBehaviour(new ReceptorComportaminento());
        myGui = new GUI(this);
    }

    private class ReceptorComportaminento extends SimpleBehaviour {
        private boolean fin = false;

        public void action() {
            ACLMessage mensaje = receive();

            if (mensaje != null) {
                System.out.println(getLocalName() + ": acaba de recibir un mensaje: ");

                extraeCSV(mensaje.getContent().toString());
                entrenar();
                System.out.println("Betas: "+beta_0+" "+beta_1);
                myGui.showGui();
                fin = true;
            }
        }
        public boolean done() {
            return fin;
        }
    }



    public void extraeCSV(String data) {
        // Remover caracteres
        String charsToRemove = "\"[]";
        for (char c : charsToRemove.toCharArray()) {
            data = data.replace(String.valueOf(c), "");
        }

        String[] linea = data.split(",");

        double mathScore;
        double readscore;


        for (int i = 0; i < linea.length-1; i += 0) {
            mathScore = Double.parseDouble(linea[i++]);
            readscore = Double.parseDouble(linea[i++]);
            Performance start = new Performance(mathScore, readscore);
            performances.add(start);
        }


        //Tamaño de matriz
        //Filas
        int filas = this.performances.size();
        int columnas = 2;
        matrizDatos = new double[filas][columnas];

        //Guardar datos en matriz
        for (int i = 0; i < matrizDatos.length; i++){
            matrizDatos[i][0] = performances.get(i).getMathScore(); //MathScore
            matrizDatos[i][1] = performances.get(i).getReadScore(); //ReadScore
        }
    }


public double predecir(double x) {
        return (beta_1 * x) + beta_0;
    }

    private double error(double y_i, double y_p) {
        return y_i - y_p;
    }

    private double partialDerivB1(double x, double y, int n) {
        var e = error(y, predecir(x));

        return ((double) (-2) / n) * x * e;
    }

    private double partialDerivB0(double x, double y, int n) {
        var e = error(y, predecir(x));
 
        return ((double) (-2) / n) * e;
    } 

    private void gradiente_descendente(double alpha, int iteraciones) {
        int N = matrizDatos.length;

        for (int i = 0; i < iteraciones; i++) {
            for (int j = 0; j < matrizDatos.length; j++) {
                var x_i = matrizDatos[j][X_POSITION];
                var y_i = matrizDatos[j][Y_POSITION];

                beta_0 -= (alpha * partialDerivB0(x_i, y_i, N));
                beta_1 -= (alpha * partialDerivB1(x_i, y_i, N));
            }
        }
    }

    public double getBeta_0() {
        return beta_0;
    }

    public double getBeta_1() {
        return beta_1;
    }

    public void entrenar() {
        gradiente_descendente(alpha, iteraciones);
    }
}

class Performance {
    private double mathScore;
    private double readscore;

    Performance(double mathScore, double readscore){
        this.mathScore = mathScore;
        this.readscore = readscore;
    }

    double getMathScore() {
        return mathScore;
    }

    double getReadScore() {
        return readscore;
    }
}
