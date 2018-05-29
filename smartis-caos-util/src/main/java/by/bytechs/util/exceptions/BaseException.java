package by.bytechs.util.exceptions;

/**
 * <p>
 * It is designed to log project exceptions
 *
 * @author Romanovich Andrei
 * @see Exception
 */
public abstract class BaseException extends Exception {

    private Object[] params;

    public BaseException(Exception e, Object... params) {
        super(e);
        this.params = params;
    }

    protected String getDescription(Exception e) {
        StackTraceElement[] stacks = e.getStackTrace();
        StringBuffer messageDesc = new StringBuffer();
        Class className = null;
        if (getParams().length > 0) {
            className = (Class) getParams()[0];
        }
        for (int i = 0; i < stacks.length; i++) {
            StackTraceElement element = stacks[i];
            if (className != null) {
                if (element.getClassName().equalsIgnoreCase(className.getName())) {
                    messageDesc.append(element.getClassName() + "." + element.getMethodName() + "(" + element.getFileName()
                            + ":" + element.getLineNumber() + ")\n");
                }
            }
        }
        if (messageDesc.length() == 0) {
            StackTraceElement element = stacks[0];
            messageDesc.append(element.getClassName() + "." + element.getMethodName() + "(" + element.getFileName()
                    + ":" + element.getLineNumber() + ")");
        }
        return messageDesc.toString();
    }

    protected Object[] getParams() {
        return params;
    }

    protected void setParams(Object[] params) {
        this.params = params;
    }
}
