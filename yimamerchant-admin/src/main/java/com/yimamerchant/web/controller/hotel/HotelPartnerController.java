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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.yimamerchant.common.annotation.Log;
import com.yimamerchant.common.core.controller.BaseController;
import com.yimamerchant.common.core.domain.AjaxResult;
import com.yimamerchant.common.core.page.TableDataInfo;
import com.yimamerchant.common.enums.BusinessType;
import com.yimamerchant.common.utils.poi.ExcelUtil;
import com.yimamerchant.system.hotel.domain.HotelPartner;
import com.yimamerchant.system.hotel.dto.HotelPartnerStatusDTO;
import com.yimamerchant.system.hotel.service.IHotelManageService;

@RestController
@RequestMapping("/hotel/cooperate/partner")
public class HotelPartnerController extends BaseController
{
    @Autowired
    private IHotelManageService hotelManageService;

    @PreAuthorize("@ss.hasPermi('hotel:cooperate:partner:list')")
    @GetMapping("/list")
    public TableDataInfo list(HotelPartner query)
    {
        startPage();
        return getDataTable(hotelManageService.selectPartnerList(query));
    }

    @PreAuthorize("@ss.hasPermi('hotel:cooperate:partner:query')")
    @GetMapping("/{hotelId}")
    public AjaxResult getInfo(@PathVariable Long hotelId)
    {
        return success(hotelManageService.selectPartnerById(hotelId));
    }

    @PreAuthorize("@ss.hasPermi('hotel:cooperate:partner:edit')")
    @Log(title = "合作酒店修改", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HotelPartner entity)
    {
        return toAjax(hotelManageService.updatePartner(entity));
    }

    @PreAuthorize("@ss.hasPermi('hotel:cooperate:partner:status')")
    @Log(title = "合作酒店状态修改", businessType = BusinessType.UPDATE)
    @PutMapping("/status")
    public AjaxResult status(@RequestBody HotelPartnerStatusDTO dto)
    {
        return toAjax(hotelManageService.updatePartnerStatus(dto));
    }

    @PreAuthorize("@ss.hasPermi('hotel:cooperate:partner:query')")
    @GetMapping("/statistics")
    public AjaxResult statistics(HotelPartner query)
    {
        return success(hotelManageService.selectPartnerStatistics(query));
    }

    @PreAuthorize("@ss.hasPermi('hotel:cooperate:partner:export')")
    @Log(title = "合作酒店导出", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HotelPartner query)
    {
        List<HotelPartner> list = hotelManageService.selectPartnerList(query);
        new ExcelUtil<>(HotelPartner.class).exportExcel(response, list, "合作酒店");
    }

    @PreAuthorize("@ss.hasPermi('hotel:cooperate:partner:resetPwd')")
    @PutMapping("/resetPassword")
    public AjaxResult resetPassword(@RequestParam Long hotelId)
    {
        return toAjax(hotelManageService.resetPartnerPassword(hotelId));
    }

    @PreAuthorize("@ss.hasPermi('hotel:cooperate:partner:query')")
    @GetMapping("/account/list")
    public AjaxResult accountList(@RequestParam Long hotelId)
    {
        return success(hotelManageService.selectPartnerAccounts(hotelId));
    }
}
