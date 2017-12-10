import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PongBehaviour extends Behaviour {

    public PongBehaviour()
    {
        super();
    }
    @Override
    public void action() {
        ACLMessage receivedMessage = this.myAgent.blockingReceive();
        printLog(String.format("received a message containing \"%s\"", receivedMessage.getContent()));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            printLog(e.getMessage());
        }
        sendProperMessage();
        sendGarbageMessage();
    }

    private void sendGarbageMessage() {
        printLog("sending garbage message");
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.addReceiver(new AID("pingAgent", false));
        msg.setContent("garbage$%@#%@#");
        this.myAgent.send(msg);
    }

    private void sendProperMessage() {
        printLog("sending pong message");
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.addReceiver(new AID("pingAgent", false));
        msg.setContent("pong!");
        this.myAgent.send(msg);
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
