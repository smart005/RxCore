/**
 * Title: BaseRedirectUtils.java
 * Description:
 * author: lijinghuan
 * data: 2015年5月4日 下午8:31:13
 */
package com.cloud.core.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.cloud.core.configs.BaseCConfig;
import com.cloud.core.configs.RxCoreConfigItems;

public class BaseRedirectUtils {

    /**
     * 启动服务
     *
     * @param context 上下文
     * @param cls     需要启动的服务类
     * @param action  启动服务action,接收时传回
     * @param bundle  服务启动时传入的数据bundle
     */
    public static void startService(Context context, Class<?> cls, String action, Bundle bundle) {
        Intent _intent = new Intent();
        if (!TextUtils.isEmpty(action)) {
            _intent.setAction(action);
        }
        _intent.setClass(context, cls);
        if (bundle != null) {
            _intent.putExtras(bundle);
        }
        context.startService(_intent);
    }

    /**
     * 启动服务
     *
     * @param context 上下文
     * @param cls     需要启动的服务类
     * @param action  启动服务action,接收时传回
     */
    public static void startService(Context context, Class<?> cls, String action) {
        startService(context, cls, action, null);
    }

    /**
     * 启动服务
     *
     * @param context 上下文
     * @param cls     需要启动的服务类
     * @param bundle  服务启动时传入的数据bundle
     */
    public static void startService(Context context, Class<?> cls, Bundle bundle) {
        startService(context, cls, "", bundle);
    }

    /**
     * 启动服务
     *
     * @param context 上下文
     * @param cls     需要启动的服务类
     */
    public static void startService(Context context, Class<?> cls) {
        startService(context, cls, "", null);
    }

    /**
     * 停止服务
     *
     * @param context 上下文
     * @param cls     要停止的服务类
     */
    public static void stopService(Context context, Class<?> cls) {
        Intent _intent = new Intent();
        _intent.setClass(context, cls);
        context.stopService(_intent);
    }

    /**
     * 绑定服务
     *
     * @param context 上下文
     * @param conn    服务连接器
     * @param action
     */
    public static void bindService(Context context, ServiceConnection conn,
                                   String action) {
        Intent _intent = new Intent(action);
        context.bindService(_intent, conn, Context.BIND_AUTO_CREATE);
    }

    /**
     * 取消服务绑定
     *
     * @param context 上下文
     * @param conn    服务连接器
     */
    public static void unbindService(Context context, ServiceConnection conn) {
        context.unbindService(conn);
    }

    /**
     * 启动activity
     *
     * @param activity 提供上下文的activity
     * @param cls      要启动类对象
     * @param bundle   传入的bundle数据
     */
    public static void startActivity(Activity activity, Class<?> cls, Bundle bundle) {
        Intent _intent = new Intent();
        _intent.setClass(activity, cls);
        if (bundle != null) {
            _intent.putExtras(bundle);
        }
        activity.startActivity(_intent);
    }

    /**
     * 以result方式启动activity
     *
     * @param activity    提供上下文的activity
     * @param cls         要启动类对象
     * @param bundle      传入的bundle数据
     * @param requestCode 回调onActivityResult时传回的requestCode
     */
    public static void startActivityForResult(Activity activity,
                                              Class<?> cls,
                                              Bundle bundle,
                                              int requestCode) {
        Intent _intent = new Intent();
        _intent.setClass(activity, cls);
        if (bundle != null) {
            _intent.putExtras(bundle);
        }
        activity.startActivityForResult(_intent, requestCode);
    }

    /**
     * 启动activity
     *
     * @param activity      提供上下文的activity
     * @param classFullName activity全路径类名
     * @param bundle        传入的bundle数据
     */
    public static void startActivity(Activity activity, String classFullName, Bundle bundle) {
        if (activity == null || TextUtils.isEmpty(classFullName)) {
            return;
        }
        Intent _intent = new Intent();
        _intent.setClassName(activity, classFullName);
        _intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (bundle != null) {
            _intent.putExtras(bundle);
        }
        activity.startActivity(_intent);
    }

