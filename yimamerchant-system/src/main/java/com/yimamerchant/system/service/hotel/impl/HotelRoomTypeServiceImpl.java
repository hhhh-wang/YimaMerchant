package com.yimamerchant.system.service.hotel.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson2.JSON;
import com.yimamerchant.common.exception.ServiceException;
import com.yimamerchant.common.utils.SecurityUtils;
import com.yimamerchant.common.utils.StringUtils;
import com.yimamerchant.system.domain.hotel.HotelInfo;
import com.yimamerchant.system.domain.hotel.HotelRoomType;
import com.yimamerchant.system.domain.hotel.HotelRoomTypeFacilityRel;
import com.yimamerchant.system.domain.hotel.form.HotelRoomTypeForm;
import com.yimamerchant.system.domain.hotel.form.HotelRoomTypeStatusForm;
import com.yimamerchant.system.domain.hotel.query.HotelRoomTypeQuery;
import com.yimamerchant.system.domain.hotel.vo.HotelRoomTypeCompleteCheckVO;
import com.yimamerchant.system.domain.hotel.vo.HotelRoomTypeDetailVO;
import com.yimamerchant.system.domain.hotel.vo.HotelRoomTypeVO;
import com.yimamerchant.system.mapper.hotel.HotelInfoMapper;
import com.yimamerchant.system.mapper.hotel.HotelRoomTypeFacilityRelMapper;
import com.yimamerchant.system.mapper.hotel.HotelRoomTypeMapper;
import com.yimamerchant.system.service.hotel.IHotelRoomTypeService;

@Service
public class HotelRoomTypeServiceImpl implements IHotelRoomTypeService
{
    @Autowired
    private HotelInfoMapper hotelInfoMapper;

    @Autowired
    private HotelRoomTypeMapper hotelRoomTypeMapper;

    @Autowired
    private HotelRoomTypeFacilityRelMapper hotelRoomTypeFacilityRelMapper;

    private Long getCurrentMerchantId()
    {
        Long merchantId = SecurityUtils.getLoginUser().getDeptId();
        if (merchantId == null)
        {
            throw new ServiceException("当前登录用户未绑定商家信息");
        }
        return merchantId;
    }

    private HotelInfo getCurrentMerchantHotel()
    {
        Long merchantId = getCurrentMerchantId();
        HotelInfo hotelInfo = hotelInfoMapper.selectByMerchantId(merchantId);
        if (hotelInfo == null)
        {
            throw new ServiceException("当前商家尚未维护酒店信息，请先完善酒店资料");
        }
        return hotelInfo;
    }

    @Override
    public List<HotelRoomTypeVO> selectRoomTypeList(HotelRoomTypeQuery query)
    {
        Long merchantId = getCurrentMerchantId();
        if (query == null)
        {
            query = new HotelRoomTypeQuery();
        }
        query.merchantId = merchantId;
        List<HotelRoomType> list = hotelRoomTypeMapper.selectRoomTypeList(query);
        if (CollectionUtils.isEmpty(list))
        {
            return Collections.emptyList();
        }
        List<HotelRoomTypeVO> result = new ArrayList<>();
        for (HotelRoomType entity : list)
        {
            result.add(toListVO(entity));
        }
        return result;
    }

    @Override
    public HotelRoomTypeDetailVO getRoomTypeDetail(Long id)
    {
        if (id == null)
        {
            throw new ServiceException("房型ID不能为空");
        }
        Long merchantId = getCurrentMerchantId();
        HotelRoomType entity = hotelRoomTypeMapper.selectRoomTypeById(id);
        if (entity == null || !merchantId.equals(entity.merchantId) || "2".equals(entity.delFlag))
        {
            throw new ServiceException("房型不存在或无权操作");
        }

        HotelRoomTypeDetailVO vo = toDetailVO(entity);
        List<Long> facilityIds = hotelRoomTypeFacilityRelMapper.selectFacilityIdsByRoomTypeId(id);
        vo.facilityIds = facilityIds == null ? Collections.emptyList() : facilityIds;
        return vo;
    }

