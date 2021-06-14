package kw.kimkihong.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import kw.kimkihong.assign3.R;
import kw.kimkihong.retrofit.PostCallback;
import kw.kimkihong.retrofit.Request;
import kw.kimkihong.retrofit.RequestCallback;
import kw.kimkihong.vo.PostVO;

import java.util.*;

public class PostRecyclerAdapter extends RecyclerView.Adapter<PostRecyclerAdapter.ViewHolder> {
    private final ArrayList<PostVO> mPostList = new ArrayList<>();
    private PostRecyclerAdapter.OnPostItemClickListener mListener;

    public void setOnItemClickListener(PostRecyclerAdapter.OnPostItemClickListener listener)
    {
        this.mListener = listener;
    }

    @NonNull
    @Override
    public PostRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
        return new PostRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostRecyclerAdapter.ViewHolder holder, int position) {
        holder.onBind(mPostList.get(position));
    }

    @Override
    public int getItemCount() {
        return mPostList.size();
    }

    public void addPost(Calendar date) {
        if(mPostList.size() != 0) {
            mPostList.clear();
        }
        Request.getInstance().getPostList(date, new PostCallback() {
            @Override
            public void onSuccess(List<PostVO> retData) {
                mPostList.addAll(retData);
                notifyDataSetChanged();
            }

            @Override
            public void onError() {}
        });
    }

    public interface OnPostItemClickListener
    {
        void onItemClick(View view, PostVO post);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView postImage;
        TextView postTitle;
        TextView postPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.postImage = itemView.findViewById(R.id.postImage);
            this.postTitle = itemView.findViewById(R.id.postTitle);
            this.postPrice =  itemView.findViewById(R.id.postPrice);

            itemView.setOnClickListener(view -> {
                mListener.onItemClick(view, mPostList.get(getAbsoluteAdapterPosition()));
            });
        }

        void onBind(PostVO item){
            postTitle.setText(item.getName());
            postPrice.setText(item.getPrice());
        }
    }
}
