package com.yimamerchant.web.controller.hotel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.yimamerchant.common.annotation.Log;
import com.yimamerchant.common.core.controller.BaseController;
import com.yimamerchant.common.core.domain.AjaxResult;
import com.yimamerchant.common.core.page.TableDataInfo;
import com.yimamerchant.common.enums.BusinessType;
import com.yimamerchant.system.hotel.domain.HotelPartner;
import com.yimamerchant.system.hotel.service.IHotelManageService;

@RestController
@RequestMapping("/hotel/operate/info")
public class HotelInfoController extends BaseController
{
    @Autowired
    private IHotelManageService hotelManageService;

    @PreAuthorize("@ss.hasPermi('hotel:operate:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(HotelPartner query)
    {
        startPage();
        return getDataTable(hotelManageService.selectHotelInfoList(query));
    }

    @PreAuthorize("@ss.hasPermi('hotel:operate:info:query')")
    @GetMapping("/{hotelId}")
    public AjaxResult getInfo(@PathVariable Long hotelId)
    {
        return success(hotelManageService.selectHotelInfoById(hotelId));
    }

    @PreAuthorize("@ss.hasPermi('hotel:operate:info:add')")
    @Log(title = "酒店信息新增", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HotelPartner entity)
    {
        return toAjax(hotelManageService.insertHotelInfo(entity));
    }

    @PreAuthorize("@ss.hasPermi('hotel:operate:info:edit')")
    @Log(title = "酒店信息修改", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HotelPartner entity)
    {
        return toAjax(hotelManageService.updateHotelInfo(entity));
    }

    @PreAuthorize("@ss.hasPermi('hotel:operate:info:edit')")
    @Log(title = "酒店信息状态修改", businessType = BusinessType.UPDATE)
    @PutMapping("/status")
    public AjaxResult status(@RequestBody HotelPartner entity)
    {
        return toAjax(hotelManageService.updateHotelInfoStatus(entity));
    }

    @PreAuthorize("@ss.hasPermi('hotel:operate:info:query')")
    @GetMapping("/options")
    public AjaxResult options(String keyword)
    {
        return success(hotelManageService.selectHotelOptions(keyword));
    }

    @PreAuthorize("@ss.hasPermi('hotel:operate:info:query')")
    @GetMapping("/changeLog")
    public AjaxResult changeLog(Long hotelId)
    {
        return success(hotelManageService.selectHotelChangeLogs(hotelId));
    }
}
