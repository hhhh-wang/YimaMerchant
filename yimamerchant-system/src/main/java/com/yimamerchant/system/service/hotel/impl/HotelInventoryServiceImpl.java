package com.yimamerchant.system.service.hotel.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.yimamerchant.common.exception.ServiceException;
import com.yimamerchant.common.utils.SecurityUtils;
import com.yimamerchant.system.domain.hotel.HotelRoomType;
import com.yimamerchant.system.domain.hotel.form.HotelInventoryBatchForm;
import com.yimamerchant.system.domain.hotel.form.HotelInventoryPriceForm;
import com.yimamerchant.system.domain.hotel.form.HotelInventoryStockForm;
import com.yimamerchant.system.domain.hotel.form.HotelInventoryStatusForm;
import com.yimamerchant.system.domain.hotel.form.HotelRoomTypeStatusForm;
import com.yimamerchant.system.domain.hotel.query.HotelInventoryQuery;
import com.yimamerchant.system.domain.hotel.vo.HotelInventoryVO;
import com.yimamerchant.system.mapper.hotel.HotelInventoryMapper;
import com.yimamerchant.system.mapper.hotel.HotelRoomTypeMapper;
import com.yimamerchant.system.service.hotel.IHotelInventoryService;
import com.yimamerchant.system.service.hotel.IHotelRoomTypeService;

@Service
public class HotelInventoryServiceImpl implements IHotelInventoryService
{
    @Autowired
    private HotelInventoryMapper hotelInventoryMapper;

    @Autowired
    private HotelRoomTypeMapper hotelRoomTypeMapper;

    @Autowired
    private IHotelRoomTypeService hotelRoomTypeService;

    @Override
    public List<HotelInventoryVO> selectInventoryList(HotelInventoryQuery query)
    {
        Long merchantId = getCurrentMerchantId();
        if (query == null)
        {
            query = new HotelInventoryQuery();
        }
        query.setMerchantId(merchantId);
        List<HotelInventoryVO> list = hotelInventoryMapper.selectInventoryList(query);
        return CollectionUtils.isEmpty(list) ? Collections.emptyList() : list;
    }

    @Override
    public int updateBasePrice(HotelInventoryPriceForm form, String operator)
    {
        if (form == null)
        {
            throw new ServiceException("请求参数不能为空");
        }
        if (form.roomTypeId == null)
        {
            throw new ServiceException("房型ID不能为空");
        }

        Long merchantId = getCurrentMerchantId();
        validateMoneyScale(form.basePrice, "basePrice");
        if (form.marketPrice != null)
        {
            validateMoneyScale(form.marketPrice, "marketPrice");
        }

        HotelRoomType exists = hotelRoomTypeMapper.selectRoomTypeById(form.roomTypeId);
        if (exists == null || !"0".equals(exists.delFlag) || !merchantId.equals(exists.merchantId))
        {
            throw new ServiceException("房型不存在或无权操作");
        }

        HotelRoomType entity = new HotelRoomType();
        entity.id = form.roomTypeId;
        entity.merchantId = merchantId;
        entity.basePrice = form.basePrice;
        entity.marketPrice = form.marketPrice;
        entity.setUpdateBy(operator);
        return hotelInventoryMapper.updateBasePrice(entity);
    }

    @Override
    public int updateBaseStock(HotelInventoryStockForm form, String operator)
    {
        if (form == null)
        {
            throw new ServiceException("请求参数不能为空");
        }
        if (form.roomTypeId == null)
        {
            throw new ServiceException("房型ID不能为空");
        }

        if (form.baseStock == null)
        {
            throw new ServiceException("默认库存不能为空");
        }

        Long merchantId = getCurrentMerchantId();
        HotelRoomType exists = hotelRoomTypeMapper.selectRoomTypeById(form.roomTypeId);
        if (exists == null || !"0".equals(exists.delFlag) || !merchantId.equals(exists.merchantId))
        {
            throw new ServiceException("房型不存在或无权操作");
        }

        HotelRoomType entity = new HotelRoomType();
        entity.id = form.roomTypeId;
        entity.merchantId = merchantId;
        entity.baseStock = form.baseStock;
        entity.setUpdateBy(operator);
        return hotelInventoryMapper.updateBaseStock(entity);
    }

