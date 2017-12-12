import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PongBehaviour extends Behaviour {

    private TestAgent myAgent;

    public PongBehaviour(TestAgent agent)
    {
        super();
        this.myAgent = agent;
    }
    @Override
    public void action() {
        ACLMessage receivedMessage = this.myAgent.blockingReceive();
        this.myAgent.printLog(String.format("received a message containing \"%s\"", receivedMessage.getContent()));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            this.myAgent.printLog(e.getMessage());
        }
        AID aid = findPingAgent();
        aid = aid != null ? aid : new AID("pingAgent", false);
        sendProperMessage(aid);
        sendGarbageMessage(aid);
    }

    private void sendGarbageMessage(AID aid) {
        this.myAgent.printLog("sending garbage message");
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.addReceiver(aid);
        msg.setContent("garbage$%@#%@#");
        this.myAgent.send(msg);
    }

    private void sendProperMessage(AID aid) {
        this.myAgent.printLog("sending pong message");
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.addReceiver(aid);
        msg.setContent("pong!");
        this.myAgent.send(msg);
    }

    private AID findPingAgent()
    {
        AID pingAgentAID = null;
        DFAgentDescription template = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType(TestAgent.SERVICE_TYPE);
        template.addServices(sd);
        try {
            DFAgentDescription[] result = DFService.search(myAgent, template);
            if (result.length > 0)
            {
                this.myAgent.printLog(String.format("found %d %s agents, this first one's AID is %s",
                        result.length,
                        TestAgent.SERVICE_TYPE,
                        result[0].getName().toString()));
                pingAgentAID = result[0].getName();
            }
            else
            {
                this.myAgent.printLog("could not find the ping service on yellow pages");
            }

        }
        catch (FIPAException fe) {
            fe.printStackTrace();
        }
        return pingAgentAID;
    }

    @Override
    public boolean done() {
        return false;
    }
}
