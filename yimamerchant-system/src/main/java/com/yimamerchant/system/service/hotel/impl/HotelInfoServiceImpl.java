package com.yimamerchant.system.service.hotel.impl;

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
import com.yimamerchant.system.domain.hotel.form.HotelInfoForm;
import com.yimamerchant.system.domain.hotel.form.HotelInfoStatusForm;
import com.yimamerchant.system.domain.hotel.vo.HotelInfoVO;
import com.yimamerchant.system.mapper.hotel.HotelInfoMapper;
import com.yimamerchant.system.service.hotel.IHotelInfoService;

@Service
public class HotelInfoServiceImpl implements IHotelInfoService
{
    @Autowired
    private HotelInfoMapper hotelInfoMapper;

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
    public HotelInfoVO getCurrentMerchantHotelInfo()
    {
        Long merchantId = getCurrentMerchantId();
        HotelInfo info = hotelInfoMapper.selectByMerchantId(merchantId);
        if (info == null)
        {
            return null;
        }
        return toVO(info);
    }

    @Override
    public int saveCurrentMerchantHotelInfo(HotelInfoForm form, String operator)
    {
        Long merchantId = getCurrentMerchantId();
        HotelInfo exists = hotelInfoMapper.selectByMerchantId(merchantId);

        HotelInfo entity = new HotelInfo();
        entity.merchantId = merchantId;
        entity.hotelName = form.hotelName;
        entity.hotelCover = form.hotelCover;
        entity.hotelImages = toImagesJson(form.hotelImages);
        entity.phone = form.phone;
        entity.provinceCode = form.provinceCode;
        entity.cityCode = form.cityCode;
        entity.districtCode = form.districtCode;
        entity.address = form.address;
        entity.longitude = form.longitude;
        entity.latitude = form.latitude;
        entity.checkInTime = form.checkInTime;
        entity.checkOutTime = form.checkOutTime;
        entity.intro = form.intro;
        entity.bookingNotice = form.bookingNotice;
        entity.cancelRule = form.cancelRule;
        entity.invoiceDesc = form.invoiceDesc;
        entity.parkingDesc = form.parkingDesc;
        entity.status = form.status;
        entity.remark = form.remark;

        if (exists == null)
        {
            entity.setCreateBy(operator);
            return hotelInfoMapper.insertHotelInfo(entity);
        }

        entity.id = exists.id;
        entity.setUpdateBy(operator);
        return hotelInfoMapper.updateHotelInfo(entity);
    }

    @Override
    public int changeStatus(HotelInfoStatusForm form, String operator)
    {
        Long merchantId = getCurrentMerchantId();
        HotelInfo entity = new HotelInfo();
        entity.id = form.id;
        entity.merchantId = merchantId;
        entity.status = form.status;
        entity.setUpdateBy(operator);
        return hotelInfoMapper.updateStatus(entity);
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

    private HotelInfoVO toVO(HotelInfo info)
    {
        HotelInfoVO vo = new HotelInfoVO();
        vo.id = info.id;
        vo.merchantId = info.merchantId;
        vo.hotelName = info.hotelName;
        vo.hotelCover = info.hotelCover;
        vo.hotelImages = parseImages(info.hotelImages);
        vo.phone = info.phone;
        vo.provinceCode = info.provinceCode;
        vo.cityCode = info.cityCode;
        vo.districtCode = info.districtCode;
        vo.address = info.address;
        vo.longitude = info.longitude;
        vo.latitude = info.latitude;
        vo.checkInTime = info.checkInTime;
        vo.checkOutTime = info.checkOutTime;
        vo.intro = info.intro;
        vo.bookingNotice = info.bookingNotice;
        vo.cancelRule = info.cancelRule;
        vo.invoiceDesc = info.invoiceDesc;
        vo.parkingDesc = info.parkingDesc;
        vo.status = info.status;
        vo.remark = info.remark;
        vo.createTime = info.getCreateTime();
        vo.updateTime = info.getUpdateTime();
        return vo;
    }
}

