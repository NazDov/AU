package www.uni_weimar.de.au.models;

/**
 * Created by user on 22.09.17.
 */

public enum AUEventScheduleDurationTags {
    AM("am"),
    VON("von"),
    BIS("bis");

    private String durationTag;
    AUEventScheduleDurationTags(String durationTag) {
        this.durationTag = durationTag;
    }

    @Override
    public String toString() {
        return durationTag;
    }
}
