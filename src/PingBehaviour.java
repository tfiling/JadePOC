import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PingBehaviour extends Behaviour {

    private static MessageTemplate expectedMessagesTemplate = MessageTemplate.MatchContent("pong!");
    private TestAgent myAgent;

    public PingBehaviour(TestAgent agent)
    {
        super();
        this.myAgent = agent;
    }
    @Override
    public void action() {
        this.myAgent.printLog("sending ping message");
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.addReceiver(new AID("pongAgent", false));
        msg.setContent("ping!");
        this.myAgent.send(msg);
        ACLMessage receivedMessage = this.myAgent.blockingReceive(this.expectedMessagesTemplate);
        this.myAgent.printLog(String.format("received a message containing \"%s\"", receivedMessage.getContent()));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            this.myAgent.printLog(e.getMessage());
        }
    }

    @Override
    public boolean done() {
        return false;
    }

}
