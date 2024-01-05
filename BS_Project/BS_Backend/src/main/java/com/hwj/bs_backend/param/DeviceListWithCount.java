package com.hwj.bs_backend.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceListWithCount {
        private List<DeviceListResponse> devices;
        private List<Integer> countList;

}
