package com.yimamerchant.system.hotel.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yimamerchant.common.annotation.Excel;
import com.yimamerchant.common.annotation.Excel.ColumnType;
import com.yimamerchant.common.core.domain.BaseEntity;

public class HotelPendingApply extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    @Excel(name = "申请ID", cellType = ColumnType.NUMERIC)
    private Long id;
    private String applyNo;
    @Excel(name = "酒店名称")
    private String hotelName;
    private String contactName;
    @Excel(name = "联系方式")
    private String contactPhone;
    private String provinceCode;
    private String provinceName;
    private String cityCode;
    private String cityName;
    private String districtCode;
    private String districtName;
    private String address;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private String coverImage;
    private String bannerImages;
    private String checkinTime;
    private String checkoutTime;
    private String hotelDesc;
    private String bookingNotice;
    private String cancelPolicy;
    private String invoiceNotice;
    private String parkingNotice;
    private String businessStatus;
    private String businessLicenseFiles;
    private String specialLicenseFiles;
    private String healthLicenseFiles;
    private String applyRemark;
    @Excel(name = "申请状态")
    private String applyStatus;
    private Long applicantId;
    @Excel(name = "申请人")
    private String applicantName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "申请时间", width = 20, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date applyTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;
    private Long auditUserId;
    private String auditUserName;
    private String rejectReason;
    private Long partnerHotelId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "最后操作时间", width = 20, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date lastOperateTime;
    private String beginApplyTime;
    private String endApplyTime;
    private HotelPartner partnerHotel;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getApplyNo() { return applyNo; }
    public void setApplyNo(String applyNo) { this.applyNo = applyNo; }
    public String getHotelName() { return hotelName; }
    public void setHotelName(String hotelName) { this.hotelName = hotelName; }
    public String getContactName() { return contactName; }
    public void setContactName(String contactName) { this.contactName = contactName; }
    public String getContactPhone() { return contactPhone; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }
    public String getProvinceCode() { return provinceCode; }
    public void setProvinceCode(String provinceCode) { this.provinceCode = provinceCode; }
    public String getProvinceName() { return provinceName; }
    public void setProvinceName(String provinceName) { this.provinceName = provinceName; }
    public String getCityCode() { return cityCode; }
    public void setCityCode(String cityCode) { this.cityCode = cityCode; }
    public String getCityName() { return cityName; }
    public void setCityName(String cityName) { this.cityName = cityName; }
    public String getDistrictCode() { return districtCode; }
    public void setDistrictCode(String districtCode) { this.districtCode = districtCode; }
    public String getDistrictName() { return districtName; }
    public void setDistrictName(String districtName) { this.districtName = districtName; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public BigDecimal getLongitude() { return longitude; }
    public void setLongitude(BigDecimal longitude) { this.longitude = longitude; }
    public BigDecimal getLatitude() { return latitude; }
    public void setLatitude(BigDecimal latitude) { this.latitude = latitude; }
    public String getCoverImage() { return coverImage; }
    public void setCoverImage(String coverImage) { this.coverImage = coverImage; }
    public String getBannerImages() { return bannerImages; }
    public void setBannerImages(String bannerImages) { this.bannerImages = bannerImages; }
    public String getCheckinTime() { return checkinTime; }
    public void setCheckinTime(String checkinTime) { this.checkinTime = checkinTime; }
    public String getCheckoutTime() { return checkoutTime; }
    public void setCheckoutTime(String checkoutTime) { this.checkoutTime = checkoutTime; }
    public String getHotelDesc() { return hotelDesc; }
    public void setHotelDesc(String hotelDesc) { this.hotelDesc = hotelDesc; }
    public String getBookingNotice() { return bookingNotice; }
    public void setBookingNotice(String bookingNotice) { this.bookingNotice = bookingNotice; }
    public String getCancelPolicy() { return cancelPolicy; }
    public void setCancelPolicy(String cancelPolicy) { this.cancelPolicy = cancelPolicy; }
    public String getInvoiceNotice() { return invoiceNotice; }
    public void setInvoiceNotice(String invoiceNotice) { this.invoiceNotice = invoiceNotice; }
    public String getParkingNotice() { return parkingNotice; }
    public void setParkingNotice(String parkingNotice) { this.parkingNotice = parkingNotice; }
    public String getBusinessStatus() { return businessStatus; }
    public void setBusinessStatus(String businessStatus) { this.businessStatus = businessStatus; }
    public String getBusinessLicenseFiles() { return businessLicenseFiles; }
    public void setBusinessLicenseFiles(String businessLicenseFiles) { this.businessLicenseFiles = businessLicenseFiles; }
    public String getSpecialLicenseFiles() { return specialLicenseFiles; }
    public void setSpecialLicenseFiles(String specialLicenseFiles) { this.specialLicenseFiles = specialLicenseFiles; }
    public String getHealthLicenseFiles() { return healthLicenseFiles; }
    public void setHealthLicenseFiles(String healthLicenseFiles) { this.healthLicenseFiles = healthLicenseFiles; }
    public String getApplyRemark() { return applyRemark; }
    public void setApplyRemark(String applyRemark) { this.applyRemark = applyRemark; }
    public String getApplyStatus() { return applyStatus; }
    public void setApplyStatus(String applyStatus) { this.applyStatus = applyStatus; }
    public Long getApplicantId() { return applicantId; }
    public void setApplicantId(Long applicantId) { this.applicantId = applicantId; }
    public String getApplicantName() { return applicantName; }
    public void setApplicantName(String applicantName) { this.applicantName = applicantName; }
    public Date getApplyTime() { return applyTime; }
    public void setApplyTime(Date applyTime) { this.applyTime = applyTime; }
    public Date getAuditTime() { return auditTime; }
    public void setAuditTime(Date auditTime) { this.auditTime = auditTime; }
    public Long getAuditUserId() { return auditUserId; }
    public void setAuditUserId(Long auditUserId) { this.auditUserId = auditUserId; }
    public String getAuditUserName() { return auditUserName; }
    public void setAuditUserName(String auditUserName) { this.auditUserName = auditUserName; }
    public String getRejectReason() { return rejectReason; }
    public void setRejectReason(String rejectReason) { this.rejectReason = rejectReason; }
    public Long getPartnerHotelId() { return partnerHotelId; }
    public void setPartnerHotelId(Long partnerHotelId) { this.partnerHotelId = partnerHotelId; }
    public Date getLastOperateTime() { return lastOperateTime; }
    public void setLastOperateTime(Date lastOperateTime) { this.lastOperateTime = lastOperateTime; }
    public String getBeginApplyTime() { return beginApplyTime; }
    public void setBeginApplyTime(String beginApplyTime) { this.beginApplyTime = beginApplyTime; }
    public String getEndApplyTime() { return endApplyTime; }
    public void setEndApplyTime(String endApplyTime) { this.endApplyTime = endApplyTime; }
    public HotelPartner getPartnerHotel() { return partnerHotel; }
    public void setPartnerHotel(HotelPartner partnerHotel) { this.partnerHotel = partnerHotel; }
}
