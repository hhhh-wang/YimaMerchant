package com.yimamerchant.system.hotel.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yimamerchant.common.core.domain.BaseEntity;

public class HotelContract extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long hotelId;
    private String contractNo;
    private String contractName;
    private String contractFileIds;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date contractStartDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date contractEndDate;
    private String commissionMode;
    private BigDecimal commissionRate;
    private BigDecimal markupRate;
    private String contractStatus;
    private Long signUserId;
    private String signUserName;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getHotelId() { return hotelId; }
    public void setHotelId(Long hotelId) { this.hotelId = hotelId; }
    public String getContractNo() { return contractNo; }
    public void setContractNo(String contractNo) { this.contractNo = contractNo; }
    public String getContractName() { return contractName; }
    public void setContractName(String contractName) { this.contractName = contractName; }
    public String getContractFileIds() { return contractFileIds; }
    public void setContractFileIds(String contractFileIds) { this.contractFileIds = contractFileIds; }
    public Date getContractStartDate() { return contractStartDate; }
    public void setContractStartDate(Date contractStartDate) { this.contractStartDate = contractStartDate; }
    public Date getContractEndDate() { return contractEndDate; }
    public void setContractEndDate(Date contractEndDate) { this.contractEndDate = contractEndDate; }
    public String getCommissionMode() { return commissionMode; }
    public void setCommissionMode(String commissionMode) { this.commissionMode = commissionMode; }
    public BigDecimal getCommissionRate() { return commissionRate; }
    public void setCommissionRate(BigDecimal commissionRate) { this.commissionRate = commissionRate; }
    public BigDecimal getMarkupRate() { return markupRate; }
    public void setMarkupRate(BigDecimal markupRate) { this.markupRate = markupRate; }
    public String getContractStatus() { return contractStatus; }
    public void setContractStatus(String contractStatus) { this.contractStatus = contractStatus; }
    public Long getSignUserId() { return signUserId; }
    public void setSignUserId(Long signUserId) { this.signUserId = signUserId; }
    public String getSignUserName() { return signUserName; }
    public void setSignUserName(String signUserName) { this.signUserName = signUserName; }
}
