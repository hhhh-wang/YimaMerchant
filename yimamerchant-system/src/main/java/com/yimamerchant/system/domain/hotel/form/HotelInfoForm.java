package com.yimamerchant.system.domain.hotel.form;

import java.math.BigDecimal;
import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * 酒店信息保存表单
 *
 * 注意：按你的要求此类不生成 getter/setter，你可后续自行生成。
 */
public class HotelInfoForm
{
    @NotBlank(message = "酒店名称不能为空")
    @Size(max = 50, message = "酒店名称最多50字")
    public String hotelName;

    @NotBlank(message = "酒店封面图不能为空")
    public String hotelCover;

    public List<String> hotelImages;

    @NotBlank(message = "联系电话不能为空")
    @Size(max = 20, message = "联系电话长度不能超过20个字符")
    @Pattern(
        regexp = "^(1\\d{10}|0\\d{2,3}-?\\d{7,8})$",
        message = "联系电话格式不正确"
    )
    public String phone;

    @NotBlank(message = "省编码不能为空")
    public String provinceCode;

    @NotBlank(message = "市编码不能为空")
    public String cityCode;

    @NotBlank(message = "区编码不能为空")
    public String districtCode;

    @NotBlank(message = "详细地址不能为空")
    @Size(max = 255, message = "详细地址最多255字")
    public String address;

    public BigDecimal longitude;

    public BigDecimal latitude;

    @NotBlank(message = "入住时间不能为空")
    public String checkInTime;

    @NotBlank(message = "离店时间不能为空")
    public String checkOutTime;

    public String intro;

    public String bookingNotice;

    public String cancelRule;

    public String invoiceDesc;

    public String parkingDesc;

    @NotNull(message = "酒店状态不能为空")
    @NotBlank(message = "酒店状态不能为空")
    public String status;

    public String remark;

    public @NotBlank(message = "酒店名称不能为空") @Size(max = 50, message = "酒店名称最多50字") String getHotelName() {
        return hotelName;
    }

    public void setHotelName(@NotBlank(message = "酒店名称不能为空") @Size(max = 50, message = "酒店名称最多50字") String hotelName) {
        this.hotelName = hotelName;
    }

    public @NotBlank(message = "酒店封面图不能为空") String getHotelCover() {
        return hotelCover;
    }

    public void setHotelCover(@NotBlank(message = "酒店封面图不能为空") String hotelCover) {
        this.hotelCover = hotelCover;
    }

    public List<String> getHotelImages() {
        return hotelImages;
    }

    public void setHotelImages(List<String> hotelImages) {
        this.hotelImages = hotelImages;
    }

    public @NotBlank(message = "联系电话不能为空") @Size(max = 20, message = "联系电话长度不能超过20个字符") @Pattern(
            regexp = "^(1\\d{10}|0\\d{2,3}-?\\d{7,8})$",
            message = "联系电话格式不正确"
    ) String getPhone() {
        return phone;
    }

    public void setPhone(@NotBlank(message = "联系电话不能为空") @Size(max = 20, message = "联系电话长度不能超过20个字符") @Pattern(
            regexp = "^(1\\d{10}|0\\d{2,3}-?\\d{7,8})$",
            message = "联系电话格式不正确"
    ) String phone) {
        this.phone = phone;
    }

    public @NotBlank(message = "省编码不能为空") String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(@NotBlank(message = "省编码不能为空") String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public @NotBlank(message = "市编码不能为空") String getCityCode() {
        return cityCode;
    }

    public void setCityCode(@NotBlank(message = "市编码不能为空") String cityCode) {
        this.cityCode = cityCode;
    }

    public @NotBlank(message = "区编码不能为空") String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(@NotBlank(message = "区编码不能为空") String districtCode) {
        this.districtCode = districtCode;
    }

    public @NotBlank(message = "详细地址不能为空") @Size(max = 255, message = "详细地址最多255字") String getAddress() {
        return address;
    }

    public void setAddress(@NotBlank(message = "详细地址不能为空") @Size(max = 255, message = "详细地址最多255字") String address) {
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

    public @NotBlank(message = "入住时间不能为空") String getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(@NotBlank(message = "入住时间不能为空") String checkInTime) {
        this.checkInTime = checkInTime;
    }

    public @NotBlank(message = "离店时间不能为空") String getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(@NotBlank(message = "离店时间不能为空") String checkOutTime) {
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

    public @NotNull(message = "酒店状态不能为空") @NotBlank(message = "酒店状态不能为空") String getStatus() {
        return status;
    }

    public void setStatus(@NotNull(message = "酒店状态不能为空") @NotBlank(message = "酒店状态不能为空") String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

