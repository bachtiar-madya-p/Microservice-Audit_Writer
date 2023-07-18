package id.ic.ms.audit.controller;

import id.ic.ms.audit.manager.EncryptionManager;
import id.ic.ms.audit.manager.PropertyManager;
import id.ic.ms.audit.util.http.model.HTTPContentType;
import id.ic.ms.audit.util.http.model.HTTPRequest;
import id.ic.ms.audit.util.property.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Base64;

public class BaseController{

    protected Logger log;
    
    public BaseController() {
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

    protected HTTPRequest buildTwilioRequest (String url)
    {
        return new HTTPRequest.Builder(url).setContentType(HTTPContentType.APPLICATION_JSON)
                .setAuthorization(getBasicAuthenticationHeader(getProperty(Property.TWILIO_ACCOUNT_ID),
                        EncryptionManager.getInstance().decrypt(getProperty(Property.TWILIO_AUTH_TOKEN)))).build();
    }

    private String getBasicAuthenticationHeader(String username, String password) {
        String valueToEncode = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(valueToEncode.getBytes());
    }
}
