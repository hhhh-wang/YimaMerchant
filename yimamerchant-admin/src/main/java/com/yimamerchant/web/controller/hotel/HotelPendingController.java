package com.yimamerchant.web.controller.hotel;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
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
import com.yimamerchant.common.utils.poi.ExcelUtil;
import com.yimamerchant.system.hotel.domain.HotelPendingApply;
import com.yimamerchant.system.hotel.dto.HotelPendingApproveDTO;
import com.yimamerchant.system.hotel.dto.HotelPendingRejectDTO;
import com.yimamerchant.system.hotel.service.IHotelManageService;

@RestController
@RequestMapping("/hotel/cooperate/pending")
public class HotelPendingController extends BaseController
{
    @Autowired
    private IHotelManageService hotelManageService;

    @PreAuthorize("@ss.hasPermi('hotel:cooperate:pending:list')")
    @GetMapping("/list")
    public TableDataInfo list(HotelPendingApply query)
    {
        startPage();
        return getDataTable(hotelManageService.selectPendingList(query));
    }

    @PreAuthorize("@ss.hasPermi('hotel:cooperate:pending:query')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable Long id)
    {
        return success(hotelManageService.selectPendingById(id));
    }

    @PreAuthorize("@ss.hasPermi('hotel:cooperate:pending:add')")
    @Log(title = "待签约酒店", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HotelPendingApply entity)
    {
        return toAjax(hotelManageService.insertPending(entity));
    }

    @PreAuthorize("@ss.hasPermi('hotel:cooperate:pending:audit')")
    @Log(title = "待签约酒店审核通过", businessType = BusinessType.UPDATE)
    @PutMapping("/approve")
    public AjaxResult approve(@RequestBody HotelPendingApproveDTO dto)
    {
        return toAjax(hotelManageService.approvePending(dto));
    }

    @PreAuthorize("@ss.hasPermi('hotel:cooperate:pending:audit')")
    @Log(title = "待签约酒店审核驳回", businessType = BusinessType.UPDATE)
    @PutMapping("/reject")
    public AjaxResult reject(@RequestBody HotelPendingRejectDTO dto)
    {
        return toAjax(hotelManageService.rejectPending(dto));
    }

    @PreAuthorize("@ss.hasPermi('hotel:cooperate:pending:remove')")
    @Log(title = "待签约酒店删除", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(hotelManageService.deletePendingByIds(ids));
    }

    @PreAuthorize("@ss.hasPermi('hotel:cooperate:pending:export')")
    @Log(title = "待签约酒店导出", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HotelPendingApply query)
    {
        List<HotelPendingApply> list = hotelManageService.selectPendingList(query);
        new ExcelUtil<>(HotelPendingApply.class).exportExcel(response, list, "待签约酒店");
    }
}
