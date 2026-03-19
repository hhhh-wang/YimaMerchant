package com.yimamerchant.web.controller.hotel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
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
import com.yimamerchant.system.domain.hotel.form.HotelRoomTypeForm;
import com.yimamerchant.system.domain.hotel.form.HotelRoomTypeStatusForm;
import com.yimamerchant.system.domain.hotel.query.HotelRoomTypeQuery;
import com.yimamerchant.system.domain.hotel.vo.HotelRoomTypeDetailVO;
import com.yimamerchant.system.domain.hotel.vo.HotelRoomTypeVO;
import com.yimamerchant.system.domain.hotel.vo.HotelRoomTypeCompleteCheckVO;
import com.yimamerchant.system.service.hotel.IHotelRoomTypeService;

/**
 * 房型管理 控制器
 *
 * 按照《酒店商家端后端接口文档》5. 房型管理接口实现，
 * 仅负责参数接收、权限与审计日志，具体业务委托给 service 层。
 */
@RestController
@RequestMapping("/hotel/roomType")
public class HotelRoomTypeController extends BaseController
{
    @Autowired
    private IHotelRoomTypeService hotelRoomTypeService;

    /**
     * 5.1 分页查询房型列表
     */
    @PreAuthorize("@ss.hasPermi('hotel:roomType:list')")
    @GetMapping("/list")
    public TableDataInfo list(HotelRoomTypeQuery query) {
        startPage();
        List<HotelRoomTypeVO> list = hotelRoomTypeService.selectRoomTypeList(query);
        return getDataTable(list);
    }
    /**
     * 5.2 查询房型详情
     */
    @PreAuthorize("@ss.hasPermi('hotel:roomType:query')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        HotelRoomTypeDetailVO detail = hotelRoomTypeService.getRoomTypeDetail(id);
        return success(detail);
    }

    /**
     * 5.3 新增房型
     */
    @Log(title = "房型管理", businessType = BusinessType.INSERT)
    @PreAuthorize("@ss.hasPermi('hotel:roomType:add')")
    @PostMapping
    public AjaxResult add(@Validated @RequestBody HotelRoomTypeForm form)
    {
        int rows = hotelRoomTypeService.addRoomType(form, getUsername());
        return rows > 0 ? success("新增成功") : error("新增失败");
    }

    /**
     * 5.4 修改房型
     */
    @Log(title = "房型管理", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermi('hotel:roomType:edit')")
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody HotelRoomTypeForm form)
    {
        int rows = hotelRoomTypeService.updateRoomType(form, getUsername());
        return rows > 0 ? success("修改成功") : error("修改失败");
    }

    /**
     * 5.5 删除房型
     */
    @Log(title = "房型管理", businessType = BusinessType.DELETE)
    @PreAuthorize("@ss.hasPermi('hotel:roomType:remove')")
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable("id") Long id)
    {
        int rows = hotelRoomTypeService.deleteRoomType(id, getUsername());
        return rows > 0 ? success("删除成功") : error("删除失败");
    }

    /**
     * 5.6 修改房型配置状态
     */
    @Log(title = "房型管理", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermi('hotel:roomType:edit')")
    @PutMapping("/changeConfigStatus")
    public AjaxResult changeConfigStatus(@Validated @RequestBody HotelRoomTypeStatusForm form)
    {
        int rows = hotelRoomTypeService.changeConfigStatus(form, getUsername());
        return rows > 0 ? success("操作成功") : error("操作失败");
    }

    /**
     * 5.7 修改房型上架状态
     */
    @Log(title = "房型管理", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermi('hotel:roomType:changeStatus')")
    @PutMapping("/changeSaleStatus")
    public AjaxResult changeSaleStatus(@Validated @RequestBody HotelRoomTypeStatusForm form)
    {
        int rows = hotelRoomTypeService.changeSaleStatus(form, getUsername());
        return rows > 0 ? success("操作成功") : error("操作失败");
    }

    /**
     * 5.8 房型完整性校验接口
     */
    @PreAuthorize("@ss.hasPermi('hotel:roomType:query')")
    @GetMapping("/checkComplete/{id}")
    public AjaxResult checkComplete(@PathVariable("id") Long id)
    {
        HotelRoomTypeCompleteCheckVO result = hotelRoomTypeService.checkComplete(id);
        return success(result);
    }
}

