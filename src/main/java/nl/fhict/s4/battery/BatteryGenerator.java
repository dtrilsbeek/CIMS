package nl.fhict.s4.battery;

import io.reactivex.Flowable;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.enterprise.context.ApplicationScoped;
import java.util.Random;
import java.util.concurrent.TimeUnit;


@ApplicationScoped
public class BatteryGenerator {
    private final Random random = new Random();
    private float batteryLevel = 100;

    @Outgoing("generated-battery")
    public Flowable<Float> generate() {

        return Flowable.interval(5, TimeUnit.SECONDS)
                .map(tick -> this.getBatteryLevel());
    }

    private Float getBatteryLevel() {
        batteryLevel -= 0.1;

        if (batteryLevel < 0.2) {
            batteryLevel = 100;
        }

        return batteryLevel;
    }

}