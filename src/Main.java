import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.StaleProxyException;




public class Main {

    public static void main(String[] args)
    {
        // Get a hold on JADE runtime
        Runtime rt = Runtime.instance();

        // Exit the JVM when there are no more containers around
        rt.setCloseVM(true);
        System.out.print("runtime created\n");

        // Create a default profile
        Profile profile = new ProfileImpl(null, 1200, null);
        System.out.print("profile created\n");

        System.out.println("Launching a whole in-process platform..." + profile);

        // now set the default Profile to start a container
        ProfileImpl pContainer = new ProfileImpl(null, 1200, null);
        System.out.println("Launching the agent container ..." + pContainer);

        //has to be created even if not used
        jade.wrapper.AgentContainer mainContainer = rt.createMainContainer(profile);

        //the container we will use
        jade.wrapper.AgentContainer cont = rt.createAgentContainer(pContainer);
        System.out.println("Launching the agent container after ..."+pContainer);

        System.out.println("containers created");
        System.out.println("Launching the rma agent on the main container ...");

        //Run manager agent. Running this gives us a GUI we can see details of all of the agents in
//        AgentController rma = null;
        //can be done better with CreateAgent - see ams example
//            rma = mainContainer.createNewAgent("rma",
//                    "jade.tools.rma.rma", new Object[0]);
//            rma.start();

        //run an agent in the container cont. agent class is TestAgent, agent name is agent1, no params
        try
        {
            Object[] pingAgentArgs = new Object[1];
            pingAgentArgs[0] = TestAgent.PingPongType.PING;
            cont.createNewAgent("pingAgent", "TestAgent", pingAgentArgs).start();
        } catch (StaleProxyException e)
        {
            e.printStackTrace();
        }

        try
        {
            Object[] pingAgentArgs = new Object[1];
            pingAgentArgs[0] = TestAgent.PingPongType.PONG;
            cont.createNewAgent("pongAgent", "TestAgent", pingAgentArgs).start();
        } catch (StaleProxyException e)
        {
            e.printStackTrace();
        }


        //kill jade after all is done
        while (!cont.isJoined())
        {

        }
        System.out.println("joined");
        try
        {
            Thread.sleep(10000);
            cont.kill();
            mainContainer.kill();
        } catch (StaleProxyException e)
        {
            e.printStackTrace();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        rt.shutDown();
        System.out.println("done");
    }
}
