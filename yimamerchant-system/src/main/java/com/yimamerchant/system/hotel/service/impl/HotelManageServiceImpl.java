package com.yimamerchant.system.hotel.service.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.yimamerchant.common.utils.DateUtils;
import com.yimamerchant.common.utils.SecurityUtils;
import com.yimamerchant.common.utils.StringUtils;
import com.yimamerchant.common.utils.uuid.IdUtils;
import com.yimamerchant.system.hotel.domain.HotelAccount;
import com.yimamerchant.system.hotel.domain.HotelBdBind;
import com.yimamerchant.system.hotel.domain.HotelBdBindHistory;
import com.yimamerchant.system.hotel.domain.HotelBill;
import com.yimamerchant.system.hotel.domain.HotelBillOrder;
import com.yimamerchant.system.hotel.domain.HotelBillPayment;
import com.yimamerchant.system.hotel.domain.HotelBusinessConfig;
import com.yimamerchant.system.hotel.domain.HotelBusinessConfigLog;
import com.yimamerchant.system.hotel.domain.HotelContract;
import com.yimamerchant.system.hotel.domain.HotelImage;
import com.yimamerchant.system.hotel.domain.HotelOptionVO;
import com.yimamerchant.system.hotel.domain.HotelOrder;
import com.yimamerchant.system.hotel.domain.HotelOrderLog;
import com.yimamerchant.system.hotel.domain.HotelPartner;
import com.yimamerchant.system.hotel.domain.HotelPartnerStatistics;
import com.yimamerchant.system.hotel.domain.HotelPendingApply;
import com.yimamerchant.system.hotel.domain.HotelPendingAuditLog;
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
import com.yimamerchant.system.hotel.mapper.HotelManageMapper;
import com.yimamerchant.system.hotel.service.IHotelManageService;

@Service
public class HotelManageServiceImpl implements IHotelManageService
{
    @Autowired
    private HotelManageMapper hotelManageMapper;

    @Override
    public List<HotelPendingApply> selectPendingList(HotelPendingApply query)
    {
        return hotelManageMapper.selectPendingList(query);
    }

