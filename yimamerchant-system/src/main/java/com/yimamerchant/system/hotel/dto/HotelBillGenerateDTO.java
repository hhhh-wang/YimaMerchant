package com.yimamerchant.system.hotel.dto;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

public class HotelBillGenerateDTO
{
    private Long hotelId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date statementStartDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date statementEndDate;
    private String generateMode;

    public Long getHotelId() { return hotelId; }
    public void setHotelId(Long hotelId) { this.hotelId = hotelId; }
    public Date getStatementStartDate() { return statementStartDate; }
    public void setStatementStartDate(Date statementStartDate) { this.statementStartDate = statementStartDate; }
    public Date getStatementEndDate() { return statementEndDate; }
    public void setStatementEndDate(Date statementEndDate) { this.statementEndDate = statementEndDate; }
    public String getGenerateMode() { return generateMode; }
    public void setGenerateMode(String generateMode) { this.generateMode = generateMode; }
}
