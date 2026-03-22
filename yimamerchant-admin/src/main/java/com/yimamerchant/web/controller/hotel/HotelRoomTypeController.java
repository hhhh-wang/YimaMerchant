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
import org.springframework.web.bind.annotation.RestController;
import com.yimamerchant.common.annotation.Log;
import com.yimamerchant.common.core.controller.BaseController;
import com.yimamerchant.common.core.domain.AjaxResult;
import com.yimamerchant.common.core.page.TableDataInfo;
import com.yimamerchant.common.enums.BusinessType;
import com.yimamerchant.system.hotel.domain.HotelRoomType;
import com.yimamerchant.system.hotel.dto.HotelRoomTypeCopyDTO;
import com.yimamerchant.system.hotel.dto.HotelRoomTypeSortDTO;
import com.yimamerchant.system.hotel.dto.HotelRoomTypeStatusDTO;
import com.yimamerchant.system.hotel.service.IHotelManageService;

@RestController
@RequestMapping("/hotel/operate/roomType")
public class HotelRoomTypeController extends BaseController
{
    @Autowired
    private IHotelManageService hotelManageService;

    @PreAuthorize("@ss.hasPermi('hotel:operate:roomType:list')")
    @GetMapping("/list")
    public TableDataInfo list(HotelRoomType query)
    {
        startPage();
        return getDataTable(hotelManageService.selectRoomTypeList(query));
    }

    @PreAuthorize("@ss.hasPermi('hotel:operate:roomType:query')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable Long id)
    {
        return success(hotelManageService.selectRoomTypeById(id));
    }

    @PreAuthorize("@ss.hasPermi('hotel:operate:roomType:add')")
    @Log(title = "房型新增", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HotelRoomType entity)
    {
        return toAjax(hotelManageService.insertRoomType(entity));
    }

    @PreAuthorize("@ss.hasPermi('hotel:operate:roomType:edit')")
    @Log(title = "房型修改", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HotelRoomType entity)
    {
        return toAjax(hotelManageService.updateRoomType(entity));
    }

    @PreAuthorize("@ss.hasPermi('hotel:operate:roomType:remove')")
    @Log(title = "房型删除", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(hotelManageService.deleteRoomTypeByIds(ids));
    }

    @PreAuthorize("@ss.hasPermi('hotel:operate:roomType:status')")
    @PutMapping("/status")
    public AjaxResult status(@RequestBody HotelRoomTypeStatusDTO dto)
    {
        return toAjax(hotelManageService.updateRoomTypeStatus(dto));
    }

    @PreAuthorize("@ss.hasPermi('hotel:operate:roomType:sort')")
    @PutMapping("/sort")
    public AjaxResult sort(@RequestBody HotelRoomTypeSortDTO dto)
    {
        return toAjax(hotelManageService.updateRoomTypeSort(dto));
    }

    @PreAuthorize("@ss.hasPermi('hotel:operate:roomType:copy')")
    @PostMapping("/copy")
    public AjaxResult copy(@RequestBody HotelRoomTypeCopyDTO dto)
    {
        return toAjax(hotelManageService.copyRoomType(dto));
    }
}
