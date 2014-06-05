package org.turbogwt.mvp.databind.client;

public class MistypedBindingException extends RuntimeException {

    public MistypedBindingException() {
    }

    public MistypedBindingException(String s) {
        super(s);
    }

    public MistypedBindingException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public MistypedBindingException(Throwable throwable) {
        super(throwable);
    }
}
