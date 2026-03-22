package com.yimamerchant.web.controller.hotel;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
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
import com.yimamerchant.common.utils.poi.ExcelUtil;
import com.yimamerchant.system.hotel.domain.HotelOrder;
import com.yimamerchant.system.hotel.dto.HotelOrderOperateDTO;
import com.yimamerchant.system.hotel.service.IHotelManageService;

@RestController
@RequestMapping("/hotel/service/order")
public class HotelOrderController extends BaseController
{
    @Autowired
    private IHotelManageService hotelManageService;

    @PreAuthorize("@ss.hasPermi('hotel:service:order:list')")
    @GetMapping("/list")
    public TableDataInfo list(HotelOrder query)
    {
        startPage();
        return getDataTable(hotelManageService.selectOrderList(query));
    }

    @PreAuthorize("@ss.hasPermi('hotel:service:order:query')")
    @GetMapping("/{orderNo}")
    public AjaxResult getInfo(@PathVariable String orderNo)
    {
        return success(hotelManageService.selectOrderByNo(orderNo));
    }

    @PreAuthorize("@ss.hasPermi('hotel:service:order:confirm')")
    @PutMapping("/confirm")
    public AjaxResult confirm(@RequestBody HotelOrderOperateDTO dto) { return toAjax(hotelManageService.confirmOrder(dto)); }

    @PreAuthorize("@ss.hasPermi('hotel:service:order:cancel')")
    @PutMapping("/cancel")
    public AjaxResult cancel(@RequestBody HotelOrderOperateDTO dto) { return toAjax(hotelManageService.cancelOrder(dto)); }

    @PreAuthorize("@ss.hasPermi('hotel:service:order:checkin')")
    @PutMapping("/checkin")
    public AjaxResult checkin(@RequestBody HotelOrderOperateDTO dto) { return toAjax(hotelManageService.checkinOrder(dto)); }

    @PreAuthorize("@ss.hasPermi('hotel:service:order:checkout')")
    @PutMapping("/checkout")
    public AjaxResult checkout(@RequestBody HotelOrderOperateDTO dto) { return toAjax(hotelManageService.checkoutOrder(dto)); }

    @PreAuthorize("@ss.hasPermi('hotel:service:order:remark')")
    @PostMapping("/remark")
    public AjaxResult remark(@RequestBody HotelOrderOperateDTO dto) { return toAjax(hotelManageService.remarkOrder(dto)); }

    @PreAuthorize("@ss.hasPermi('hotel:service:order:export')")
    @Log(title = "酒店订单导出", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HotelOrder query)
    {
        List<HotelOrder> list = hotelManageService.selectOrderList(query);
        new ExcelUtil<>(HotelOrder.class).exportExcel(response, list, "酒店订单");
    }

    @PreAuthorize("@ss.hasPermi('hotel:service:order:query')")
    @GetMapping("/logs")
    public AjaxResult logs(String orderNo)
    {
        return success(hotelManageService.selectOrderLogs(orderNo));
    }

    @PreAuthorize("@ss.hasPermi('hotel:service:order:refund')")
    @PostMapping("/refund")
    public AjaxResult refund(@RequestBody HotelOrderOperateDTO dto) { return toAjax(hotelManageService.refundOrder(dto)); }

    @PreAuthorize("@ss.hasPermi('hotel:service:order:dispute')")
    @PostMapping("/dispute")
    public AjaxResult dispute(@RequestBody HotelOrderOperateDTO dto) { return toAjax(hotelManageService.disputeOrder(dto)); }
}
