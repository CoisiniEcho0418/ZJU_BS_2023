package com.hwj.bs_backend.controller;

import com.hwj.bs_backend.annotation.TokenRequired;
import com.hwj.bs_backend.param.*;
import com.hwj.bs_backend.pojo.Device;
import com.hwj.bs_backend.pojo.PageResult;
import com.hwj.bs_backend.pojo.Result;
import com.hwj.bs_backend.service.DeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin // 允许跨域
@Slf4j
@RestController
@RequestMapping("device")
public class DeviceController {
    @Autowired
    DeviceService deviceService;

    /**
     * 获取某个设备信息
     *
     * @param deviceId 设备ID
     * @return 返回设备信息
     */
    @GetMapping("/{device_id}")
     @TokenRequired // 添加 TokenRequired 注解，表示需要 token 鉴权
    public Result<Device> getDeviceInfo(
            @PathVariable("device_id") String deviceId
    ) {
        return deviceService.getDeviceInfo(deviceId);
    }

    /**
     * 获取用户设备列表
     *
     * @param userId 用户的id
     * @return Result 包含设备列表的结果
     */
    @GetMapping("/user/{user_id}")
     @TokenRequired // 添加 TokenRequired 注解，表示需要 token 鉴权
    public Result<DeviceListWithCount> getUserDevices(
            @PathVariable("user_id") Integer userId) {
        return deviceService.getUserDevices(userId);
    }

    /**
     * 新增设备
     *
     * @param request 修改设备配置参数类
     * @return 返回新增设备处理结果
     */
    @PostMapping("/add")
     @TokenRequired // 添加 TokenRequired 注解，表示需要 token 鉴权
    public Result<String> addDevice(@RequestBody DeviceAddRequest request) {
        System.out.println(request);
        return deviceService.addDevice(request);
    }

    /**
     * 修改设备配置
     *
     * @param request  修改设备配置参数类
     * @param deviceId 设备id
     * @return 返回修改设备配置处理结果
     */
    @PutMapping("/update/{device_id}")
     @TokenRequired // 添加 TokenRequired 注解，表示需要 token 鉴权
    public Result<String> updateDevice(
            @PathVariable("device_id") String deviceId,
            @RequestBody DeviceUpdateRequest request) {
        System.out.println(request);
        return deviceService.updateDevice(deviceId, request);
    }

    /**
     * 获取最近七天新增设备数量
     *
     * @param userId 用户id
     * @param today  当天的日期
     * @return 最近七天新增的设备列表
     */
    @GetMapping("/new-devices-count")
    public Result<List<DeviceCountResponse>> getNewDevicesCount(
            @RequestParam("user_id") Integer userId,
            @RequestParam("today") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date today) {
        return deviceService.getNewDevicesCount(userId, today);
    }

    /**
     * 查询设备列表（带分页）
     *
     * @param userId       用户ID
     * @param deviceId     设备ID（可为空）
     * @param deviceName   设备名称（可为空）
     * @param deviceType   设备类型（可为空）
     * @param page         页码
     * @param pageSize     每页显示数量
     * @return 设备列表（带分页）
     */
    @GetMapping("/search-with-pagination")
    public Result<PageResult<List<DeviceListResponse>>> searchDevicesWithPagination(
            @RequestParam("user_id") Integer userId,
            @RequestParam(value = "device_id", required = false) String deviceId,
            @RequestParam(value = "device_name", required = false) String deviceName,
            @RequestParam(value = "device_type", required = false) Integer deviceType,
            @RequestParam(value = "is_active", required = false) Boolean isActive,
            @RequestParam("current") int page,
            @RequestParam("size") int pageSize) {

        DeviceSearchRequest searchRequest = new DeviceSearchRequest();
        searchRequest.setUserId(userId);
        searchRequest.setDeviceId(deviceId);
        searchRequest.setDeviceName(deviceName);
        searchRequest.setDeviceType(deviceType);
        searchRequest.setIsActive(isActive);

        try {
            PageResult<List<DeviceListResponse>> pageResult = deviceService.searchDevicesWithPagination(
                    searchRequest, page, pageSize);
            return Result.success(pageResult);
        } catch (Exception e) {
            log.error("Error retrieving paginated device list", e);
            return Result.error("Error retrieving paginated device list: " + e.getMessage());
        }
    }

    /**
     * 删除设备
     *
     * @param deviceId 设备id
     * @return 返回删除结果
     */
    @DeleteMapping("/delete/{device_id}")
    public Result<String> deleteDevice(@PathVariable("device_id") String deviceId) {
        return deviceService.deleteDevice(deviceId);
    }

}
