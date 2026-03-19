package com.yimamerchant.web.controller.hotel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yimamerchant.common.annotation.Log;
import com.yimamerchant.common.core.controller.BaseController;
import com.yimamerchant.common.core.domain.AjaxResult;
import com.yimamerchant.common.core.page.TableDataInfo;
import com.yimamerchant.common.enums.BusinessType;
import com.yimamerchant.system.domain.hotel.form.HotelInventoryBatchForm;
import com.yimamerchant.system.domain.hotel.form.HotelInventoryPriceForm;
import com.yimamerchant.system.domain.hotel.form.HotelInventoryStockForm;
import com.yimamerchant.system.domain.hotel.form.HotelInventoryStatusForm;
import com.yimamerchant.system.domain.hotel.query.HotelInventoryQuery;
import com.yimamerchant.system.domain.hotel.vo.HotelInventoryVO;
import com.yimamerchant.system.service.hotel.IHotelInventoryService;

/**
 * 库存价格管理 控制器
 *
 * 对接接口文档 7. 库存价格管理接口。
 */
@RestController
@RequestMapping("/hotel/inventory")
public class HotelInventoryController extends BaseController
{
    @Autowired
    private IHotelInventoryService hotelInventoryService;

    /**
     * 7.1 分页查询库存价格列表
     */
    @PreAuthorize("@ss.hasPermi('hotel:inventory:list')")
    @GetMapping("/list")
    public TableDataInfo list(HotelInventoryQuery query)
    {
        startPage();
        List<HotelInventoryVO> list = hotelInventoryService.selectInventoryList(query);
        return getDataTable(list);
    }

    /**
     * 7.2 修改单个房型默认价格
     */
    @Log(title = "库存价格管理", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermi('hotel:inventory:edit')")
    @PutMapping("/updateBasePrice")
    public AjaxResult updateBasePrice(@Validated @RequestBody HotelInventoryPriceForm form)
    {
        int rows = hotelInventoryService.updateBasePrice(form, getUsername());
        return rows > 0 ? success("操作成功") : error("操作失败");
    }

    /**
     * 7.3 修改单个房型默认库存
     */
    @Log(title = "库存价格管理", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermi('hotel:inventory:edit')")
    @PutMapping("/updateBaseStock")
    public AjaxResult updateBaseStock(@Validated @RequestBody HotelInventoryStockForm form)
    {
        int rows = hotelInventoryService.updateBaseStock(form, getUsername());
        return rows > 0 ? success("操作成功") : error("操作失败");
    }

    /**
     * 7.4 批量修改价格库存
     */
    @Log(title = "库存价格管理", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermi('hotel:inventory:batchEdit')")
    @PutMapping("/batchUpdate")
    public AjaxResult batchUpdateInventory(@Validated @RequestBody HotelInventoryBatchForm form)
    {
        int rows = hotelInventoryService.batchUpdateInventory(form, getUsername());
        return rows > 0 ? success("批量更新成功") : error("批量更新失败");
    }

    /**
     * 7.5 修改可预订状态
     */
    @Log(title = "库存价格管理", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermi('hotel:inventory:changeStatus')")
    @PutMapping("/changeBookableFlag")
    public AjaxResult changeBookableFlag(@Validated @RequestBody HotelInventoryStatusForm form)
    {
        int rows = hotelInventoryService.changeBookableFlag(form, getUsername());
        return rows > 0 ? success("操作成功") : error("操作失败");
    }

    /**
     * 7.6 修改上下架状态（复用房型上/下架完整性校验逻辑）
     */
    @Log(title = "库存价格管理", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermi('hotel:inventory:changeStatus')")
    @PutMapping("/changeSaleStatus")
    public AjaxResult changeSaleStatus(@Validated @RequestBody HotelInventoryStatusForm form)
    {
        int rows = hotelInventoryService.changeSaleStatus(form, getUsername());
        return rows > 0 ? success("操作成功") : error("操作失败");
    }
}