    /**
     * 启动activity
     *
     * @param context       上下文
     * @param classFullName activity全路径类名
     * @param bundle        传入的bundle数据
     */
    public static void startActivity(Context context, String classFullName, Bundle bundle) {
        if (context == null || TextUtils.isEmpty(classFullName)) {
            return;
        }
        Intent _intent = new Intent();
        _intent.setClassName(context, classFullName);
        _intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (bundle != null) {
            _intent.putExtras(bundle);
        }
        context.startActivity(_intent);
    }

    /**
     * 以result方式启动activity
     *
     * @param activity    提供上下文的activity
     * @param cls         activity全路径类名
     * @param bundle      传入的bundle数据
     * @param requestCode 回调onActivityResult时传回的requestCode
     */
    public static void startActivityForResult(Activity activity,
                                              String classFullName,
                                              Bundle bundle,
                                              int requestCode) {
        if (activity == null || TextUtils.isEmpty(classFullName)) {
            return;
        }
        Intent _intent = new Intent();
        _intent.setClassName(activity, classFullName);
        _intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (bundle != null) {
            _intent.putExtras(bundle);
        }
        activity.startActivityForResult(_intent, requestCode);
    }

    /**
     * 结束当前activity
     *
     * @param activity 提供上下文的activity
     */
    public static void finishActivity(Activity activity) {
        activity.finish();
    }

    /**
     * 发送广播
     *
     * @param context    上下文
     * @param action     接收广播时通过action来判断来源
     * @param permission 广播发送与接收权限
     * @param bundle     传入的bundle数据
     */
    public static void sendBroadcast(Context context,
                                     String action,
                                     String permission,
                                     Bundle bundle) {
        Intent intent = new Intent(action);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (TextUtils.isEmpty(permission)) {
            context.sendBroadcast(intent);
        } else {
            context.sendBroadcast(intent, permission);
        }
    }

    /**
     * 发送广播
     *
     * @param context 上下文
     * @param action  接收广播时通过action来判断来源
     * @param bundle  传入的bundle数据
     */
    public static void sendBroadcast(Context context,
                                     String action,
                                     Bundle bundle) {
        sendBroadcast(context, action, "", bundle);
    }

    /**
     * 发送广播
     * 通过此方式发送的广播，action在框架初始化时设置
     *
     * @param context 上下文
     * @param bundle  传入的bundle数据
     */
    public static void sendBroadcast(Context context, Bundle bundle) {
        RxCoreConfigItems configItems = BaseCConfig.getInstance().getConfigItems(context);
        sendBroadcast(context, configItems.getReceiveAction(), bundle);
    }

    /**
     * 调起通话界面
     *
     * @param context     上下文
     * @param phonenumber 电话号码
     */
    public static void callTel(Context context, String phonenumber) {
        Intent intent = null;
        if (TextUtils.isEmpty(phonenumber)) {
            return;
        }
        if (phonenumber.contains("tel")) {
            intent = new Intent(Intent.ACTION_CALL, Uri.parse(phonenumber));
        } else {
            intent = new Intent(Intent.ACTION_CALL, Uri.parse(String.format(
                    "tel:%s", phonenumber)));
        }
        context.startActivity(intent);
    }

    /**
     * 启动桌面
     *
     * @param context 上下文
     */
    public static void startHome(Context context) {
        Intent home = new Intent(Intent.ACTION_MAIN);
        home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        home.addCategory(Intent.CATEGORY_HOME);
        context.startActivity(home);
    }

    /**
     * 判断某个Activity是否存在
     *
     * @param context     上下文
     * @param packageName 包名
     * @param className   要判断activity的类名
     * @return true:存在;false:不存在;
     */
    public boolean isActivityExist(Context context,
                                   String packageName,
                                   String className) {
        Intent intent = new Intent();
        intent.setClassName(packageName, className);
        if (context.getPackageManager().resolveActivity(intent, 0) == null) {
            return false;
        } else {
            return true;
        }
    }
}
