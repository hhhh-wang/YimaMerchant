package com.yimamerchant.system.domain.hotel;

import java.math.BigDecimal;
import com.yimamerchant.common.core.domain.BaseEntity;

/**
 * 酒店信息表 hotel_info
 *
 * 注意：按你的要求此类不生成 getter/setter，你可后续自行生成。
 */
public class HotelInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    public Long id;

    /** 商家编号（当前项目暂用 deptId 作为商家隔离标识） */
    public Long merchantId;

    /** 酒店名称 */
    public String hotelName;

    /** 酒店封面图 */
    public String hotelCover;

    /** 酒店轮播图(JSON数组字符串) */
    public String hotelImages;

    /** 联系电话 */
    public String phone;

    /** 省编码 */
    public String provinceCode;

    /** 市编码 */
    public String cityCode;

    /** 区编码 */
    public String districtCode;

    /** 详细地址 */
    public String address;

    /** 经度 */
    public BigDecimal longitude;

    /** 纬度 */
    public BigDecimal latitude;

    /** 入住时间 */
    public String checkInTime;

    /** 离店时间 */
    public String checkOutTime;

    /** 酒店简介 */
    public String intro;

    /** 预订须知 */
    public String bookingNotice;

    /** 取消规则 */
    public String cancelRule;

    /** 开票说明 */
    public String invoiceDesc;

    /** 停车说明 */
    public String parkingDesc;

    /** 酒店状态 */
    public String status;

    /** 删除标记（0存在 2删除） */
    public String delFlag;

    /** 备注 */
    public String remark;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelCover() {
        return hotelCover;
    }

    public void setHotelCover(String hotelCover) {
        this.hotelCover = hotelCover;
    }

    public String getHotelImages() {
        return hotelImages;
    }

    public void setHotelImages(String hotelImages) {
        this.hotelImages = hotelImages;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }

    public String getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(String checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getBookingNotice() {
        return bookingNotice;
    }

    public void setBookingNotice(String bookingNotice) {
        this.bookingNotice = bookingNotice;
    }

    public String getCancelRule() {
        return cancelRule;
    }

    public void setCancelRule(String cancelRule) {
        this.cancelRule = cancelRule;
    }

    public String getInvoiceDesc() {
        return invoiceDesc;
    }

    public void setInvoiceDesc(String invoiceDesc) {
        this.invoiceDesc = invoiceDesc;
    }

    public String getParkingDesc() {
        return parkingDesc;
    }

    public void setParkingDesc(String parkingDesc) {
        this.parkingDesc = parkingDesc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }
}

