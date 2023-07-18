package id.ic.ms.audit.util.http;

import id.ic.ms.audit.manager.PropertyManager;
import id.ic.ms.audit.util.http.handler.HTTPResponseHandler;
import id.ic.ms.audit.util.http.helper.HTTPClientHelper;
import id.ic.ms.audit.util.http.model.*;
import id.ic.ms.audit.util.property.Property;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.ssl.SSLContextBuilder;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Component
public class HTTPClient extends BaseHttpClient {
    private static Logger log;
    private static final HTTPResponseHandler responseHandler = new HTTPResponseHandler();

    private static final String PUT = "put";
    private static final String DELETE = "delete";
    private static final String POST = "post";

    private HTTPClient() {
        log = getLogger(this.getClass());
    }

    // GET METHODS
    public static HTTPResponse get(final HTTPRequest request) {
        return get(request, null);
    }

    public static HTTPResponse get(final String url) {
        return get(new HTTPRequest.Builder(url).build(), null);
    }

    public static HTTPResponse get(final String url, HTTPHeader headers) {
        HTTPRequest request = new HTTPRequest.Builder(url).addHeaders(headers).build();

        return get(request, null);
    }

    public static HTTPResponse get(final HTTPRequest request, HTTPParameter parameters) {

        HTTPResponse response = new HTTPResponse();

        CookieStore cookieStore = HTTPClientHelper.getCookieStore(request);

        try (CloseableHttpClient client = getHttpClient(cookieStore, request.getUrl())) {
            // Construct URI
            URIBuilder builder = new URIBuilder(request.getUrl());

            // Add URL Parameters
            if (HTTPClientHelper.validateHttpParameter(parameters)) {
                parameters.entrySet().forEach(entry -> builder.addParameter(entry.getKey(), entry.getValue()));
            }
            log.info(builder.toString());
            HttpGet httpRequest = new HttpGet(builder.build());

            // Set Headers
            HTTPClientHelper.populateHeader(httpRequest, request.getHeaders());

            // Execute Request
            response = execute(client, httpRequest, cookieStore);

        } catch (Exception ex) {
            log.error("get", ex.getMessage(), ex);
        }
        return response;
    }

    // POST METHODS
    public static HTTPResponse post(final HTTPRequest request, HTTPParameter params) {
        HTTPResponse response = new HTTPResponse();
        CookieStore cookieStore = HTTPClientHelper.getCookieStore(request);

        try (CloseableHttpClient client = getHttpClient(cookieStore, request.getUrl())) {

            HttpPost httpRequest = new HttpPost(request.getUrl());

            // Set Headers
            HTTPClientHelper.populateHeader(httpRequest, request.getHeaders());

            if (HTTPClientHelper.validateHttpParameter(params)) {

                // Set Parameters
                List<NameValuePair> parameterList = HTTPClientHelper.getNVPList(params);

                httpRequest.setEntity(new UrlEncodedFormEntity(parameterList));
            }

            // Execute Request
            response = execute(client, httpRequest, cookieStore);

        } catch (Exception ex) {
            log.error(POST, ex.getMessage(), ex);
        }
        return response;
    }

    public static HTTPResponse post(final HTTPRequest request, String body) {

        HTTPResponse result = new HTTPResponse();
        CookieStore cookieStore = HTTPClientHelper.getCookieStore(request);

        try (CloseableHttpClient client = getHttpClient(cookieStore, request.getUrl())) {

            HttpPost httpRequest = new HttpPost(request.getUrl());

            // Set Headers
            HTTPClientHelper.populateHeader(httpRequest, request.getHeaders());

            if (body != null && !body.isEmpty()) {
                // Set Request Body
                httpRequest.setEntity(new StringEntity(body));
            }

            // Execute Request
            result = execute(client, httpRequest, cookieStore);

        } catch (Exception ex) {
            log.error(POST, ex.getMessage(), ex);
        }
        return result;
    }

