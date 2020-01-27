package tracer.profiles.base;

import com.flash3388.flashlib.time.Time;
import tracer.profiles.ProfileState;

public class LinkedProfile extends BaseProfile {
    private final DelegatedProfile next;
    private final Profile current;

    public LinkedProfile(Profile prevProfile, Profile profile, Profile next) {
        this(prevProfile.finalState(), profile, next);
    }

    public LinkedProfile(ProfileState initialState, Profile profile, Profile next) {
        super(initialState);
        current = profile;
        this.next = new DelegatedProfile(current.finalState().sub(initialState), next);
    }

    @Override
    public Time duration() {
        return current.deltaState().timestamp().add(next.duration());
    }

    @Override
    protected ProfileState relativeProfileState(Time relativeTime) {
        if(relativeTime.after(current.finalState().timestamp()))
            return next.state(relativeTime);
        else
            return current.state(relativeTime);
    }
}
