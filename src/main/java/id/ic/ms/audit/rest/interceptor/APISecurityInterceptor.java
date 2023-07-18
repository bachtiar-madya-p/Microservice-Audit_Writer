package id.ic.ms.audit.rest.interceptor;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import id.ic.ms.audit.rest.model.response.ServiceResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import id.ic.ms.audit.util.json.JsonUtil;

@Component
public class APISecurityInterceptor implements HandlerInterceptor {

    @Value("${security.api-key.header}")
    private String header;

    @Value("${security.api-key.value}")
    private String key;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String headerValue = request.getHeader(header);
        if (isValid(headerValue) && headerValue.equals(key)) {
            return true;
        } else {
            setUnauthorizedServletResponse(response);
            return false;
        }
    }

    private boolean isValid(String str) {
        return str != null && !str.isEmpty();
    }

    private void setUnauthorizedServletResponse(HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(JsonUtil.toJson(new ServiceResponse(HttpStatus.UNAUTHORIZED)));
    }
}
