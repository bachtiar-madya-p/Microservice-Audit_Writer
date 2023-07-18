package id.ic.ms.audit.repository;

import id.ic.ms.audit.util.property.Property;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.Query;
import org.jdbi.v3.core.statement.Update;
import org.springframework.stereotype.Repository;
import id.ic.ms.audit.repository.model.Otp;
import id.ic.ms.audit.util.date.DateUtil;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public class OtpRepository extends BaseRepository {

    private static String paramUid      = "uid";
    private static String paramMobileNo = "mobileNo";
    private static String paramOtp      = "otp";
    private static String paramExpiryDt = "expiryDt";
    private static String paramNow      = "now";
    private static String paramCount    = "count";
    private static String paramLocked   = "locked";
    private static String paramUnlockDt = "unlockDt";

    public OtpRepository() {
        log = getLogger(this.getClass());
    }

    public LocalDateTime getOtpLastGenerated(String mobileNo) {
        final String methodName = "getOtpLastGenerated";
        start(methodName);
        LocalDateTime result = null;

        // one or more rows might be returned. take the most recent expiryDt
        String sql = "SELECT expiryDt FROM otp WHERE mobileNo = :mobileNo ORDER BY expiryDt DESC;";

        try (Handle h = getHandle(); Query q = h.createQuery(sql).bind(paramMobileNo, mobileNo)) {
            Optional<LocalDateTime> entry = q.mapTo(LocalDateTime.class).findFirst();
            if (entry.isPresent()) {
                result = entry.get();
            }

        } catch (Exception ex) {
            logError(methodName, ex);
        }
        completed(methodName);
        return result;
    }

    public boolean createOtp(String mobileNo, String otp, LocalDateTime expiryDt) {
        final String methodName = "createOtp";
        start(methodName);
        boolean result = false;
        final String sql = "INSERT INTO otp (uid, mobileNo,otp,expiryDt) " +
                " VALUES (:uid, :mobileNo, :otp, :expiryDt);";

        try (Handle h = getHandle(); Update update = h.createUpdate(sql)) {
            update.bind(paramUid, UUID.randomUUID().toString());
            update.bind(paramMobileNo, mobileNo);
            update.bind(paramOtp, otp);
            update.bind(paramExpiryDt, expiryDt);
            result = executeUpdate(update);
        } catch (Exception ex) {
            logError(methodName, ex);
        }
        completed(methodName);
        return result;
    }

    public boolean updateOtp(String mobileNo, int count, boolean locked, String unlockDt) {
        final String methodName = "updateOtpCountLock";
        start(methodName);
        boolean result = false;
        String sql = "UPDATE otp SET count = :count, locked = :locked";
        if (locked) {
            sql += ", unlockDt = :unlockDt ";
        }
        sql += " WHERE mobileNo = :mobileNo;";

        try (Handle h = getHandle()) {
            Update update = h.createUpdate(sql);
            update.bind(paramMobileNo, mobileNo);
            update.bind(paramCount, count);
            update.bind(paramLocked, locked);
            if (locked) {
                update.bind(paramUnlockDt, unlockDt);
            }
            result = executeUpdate(update);
        } catch (Exception ex) {
            logError(methodName, ex);
        }
        completed(methodName);
        return result;
    }

    public Otp getOtp(String mobileNo) {
        final String methodName = "getOtp";
        start(methodName);

        Otp result = null;

        // in non-prod env, otp is static. therefore, there will be many duplicate
        // records if search by (mobileNo + otp). limit query to return the most
        // recent record. prod env will always return 1 record
        String sql = "SELECT uid, mobileNo, otp, expiryDt, count, locked, unlockDt " +
                " FROM otp" +
                " WHERE mobileNo = :mobileNo " +
                " ORDER BY expiryDt DESC " +
                " LIMIT 1 ";

        try (Handle h = getHandle(); Query q = h.createQuery(sql)) {
            q.bind(paramMobileNo, mobileNo);

            result = q.mapToBean(Otp.class).one();

        } catch (Exception ex) {
            logError(methodName, ex);
        }
        completed(methodName);
        return result;
    }

    public Otp getOtp(String mobileNo, String otp) {
        final String methodName = "getOtp";
        start(methodName);

        Otp result = null;

        // in non-prod env, otp is static. therefore, there will be many duplicate
        // records if search by (mobileNo + otp). limit query to return the most
        // recent record. prod env will always return 1 record
        String sql = "SELECT uid, mobileNo, otp, expiryDt " +
                "  FROM otp " +
                " WHERE mobileNo = :mobileNo AND otp = :otp " +
                " ORDER BY expiryDt DESC " +
                " LIMIT 1 ";

        try (Handle h = getHandle(); Query q = h.createQuery(sql)) {
            q.bind(paramMobileNo, mobileNo);
            q.bind(paramOtp, otp);

            result = q.mapToBean(Otp.class).one();

        } catch (Exception ex) {
            logError(methodName, ex);
        }
        completed(methodName);
        return result;
    }

    public boolean deleteOtp(String mobileNo) {
        final String methodName = "deleteOtp";
        start(methodName);
        boolean result = false;

        // remove all db records that belong to this mobile no
        final String sql = "DELETE FROM otp WHERE mobileNo = :mobileNo AND locked = false;";

        try (Handle h = getHandle(); Update update = h.createUpdate(sql)) {
            update.bind(paramMobileNo, mobileNo);
            result = executeUpdate(update);
        } catch (Exception ex) {
            logError(methodName, ex);
        }
        completed(methodName);
        return result;
    }

    public boolean deleteLockedOtp(String mobileNo) {
        final String methodName = "deleteLockedOtp";
        start(methodName);
        boolean result = false;

        // remove db records that belong to this mobile no and locked
        final String sql = "DELETE FROM otp WHERE mobileNo = :mobileNo AND locked = true;";

        try (Handle h = getHandle(); Update update = h.createUpdate(sql)) {
            update.bind(paramMobileNo, mobileNo);
            result = executeUpdate(update);
        } catch (Exception ex) {
            logError(methodName, ex);
        }
        completed(methodName);
        return result;
    }

    // this method is triggered by ScheduleManager
    public boolean purgeExpiredOtp() {
        final String methodName = "purgeExpiredOtp";
        boolean result = false;

        // Task 1: Find unlocked record, delete record(s) where expiryDt has passed
        // Task 2: Find locked record, delete record(s) where unlockDt has passed
        final String sql = "DELETE FROM otp WHERE (locked = 0 AND expiryDt <= :now) OR (locked = 1 AND unlockDt <= :now);";

        try (Handle h = getHandle(); Update update = h.createUpdate(sql);) {
            update.bind(paramNow, DateUtil.formatDateTime(LocalDateTime.now()));
            result = executeUpdate(update);
        } catch (Exception ex) {
            logError(methodName, ex);
        }
        return result;
    }


    // Enable/Disable/Verify-Link below ...

    public boolean storeToken(String token, String uid, String sub, boolean enable) {
        return storeToken(token, uid, sub, enable, LocalDateTime.now().plusSeconds(getIntProperty(Property.OTP_EMAIL_VALIDITY)));
    }

    private boolean storeToken(String token, String uid, String sub, boolean enable, LocalDateTime validity) {
        final String methodName = "storeToken";
        start(methodName);
        boolean result = false;
        String sql = "INSERT INTO email_otp (id, uid, sub, enable, validity) VALUES( :id, :uid, :sub, :enable, :validity);";

        try (Handle h = getHandle(); Update u = h.createUpdate(sql)) {
            u.bind("id", token);
            u.bind("uid", uid);
            u.bind("sub", sub);
            u.bind("enable", enable);
            u.bind("validity", validity);
            result = executeUpdate(u);

        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }



    public boolean delete(String id) {
        final String methodName = "delete";
        start(methodName);
        boolean result = false;
        final String sql = "DELETE FROM email_otp WHERE id = :id;";

        try (Handle h = getHandle(); Update update = h.createUpdate(sql)) {
            update.bind("id", id);
            result = executeUpdate(update);
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }


    // this method is triggered by ScheduleManager
    public boolean deleteExpiredEmailOtp() {
        final String methodName = "deleteExpiredEmailOtp";
        boolean result = false;
        final String sql = "DELETE FROM email_otp WHERE validity <= :now;";

        try (Handle h = getHandle(); Update update = h.createUpdate(sql)) {
            update.bind(paramNow, DateUtil.formatDateTime(LocalDateTime.now()));
            result = executeUpdate(update);
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        return result;
    }

}
