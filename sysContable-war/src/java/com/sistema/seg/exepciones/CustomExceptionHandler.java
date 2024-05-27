/**
 * 
 */
package com.sistema.seg.exepciones;
import javax.faces.FacesException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import java.util.Iterator;

/**
 * 
 * @author BME_PERSONAL
 */
public class CustomExceptionHandler extends ExceptionHandlerWrapper {

    /**
     * 
     */
    private ExceptionHandler wrapped;

    /**
     * 
     * @param wrapped 
     */
    public CustomExceptionHandler(ExceptionHandler wrapped) {
        this.wrapped = wrapped;
    }

    /**
     * 
     * @return 
     */
    @Override
    public ExceptionHandler getWrapped() {
        return wrapped;
    }

    /**
     * 
     * @throws FacesException 
     */
    @Override
    public void handle() throws FacesException {
        FacesContext context = FacesContext.getCurrentInstance();
        Iterator<ExceptionQueuedEvent> exceptionQueuedEvents = getUnhandledExceptionQueuedEvents().iterator();
        while (exceptionQueuedEvents.hasNext()) {
            ExceptionQueuedEvent event = exceptionQueuedEvents.next();
            ExceptionQueuedEventContext exceptionContext = (ExceptionQueuedEventContext) event.getSource();
            Throwable throwable = exceptionContext.getException();
            if (throwable instanceof javax.faces.application.ViewExpiredException) {
                // Manejar la excepción aquí, por ejemplo, redirigir a una página de inicio de sesión
                try {
                    context.getExternalContext().redirect("login.xhtml");
                    context.responseComplete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }
        }
        // Delegar a la implementación predeterminada
        getWrapped().handle();
    }
}
