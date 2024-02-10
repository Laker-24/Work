package com.example.work.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.work.Dao.NoteDao;
import com.example.work.EditActivity;
import com.example.work.R;
import com.example.work.db.AppDatabase;
import com.example.work.entity.Note;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Note> mBeanList;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    private NoteDao noteDao;

    private int viewType;

    public static int TYPE_LINEAR_LAYOUT = 0;
    public static int TYPE_GRID_LAYOUT = 1;

    public NoteAdapter(FragmentActivity context, List<Note> mBeanList) {
        this.mBeanList = mBeanList;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        noteDao= AppDatabase.getINSTANCE(mContext).noteDao();
    }
    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    @Override
    public int getItemViewType(int position) {
        return viewType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == TYPE_LINEAR_LAYOUT){
            View view = mLayoutInflater.inflate(R.layout.list_item_layout, parent, false);
            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }else if(viewType == TYPE_GRID_LAYOUT){
            View view = mLayoutInflater.inflate(R.layout.list_item_grid_layout, parent, false);
            MyGridViewHolder myGridViewHolder = new MyGridViewHolder(view);
            return myGridViewHolder;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder == null) {
            return;
        }
        if(holder instanceof MyViewHolder){
            bindMyViewHolder((MyViewHolder) holder, position);
        } else if (holder instanceof MyGridViewHolder) {
            bindGridViewHolder((MyGridViewHolder) holder, position);
        }
    }
    private void bindMyViewHolder(MyViewHolder holder, int position) {
        Note note = mBeanList.get(position);
        holder.mTvTitle.setText(note.getTitle());
        holder.mTvContent.setText(note.getContent());
        holder.mTvTime.setText(Long.toString(note.getTime())
                );
        holder.rlContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, EditActivity.class);
                intent.putExtra("note_id",  note.id);
                mContext.startActivity(intent);
            }
        });
        holder.rlContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // 弹出弹窗
                Dialog dialog = new Dialog(mContext, android.R.style.ThemeOverlay_Material_Dialog_Alert);
                View dialogView = mLayoutInflater.inflate(R.layout.list_item_dialog_layout, null);
                TextView tvDelete = dialogView.findViewById(R.id.tv_delete);
                TextView tvEdit = dialogView.findViewById(R.id.tv_edit);

                tvDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int row = noteDao.delete(note.getId());
                        if (row>0) {
                            removeData(position);
                        }
                        dialog.dismiss();
                    }
                });


                tvEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, EditActivity.class);
                        intent.putExtra("note", (CharSequence) note);
                        mContext.startActivity(intent);
                        dialog.dismiss();
                    }
                });
                dialog.setContentView(dialogView);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
                return true;
            }
        });
    }


    private void bindGridViewHolder(MyGridViewHolder holder, int position) {
        Note note = mBeanList.get(position);
        holder.mTvTitle.setText(note.getTitle());
        holder.mTvContent.setText(note.getContent());
        holder.mTvTime.setText(Long.toString(note.getTime()));
        holder.rlContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, EditActivity.class);
                intent.putExtra("note", (CharSequence) note);
                mContext.startActivity(intent);
            }
        });

        holder.rlContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // 弹出弹窗
                Dialog dialog = new Dialog(mContext, android.R.style.ThemeOverlay_Material_Dialog_Alert);
                View dialogView = mLayoutInflater.inflate(R.layout.list_item_dialog_layout, null);
                TextView tvDelete = dialogView.findViewById(R.id.tv_delete);
                TextView tvEdit = dialogView.findViewById(R.id.tv_edit);

                tvDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int row = noteDao.delete(note.getId());
                        if (row > 0) {
                            removeData(position);
                        }
                        dialog.dismiss();
                    }
                });


                tvEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, EditActivity.class);
                        intent.putExtra("note", (CharSequence) note);
                        mContext.startActivity(intent);
                        dialog.dismiss();
                    }
                });
                dialog.setContentView(dialogView);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
                return true;
            }
        });
    }
    @Override
    public int getItemCount() {
        return mBeanList.size();
    }
    public void refreshData(List<Note> notes) {
        this.mBeanList = notes;
        notifyDataSetChanged();
    }

    public void removeData(int pos) {
        mBeanList.remove(pos);
        notifyItemRemoved(pos);
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView mTvTitle;
        TextView mTvContent;
        TextView mTvTime;
        ViewGroup rlContainer;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mTvTitle = itemView.findViewById(R.id.tv_title);
            this.mTvContent = itemView.findViewById(R.id.tv_content);
            this.mTvTime = itemView.findViewById(R.id.tv_time);
            this.rlContainer = itemView.findViewById(R.id.rl_item_container);
        }
    }

    class MyGridViewHolder extends RecyclerView.ViewHolder{

        TextView mTvTitle;
        TextView mTvContent;
        TextView mTvTime;
        ViewGroup rlContainer;

        public MyGridViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mTvTitle = itemView.findViewById(R.id.tv_title);
            this.mTvContent = itemView.findViewById(R.id.tv_content);
            this.mTvTime = itemView.findViewById(R.id.tv_time);
            this.rlContainer = itemView.findViewById(R.id.rl_item_container);
        }
    }
}
