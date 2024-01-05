package com.hwj.bs_backend.service.impl;

import com.hwj.bs_backend.mapper.MessageMapper;
import com.hwj.bs_backend.param.MessageCountResponse;
import com.hwj.bs_backend.param.TrackResponse;
import com.hwj.bs_backend.param.MessageResponse;
import com.hwj.bs_backend.pojo.Result;
import com.hwj.bs_backend.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    /**
     * 查询当前用户所有设备的总消息数
     *
     * @param userId 用户id
     * @return 所有设备的总消息数
     */
    @Override
    public Result<Integer> getTotalMessageCountByUserId(int userId) {
        try {
            int counts = messageMapper.getTotalMessageCountByUserId(userId);
            return Result.success(counts);
        } catch (Exception e) {
            log.error("查询当前用户所有设备的总消息数失败", e);
            return Result.error("查询当前用户所有设备的总消息数失败：" + e.getMessage());
        }
    }

    /**
     * 查询设备的历史轨迹信息
     *
     * @param deviceId 目标设备的id
     * @return 包含设备历史轨迹信息的数组
     */
    @Override
    public Result<List<TrackResponse>> getDeviceHistoryTrack(String deviceId) {
        try {
            // 调用mapper查询设备的历史轨迹信息
            List<TrackResponse> tracks = messageMapper.getDeviceHistoryTrack(deviceId);
            return Result.success(tracks);
        } catch (Exception e) {
            log.error("查询历史轨迹信息失败", e);
            return Result.error("查询历史轨迹信息失败：" + e.getMessage());
        }

    }

    /**
     * 查询某一设备的历史消息
     *
     * @param deviceId 目标设备的id
     * @return 设备历史消息列表
     */
    @Override
    public Result<List<MessageResponse>> getDeviceHistory(String deviceId) {
        try {
            List<MessageResponse> messages = messageMapper.getDeviceHistory(deviceId);
            return Result.success(messages);
        } catch (Exception e) {
            log.error("查询历史消息失败", e);
            return Result.error("查询历史消息失败：" + e.getMessage());
        }
    }

    /**
     * 查询用户所属设备最近七天的消息总数
     *
     * @param userId 用户id
     * @param today  当天的日期
     * @return 最近七天用户接收的消息数量
     */
    public Result<List<MessageCountResponse>> getReceivedMessageCount(Integer userId, Date today) {
        try {
            List<MessageCountResponse> messageCountLists = messageMapper.getReceivedMessageCount(userId, today);
            return Result.success(messageCountLists);
        } catch (Exception e) {
            log.error("查询用户所属设备最近七天的消息总数失败", e);
            return Result.error("查询用户所属设备最近七天的消息总数失败：" + e.getMessage());
        }
    }

}
