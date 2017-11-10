package com.example.liuwangshu.moonvolley;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "TAG_Volley";
    private ImageView iv_image;
    private NetworkImageView nv_image;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv_image = findViewById(R.id.iv_image);
        nv_image = findViewById(R.id.nv_image);
        mQueue = Volley.newRequestQueue(MainActivity.this);
        findViewById(R.id.bt_send).setOnClickListener(v -> {
            UseStringRequest();
            UseJsonRequest();
            UseImageRequest();
            UseImageLoader();
            UseNetworkImageView();
        });
    }

    private void UseStringRequest() {
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, "https://www.baidu.com", response -> Log.d(TAG, response), error -> Log.e(TAG, error.getMessage(), error));        //创建请求队列
        mQueue.add(mStringRequest);             //将请求添加在请求队列中
    }

    private void UseJsonRequest() {
        JsonObjectRequest mJsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "http://ip.taobao.com/service/getIpInfo.php?ip=59.108.54.37", response -> {
            IpModel ipModel = new Gson().fromJson(response.toString(), IpModel.class);
            if (null != ipModel && null != ipModel.getData()) {
                Log.d(TAG, ipModel.getData().getCity());
            }
        }, error -> Log.e(TAG, error.getMessage(), error));
        mQueue.add(mJsonObjectRequest);
    }

    private void UseImageRequest() {
        ImageRequest imageRequest = new ImageRequest("http://img.my.csdn.net/uploads/201603/26/1458988468_5804.jpg", response -> iv_image.setImageBitmap(response), 0, 0, Bitmap.Config.RGB_565, error -> iv_image.setImageResource(R.drawable.ico_default));
        mQueue.add(imageRequest);
    }

    private void UseImageLoader() {
        ImageLoader imageLoader = new ImageLoader(mQueue, new BitmapCache());
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(iv_image, R.drawable.ico_default, R.drawable.ico_default);
        imageLoader.get("http://img.my.csdn.net/uploads/201603/26/1458988468_5804.jpg", listener);
    }

    private void UseNetworkImageView() {
        ImageLoader imageLoader = new ImageLoader(mQueue, new BitmapCache());
        nv_image.setDefaultImageResId(R.drawable.ico_default);
        nv_image.setErrorImageResId(R.drawable.ico_default);
        nv_image.setImageUrl("http://img.my.csdn.net/uploads/201603/26/1458988468_5804.jpg", imageLoader);
    }
}
