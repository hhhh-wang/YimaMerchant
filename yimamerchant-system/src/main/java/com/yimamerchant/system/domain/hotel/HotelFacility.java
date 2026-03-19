package com.yimamerchant.system.domain.hotel;

import com.yimamerchant.common.core.domain.BaseEntity;

/**
 * 酒店设施表 hotel_facility
 *
 * 注意：按你的要求此类不生成 getter/setter，你可后续自行生成。
 */
public class HotelFacility extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    public Long id;

    /** 设施名称 */
    public String facilityName;

    /** 设施分类（1房间设施 2卫浴设施 3公共设施 4服务设施） */
    public String facilityType;

    /** 状态（0停用 1启用） */
    public String status;

    /** 排序号 */
    public Integer sortNum;

    /** 备注 */
    public String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

