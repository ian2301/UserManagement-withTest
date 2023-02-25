package com.example.notUsedApp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**    **- 未使用 -**
 * 数组转换为字符串： ["a","b"]  to a:b
 */
public class ListToString {


    public static String getStringFromList(List<String> inputList) {

        //用 for
        String tags = "";
        for (int i = 0; i < inputList.size(); i++) {
            tags += inputList.get(i) + (i == inputList.size() - 1 ? "" : ":");
        }


//         用iterator 实现  tested  ->OK
        String tags1 = "";
        Iterator iterator = inputList.iterator();
        while (iterator.hasNext()) {
            System.out.println("iterator: " + iterator);
            tags1 += iterator.next() + (iterator.hasNext() ? ":" : "");
        }

        // 用 String.join -- tested OK  | 只能处理字符或字符串
        String tags2 = String.join(":", inputList);

        // 用 stream().map().collect  | 能处理 object
        String tags3 = inputList.stream().map(Object::toString).collect(Collectors.joining(":"));

        return tags;

    }
}
