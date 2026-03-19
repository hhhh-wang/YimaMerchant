package com.yimamerchant.web.controller.hotel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yimamerchant.common.annotation.Log;
import com.yimamerchant.common.core.controller.BaseController;
import com.yimamerchant.common.core.domain.AjaxResult;
import com.yimamerchant.common.enums.BusinessType;
import com.yimamerchant.system.domain.hotel.HotelFacility;
import com.yimamerchant.system.domain.hotel.form.HotelRoomTypeFacilityForm;
import com.yimamerchant.system.domain.hotel.query.HotelFacilityQuery;
import com.yimamerchant.system.service.hotel.IHotelFacilityService;

/**
 * 酒店设施 控制器
 *
 * 对接接口文档 6. 设施接口。
 */
@RestController
@RequestMapping("/hotel/facility")
public class HotelFacilityController extends BaseController
{
    @Autowired
    private IHotelFacilityService hotelFacilityService;

    /**
     * 6.1 查询设施列表
     *
     * 根据文档建议，权限可复用 hotel:roomType:query。
     */
    @PreAuthorize("@ss.hasPermi('hotel:roomType:query')")
    @GetMapping("/list")
    public AjaxResult list(HotelFacilityQuery query)
    {
        List<HotelFacility> list = hotelFacilityService.selectFacilityList(query);
        return success(list);
    }

    /**
     * 6.2 查询房型已绑定设施
     */
    @PreAuthorize("@ss.hasPermi('hotel:roomType:query')")
    @GetMapping("/roomType/{roomTypeId}")
    public AjaxResult getRoomTypeFacilities(@PathVariable("roomTypeId") Long roomTypeId)
    {
        List<Long> ids = hotelFacilityService.selectRoomTypeFacilityIds(roomTypeId);
        return success(ids);
    }

    /**
     * 6.3 保存房型设施绑定
     */
    @Log(title = "房型设施绑定", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermi('hotel:roomType:edit')")
    @PostMapping("/saveRoomTypeFacilities")
    public AjaxResult saveRoomTypeFacilities(@Validated @RequestBody HotelRoomTypeFacilityForm form)
    {
        int rows = hotelFacilityService.saveRoomTypeFacilities(form);
        return rows > 0 ? success("保存成功") : error("保存失败");
    }
}

