package id.ic.ms.audit.util.http.model;

public enum HTTPContentType {


    APPLICATION_JSON("application/json"), APPLICATION_XML("application/xml"), APPLICATION_FORM_URLENCODED(
            "application/x-www-form-urlencoded");

    private String type;

    private HTTPContentType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
