package com.ez08.trade.ui.trade;

public interface OptionsDelegate{
    void search(String code);
    void getMax(String code, String price);
    void submit(String code, String price, int single, String num, String bsflag);
}