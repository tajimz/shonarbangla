package com.tajim.shonarbangla;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.tajim.shonarbangla.others.BaseActivity;
import com.tajim.shonarbangla.others.SQLiteHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SplashActivity extends BaseActivity {
    SQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sqLiteHelper = new SQLiteHelper(this);
        getData();
    }

    private void getData(){
        jsonArrayReq(false,"http://192.168.1.7/shonarbangla/scrap/getPrice.php" ,null, new JsonArrayCallBack() {
            @Override
            public void onSuccess(JSONArray result) {
                sqLiteHelper.clear();
                Log.d("appLog", result.toString());
                for (int i = 0 ; i < result.length(); i++){
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject = (JSONObject) result.get(i);
                        String k22_price = jsonObject.getString("22k_price");
                        String k21_price = jsonObject.getString("21k_price");
                        String k18_price = jsonObject.getString("18k_price");

                        String k22_priceS = jsonObject.getString("22k_price_silver");
                        String k21_priceS = jsonObject.getString("21k_price_silver");
                        String k18_priceS = jsonObject.getString("18k_price_silver");

                        String k22_priceSaudi = jsonObject.getString("22k_price_saudi");
                        String k21_priceSaudi = jsonObject.getString("21k_price_saudi");
                        String k18_priceSaudi = jsonObject.getString("18k_price_saudi");
                        String date = jsonObject.getString("date");

                        sqLiteHelper.insertData(k22_price,k21_price, k18_price,k22_priceS,k21_priceS, k18_priceS, date,k22_priceSaudi,k21_priceSaudi, k18_priceSaudi );

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }

                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();


            }

            @Override
            public void onFailed() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        });
    }


}