package com.example.work;

import static android.view.KeyCharacterMap.load;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Insert;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.RoundedCorner;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.work.Dao.UserDao;
import com.example.work.db.AppDatabase;
import com.example.work.entity.User;

import java.text.BreakIterator;
import java.time.Instant;
import java.time.temporal.TemporalAdjuster;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static Handler handler;
    private AppDatabase appDatabase;
    private EditText l_username, l_password;
    Button lg_register;
    ImageView imageview;
    Button btn;
    CheckBox checkBox1, checkBox2;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        l_username = findViewById(R.id.username);
        l_password = findViewById(R.id.password);
        ActionBar actionBar = getSupportActionBar(); //获取ActionBar
        actionBar.hide(); //隐藏ActionBar
        sharedPreferences=getSharedPreferences("userinfo",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        String a=sharedPreferences.getString("checkbox1","");
        l_username.setText(sharedPreferences.getString("user",""));
        l_password.setText(sharedPreferences.getString("pass",""));
        Intent intent3 = getIntent();
        Bundle bundle = intent3.getExtras();
        if (bundle != null) {
            String username = bundle.getString("username", "");
            String password = bundle.getString("password", "");
            l_username.setText(username);
            l_password.setText(password);
        }
        checkBox1 = findViewById(R.id.checkBox1);
        checkBox2 = findViewById(R.id.checkBox2);
        lg_register = findViewById(R.id.register);
        lg_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent1);
            }
        });

        btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(checkBox1.isChecked()){
                        editor.putString("user",l_username.getText().toString());
                        editor.putString("pass",l_password.getText().toString());
                        editor.putString("checkbox1","1");
                        editor.commit();
                    }else{
                        editor.putString("user","");
                        editor.putString("pass","");
                        editor.putString("checkbox1","");
                        editor.commit();
                    }
                    if(checkBox2.isChecked()){
                        editor.putString("user",l_username.getText().toString());
                        editor.putString("pass",l_password.getText().toString());
                        editor.commit();
                    }else{
                        editor.putString("user","");
                        editor.putString("pass","");
                        editor.commit();
                    }
                login();

            }
        });
        if(!TextUtils.isEmpty(l_username.getText().toString())&&!TextUtils.isEmpty(l_password.getText().toString())){
            if(a.equals("1")){
                checkBox1.setChecked(true);
                login();
            }else{
                checkBox2.setChecked(true);
            }
        }
    }

        public void login () {
            String username = l_username.getText().toString();
            String password = l_password.getText().toString();
            UserDao userDao = AppDatabase.getINSTANCE(MainActivity.this).getuserDao();
            User user = userDao.findUser(username, password);

            if (user != null) {
                editor.putInt("id",user.id);
                editor.apply();
                Intent intent2 = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent2);
                Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "登录失败", Toast.LENGTH_LONG).show();
            }
        }


}