package examples.agente2;

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

  //Leer archivo CSV
  public LeerCSV archivo = new LeerCSV();
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
            
            fin = true;
        //Envia el mensaje a los destinatarios
        }
 
        public boolean done()
        {
            return fin;
        }
    }

}