    @Override
    public int addRoomType(HotelRoomTypeForm form, String operator)
    {
        if (form == null)
        {
            throw new ServiceException("请求参数不能为空");
        }

        HotelInfo hotelInfo = getCurrentMerchantHotel();
        Long merchantId = hotelInfo.merchantId;

        HotelRoomType entity = new HotelRoomType();
        entity.hotelId = hotelInfo.id;
        entity.merchantId = merchantId;
        fillEntityFromForm(entity, form);
        entity.soldNum = 0;
        entity.delFlag = "0";
        entity.setCreateBy(operator);

        int rows = hotelRoomTypeMapper.insertRoomType(entity);
        if (rows <= 0)
        {
            return rows;
        }

        Long roomTypeId = entity.id;
        saveFacilities(hotelInfo.id, roomTypeId, form.facilityIds);
        return rows;
    }

    @Override
    public int updateRoomType(HotelRoomTypeForm form, String operator)
    {
        if (form == null || form.id == null)
        {
            throw new ServiceException("房型ID不能为空");
        }

        Long merchantId = getCurrentMerchantId();
        HotelRoomType exists = hotelRoomTypeMapper.selectRoomTypeById(form.id);
        if (exists == null || !"0".equals(exists.delFlag) || !merchantId.equals(exists.merchantId))
        {
            throw new ServiceException("房型不存在或无权操作");
        }

        HotelRoomType entity = new HotelRoomType();
        entity.id = form.id;
        entity.hotelId = exists.hotelId;
        entity.merchantId = merchantId;
        fillEntityFromForm(entity, form);
        entity.setUpdateBy(operator);

        int rows = hotelRoomTypeMapper.updateRoomType(entity);
        saveFacilities(exists.hotelId, exists.id, form.facilityIds);
        return rows;
    }

    @Override
    public int deleteRoomType(Long id, String operator)
    {
        if (id == null)
        {
            throw new ServiceException("房型ID不能为空");
        }
        Long merchantId = getCurrentMerchantId();
        HotelRoomType exists = hotelRoomTypeMapper.selectRoomTypeById(id);
        if (exists == null || !"0".equals(exists.delFlag) || !merchantId.equals(exists.merchantId))
        {
            throw new ServiceException("房型不存在或无权操作");
        }

        // 此处预留：如果存在订单、库存经营数据，应禁止删除
        HotelRoomType entity = new HotelRoomType();
        entity.id = id;
        entity.merchantId = merchantId;
        entity.setUpdateBy(operator);
        int rows = hotelRoomTypeMapper.logicalDeleteRoomType(entity);

        // 删除设施关联
        hotelRoomTypeFacilityRelMapper.deleteByRoomTypeId(id);
        return rows;
    }

    @Override
    public int changeConfigStatus(HotelRoomTypeStatusForm form, String operator)
    {
        if (form == null || form.id == null)
        {
            throw new ServiceException("房型ID不能为空");
        }
        Long merchantId = getCurrentMerchantId();
        HotelRoomType entity = new HotelRoomType();
        entity.id = form.id;
        entity.merchantId = merchantId;
        entity.configStatus = form.configStatus;
        entity.setUpdateBy(operator);
        return hotelRoomTypeMapper.updateConfigStatus(entity);
    }

