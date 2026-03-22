package com.yimamerchant.system.hotel.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yimamerchant.common.annotation.Excel;
import com.yimamerchant.common.annotation.Excel.ColumnType;
import com.yimamerchant.common.core.domain.BaseEntity;

public class HotelPartner extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    @Excel(name = "酒店ID", cellType = ColumnType.NUMERIC)
    private Long hotelId;
    private String hotelCode;
    @Excel(name = "酒店名称")
    private String hotelName;
    private String hotelNameEn;
    private String brandName;
    private String starLevel;
    private String contactName;
    private String contactPhone;
    private String contactEmail;
    private String provinceCode;
    private String provinceName;
    private String cityCode;
    private String cityName;
    private String districtCode;
    private String districtName;
    private String address;
    private BigDecimal longitude;
    private BigDecimal latitude;
    @Excel(name = "合作状态")
    private String cooperateStatus;
    private Long sourceApplyId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "签约时间", width = 20, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date signDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date contractStartDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date contractEndDate;
    private String commissionMode;
    private BigDecimal commissionRate;
    private BigDecimal markupRate;
    private Long bdUserId;
    @Excel(name = "所属BD")
    private String bdUserName;
    private String accountStatus;
    private String statusReason;
    @Excel(name = "本月订单数", cellType = ColumnType.NUMERIC)
    private Integer monthlyOrderCount;
    @Excel(name = "本月销售额")
    private BigDecimal monthlySaleAmount;
    private String beginSignDate;
    private String endSignDate;
    private String hotelLogo;
    private String coverImage;
    private String hotelDesc;
    private String serviceTags;
    private String trafficInfo;
    private String customerServiceTime;
    private String checkinTime;
    private String checkoutTime;
    private String childPolicy;
    private String petPolicy;
    private String cancelPolicy;
    private String saleStatus;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date syncTime;
    private List<HotelContract> contracts;
    private List<HotelAccount> accounts;
    private List<HotelImage> imageList;

    public Long getHotelId() { return hotelId; }
    public void setHotelId(Long hotelId) { this.hotelId = hotelId; }
    public String getHotelCode() { return hotelCode; }
    public void setHotelCode(String hotelCode) { this.hotelCode = hotelCode; }
    public String getHotelName() { return hotelName; }
    public void setHotelName(String hotelName) { this.hotelName = hotelName; }
    public String getHotelNameEn() { return hotelNameEn; }
    public void setHotelNameEn(String hotelNameEn) { this.hotelNameEn = hotelNameEn; }
    public String getBrandName() { return brandName; }
    public void setBrandName(String brandName) { this.brandName = brandName; }
    public String getStarLevel() { return starLevel; }
    public void setStarLevel(String starLevel) { this.starLevel = starLevel; }
    public String getContactName() { return contactName; }
    public void setContactName(String contactName) { this.contactName = contactName; }
    public String getContactPhone() { return contactPhone; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }
    public String getContactEmail() { return contactEmail; }
    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }
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
    public String getCooperateStatus() { return cooperateStatus; }
    public void setCooperateStatus(String cooperateStatus) { this.cooperateStatus = cooperateStatus; }
    public Long getSourceApplyId() { return sourceApplyId; }
    public void setSourceApplyId(Long sourceApplyId) { this.sourceApplyId = sourceApplyId; }
    public Date getSignDate() { return signDate; }
    public void setSignDate(Date signDate) { this.signDate = signDate; }
    public Date getContractStartDate() { return contractStartDate; }
    public void setContractStartDate(Date contractStartDate) { this.contractStartDate = contractStartDate; }
    public Date getContractEndDate() { return contractEndDate; }
    public void setContractEndDate(Date contractEndDate) { this.contractEndDate = contractEndDate; }
    public String getCommissionMode() { return commissionMode; }
    public void setCommissionMode(String commissionMode) { this.commissionMode = commissionMode; }
    public BigDecimal getCommissionRate() { return commissionRate; }
    public void setCommissionRate(BigDecimal commissionRate) { this.commissionRate = commissionRate; }
    public BigDecimal getMarkupRate() { return markupRate; }
    public void setMarkupRate(BigDecimal markupRate) { this.markupRate = markupRate; }
    public Long getBdUserId() { return bdUserId; }
    public void setBdUserId(Long bdUserId) { this.bdUserId = bdUserId; }
    public String getBdUserName() { return bdUserName; }
    public void setBdUserName(String bdUserName) { this.bdUserName = bdUserName; }
    public String getAccountStatus() { return accountStatus; }
    public void setAccountStatus(String accountStatus) { this.accountStatus = accountStatus; }
    public String getStatusReason() { return statusReason; }
    public void setStatusReason(String statusReason) { this.statusReason = statusReason; }
    public Integer getMonthlyOrderCount() { return monthlyOrderCount; }
    public void setMonthlyOrderCount(Integer monthlyOrderCount) { this.monthlyOrderCount = monthlyOrderCount; }
    public BigDecimal getMonthlySaleAmount() { return monthlySaleAmount; }
    public void setMonthlySaleAmount(BigDecimal monthlySaleAmount) { this.monthlySaleAmount = monthlySaleAmount; }
    public String getBeginSignDate() { return beginSignDate; }
    public void setBeginSignDate(String beginSignDate) { this.beginSignDate = beginSignDate; }
    public String getEndSignDate() { return endSignDate; }
    public void setEndSignDate(String endSignDate) { this.endSignDate = endSignDate; }
    public String getHotelLogo() { return hotelLogo; }
    public void setHotelLogo(String hotelLogo) { this.hotelLogo = hotelLogo; }
    public String getCoverImage() { return coverImage; }
    public void setCoverImage(String coverImage) { this.coverImage = coverImage; }
    public String getHotelDesc() { return hotelDesc; }
    public void setHotelDesc(String hotelDesc) { this.hotelDesc = hotelDesc; }
    public String getServiceTags() { return serviceTags; }
    public void setServiceTags(String serviceTags) { this.serviceTags = serviceTags; }
    public String getTrafficInfo() { return trafficInfo; }
    public void setTrafficInfo(String trafficInfo) { this.trafficInfo = trafficInfo; }
    public String getCustomerServiceTime() { return customerServiceTime; }
    public void setCustomerServiceTime(String customerServiceTime) { this.customerServiceTime = customerServiceTime; }
    public String getCheckinTime() { return checkinTime; }
    public void setCheckinTime(String checkinTime) { this.checkinTime = checkinTime; }
    public String getCheckoutTime() { return checkoutTime; }
    public void setCheckoutTime(String checkoutTime) { this.checkoutTime = checkoutTime; }
    public String getChildPolicy() { return childPolicy; }
    public void setChildPolicy(String childPolicy) { this.childPolicy = childPolicy; }
    public String getPetPolicy() { return petPolicy; }
    public void setPetPolicy(String petPolicy) { this.petPolicy = petPolicy; }
    public String getCancelPolicy() { return cancelPolicy; }
    public void setCancelPolicy(String cancelPolicy) { this.cancelPolicy = cancelPolicy; }
    public String getSaleStatus() { return saleStatus; }
    public void setSaleStatus(String saleStatus) { this.saleStatus = saleStatus; }
    public Date getSyncTime() { return syncTime; }
    public void setSyncTime(Date syncTime) { this.syncTime = syncTime; }
    public List<HotelContract> getContracts() { return contracts; }
    public void setContracts(List<HotelContract> contracts) { this.contracts = contracts; }
    public List<HotelAccount> getAccounts() { return accounts; }
    public void setAccounts(List<HotelAccount> accounts) { this.accounts = accounts; }
    public List<HotelImage> getImageList() { return imageList; }
    public void setImageList(List<HotelImage> imageList) { this.imageList = imageList; }
}
