package com.yimamerchant.system.service.hotel;

import java.util.List;

import com.yimamerchant.system.domain.hotel.form.HotelRefundAuditForm;
import com.yimamerchant.system.domain.hotel.query.HotelRefundQuery;
import com.yimamerchant.system.domain.hotel.vo.HotelRefundDetailVO;
import com.yimamerchant.system.domain.hotel.vo.HotelRefundVO;

/**
 * 酒店退款管理 服务接口
 */
public interface IHotelRefundService
{
    /**
     * 退款分页列表
     */
    List<HotelRefundVO> selectRefundOrderList(HotelRefundQuery query);

    /**
     * 退款详情
     */
    HotelRefundDetailVO getRefundDetail(Long id);

    /**
     * 同意退款（商家审核）
     */
    int approveRefund(HotelRefundAuditForm form, String operator);

    /**
     * 拒绝退款（商家审核）
     */
    int rejectRefund(HotelRefundAuditForm form, String operator);
}

