package nl.fhict.s4;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import io.smallrye.reactive.messaging.connectors.InMemoryConnector;

import java.util.HashMap;
import java.util.Map;

public class KafkaResource implements QuarkusTestResourceLifecycleManager {



    @Override
    public Map<String, String> start() {
        Map<String, String> env = new HashMap<>();
        Map<String, String> props1 = InMemoryConnector.switchIncomingChannelsToInMemory("events");
        Map<String, String> props2 = InMemoryConnector.switchOutgoingChannelsToInMemory("generated-event");
        Map<String, String> props3 = InMemoryConnector.switchOutgoingChannelsToInMemory("event-create");
        
        env.putAll(props1);
        env.putAll(props2);
        env.putAll(props3);

        return env;
    }

    @Override
    public void stop() {
        InMemoryConnector.clear();
    }
}
