package com.hncd.carcontrol.base;

import com.alibaba.fastjson.JSONObject;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    /**
     * 登录接口
     *
     * @param body
     * @return
     */
    @POST("cancellation/login")
    Observable<JSONObject> login(@Body RequestBody body);

    /**
     * 修改密码
     *
     * @param body
     * @return
     */
    @POST("cancellation/upPwd")
    Observable<JSONObject> upPwd(@Body RequestBody body);

    /**
     * 登记信息接口
     *
     * @param body
     * @return
     */
    @POST("cancellation/getRegInfo")
    Observable<JSONObject> getRegInfo(@Body RequestBody body);

}
