package com.cloud.coretest;

import com.cloud.core.annotations.BaseUrlTypeName;
import com.cloud.core.annotations.DELETE;
import com.cloud.core.annotations.DataParam;
import com.cloud.core.annotations.GET;
import com.cloud.core.annotations.Header;
import com.cloud.core.annotations.Headers;
import com.cloud.core.annotations.POST;
import com.cloud.core.annotations.Param;
import com.cloud.core.annotations.Path;
import com.cloud.core.annotations.RetCodes;
import com.cloud.core.annotations.UrlItem;
import com.cloud.core.annotations.UrlItemKey;
import com.cloud.core.beans.BaseBean;
import com.cloud.core.beans.RetrofitParams;
import com.cloud.core.okrx.RequestContentType;
import com.cloud.coretest.beans.VersionBean;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/6/6
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
@BaseUrlTypeName(value = ApiUrlType.API_URL_TYPE_NAME, tokenName = "Authorization", contentType = RequestContentType.Json)
public interface TestAPI {

    @POST(values = {
            @UrlItem(value = "/kol/1.0/article", key = "key1"),
            @UrlItem(value = "/kol/1.0/article", key = "key2")
    }, isRemoveEmptyValueField = true)
    @BaseUrlTypeName(ApiUrlType.API_URL_TYPE_NAME)
    @RetCodes("200")
    @DataParam(value = KolListBean.class)
    RetrofitParams requestVersion(
            @Param("searchType") int searchType,
            @UrlItemKey("urlKey") String urlKey
    );

    @DELETE("kol/1.0/follows/{friendId}")
    @DataParam(BaseBean.class)
    RetrofitParams cancelConcernFriends(
            @Path("friendId") int friendId,
            @Param("token") String frToken
    );

    @GET(value = "/rest/version")
    @BaseUrlTypeName(value = OkRxTest.baseUrl)
    @DataParam(value = VersionBean.class)
    RetrofitParams requestOutsideUrl();

    @POST(value = "相对url", isPrintApiLog = true)
    @Header(name = "token", value = "xxxxx")
    @Headers({"key1:value1", "key2:{value2}"})
    @DataParam(value = BaseBean.class)
    RetrofitParams payCode(
            @Param("phone") String phone,
            @Param("orderNo") String orderNo
    );
}
