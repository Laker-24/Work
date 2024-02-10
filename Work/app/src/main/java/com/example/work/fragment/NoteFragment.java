package com.example.work.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.work.Adapter.NoteAdapter;
import com.example.work.AddActivity;
import com.example.work.Dao.NoteDao;
import com.example.work.NoteActivity;
import com.example.work.R;
import com.example.work.db.AppDatabase;
import com.example.work.entity.Note;
import com.example.work.util.SpfUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private FloatingActionButton mBtnAdd;
    private List<Note> mNotes;
    private NoteAdapter mMyAdapter;

    private NoteDao noteDao;
    public static final int MODE_LINEAR = 0;
    public static final int MODE_GRID = 1;

    public static final String KEY_LAYOUT_MODE = "key_layout_mode";

    private int currentListLayoutMode = MODE_LINEAR;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private LayoutInflater menuInflater;

    public NoteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NoteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NoteFragment newInstance(String param1, String param2) {
        NoteFragment fragment = new NoteFragment();
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
    @Override
    public void onResume() {
        super.onResume();
        refreshDataFromDb();
        setListLayout();
        initEvent();
    }
    private void setListLayout() {
        currentListLayoutMode = SpfUtil.getIntWithDefault(getActivity(), KEY_LAYOUT_MODE, MODE_LINEAR);
        if (currentListLayoutMode == MODE_LINEAR) {
            setToLinearList();

        }else{
            setToGridList();
        }
    }

    private void refreshDataFromDb() {
        mNotes = getDataFromDB();
        Toast.makeText(requireActivity(),mNotes.toString(),Toast.LENGTH_SHORT).show();
        mMyAdapter.refreshData(mNotes);
    }

//    private void initEvent() {
//        mMyAdapter = new NoteAdapter((NoteActivity) getActivity(), mNotes);
//        mRecyclerView.setAdapter(mMyAdapter);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//        mRecyclerView.setLayoutManager(linearLayoutManager);
//        mMyAdapter.setViewType(NoteAdapter.TYPE_LINEAR_LAYOUT);
//        setListLayout();
//
//    }

//    private void initData() {
//        mNotes = new ArrayList<>();
//        noteDao= AppDatabase.getINSTANCE(getActivity()).noteDao();
//
//        for (int i = 0; i < 30; i++) {
//            Note note = new Note();
//            note.setTitle("这是标题"+i);
//            note.setContent("这是内容"+i);
//            note.setCreatedTime(getCurrentTimeFormat());
//            mNotes.add(note);
//        }

//        mNotes = getDataFromDB();

//    }

    private List<Note> getDataFromDB() {
        return noteDao.getAll();
    }

    private String getCurrentTimeFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY年MM月dd HH:mm:ss");
        Date date = new Date();
        return simpleDateFormat.format(date);
    }

//    private void initView() {
//        mRecyclerView = view.findViewById(R.id.recycler);
//    }


//    public void add(View view) {
//        Intent intent = new Intent(requireActivity(), AddActivity.class);
//        startActivity(intent);
//    }




/*    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);

        switch (item.getItemId()) {

            case R.id.menu_linear:
                setToLinearList();
                currentListLayoutMode = MODE_LINEAR;
                SpfUtil.saveInt(getActivity(),KEY_LAYOUT_MODE,MODE_LINEAR);

                return true;
            case R.id.menu_grid:

                setToGridList();
                currentListLayoutMode = MODE_GRID;
                SpfUtil.saveInt(getActivity(),KEY_LAYOUT_MODE,MODE_GRID);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }*/

    private void setToLinearList() {
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mMyAdapter.setViewType(NoteAdapter.TYPE_LINEAR_LAYOUT);
        mMyAdapter.notifyDataSetChanged();
    }


    private void setToGridList() {
        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mMyAdapter.setViewType(NoteAdapter.TYPE_GRID_LAYOUT);
        mMyAdapter.notifyDataSetChanged();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_note, container, false);
        View view =inflater.inflate(R.layout.fragment_note, container, false);
        mRecyclerView = view.findViewById(R.id.recycler);
        initData();
        initEvent();

        /*mRecyclerView = view.findViewById(R.id.recycler);
        mMyAdapter = new NoteAdapter(getActivity(),mNotes);
        mRecyclerView.setAdapter(mMyAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mMyAdapter.setViewType(NoteAdapter.TYPE_LINEAR_LAYOUT);
        setListLayout();
        mNotes = new ArrayList<>();
        noteDao= AppDatabase.getINSTANCE(getActivity()).noteDao();
        for (int i = 0; i < 30; i++) {
            Note note = new Note();
            note.setTitle("这是标题"+i);
            note.setContent("这是内容"+i);
            note.setTime(System.currentTimeMillis());
            mNotes.add(note);
        }
        mNotes = getDataFromDB();

       */
        mBtnAdd=view.findViewById(R.id.fab);
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), AddActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void initData() {
        mNotes = new ArrayList<>();
        noteDao= AppDatabase.getINSTANCE(requireActivity()).noteDao();
        mNotes=noteDao.getAll();
//        Log.e("TAG", "initData: "+mNotes.size() );
    }
    private void initEvent() {
        mMyAdapter = new NoteAdapter(requireActivity(), mNotes);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mMyAdapter.setViewType(NoteAdapter.TYPE_LINEAR_LAYOUT); mRecyclerView.setAdapter(mMyAdapter);
//        setListLayout();

    }


}