package com.yimamerchant.web.controller.hotel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.yimamerchant.common.core.controller.BaseController;
import com.yimamerchant.common.core.domain.AjaxResult;
import com.yimamerchant.system.hotel.domain.HotelRoomCalendarLog;
import com.yimamerchant.system.hotel.dto.HotelPriceCalendarQuery;
import com.yimamerchant.system.hotel.dto.HotelPriceCopyDTO;
import com.yimamerchant.system.hotel.dto.HotelPriceOperateDTO;
import com.yimamerchant.system.hotel.service.IHotelManageService;

@RestController
@RequestMapping("/hotel/operate/price")
public class HotelPriceController extends BaseController
{
    @Autowired
    private IHotelManageService hotelManageService;

    @PreAuthorize("@ss.hasPermi('hotel:operate:price:query')")
    @GetMapping("/calendar")
    public AjaxResult calendar(HotelPriceCalendarQuery query)
    {
        return success(hotelManageService.selectPriceCalendar(query));
    }

    @PreAuthorize("@ss.hasPermi('hotel:operate:price:daily')")
    @PostMapping("/daily")
    public AjaxResult daily(@RequestBody HotelPriceOperateDTO dto)
    {
        return toAjax(hotelManageService.saveDailyPrice(dto));
    }

    @PreAuthorize("@ss.hasPermi('hotel:operate:price:batch')")
    @PostMapping("/batch")
    public AjaxResult batch(@RequestBody HotelPriceOperateDTO dto)
    {
        return toAjax(hotelManageService.batchSavePrice(dto));
    }

    @PreAuthorize("@ss.hasPermi('hotel:operate:price:close')")
    @PostMapping("/close")
    public AjaxResult close(@RequestBody HotelPriceOperateDTO dto)
    {
        return toAjax(hotelManageService.closePrice(dto));
    }

    @PreAuthorize("@ss.hasPermi('hotel:operate:price:copy')")
    @PostMapping("/copy")
    public AjaxResult copy(@RequestBody HotelPriceCopyDTO dto)
    {
        return toAjax(hotelManageService.copyPrice(dto));
    }

    @PreAuthorize("@ss.hasPermi('hotel:operate:price:restore')")
    @PostMapping("/restore")
    public AjaxResult restore(@RequestBody HotelPriceOperateDTO dto)
    {
        return toAjax(hotelManageService.restorePrice(dto));
    }

    @PreAuthorize("@ss.hasPermi('hotel:operate:price:history')")
    @GetMapping("/history")
    public AjaxResult history(HotelRoomCalendarLog query)
    {
        return success(hotelManageService.selectPriceLogList(query));
    }
}
