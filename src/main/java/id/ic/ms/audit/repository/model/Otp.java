package id.ic.ms.audit.repository.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class Otp {

    @JsonProperty("mobileNo")
    private String mobileNo;
    @JsonProperty("otp")
    private String otp;
    @JsonProperty("expiryDt")
    private LocalDateTime expiryDt;
    @JsonProperty("count")
    private int count;
    @JsonProperty("locked")
    private boolean locked;
    @JsonProperty("unlockDt")
    private LocalDateTime unlockDt;

    public Otp() {
        //Empty constructor
    }

    public Otp(String mobileNo, String otp, LocalDateTime expiryDt) {
        this.mobileNo = mobileNo;
        this.otp = otp;
        this.expiryDt = expiryDt;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public LocalDateTime getExpiryDt() {
        return expiryDt;
    }

    public void setExpiryDt(LocalDateTime expiryDt) {
        this.expiryDt = expiryDt;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public LocalDateTime getUnlockDt() {
        return unlockDt;
    }

    public void setUnlockDt(LocalDateTime unlockDt) {
        this.unlockDt = unlockDt;
    }
}
