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
import com.yimamerchant.common.core.page.TableDataInfo;
import com.yimamerchant.common.enums.BusinessType;
import com.yimamerchant.system.domain.hotel.form.HotelRefundAuditForm;
import com.yimamerchant.system.domain.hotel.query.HotelRefundQuery;
import com.yimamerchant.system.domain.hotel.vo.HotelRefundDetailVO;
import com.yimamerchant.system.domain.hotel.vo.HotelRefundVO;
import com.yimamerchant.system.service.hotel.IHotelRefundService;

/**
 * 酒店退款管理 控制器
 */
@RestController
@RequestMapping("/hotel/refund")
public class HotelRefundController extends BaseController
{
    @Autowired
    private IHotelRefundService hotelRefundService;

    /**
     * 8.1 分页查询退款列表
     */
    @PreAuthorize("@ss.hasPermi('hotel:refund:query')")
    @GetMapping("/list")
    public TableDataInfo list(HotelRefundQuery query)
    {
        startPage();
        List<HotelRefundVO> list = hotelRefundService.selectRefundOrderList(query);
        return getDataTable(list);
    }

    /**
     * 8.2 查询退款详情
     */
    @PreAuthorize("@ss.hasPermi('hotel:refund:detail')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        HotelRefundDetailVO detail = hotelRefundService.getRefundDetail(id);
        return success(detail);
    }

    /**
     * 8.3 同意退款（商家审核）
     */
    @Log(title = "酒店退款管理", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermi('hotel:refund:audit')")
    @PostMapping("/approve")
    public AjaxResult approve(@Validated @RequestBody HotelRefundAuditForm form)
    {
        int rows = hotelRefundService.approveRefund(form, getUsername());
        return rows > 0 ? success("操作成功") : error("操作失败");
    }

    /**
     * 8.4 拒绝退款（商家审核）
     */
    @Log(title = "酒店退款管理", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermi('hotel:refund:audit')")
    @PostMapping("/reject")
    public AjaxResult reject(@Validated @RequestBody HotelRefundAuditForm form)
    {
        int rows = hotelRefundService.rejectRefund(form, getUsername());
        return rows > 0 ? success("操作成功") : error("操作失败");
    }
}

