package com.yimamerchant.system.hotel.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yimamerchant.common.annotation.Excel;
import com.yimamerchant.common.annotation.Excel.ColumnType;
import com.yimamerchant.common.core.domain.BaseEntity;

public class HotelOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;
    @Excel(name = "订单号")
    private String orderNo;
    private Long hotelId;
    @Excel(name = "酒店")
    private String hotelName;
    private Long roomTypeId;
    @Excel(name = "房型")
    private String roomTypeName;
    private Long userId;
    @Excel(name = "用户")
    private String userName;
    private String userMobile;
    private Long bdUserId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入住日期", dateFormat = "yyyy-MM-dd")
    private Date checkinDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "离店日期", dateFormat = "yyyy-MM-dd")
    private Date checkoutDate;
    private Integer nightCount;
    private Integer roomCount;
    @Excel(name = "订单金额")
    private BigDecimal orderAmount;
    private BigDecimal taxAmount;
    private BigDecimal discountAmount;
    private BigDecimal payAmount;
    private BigDecimal refundAmount;
    @Excel(name = "状态")
    private String orderStatus;
    private String refundStatus;
    private String refundReason;
    private String specialRequest;
    private String innerRemark;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date confirmTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date checkinTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date checkoutTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date cancelTime;
    private String cancelReason;
    @Excel(name = "下单时间", width = 20, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private String beginCreateTime;
    private String endCreateTime;
    private String beginCheckinDate;
    private String endCheckinDate;
    private List<HotelOrderGuest> guestList;
    private List<HotelOrderLog> logList;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getOrderNo() { return orderNo; }
    public void setOrderNo(String orderNo) { this.orderNo = orderNo; }
    public Long getHotelId() { return hotelId; }
    public void setHotelId(Long hotelId) { this.hotelId = hotelId; }
    public String getHotelName() { return hotelName; }
    public void setHotelName(String hotelName) { this.hotelName = hotelName; }
    public Long getRoomTypeId() { return roomTypeId; }
    public void setRoomTypeId(Long roomTypeId) { this.roomTypeId = roomTypeId; }
    public String getRoomTypeName() { return roomTypeName; }
    public void setRoomTypeName(String roomTypeName) { this.roomTypeName = roomTypeName; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getUserMobile() { return userMobile; }
    public void setUserMobile(String userMobile) { this.userMobile = userMobile; }
    public Long getBdUserId() { return bdUserId; }
    public void setBdUserId(Long bdUserId) { this.bdUserId = bdUserId; }
    public Date getCheckinDate() { return checkinDate; }
    public void setCheckinDate(Date checkinDate) { this.checkinDate = checkinDate; }
    public Date getCheckoutDate() { return checkoutDate; }
    public void setCheckoutDate(Date checkoutDate) { this.checkoutDate = checkoutDate; }
    public Integer getNightCount() { return nightCount; }
    public void setNightCount(Integer nightCount) { this.nightCount = nightCount; }
    public Integer getRoomCount() { return roomCount; }
    public void setRoomCount(Integer roomCount) { this.roomCount = roomCount; }
    public BigDecimal getOrderAmount() { return orderAmount; }
    public void setOrderAmount(BigDecimal orderAmount) { this.orderAmount = orderAmount; }
    public BigDecimal getTaxAmount() { return taxAmount; }
    public void setTaxAmount(BigDecimal taxAmount) { this.taxAmount = taxAmount; }
    public BigDecimal getDiscountAmount() { return discountAmount; }
    public void setDiscountAmount(BigDecimal discountAmount) { this.discountAmount = discountAmount; }
    public BigDecimal getPayAmount() { return payAmount; }
    public void setPayAmount(BigDecimal payAmount) { this.payAmount = payAmount; }
    public BigDecimal getRefundAmount() { return refundAmount; }
    public void setRefundAmount(BigDecimal refundAmount) { this.refundAmount = refundAmount; }
    public String getOrderStatus() { return orderStatus; }
    public void setOrderStatus(String orderStatus) { this.orderStatus = orderStatus; }
    public String getRefundStatus() { return refundStatus; }
    public void setRefundStatus(String refundStatus) { this.refundStatus = refundStatus; }
    public String getRefundReason() { return refundReason; }
    public void setRefundReason(String refundReason) { this.refundReason = refundReason; }
    public String getSpecialRequest() { return specialRequest; }
    public void setSpecialRequest(String specialRequest) { this.specialRequest = specialRequest; }
    public String getInnerRemark() { return innerRemark; }
    public void setInnerRemark(String innerRemark) { this.innerRemark = innerRemark; }
    public Date getConfirmTime() { return confirmTime; }
    public void setConfirmTime(Date confirmTime) { this.confirmTime = confirmTime; }
    public Date getCheckinTime() { return checkinTime; }
    public void setCheckinTime(Date checkinTime) { this.checkinTime = checkinTime; }
    public Date getCheckoutTime() { return checkoutTime; }
    public void setCheckoutTime(Date checkoutTime) { this.checkoutTime = checkoutTime; }
    public Date getCancelTime() { return cancelTime; }
    public void setCancelTime(Date cancelTime) { this.cancelTime = cancelTime; }
    public String getCancelReason() { return cancelReason; }
    public void setCancelReason(String cancelReason) { this.cancelReason = cancelReason; }
    public String getBeginCreateTime() { return beginCreateTime; }
    public void setBeginCreateTime(String beginCreateTime) { this.beginCreateTime = beginCreateTime; }
    public String getEndCreateTime() { return endCreateTime; }
    public void setEndCreateTime(String endCreateTime) { this.endCreateTime = endCreateTime; }
    public String getBeginCheckinDate() { return beginCheckinDate; }
    public void setBeginCheckinDate(String beginCheckinDate) { this.beginCheckinDate = beginCheckinDate; }
    public String getEndCheckinDate() { return endCheckinDate; }
    public void setEndCheckinDate(String endCheckinDate) { this.endCheckinDate = endCheckinDate; }
    public List<HotelOrderGuest> getGuestList() { return guestList; }
    public void setGuestList(List<HotelOrderGuest> guestList) { this.guestList = guestList; }
    public List<HotelOrderLog> getLogList() { return logList; }
    public void setLogList(List<HotelOrderLog> logList) { this.logList = logList; }
}
