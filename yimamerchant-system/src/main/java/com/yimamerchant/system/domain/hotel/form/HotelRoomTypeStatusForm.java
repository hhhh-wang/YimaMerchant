package com.yimamerchant.system.domain.hotel.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 房型状态修改表单
 *
 * 用于配置状态与上架状态的修改接口。
 *
 * 注意：按你的要求此类不生成 getter/setter，你可后续自行生成。
 */
public class HotelRoomTypeStatusForm
{
    /** 房型ID */
    @NotNull(message = "房型ID不能为空")
    public Long id;

    /** 配置状态（0停用 1启用），仅在修改配置状态接口中使用 */
    public String configStatus;

    /** 上架状态（0下架 1上架），仅在修改上架状态接口中使用 */
    public String saleStatus;

    public @NotNull(message = "房型ID不能为空") Long getId() {
        return id;
    }

    public void setId(@NotNull(message = "房型ID不能为空") Long id) {
        this.id = id;
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

