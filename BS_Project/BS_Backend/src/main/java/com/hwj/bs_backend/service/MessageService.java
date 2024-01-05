package com.hwj.bs_backend.service;


import com.hwj.bs_backend.param.MessageCountResponse;
import com.hwj.bs_backend.param.MessageResponse;
import com.hwj.bs_backend.param.TrackResponse;
import com.hwj.bs_backend.pojo.Result;

import java.util.Date;
import java.util.List;

public interface MessageService {

    /**
     * 查询当前用户所有设备的总消息数
     *
     * @param userId 用户ID
     * @return 所有设备的总消息数量
     */
    Result<Integer> getTotalMessageCountByUserId(int userId);

    /**
     * 查询设备的历史轨迹信息
     *
     * @param deviceId 目标设备的id
     * @return 包含设备历史轨迹信息的数组
     */
    Result<List<TrackResponse>> getDeviceHistoryTrack(String deviceId);

    /**
     * 查询某一设备的历史消息
     *
     * @param deviceId 目标设备的id
     * @return 设备历史消息列表
     */
    Result<List<MessageResponse>> getDeviceHistory(String deviceId);

    /**
     * 查询用户所属设备最近七天的消息总数
     *
     * @param userId 用户id
     * @param today  当天的日期
     * @return 最近七天用户接收的消息数量
     */
    Result<List<MessageCountResponse>> getReceivedMessageCount(Integer userId, Date today);
}
