package com.yimamerchant.system.mapper.hotel;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yimamerchant.system.domain.hotel.HotelRefundOrder;
import com.yimamerchant.system.domain.hotel.query.HotelRefundQuery;
import com.yimamerchant.system.domain.hotel.vo.HotelRefundDetailVO;
import com.yimamerchant.system.domain.hotel.vo.HotelRefundVO;

/**
 * 酒店退款单 数据层
 */
public interface HotelRefundOrderMapper
{
    /**
     * 退款分页列表
     */
    List<HotelRefundVO> selectRefundOrderList(HotelRefundQuery query);

    /**
     * 退款详情（包含房型与取消规则信息）
     */
    HotelRefundDetailVO selectRefundDetailById(@Param("id") Long id, @Param("merchantId") Long merchantId);

    /**
     * 审核处理用：按商家隔离查询退款单
     */
    HotelRefundOrder selectRefundOrderByIdForAudit(@Param("id") Long id, @Param("merchantId") Long merchantId);

    /**
     * 同意退款
     */
    int approveRefund(HotelRefundOrder order);

    /**
     * 拒绝退款
     */
    int rejectRefund(HotelRefundOrder order);
}

