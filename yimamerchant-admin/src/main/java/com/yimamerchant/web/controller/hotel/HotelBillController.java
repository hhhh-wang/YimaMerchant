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
import com.yimamerchant.common.core.controller.BaseController;
import com.yimamerchant.common.core.domain.AjaxResult;
import com.yimamerchant.common.core.page.TableDataInfo;
import com.yimamerchant.system.hotel.domain.HotelBill;
import com.yimamerchant.system.hotel.domain.HotelBillPayment;
import com.yimamerchant.system.hotel.dto.HotelBillGenerateDTO;
import com.yimamerchant.system.hotel.dto.HotelBillOperateDTO;
import com.yimamerchant.system.hotel.service.IHotelManageService;

@RestController
@RequestMapping("/hotel/finance/bill")
public class HotelBillController extends BaseController
{
    @Autowired
    private IHotelManageService hotelManageService;

    @PreAuthorize("@ss.hasPermi('hotel:finance:bill:list')")
    @GetMapping("/list")
    public TableDataInfo list(HotelBill query)
    {
        startPage();
        return getDataTable(hotelManageService.selectBillList(query));
    }

    @PreAuthorize("@ss.hasPermi('hotel:finance:bill:generate')")
    @PostMapping("/generate")
    public AjaxResult generate(@RequestBody HotelBillGenerateDTO dto)
    {
        return success(hotelManageService.generateBill(dto));
    }

    @PreAuthorize("@ss.hasPermi('hotel:finance:bill:query')")
    @GetMapping("/{billNo}")
    public AjaxResult getInfo(@PathVariable String billNo)
    {
        return success(hotelManageService.selectBillByNo(billNo));
    }

    @PreAuthorize("@ss.hasPermi('hotel:finance:bill:query')")
    @GetMapping("/orders")
    public AjaxResult orders(String billNo, String orderNo)
    {
        return success(hotelManageService.selectBillOrders(billNo, orderNo));
    }

    @PreAuthorize("@ss.hasPermi('hotel:finance:bill:confirm')")
    @PutMapping("/confirm")
    public AjaxResult confirm(@RequestBody HotelBillOperateDTO dto) { return toAjax(hotelManageService.confirmBill(dto)); }

    @PreAuthorize("@ss.hasPermi('hotel:finance:bill:dispute')")
    @PutMapping("/dispute")
    public AjaxResult dispute(@RequestBody HotelBillOperateDTO dto) { return toAjax(hotelManageService.disputeBill(dto)); }

    @PreAuthorize("@ss.hasPermi('hotel:finance:bill:recalculate')")
    @PutMapping("/recalculate")
    public AjaxResult recalculate(@RequestBody HotelBillOperateDTO dto) { return toAjax(hotelManageService.recalculateBill(dto)); }

    @PreAuthorize("@ss.hasPermi('hotel:finance:bill:payment')")
    @PostMapping("/payment")
    public AjaxResult payment(@RequestBody HotelBillPayment entity) { return toAjax(hotelManageService.paymentBill(entity)); }

    @PreAuthorize("@ss.hasPermi('hotel:finance:bill:export')")
    @GetMapping("/export")
    public AjaxResult export(HotelBill query)
    {
        return success(hotelManageService.selectBillList(query));
    }

    @PreAuthorize("@ss.hasPermi('hotel:finance:bill:query')")
    @GetMapping("/statistics")
    public AjaxResult statistics(HotelBill query)
    {
        return success(hotelManageService.selectBillStatistics(query));
    }
}
