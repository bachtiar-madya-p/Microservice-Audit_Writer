package id.ic.ms.audit.util.http.helper;

import id.ic.ms.audit.util.http.model.HTTPHeader;
import id.ic.ms.audit.util.http.model.HTTPParameter;
import id.ic.ms.audit.util.http.model.HTTPRequest;
import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import id.ic.ms.audit.util.http.BaseHttpClient;


import java.util.List;
import java.util.stream.Collectors;

public class HTTPClientHelper extends BaseHttpClient {

    private HTTPClientHelper() {}

    public static void populateHeader(HttpRequest request, HTTPHeader headers) {
        if (headers != null && !headers.isEmpty()) {
            List<Header> list = headers.entrySet().stream()
                    .map(entry -> new BasicHeader(entry.getKey(), entry.getValue())).collect(Collectors.toList());
            list.forEach(request::addHeader);
        }
    }

    public static List<NameValuePair> getNVPList(HTTPParameter params) {
        return params.entrySet().stream().map(entry -> new BasicNameValuePair(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public static CookieStore getCookieStore(final HTTPRequest request) {
        CookieStore cookieStore = new BasicCookieStore();

        if (request.hasCookies()) {
            request.getCookies().forEach(cookieStore::addCookie);
        }

        return cookieStore;
    }

    public static boolean validateHttpParameter(HTTPParameter params) {
        return params != null && !params.isEmpty();
    }
}
