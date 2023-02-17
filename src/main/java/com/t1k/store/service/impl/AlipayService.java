package com.t1k.store.service.impl;


import com.alibaba.fastjson2.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.t1k.store.vo.OrderVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AlipayService
{
    @Value("${alipay.url}")
    private String url;
    @Value("${alipay.appid}")
    private String appid;
    @Value("${alipay.privateKey}")
    private String privateKey;
    @Value("${alipay.publicKey}")
    private String publicKey;
    @Value("${alipay.notifyUrl}")
    private String notifyUrl;
    @Value("${alipay.returnUrl}")
    private String returnUrl;

    public String pay(Integer oid, String totalPrice)
    {
        AlipayClient alipayClient = new DefaultAlipayClient(url, appid, privateKey, "json", "UTF-8", publicKey, "RSA2");
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setNotifyUrl("");
        request.setReturnUrl(returnUrl);
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", oid);
        bizContent.put("total_amount", totalPrice);
        bizContent.put("subject", "支付宝沙盒支付测试");
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");
        //bizContent.put("time_expire", "2022-08-01 22:00:00");

        //// 商品明细信息，按需传入
        //JSONArray goodsDetail = new JSONArray();
        //JSONObject goods1 = new JSONObject();
        //goods1.put("goods_id", "goodsNo1");
        //goods1.put("goods_name", "子商品1");
        //goods1.put("quantity", 1);
        //goods1.put("price", 0.01);
        //goodsDetail.add(goods1);
        //bizContent.put("goods_detail", goodsDetail);

        //// 扩展信息，按需传入
        //JSONObject extendParams = new JSONObject();
        //extendParams.put("sys_service_provider_id", "2088511833207846");
        //bizContent.put("extend_params", extendParams);
        request.setBizContent(bizContent.toJSONString());
//        request.setBizContent(JSON.toJSONString(0));
        AlipayTradePagePayResponse response = null;
        String from = null;
        try{
            response = alipayClient.pageExecute(request);
            from = response.getBody();
        } catch(AlipayApiException e){
            e.printStackTrace();
        }
        assert response != null;
        if(response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
        return from;
    }
}
