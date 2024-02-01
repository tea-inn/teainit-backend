package top.teainn.project.util;

import cn.hutool.http.HttpUtil;
import com.google.gson.Gson;
import top.teainn.project.model.dto.log.city.IpToAddressDto;

import java.util.HashMap;
 
public class IpToAddressUtil {
    //使用腾讯的接口通过ip拿到城市信息
    private static final String KEY = "FAYBZ-MXTKB-BW7UE-JAP7N-XCIK3-H4BZI";
    public static IpToAddressDto getCityInfo(String ip) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("key", KEY);
        paramMap.put("ip", ip);

        String result= HttpUtil.get("https://apis.map.qq.com/ws/location/v1/ip", paramMap);
        Gson gson = new Gson();
        IpToAddressDto ipToAddressDto = gson.fromJson(result, IpToAddressDto.class);
        return ipToAddressDto;
    }
 
}