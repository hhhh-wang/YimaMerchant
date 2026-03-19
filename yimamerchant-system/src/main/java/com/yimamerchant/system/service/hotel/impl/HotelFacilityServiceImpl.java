package com.yimamerchant.system.service.hotel.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.yimamerchant.common.exception.ServiceException;
import com.yimamerchant.common.utils.SecurityUtils;
import com.yimamerchant.system.domain.hotel.HotelRoomType;
import com.yimamerchant.system.domain.hotel.HotelRoomTypeFacilityRel;
import com.yimamerchant.system.domain.hotel.HotelFacility;
import com.yimamerchant.system.domain.hotel.form.HotelRoomTypeFacilityForm;
import com.yimamerchant.system.domain.hotel.query.HotelFacilityQuery;
import com.yimamerchant.system.mapper.hotel.HotelFacilityMapper;
import com.yimamerchant.system.mapper.hotel.HotelRoomTypeFacilityRelMapper;
import com.yimamerchant.system.mapper.hotel.HotelRoomTypeMapper;
import com.yimamerchant.system.service.hotel.IHotelFacilityService;

@Service
public class HotelFacilityServiceImpl implements IHotelFacilityService
{
    @Autowired
    private HotelFacilityMapper hotelFacilityMapper;

    @Autowired
    private HotelRoomTypeMapper hotelRoomTypeMapper;

    @Autowired
    private HotelRoomTypeFacilityRelMapper hotelRoomTypeFacilityRelMapper;

    @Override
    public List<HotelFacility> selectFacilityList(HotelFacilityQuery query)
    {
        if (query == null)
        {
            query = new HotelFacilityQuery();
        }
        // 默认只查询启用设施
        if (query.status == null || "".equals(query.status))
        {
            query.status = "1";
        }
        List<HotelFacility> list = hotelFacilityMapper.selectFacilityList(query);
        if (CollectionUtils.isEmpty(list))
        {
            return Collections.emptyList();
        }
        return list;
    }

    @Override
    public List<Long> selectRoomTypeFacilityIds(Long roomTypeId)
    {
        if (roomTypeId == null)
        {
            throw new ServiceException("房型ID不能为空");
        }
        return hotelRoomTypeFacilityRelMapper.selectFacilityIdsByRoomTypeId(roomTypeId);
    }

    @Override
    public int saveRoomTypeFacilities(HotelRoomTypeFacilityForm form)
    {
        if (form == null || form.roomTypeId == null)
        {
            throw new ServiceException("房型ID不能为空");
        }
        Long merchantId = getCurrentMerchantId();
        HotelRoomType roomType = hotelRoomTypeMapper.selectRoomTypeById(form.roomTypeId);
        if (roomType == null || !"0".equals(roomType.delFlag) || !merchantId.equals(roomType.merchantId))
        {
            throw new ServiceException("房型不存在或无权操作");
        }

        Long hotelId = roomType.hotelId;
        Long roomTypeId = roomType.id;

        // 先删除已有绑定
        hotelRoomTypeFacilityRelMapper.deleteByRoomTypeId(roomTypeId);

        if (CollectionUtils.isEmpty(form.facilityIds))
        {
            return 1;
        }

        List<HotelRoomTypeFacilityRel> list = new ArrayList<>();
        for (Long facilityId : form.facilityIds)
        {
            if (facilityId == null)
            {
                continue;
            }
            HotelRoomTypeFacilityRel rel = new HotelRoomTypeFacilityRel();
            rel.hotelId = hotelId;
            rel.roomTypeId = roomTypeId;
            rel.facilityId = facilityId;
            list.add(rel);
        }
        if (list.isEmpty())
        {
            return 1;
        }
        return hotelRoomTypeFacilityRelMapper.batchInsert(list);
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
}

