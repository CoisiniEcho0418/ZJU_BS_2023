package com.hwj.bs_backend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页获取设备结果类
 * @param <T>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {
    private T data;
    private int totalCount; // 总共返回的数量
    private int currentPage; // 当前页
    private int pageSize; // 页的大小
}

