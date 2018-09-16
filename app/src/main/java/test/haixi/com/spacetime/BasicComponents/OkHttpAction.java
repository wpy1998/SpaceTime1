package test.haixi.com.spacetime.BasicComponents;

import android.content.Context;
import android.content.Intent;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static okhttp3.RequestBody.create;
import static test.haixi.com.spacetime.BasicComponents.BasicActivity.phoneNumber;
import static test.haixi.com.spacetime.BasicComponents.Settings.web;
import static test.haixi.com.spacetime.BasicComponents.BasicActivity.token;

public class OkHttpAction {
    public static final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
    public static final MediaType URLENCODED = MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8");
    Context context;
    public OkHttpAction(Context context){
        this.context = context;
    }

    //post 注册用户
    public void registerUser(final String verificationCode, final String phoneNumber, final String password,
                             final String intentAction){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    String urlencoded = "smsCode=" + verificationCode + "&phoneNumber=" + phoneNumber +
                            "&password=" + password;
                    RequestBody body = create(URLENCODED, urlencoded);
                    Request request = new Request.Builder().url(web + "/users").post(body).build();
                    Response response = client.newCall(request).execute();
                    String action = response.body().string();
                    System.out.println(action);
                    Intent intent = new Intent(intentAction);
                    intent.putExtra("data", action);
                    context.sendBroadcast(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //post 获取验证码
    public void getVerificationCode(final String phoneNumber, final String intentAction){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    String urlencoded = "phoneNumber=" + phoneNumber;
                    RequestBody body = create(URLENCODED, urlencoded);
                    Request request = new Request.Builder().url(web + "/auth/sms-code").post(body).build();
                    Response response = client.newCall(request).execute();
                    String action = response.body().string();
                    System.out.println(action);
                    Intent intent = new Intent(intentAction);
                    intent.putExtra("data", action);
                    context.sendBroadcast(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //get 获取用户信息
    public void getUserMessage(final String intentAction){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    String urlencoded = "token=" + token + "&phoneNumber=" + phoneNumber;
                    Request request = new Request.Builder().url(web + "/users/" + phoneNumber + "?" + urlencoded).build();
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()){
                        String action = response.body().string();
                        System.out.println(action);
                        Intent intent = new Intent(intentAction);
                        intent.putExtra("data", action);
                        context.sendBroadcast(intent);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //put 设置头像
    public void setUserAvatar(final File avatar, final String intentAction){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //get 检查手机号是否被使用
    public void checkExistence(final String phoneNumber, final String intentAction){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    String urlencoded = "phoneNumber=" + phoneNumber;
                    Request request = new Request.Builder().url(web + "/users/" + phoneNumber + "/check-existence?" + urlencoded)
                            .build();
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()){
                        String action = response.body().string();
                        System.out.println(action);
                        Intent intent = new Intent(intentAction);
                        intent.putExtra("data", action);
                        context.sendBroadcast(intent);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //put 重置密码
    public void resetPassword(final String phoneNumber, final String newPassword, final String intentAction){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    FormBody build = new FormBody.Builder()
                            .add("token", BasicActivity.token)
                            .add("phoneNumber", phoneNumber)
                            .add("newPassword", newPassword)
                            .build();
                    Request request = new Request.Builder().url(web + "/users/" + phoneNumber + "/password")
                            .put(build).build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String action = response.body().string();
                            System.out.println(action);
                            Intent intent = new Intent(intentAction);
                            intent.putExtra("data", action);
                            context.sendBroadcast(intent);
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //post 用户登录
    public void loginWithPassword(final String phoneNumber, final String password, final String intentAction){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    String urlencoded = "phoneNumber=" + phoneNumber + "&password=" + password;
                    RequestBody body = create(URLENCODED, urlencoded);
                    Request request = new Request.Builder().url(web + "/auth/token/authorize-with-password")
                            .post(body).build();
                    Response response = client.newCall(request).execute();
                    String action = response.body().string();
                    System.out.println(action);
                    System.out.println("phone = " + phoneNumber + ", password = " + password);
                    Intent intent = new Intent(intentAction);
                    intent.putExtra("data", action);
                    context.sendBroadcast(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //post 用户登录
    public void loginWithVerificationCode(final String phoneNumber, final String verificationCode,
                                          final String intentAction){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    String urlencoded = "phoneNumber=" + phoneNumber + "&smsCode=" + verificationCode;
                    RequestBody body = create(URLENCODED, urlencoded);
                    Request request = new Request.Builder().url(web + "/auth/token/authorize-with-sms-code")
                            .post(body).build();
                    Response response = client.newCall(request).execute();
                    String action = response.body().string();
                    System.out.println(action);
                    Intent intent = new Intent(intentAction);
                    intent.putExtra("data", action);
                    context.sendBroadcast(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //delete 退出账户
    public void logout(final String intentAction){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    String urlencoded = "token=" + token + "&phoneNumber=" + phoneNumber;
                    RequestBody body = create(URLENCODED, urlencoded);
                    Request request = new Request.Builder().url(web + "/auth/token")
                            .delete(body).build();
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()){
                        String action = response.body().string();
                        System.out.println(action);
                        Intent intent = new Intent(intentAction);
                        intent.putExtra("data", action);
                        context.sendBroadcast(intent);
                    }else {
                        System.out.println("登出失败");
                        String action = response.body().string();
                        System.out.println(action);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //get 检查验证码是否正确
    public void checkVerificationCode(final String phoneNumber, final String smsCode, final String intentAction){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    String urlencoded = "phoneNumber=" + phoneNumber + "&smsCode=" + smsCode;
                    Request request = new Request.Builder().url(web + "/auth/sms-code/check?" + urlencoded)
                            .build();
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()){
                        String action = response.body().string();
                        System.out.println(action);
                        Intent intent = new Intent(intentAction);
                        intent.putExtra("data", action);
                        context.sendBroadcast(intent);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
