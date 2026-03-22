package com.yimamerchant.system.hotel.dto;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

public class HotelPendingApproveDTO
{
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date contractStartDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date contractEndDate;
    private String commissionMode;
    private BigDecimal commissionRate;
    private BigDecimal markupRate;
    private Long bdUserId;
    private String auditRemark;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
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
    public Long getBdUserId() { return bdUserId; }
    public void setBdUserId(Long bdUserId) { this.bdUserId = bdUserId; }
    public String getAuditRemark() { return auditRemark; }
    public void setAuditRemark(String auditRemark) { this.auditRemark = auditRemark; }
}
