package test.haixi.com.spacetime.FunctionWelcome.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.Calendar;

import test.haixi.com.spacetime.BasicComponents.BasicActivity;
import test.haixi.com.spacetime.BasicComponents.Components.FileOperation;
import test.haixi.com.spacetime.BasicComponents.OkHttpAction;
import test.haixi.com.spacetime.R;

@SuppressLint("ValidFragment")
public class RegisterCompleteMessage extends Fragment {
    EditText name;
    ImageView image;
    TextView gender, time;
    Button nextPage;
    Calendar calendar;
    IntentFilter intentFilter;
    HttpBroadcastReceiver httpBroadcastReceiver;

    String intentActionFinish, intentActionNext,
            intentHttpRegisterUser = "wpy.example.com.goalproject.Fragment.RegisterCompleteMessage.registerUser",
            intentHttpLogin = "wpy.example.com.goalproject.Fragment.RegisterCompleteMessage.login";
    int genderWhich;

    public RegisterCompleteMessage(String intentActionFinish, String intentActionNext){
        this.intentActionFinish = intentActionFinish;
        this.intentActionNext = intentActionNext;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_complete_message, container, false);
        initView(view);
        setAction();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(httpBroadcastReceiver);
    }

    private void initView(View view) {
        name = view.findViewById(R.id.register_complete_message_name);
        time = view.findViewById(R.id.register_complete_message_time);
        image = view.findViewById(R.id.register_complete_message_image);
        gender = view.findViewById(R.id.register_complete_message_gender);
        nextPage = view.findViewById(R.id.register_complete_message_nextPage);

        intentFilter = new IntentFilter();
        intentFilter.addAction(intentHttpRegisterUser);
        intentFilter.addAction(intentHttpLogin);
        httpBroadcastReceiver = new HttpBroadcastReceiver();
        getContext().registerReceiver(httpBroadcastReceiver, intentFilter);
    }

    private void setAction() {
        gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext()).setTitle("请选择您的性别").setIcon(
                        R.drawable.myperson).setSingleChoiceItems(
                        new String[] { "男", "女", "保密"}, genderWhich,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                genderWhich = which;
                                if (genderWhich == 2){
                                    gender.setText("保密");
                                }else if (genderWhich == 0){
                                    gender.setText("男");
                                }else if (genderWhich == 1){
                                    gender.setText("女");
                                }
                                dialog.dismiss();
                            }
                        }).setNegativeButton("取消", null).show();
            }
        });

        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpAction okHttpAction = new OkHttpAction(getContext());
                okHttpAction.registerUser(smsCode, phoneNumber, password, intentHttpRegisterUser);
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                //修改日历控件的年，月，日
                                //这里的year,monthOfYear,dayOfMonth的值与DatePickerDialog控件设置的最新值一致
                                year = calendar.get(Calendar.YEAR);
                                month = calendar.get(Calendar.MONTH);
                                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                                time.setText(year + "年" + (month + 1) + "月" + dayOfMonth + "日");
                            }
                        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //设定结果返回
                startActivityForResult(i, 1);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("requestCode = " + requestCode + ", resultCode = " + resultCode);
        if (requestCode == 1 && resultCode == -1){
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            //获取选择照片的数据视图
            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            //从数据视图中获取已选择图片的路径
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            System.out.println("picturePath = " + picturePath);
            cursor.close();
            FileOperation.setBitmap(picturePath);
            image.setImageBitmap(FileOperation.bitmap);
        }
    }

    String smsCode, phoneNumber, password;
    public void initData(String smsCode, String phoneNumber, String password){
        this.password = password;
        this.smsCode = smsCode;
        this.phoneNumber = phoneNumber;
    }

    private class HttpBroadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(intentHttpRegisterUser)){
                String data = intent.getStringExtra("data");
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String message = jsonObject.getString("message");
                    if (!message.equals("success")){
                        return;
                    }

                    Toast.makeText(getContext(), "注册成功", Toast.LENGTH_SHORT).show();
                    OkHttpAction okHttpAction = new OkHttpAction(getContext());
                    okHttpAction.loginWithPassword(phoneNumber, password, intentHttpLogin);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else if (action.equals(intentHttpLogin)){
                String data = intent.getStringExtra("data");
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String message = jsonObject.getString("message");
                    if (!message.equals("success")){
                        Toast.makeText(getContext(), "登录失败", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    String data1 = jsonObject.getString("data");
                    JSONObject object = new JSONObject(data1);
                    BasicActivity.token = object.getString("token");
                    BasicActivity.phoneNumber = phoneNumber;
                    BasicActivity.password = password;

                    Intent intent1 = new Intent(intentActionNext);
                    getActivity().sendBroadcast(intent1);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
