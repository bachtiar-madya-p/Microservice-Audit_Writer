package id.ic.ms.audit.util.http.model;

import org.apache.http.Header;
import org.apache.http.cookie.Cookie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HTTPResponse {
    private String body;
    private int code = 500;
    private List<Header> headers;
    private Map<String, String> headerMap;
    private List<Cookie> cookies;

    public HTTPResponse() {
        headers = new ArrayList<>();
        cookies = new ArrayList<>();
        headerMap = new HashMap<>();
    }

    public HTTPResponse(String body, int code) {
        this();
        setBody(body);
        setCode(code);
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<Header> getHeaders() {
        return headers;
    }

    public String getHeader(String key) {
        return headerMap.getOrDefault(key.toLowerCase(), "");
    }

    public void setHeaders(List<Header> headers) {
        this.headers = headers;
        headers.forEach(header -> headerMap.put(header.getName().toLowerCase(), header.getValue()));
    }

    public List<Cookie> getCookies() {
        return cookies;
    }

    public void setCookies(List<Cookie> cookies) {
        this.cookies = cookies;
    }

    @Override
    public String toString() {
        return code + " - " + body;

    }
}
