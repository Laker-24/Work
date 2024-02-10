package com.example.work;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.work.Dao.NoteDao;
import com.example.work.db.AppDatabase;
import com.example.work.entity.Note;
import com.example.work.util.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EditActivity extends AppCompatActivity{

    private Note note;
    private EditText etTitle,etContent;

    private NoteDao noteDao= AppDatabase.getINSTANCE(this).noteDao();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etTitle = findViewById(R.id.et_title);
        etContent = findViewById(R.id.et_content);

        initData();

    }

    private void initData() {
        Intent intent = getIntent();
        int note_id = intent.getIntExtra("note_id",0);
        note = noteDao.findNoteById(note_id);
        if (note != null) {
            etTitle.setText(note.getTitle());
            etContent.setText(note.getContent());
        }

    }

    public void save(View view) {
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
        note.setTime(System.currentTimeMillis());
        noteDao.update(note);
            ToastUtil.toastShort(this, "修改成功！");
            this.finish();


    }

    private String getCurrentTimeFormat() {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY年MM月dd日 HH:mm:ss");
        Date date = new Date();
        return sdf.format(date);
    }
}
