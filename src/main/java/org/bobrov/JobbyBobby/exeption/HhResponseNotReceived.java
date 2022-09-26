package org.bobrov.JobbyBobby.exeption;

public class HhResponseNotReceived extends RuntimeException {
    public HhResponseNotReceived() {
    }

    public HhResponseNotReceived(String message) {
        super(message);
    }

    public HhResponseNotReceived(String message, Throwable cause) {
        super(message, cause);
    }

    public HhResponseNotReceived(Throwable cause) {
        super(cause);
    }
}
