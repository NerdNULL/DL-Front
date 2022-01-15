package com.example.nerdnullfront.ServerInterface;

import com.example.nerdnullfront.ServerResponseDataSet.CreateScheduleResponseData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CreateScheduleServerRequestAPI {
    @GET("rest URL/{keyword}")    // Json의 정보를 받아옴
    Call<CreateScheduleResponseData> getSearchKeyword(
            @Path("keyword")String keyword     // 카카오 API 인증키 [필수]

    );// 받아온 정보가 AddressResponseData 클래스의 구조로 담김
}