    @Override
    public int batchUpdateInventory(HotelInventoryBatchForm form, String operator)
    {
        if (form == null)
        {
            throw new ServiceException("请求参数不能为空");
        }
        if (CollectionUtils.isEmpty(form.roomTypeIds))
        {
            throw new ServiceException("房型ID列表不能为空");
        }
        if (form.basePrice == null && form.baseStock == null)
        {
            throw new ServiceException("参数至少包含价格或库存");
        }
        if (form.marketPrice != null && form.basePrice == null)
        {
            throw new ServiceException("默认价格不能为空");
        }

        validateMoneyScale(form.basePrice, "basePrice");
        if (form.marketPrice != null)
        {
            validateMoneyScale(form.marketPrice, "marketPrice");
        }

        Long merchantId = getCurrentMerchantId();
        form.merchantId = merchantId;
        form.updateBy = operator;

        // 去重并过滤 null，避免 update affected rows 不可预期
        Set<Long> distinct = new LinkedHashSet<>();
        for (Long id : form.roomTypeIds)
        {
            if (id != null)
            {
                distinct.add(id);
            }
        }
        List<Long> ids = new ArrayList<>(distinct);
        if (ids.isEmpty())
        {
            throw new ServiceException("房型ID列表不能为空");
        }
        form.roomTypeIds = ids;

        int rows = hotelInventoryMapper.batchUpdateInventory(form);
        if (rows != ids.size())
        {
            throw new ServiceException("房型不存在或无权操作");
        }
        return rows;
    }

    @Override
    public int changeBookableFlag(HotelInventoryStatusForm form, String operator)
    {
        if (form == null || form.roomTypeId == null)
        {
            throw new ServiceException("房型ID不能为空");
        }
        if (form.bookableFlag == null || form.bookableFlag.isEmpty())
        {
            throw new ServiceException("是否可预订不能为空");
        }

        Long merchantId = getCurrentMerchantId();
        HotelRoomType exists = hotelRoomTypeMapper.selectRoomTypeById(form.roomTypeId);
        if (exists == null || !"0".equals(exists.delFlag) || !merchantId.equals(exists.merchantId))
        {
            throw new ServiceException("房型不存在或无权操作");
        }

        HotelRoomType entity = new HotelRoomType();
        entity.id = form.roomTypeId;
        entity.merchantId = merchantId;
        entity.bookableFlag = form.bookableFlag;
        entity.setUpdateBy(operator);
        return hotelInventoryMapper.updateBookableFlag(entity);
    }

    @Override
    public int changeSaleStatus(HotelInventoryStatusForm form, String operator)
    {
        if (form == null || form.roomTypeId == null)
        {
            throw new ServiceException("房型ID不能为空");
        }
        if (form.saleStatus == null || form.saleStatus.isEmpty())
        {
            throw new ServiceException("上架状态不能为空");
        }

        HotelRoomTypeStatusForm statusForm = new HotelRoomTypeStatusForm();
        statusForm.id = form.roomTypeId;
        statusForm.saleStatus = form.saleStatus;
        // 复用房型上/下架完整性校验逻辑（文档推荐：库存页内部调用房型状态接口）
        return hotelRoomTypeService.changeSaleStatus(statusForm, operator);
    }

    private Long getCurrentMerchantId()
    {
        Long merchantId = SecurityUtils.getLoginUser().getDeptId();
        if (merchantId == null)
        {
            throw new ServiceException("当前登录用户未绑定商家信息");
        }
        return merchantId;
    }

    private void validateMoneyScale(BigDecimal value, String fieldName)
    {
        if (value == null)
        {
            return;
        }
        // stripTrailingZeros 处理如 299.00 的 scale==0 情况
        if (value.stripTrailingZeros().scale() > 2)
        {
            throw new ServiceException("金额非法");
        }
    }
}

