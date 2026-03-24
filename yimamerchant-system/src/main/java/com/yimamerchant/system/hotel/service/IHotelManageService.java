package com.yimamerchant.system.hotel.service;

import java.util.List;
import com.yimamerchant.system.hotel.domain.HotelAccount;
import com.yimamerchant.system.hotel.domain.HotelBdBind;
import com.yimamerchant.system.hotel.domain.HotelBdBindHistory;
import com.yimamerchant.system.hotel.domain.HotelBill;
import com.yimamerchant.system.hotel.domain.HotelBillOrder;
import com.yimamerchant.system.hotel.domain.HotelBillPayment;
import com.yimamerchant.system.hotel.domain.HotelBusinessConfig;
import com.yimamerchant.system.hotel.domain.HotelBusinessConfigLog;
import com.yimamerchant.system.hotel.domain.HotelOptionVO;
import com.yimamerchant.system.hotel.domain.HotelOrder;
import com.yimamerchant.system.hotel.domain.HotelOrderLog;
import com.yimamerchant.system.hotel.domain.HotelPartner;
import com.yimamerchant.system.hotel.domain.HotelPartnerStatistics;
import com.yimamerchant.system.hotel.domain.HotelPendingApply;
import com.yimamerchant.system.hotel.domain.HotelPriceCalendarItem;
import com.yimamerchant.system.hotel.domain.HotelRoomCalendarLog;
import com.yimamerchant.system.hotel.domain.HotelRoomType;
import com.yimamerchant.system.hotel.dto.HotelBillGenerateDTO;
import com.yimamerchant.system.hotel.dto.HotelBillOperateDTO;
import com.yimamerchant.system.hotel.dto.HotelBindBatchDTO;
import com.yimamerchant.system.hotel.dto.HotelBindTransferDTO;
import com.yimamerchant.system.hotel.dto.HotelOrderOperateDTO;
import com.yimamerchant.system.hotel.dto.HotelPartnerStatusDTO;
import com.yimamerchant.system.hotel.dto.HotelPendingApproveDTO;
import com.yimamerchant.system.hotel.dto.HotelPendingRejectDTO;
import com.yimamerchant.system.hotel.dto.HotelPriceCalendarQuery;
import com.yimamerchant.system.hotel.dto.HotelPriceCopyDTO;
import com.yimamerchant.system.hotel.dto.HotelPriceOperateDTO;
import com.yimamerchant.system.hotel.dto.HotelRoomTypeCopyDTO;
import com.yimamerchant.system.hotel.dto.HotelRoomTypeSortDTO;
import com.yimamerchant.system.hotel.dto.HotelRoomTypeStatusDTO;

public interface IHotelManageService
{
    List<HotelPendingApply> selectPendingList(HotelPendingApply query);
    HotelPendingApply selectPendingById(Long id);
    int insertPending(HotelPendingApply entity);
    int updatePending(HotelPendingApply entity);
    int approvePending(HotelPendingApproveDTO dto);
    int rejectPending(HotelPendingRejectDTO dto);
    int deletePendingByIds(Long[] ids);

    List<HotelPartner> selectPartnerList(HotelPartner query);
    HotelPartner selectPartnerById(Long hotelId);
    int updatePartner(HotelPartner entity);
    int updatePartnerStatus(HotelPartnerStatusDTO dto);
    HotelPartnerStatistics selectPartnerStatistics(HotelPartner query);
    int resetPartnerPassword(Long hotelId);
    List<HotelAccount> selectPartnerAccounts(Long hotelId);

    List<HotelBdBind> selectBindList(HotelBdBind query);
    int batchBind(HotelBindBatchDTO dto);
    int batchUnbind(Long[] ids);
    int transferBind(HotelBindTransferDTO dto);
    List<HotelBdBindHistory> selectBindHistoryList(HotelBdBindHistory query);
    List<HotelOptionVO> selectBdOptions(String keyword);
    List<HotelOptionVO> selectHotelOptions(String keyword);

    List<HotelPartner> selectHotelInfoList(HotelPartner query);
    HotelPartner selectHotelInfoById(Long hotelId);
    int insertHotelInfo(HotelPartner entity);
    int updateHotelInfo(HotelPartner entity);
    int updateHotelInfoStatus(HotelPartner entity);
    List<?> selectHotelChangeLogs(Long hotelId);

    List<HotelRoomType> selectRoomTypeList(HotelRoomType query);
    HotelRoomType selectRoomTypeById(Long id);
    int insertRoomType(HotelRoomType entity);
    int updateRoomType(HotelRoomType entity);
    int deleteRoomTypeByIds(Long[] ids);
    int updateRoomTypeStatus(HotelRoomTypeStatusDTO dto);
    int updateRoomTypeSort(HotelRoomTypeSortDTO dto);
    int copyRoomType(HotelRoomTypeCopyDTO dto);

    List<HotelPriceCalendarItem> selectPriceCalendar(HotelPriceCalendarQuery query);
    int saveDailyPrice(HotelPriceOperateDTO dto);
    int batchSavePrice(HotelPriceOperateDTO dto);
    int closePrice(HotelPriceOperateDTO dto);
    int copyPrice(HotelPriceCopyDTO dto);
    int restorePrice(HotelPriceOperateDTO dto);
    List<HotelRoomCalendarLog> selectPriceLogList(HotelRoomCalendarLog query);

    List<HotelOrder> selectOrderList(HotelOrder query);
    HotelOrder selectOrderByNo(String orderNo);
    int confirmOrder(HotelOrderOperateDTO dto);
    int cancelOrder(HotelOrderOperateDTO dto);
    int checkinOrder(HotelOrderOperateDTO dto);
    int checkoutOrder(HotelOrderOperateDTO dto);
    int remarkOrder(HotelOrderOperateDTO dto);
    int refundOrder(HotelOrderOperateDTO dto);
    int disputeOrder(HotelOrderOperateDTO dto);
    List<HotelOrderLog> selectOrderLogs(String orderNo);

    List<HotelBill> selectBillList(HotelBill query);
    String generateBill(HotelBillGenerateDTO dto);
    HotelBill selectBillByNo(String billNo);
    List<HotelBillOrder> selectBillOrders(String billNo, String orderNo);
    int confirmBill(HotelBillOperateDTO dto);
    int disputeBill(HotelBillOperateDTO dto);
    int recalculateBill(HotelBillOperateDTO dto);
    int paymentBill(HotelBillPayment entity);
    HotelPartnerStatistics selectBillStatistics(HotelBill query);

    List<HotelBusinessConfig> selectBusinessConfigs();
    int saveBusinessConfigs(List<HotelBusinessConfig> list);
    List<HotelBusinessConfigLog> selectBusinessConfigLogs();
}
