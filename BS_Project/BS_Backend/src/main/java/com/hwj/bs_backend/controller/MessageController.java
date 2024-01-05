package com.hwj.bs_backend.controller;

import com.hwj.bs_backend.annotation.TokenRequired;
import com.hwj.bs_backend.param.MessageCountResponse;
import com.hwj.bs_backend.param.MessageResponse;
import com.hwj.bs_backend.param.TrackResponse;
import com.hwj.bs_backend.pojo.Result;
import com.hwj.bs_backend.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin // 允许跨域
@Slf4j
@RestController
@RequestMapping("message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    /**
     * 查询当前用户所有设备的总消息数
     *
     * @param userId 用户ID
     * @return 返回所有设备的总消息数量
     */
    @GetMapping("/total-count")
     @TokenRequired // 添加 TokenRequired 注解，表示需要 token 鉴权
    public Result<Integer> getTotalMessageCount(@RequestParam("user_id") int userId) {
        return messageService.getTotalMessageCountByUserId(userId);
    }

    /**
     * 查询设备的历史轨迹信息
     *
     * @param deviceId 目标设备的id
     * @return 响应结果
     */
    @GetMapping("/history-track/{device_id}")
    @TokenRequired // 添加 TokenRequired 注解，表示需要 token 鉴权
    public Result<List<TrackResponse>> getDeviceHistoryTrack(@PathVariable("device_id") String deviceId) {
        return messageService.getDeviceHistoryTrack(deviceId);
    }

    /**
     * 查询某一设备的历史消息
     *
     * @param deviceId 目标设备的id
     * @return 设备历史消息列表
     */
    @GetMapping("/device-history/{device_id}")
    @TokenRequired // 添加 TokenRequired 注解，表示需要 token 鉴权
    public Result<List<MessageResponse>> getDeviceHistory(
            @PathVariable("device_id") String deviceId) {
        return messageService.getDeviceHistory(deviceId);
    }

    /**
     * 查询最近七天接收的消息数
     *
     * @param userId 用户id
     * @return 响应结果
     */
    @GetMapping("/received-count")
    @TokenRequired // 添加 TokenRequired 注解，表示需要 token 鉴权
    public Result<List<MessageCountResponse>> getReceivedMessageCount(
            @RequestParam("user_id") Integer userId,
            @RequestParam("today") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date today) {
        return messageService.getReceivedMessageCount(userId, today);
    }

}
