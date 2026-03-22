package com.yimamerchant.system.hotel.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yimamerchant.common.core.domain.BaseEntity;

public class HotelBillPayment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long billId;
    private String billNo;
    private String paymentNo;
    private BigDecimal paymentAmount;
    private String paymentStatus;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date paymentTime;
    private String voucherFileIds;
    private String invoiceTitle;
    private String invoiceTaxNo;
    private String bankAccountName;
    private String bankName;
    private String bankAccountNo;
    private Long applyUserId;
    private String applyUserName;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getBillId() { return billId; }
    public void setBillId(Long billId) { this.billId = billId; }
    public String getBillNo() { return billNo; }
    public void setBillNo(String billNo) { this.billNo = billNo; }
    public String getPaymentNo() { return paymentNo; }
    public void setPaymentNo(String paymentNo) { this.paymentNo = paymentNo; }
    public BigDecimal getPaymentAmount() { return paymentAmount; }
    public void setPaymentAmount(BigDecimal paymentAmount) { this.paymentAmount = paymentAmount; }
    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
    public Date getPaymentTime() { return paymentTime; }
    public void setPaymentTime(Date paymentTime) { this.paymentTime = paymentTime; }
    public String getVoucherFileIds() { return voucherFileIds; }
    public void setVoucherFileIds(String voucherFileIds) { this.voucherFileIds = voucherFileIds; }
    public String getInvoiceTitle() { return invoiceTitle; }
    public void setInvoiceTitle(String invoiceTitle) { this.invoiceTitle = invoiceTitle; }
    public String getInvoiceTaxNo() { return invoiceTaxNo; }
    public void setInvoiceTaxNo(String invoiceTaxNo) { this.invoiceTaxNo = invoiceTaxNo; }
    public String getBankAccountName() { return bankAccountName; }
    public void setBankAccountName(String bankAccountName) { this.bankAccountName = bankAccountName; }
    public String getBankName() { return bankName; }
    public void setBankName(String bankName) { this.bankName = bankName; }
    public String getBankAccountNo() { return bankAccountNo; }
    public void setBankAccountNo(String bankAccountNo) { this.bankAccountNo = bankAccountNo; }
    public Long getApplyUserId() { return applyUserId; }
    public void setApplyUserId(Long applyUserId) { this.applyUserId = applyUserId; }
    public String getApplyUserName() { return applyUserName; }
    public void setApplyUserName(String applyUserName) { this.applyUserName = applyUserName; }
}
