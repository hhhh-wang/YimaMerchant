package com.yimamerchant.web.controller.hotel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.yimamerchant.common.annotation.Log;
import com.yimamerchant.common.core.controller.BaseController;
import com.yimamerchant.common.core.domain.AjaxResult;
import com.yimamerchant.common.core.page.TableDataInfo;
import com.yimamerchant.common.enums.BusinessType;
import com.yimamerchant.system.hotel.domain.HotelBdBind;
import com.yimamerchant.system.hotel.domain.HotelBdBindHistory;
import com.yimamerchant.system.hotel.dto.HotelBindBatchDTO;
import com.yimamerchant.system.hotel.dto.HotelBindTransferDTO;
import com.yimamerchant.system.hotel.service.IHotelManageService;

@RestController
@RequestMapping("/hotel/cooperate/bind")
public class HotelBindController extends BaseController
{
    @Autowired
    private IHotelManageService hotelManageService;

    @PreAuthorize("@ss.hasPermi('hotel:cooperate:bind:list')")
    @GetMapping("/list")
    public TableDataInfo list(HotelBdBind query)
    {
        startPage();
        return getDataTable(hotelManageService.selectBindList(query));
    }

    @PreAuthorize("@ss.hasPermi('hotel:cooperate:bind:add')")
    @Log(title = "BD酒店绑定", businessType = BusinessType.INSERT)
    @PostMapping("/batch")
    public AjaxResult batch(@RequestBody HotelBindBatchDTO dto)
    {
        return toAjax(hotelManageService.batchBind(dto));
    }

    @PreAuthorize("@ss.hasPermi('hotel:cooperate:bind:remove')")
    @Log(title = "BD酒店解绑", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(hotelManageService.batchUnbind(ids));
    }

    @PreAuthorize("@ss.hasPermi('hotel:cooperate:bind:transfer')")
    @Log(title = "BD酒店转移", businessType = BusinessType.UPDATE)
    @PutMapping("/transfer")
    public AjaxResult transfer(@RequestBody HotelBindTransferDTO dto)
    {
        return toAjax(hotelManageService.transferBind(dto));
    }

    @PreAuthorize("@ss.hasPermi('hotel:cooperate:bind:history')")
    @GetMapping("/history")
    public TableDataInfo history(HotelBdBindHistory query)
    {
        startPage();
        return getDataTable(hotelManageService.selectBindHistoryList(query));
    }

    @PreAuthorize("@ss.hasPermi('hotel:cooperate:bind:query')")
    @GetMapping("/bdOptions")
    public AjaxResult bdOptions(@RequestParam(required = false) String keyword)
    {
        return success(hotelManageService.selectBdOptions(keyword));
    }

    @PreAuthorize("@ss.hasPermi('hotel:cooperate:bind:query')")
    @GetMapping("/hotelOptions")
    public AjaxResult hotelOptions(@RequestParam(required = false) String keyword)
    {
        return success(hotelManageService.selectHotelOptions(keyword));
    }
}
