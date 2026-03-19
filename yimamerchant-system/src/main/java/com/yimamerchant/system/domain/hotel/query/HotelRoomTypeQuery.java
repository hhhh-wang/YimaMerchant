package com.yimamerchant.system.domain.hotel.query;

/**
 * 房型分页查询参数
 *
 * 注意：按你的要求此类不生成 getter/setter，你可后续自行生成。
 */
public class HotelRoomTypeQuery
{
    /** 商家编号（服务层注入，不由前端传参） */
    public Long merchantId;

    /** 页码 */
    public Integer pageNum;

    /** 每页条数 */
    public Integer pageSize;

    /** 房型名称 */
    public String roomTypeName;

    /** 配置状态（0停用 1启用） */
    public String configStatus;

    /** 上架状态（0下架 1上架） */
    public String saleStatus;

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    public String getConfigStatus() {
        return configStatus;
    }

    public void setConfigStatus(String configStatus) {
        this.configStatus = configStatus;
    }

    public String getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(String saleStatus) {
        this.saleStatus = saleStatus;
    }
}

