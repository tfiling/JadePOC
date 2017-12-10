import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PingBehaviour extends Behaviour {

    public PingBehaviour()
    {
        super();
    }
    @Override
    public void action() {
        printLog("sending ping message");
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.addReceiver(new AID("pongAgent", false));
        msg.setContent("ping!");
        this.myAgent.send(msg);
        ACLMessage receivedMessage = this.myAgent.blockingReceive();
        printLog(String.format("received a message containing \"%s\"", receivedMessage.getContent()));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            printLog(e.getMessage());
        }
    }

    @Override
    public boolean done() {
        return false;
    }

    private void printLog(String message)
    {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        System.out.println(String.format("%s - %s: %S", timeStamp, this.myAgent.getAID().toString(), message));
    }
}
