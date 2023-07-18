package id.ic.ms.audit.repository;

import id.ic.ms.audit.manager.PropertyManager;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseRepository{

    @Autowired
    protected Jdbi jdbi;

    protected Logger log;

    public BaseRepository() {
        // Empty Constructor
    }
    
    protected Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    protected void start(String methodName) {
        log.debug("[{}] Start", methodName);
    }

    protected void completed(String methodName) {
        log.debug("[{}] Completed", methodName);
    }

    protected void logRequest(String methodName, Object object) {
        log.debug("[{}] Request : {}", methodName, object);
    }

    protected void logDebug(String methodName, Object object) {
        log.debug("[{}] Debug : {}", methodName, object);
    }

    protected void logDebug(String methodName, String str) {
        log.error("[{}] Debug : {}", methodName, str);
    }

    protected void logError(String methodName, String str) {
        log.error("[{}] Error : {}", methodName, str);
    }

    protected void logError(String methodName, Object object) {
        log.error("[{}] Error : {}", methodName, object);
    }

    protected void logResponse(String methodName, Object object) {
        log.debug("[{}] Response : {}", methodName, object);
    }

    protected String getProperty(String key) {
        return PropertyManager.getInstance().getProperty(key);
    }

    protected int getIntProperty(String key) {
        return PropertyManager.getInstance().getIntProperty(key);
    }

    protected boolean getBoolProperty(String key) {
        return PropertyManager.getInstance().getBooleanProperty(key);
    }
    
    protected Handle getHandle() {
        return jdbi.open();
    }

    protected boolean executeUpdate(Update update) {
        return update.execute() > 0;
    }

    protected int execute(Update update) {
        return update.execute();
    }
}
