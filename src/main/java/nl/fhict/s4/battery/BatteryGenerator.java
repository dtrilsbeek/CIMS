package nl.fhict.s4.battery;

import io.reactivex.Flowable;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.TimeUnit;


@ApplicationScoped
public class BatteryGenerator {
    private final double start = 51.451069;
    private final double end = 51.462120;
    private  double position = start;
    private double increment = 0.001505;

    private float batteryLevel = 100;

    @Outgoing("generated-battery")
    public Flowable<Double> generate() {
        return Flowable.interval(2, TimeUnit.SECONDS)
                .onBackpressureDrop()
                .map(tick -> getLocation());
    }

    private double getLocation(){
        position += increment;

        if(position <= start || position >= end ){
            increment *= -1;
        }

        return position;
    }

    private Float getBatteryLevel() {
        batteryLevel -= 0.1;

        if (batteryLevel < 0.2) {
            batteryLevel = 100;
        }

        return batteryLevel;
    }

}