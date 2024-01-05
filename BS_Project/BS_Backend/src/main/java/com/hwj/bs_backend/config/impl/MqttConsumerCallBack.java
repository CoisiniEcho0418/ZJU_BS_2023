package com.hwj.bs_backend.config.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hwj.bs_backend.mapper.DeviceMapper;
import com.hwj.bs_backend.mapper.MessageMapper;
import com.hwj.bs_backend.pojo.Message;
import com.hwj.bs_backend.pojo.MqttMessageData;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class MqttConsumerCallBack implements MqttCallback {
    private final DeviceMapper deviceMapper;
    private final MessageMapper messageMapper;

    // Constructor injection
    public MqttConsumerCallBack(DeviceMapper deviceMapper,MessageMapper messageMapper) {
        this.deviceMapper = deviceMapper;
        this.messageMapper=messageMapper;
    }


    private ObjectMapper objectMapper = new ObjectMapper();


    /**
     * 客户端断开连接的回调
     */
    @Override
    public void connectionLost(Throwable throwable) {
        System.out.println("与服务器断开连接，可重连");
    }

    /**
     * 消息到达的回调
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        try {
            String payload = new String(message.getPayload());

            // 解析 MQTT 消息
            MqttMessageData mqttMessageData = objectMapper.readValue(payload, MqttMessageData.class);

            // 查询设备名称对应的所有设备 ID
            List<String> deviceIds = deviceMapper.findDeviceIdsByDeviceName(mqttMessageData.getClientId());

            // 插入消息到数据库
            for (String deviceId : deviceIds) {
                Message deviceMessage = new Message();
                deviceMessage.setDeviceId(deviceId);
                deviceMessage.setTimestamp(new Date(mqttMessageData.getTimestamp()));
                deviceMessage.setMessageType(mqttMessageData.getAlert() == 1 ? 1 : 0);
                deviceMessage.setMessageContent(mqttMessageData.getInfo());
                deviceMessage.setLatitude(mqttMessageData.getLat());
                deviceMessage.setLongitude(mqttMessageData.getLng());
                deviceMessage.setValue(mqttMessageData.getValue());

                messageMapper.insertMessage(deviceMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(String.format("接收消息主题 : %s", topic));
        System.out.println(String.format("接收消息Qos : %d", message.getQos()));
        System.out.println(String.format("接收消息内容 : %s", new String(message.getPayload())));
        System.out.println(String.format("接收消息retained : %b", message.isRetained()));
    }

    /**
     * 消息发布成功的回调
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        System.out.println(String.format("接收消息成功"));
    }
}