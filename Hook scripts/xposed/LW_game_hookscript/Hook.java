package com.example.xposedsecond;

import android.content.Intent;
import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Hook implements IXposedHookLoadPackage {
    String TAG = "HLR";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        Class clazz = loadPackageParam.classLoader.loadClass("com.image.abo.pingxx.MainActivity");
        XposedHelpers.findAndHookMethod(clazz, "onActivityResult",
                int.class,
                int.class,
                Intent.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        Log.d(TAG,"first hook 开始!");
                        Intent intent_0 = new Intent();
                        intent_0.putExtra("pay_result","success");
                        param.args[2] = intent_0;
                        Log.d(TAG,"first hook 结束");
                    }
                });

         /*
        定义两个全局变量对第三次hook赋值
         */
        final String[] productName = new String[1];
        final String[] productId = new String[1];

        Class clazz0 = loadPackageParam.classLoader.loadClass("com.chillyroomsdk.sdkbridge.order.BaseOrderAgent");
        XposedHelpers.findAndHookMethod(clazz0, "requestOrder",
                String.class, String.class, String.class, String.class, String.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        Log.d(TAG,"second hook 开始!");
                        productId[0] = (String) param.args[0];
                        productName[0] = (String) param.args[1];
                        Log.d(TAG,"Id"+productId[0]);
                        Log.d(TAG,"Name"+productName[0]);
                        Log.d(TAG,"second hook 结束");
                    }
                });
        /*
        把值传进去进行hook
         */
        Class clazz1 = loadPackageParam.classLoader.loadClass("com.chillyroomsdk.sdkbridge.order.https_task.OrderCheckTaskEx");
        XposedHelpers.findAndHookMethod(clazz1, "onPostExecute", String.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        Log.d(TAG,"Third hook 开始!");
                        param.args[0] = "{\"retcode\":0,\"order\":{\"can_send\":1,\"item_name\":\""+ productName[0] +"\",\"item_id\":"+ productId[0] +",\"total_price\":0},"+"\"msg\":\"购买成功\"}";
                        Log.d(TAG,""+param.args[0]);
                        Log.d(TAG,"Third hook 结束!");
                    }
                });
    }
}
