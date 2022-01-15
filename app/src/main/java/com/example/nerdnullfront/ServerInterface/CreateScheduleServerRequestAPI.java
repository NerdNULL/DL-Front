package com.example.nerdnullfront.ServerInterface;

import com.example.nerdnullfront.ServerResponseDataSet.CreateScheduleResponseData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CreateScheduleServerRequestAPI {
    @GET("api/v1/plan/details/{planId}")    // Keyword.json의 정보를 받아옴
    Call<CreateScheduleResponseData> getSearchKeyword(
            @Path("planId") String planId            // 검색을 원하는 질의어 [필수]

    );
}
