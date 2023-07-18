package id.ic.ms.audit.util.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseHttpClient {

    public BaseHttpClient() {
        //Empty constructor
    }

    protected Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }
}
