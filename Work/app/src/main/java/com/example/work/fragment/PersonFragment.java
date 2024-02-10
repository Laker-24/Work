package com.example.work.fragment;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.work.MainActivity;
import com.example.work.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PersonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonFragment extends Fragment {

    private AlertDialog dlg;
    private TextView tv_user;
    private ImageView btn_user;
    private Button btn_exit;
    private ImageView iv_avatar;
    private AlertDialog dlg_phone;

    private AlertDialog pho_select;
    private TextView tv_phone;
    private ImageView btn_phone;
    private int flag=0;
    private int choice=0;


    private TextView album;
    private TextView take;
    private ImageView imageView;
    private Uri ImageUri;
    private static String filePath;
    final int TAKE_PHOTO=1;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PersonFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PersonFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PersonFragment newInstance(String param1, String param2) {
        PersonFragment fragment = new PersonFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    class Listener implements View.OnClickListener{

        @SuppressLint("MissingInflatedId")
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_user:
                    View dlgview = LayoutInflater.from(requireActivity()).inflate(R.layout.dialog_layout, null);
                    EditText etname = dlgview.findViewById(R.id.etname);
                    Button btnok = dlgview.findViewById(R.id.btnok);
                    btnok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            tv_user.setText(etname.getText().toString());
                            dlg.dismiss();
                        }
                    });
                    dlg = new AlertDialog.Builder(requireActivity())
                            .setView(dlgview)
                            .create();
                    dlg.show();
                    break;
                case R.id.btn_phone:
//                    Toast.makeText(requireActivity(), "当前时间："+System.currentTimeMillis(), Toast.LENGTH_SHORT).show();
                    View dlgview_phone = LayoutInflater.from(requireActivity()).inflate(R.layout.dialog_layout, null);
                    EditText etname_phone = dlgview_phone.findViewById(R.id.etname);
                    Button btnok_phone = dlgview_phone.findViewById(R.id.btnok);
                    btnok_phone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            tv_phone.setText(etname_phone.getText().toString());
                            dlg_phone.dismiss();
                        }
                    });
                    dlg_phone = new AlertDialog.Builder(requireActivity())
                            .setView(dlgview_phone)
                            .create();
                    dlg_phone.show();
                    break;
                case R.id.iv_avatar:
                    View dlgview_avatar = LayoutInflater.from(requireContext()).inflate(R.layout.photo_select, null);
                    TextView text_take = dlgview_avatar.findViewById(R.id.tv_take_pictures);
                    TextView text_album = dlgview_avatar.findViewById(R.id.tv_open_album);
                    TextView text_delete = dlgview_avatar.findViewById(R.id.tv_cancel);
                    imageView =  dlgview_avatar.findViewById(R.id.iv_avatar);

                    text_take.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            requestPermission();
                           launcher3.launch(null);
                        }

                    });


                    text_album.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            requestPermission();

                            launcher2.launch("image/*");
                        }


                    });

                    text_delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            pho_select.dismiss();
                        }
                    });
                    pho_select = new AlertDialog.Builder(requireActivity())
                            .setView(dlgview_avatar)
                            .create();
                    pho_select.show();

                    break;

            }
        }
    }
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK)
                try {
                    //拿到相机存储在指定路径的图片，而后将其转化为bitmap格式，然后显示在界面上
                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(ImageUri));
                    imageView.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
        }
    }

    private ContentResolver getContentResolver() {
        return null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_person, container, false);
        View view=inflater.inflate(R.layout.fragment_person,container,false);
        tv_user = view.findViewById(R.id.tv_user);
        btn_user = view.findViewById(R.id.btn_user);
        tv_phone = view.findViewById(R.id.tv_phone);
        btn_phone = view.findViewById(R.id.btn_phone);
        iv_avatar = view.findViewById(R.id.iv_avatar);
        Listener listener = new Listener();
        btn_user.setOnClickListener(listener);
        btn_phone.setOnClickListener(listener);
        iv_avatar.setOnClickListener(listener);
        btn_exit=view.findViewById(R.id.btn_exit);
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(requireActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
    ActivityResultLauncher launcher2 = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    iv_avatar.setImageURI(result);
                    // todo 同下面的存储需求，只不过需要通过uri找到相册的资源，然后再存储
                }
            });
    ActivityResultLauncher launcher3 = registerForActivityResult(
            new ActivityResultContracts.TakePicturePreview(),

new ActivityResultCallback<Bitmap>() {
        @Override
        public void onActivityResult(Bitmap result) {
//result为拍摄照片Bitmap格式
            iv_avatar.setImageBitmap(result);
//            TODO 把bitmap格式转为String等可以存储在数据库中的数据类型，然后存入数据库（需要修改User的定义，或者是重新建一个实体型和User的id关联）
        }
    });

    private final ActivityResultLauncher permissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestMultiplePermissions(),
            new ActivityResultCallback<Map<String, Boolean>>() {
                @Override
                public void onActivityResult( Map<String, Boolean> result ) {
                    if (result.get(Manifest.permission.READ_MEDIA_IMAGES) != null && result.get(Manifest.permission.CAMERA) != null) {
                        if (Objects.requireNonNull(result.get(Manifest.permission.READ_MEDIA_IMAGES)).equals(true)
                                && Objects.requireNonNull(result.get(Manifest.permission.CAMERA)).equals(true)) {
//权限全部获取到之后的动作
                        } else {
//有权限没有获取到的动作
                        }
                    }
                }
            }
    );
    //② 在需要的时候启动权限请求（封装一下）
    private void requestPermission() {
//权限数组
        String[] permissions = new String[] { Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.READ_MEDIA_VIDEO };
        permissionLauncher.launch( permissions );
    }
}