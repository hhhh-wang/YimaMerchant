package com.yimamerchant.system.domain.hotel.form;

import jakarta.validation.constraints.NotNull;

/**
 * 修改是否可预订 / 上下架状态表单
 */
public class HotelInventoryStatusForm
{
    /** 房型ID */
    @NotNull(message = "房型ID不能为空")
    public Long roomTypeId;

    /** 是否可预订（Y/N），仅用于 changeBookableFlag */
    public String bookableFlag;

    /** 上架状态（0下架 1上架），仅用于 changeSaleStatus */
    public String saleStatus;

    public Long getRoomTypeId()
    {
        return roomTypeId;
    }

    public void setRoomTypeId(Long roomTypeId)
    {
        this.roomTypeId = roomTypeId;
    }

    public String getBookableFlag()
    {
        return bookableFlag;
    }

    public void setBookableFlag(String bookableFlag)
    {
        this.bookableFlag = bookableFlag;
    }

    public String getSaleStatus()
    {
        return saleStatus;
    }

    public void setSaleStatus(String saleStatus)
    {
        this.saleStatus = saleStatus;
    }
}

