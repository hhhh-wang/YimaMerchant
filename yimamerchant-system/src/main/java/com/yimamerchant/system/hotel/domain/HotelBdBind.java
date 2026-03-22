package com.yimamerchant.system.hotel.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yimamerchant.common.core.domain.BaseEntity;

public class HotelBdBind extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long hotelId;
    private String hotelName;
    private Long bdUserId;
    private String bdUserName;
    private String bdUserCode;
    private String bindStatus;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date bindTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date unbindTime;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getHotelId() { return hotelId; }
    public void setHotelId(Long hotelId) { this.hotelId = hotelId; }
    public String getHotelName() { return hotelName; }
    public void setHotelName(String hotelName) { this.hotelName = hotelName; }
    public Long getBdUserId() { return bdUserId; }
    public void setBdUserId(Long bdUserId) { this.bdUserId = bdUserId; }
    public String getBdUserName() { return bdUserName; }
    public void setBdUserName(String bdUserName) { this.bdUserName = bdUserName; }
    public String getBdUserCode() { return bdUserCode; }
    public void setBdUserCode(String bdUserCode) { this.bdUserCode = bdUserCode; }
    public String getBindStatus() { return bindStatus; }
    public void setBindStatus(String bindStatus) { this.bindStatus = bindStatus; }
    public Date getBindTime() { return bindTime; }
    public void setBindTime(Date bindTime) { this.bindTime = bindTime; }
    public Date getUnbindTime() { return unbindTime; }
    public void setUnbindTime(Date unbindTime) { this.unbindTime = unbindTime; }
}
