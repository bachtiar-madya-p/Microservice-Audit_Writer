package id.ic.ms.audit.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@PropertySource("classpath:application.properties")
@Configuration
public class PropertyManager extends BaseManager implements EnvironmentAware {

    @Autowired
    private Environment env;

    private static PropertyManager instance;

    public PropertyManager() {
        log = getLogger(PropertyManager.class);
    }

    @Override
    public void setEnvironment(Environment environment) {
        getInstance().env = environment;
    }

    @Bean
    public PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    public String getProperty(String key) {
        return getProperty(key, "");
    }

    public int getIntProperty(String key) {
        return getInstance().env.getProperty(key, Integer.class);
    }

    public int getIntProperty(String key, int defaultValue) {
        return getInstance().env.getProperty(key, Integer.class, defaultValue);
    }

    public long getLongProperty(String key) {
        return Long.parseLong(getProperty(key));
    }

    public long getLongProperty(String key, long defaultValue) {
        return Long.parseLong(getProperty(key, String.valueOf(defaultValue)));
    }

    public double getDoubleProperty(String key) {
        return Double.parseDouble(getProperty(key));
    }

    public double getDoubleProperty(String key, double defaultValue) {
        return Double.parseDouble(getProperty(key, String.valueOf(defaultValue)));
    }

    public boolean getBooleanProperty(String key) {
        return Boolean.parseBoolean(getProperty(key));
    }

    public Date getDateProperty(String key, String format) {
        DateFormat df = new SimpleDateFormat(format);
        Date retDate = new Date();
        try {
            retDate = df.parse(getProperty(key));
        } catch (ParseException e) {
            retDate = new Date(System.currentTimeMillis());
        }
        return retDate;

    }

    private String getProperty(String key, String defaultValue) {
        String methodName = "key";
        try {
            return getInstance().env.getRequiredProperty(key);

        } catch (Exception e) {
            log.error(methodName, e.getStackTrace());
        }
        return defaultValue;
    }

    public static PropertyManager getInstance() {
        if (instance == null) {
            instance = new PropertyManager();
        }
        return instance;
    }


}
