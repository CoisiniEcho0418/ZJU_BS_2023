package com.hwj.bs_backend.param;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class DeviceSqlProvider {

    /**
     * 动态生成查询设备列表的 SQL 语句
     *
     * @param deviceSearchRequest 查询条件
     * @param offset 偏移量（(page - 1) * pageSize）
     * @param pageSize 页的大小
     * @return SQL 语句
     */
    public String searchDevicesWithPaginationSql(
            @Param("searchRequest") DeviceSearchRequest deviceSearchRequest,
            @Param("offset") int offset, @Param("pageSize") int pageSize) {
        return new SQL() {{
            SELECT("device_id, device_name, device_type, device_description, registration_time, is_active");
            FROM("device");
            WHERE("user_id = #{searchRequest.userId}");
            if (deviceSearchRequest.getDeviceId() != null && !deviceSearchRequest.getDeviceId().equals("")) {
                AND().WHERE("device_id = #{searchRequest.deviceId}");
            }
            if (deviceSearchRequest.getDeviceName() != null && !deviceSearchRequest.getDeviceName().equals("")) {
                AND().WHERE("device_name LIKE CONCAT('%', #{searchRequest.deviceName}, '%')");
            }
            if (deviceSearchRequest.getDeviceType() != null) {
                AND().WHERE("device_type = #{searchRequest.deviceType}");
            }
            if(deviceSearchRequest.getIsActive()!= null){
                AND().WHERE("is_active = #{searchRequest.isActive}");
            }

            ORDER_BY("registration_time DESC");
            LIMIT("#{pageSize} OFFSET #{offset}");
        }}.toString();
    }

    /**
     * 计算分页获得数据数量
     *
     * @param deviceSearchRequest 查询请求条件
     * @return Total count
     */
    public String countDevicesSql(@Param("searchRequest") DeviceSearchRequest deviceSearchRequest) {
        return new SQL() {{
            SELECT("COUNT(*)");
            FROM("device");
            WHERE("user_id = #{searchRequest.userId}");

            if (deviceSearchRequest.getDeviceId() != null && !deviceSearchRequest.getDeviceId().equals("")) {
                AND().WHERE("device_id = #{searchRequest.deviceId}");
            }
            if (deviceSearchRequest.getDeviceName() != null && !deviceSearchRequest.getDeviceName().equals("")) {
                AND().WHERE("device_name LIKE CONCAT('%', #{searchRequest.deviceName}, '%')");
            }
            if (deviceSearchRequest.getDeviceType() != null) {
                AND().WHERE("device_type = #{searchRequest.deviceType}");
            }
            if(deviceSearchRequest.getIsActive()!= null){
                AND().WHERE("is_active = #{searchRequest.isActive}");
            }
        }}.toString();
    }
}
