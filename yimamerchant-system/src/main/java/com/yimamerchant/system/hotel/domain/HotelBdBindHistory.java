package com.yimamerchant.system.hotel.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yimamerchant.common.core.domain.BaseEntity;

public class HotelBdBindHistory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long hotelId;
    private String hotelName;
    private Long oldBdUserId;
    private String oldBdUserName;
    private Long newBdUserId;
    private String newBdUserName;
    private String operateType;
    private String operateReason;
    private Long operateUserId;
    private String operateUserName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date operateTime;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getHotelId() { return hotelId; }
    public void setHotelId(Long hotelId) { this.hotelId = hotelId; }
    public String getHotelName() { return hotelName; }
    public void setHotelName(String hotelName) { this.hotelName = hotelName; }
    public Long getOldBdUserId() { return oldBdUserId; }
    public void setOldBdUserId(Long oldBdUserId) { this.oldBdUserId = oldBdUserId; }
    public String getOldBdUserName() { return oldBdUserName; }
    public void setOldBdUserName(String oldBdUserName) { this.oldBdUserName = oldBdUserName; }
    public Long getNewBdUserId() { return newBdUserId; }
    public void setNewBdUserId(Long newBdUserId) { this.newBdUserId = newBdUserId; }
    public String getNewBdUserName() { return newBdUserName; }
    public void setNewBdUserName(String newBdUserName) { this.newBdUserName = newBdUserName; }
    public String getOperateType() { return operateType; }
    public void setOperateType(String operateType) { this.operateType = operateType; }
    public String getOperateReason() { return operateReason; }
    public void setOperateReason(String operateReason) { this.operateReason = operateReason; }
    public Long getOperateUserId() { return operateUserId; }
    public void setOperateUserId(Long operateUserId) { this.operateUserId = operateUserId; }
    public String getOperateUserName() { return operateUserName; }
    public void setOperateUserName(String operateUserName) { this.operateUserName = operateUserName; }
    public Date getOperateTime() { return operateTime; }
    public void setOperateTime(Date operateTime) { this.operateTime = operateTime; }
}
