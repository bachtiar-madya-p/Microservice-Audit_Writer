package id.ic.ms.audit.rest.validator;

public class BaseValidator {

    public BaseValidator() {
        // Empty Constructor
    }

    public boolean notNull(Object obj) {
        return null != obj;
    }

    public boolean validateStr(String str) {
        return null != str && !str.trim().isEmpty();
    }

    public boolean validate(String str) {
        return validateStr(str);
    }

    public boolean validate(String... strs) {
        for (String str : strs)
            if (!validateStr(str)) {
                return false;
            }
        return true;
    }
}
