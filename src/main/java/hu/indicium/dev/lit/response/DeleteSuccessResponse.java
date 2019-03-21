package hu.indicium.dev.lit.response;

public class DeleteSuccessResponse extends Response {
    public DeleteSuccessResponse() {
        super(new DeleteStatus(true), null);
    }
}
