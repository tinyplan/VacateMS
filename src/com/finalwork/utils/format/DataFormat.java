package com.finalwork.utils.format;

import com.finalwork.po.Student;
import com.finalwork.po.VacateVO;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class DataFormat {
    private static final String format = "yyyy-MM-dd";
    private static final String[] type = {"病假", "事假", "学院学生辅助工作", "其它"};

    /**
     * 根据提交时间判断学期
     *
     * @param submitTime 提交时间
     * @return 学期
     */
    public static String calTerm(String submitTime) throws ParseException {
        submitTime = submitTime.replaceAll("/", "-");

        String format = "yyyy-MM-dd";
        Date date = new SimpleDateFormat(format).parse(submitTime);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int term;

        StringBuilder formatDate = new StringBuilder();
        if (month < 9) {
            //若month小于9,判断为第二学期
            term = 2;
        } else {
            year += 1;
            term = 1;
        }
        formatDate.append(year - 1).append("-").append(year).append("-").append(term);
        return formatDate.toString();
    }

    /**
     * 计算星期
     *
     * @param dateString 日期字符串
     * @return 星期
     */
    public static Integer calWeek(String dateString) throws ParseException {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(parseToStamp(dateString)));
        int dayForWeek = 0;
        if(c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
            dayForWeek = 7;
        }else{
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        return dayForWeek;
    }

    /**
     * 重新编码
     *
     * @param source 源
     * @return 转换后的字符串
     */
    public static String reEncode(String source) {
        return new String(source.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
    }

    /**
     * 转换为时间戳
     *
     * @param dateString 日期字符串
     * @return 对应的时间戳
     */
    public static long parseToStamp(String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(dateString).getTime();
    }

    public static long parseToStamp(String format,String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(dateString).getTime();
    }

    /**
     * 转换为日期
     *
     * @param timeStamp 时间戳
     * @return 对应的日期字符串
     */
    public static String parseToDate(String timeStamp){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(new Long(timeStamp)));
    }

    public static String parseToDate(String format,String timeStamp){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(new Long(timeStamp)));
    }

    /**
     * 获取日期相隔天数
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 相隔天数
     */
    public static int calInterval(String startDate, String endDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        //若两日期相同,计算为1天
        long interval = 1 + (sdf.parse(endDate).getTime() - sdf.parse(startDate).getTime()) / (1000 * 60 * 60 * 24);
        return Integer.parseInt(String.valueOf(interval));
    }

    /**
     * 请假类型转换
     * @param typeIndex 类型序号
     */
    public static String switchType(Integer typeIndex) {
        return type[typeIndex];
    }

    /**
     * 包装数据
     * @param userId 用户ID
     * @param status 请假状态
     * @return map
     */
    public static HashMap<String,Object> wrapData(String userId, Integer status){
        HashMap<String,Object> map = new HashMap<>();
        /*map.put("prefix",prefix);
        map.put("tableName",prefix+"_vacate");
        map.put("tableId",prefix+"_id");*/
        map.put("userId",userId);
        map.put("status",status);
        return map;
    }

    public static List<VacateVO> formatList(List<VacateVO> vacates){
        for(VacateVO vacateVO:vacates){
            vacateVO.getVacate().formatDate();
        }
        return vacates;
    }
}
