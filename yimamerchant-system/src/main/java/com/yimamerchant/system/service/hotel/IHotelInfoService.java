package com.yimamerchant.system.service.hotel;

import com.yimamerchant.system.domain.hotel.form.HotelInfoForm;
import com.yimamerchant.system.domain.hotel.form.HotelInfoStatusForm;
import com.yimamerchant.system.domain.hotel.vo.HotelInfoVO;

/**
 * 酒店信息 服务层
 */
public interface IHotelInfoService
{
    /**
     * 查询当前商家酒店信息
     */
    HotelInfoVO getCurrentMerchantHotelInfo();

    /**
     * 保存当前商家酒店信息（有则更新，无则新增）
     */
    int saveCurrentMerchantHotelInfo(HotelInfoForm form, String operator);

    /**
     * 修改酒店状态（仅当前商家自己的酒店）
     */
    int changeStatus(HotelInfoStatusForm form, String operator);
}

