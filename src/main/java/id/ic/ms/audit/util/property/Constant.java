package id.ic.ms.audit.util.property;

public class Constant {
    private Constant() {
        //Empty constructor
    }

    // OTP message template
    public static final String TEMPLATE_SMS_OTP          = "sms.otp.template";
    public static final String TEMPLATE_VOICE_OTP        = "voice.otp.template";
    public static final String TEMPLATE_EMAIL_ENABLE_OTP = "mail.enable.otp.template";
    public static final String TEMPLATE_EMAIL_OTP        = "mail.otp.template";

    // OTP key
    public static final String KEY_OTP_SMS          = "SMS";
    public static final String KEY_OTP_VOICE        = "VOICE";
    public static final String KEY_OTP_EMAIL        = "EMAIL";
    public static final String KEY_OTP_ENABLE_EMAIL = "ENABLE_EMAIL";

    // OTP error message
    public static final String ERROR_MOBILE_REQUIRED          = "Mobile Number Required";
    public static final String ERROR_MOBILE_OTP_REQUIRED      = "Mobile Number and OTP Required";
    public static final String ERROR_EMAIL_FIRSTNAME_REQUIRED = "Email and First Name Required";
    public static final String ERROR_CODE_REQUIRED            = "Code Required";
    public static final String ERROR_INVALID_CODE             = "Invalid Code";
    public static final String ERROR_INVALID_OTP_TYPE         = "Invalid OTP Type";
    public static final String ERROR_INVALID_OTP_MSG          = "Invalid OTP";
    public static final String ERROR_EXPIRED_OTP_MSG          = "Expired OTP";
    public static final String ERROR_OTP_IS_VALID_MSG         = "OTP has not expired";
    public static final String ERROR_EMAIL_VERIFY_LINK_MSG    = "Failed to send verification email";
    public static final String ERROR_INVALID_NUMBER           = "Invalid number";
    public static final String ERROR_OTP_LIMIT_REACHED_MSG    = "OTP limit reached";
    public static final String ERROR_OTP_LOCKED_MSG           = "You have exceeded your OTP requests";
}
