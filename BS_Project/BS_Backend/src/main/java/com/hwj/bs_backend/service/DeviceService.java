package com.hwj.bs_backend.service;

import com.hwj.bs_backend.param.*;
import com.hwj.bs_backend.pojo.Device;
import com.hwj.bs_backend.pojo.PageResult;
import com.hwj.bs_backend.pojo.Result;

import java.util.Date;
import java.util.List;

public interface DeviceService {

    /**
     * 获取某个设备信息
     *
     * @param deviceId 设备ID
     * @return 返回设备信息
     */
    Result<Device> getDeviceInfo(String deviceId);

    /**
     * 获取用户设备列表
     *
     * @param userId 用户ID
     * @return 返回用户设备信息列表
     */
    Result<DeviceListWithCount> getUserDevices(Integer userId);

    /**
     * 新增设备
     *
     * @param request 新增设备参数类
     * @return 返回新增设备处理结果
     */
    Result<String> addDevice(DeviceAddRequest request);

    /**
     * 修改设备配置
     *
     * @param request  修改设备配置参数类
     * @param deviceId 设备id
     * @return 返回修改设备配置处理结果
     */
    Result<String> updateDevice(String deviceId, DeviceUpdateRequest request);

    /**
     * 获取最近七天新增设备数量
     *
     * @param userId 用户id
     * @param today  当天的日期
     * @return 最近七天新增的设备列表
     */
    Result<List<DeviceCountResponse>> getNewDevicesCount(Integer userId, Date today);

    /**
     * 根据查询条件返回分页数据
     *
     * @param deviceSearchRequest 查询条件
     * @param page                 当前的页号
     * @param pageSize             页的大小
     * @return 分页结果(带当前页号和所有符合条件的结果数量+当前页号+页的大小)
     */
    PageResult<List<DeviceListResponse>> searchDevicesWithPagination(
            DeviceSearchRequest deviceSearchRequest, int page, int pageSize);

    /**
     * 删除设备
     *
     * @param deviceId 设备id
     * @return 返回删除结果
     */
    Result<String> deleteDevice(String deviceId);
}
