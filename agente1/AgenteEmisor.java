package examples.agente1;

import jade.core.Agent;
import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;
import java.util.Arrays;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

public class AgenteEmisor extends Agent {

  /*protected void setup() {
  	System.out.println("Hello World! My name is "+getLocalName());
  	// Make this agent terminate
  	doDelete();
  } */

  //Leer archivo CSV
  public LeerCSV archivo = new LeerCSV();
  public String ruta = "C:/jade/src/examples/agente1/50_Startups.csv";
  public ArrayList<ArrayList<String>> datos = new ArrayList<ArrayList<String>>();

  protected void setup() {
        addBehaviour(new EmisorComportaminento());
    }

   private class EmisorComportaminento extends SimpleBehaviour {
        boolean fin = false;
      
        public void action() {
            datos = archivo.leer();
            System.out.println(getLocalName() +": Preparandose para enviar data set al receptor");
            AID id = new AID();
            id.setLocalName("r1");

            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            msg.addReceiver(id);
            String cadena = Arrays.deepToString(datos.toArray());
            msg.setSender(getAID());
            msg.setContent(cadena);
            msg.setLanguage("English");
            send(msg);

            System.out.println(getLocalName() + ": ... What are you up to");
            //System.out.println(msg.toString());
            fin = true;
        //Envia el mensaje a los destinatarios
        }
 
        public boolean done()
        {
            return fin;
        }
    }

}