    @Override
    public HotelPendingApply selectPendingById(Long id)
    {
        HotelPendingApply apply = hotelManageMapper.selectPendingById(id);
        if (apply != null)
        {
            apply.getParams().put("auditLogs", hotelManageMapper.selectPendingAuditLogs(id));
        }
        return apply;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertPending(HotelPendingApply entity)
    {
        Date now = DateUtils.getNowDate();
        entity.setApplyNo(buildNo("APPLY"));
        entity.setApplyStatus("PENDING");
        entity.setApplicantId(SecurityUtils.getUserId());
        entity.setApplicantName(SecurityUtils.getUsername());
        entity.setBusinessStatus(StringUtils.defaultIfBlank(entity.getBusinessStatus(), "OPEN"));
        entity.setApplyTime(now);
        entity.setLastOperateTime(now);
        entity.setCreateBy(SecurityUtils.getUsername());
        int rows = hotelManageMapper.insertPending(entity);
        if (rows > 0) savePendingLog(entity.getId(), "SUBMIT", entity.getApplyRemark());
        return rows;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updatePending(HotelPendingApply entity)
    {
        HotelPendingApply old = hotelManageMapper.selectPendingById(entity.getId());
        if (old == null || "APPROVED".equals(old.getApplyStatus())) return 0;
        entity.setLastOperateTime(DateUtils.getNowDate());
        entity.setUpdateBy(SecurityUtils.getUsername());
        int rows = hotelManageMapper.updatePending(entity);
        if (rows > 0) savePendingLog(entity.getId(), "UPDATE", "编辑申请");
        return rows;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int approvePending(HotelPendingApproveDTO dto)
    {
        HotelPendingApply apply = hotelManageMapper.selectPendingById(dto.getId());
        if (apply == null) return 0;
        Date now = DateUtils.getNowDate();
        HotelPartner partner = new HotelPartner();
        partner.setHotelCode(buildNo("HOTEL"));
        partner.setHotelName(apply.getHotelName());
        partner.setContactName(apply.getContactName());
        partner.setContactPhone(apply.getContactPhone());
        partner.setProvinceCode(apply.getProvinceCode());
        partner.setProvinceName(apply.getProvinceName());
        partner.setCityCode(apply.getCityCode());
        partner.setCityName(apply.getCityName());
        partner.setDistrictCode(apply.getDistrictCode());
        partner.setDistrictName(apply.getDistrictName());
        partner.setAddress(apply.getAddress());
        partner.setLongitude(apply.getLongitude());
        partner.setLatitude(apply.getLatitude());
        partner.setCoverImage(apply.getCoverImage());
        partner.setHotelDesc(apply.getHotelDesc());
        partner.setBookingNotice(apply.getBookingNotice());
        partner.setCancelPolicy(apply.getCancelPolicy());
        partner.setInvoiceNotice(apply.getInvoiceNotice());
        partner.setParkingNotice(apply.getParkingNotice());
        partner.setBusinessStatus(StringUtils.defaultIfBlank(apply.getBusinessStatus(), "OPEN"));
        partner.setCheckinTime(apply.getCheckinTime());
        partner.setCheckoutTime(apply.getCheckoutTime());
        partner.setSaleStatus("OFF_SHELF");
        partner.setCooperateStatus("NORMAL");
        partner.setSourceApplyId(apply.getId());
        partner.setSignDate(now);
        partner.setContractStartDate(dto.getContractStartDate());
        partner.setContractEndDate(dto.getContractEndDate());
        partner.setCommissionMode(StringUtils.defaultIfBlank(dto.getCommissionMode(), "BASE_PRICE"));
        partner.setCommissionRate(dto.getCommissionRate());
        partner.setMarkupRate(dto.getMarkupRate());
        partner.setBdUserId(dto.getBdUserId());
        partner.setAccountStatus("ENABLED");
        partner.setCreateBy(SecurityUtils.getUsername());
        hotelManageMapper.insertPartner(partner);
        hotelManageMapper.insertHotelProfile(partner);
        saveHotelImages(partner.getHotelId(), buildBannerImages(apply.getBannerImages()));

        HotelContract contract = new HotelContract();
        contract.setHotelId(partner.getHotelId());
        contract.setContractNo(buildNo("CONTRACT"));
        contract.setContractName(partner.getHotelName() + "合作协议");
        contract.setContractStartDate(dto.getContractStartDate());
        contract.setContractEndDate(dto.getContractEndDate());
        contract.setCommissionMode(partner.getCommissionMode());
        contract.setCommissionRate(dto.getCommissionRate());
        contract.setMarkupRate(dto.getMarkupRate());
        contract.setContractStatus("EFFECTIVE");
        contract.setSignUserId(SecurityUtils.getUserId());
        contract.setSignUserName(SecurityUtils.getUsername());
        contract.setCreateBy(SecurityUtils.getUsername());
        hotelManageMapper.insertContract(contract);

        HotelAccount account = new HotelAccount();
        account.setHotelId(partner.getHotelId());
        account.setAccountType("MAIN");
        account.setAccountName(StringUtils.defaultIfBlank(apply.getContactPhone(), partner.getHotelCode()));
        account.setNickName(StringUtils.defaultIfBlank(apply.getContactName(), partner.getHotelName()));
        account.setMobile(apply.getContactPhone());
        account.setRoleNames("HOTEL_ADMIN");
        account.setAccountStatus("ENABLED");
        account.setCreateBy(SecurityUtils.getUsername());
        hotelManageMapper.insertAccount(account);

        if (dto.getBdUserId() != null)
        {
            HotelBdBind bind = new HotelBdBind();
            bind.setHotelId(partner.getHotelId());
            bind.setBdUserId(dto.getBdUserId());
            bind.setBindStatus("BOUND");
            bind.setBindTime(now);
            bind.setCreateBy(SecurityUtils.getUsername());
            hotelManageMapper.insertBind(bind);
            saveBindHistory(partner.getHotelId(), null, null, dto.getBdUserId(), null, "BIND", dto.getAuditRemark());
        }

        apply.setPartnerHotelId(partner.getHotelId());
        apply.setApplyStatus("APPROVED");
        apply.setAuditTime(now);
        apply.setAuditUserId(SecurityUtils.getUserId());
        apply.setAuditUserName(SecurityUtils.getUsername());
        apply.setLastOperateTime(now);
        apply.setUpdateBy(SecurityUtils.getUsername());
        hotelManageMapper.updatePending(apply);
        savePendingLog(apply.getId(), "APPROVE", dto.getAuditRemark());
        return 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int rejectPending(HotelPendingRejectDTO dto)
    {
        HotelPendingApply apply = hotelManageMapper.selectPendingById(dto.getId());
        if (apply == null) return 0;
        apply.setApplyStatus("REJECTED");
        apply.setRejectReason(dto.getAuditRemark());
        apply.setAuditTime(DateUtils.getNowDate());
        apply.setAuditUserId(SecurityUtils.getUserId());
        apply.setAuditUserName(SecurityUtils.getUsername());
        apply.setLastOperateTime(DateUtils.getNowDate());
        apply.setUpdateBy(SecurityUtils.getUsername());
        int rows = hotelManageMapper.updatePending(apply);
        if (rows > 0) savePendingLog(apply.getId(), "REJECT", dto.getAuditRemark());
        return rows;
    }

    @Override
    public int deletePendingByIds(Long[] ids)
    {
        return hotelManageMapper.deletePendingByIds(ids);
    }

    @Override
    public List<HotelPartner> selectPartnerList(HotelPartner query)
    {
        return hotelManageMapper.selectPartnerList(query);
    }

    @Override
    public HotelPartner selectPartnerById(Long hotelId)
    {
        HotelPartner partner = hotelManageMapper.selectPartnerById(hotelId);
        if (partner != null)
        {
            partner.setContracts(hotelManageMapper.selectPartnerContracts(hotelId));
            partner.setAccounts(hotelManageMapper.selectPartnerAccounts(hotelId));
            partner.setImageList(hotelManageMapper.selectHotelImages(hotelId));
        }
        return partner;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updatePartner(HotelPartner entity)
    {
        entity.setUpdateBy(SecurityUtils.getUsername());
        int rows = hotelManageMapper.updatePartner(entity);
        if (entity.getContracts() != null && !entity.getContracts().isEmpty())
        {
            HotelContract contract = entity.getContracts().get(0);
            contract.setHotelId(entity.getHotelId());
            contract.setUpdateBy(SecurityUtils.getUsername());
            if (hotelManageMapper.updateContractByHotelId(contract) == 0)
            {
                contract.setContractNo(buildNo("CONTRACT"));
                contract.setCreateBy(SecurityUtils.getUsername());
                hotelManageMapper.insertContract(contract);
            }
        }
        return rows;
    }

    @Override
    public int updatePartnerStatus(HotelPartnerStatusDTO dto)
    {
        HotelPartner entity = new HotelPartner();
        entity.setHotelId(dto.getHotelId());
        entity.setCooperateStatus(dto.getCooperateStatus());
        entity.setStatusReason(dto.getOperateReason());
        entity.setUpdateBy(SecurityUtils.getUsername());
        return hotelManageMapper.updatePartner(entity);
    }

    @Override
    public HotelPartnerStatistics selectPartnerStatistics(HotelPartner query)
    {
        HotelPartnerStatistics statistics = hotelManageMapper.selectPartnerStatistics(query);
        return statistics == null ? new HotelPartnerStatistics() : statistics;
    }

    @Override
    public int resetPartnerPassword(Long hotelId)
    {
        return hotelManageMapper.selectPartnerAccounts(hotelId).isEmpty() ? 0 : 1;
    }

    @Override
    public List<HotelAccount> selectPartnerAccounts(Long hotelId)
    {
        return hotelManageMapper.selectPartnerAccounts(hotelId);
    }

    @Override
    public List<HotelBdBind> selectBindList(HotelBdBind query)
    {
        return hotelManageMapper.selectBindList(query);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchBind(HotelBindBatchDTO dto)
    {
        int rows = 0;
        for (Long hotelId : dto.getHotelIds())
        {
            HotelBdBind oldBind = hotelManageMapper.selectActiveBindByHotelId(hotelId);
            if (oldBind != null)
            {
                oldBind.setBindStatus("UNBOUND");
                oldBind.setUnbindTime(DateUtils.getNowDate());
                oldBind.setUpdateBy(SecurityUtils.getUsername());
                hotelManageMapper.updateBind(oldBind);
            }
            HotelBdBind bind = new HotelBdBind();
            bind.setHotelId(hotelId);
            bind.setBdUserId(dto.getBdUserId());
            bind.setBindStatus("BOUND");
            bind.setBindTime(DateUtils.getNowDate());
            bind.setCreateBy(SecurityUtils.getUsername());
            rows += hotelManageMapper.insertBind(bind);
            hotelManageMapper.updatePartnerBdInfo(hotelId, dto.getBdUserId());
            saveBindHistory(hotelId, oldBind == null ? null : oldBind.getBdUserId(), oldBind == null ? null : oldBind.getBdUserName(),
                dto.getBdUserId(), null, "BIND", dto.getRemark());
        }
        return rows;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchUnbind(Long[] ids)
    {
        int rows = 0;
        for (HotelBdBind bind : hotelManageMapper.selectBindByIds(ids))
        {
            bind.setBindStatus("UNBOUND");
            bind.setUnbindTime(DateUtils.getNowDate());
            bind.setUpdateBy(SecurityUtils.getUsername());
            rows += hotelManageMapper.updateBind(bind);
            hotelManageMapper.updatePartnerBdInfo(bind.getHotelId(), null);
            saveBindHistory(bind.getHotelId(), bind.getBdUserId(), bind.getBdUserName(), null, null, "UNBIND", bind.getRemark());
        }
        return rows;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int transferBind(HotelBindTransferDTO dto)
    {
        int rows = 0;
        for (Long hotelId : dto.getHotelIds())
        {
            HotelBdBind oldBind = hotelManageMapper.selectActiveBindByHotelId(hotelId);
            if (oldBind != null)
            {
                oldBind.setBindStatus("UNBOUND");
                oldBind.setUnbindTime(DateUtils.getNowDate());
                oldBind.setUpdateBy(SecurityUtils.getUsername());
                hotelManageMapper.updateBind(oldBind);
            }
            HotelBdBind bind = new HotelBdBind();
            bind.setHotelId(hotelId);
            bind.setBdUserId(dto.getTargetBdUserId());
            bind.setBindStatus("BOUND");
            bind.setBindTime(DateUtils.getNowDate());
            bind.setCreateBy(SecurityUtils.getUsername());
            rows += hotelManageMapper.insertBind(bind);
            hotelManageMapper.updatePartnerBdInfo(hotelId, dto.getTargetBdUserId());
            saveBindHistory(hotelId, oldBind == null ? null : oldBind.getBdUserId(), oldBind == null ? null : oldBind.getBdUserName(),
                dto.getTargetBdUserId(), null, "TRANSFER", dto.getTransferReason());
        }
        return rows;
    }

    @Override
    public List<HotelBdBindHistory> selectBindHistoryList(HotelBdBindHistory query)
    {
        return hotelManageMapper.selectBindHistoryList(query);
    }

    @Override
    public List<HotelOptionVO> selectBdOptions(String keyword)
    {
        return hotelManageMapper.selectBdOptions(keyword);
    }

    @Override
    public List<HotelOptionVO> selectHotelOptions(String keyword)
    {
        return hotelManageMapper.selectHotelOptions(keyword);
    }

    @Override
    public List<HotelPartner> selectHotelInfoList(HotelPartner query)
    {
        return hotelManageMapper.selectHotelInfoList(query);
    }

    @Override
    public HotelPartner selectHotelInfoById(Long hotelId)
    {
        HotelPartner entity = hotelManageMapper.selectHotelInfoById(hotelId);
        if (entity != null)
        {
            List<HotelImage> images = hotelManageMapper.selectHotelImages(hotelId);
            entity.setImageList(images);
            entity.setBannerImages(images.stream().map(HotelImage::getImageUrl).filter(StringUtils::isNotBlank).collect(Collectors.joining(",")));
        }
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertHotelInfo(HotelPartner entity)
    {
        entity.setCreateBy(SecurityUtils.getUsername());
        if (entity.getHotelId() == null)
        {
            entity.setHotelCode(buildNo("HOTEL"));
            entity.setCooperateStatus(StringUtils.defaultIfBlank(entity.getCooperateStatus(), "NORMAL"));
            hotelManageMapper.insertPartner(entity);
        }
        entity.setBusinessStatus(StringUtils.defaultIfBlank(entity.getBusinessStatus(), "OPEN"));
        entity.setSaleStatus(StringUtils.defaultIfBlank(entity.getSaleStatus(), "OFF_SHELF"));
        hotelManageMapper.insertHotelProfile(entity);
        saveHotelImages(entity.getHotelId(), mergeBannerImages(entity));
        return 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateHotelInfo(HotelPartner entity)
    {
        entity.setUpdateBy(SecurityUtils.getUsername());
        entity.setBusinessStatus(StringUtils.defaultIfBlank(entity.getBusinessStatus(), "OPEN"));
        hotelManageMapper.updatePartner(entity);
        if (hotelManageMapper.updateHotelProfile(entity) == 0)
        {
            entity.setCreateBy(SecurityUtils.getUsername());
            entity.setSaleStatus(StringUtils.defaultIfBlank(entity.getSaleStatus(), "OFF_SHELF"));
            hotelManageMapper.insertHotelProfile(entity);
        }
        saveHotelImages(entity.getHotelId(), mergeBannerImages(entity));
        return 1;
    }

    @Override
    public int updateHotelInfoStatus(HotelPartner entity)
    {
        entity.setUpdateBy(SecurityUtils.getUsername());
        return hotelManageMapper.updateHotelProfile(entity);
    }

    @Override
    public List<?> selectHotelChangeLogs(Long hotelId)
    {
        return hotelManageMapper.selectHotelChangeLogs(hotelId);
    }

    @Override
    public List<HotelRoomType> selectRoomTypeList(HotelRoomType query)
    {
        return hotelManageMapper.selectRoomTypeList(query);
    }

    @Override
    public HotelRoomType selectRoomTypeById(Long id)
    {
        HotelRoomType entity = hotelManageMapper.selectRoomTypeById(id);
        if (entity != null) entity.setImageList(hotelManageMapper.selectRoomTypeImages(id));
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertRoomType(HotelRoomType entity)
    {
        entity.setRoomTypeCode(buildNo("ROOM"));
        entity.setCreateBy(SecurityUtils.getUsername());
        int rows = hotelManageMapper.insertRoomType(entity);
        saveRoomImages(entity.getId(), entity.getImageList());
        return rows;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateRoomType(HotelRoomType entity)
    {
        entity.setUpdateBy(SecurityUtils.getUsername());
        int rows = hotelManageMapper.updateRoomType(entity);
        saveRoomImages(entity.getId(), entity.getImageList());
        return rows;
    }

    @Override
    public int deleteRoomTypeByIds(Long[] ids)
    {
        return hotelManageMapper.deleteRoomTypeByIds(ids);
    }

    @Override
    public int updateRoomTypeStatus(HotelRoomTypeStatusDTO dto)
    {
        HotelRoomType entity = new HotelRoomType();
        entity.setId(dto.getId());
        entity.setSaleStatus(dto.getSaleStatus());
        entity.setUpdateBy(SecurityUtils.getUsername());
        return hotelManageMapper.updateRoomType(entity);
    }

    @Override
    public int updateRoomTypeSort(HotelRoomTypeSortDTO dto)
    {
        HotelRoomType entity = new HotelRoomType();
        entity.setId(dto.getId());
        entity.setSortNum(dto.getSortNum());
        entity.setUpdateBy(SecurityUtils.getUsername());
        return hotelManageMapper.updateRoomType(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int copyRoomType(HotelRoomTypeCopyDTO dto)
    {
        int rows = 0;
        for (Long id : dto.getRoomTypeIds())
        {
            HotelRoomType source = selectRoomTypeById(id);
            if (source == null) continue;
            source.setId(null);
            source.setHotelId(dto.getTargetHotelId());
            source.setRoomTypeCode(buildNo("ROOM"));
            source.setCreateBy(SecurityUtils.getUsername());
            rows += hotelManageMapper.insertRoomType(source);
            saveRoomImages(source.getId(), source.getImageList());
        }
        return rows;
    }

    @Override
    public List<HotelPriceCalendarItem> selectPriceCalendar(HotelPriceCalendarQuery query)
    {
        List<Long> roomTypeIds = parseLongList(query.getRoomTypeIds());
        String month = StringUtils.defaultIfBlank(query.getMonth(), DateUtils.dateTimeNow(DateUtils.YYYY_MM));
        return hotelManageMapper.selectPriceCalendar(query.getHotelId(), roomTypeIds, month + "-01", month + "-31");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveDailyPrice(HotelPriceOperateDTO dto) { return savePriceOperate(dto, "DAILY"); }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchSavePrice(HotelPriceOperateDTO dto) { return savePriceOperate(dto, "BATCH"); }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int closePrice(HotelPriceOperateDTO dto)
    {
        dto.setInventory(0);
        dto.setRoomStatus("CLOSED");
        return savePriceOperate(dto, "CLOSE");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int copyPrice(HotelPriceCopyDTO dto)
    {
        int rows = 0;
        for (Long roomTypeId : dto.getRoomTypeIds())
        {
            HotelPriceCalendarItem source = hotelManageMapper.selectPriceByRoomTypeAndDate(roomTypeId, dto.getSourceDate());
            if (source == null) continue;
            for (String targetDate : dto.getTargetDates())
            {
                HotelPriceOperateDTO op = new HotelPriceOperateDTO();
                op.setHotelId(dto.getHotelId());
                op.setRoomTypeIds(Collections.singletonList(roomTypeId));
                op.setBizDates(Collections.singletonList(targetDate));
                op.setSettlementPrice(source.getSettlementPrice());
                op.setSalePrice(source.getSalePrice());
                op.setCommissionRate(source.getCommissionRate());
                op.setInventory(source.getInventory());
                op.setRefundRule(source.getRefundRule());
                op.setSpecialTag(source.getSpecialTag());
                op.setRoomStatus(source.getRoomStatus());
                op.setRemark(dto.getRemark());
                rows += savePriceOperate(op, "COPY");
            }
        }
        return rows;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int restorePrice(HotelPriceOperateDTO dto)
    {
        dto.setRoomStatus("OPEN");
        return savePriceOperate(dto, "RESTORE");
    }

    @Override
    public List<HotelRoomCalendarLog> selectPriceLogList(HotelRoomCalendarLog query)
    {
        return hotelManageMapper.selectPriceLogList(query);
    }

    @Override
    public List<HotelOrder> selectOrderList(HotelOrder query)
    {
        return hotelManageMapper.selectOrderList(query);
    }

    @Override
    public HotelOrder selectOrderByNo(String orderNo)
    {
        HotelOrder order = hotelManageMapper.selectOrderByNo(orderNo);
        if (order != null)
        {
            order.setGuestList(hotelManageMapper.selectOrderGuests(orderNo));
            order.setLogList(hotelManageMapper.selectOrderLogs(orderNo));
        }
        return order;
    }

    @Override
    public int confirmOrder(HotelOrderOperateDTO dto) { return updateOrderStatus(dto, "CONFIRM", "PENDING_CONFIRM", "CONFIRMED"); }

    @Override
    public int cancelOrder(HotelOrderOperateDTO dto) { return updateOrderStatus(dto, "CANCEL", null, "CANCELLED"); }

    @Override
    public int checkinOrder(HotelOrderOperateDTO dto) { return updateOrderStatus(dto, "CHECKIN", "CONFIRMED", "CHECKED_IN"); }

    @Override
    public int checkoutOrder(HotelOrderOperateDTO dto) { return updateOrderStatus(dto, "CHECKOUT", "CHECKED_IN", "COMPLETED"); }

    @Override
    public int remarkOrder(HotelOrderOperateDTO dto)
    {
        HotelOrder order = hotelManageMapper.selectOrderByNo(dto.getOrderNo());
        if (order == null) return 0;
        order.setInnerRemark(dto.getOperateRemark());
        order.setUpdateBy(SecurityUtils.getUsername());
        int rows = hotelManageMapper.updateOrder(order);
        saveOrderLog(order, "REMARK", order.getOrderStatus(), order.getOrderStatus(), dto.getOperateRemark());
        return rows;
    }

    @Override
    public int refundOrder(HotelOrderOperateDTO dto)
    {
        HotelOrder order = hotelManageMapper.selectOrderByNo(dto.getOrderNo());
        if (order == null) return 0;
        order.setRefundStatus("PROCESSING");
        order.setRefundReason(dto.getRefundReason());
        order.setUpdateBy(SecurityUtils.getUsername());
        int rows = hotelManageMapper.updateOrder(order);
        saveOrderLog(order, "REFUND", order.getOrderStatus(), order.getOrderStatus(), dto.getRefundReason());
        return rows;
    }

    @Override
    public int disputeOrder(HotelOrderOperateDTO dto)
    {
        HotelOrder order = hotelManageMapper.selectOrderByNo(dto.getOrderNo());
        if (order == null) return 0;
        saveOrderLog(order, "DISPUTE", order.getOrderStatus(), order.getOrderStatus(), dto.getOperateRemark());
        return 1;
    }

    @Override
    public List<HotelOrderLog> selectOrderLogs(String orderNo)
    {
        return hotelManageMapper.selectOrderLogs(orderNo);
    }

    @Override
    public List<HotelBill> selectBillList(HotelBill query)
    {
        return hotelManageMapper.selectBillList(query);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String generateBill(HotelBillGenerateDTO dto)
    {
        String billNo = buildNo("BILL");
        HotelBill bill = buildBill(dto, billNo);
        hotelManageMapper.insertBill(bill);
        saveBillOrders(bill);
        saveBillCheckLog(bill.getBillNo(), "GENERATE", "生成账单");
        return billNo;
    }

    @Override
    public HotelBill selectBillByNo(String billNo)
    {
        HotelBill bill = hotelManageMapper.selectBillByNo(billNo);
        if (bill != null)
        {
            bill.setOrderList(hotelManageMapper.selectBillOrders(billNo, null));
            bill.setCheckLogList(hotelManageMapper.selectBillCheckLogs(billNo));
            bill.setPaymentList(hotelManageMapper.selectBillPayments(billNo));
        }
        return bill;
    }

    @Override
    public List<HotelBillOrder> selectBillOrders(String billNo, String orderNo)
    {
        return hotelManageMapper.selectBillOrders(billNo, orderNo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int confirmBill(HotelBillOperateDTO dto)
    {
        HotelBill bill = hotelManageMapper.selectBillByNo(dto.getBillNo());
        if (bill == null) return 0;
        bill.setBillStatus("CONFIRMED");
        bill.setConfirmTime(DateUtils.getNowDate());
        bill.setConfirmUserId(SecurityUtils.getUserId());
        bill.setConfirmUserName(SecurityUtils.getUsername());
        bill.setUpdateBy(SecurityUtils.getUsername());
        int rows = hotelManageMapper.updateBill(bill);
        saveBillCheckLog(dto.getBillNo(), "CONFIRM", dto.getOperateRemark());
        return rows;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int disputeBill(HotelBillOperateDTO dto)
    {
        HotelBill bill = hotelManageMapper.selectBillByNo(dto.getBillNo());
        if (bill == null) return 0;
        bill.setBillStatus("DISPUTED");
        bill.setDisputeStatus("PENDING");
        bill.setUpdateBy(SecurityUtils.getUsername());
        int rows = hotelManageMapper.updateBill(bill);
        saveBillCheckLog(dto.getBillNo(), "DISPUTE", dto.getOperateRemark());
        return rows;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int recalculateBill(HotelBillOperateDTO dto)
    {
        HotelBill current = hotelManageMapper.selectBillByNo(dto.getBillNo());
        if (current == null) return 0;
        HotelBillGenerateDTO generateDTO = new HotelBillGenerateDTO();
        generateDTO.setHotelId(current.getHotelId());
        generateDTO.setStatementStartDate(current.getStatementStartDate());
        generateDTO.setStatementEndDate(current.getStatementEndDate());
        generateDTO.setGenerateMode("MANUAL");
        HotelBill rebuild = buildBill(generateDTO, current.getBillNo());
        rebuild.setId(current.getId());
        rebuild.setUpdateBy(SecurityUtils.getUsername());
        hotelManageMapper.updateBill(rebuild);
        hotelManageMapper.deleteBillOrdersByBillNo(current.getBillNo());
        saveBillOrders(rebuild);
        saveBillCheckLog(dto.getBillNo(), "RECALCULATE", dto.getOperateRemark());
        return 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int paymentBill(HotelBillPayment entity)
    {
        HotelBill bill = hotelManageMapper.selectBillByNo(entity.getBillNo());
        if (bill == null) return 0;
        entity.setPaymentNo(StringUtils.defaultIfBlank(entity.getPaymentNo(), buildNo("PAY")));
        entity.setCreateBy(SecurityUtils.getUsername());
        if (entity.getPaymentTime() == null) entity.setPaymentTime(DateUtils.getNowDate());
        if (StringUtils.isBlank(entity.getPaymentStatus())) entity.setPaymentStatus("PAID");
        hotelManageMapper.insertBillPayment(entity);
        bill.setPaymentStatus(entity.getPaymentStatus());
        bill.setBillStatus("PAID");
        bill.setUpdateBy(SecurityUtils.getUsername());
        hotelManageMapper.updateBill(bill);
        return 1;
    }

    @Override
    public HotelPartnerStatistics selectBillStatistics(HotelBill query)
    {
        HotelPartnerStatistics statistics = new HotelPartnerStatistics();
        List<HotelBill> list = hotelManageMapper.selectBillList(query);
        statistics.setTotalCount((long) list.size());
        statistics.setNormalCount(list.stream().map(HotelBill::getOrderCount).filter(Objects::nonNull).mapToLong(Integer::longValue).sum());
        statistics.setMonthlySalesAmount(list.stream().map(HotelBill::getTotalSettlementAmount).filter(Objects::nonNull)
            .reduce(BigDecimal.ZERO, BigDecimal::add));
        return statistics;
    }

    @Override
    public List<HotelBusinessConfig> selectBusinessConfigs()
    {
        return hotelManageMapper.selectBusinessConfigs();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveBusinessConfigs(List<HotelBusinessConfig> list)
    {
        int rows = 0;
        for (HotelBusinessConfig entity : list)
        {
            HotelBusinessConfig old = hotelManageMapper.selectBusinessConfigByKey(entity.getConfigKey());
            if (old == null)
            {
                entity.setCreateBy(SecurityUtils.getUsername());
                rows += hotelManageMapper.insertBusinessConfig(entity);
                saveConfigLog(entity.getId(), entity.getConfigKey(), null, entity.getConfigValue());
            }
            else
            {
                entity.setId(old.getId());
                entity.setUpdateBy(SecurityUtils.getUsername());
                rows += hotelManageMapper.updateBusinessConfig(entity);
                saveConfigLog(old.getId(), old.getConfigKey(), old.getConfigValue(), entity.getConfigValue());
            }
        }
        return rows;
    }

    @Override
    public List<HotelBusinessConfigLog> selectBusinessConfigLogs()
    {
        return hotelManageMapper.selectBusinessConfigLogs();
    }

    private int savePriceOperate(HotelPriceOperateDTO dto, String operateType)
    {
        int rows = 0;
        for (Long roomTypeId : dto.getRoomTypeIds())
        {
            for (String bizDate : dto.getBizDates())
            {
                HotelPriceCalendarItem old = hotelManageMapper.selectPriceByRoomTypeAndDate(roomTypeId, bizDate);
                HotelPriceCalendarItem entity = old == null ? new HotelPriceCalendarItem() : old;
                entity.setHotelId(dto.getHotelId());
                entity.setRoomTypeId(roomTypeId);
                entity.setBizDate(DateUtils.parseDate(bizDate));
                entity.setSettlementPrice(dto.getSettlementPrice());
                entity.setSalePrice(dto.getSalePrice());
                entity.setCommissionRate(dto.getCommissionRate());
                entity.setInventory(dto.getInventory());
                entity.setRefundRule(dto.getRefundRule());
                entity.setSpecialTag(dto.getSpecialTag());
                entity.setIsDefaultData(StringUtils.defaultIfBlank(entity.getIsDefaultData(), "0"));
                entity.setRoomStatus(StringUtils.defaultIfBlank(dto.getRoomStatus(), "OPEN"));
                entity.setCommissionAmount(calcCommissionAmount(entity.getSettlementPrice(), entity.getSalePrice(), entity.getCommissionRate()));
                if (old == null) rows += hotelManageMapper.insertPriceCalendar(entity);
                else rows += hotelManageMapper.updatePriceCalendar(entity);
                HotelRoomCalendarLog log = new HotelRoomCalendarLog();
                log.setCalendarId(entity.getId());
                log.setHotelId(dto.getHotelId());
                log.setRoomTypeId(roomTypeId);
                log.setBizDate(entity.getBizDate());
                log.setOperateType(operateType);
                log.setBeforeJson(old == null ? null : old.toString());
                log.setAfterJson(entity.toString());
                log.setOperateUserId(SecurityUtils.getUserId());
                log.setOperateUserName(SecurityUtils.getUsername());
                log.setRemark(dto.getRemark());
                log.setCreateBy(SecurityUtils.getUsername());
                hotelManageMapper.insertPriceLog(log);
            }
        }
        return rows;
    }

    private int updateOrderStatus(HotelOrderOperateDTO dto, String operateType, String expectStatus, String targetStatus)
    {
        HotelOrder order = hotelManageMapper.selectOrderByNo(dto.getOrderNo());
        if (order == null) return 0;
        if (StringUtils.isNotBlank(expectStatus) && !expectStatus.equals(order.getOrderStatus())) return 0;
        String before = order.getOrderStatus();
        order.setOrderStatus(targetStatus);
        if ("CANCEL".equals(operateType)) order.setCancelReason(dto.getCancelReason());
        if ("CONFIRM".equals(operateType)) order.setConfirmTime(DateUtils.getNowDate());
        if ("CHECKIN".equals(operateType)) order.setCheckinTime(DateUtils.getNowDate());
        if ("CHECKOUT".equals(operateType)) order.setCheckoutTime(DateUtils.getNowDate());
        if ("CANCEL".equals(operateType)) order.setCancelTime(DateUtils.getNowDate());
        order.setUpdateBy(SecurityUtils.getUsername());
        int rows = hotelManageMapper.updateOrder(order);
        saveOrderLog(order, operateType, before, targetStatus, StringUtils.defaultIfBlank(dto.getOperateRemark(), dto.getCancelReason()));
        return rows;
    }

    private HotelBill buildBill(HotelBillGenerateDTO dto, String billNo)
    {
        HotelOrder query = new HotelOrder();
        query.setHotelId(dto.getHotelId());
        query.setOrderStatus("COMPLETED");
        query.setBeginCheckinDate(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, dto.getStatementStartDate()));
        query.setEndCheckinDate(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, dto.getStatementEndDate()));
        List<HotelOrder> orders = hotelManageMapper.selectOrderList(query);
        HotelBill bill = new HotelBill();
        bill.setBillNo(billNo);
        bill.setHotelId(dto.getHotelId());
        if (!orders.isEmpty())
        {
            bill.setHotelName(orders.get(0).getHotelName());
        }
        else
        {
            HotelPartner partner = hotelManageMapper.selectPartnerById(dto.getHotelId());
            if (partner != null) bill.setHotelName(partner.getHotelName());
        }
        bill.setStatementStartDate(dto.getStatementStartDate());
        bill.setStatementEndDate(dto.getStatementEndDate());
        bill.setOrderCount(orders.size());
        bill.setRoomNightCount(orders.stream().map(HotelOrder::getNightCount).filter(Objects::nonNull).mapToInt(Integer::intValue).sum());
        bill.setTotalRoomAmount(orders.stream().map(HotelOrder::getOrderAmount).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add));
        bill.setTotalCommissionAmount(orders.stream().map(this::calcCommissionByOrder).reduce(BigDecimal.ZERO, BigDecimal::add));
        bill.setTotalSettlementAmount(bill.getTotalRoomAmount().subtract(bill.getTotalCommissionAmount()));
        bill.setBillStatus("PENDING_CONFIRM");
        bill.setPaymentStatus("UNPAID");
        bill.setGenerateMode(StringUtils.defaultIfBlank(dto.getGenerateMode(), "MANUAL"));
        bill.setCreateBy(SecurityUtils.getUsername());
        bill.setOrderList(orders.stream().map(this::toBillOrder).collect(Collectors.toList()));
        return bill;
    }

    private void saveBillOrders(HotelBill bill)
    {
        for (HotelBillOrder order : bill.getOrderList())
        {
            order.setBillId(bill.getId());
            order.setBillNo(bill.getBillNo());
            order.setHotelId(bill.getHotelId());
            order.setCreateBy(SecurityUtils.getUsername());
            hotelManageMapper.insertBillOrder(order);
        }
    }

    private HotelBillOrder toBillOrder(HotelOrder order)
    {
        HotelBillOrder entity = new HotelBillOrder();
        entity.setOrderId(order.getId());
        entity.setOrderNo(order.getOrderNo());
        entity.setRoomTypeId(order.getRoomTypeId());
        entity.setRoomTypeName(order.getRoomTypeName());
        entity.setCheckinDate(order.getCheckinDate());
        entity.setCheckoutDate(order.getCheckoutDate());
        entity.setNightCount(order.getNightCount());
        entity.setOrderAmount(order.getOrderAmount());
        entity.setCommissionAmount(calcCommissionByOrder(order));
        entity.setSettlementAmount(order.getOrderAmount().subtract(entity.getCommissionAmount()));
        return entity;
    }

    private BigDecimal calcCommissionByOrder(HotelOrder order)
    {
        HotelContract contract = hotelManageMapper.selectActiveContractByHotelId(order.getHotelId());
        if (contract == null) return BigDecimal.ZERO;
        BigDecimal rate = contract.getCommissionRate() == null ? BigDecimal.ZERO : contract.getCommissionRate();
        return order.getOrderAmount() == null ? BigDecimal.ZERO : order.getOrderAmount().multiply(rate).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    private BigDecimal calcCommissionAmount(BigDecimal settlementPrice, BigDecimal salePrice, BigDecimal commissionRate)
    {
        if (salePrice == null || commissionRate == null) return BigDecimal.ZERO;
        if (settlementPrice != null && salePrice.compareTo(settlementPrice) > 0) return salePrice.subtract(settlementPrice);
        return salePrice.multiply(commissionRate).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    private void savePendingLog(Long applyId, String type, String remark)
    {
        HotelPendingAuditLog log = new HotelPendingAuditLog();
        log.setApplyId(applyId);
        log.setOperateType(type);
        log.setOperateUserId(SecurityUtils.getUserId());
        log.setOperateUserName(SecurityUtils.getUsername());
        log.setOperateRemark(remark);
        log.setCreateBy(SecurityUtils.getUsername());
        hotelManageMapper.insertPendingAuditLog(log);
    }

    private void saveBindHistory(Long hotelId, Long oldBdUserId, String oldBdUserName, Long newBdUserId, String newBdUserName, String type, String reason)
    {
        HotelBdBindHistory entity = new HotelBdBindHistory();
        entity.setHotelId(hotelId);
        entity.setOldBdUserId(oldBdUserId);
        entity.setOldBdUserName(oldBdUserName);
        entity.setNewBdUserId(newBdUserId);
        entity.setNewBdUserName(newBdUserName);
        entity.setOperateType(type);
        entity.setOperateReason(reason);
        entity.setOperateUserId(SecurityUtils.getUserId());
        entity.setOperateUserName(SecurityUtils.getUsername());
        entity.setCreateBy(SecurityUtils.getUsername());
        hotelManageMapper.insertBindHistory(entity);
    }

    private void saveHotelImages(Long hotelId, List<HotelImage> imageList)
    {
        hotelManageMapper.deleteHotelImages(hotelId);
        if (imageList == null) return;
        int sort = 1;
        for (HotelImage image : imageList)
        {
            if (StringUtils.isBlank(image.getImageUrl())) continue;
            image.setHotelId(hotelId);
            image.setImageType(StringUtils.defaultIfBlank(image.getImageType(), "BANNER"));
            image.setSortNum(image.getSortNum() == null ? sort++ : image.getSortNum());
            image.setCreateBy(SecurityUtils.getUsername());
            hotelManageMapper.insertHotelImage(image);
        }
    }

    private List<HotelImage> mergeBannerImages(HotelPartner entity)
    {
        if (entity.getImageList() != null && !entity.getImageList().isEmpty()) return entity.getImageList();
        return buildBannerImages(entity.getBannerImages());
    }

    private List<HotelImage> buildBannerImages(String bannerImages)
    {
        if (StringUtils.isBlank(bannerImages)) return Collections.emptyList();
        java.util.ArrayList<HotelImage> result = new java.util.ArrayList<>();
        int sort = 1;
        for (String item : bannerImages.split(","))
        {
            if (StringUtils.isBlank(item)) continue;
            HotelImage image = new HotelImage();
            image.setImageType("BANNER");
            image.setImageUrl(item.trim());
            image.setImageName(item.trim());
            image.setSortNum(sort++);
            result.add(image);
        }
        return result;
    }

    private void saveRoomImages(Long roomTypeId, List<HotelImage> imageList)
    {
        hotelManageMapper.deleteRoomTypeImages(roomTypeId);
        if (imageList == null) return;
        int sort = 1;
        for (HotelImage image : imageList)
        {
            image.setRoomTypeId(roomTypeId);
            image.setSortNum(image.getSortNum() == null ? sort++ : image.getSortNum());
            image.setCreateBy(SecurityUtils.getUsername());
            hotelManageMapper.insertRoomTypeImage(image);
        }
    }

    private void saveOrderLog(HotelOrder order, String type, String beforeStatus, String afterStatus, String remark)
    {
        HotelOrderLog log = new HotelOrderLog();
        log.setOrderId(order.getId());
        log.setOrderNo(order.getOrderNo());
        log.setOperateType(type);
        log.setBeforeStatus(beforeStatus);
        log.setAfterStatus(afterStatus);
        log.setOperateUserId(SecurityUtils.getUserId());
        log.setOperateUserName(SecurityUtils.getUsername());
        log.setOperateRemark(remark);
        log.setCreateBy(SecurityUtils.getUsername());
        hotelManageMapper.insertOrderLog(log);
    }

    private void saveBillCheckLog(String billNo, String type, String remark)
    {
        com.yimamerchant.system.hotel.domain.HotelBillCheckLog log = new com.yimamerchant.system.hotel.domain.HotelBillCheckLog();
        HotelBill bill = hotelManageMapper.selectBillByNo(billNo);
        if (bill != null) log.setBillId(bill.getId());
        log.setBillNo(billNo);
        log.setOperateType(type);
        log.setOperateUserId(SecurityUtils.getUserId());
        log.setOperateUserName(SecurityUtils.getUsername());
        log.setOperateRemark(remark);
        log.setCreateBy(SecurityUtils.getUsername());
        hotelManageMapper.insertBillCheckLog(log);
    }

    private void saveConfigLog(Long configId, String key, String beforeValue, String afterValue)
    {
        HotelBusinessConfigLog log = new HotelBusinessConfigLog();
        log.setConfigId(configId);
        log.setConfigKey(key);
        log.setBeforeValue(beforeValue);
        log.setAfterValue(afterValue);
        log.setOperateUserId(SecurityUtils.getUserId());
        log.setOperateUserName(SecurityUtils.getUsername());
        log.setCreateBy(SecurityUtils.getUsername());
        hotelManageMapper.insertBusinessConfigLog(log);
    }

    private String buildNo(String prefix)
    {
        return prefix + DateUtils.dateTimeNow("yyyyMMddHHmmss") + IdUtils.fastSimpleUUID().substring(0, 6).toUpperCase();
    }

    private List<Long> parseLongList(String text)
    {
        if (StringUtils.isBlank(text)) return new java.util.ArrayList<>();
        java.util.ArrayList<Long> result = new java.util.ArrayList<>();
        for (String item : text.split(","))
        {
            if (StringUtils.isNotBlank(item)) result.add(Long.valueOf(item.trim()));
        }
        return result;
    }
}
