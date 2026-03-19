package com.yimamerchant.system.domain.hotel.form;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * 酒店退款审核表单
 */
public class HotelRefundAuditForm
{
    /** 退款单 ID */
    @NotNull(message = "退款单ID不能为空")
    public Long id;

    /** 审核备注 */
    @Size(max = 500, message = "审核备注最多500字")
    public String auditRemark;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getAuditRemark()
    {
        return auditRemark;
    }

    public void setAuditRemark(String auditRemark)
    {
        this.auditRemark = auditRemark;
    }



}

