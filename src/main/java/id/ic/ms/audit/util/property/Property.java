package id.ic.ms.audit.util.property;

public class Property {
    private Property() {
        //Empty constructor
    }

    // OAUTH
    public static final String OAUTH_BASE_URL = "oauth.base.url";
    public static final String OAUTH_CLIENT_ID = "oauth.client.id";
    public static final String OAUTH_CLIENT_SECRET = "oauth.client.secret";
    public static final String OAUTH_TOKEN_URL = "oauth.token.url";
    public static final String OAUTH_CLIENT_SCOPES = "oauth.client.scopes";

    // API
    public static final String API_BASE_URL = "api.base.url";
    public static final String API_SEND_SMS_URL = "api.send.sms.url";
    public static final String API_SEND_VOICE_URL = "api.send.voice.url";
    public static final String SEND_EMAIL_URL = "api.send.email.url";

    // OTP
    public static final String OTP_LENGTH = "otp.length";
    public static final String OTP_STATIC = "otp.static";
    public static final String OTP_STATIC_VALUE = "otp.static.value";
    public static final String OTP_RETRY_COUNT  = "otp.retry.count";
    public static final String OTP_RESEND_INTERVAL = "otp.resend.interval";
    public static final String OTP_VALIDITY = "otp.validity";
    public static final String OTP_EMAIL_VALIDITY = "otp.email.validity";
    public static final String OTP_PURGE_INTERVAL  = "otp.purge.interval.min";
    public static final String OTP_LOCK_SOURCE  = "otp.lock.source";
    public static final String OTP_LOCK_DURATION_MIN  = "otp.lock.duration.min";

    // OTP email
    public static final String OTP_EMAIL_SENDER = "mail.sender";
    public static final String OTP_EMAIL_SUBJECT = "mail.otp.subject";
    public static final String OTP_MAIL_ENABLE_SUBJECT = "mail.enable.subject";

    // TWILIO
    public static final String TWILIO_BASE_URL = "twilio.base.url";
    public static final String TWILIO_NUMBER_LOOKUP_URL = "twilio.number-lookup.url";
    public static final String TWILIO_ACCOUNT_ID = "twilio.account_id";
    public static final String TWILIO_AUTH_TOKEN = "twilio.auth-token";

    //Proxy config
    public static final String PROXY_ENABLE = "proxy.enable";
    public static final String PROXY_EXCLUDE = "proxy.exclude";
    public static final String PROXY_HOST = "proxy.host";
    public static final String PROXY_PORT = "proxy.port";
    public static final String PROXY_SCHEMA = "proxy.scheme";

}
