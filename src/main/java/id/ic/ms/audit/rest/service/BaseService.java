package id.ic.ms.audit.rest.service;

import id.ic.ms.audit.rest.model.response.ServiceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import id.ic.ms.audit.manager.PropertyManager;
import id.ic.ms.audit.util.json.JsonUtil;

public class BaseService {

	protected Logger log;

	public BaseService() {
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

	protected String getProperty(String key) {
		return PropertyManager.getInstance().getProperty(key);
	}

	protected int getIntProperty(String key) {
		return PropertyManager.getInstance().getIntProperty(key);
	}

	protected boolean getBooleanProperty(String key) {
		return PropertyManager.getInstance().getBooleanProperty(key);
	}

	protected void logRequest(String methodName, Object object) {
		log.debug("[{}] Request : {}", methodName, JsonUtil.toJson(object));
	}

	protected void logDebug(String methodName, String str) {
		log.debug("[{}] Debug : {}", methodName, str);
	}

	protected void logError(String methodName, Object object) {
		log.error("[{}] Error : {}", methodName, object);
	}

	protected void logResponse(String methodName, Object object) {
		log.debug("[{}] Response : {}", methodName, JsonUtil.toJson(object));
	}

	// Success response
	protected ResponseEntity<ServiceResponse> buildSuccessResponse() {
		return buildServiceResponseEntity(HttpStatus.OK);
	}

	protected <T> ResponseEntity<T> buildSuccessResponse(@Nullable T body) {
		return new ResponseEntity<>(body, HttpStatus.OK);
	}

	// Bad request response
	protected ResponseEntity<ServiceResponse> buildBadRequestResponse() {
		return buildServiceResponseEntity(HttpStatus.BAD_REQUEST, "Bad request");
	}

	protected ResponseEntity<ServiceResponse> buildBadRequestResponse(String message) {
		return new ResponseEntity<>(new ServiceResponse(HttpStatus.BAD_REQUEST, message), HttpStatus.BAD_REQUEST);
	}

	protected ResponseEntity<ServiceResponse> buildServiceResponseEntity(HttpStatus status, String message) {
		return new ResponseEntity<>(new ServiceResponse(status, message), status);
	}

	protected ResponseEntity<ServiceResponse> buildServiceResponseEntity(HttpStatus status) {
		return new ResponseEntity<>(new ServiceResponse(status), status);
	}

}