    public static HTTPResponse postEncoded(final HTTPRequest request, String body) {

        HTTPResponse result = new HTTPResponse();
        CookieStore cookieStore = HTTPClientHelper.getCookieStore(request);

        try (CloseableHttpClient client = getHttpClient(cookieStore, request.getUrl())) {

            HttpPost httpRequest = new HttpPost(request.getUrl());

            // Set Headers
            HTTPClientHelper.populateHeader(httpRequest, request.getHeaders());

            if (body != null && !body.isEmpty()) {
                // Set Request Body
                httpRequest.setEntity(new StringEntity(body, StandardCharsets.UTF_8));
            }

            // Execute Request
            result = execute(client, httpRequest, cookieStore);

        } catch (Exception ex) {
            log.error(POST, ex.getMessage(), ex);
        }
        return result;
    }

    // PUT METHODS
    public static HTTPResponse put(final HTTPRequest request, HTTPParameter params) {
        HTTPResponse result = new HTTPResponse();
        CookieStore cookieStore = HTTPClientHelper.getCookieStore(request);

        try (CloseableHttpClient client = getHttpClient(cookieStore, request.getUrl())) {

            HttpPut httpRequest = new HttpPut(request.getUrl());

            // Set Headers
            HTTPClientHelper.populateHeader(httpRequest, request.getHeaders());

            if (HTTPClientHelper.validateHttpParameter(params)) {

                // Set Parameters
                List<NameValuePair> parameterList = HTTPClientHelper.getNVPList(params);

                httpRequest.setEntity(new UrlEncodedFormEntity(parameterList));
            }

            // Execute Request
            result = execute(client, httpRequest, cookieStore);

        } catch (Exception ex) {
            log.error(PUT, ex.getMessage(), ex);
        }
        return result;
    }

    public static HTTPResponse put(final HTTPRequest request, String body) {

        HTTPResponse result = new HTTPResponse();
        CookieStore cookieStore = HTTPClientHelper.getCookieStore(request);

        try (CloseableHttpClient client = getHttpClient(cookieStore, request.getUrl())) {

            HttpPut httpRequest = new HttpPut(request.getUrl());

            // Set Headers
            HTTPClientHelper.populateHeader(httpRequest, request.getHeaders());

            if (body != null && !body.isEmpty()) {
                // Set Request Body
                httpRequest.setEntity(new StringEntity(body));
            }

            // Execute Request
            result = execute(client, httpRequest, cookieStore);

        } catch (Exception ex) {
            log.error(PUT, ex.getMessage(), ex);
        }
        return result;
    }

    // PATCH METHODS
    public static HTTPResponse patch(final HTTPRequest request, HTTPParameter params) {
        HTTPResponse result = new HTTPResponse();
        CookieStore cookieStore = HTTPClientHelper.getCookieStore(request);

        try (CloseableHttpClient client = getHttpClient(cookieStore, request.getUrl())) {

            HttpPatch httpRequest = new HttpPatch(request.getUrl());

            // Set Headers
            HTTPClientHelper.populateHeader(httpRequest, request.getHeaders());

            if (HTTPClientHelper.validateHttpParameter(params)) {

                // Set Parameters
                List<NameValuePair> parameterList = HTTPClientHelper.getNVPList(params);

                httpRequest.setEntity(new UrlEncodedFormEntity(parameterList));
            }

            // Execute Request
            result = execute(client, httpRequest, cookieStore);

        } catch (Exception ex) {
            log.error(PUT, ex.getMessage(), ex);
        }
        return result;
    }

    public static HTTPResponse patch(final HTTPRequest request, String body) {

        HTTPResponse result = new HTTPResponse();
        CookieStore cookieStore = HTTPClientHelper.getCookieStore(request);

        try (CloseableHttpClient client = getHttpClient(cookieStore, request.getUrl())) {

            HttpPatch httpRequest = new HttpPatch(request.getUrl());

            // Set Headers
            HTTPClientHelper.populateHeader(httpRequest, request.getHeaders());

            if (body != null && !body.isEmpty()) {
                // Set Request Body
                httpRequest.setEntity(new StringEntity(body));
            }

            // Execute Request
            result = execute(client, httpRequest, cookieStore);

        } catch (Exception ex) {
            log.error(PUT, ex.getMessage(), ex);
        }
        return result;
    }


    // DELETE METHODS
    public static HTTPResponse delete(final HTTPRequest request) {
        HTTPResponse response = new HTTPResponse();
        CookieStore cookieStore = HTTPClientHelper.getCookieStore(request);

        try (CloseableHttpClient client = getHttpClient(cookieStore, request.getUrl())) {

            HttpDelete httpRequest = new HttpDelete(request.getUrl());

            // Set Headers
            HTTPClientHelper.populateHeader(httpRequest, request.getHeaders());

            // Execute Request
            response = execute(client, httpRequest, cookieStore);

        } catch (Exception ex) {
            log.error(DELETE, ex.getMessage(), ex);
        }
        return response;
    }

