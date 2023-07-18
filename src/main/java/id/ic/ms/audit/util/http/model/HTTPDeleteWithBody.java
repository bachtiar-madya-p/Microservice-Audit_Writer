package id.ic.ms.audit.util.http.model;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

import java.net.URI;

public class HTTPDeleteWithBody extends HttpEntityEnclosingRequestBase {
    public static final String METHOD_NAME = "DELETE";

    public String getMethod() {
        return METHOD_NAME;
    }

    public HTTPDeleteWithBody(final String uri) {
        super();
        setURI(URI.create(uri));
    }

    public HTTPDeleteWithBody(final URI uri) {
        super();
        setURI(uri);
    }

    public HTTPDeleteWithBody() {
        super();
    }
}