    @Override
    public int changeSaleStatus(HotelRoomTypeStatusForm form, String operator)
    {
        if (form == null || form.id == null)
        {
            throw new ServiceException("房型ID不能为空");
        }
        Long merchantId = getCurrentMerchantId();
        HotelRoomType exists = hotelRoomTypeMapper.selectRoomTypeById(form.id);
        if (exists == null || !"0".equals(exists.delFlag) || !merchantId.equals(exists.merchantId))
        {
            throw new ServiceException("房型不存在或无权操作");
        }

        // 上架前校验完整性
        if ("1".equals(form.saleStatus))
        {
            HotelRoomTypeCompleteCheckVO check = doCompleteCheck(exists);
            if (!check.pass)
            {
                throw new ServiceException("房型基础信息不完整，无法上架：" + check.message);
            }
        }

        HotelRoomType entity = new HotelRoomType();
        entity.id = form.id;
        entity.merchantId = merchantId;
        entity.saleStatus = form.saleStatus;
        entity.setUpdateBy(operator);
        return hotelRoomTypeMapper.updateSaleStatus(entity);
    }

    @Override
    public HotelRoomTypeCompleteCheckVO checkComplete(Long id)
    {
        if (id == null)
        {
            throw new ServiceException("房型ID不能为空");
        }
        Long merchantId = getCurrentMerchantId();
        HotelRoomType exists = hotelRoomTypeMapper.selectRoomTypeById(id);
        if (exists == null || !"0".equals(exists.delFlag) || !merchantId.equals(exists.merchantId))
        {
            throw new ServiceException("房型不存在或无权操作");
        }
        return doCompleteCheck(exists);
    }

    private HotelRoomTypeVO toListVO(HotelRoomType entity)
    {
        HotelRoomTypeVO vo = new HotelRoomTypeVO();
        vo.id = entity.id;
        vo.hotelId = entity.hotelId;
        vo.merchantId = entity.merchantId;
        vo.roomTypeName = entity.roomTypeName;
        vo.roomTypeCode = entity.roomTypeCode;
        vo.roomImages = parseImages(entity.roomImages);
        vo.firstImage = CollectionUtils.isEmpty(vo.roomImages) ? null : vo.roomImages.get(0);
        vo.bedType = entity.bedType;
        vo.peopleLimit = entity.peopleLimit;
        vo.basePrice = entity.basePrice;
        vo.marketPrice = entity.marketPrice;
        vo.baseStock = entity.baseStock;
        vo.soldNum = entity.soldNum;
        vo.availableNum = calculateAvailableNum(entity.baseStock, entity.soldNum);
        vo.configStatus = entity.configStatus;
        vo.saleStatus = entity.saleStatus;
        vo.bookableFlag = entity.bookableFlag;
        vo.sortNum = entity.sortNum;
        vo.updateTime = entity.getUpdateTime();
        return vo;
    }

    private HotelRoomTypeDetailVO toDetailVO(HotelRoomType entity)
    {
        HotelRoomTypeDetailVO vo = new HotelRoomTypeDetailVO();
        vo.id = entity.id;
        vo.hotelId = entity.hotelId;
        vo.merchantId = entity.merchantId;
        vo.roomTypeName = entity.roomTypeName;
        vo.roomTypeCode = entity.roomTypeCode;
        vo.roomImages = parseImages(entity.roomImages);
        vo.bedType = entity.bedType;
        vo.peopleLimit = entity.peopleLimit;
        vo.area = entity.area;
        vo.floorDesc = entity.floorDesc;
        vo.windowType = entity.windowType;
        vo.breakfastCount = entity.breakfastCount;
        vo.extraBedFlag = entity.extraBedFlag;
        vo.description = entity.description;
        vo.basePrice = entity.basePrice;
        vo.marketPrice = entity.marketPrice;
        vo.baseStock = entity.baseStock;
        vo.soldNum = entity.soldNum;
        vo.availableNum = calculateAvailableNum(entity.baseStock, entity.soldNum);
        vo.configStatus = entity.configStatus;
        vo.saleStatus = entity.saleStatus;
        vo.bookableFlag = entity.bookableFlag;
        vo.sortNum = entity.sortNum;
        vo.remark = entity.remark;
        vo.createTime = entity.getCreateTime();
        vo.updateTime = entity.getUpdateTime();
        return vo;
    }

