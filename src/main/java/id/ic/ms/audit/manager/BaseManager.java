package id.ic.ms.audit.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseManager {

    protected Logger log;

    public BaseManager() {
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

    protected String getProp(String key) {
        return PropertyManager.getInstance().getProperty(key);
    }
}
