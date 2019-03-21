package hu.indicium.dev.lit.response;

public class DeleteStatus {
    private boolean success;

    public DeleteStatus(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
