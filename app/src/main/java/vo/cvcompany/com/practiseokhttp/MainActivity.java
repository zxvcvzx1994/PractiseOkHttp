package vo.cvcompany.com.practiseokhttp;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.txtData)
    TextView txtData;

    private OkHttpClient okHttpClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn)
    public void btn(){
        okHttpClient = new OkHttpClient();
        getWebService();
    }

    @OnClick(R.id.btnData)
    public void btnData(){
        if(txtData.getText().toString().length()>0){
            if(Build.VERSION.SDK_INT>=19){
                Log.i(TAG, "btnData: 1111111111111");
                processData(txtData);
            }

        }
    }
    public void getWebService(){
        Request request = new Request.Builder().url("http://192.168.1.10/DuLieu/josn.php").build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtData.setText("failure");
                    }
                });

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            txtData.setText(response.body().string());
                        }catch (Exception e){
                            txtData.setText("Get response failure");
                        }
                    }
                });

            }
        });
    }


    private void processData (TextView a){
        if(Build.VERSION.SDK_INT>=19)
        try {
            JSONArray jsonArray = new JSONArray(a.getText().toString().trim());
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            Toast.makeText(this, ""+jsonObject.getString("Id"), Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {e.printStackTrace();

        }
    }

}
