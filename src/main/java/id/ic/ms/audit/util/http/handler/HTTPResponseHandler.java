package id.ic.ms.audit.util.http.handler;

import id.ic.ms.audit.util.http.model.HTTPResponse;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import id.ic.ms.audit.util.http.BaseHttpClient;


import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

public class HTTPResponseHandler extends BaseHttpClient implements ResponseHandler<HTTPResponse> {
    private final Logger log;

    public HTTPResponseHandler() {
        super();
        log =  getLogger(this.getClass());
    }

    @Override
    public HTTPResponse handleResponse(HttpResponse httpResponse) throws IOException {
        HTTPResponse response = new HTTPResponse();
        response.setBody(processHttpResponse(httpResponse));
        response.setCode(httpResponse.getStatusLine().getStatusCode());
        response.setHeaders(Arrays.asList(httpResponse.getAllHeaders()));

        // Close HttpResponse
        HttpClientUtils.closeQuietly(httpResponse);

        return response;
    }

    private String processHttpResponse(HttpResponse response) {
        StringBuilder builder = new StringBuilder();

        HttpEntity entity = response.getEntity();

        try (InputStream instream = entity.getContent(); Scanner sc = new Scanner(instream, "UTF-8")) {

            while (sc.hasNextLine()) {
                builder.append(sc.nextLine());
            }
            EntityUtils.consume(entity);
        } catch (Exception ex) {
            log.error("processHttpResponse", ex);
        }

        return builder.toString();
    }

}
