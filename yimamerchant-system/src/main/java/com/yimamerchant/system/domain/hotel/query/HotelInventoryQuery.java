package com.yimamerchant.system.domain.hotel.query;

/**
 * 库存价格分页查询参数
 */
public class HotelInventoryQuery
{
    /** 商家编号（服务层注入，不由前端传参） */
    public Long merchantId;

    /** 页码 */
    public Integer pageNum;

    /** 每页条数 */
    public Integer pageSize;

    /** 房型名称 */
    public String roomTypeName;

    /** 上架状态 */
    public String saleStatus;

    /** 是否可预订 */
    public String bookableFlag;

    public Long getMerchantId()
    {
        return merchantId;
    }

    public void setMerchantId(Long merchantId)
    {
        this.merchantId = merchantId;
    }

    public Integer getPageNum()
    {
        return pageNum;
    }

    public void setPageNum(Integer pageNum)
    {
        this.pageNum = pageNum;
    }

    public Integer getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }

    public String getRoomTypeName()
    {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName)
    {
        this.roomTypeName = roomTypeName;
    }

    public String getSaleStatus()
    {
        return saleStatus;
    }

    public void setSaleStatus(String saleStatus)
    {
        this.saleStatus = saleStatus;
    }

    public String getBookableFlag()
    {
        return bookableFlag;
    }

    public void setBookableFlag(String bookableFlag)
    {
        this.bookableFlag = bookableFlag;
    }
}

