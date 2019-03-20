package hu.indicium.dev.lit.response;

public class ErrorResponse extends Response {
    public ErrorResponse(Object error) {
        super(null, error);
    }
}
