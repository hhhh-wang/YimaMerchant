package com.yimamerchant.system.hotel.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yimamerchant.common.core.domain.BaseEntity;

public class HotelPendingAuditLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long applyId;
    private String operateType;
    private Long operateUserId;
    private String operateUserName;
    private String operateRemark;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date operateTime;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getApplyId() { return applyId; }
    public void setApplyId(Long applyId) { this.applyId = applyId; }
    public String getOperateType() { return operateType; }
    public void setOperateType(String operateType) { this.operateType = operateType; }
    public Long getOperateUserId() { return operateUserId; }
    public void setOperateUserId(Long operateUserId) { this.operateUserId = operateUserId; }
    public String getOperateUserName() { return operateUserName; }
    public void setOperateUserName(String operateUserName) { this.operateUserName = operateUserName; }
    public String getOperateRemark() { return operateRemark; }
    public void setOperateRemark(String operateRemark) { this.operateRemark = operateRemark; }
    public Date getOperateTime() { return operateTime; }
    public void setOperateTime(Date operateTime) { this.operateTime = operateTime; }
}
