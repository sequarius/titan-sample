package com.sequarius.titan.sample.common.util;

import com.sequarius.titan.sample.common.domain.Response;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

/**
 * 身份证号校验
 *
 * @author JiangDingMing *
 * @since 25/03/2020
 */
public class IDCardUtil {

    // wi =2(n-1)(mod 11)
    static final int[] wi = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1};
    // verify digit
    static final int[] vi = {1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2};
    private static int[] ai = new int[18];
    private static String[] _areaCode = {"11", "12", "13", "14", "15", "21",
            "22", "23", "31", "32", "33", "34", "35", "36", "37", "41", "42",
            "43", "44", "45", "46", "50", "51", "52", "53", "54", "61", "62",
            "63", "64", "65", "71", "81", "82", "91"};
    private static HashMap<String, Integer> dateMap;
    private static HashMap<String, String> areaCodeMap;

    static {
        dateMap = new HashMap<String, Integer>();
        dateMap.put("01", 31);
        dateMap.put("02", null);
        dateMap.put("03", 31);
        dateMap.put("04", 30);
        dateMap.put("05", 31);
        dateMap.put("06", 30);
        dateMap.put("07", 31);
        dateMap.put("08", 31);
        dateMap.put("09", 30);
        dateMap.put("10", 31);
        dateMap.put("11", 30);
        dateMap.put("12", 31);
        areaCodeMap = new HashMap<String, String>();
        for (String code : _areaCode) {
            areaCodeMap.put(code, null);
        }
    }

    // 验证身份证位数,15位和18位身份证
    public static Response<String> verifyLength(String code) {
        int length = code.length();
        if (length == 15 || length == 18) {
            return Response.success(null);
        } else {
            return Response.fail("错误：输入的身份证号不是15位和18位的");
        }
    }

    // 判断地区码
    public static Response<String> verifyAreaCode(String code) {
        String areaCode = code.substring(0, 2);
        // Element child= _areaCodeElement.getChild("_"+areaCode);
        if (areaCodeMap.containsKey(areaCode)) {
            return Response.success(null);
        } else {
            return Response.fail("错误：输入的身份证号的地区码(1-2位)[" + areaCode
                    + "]不符合中国行政区划分代码规定(GB/T2260-1999)");
        }
    }

    public static Date getBirthDate(String code){
        String month = code.substring(10, 12);
        String day = code.substring(12, 14);
        String year = code.substring(6, 10);
        LocalDate localDate = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    // 判断月份和日期
    public static Response<String> verifyBirthdayCode(String code) {
        // 验证月份
        String month = code.substring(10, 12);
        boolean isEighteenCode = (18 == code.length());
        if (!dateMap.containsKey(month)) {
            return Response.fail("错误：输入的身份证号"
                    + (isEighteenCode ? "(11-12位)" : "(9-10位)") + "不存在["
                    + month + "]月份,不符合要求(GB/T7408)");
        }
        // 验证日期
        String dayCode = code.substring(12, 14);
        Integer day = dateMap.get(month);
        String yearCode = code.substring(6, 10);
        Integer year = Integer.valueOf(yearCode);

        // 非2月的情况
        if (day != null) {
            if (Integer.parseInt(dayCode) > day || Integer.parseInt(dayCode) < 1) {
                return Response.fail("错误：输入的身份证号"
                        + (isEighteenCode ? "(13-14位)" : "(11-13位)") + "["
                        + dayCode + "]号不符合小月1-30天大月1-31天的规定(GB/T7408)");
            }
        }
        // 2月的情况
        else {
            // 闰月的情况
            if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
                if (Integer.parseInt(dayCode) > 29
                        || Integer.parseInt(dayCode) < 1) {
                    return Response.fail("错误：输入的身份证号"
                            + (isEighteenCode ? "(13-14位)" : "(11-13位)") + "["
                            + dayCode + "]号在" + year
                            + "闰年的情况下未符合1-29号的规定(GB/T7408)");
                }
            }
            // 非闰月的情况
            else {
                if (Integer.parseInt(dayCode) > 28
                        || Integer.parseInt(dayCode) < 1) {
                    return Response.fail("错误：输入的身份证号"
                            + (isEighteenCode ? "(13-14位)" : "(11-13位)") + "["
                            + dayCode + "]号在" + year
                            + "平年的情况下未符合1-28号的规定(GB/T7408)");                }
            }
        }
        return Response.success(null);
    }

    // 验证身份除了最后位其他的是否包含字母
    public static Response<String> containsAllNumber(String code) {
        String str = "";
        if (code.length() == 15) {
            str = code.substring(0, 15);
        } else if (code.length() == 18) {
            str = code.substring(0, 17);
        }
        char[] ch = str.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            if (!(ch[i] >= '0' && ch[i] <= '9')) {
                return Response.fail("错误：输入的身份证号第" + (i + 1) + "位包含字母");                   }
        }
        return Response.success(null);
    }

    // 验证身份证
    public static Response<String> verify(String idcard) {
        // 验证身份证位数,15位和18位身份证
        Response<String> response = verifyLength(idcard);
        if (!response.getResult()) {
            return response;
        }
        // 验证身份除了最后位其他的是否包含字母
        response = ontainsAllNumber(idcard);
        if (!response.getResult()) {
            return response;
        }

        // 如果是15位的就转成18位的身份证
        String eifhteencard = "";
        if (idcard.length() == 15) {
            eifhteencard = uptoeighteen(idcard);
        } else {
            eifhteencard = idcard;
        }
        // 验证身份证的地区码
        response = verifyAreaCode(eifhteencard);
        if (!response.getResult()) {
            return response;
        }
        // 判断月份和日期
        response = verifyBirthdayCode(eifhteencard);
        if (!response.getResult()) {
            return response;
        }
        // 验证18位校验码,校验码采用ISO 7064：1983，MOD 11-2 校验码系统
        response = verifyMOD(eifhteencard);
        if (!response.getResult()) {
            return response;
        }
        return response;
    }

    private static Response<String> ontainsAllNumber(String code) {
        String str = "";
        if (code.length() == 15) {
            str = code.substring(0, 15);
        } else if (code.length() == 18) {
            str = code.substring(0, 17);
        }
        char[] ch = str.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            if (!(ch[i] >= '0' && ch[i] <= '9')) {
                return Response.fail("错误：输入的身份证号第" + (i + 1) + "位包含字母");
            }
        }
        return Response.success(null);
    }

    // 验证18位校验码,校验码采用ISO 7064：1983，MOD 11-2 校验码系统
    public static Response<String> verifyMOD(String code) {
        String verify = code.substring(17, 18);
        if ("x".equals(verify)) {
            code = code.replaceAll("x", "X");
            verify = "X";
        }
        String verifyIndex = getVerify(code);
        if (verify.equals(verifyIndex)) {
            return Response.success(null);
        }

        return Response.fail("错误：输入的身份证号最末尾的数字验证码错误");
    }

    // 获得校验位
    public static String getVerify(String eightcardid) {
        int remaining = 0;

        if (eightcardid.length() == 18) {
            eightcardid = eightcardid.substring(0, 17);
        }

        if (eightcardid.length() == 17) {
            int sum = 0;
            for (int i = 0; i < 17; i++) {
                String k = eightcardid.substring(i, i + 1);
                ai[i] = Integer.parseInt(k);
            }

            for (int i = 0; i < 17; i++) {
                sum = sum + wi[i] * ai[i];
            }
            remaining = sum % 11;
        }

        return remaining == 2 ? "X" : String.valueOf(vi[remaining]);
    }

    // 15位转18位身份证
    public static String uptoeighteen(String fifteencardid) {
        String eightcardid = fifteencardid.substring(0, 6);
        eightcardid = eightcardid + "19";
        eightcardid = eightcardid + fifteencardid.substring(6, 15);
        eightcardid = eightcardid + getVerify(eightcardid);
        return eightcardid;
    }
}
