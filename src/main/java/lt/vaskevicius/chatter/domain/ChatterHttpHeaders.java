package lt.vaskevicius.chatter.domain;

public class ChatterHttpHeaders {

    private ChatterHttpHeaders() {
        throw new IllegalStateException("Utility class");
    }

    public static final String ACCESS_TOKEN = "Chatter-Access-Token";
}
