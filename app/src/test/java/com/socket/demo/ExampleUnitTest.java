package com.socket.demo;

import com.socket.demo.net.NetUtil;
import com.socket.demo.net.STradeGateUserInfo;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        int[] reserve = new int[4];
        byte[] bytes = new byte[25];

        System.out.println(sizeof(new STradeGateUserInfo()));
        System.out.println(sizeof(reserve));
        System.out.println(sizeof(bytes));

    }


    private int sizeof(Object o){
       return NetUtil.sizeOf(o);
    }
}