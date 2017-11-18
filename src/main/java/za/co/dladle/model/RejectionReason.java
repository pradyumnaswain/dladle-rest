package za.co.dladle.model;

/**
 * Created by prady on 11/19/2017.
 */
public enum RejectionReason {
    PRICE_HIGH("Price looks too high"),
    VENDOR_INAPPROPRIATE("Vendor does not look Appropriate"),
    VENDOR_INEXPERIENCED("Vendor seems like inexperienced"),
    VENDOR_RATING_BAD("Vendor rating looks very bad"),
    OTHER("Other");
    private final String id;

    private RejectionReason(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
}
