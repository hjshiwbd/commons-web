package hjin.commons.web.spring;

import hjin.commons.web.session.ISessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommonControllerAdvisor {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ISessionManager session;

    @ExceptionHandler(Exception.class)
    public String to500(Exception e) {
        logger.error("", e);
        return session.getViewPrefix() + "500";
    }
}
