package com.yimamerchant.system.domain.hotel;

import java.math.BigDecimal;
import com.yimamerchant.common.core.domain.BaseEntity;

/**
 * 酒店信息表
 * 
 * 注意：为符合你的约定，本类字段不额外生成 get/set，
 * 由你在 IDEA 中自动补全时，MyBatis/Jackson 将更容易工作。
 */
public class HotelInfo extends BaseEntity
{
    public Long id;

    public Long merchantId;

    public String hotelName;

    public String hotelCover;

    /** JSON字符串：前端数组 hotelImages */
    public String hotelImages;

    public String phone;

    public String provinceCode;

    public String cityCode;

    public String districtCode;

    public String address;

    public BigDecimal longitude;

    public BigDecimal latitude;

    public String checkInTime;

    public String checkOutTime;

    public String intro;

    public String bookingNotice;

    public String invoiceDesc;

    public String parkingDesc;

    /** 佣金模式：hotel_commission_mode */
    public String commissionMode;

    public BigDecimal commissionValue;

    /** 当前BD */
    public Long bdUserId;

    /** 当前BD名称快照 */
    public String bdName;

    /** 当前BD电话（快照） */
    public String bdPhone;

    /** 退款规则类型：hotel_refund_rule_type */
    public String refundRuleType;

    public String refundRuleDesc;

    public String refundDeadlineDesc;

    /** 酒店状态：hotel_status */
    public String status;

    /** 删除标记（0存在 2删除） */
    public String delFlag;
}

