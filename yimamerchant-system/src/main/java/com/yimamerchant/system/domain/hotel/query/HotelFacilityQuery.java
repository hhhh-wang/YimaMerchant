package com.yimamerchant.system.domain.hotel.query;

/**
 * 设施查询参数
 *
 * 注意：按你的要求此类不生成 getter/setter，你可后续自行生成。
 */
public class HotelFacilityQuery
{
    /** 设施分类 */
    public String facilityType;

    /** 状态（0停用 1启用），前端不传时默认查启用 */
    public String status;

    public String getFacilityType() {
        return facilityType;
    }

    public void setFacilityType(String facilityType) {
        this.facilityType = facilityType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

