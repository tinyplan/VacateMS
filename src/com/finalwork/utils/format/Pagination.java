package com.finalwork.utils.format;

import com.finalwork.po.VacateVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Pagination {

    /**
     * 逻辑分页
     * @param dataList 数据列表
     * @param pageSize 页面容量
     */
    public static HashMap<Integer, List<VacateVO>> getLogicPagination(List<VacateVO> dataList, Integer pageSize) {
        HashMap<Integer, List<VacateVO>> dataMap = new HashMap<>();
        List<VacateVO> temp = null;
        int pageNo = 1;
        int offsetIndex = pageNo * pageSize;
        while (offsetIndex <= dataList.size()) {
            temp = new ArrayList<>();
            for (int i = offsetIndex - pageSize; i < offsetIndex; i++) {
                temp.add(dataList.get(i));
            }
            dataMap.put(pageNo, temp);
            pageNo++;
            offsetIndex = pageNo * pageSize;
        }
        //扫尾处理
        //计算剩余
        int rest = dataList.size() - (pageNo - 1) * pageSize;
        if (rest > 0) {
            temp = new ArrayList<>();
            for (int i = 0; i < rest; i++) {
                temp.add(dataList.get(offsetIndex - pageSize + i));
            }
            dataMap.put(pageNo, temp);
        }
        return dataMap;
    }
}
