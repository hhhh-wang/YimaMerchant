package com.yimamerchant.system.hotel.domain;

import com.yimamerchant.common.core.domain.BaseEntity;

public class HotelOrderGuest extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long orderId;
    private String orderNo;
    private String guestName;
    private String guestMobile;
    private String certificateType;
    private String certificateNo;
    private String isMainGuest;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public String getOrderNo() { return orderNo; }
    public void setOrderNo(String orderNo) { this.orderNo = orderNo; }
    public String getGuestName() { return guestName; }
    public void setGuestName(String guestName) { this.guestName = guestName; }
    public String getGuestMobile() { return guestMobile; }
    public void setGuestMobile(String guestMobile) { this.guestMobile = guestMobile; }
    public String getCertificateType() { return certificateType; }
    public void setCertificateType(String certificateType) { this.certificateType = certificateType; }
    public String getCertificateNo() { return certificateNo; }
    public void setCertificateNo(String certificateNo) { this.certificateNo = certificateNo; }
    public String getIsMainGuest() { return isMainGuest; }
    public void setIsMainGuest(String isMainGuest) { this.isMainGuest = isMainGuest; }
}
