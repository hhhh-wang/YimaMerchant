package com.yimamerchant.web.controller.hotel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.yimamerchant.common.annotation.Log;
import com.yimamerchant.common.core.controller.BaseController;
import com.yimamerchant.common.core.domain.AjaxResult;
import com.yimamerchant.common.enums.BusinessType;
import com.yimamerchant.system.domain.hotel.form.HotelInfoForm;
import com.yimamerchant.system.domain.hotel.form.HotelInfoStatusForm;
import com.yimamerchant.system.domain.hotel.vo.HotelInfoVO;
import com.yimamerchant.system.service.hotel.IHotelInfoService;

@RestController
@RequestMapping("/hotel/info")
public class HotelInfoController extends BaseController
{
    @Autowired
    private IHotelInfoService hotelInfoService;

    /**
     * 查询当前商家酒店信息
     */
    @PreAuthorize("@ss.hasPermi('hotel:info:query')")
    @GetMapping("/getInfo")
    public AjaxResult getInfo()
    {
        HotelInfoVO vo = hotelInfoService.getCurrentMerchantHotelInfo();
        return success(vo);
    }

    /**
     * 新增或保存酒店信息（有则更新，无则新增）
     */
    @Log(title = "酒店信息", businessType = BusinessType.INSERT)
    @PreAuthorize("@ss.hasPermi('hotel:info:edit')")
    @PostMapping("/save")
    public AjaxResult save(@Validated @RequestBody HotelInfoForm form)
    {
        int rows = hotelInfoService.saveCurrentMerchantHotelInfo(form, getUsername());
        return rows > 0 ? success("保存成功") : error("保存失败");
    }

    /**
     * 修改酒店状态
     */
    @Log(title = "酒店信息", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermi('hotel:info:edit')")
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@Validated @RequestBody HotelInfoStatusForm form)
    {
        int rows = hotelInfoService.changeStatus(form, getUsername());
        return rows > 0 ? success("操作成功") : error("操作失败");
    }
}

