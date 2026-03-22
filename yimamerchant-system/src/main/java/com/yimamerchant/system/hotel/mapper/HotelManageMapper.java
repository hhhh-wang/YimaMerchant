package com.yimamerchant.system.hotel.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.yimamerchant.system.hotel.domain.HotelAccount;
import com.yimamerchant.system.hotel.domain.HotelBdBind;
import com.yimamerchant.system.hotel.domain.HotelBdBindHistory;
import com.yimamerchant.system.hotel.domain.HotelBill;
import com.yimamerchant.system.hotel.domain.HotelBillCheckLog;
import com.yimamerchant.system.hotel.domain.HotelBillOrder;
import com.yimamerchant.system.hotel.domain.HotelBillPayment;
import com.yimamerchant.system.hotel.domain.HotelBusinessConfig;
import com.yimamerchant.system.hotel.domain.HotelBusinessConfigLog;
import com.yimamerchant.system.hotel.domain.HotelContract;
import com.yimamerchant.system.hotel.domain.HotelImage;
import com.yimamerchant.system.hotel.domain.HotelOptionVO;
import com.yimamerchant.system.hotel.domain.HotelOrder;
import com.yimamerchant.system.hotel.domain.HotelOrderGuest;
import com.yimamerchant.system.hotel.domain.HotelOrderLog;
import com.yimamerchant.system.hotel.domain.HotelPartner;
import com.yimamerchant.system.hotel.domain.HotelPartnerStatistics;
import com.yimamerchant.system.hotel.domain.HotelPendingApply;
import com.yimamerchant.system.hotel.domain.HotelPendingAuditLog;
import com.yimamerchant.system.hotel.domain.HotelPriceCalendarItem;
import com.yimamerchant.system.hotel.domain.HotelRoomCalendarLog;
import com.yimamerchant.system.hotel.domain.HotelRoomType;

public interface HotelManageMapper
{
    List<HotelPendingApply> selectPendingList(HotelPendingApply query);

    HotelPendingApply selectPendingById(Long id);

    int insertPending(HotelPendingApply entity);

    int updatePending(HotelPendingApply entity);

    int deletePendingByIds(@Param("ids") Long[] ids);

    int insertPendingAuditLog(HotelPendingAuditLog entity);

    List<HotelPendingAuditLog> selectPendingAuditLogs(Long applyId);

    List<HotelPartner> selectPartnerList(HotelPartner query);

    HotelPartner selectPartnerById(Long hotelId);

    int insertPartner(HotelPartner entity);

    int updatePartner(HotelPartner entity);

    int updatePartnerBdInfo(@Param("hotelId") Long hotelId, @Param("bdUserId") Long bdUserId);

    HotelPartnerStatistics selectPartnerStatistics(HotelPartner query);

    List<HotelContract> selectPartnerContracts(Long hotelId);

    HotelContract selectActiveContractByHotelId(Long hotelId);

    int insertContract(HotelContract entity);

    int updateContractByHotelId(HotelContract entity);

    List<HotelAccount> selectPartnerAccounts(Long hotelId);

    int insertAccount(HotelAccount entity);

    int updateAccountByHotelId(HotelAccount entity);

    List<HotelBdBind> selectBindList(HotelBdBind query);

    List<HotelBdBind> selectBindByIds(@Param("ids") Long[] ids);

    HotelBdBind selectActiveBindByHotelId(Long hotelId);

    int insertBind(HotelBdBind entity);

    int updateBind(HotelBdBind entity);

    int updateBindByHotelIds(@Param("hotelIds") List<Long> hotelIds, @Param("status") String status, @Param("remark") String remark);

    List<HotelBdBindHistory> selectBindHistoryList(HotelBdBindHistory query);

    int insertBindHistory(HotelBdBindHistory entity);

    List<HotelOptionVO> selectBdOptions(@Param("keyword") String keyword);

    List<HotelOptionVO> selectHotelOptions(@Param("keyword") String keyword);

    List<HotelPartner> selectHotelInfoList(HotelPartner query);

    HotelPartner selectHotelInfoById(Long hotelId);

    int insertHotelProfile(HotelPartner entity);

    int updateHotelProfile(HotelPartner entity);

    List<HotelImage> selectHotelImages(Long hotelId);

    int deleteHotelImages(Long hotelId);

    int insertHotelImage(HotelImage entity);

    List<HotelPendingAuditLog> selectHotelChangeLogs(Long hotelId);

    List<HotelRoomType> selectRoomTypeList(HotelRoomType query);

    HotelRoomType selectRoomTypeById(Long id);

    int insertRoomType(HotelRoomType entity);

    int updateRoomType(HotelRoomType entity);

    int deleteRoomTypeByIds(@Param("ids") Long[] ids);

    List<HotelImage> selectRoomTypeImages(Long roomTypeId);

    int deleteRoomTypeImages(Long roomTypeId);

    int insertRoomTypeImage(HotelImage entity);

    List<HotelPriceCalendarItem> selectPriceCalendar(@Param("hotelId") Long hotelId, @Param("roomTypeIds") List<Long> roomTypeIds,
            @Param("beginDate") String beginDate, @Param("endDate") String endDate);

    HotelPriceCalendarItem selectPriceByRoomTypeAndDate(@Param("roomTypeId") Long roomTypeId, @Param("bizDate") String bizDate);

    int insertPriceCalendar(HotelPriceCalendarItem entity);

    int updatePriceCalendar(HotelPriceCalendarItem entity);

    int insertPriceLog(HotelRoomCalendarLog entity);

    List<HotelRoomCalendarLog> selectPriceLogList(HotelRoomCalendarLog query);

    List<HotelOrder> selectOrderList(HotelOrder query);

    HotelOrder selectOrderByNo(String orderNo);

    int updateOrder(HotelOrder entity);

    List<HotelOrderGuest> selectOrderGuests(String orderNo);

    List<HotelOrderLog> selectOrderLogs(String orderNo);

    int insertOrderLog(HotelOrderLog entity);

    List<HotelBill> selectBillList(HotelBill query);

    HotelBill selectBillByNo(String billNo);

    int insertBill(HotelBill entity);

    int updateBill(HotelBill entity);

    List<HotelBillOrder> selectBillOrders(@Param("billNo") String billNo, @Param("orderNo") String orderNo);

    int deleteBillOrdersByBillNo(String billNo);

    int insertBillOrder(HotelBillOrder entity);

    List<HotelBillCheckLog> selectBillCheckLogs(String billNo);

    int insertBillCheckLog(HotelBillCheckLog entity);

    List<HotelBillPayment> selectBillPayments(String billNo);

    int insertBillPayment(HotelBillPayment entity);

    List<HotelBusinessConfig> selectBusinessConfigs();

    HotelBusinessConfig selectBusinessConfigByKey(String configKey);

    int insertBusinessConfig(HotelBusinessConfig entity);

    int updateBusinessConfig(HotelBusinessConfig entity);

    List<HotelBusinessConfigLog> selectBusinessConfigLogs();

    int insertBusinessConfigLog(HotelBusinessConfigLog entity);
}
