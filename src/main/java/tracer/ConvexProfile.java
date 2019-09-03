package tracer;

import com.flash3388.flashlib.time.Time;

public class ConvexProfile extends Profile {
    private final double maxAcceleration;
    private final double maxJerk;

    private final double initialVelocity;

    public ConvexProfile(double initialDistance, double initialVelocity, MotionParameters max, Time startTime) {
        super(initialDistance, initialVelocity, max, startTime, calcDuration(max));

        maxAcceleration = getMaxAcceleration();
        maxJerk = getMaxJerk();

        this.initialVelocity = getInitialVelocity();
    }

    private static Time calcDuration(MotionParameters max) {
        return Time.seconds(max.getAcceleration()/max.getJerk());
    }

    @Override
    public double relativeVelocityAt(double t) {
        return maxAcceleration * t -maxJerk * Math.pow(t, 2)/2;
    }

    @Override
    public double relativeDistanceAt(double t) {
        return initialVelocity * t + maxAcceleration * Math.pow(t, 2)/2 -maxJerk * Math.pow(t, 3)/6;
    }
}
