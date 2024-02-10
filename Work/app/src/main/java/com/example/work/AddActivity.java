package com.example.work;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.work.Dao.NoteDao;
import com.example.work.db.AppDatabase;
import com.example.work.entity.Note;
import com.example.work.fragment.NoteFragment;
import com.example.work.util.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddActivity extends AppCompatActivity {
    private EditText etTitle,etContent;
    private Button btntj;
    private NoteDao noteDao;
    SharedPreferences sp;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etTitle = findViewById(R.id.et_title);
        etContent = findViewById(R.id.et_content);
        noteDao = AppDatabase.getINSTANCE(this).noteDao();

    }

    public void add(View view) {
        String title = etTitle.getText().toString();
        String content = etContent.getText().toString();
        if (TextUtils.isEmpty(title)) {
            ToastUtil.toastShort(this, "标题不能为空！");
            return;
        }

        Note note = new Note();
        note.setEmp_id(getSharedPreferences("userinfo",MODE_PRIVATE).getInt("id",0));
        note.setTitle(title);
        note.setContent(content);
        note.setTime( System.currentTimeMillis());
        Log.w("TAG", "add: "+ note.getTime());
         noteDao.insert(note);

            ToastUtil.toastShort(this,"添加成功！");
            this.finish();

    }

    private String getCurrentTimeFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY年MM月dd HH:mm:ss");
        Date date = new Date();
        return simpleDateFormat.format(date);
    }
}
