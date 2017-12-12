import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.text.SimpleDateFormat;
import java.util.Date;


public class TestAgent extends Agent {

    public static final String SERVICE_TYPE = "pingPong";
    public static final String PING_SERVICE_NAME = "pingService";
    public static final String PONG_SERVICE_NAME = "pongService";


    public enum PingPongType
    {
        PING,
        PONG
    }
    private PingPongType behaviourType;

    @Override
    protected void setup() {
        super.setup();
        this.behaviourType = (PingPongType) getArguments()[0];
        switch (this.behaviourType)
        {
            case PING:
                createPingAgent();
                break;
            case PONG:
                addBehaviour(new PongBehaviour(this));
                break;
            default:
                throw new NotImplementedException();
        }
    }

    private void createPingAgent()
    {
        addBehaviour(new PingBehaviour(this));
        // register to the yellow pages
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType(TestAgent.SERVICE_TYPE);
        sd.setName(TestAgent.PING_SERVICE_NAME);
        dfd.addServices(sd);
        try
        {
            DFService.register(this, dfd);
        }
        catch (FIPAException e)
        {
            e.printStackTrace();
        }
        this.printLog("published ping service on DFAgent");
    }

    public void printLog(String message)
    {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String agentName = new String(this.getAID().getName().toString());
        agentName = agentName.substring(0, agentName.indexOf('@'));
        System.out.println(String.format("%s - %s: %S", timeStamp, agentName, message));
    }

}
