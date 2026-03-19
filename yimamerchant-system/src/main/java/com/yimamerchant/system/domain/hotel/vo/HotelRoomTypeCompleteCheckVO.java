package com.yimamerchant.system.domain.hotel.vo;

import java.util.List;

/**
 * 房型完整性校验结果 VO
 *
 * 对应接口文档 5.8 响应结构。
 *
 * 注意：按你的要求此类不生成 getter/setter，你可后续自行生成。
 */
public class HotelRoomTypeCompleteCheckVO
{
    /** 是否通过校验 */
    public boolean pass;

    /** 失败原因提示文案 */
    public String message;

    /** 缺失字段列表 */
    public List<String> missingFields;

    public boolean isPass() {
        return pass;
    }

    public void setPass(boolean pass) {
        this.pass = pass;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getMissingFields() {
        return missingFields;
    }

    public void setMissingFields(List<String> missingFields) {
        this.missingFields = missingFields;
    }
}

