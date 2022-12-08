package examples.agente1;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class AgenteReceptor extends Agent {

    protected void setup() {
        addBehaviour(new ReceptorComportaminento());
    }

    private class ReceptorComportaminento extends SimpleBehaviour {
        ArrayList<Startup> startups = new ArrayList<Startup>();
        private boolean fin = false;
        float b0 = 0, b1 = 0;

        public void action() {
            ACLMessage mensaje = receive();

            if (mensaje != null) {
                System.out.println(getLocalName() + ": acaba de recibir un mensaje: ");

                extraeCSV(mensaje.getContent().toString());
                regression();
                fin = true;
            }
        }

        public void extraeCSV(String data) {

            // Remover caracteres
            String charsToRemove = "\"[]";
            for (char c : charsToRemove.toCharArray()) {
                data = data.replace(String.valueOf(c), "");
            }

            String[] linea = data.split(",");

            float rdSpend;
            float administration;
            float mSpend;
            float profit;
            String state;

            for (int i = 5; i < linea.length; i += 0) {
                rdSpend = Float.parseFloat(linea[i++]);
                administration = Float.parseFloat(linea[i++]);
                mSpend = Float.parseFloat(linea[i++]);
                state = linea[i++];
                profit = Float.parseFloat(linea[i++]);
                Startup start = new Startup(rdSpend, administration, mSpend, profit, state);
                startups.add(start);
            }
        }

        void regression() {
            float mSpend = 0, mSpend2 = 0, profit = 0;
            float madProfit = 0;

            for (int i = 0; i < startups.size(); i++) {
                mSpend += startups.get(i).getmSpend(); // Sumatoria marketing spend
                profit += startups.get(i).getProfit(); // Sumatoria profit
                mSpend2 += Math.pow(startups.get(i).getmSpend(), 2); // Sumatoria de raíz de marketing spend
                madProfit += startups.get(i).getmSpend() * startups.get(i).getProfit(); // Sumatoria de marketing spend
                                                                                        // * profit
            }

            b1 = this.calculaBeta1(startups.size(), madProfit, mSpend, profit, mSpend2); // n,sumMarketingSpend,profit,sumRaizMarketingSpend
            b0 = this.calculaBeta0(startups.size(), profit, mSpend, b1); // n,profit,sumProfit,sumMarketingSpend,beta1;

            System.out.println("Beta 0 = " + b0 + " Beta 1 = " + b1);
            System.out.println("Predecir en función de Marketin spend a Profit");
            float x = randNum(); 
            System.out.println("Si Marketin spend es igual a: " + x + " la prediccion para Profit es: " + predict(x));
        }

        float calculaBeta1(int n, float madProfit, float mSpend, float profit, float mSpend2) {
            return (n * madProfit - mSpend * profit) / (n * mSpend2 - mSpend * mSpend);
        }

        float calculaBeta0(int n, float profit, float mSpend, float b1) {
            return (profit - b1 * mSpend) / n;
        }

        float predict(Float x) {
            return (b0 + b1 * x);
        }

        float randNum() {
            Random ran = new Random();
            float x = ran.nextFloat(500000.1f) + 100000.1f;
            return x;
        }

        public boolean done() {
            return fin;
        }
    }
}

class Startup {
    private float rdSpend;
    private float administration;
    private float mSpend;
    private float profit;
    private String state;

    Startup(float rdSpend, float administration, float mSpend, float profit, String state) {
        this.rdSpend = rdSpend;
        this.administration = administration;
        this.mSpend = mSpend;
        this.profit = profit;
        this.state = state;
    }

    float getrdSpend() {
        return rdSpend;
    }

    float getadministration() {
        return administration;
    }

    float getProfit() {
        return profit;
    }

    float getmSpend() {
        return mSpend;
    }

    String getState() {
        return state;
    }
}
