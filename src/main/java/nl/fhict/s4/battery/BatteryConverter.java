package nl.fhict.s4.battery;

import io.smallrye.reactive.messaging.annotations.Broadcast;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class BatteryConverter {

    private static final double ACCURACY = 0.98;

//    @Incoming("battery-levels")
//    @Outgoing("my-data-stream-2")
//    @Broadcast
//    public double process(float batteryLevel) {
//        return batteryLevel * ACCURACY;
//    }

    @Incoming("battery-levels")
    @Outgoing("my-data-stream-2")
    @Broadcast
    public double process(Double position) {
        return position;
    }

}