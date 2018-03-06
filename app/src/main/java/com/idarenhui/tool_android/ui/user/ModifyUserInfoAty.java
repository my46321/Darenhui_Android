package com.idarenhui.tool_android.ui.user;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.idarenhui.tool_android.R;
import com.idarenhui.tool_android.constant.Constants;
import com.idarenhui.tool_android.constant.UserInfo;
import com.idarenhui.tool_android.custom.CirclePictureTransformation;
import com.idarenhui.tool_android.custom.CustomToast;
import com.idarenhui.tool_android.event.CampusChoseEvent;
import com.idarenhui.tool_android.model.gson.ModifyUserInfoReqJSON;
import com.idarenhui.tool_android.model.gson.QiniuJSON;
import com.idarenhui.tool_android.model.gson.UserInfoJSON;
import com.idarenhui.tool_android.model.gson.UserLoginInfoJSON;
import com.idarenhui.tool_android.net.Api;
import com.idarenhui.tool_android.net.UserCallback;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.zhy.http.okhttp.OkHttpUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by chen on 2017/7/30.
 */

public class ModifyUserInfoAty extends Activity {
    @BindView(R.id.profile_image)
    ImageView profileImage;
    @BindView(R.id.photo_picker_btn)
    Button photoPickerBtn;
    @BindView(R.id.male_image)
    ImageView maleImage;
    @BindView(R.id.female_image)
    ImageView femaleImage;
    @BindView(R.id.name_et)
    EditText nameEt;

    @BindView(R.id.school_campus_et)
    TextView schoolCampusEt;

    @BindView(R.id.identity_spinner)
    Spinner identitySpinner;
    @BindView(R.id.enrollment_et)
    EditText yearEitdText;
    @BindView(R.id.btn_reg)
    Button btnReg;

    byte[] picData;
    String key;
    String token;
    String domain;
    String picInfo;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    @BindView(R.id.close_icon)
    ImageView closeIcon;
    @BindView(R.id.phone_num_show)
    EditText phoneNumShow;

    private String name = "", sex = "", campus = "", identity = "", year = "";
    private String avatar = "";

