package test.struts2.exception;

public class InterceptorException extends RuntimeException {

    private static final long serialVersionUID = 8875716046701378792L;

    public InterceptorException() {
    }

    public InterceptorException(String message) {
        super(message);
    }

    public InterceptorException(String message, Throwable cause) {
        super(message, cause);
    }

}
