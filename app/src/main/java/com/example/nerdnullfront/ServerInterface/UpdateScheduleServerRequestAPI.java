package com.example.nerdnullfront.ServerInterface;

import com.example.nerdnullfront.ServerResponseDataSet.UpdateScheduleResponseData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface UpdateScheduleServerRequestAPI {
    @GET("v2/local/search/keyword.json")    // Keyword.json의 정보를 받아옴
    Call<UpdateScheduleResponseData> getSearchKeyword(
            @Header("Authorization")String key,     // 카카오 API 인증키 [필수]
            @Query("query")String query            // 검색을 원하는 질의어 [필수]
            // 매개변수 추가 가능
            // @Query("category_group_code") category: String

    );// 받아온 정보가 AddressResponseData 클래스의 구조로 담김
}