    private UploadManager uploadManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_register_main);
        Log.i(Constants.TAG, "ModifyUserInfoAty.onCreate()");

        EventBus.getDefault().register(this);
        ButterKnife.bind(this);

        init();

        overridePendingTransition(R.transition.slide_right_in, R.transition.slide_right_out);
    }

    private void init() {
        phoneNumShow.setText(UserInfo.tel+"（该信息不可更改）");
        loadFromNet();
        uploadManager = new UploadManager();

    }

    protected void loadFromNet() {
        String url = Api.modifyUserInfo;
        OkHttpUtils
                .get()//
                .url(url)//
                .addHeader("Access-Token", UserInfo.token)
                .build()//
                .execute(new UserCallback<UserInfoJSON>() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.i(Constants.TAG, e.getMessage());
                    }

                    @Override
                    public void onResponse(UserInfoJSON response, int id) {
                        Log.i(Constants.TAG, "获取用户信息" + response.code);

                        Glide.with(ModifyUserInfoAty.this).load(response.data.user_info.avatar)
                                .bitmapTransform(new CirclePictureTransformation(ModifyUserInfoAty.this))
                                .error(R.drawable.test)
                                .into(profileImage);

                        name = response.data.user_info.realName;
                        sex = response.data.user_info.sex;
                        identity = response.data.user_info.identity;
                        campus = response.data.user_info.college;
                        year = response.data.user_info.grade;

                        //初始化显示的值，用户当前的信息
                        Log.i(Constants.TAG, "name:" + name);
                        nameEt.setText(name);
                        schoolCampusEt.setText(campus);
                        if (identity .equals( "本科生")) {
                            identitySpinner.setSelection(0);//设置Spinner默认选中值
                        } else {
                            identitySpinner.setSelection(1);
                        }
                        yearEitdText.setText(year);

                    }

                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            Log.e("uri", uri.toString());
            ContentResolver cr = this.getContentResolver();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                /* 将Bitmap设定到ImageView */
                profileImage.setImageBitmap(bitmap);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                picData = baos.toByteArray();

                uploadManager.put(picData, key, token, new UpCompletionHandler() {
                    public void complete(String key, ResponseInfo rinfo, JSONObject response) {
                        //btnUpload.setVisibility(View.INVISIBLE);
                        String s = key + ", " + rinfo + ", " + response;
                        //显示上传后文件的url
                        picInfo = s + "\n" + domain + key;
                        avatar = domain + key;
                        Glide.with(ModifyUserInfoAty.this).load(avatar)
                                .bitmapTransform(new CirclePictureTransformation(ModifyUserInfoAty.this))
                                .error(R.drawable.test)
                                .into(profileImage);
                        Log.i("qiniutest", avatar);

                    }
                }, new UploadOptions(null, "test-type", true, null, null));

            } catch (FileNotFoundException e) {
                Log.e("Exception", e.getMessage(), e);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick({R.id.photo_picker_btn, R.id.male_image, R.id.female_image, R.id.school_campus_et, R.id.btn_reg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.photo_picker_btn:
                upLoadImg();
                break;
            case R.id.male_image:
                //点击男性
                sex = "man";
                Log.i("testSex", "to man");
                femaleImage.setImageResource(R.drawable.reg_female_image2);
                maleImage.setImageResource(R.drawable.reg_male_image);
                //femaleImage.clearColorFilter();
                //maleImage.setColorFilter(Color.BLUE, PorterDuff.Mode.MULTIPLY);
                break;
            case R.id.female_image:
                //点击女性
                sex = "woman";
                Log.i("testSex", "to woman");
                maleImage.setImageResource(R.drawable.reg_male_image2);
                femaleImage.setImageResource(R.drawable.reg_female_image);
                //maleImage.clearColorFilter();
                //femaleImage.setColorFilter(Color.BLUE,PorterDuff.Mode.MULTIPLY);
                break;
            case R.id.school_campus_et:
                //点击校区选择,打开校区选择activity
                startActivity(new Intent(ModifyUserInfoAty.this, RegisterCampusActivity.class));
                break;
            case R.id.btn_reg:
                //点击确认注册
                Log.i(Constants.TAG, "点击确认注册");
                signIn();
                break;
        }
    }

    //上传图片网络请求
    private void upLoadImg() {
        String url = "https://api.idarenhui.com/DRH_Test/v1.0/static/qiniu";
        OkHttpUtils
                .get()
                .url(url)
                .addHeader("Access-Token", UserInfo.token)
                .build()
                .execute(new UserCallback<QiniuJSON>() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(QiniuJSON response, int id) {
                        key = response.data.key;
                        domain = response.data.domain;
                        token = response.data.token;
                    }
                });
        Intent intent = new Intent();
                /* 开启Pictures画面Type设定为image */
        intent.setType("image/*");
                /* 使用Intent.ACTION_GET_CONTENT这个Action */
        intent.setAction(Intent.ACTION_GET_CONTENT);
                /* 取得相片后返回本画面 */
        startActivityForResult(intent, 1);
    }


    private void signIn() {
        name = nameEt.getText().toString();
        year = yearEitdText.getText().toString();
        identity = identitySpinner.getSelectedItem().toString();
        Log.i(Constants.TAG, "更改个人信息");
        //判断是否有空
        if ((name.isEmpty() == false) && (sex.isEmpty() == false) && (identity.isEmpty() == false) && (campus.isEmpty() == false) && (year.isEmpty() == false)) {
            //没有空字符串，上传至服务器
            String token = UserInfo.token;

            ModifyUserInfoReqJSON putData = new ModifyUserInfoReqJSON();
            putData.user_info.realName = name;
            putData.user_info.sex = sex;
            putData.user_info.college = campus;
            putData.user_info.identity = identity;
            putData.user_info.avatar = avatar;
            putData.user_info.grade = year;

            String json = new Gson().toJson(putData);
            String url = Api.modifyUserInfo;
            OkHttpUtils
                    .put()
                    .url(url)
                    .addHeader("Access-Token", UserInfo.token)
                    .requestBody(RequestBody.create(JSON, json))
                    .build()
                    .execute(new UserCallback<UserLoginInfoJSON>() {

                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Log.i(Constants.TAG, e.getMessage().toString());

                            //   ContactDialog.showTipDialog(getBaseContext(), "修改失败！");
                        }

                        @Override
                        public void onResponse(UserLoginInfoJSON response, int id) {
                            // ContactDialog.showTipDialog(getBaseContext(), "修改成功！");

                            finish();
                        }
                    });
        } else {
            CustomToast.toastMsgShort(getBaseContext(), "请填写完整的信息");
        }
    }

    @Subscribe
    public void onEventMainThread(CampusChoseEvent event) {
        campus = event.getCampusName();
        Log.i(Constants.TAG, "选择了" + campus);
        schoolCampusEt.setText(campus);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.close_icon)
    public void onViewClicked() {
        finish();
    }
}