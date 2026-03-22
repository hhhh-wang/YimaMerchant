package com.yimamerchant.system.hotel.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yimamerchant.common.annotation.Excel;
import com.yimamerchant.common.annotation.Excel.ColumnType;
import com.yimamerchant.common.core.domain.BaseEntity;

public class HotelBill extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;
    @Excel(name = "账单号")
    private String billNo;
    private Long hotelId;
    @Excel(name = "酒店名称")
    private String hotelName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date statementStartDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date statementEndDate;
    @Excel(name = "订单数量", cellType = ColumnType.NUMERIC)
    private Integer orderCount;
    private Integer roomNightCount;
    private BigDecimal totalRoomAmount;
    private BigDecimal totalCommissionAmount;
    @Excel(name = "结算金额")
    private BigDecimal totalSettlementAmount;
    @Excel(name = "状态")
    private String billStatus;
    private String disputeStatus;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date confirmTime;
    private Long confirmUserId;
    private String confirmUserName;
    private String paymentStatus;
    private String generateMode;
    @Excel(name = "生成时间", width = 20, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private String beginCreateTime;
    private String endCreateTime;
    private List<HotelBillOrder> orderList;
    private List<HotelBillCheckLog> checkLogList;
    private List<HotelBillPayment> paymentList;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getBillNo() { return billNo; }
    public void setBillNo(String billNo) { this.billNo = billNo; }
    public Long getHotelId() { return hotelId; }
    public void setHotelId(Long hotelId) { this.hotelId = hotelId; }
    public String getHotelName() { return hotelName; }
    public void setHotelName(String hotelName) { this.hotelName = hotelName; }
    public Date getStatementStartDate() { return statementStartDate; }
    public void setStatementStartDate(Date statementStartDate) { this.statementStartDate = statementStartDate; }
    public Date getStatementEndDate() { return statementEndDate; }
    public void setStatementEndDate(Date statementEndDate) { this.statementEndDate = statementEndDate; }
    public Integer getOrderCount() { return orderCount; }
    public void setOrderCount(Integer orderCount) { this.orderCount = orderCount; }
    public Integer getRoomNightCount() { return roomNightCount; }
    public void setRoomNightCount(Integer roomNightCount) { this.roomNightCount = roomNightCount; }
    public BigDecimal getTotalRoomAmount() { return totalRoomAmount; }
    public void setTotalRoomAmount(BigDecimal totalRoomAmount) { this.totalRoomAmount = totalRoomAmount; }
    public BigDecimal getTotalCommissionAmount() { return totalCommissionAmount; }
    public void setTotalCommissionAmount(BigDecimal totalCommissionAmount) { this.totalCommissionAmount = totalCommissionAmount; }
    public BigDecimal getTotalSettlementAmount() { return totalSettlementAmount; }
    public void setTotalSettlementAmount(BigDecimal totalSettlementAmount) { this.totalSettlementAmount = totalSettlementAmount; }
    public String getBillStatus() { return billStatus; }
    public void setBillStatus(String billStatus) { this.billStatus = billStatus; }
    public String getDisputeStatus() { return disputeStatus; }
    public void setDisputeStatus(String disputeStatus) { this.disputeStatus = disputeStatus; }
    public Date getConfirmTime() { return confirmTime; }
    public void setConfirmTime(Date confirmTime) { this.confirmTime = confirmTime; }
    public Long getConfirmUserId() { return confirmUserId; }
    public void setConfirmUserId(Long confirmUserId) { this.confirmUserId = confirmUserId; }
    public String getConfirmUserName() { return confirmUserName; }
    public void setConfirmUserName(String confirmUserName) { this.confirmUserName = confirmUserName; }
    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
    public String getGenerateMode() { return generateMode; }
    public void setGenerateMode(String generateMode) { this.generateMode = generateMode; }
    public String getBeginCreateTime() { return beginCreateTime; }
    public void setBeginCreateTime(String beginCreateTime) { this.beginCreateTime = beginCreateTime; }
    public String getEndCreateTime() { return endCreateTime; }
    public void setEndCreateTime(String endCreateTime) { this.endCreateTime = endCreateTime; }
    public List<HotelBillOrder> getOrderList() { return orderList; }
    public void setOrderList(List<HotelBillOrder> orderList) { this.orderList = orderList; }
    public List<HotelBillCheckLog> getCheckLogList() { return checkLogList; }
    public void setCheckLogList(List<HotelBillCheckLog> checkLogList) { this.checkLogList = checkLogList; }
    public List<HotelBillPayment> getPaymentList() { return paymentList; }
    public void setPaymentList(List<HotelBillPayment> paymentList) { this.paymentList = paymentList; }
}
