package com.yimamerchant.system.domain.hotel.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 酒店状态修改表单
 *
 * 注意：按你的要求此类不生成 getter/setter，你可后续自行生成。
 */

public class HotelInfoStatusForm
{
    @NotNull(message = "酒店ID不能为空")
    public Long id;

    @NotBlank(message = "酒店状态不能为空")
    public String status;


    public @NotNull(message = "酒店ID不能为空") Long getId() {
        return id;
    }

    public void setId(@NotNull(message = "酒店ID不能为空") Long id) {
        this.id = id;
    }

    public @NotBlank(message = "酒店状态不能为空") String getStatus() {
        return status;
    }

    public void setStatus(@NotBlank(message = "酒店状态不能为空") String status) {
        this.status = status;
    }
}

