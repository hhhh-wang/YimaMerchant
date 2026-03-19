package com.yimamerchant.system.service.hotel;

import java.util.List;

import com.yimamerchant.system.domain.hotel.form.HotelRoomTypeForm;
import com.yimamerchant.system.domain.hotel.form.HotelRoomTypeStatusForm;
import com.yimamerchant.system.domain.hotel.query.HotelRoomTypeQuery;
import com.yimamerchant.system.domain.hotel.vo.HotelRoomTypeCompleteCheckVO;
import com.yimamerchant.system.domain.hotel.vo.HotelRoomTypeDetailVO;
import com.yimamerchant.system.domain.hotel.vo.HotelRoomTypeVO;

/**
 * 房型管理 服务接口
 */
public interface IHotelRoomTypeService
{
    /**
     * 分页查询当前商家房型列表
     */
    List<HotelRoomTypeVO> selectRoomTypeList(HotelRoomTypeQuery query);

    /**
     * 查询房型详情（包含设施绑定）
     */
    HotelRoomTypeDetailVO getRoomTypeDetail(Long id);

    /**
     * 新增房型
     */
    int addRoomType(HotelRoomTypeForm form, String operator);

    /**
     * 修改房型
     */
    int updateRoomType(HotelRoomTypeForm form, String operator);

    /**
     * 删除房型（建议逻辑删除）
     */
    int deleteRoomType(Long id, String operator);

    /**
     * 修改房型配置状态
     */
    int changeConfigStatus(HotelRoomTypeStatusForm form, String operator);

    /**
     * 修改房型上架状态
     */
    int changeSaleStatus(HotelRoomTypeStatusForm form, String operator);

    /**
     * 房型完整性校验
     */
    HotelRoomTypeCompleteCheckVO checkComplete(Long id);
}

