package com.hwj.bs_backend.controller;


import com.hwj.bs_backend.config.MqttConsumerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MqttController {
    @Autowired
    private MqttConsumerConfig client;

    @Value("${mqtt.client.id}")
    private String clientId;

    @RequestMapping("/connect")
    @ResponseBody
    public String connect(){
        client.connect();
        return clientId + "连接到服务器";
    }

    @RequestMapping("/disConnect")
    @ResponseBody
    public String disConnect(){
        client.disConnect();
        return clientId + "与服务器断开连接";
    }
}