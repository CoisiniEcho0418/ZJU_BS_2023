package com.hwj.bs_backend.mapper;

import com.hwj.bs_backend.param.MessageCountResponse;
import com.hwj.bs_backend.param.MessageResponse;
import com.hwj.bs_backend.param.TrackResponse;
import com.hwj.bs_backend.pojo.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

@Mapper
public interface MessageMapper {
    /**
     * 查询当前用户所有设备的总消息数
     *
     * @param userId 用户ID
     * @return 所有设备的总消息数量
     */
    @Select("SELECT COUNT(*) FROM message WHERE device_id IN (SELECT device_id FROM device WHERE user_id = #{userId})")
    int getTotalMessageCountByUserId(@Param("userId") int userId);

    /**
     * 查询设备的历史轨迹信息
     *
     * @param deviceId 目标设备的id
     * @return 包含设备历史轨迹信息的数组
     */
    @Select("SELECT timestamp, latitude, longitude FROM message " +
            "WHERE device_id = #{deviceId} " +
            "ORDER BY timestamp DESC ")
    List<TrackResponse> getDeviceHistoryTrack(@Param("deviceId") String deviceId);

    /**
     * 查询某一设备的历史消息
     *
     * @param deviceId 目标设备的id
     * @return 设备历史消息列表
     */
    @Select("SELECT message_id, timestamp, message_type, message_content, latitude, longitude, value " +
            "FROM message " +
            "WHERE device_id = #{deviceId} " +
            "ORDER BY timestamp DESC")
    // 按照时间戳降序排序
    List<MessageResponse> getDeviceHistory(@Param("deviceId") String deviceId);

    /**
     * 查询用户所属设备最近七天的消息总数
     *
     * @param userId 用户id
     * @param today  当天的日期
     * @return 最近七天用户接收的消息数量
     */
    @Select("SELECT DATE(m.timestamp) AS date, " +
            "COUNT(*) AS count, " +
            "SUM(CASE WHEN m.message_type = 0 THEN 1 ELSE 0 END) AS normalCount, " +
            "SUM(CASE WHEN m.message_type = 1 THEN 1 ELSE 0 END) AS abnormalCount " +
            "FROM message m " +
            "JOIN device d ON m.device_id = d.device_id " +
            "WHERE d.user_id = #{userId} AND DATE(m.timestamp) BETWEEN DATE_SUB(#{today}, INTERVAL 6 DAY) AND #{today} " +
            "GROUP BY DATE(m.timestamp)" +
            "ORDER BY date DESC")
    List<MessageCountResponse> getReceivedMessageCount(@Param("userId") Integer userId, @Param("today") Date today);

    /**
     * 新增消息
     *
     * @param message 封装了新增消息数据的对象
     */
    @Insert("INSERT INTO message (device_id, timestamp, message_type, message_content, latitude, longitude, value) " +
            "VALUES (#{message.deviceId}, #{message.timestamp}, #{message.messageType}, " +
            "#{message.messageContent}, #{message.latitude}, #{message.longitude}, #{message.value})")
    void insertMessage(@Param("message") Message message);
}
