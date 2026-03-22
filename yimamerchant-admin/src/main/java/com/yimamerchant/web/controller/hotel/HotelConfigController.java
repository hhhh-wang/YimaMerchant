package com.yimamerchant.web.controller.hotel;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.yimamerchant.common.core.controller.BaseController;
import com.yimamerchant.common.core.domain.AjaxResult;
import com.yimamerchant.system.hotel.domain.HotelBusinessConfig;
import com.yimamerchant.system.hotel.service.IHotelManageService;

@RestController
@RequestMapping("/hotel/config")
public class HotelConfigController extends BaseController
{
    @Autowired
    private IHotelManageService hotelManageService;

    @PreAuthorize("@ss.hasPermi('hotel:config:query')")
    @GetMapping("/get")
    public AjaxResult get()
    {
        return success(hotelManageService.selectBusinessConfigs());
    }

    @PreAuthorize("@ss.hasPermi('hotel:config:edit')")
    @PutMapping("/save")
    public AjaxResult save(@RequestBody List<HotelBusinessConfig> list)
    {
        return toAjax(hotelManageService.saveBusinessConfigs(list));
    }

    @PreAuthorize("@ss.hasPermi('hotel:config:log')")
    @GetMapping("/logs")
    public AjaxResult logs()
    {
        return success(hotelManageService.selectBusinessConfigLogs());
    }
}
