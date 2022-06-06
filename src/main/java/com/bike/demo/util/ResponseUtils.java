package com.bike.demo.util;

import java.util.HashMap;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.bike.demo.pojo.CommonResponse;
import com.bike.demo.pojo.StatusResponse;

@Component
public class ResponseUtils {
	public CommonResponse mapToCommonResponse(StatusResponse metadata, Object data){
        HashMap<String, Object> map = new HashMap<>();
        map.put("metadata", metadata);
        map.put("data", data);
        //return new ModelMapper().map(map, CommonResponse.class);
        CommonResponse cr = new CommonResponse();
        cr.setStatus(metadata);
        cr.setObjectResponse(data);
        return cr;
    }
}
