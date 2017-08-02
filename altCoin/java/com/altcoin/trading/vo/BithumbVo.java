package com.altcoin.trading.vo;

import java.util.ArrayList;

import lombok.Data;

@Data
public class BithumbVo {

    private Object data; // �쟾泥� �뜲�씠�꽣
    private String timestamp; // �쁽�옱 �떆媛�
    private String order_currency; // 二쇰Ц�솕�룓 �떒�쐞
    private String payment_currency; // 寃곗젣 �솕�룓�떒�쐞
    private ArrayList<Object> bids; // 援щℓ�슂泥�
    private ArrayList<Object> asks; // �뙋留ㅼ슂泥�
    private String quantity; // 媛��긽�솕�룓 �닔�웾
    private String price; // 媛�寃�
    private String transaction_date; // �긽�깭肄붾뱶
    private String type; // �긽�깭肄붾뱶
    private String units_traded; // �긽�깭肄붾뱶
    private String status; // �긽�깭肄붾뱶

    private String coin;

}
