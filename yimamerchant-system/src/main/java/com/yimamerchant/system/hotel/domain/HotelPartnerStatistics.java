package com.yimamerchant.system.hotel.domain;

import java.math.BigDecimal;

public class HotelPartnerStatistics
{
    private Long totalCount;
    private Long normalCount;
    private Long monthlyNewCount;
    private BigDecimal monthlySalesAmount;

    public Long getTotalCount() { return totalCount; }
    public void setTotalCount(Long totalCount) { this.totalCount = totalCount; }
    public Long getNormalCount() { return normalCount; }
    public void setNormalCount(Long normalCount) { this.normalCount = normalCount; }
    public Long getMonthlyNewCount() { return monthlyNewCount; }
    public void setMonthlyNewCount(Long monthlyNewCount) { this.monthlyNewCount = monthlyNewCount; }
    public BigDecimal getMonthlySalesAmount() { return monthlySalesAmount; }
    public void setMonthlySalesAmount(BigDecimal monthlySalesAmount) { this.monthlySalesAmount = monthlySalesAmount; }
}
