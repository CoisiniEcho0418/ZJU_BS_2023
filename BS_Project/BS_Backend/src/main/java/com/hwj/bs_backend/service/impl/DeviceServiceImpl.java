package com.hwj.bs_backend.service.impl;

import com.hwj.bs_backend.mapper.DeviceMapper;
import com.hwj.bs_backend.param.*;
import com.hwj.bs_backend.pojo.Device;
import com.hwj.bs_backend.pojo.PageResult;
import com.hwj.bs_backend.pojo.Result;
import com.hwj.bs_backend.service.DeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    DeviceMapper deviceMapper;

    /**
     * 获取某个设备信息
     *
     * @param deviceId 目标设备的id
     * @return Result 包含设备信息的结果
     */
    @Override
    public Result<Device> getDeviceInfo(String deviceId) {
        try {
            // 调用 Mapper 层查询设备信息
            Device device = deviceMapper.getDeviceInfo(deviceId);

            if (device != null) {
                return Result.success(device);
            } else {
                return Result.error("设备不存在");
            }
        } catch (Exception e) {
            log.error("获取设备信息失败", e);
            return Result.error("获取设备信息失败：" + e.getMessage());
        }
    }

    /**
     * 查询用户设备列表
     *
     * @param userId 用户的id
     * @return Result 包含设备列表的结果
     */
    @Override
    public Result<DeviceListWithCount> getUserDevices(Integer userId) {
        try {
            // 调用 Mapper 层查询用户设备列表
            List<DeviceListResponse> devices = deviceMapper.getUserDevices(userId);

            // 计算设备类型数量
            Map<Integer, Long> countMap = devices.stream()
                    .collect(Collectors.groupingBy(DeviceListResponse::getDeviceType, Collectors.counting()));

            // 验证设备类型是否在指定范围内
            for (Integer type : countMap.keySet()) {
                if (type < 1 || type > 6) {
                    return Result.error("无效的设备类型：" + type);
                }
            }

            // 创建 countList
            List<Integer> countList = Arrays.asList(
                    Math.toIntExact(countMap.getOrDefault(1, 0L)),
                    Math.toIntExact(countMap.getOrDefault(2, 0L)),
                    Math.toIntExact(countMap.getOrDefault(3, 0L)),
                    Math.toIntExact(countMap.getOrDefault(4, 0L)),
                    Math.toIntExact(countMap.getOrDefault(5, 0L)),
                    Math.toIntExact(countMap.getOrDefault(6, 0L))
            );

            // 封装到新的类中
            DeviceListWithCount result = new DeviceListWithCount(devices, countList);

            return Result.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("获取设备列表失败：" + e.getMessage());
        }
    }

    /**
     * 新增设备
     *
     * @param request 新增设备参数类
     * @return 返回新增设备处理结果
     */
    @Override
    public Result<String> addDevice(DeviceAddRequest request) {
        try {
            int insertRowCount = deviceMapper.insertDevice(request);
            if (insertRowCount != 0) {
                return Result.success("设备添加成功");
            } else {
                return Result.error("新增设备失败：");
            }

        } catch (Exception e) {
            log.error("新增设备失败", e);
            return Result.error("新增设备失败：" + e.getMessage());
        }
    }


    /**
     * 修改设备配置
     *
     * @param request  修改设备配置参数类
     * @param deviceId 设备id
     * @return 返回修改设备配置处理结果
     */
    @Override
    public Result<String> updateDevice(String deviceId, DeviceUpdateRequest request) {
        try {
            // 更新设备信息时，只更新参数不为null的字段
            int updateRowCount = deviceMapper.updateDevice(deviceId, request);
            if (updateRowCount != 0) {
                return Result.success("设备信息更新成功");
            } else {
                return Result.error("设备信息更新失败，设备不存在或更新行数为0");
            }
        } catch (Exception e) {
            log.error("更新设备信息失败", e);
            return Result.error("更新设备信息失败：" + e.getMessage());
        }
    }

    /**
     * 获取最近七天新增设备数量
     *
     * @param userId 用户id
     * @param today  当天的日期
     * @return 最近七天新增的设备列表
     */
    @Override
    public Result<List<DeviceCountResponse>> getNewDevicesCount(Integer userId, Date today) {
        try {
            List<DeviceCountResponse> counts = deviceMapper.getNewDevicesCount(userId, today);
            return Result.success(counts);
        } catch (Exception e) {
            log.error("获取最近七天新增设备数量失败", e);
            return Result.error("获取最近七天新增设备数量失败：" + e.getMessage());
        }
    }

    /**
     * 根据查询条件返回分页数据
     *
     * @param deviceSearchRequest 查询条件
     * @param page                 当前的页号
     * @param pageSize             页的大小
     * @return 分页结果
     */
    @Override
    public PageResult<List<DeviceListResponse>> searchDevicesWithPagination(
            DeviceSearchRequest deviceSearchRequest, int page, int pageSize) {
        try {
            int offset = (page - 1) * pageSize;
            List<DeviceListResponse> lists = deviceMapper.searchDevicesWithPagination(
                    deviceSearchRequest, offset, pageSize);

            int totalCount = deviceMapper.countDevices(deviceSearchRequest);

            return new PageResult<>(lists, totalCount, page, pageSize);
        } catch (Exception e) {
            log.error("Querying paginated device list failed", e);
            throw new RuntimeException("Querying paginated device list failed: " + e.getMessage());
        }
    }

    /**
     * 删除设备
     *
     * @param deviceId 设备id
     * @return 返回删除结果
     */
    @Override
    public Result<String> deleteDevice(String deviceId) {
        try {
            // 调用 Mapper 层删除设备
            int deleteRowCount = deviceMapper.deleteDevice(deviceId);
            if (deleteRowCount > 0) {
                return Result.success("设备删除成功");
            } else {
                return Result.error("设备删除失败，设备不存在或删除行数为0");
            }
        } catch (Exception e) {
            log.error("删除设备失败", e);
            return Result.error("删除设备失败：" + e.getMessage());
        }
    }
}

