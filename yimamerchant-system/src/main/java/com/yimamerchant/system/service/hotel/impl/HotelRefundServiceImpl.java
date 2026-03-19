package com.yimamerchant.system.service.hotel.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.yimamerchant.common.exception.ServiceException;
import com.yimamerchant.common.utils.DictUtils;
import com.yimamerchant.common.utils.SecurityUtils;
import com.yimamerchant.common.utils.StringUtils;
import com.yimamerchant.system.domain.hotel.HotelRefundOrder;
import com.yimamerchant.system.domain.hotel.form.HotelRefundAuditForm;
import com.yimamerchant.system.domain.hotel.query.HotelRefundQuery;
import com.yimamerchant.system.domain.hotel.vo.HotelRefundDetailVO;
import com.yimamerchant.system.domain.hotel.vo.HotelRefundVO;
import com.yimamerchant.system.mapper.hotel.HotelRefundOrderMapper;
import com.yimamerchant.system.service.hotel.IHotelRefundService;

/**
 * 酒店退款管理 服务实现
 */
@Service
public class HotelRefundServiceImpl implements IHotelRefundService
{
    @Autowired
    private HotelRefundOrderMapper hotelRefundOrderMapper;

    private Long getCurrentMerchantId()
    {
        Long merchantId = SecurityUtils.getLoginUser().getDeptId();
        if (merchantId == null)
        {
            throw new ServiceException("当前登录用户未绑定商家信息");
        }
        return merchantId;
    }

    @Override
    public List<HotelRefundVO> selectRefundOrderList(HotelRefundQuery query)
    {
        Long merchantId = getCurrentMerchantId();
        if (query == null)
        {
            query = new HotelRefundQuery();
        }
        query.merchantId = merchantId;

        List<HotelRefundVO> list = hotelRefundOrderMapper.selectRefundOrderList(query);
        if (CollectionUtils.isEmpty(list))
        {
            return Collections.emptyList();
        }

        for (HotelRefundVO vo : list)
        {
            vo.refundStatusLabel = DictUtils.getDictLabel("hotel_refund_status", vo.refundStatus);
        }
        return list;
    }

    @Override
    public HotelRefundDetailVO getRefundDetail(Long id)
    {
        if (id == null)
        {
            throw new ServiceException("退款单ID不能为空");
        }
        Long merchantId = getCurrentMerchantId();

        HotelRefundDetailVO detail = hotelRefundOrderMapper.selectRefundDetailById(id, merchantId);
        if (detail == null)
        {
            throw new ServiceException("退款单不存在或无权操作");
        }
        // 退款详情页面目前文档未要求 label，这里不强制补充
        return detail;
    }

    @Override
    public int approveRefund(HotelRefundAuditForm form, String operator)
    {
        if (form == null || form.id == null)
        {
            throw new ServiceException("退款单ID不能为空");
        }
        Long merchantId = getCurrentMerchantId();

        HotelRefundOrder exists = hotelRefundOrderMapper.selectRefundOrderByIdForAudit(form.id, merchantId);
        if (exists == null)
        {
            throw new ServiceException("退款单不存在或无权操作");
        }
        if (!"1".equals(exists.refundStatus))
        {
            throw new ServiceException("退款状态非法");
        }

        HotelRefundOrder update = new HotelRefundOrder();
        update.id = form.id;
        update.merchantId = merchantId;
        update.auditRemark = form.auditRemark;
        update.auditBy = operator;
        return hotelRefundOrderMapper.approveRefund(update);
    }

    @Override
    public int rejectRefund(HotelRefundAuditForm form, String operator)
    {
        if (form == null || form.id == null)
        {
            throw new ServiceException("退款单ID不能为空");
        }
        if (StringUtils.isEmpty(form.auditRemark))
        {
            throw new ServiceException("拒绝退款未填备注");
        }

        Long merchantId = getCurrentMerchantId();
        HotelRefundOrder exists = hotelRefundOrderMapper.selectRefundOrderByIdForAudit(form.id, merchantId);
        if (exists == null)
        {
            throw new ServiceException("退款单不存在或无权操作");
        }
        if (!"1".equals(exists.refundStatus))
        {
            throw new ServiceException("退款状态非法");
        }

        HotelRefundOrder update = new HotelRefundOrder();
        update.id = form.id;
        update.merchantId = merchantId;
        update.auditRemark = form.auditRemark;
        update.auditBy = operator;
        return hotelRefundOrderMapper.rejectRefund(update);
    }
}