    public static HTTPResponse delete(final HTTPRequest request, HTTPParameter params) {

        HTTPResponse response = new HTTPResponse();
        CookieStore cookieStore = HTTPClientHelper.getCookieStore(request);

        try (CloseableHttpClient client = getHttpClient(cookieStore, request.getUrl())) {

            HTTPDeleteWithBody httpRequest = new HTTPDeleteWithBody(request.getUrl());

            // Set Headers
            HTTPClientHelper.populateHeader(httpRequest, request.getHeaders());

            // Set Parameters
            if (HTTPClientHelper.validateHttpParameter(params)) {

                List<NameValuePair> parameterList = HTTPClientHelper.getNVPList(params);

                httpRequest.setEntity(new UrlEncodedFormEntity(parameterList));
            }

            // Execute Request
            response = execute(client, httpRequest, cookieStore);

        } catch (Exception ex) {
            log.error(DELETE, ex.getMessage(), ex);
        }
        return response;
    }

    public static HTTPResponse delete(final HTTPRequest request, String body) {

        HTTPResponse response = new HTTPResponse();
        CookieStore cookieStore = HTTPClientHelper.getCookieStore(request);

        try (CloseableHttpClient client = getHttpClient(cookieStore, request.getUrl())) {

            HTTPDeleteWithBody httpRequest = new HTTPDeleteWithBody(request.getUrl());

            // Set Headers
            HTTPClientHelper.populateHeader(httpRequest, request.getHeaders());

            if (body != null && !body.isEmpty()) {
                // Set Request Body
                httpRequest.setEntity(new StringEntity(body));
            }

            // Execute Request
            response = execute(client, httpRequest, cookieStore);

        } catch (Exception ex) {
            log.error(DELETE, ex.getMessage(), ex);
        }
        return response;
    }

    private static CloseableHttpClient getHttpClient(CookieStore cookieStore, String url) {

        // use the TrustSelfSignedStrategy to allow Self Signed Certificates
        SSLContext sslContext = null;
        try {
            sslContext = SSLContextBuilder.create()
                    .loadTrustMaterial(new TrustSelfSignedStrategy())
                    .build();
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
            e.printStackTrace();
        }

        // create an SSL Socket Factory to use the SSLContext with the trust self signed certificate strategy
        SSLConnectionSocketFactory connFactory = new SSLConnectionSocketFactory(sslContext,NoopHostnameVerifier.INSTANCE);

        if (PropertyManager.getInstance().getBooleanProperty(Property.PROXY_ENABLE) && !proxyExclude(url)) {
            HttpHost proxyHost = new HttpHost(
                    PropertyManager.getInstance().getProperty(Property.PROXY_HOST),
                    PropertyManager.getInstance().getIntProperty(Property.PROXY_PORT),
                    PropertyManager.getInstance().getProperty(Property.PROXY_SCHEMA));

            DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxyHost);

            return HttpClients.custom().setDefaultCookieStore(cookieStore).disableRedirectHandling()
                    .setSSLSocketFactory(connFactory).setRoutePlanner(routePlanner).build();
        } else {
            return HttpClients.custom().setDefaultCookieStore(cookieStore).setSSLSocketFactory(connFactory)
                    .disableRedirectHandling().build();
        }
    }

    private static boolean proxyExclude(String url) {
        boolean exclude = false;
        if (url != null) {
            String excludeStr = PropertyManager.getInstance().getProperty(Property.PROXY_EXCLUDE);
            String[] strs = excludeStr.split(",");
            List<String> excludeList = new ArrayList<>();
            for (int i = 0; i < strs.length; i++) {
                excludeList.add(strs[i].trim());
            }
            exclude = excludeList.stream().anyMatch(url::contains);
        }
        return exclude;
    }

    private static HTTPResponse execute(CloseableHttpClient client, HttpUriRequest request, CookieStore cookieStore)
            throws IOException {

        HTTPResponse response = client.execute(request, responseHandler);
        response.setCookies(cookieStore.getCookies());

        return response;
    }
}
