package com.hodehtml.demo.model;

public class UserOrderBill {
    private Integer id;

    private String orderId;

    private String mobile;

    private String code;

    private String message;

    private String billMonth;

    private String billstartdate;

    private String billenddate;

    private Integer baseFee;

    private Integer extraservicefee;

    private Integer voiceFee;

    private Integer smsFee;

    private Integer webFee;

    private Integer extraFee;

    private Integer totalFee;

    private Integer discount;

    private Integer extraDiscount;

    private Integer actualFee;

    private Integer paidFee;

    private Integer unpaidFee;

    private Integer point;

    private Integer lastPoint;

    private String relatedMobiles;

    private String notes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    public String getBillMonth() {
        return billMonth;
    }

    public void setBillMonth(String billMonth) {
        this.billMonth = billMonth == null ? null : billMonth.trim();
    }

    public String getBillstartdate() {
        return billstartdate;
    }

    public void setBillstartdate(String billstartdate) {
        this.billstartdate = billstartdate == null ? null : billstartdate.trim();
    }

    public String getBillenddate() {
        return billenddate;
    }

    public void setBillenddate(String billenddate) {
        this.billenddate = billenddate == null ? null : billenddate.trim();
    }

    public Integer getBaseFee() {
        return baseFee;
    }

    public void setBaseFee(Integer baseFee) {
        this.baseFee = baseFee;
    }

    public Integer getExtraservicefee() {
        return extraservicefee;
    }

    public void setExtraservicefee(Integer extraservicefee) {
        this.extraservicefee = extraservicefee;
    }

    public Integer getVoiceFee() {
        return voiceFee;
    }

    public void setVoiceFee(Integer voiceFee) {
        this.voiceFee = voiceFee;
    }

    public Integer getSmsFee() {
        return smsFee;
    }

    public void setSmsFee(Integer smsFee) {
        this.smsFee = smsFee;
    }

    public Integer getWebFee() {
        return webFee;
    }

    public void setWebFee(Integer webFee) {
        this.webFee = webFee;
    }

    public Integer getExtraFee() {
        return extraFee;
    }

    public void setExtraFee(Integer extraFee) {
        this.extraFee = extraFee;
    }

    public Integer getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getExtraDiscount() {
        return extraDiscount;
    }

    public void setExtraDiscount(Integer extraDiscount) {
        this.extraDiscount = extraDiscount;
    }

    public Integer getActualFee() {
        return actualFee;
    }

    public void setActualFee(Integer actualFee) {
        this.actualFee = actualFee;
    }

    public Integer getPaidFee() {
        return paidFee;
    }

    public void setPaidFee(Integer paidFee) {
        this.paidFee = paidFee;
    }

    public Integer getUnpaidFee() {
        return unpaidFee;
    }

    public void setUnpaidFee(Integer unpaidFee) {
        this.unpaidFee = unpaidFee;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Integer getLastPoint() {
        return lastPoint;
    }

    public void setLastPoint(Integer lastPoint) {
        this.lastPoint = lastPoint;
    }

    public String getRelatedMobiles() {
        return relatedMobiles;
    }

    public void setRelatedMobiles(String relatedMobiles) {
        this.relatedMobiles = relatedMobiles == null ? null : relatedMobiles.trim();
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes == null ? null : notes.trim();
    }
}