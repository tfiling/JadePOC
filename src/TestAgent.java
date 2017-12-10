import jade.core.Agent;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestAgent extends Agent {

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
                addBehaviour(new PingBehaviour());
                break;
            case PONG:
                addBehaviour(new PongBehaviour());
                break;
            default:
                throw new NotImplementedException();
        }
    }
}
