package com.cloud.coretest;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.cloud.core.Func1;
import com.cloud.core.annotations.ApiCheckAnnotation;
import com.cloud.core.beans.BaseBean;
import com.cloud.core.beans.RetrofitParams;
import com.cloud.core.okrx.BaseService;
import com.cloud.core.okrx.BaseSubscriber;
import com.cloud.core.okrx.OkRxValidParam;
import com.cloud.coretest.beans.VersionBean;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/6/8
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class OkRxTest extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.okgo_view);
        View annotationBtn = findViewById(R.id.annotation_btn);
        annotationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestServer testServer = new TestServer();
                testServer.payCode(OkRxTest.this);
            }
        });
    }

    public class TestServer extends BaseService {
        //https://mobile.findaily.cn/rest/version
        @ApiCheckAnnotation(IsNetworkValid = true)
        public void requestInfo(Context context) {
            BaseSubscriber baseSubscriber = new BaseSubscriber<BaseBean, TestServer>(context, this) {
                @Override
                protected void onSuccessful(BaseBean baseBean) {

                }
            };
            OkRxValidParam validParam = OkRxValidParsing.check(context, this);
            requestObject(context, TestAPI.class, this, baseSubscriber, validParam, new Func1<String, String>() {
                @Override
                public String call(String apiUrlTypeName) {
                    //获取baseurl实际项目中需要再统一封装个方法
                    return "https://talk.iqiaorong.com/";
                }
            }, new Func1<TestAPI, RetrofitParams>() {
                @Override
                public RetrofitParams call(TestAPI testAPI) {
                    return testAPI.cancelConcernFriends(459338, "54d41d85db2d635900263cbce910122e");
                }
            }, null);
        }

        @ApiCheckAnnotation(IsNetworkValid = true)
        public void requestOutsideVersion(Context context) {
            BaseSubscriber baseSubscriber = new BaseSubscriber<VersionBean, TestServer>(context, this) {
                @Override
                protected void onSuccessful(VersionBean versionBean) {

                }
            };
            OkRxValidParam validParam = OkRxValidParsing.check(context, this);
            requestObject(context, TestAPI.class, this, baseSubscriber, validParam, new Func1<String, String>() {
                @Override
                public String call(String apiUrlTypeName) {
                    //获取baseurl实际项目中需要再统一封装个方法
                    return "http://mobile.findaily.cn/";
                }
            }, new Func1<TestAPI, RetrofitParams>() {
                @Override
                public RetrofitParams call(TestAPI testAPI) {
                    return testAPI.requestOutsideUrl();
                }
            }, null);
        }

        @ApiCheckAnnotation(IsNetworkValid = true, IsTokenValid = true)
        public void payCode(Context context) {
            BaseSubscriber baseSubscriber = new BaseSubscriber<BaseBean, TestServer>(context, this) {
                @Override
                protected void onSuccessful(BaseBean aqBaseBean, String reqKey) {

                }
            };
            OkRxValidParam validParam = OkRxValidParsing.check(context, this);
            requestObject(context, TestAPI.class, this, baseSubscriber, validParam, new Func1<String, String>() {
                @Override
                public String call(String s) {
                    return "http://120.27.225.7:18080/";
                }
            }, new Func1<TestAPI, RetrofitParams>() {
                @Override
                public RetrofitParams call(TestAPI iWalletAPI) {
                    return iWalletAPI.payCode("13967189624", "24062017112314182203");
                }
            }, null);
        }
    }

    public static final String baseUrl = "";
}