    private void fillEntityFromForm(HotelRoomType entity, HotelRoomTypeForm form)
    {
        entity.roomTypeName = form.roomTypeName;
        entity.roomTypeCode = form.roomTypeCode;
        entity.roomImages = toImagesJson(form.roomImages);
        entity.bedType = form.bedType;
        entity.peopleLimit = form.peopleLimit;
        entity.area = form.area;
        entity.floorDesc = form.floorDesc;
        entity.windowType = form.windowType;
        entity.breakfastCount = form.breakfastCount;
        entity.extraBedFlag = form.extraBedFlag;
        entity.description = form.description;
        entity.basePrice = form.basePrice;
        entity.marketPrice = form.marketPrice;
        entity.baseStock = form.baseStock;
        entity.configStatus = form.configStatus;
        entity.saleStatus = form.saleStatus;
        entity.bookableFlag = form.bookableFlag;
        entity.sortNum = form.sortNum;
        entity.remark = form.remark;
    }

    private void saveFacilities(Long hotelId, Long roomTypeId, List<Long> facilityIds)
    {
        if (roomTypeId == null)
        {
            return;
        }
        // 先删后插
        hotelRoomTypeFacilityRelMapper.deleteByRoomTypeId(roomTypeId);
        if (CollectionUtils.isEmpty(facilityIds))
        {
            return;
        }
        List<HotelRoomTypeFacilityRel> list = new ArrayList<>();
        for (Long facilityId : facilityIds)
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
        if (!list.isEmpty())
        {
            hotelRoomTypeFacilityRelMapper.batchInsert(list);
        }
    }

    private String toImagesJson(List<String> images)
    {
        if (CollectionUtils.isEmpty(images))
        {
            return "[]";
        }
        return JSON.toJSONString(images);
    }

    private List<String> parseImages(String json)
    {
        if (StringUtils.isEmpty(json))
        {
            return Collections.emptyList();
        }
        try
        {
            return JSON.parseArray(json, String.class);
        }
        catch (Exception e)
        {
            return Collections.emptyList();
        }
    }

    private Integer calculateAvailableNum(Integer baseStock, Integer soldNum)
    {
        int stock = baseStock == null ? 0 : baseStock;
        int sold = soldNum == null ? 0 : soldNum;
        int available = stock - sold;
        return Math.max(available, 0);
    }

    private HotelRoomTypeCompleteCheckVO doCompleteCheck(HotelRoomType entity)
    {
        HotelRoomTypeCompleteCheckVO vo = new HotelRoomTypeCompleteCheckVO();
        List<String> missing = new ArrayList<>();

        if (StringUtils.isEmpty(entity.roomTypeName))
        {
            missing.add("roomTypeName");
        }
        if (StringUtils.isEmpty(entity.roomTypeCode))
        {
            missing.add("roomTypeCode");
        }
        if (StringUtils.isEmpty(entity.roomImages))
        {
            missing.add("roomImages");
        }
        if (StringUtils.isEmpty(entity.bedType))
        {
            missing.add("bedType");
        }
        if (entity.peopleLimit == null || entity.peopleLimit <= 0)
        {
            missing.add("peopleLimit");
        }
        if (entity.basePrice == null || entity.basePrice.compareTo(java.math.BigDecimal.ZERO) <= 0)
        {
            missing.add("basePrice");
        }
        if (entity.baseStock == null || entity.baseStock < 0)
        {
            missing.add("baseStock");
        }
        if (!"1".equals(entity.configStatus))
        {
            missing.add("configStatus");
        }
        if (!"Y".equalsIgnoreCase(entity.bookableFlag))
        {
            missing.add("bookableFlag");
        }

        vo.missingFields = missing;
        vo.pass = missing.isEmpty();
        vo.message = vo.pass ? "校验通过" : "部分必填字段缺失";
        return vo;
    }
}

