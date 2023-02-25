package com.example;

import com.example.notUsedApp.ListToString;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class ListToStringTest {
    /**
     *  {"a", "b","cfd"}  to "a:b:cfd"
     */

    @Test
    @DisplayName("测试： 数组转字符串 ")

    public  void listToString() {
        List<String> ls = Arrays.asList("a", "b","cfd");

        String st = ListToString.getStringFromList(ls);

        assertNotNull(st.length());
        assertThat(st.contains(":"));
        assertThat(st.contains(":"));
        log.info("st =" + st);
    }
}
