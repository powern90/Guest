package kw.kimkihong.assign3.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import kw.kimkihong.assign3.R;
import kw.kimkihong.assign3.retrofit.ListCallback;
import kw.kimkihong.assign3.retrofit.Request;
import kw.kimkihong.assign3.vo.PostVO;

import java.util.*;

public class PostRecyclerAdapter extends RecyclerView.Adapter<PostRecyclerAdapter.ViewHolder> {
    //declare each variable
    private final ArrayList<PostVO> mPostList = new ArrayList<>();
    private PostRecyclerAdapter.OnPostItemClickListener mListener;

    //make on item click listener
    public void setOnItemClickListener(PostRecyclerAdapter.OnPostItemClickListener listener)
    {
        this.mListener = listener;
    }

    //declare on create view holder
    @NonNull
    @Override
    public PostRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
        return new PostRecyclerAdapter.ViewHolder(view);
    }

    //declare onbind holder
    @Override
    public void onBindViewHolder(@NonNull PostRecyclerAdapter.ViewHolder holder, int position) {
        holder.onBind(mPostList.get(position));
    }

    //declare count function
    @Override
    public int getItemCount() {
        return mPostList.size();
    }

    //declare add element function
    public void addPost(Calendar date) {
        //remove all element
        if(mPostList.size() != 0) {
            mPostList.clear();
        }
        //call API for new data
        Request.getInstance().getPostList(date, new ListCallback() {
            @Override
            public void onSuccess(List<PostVO> retData) {
                mPostList.addAll(retData);
                notifyDataSetChanged();
            }

            @Override
            public void onError() {}
        });
    }

    //declare interface for item click listener
    public interface OnPostItemClickListener {
        void onItemClick(View view, PostVO post);
    }

    //declare viewholder
    class ViewHolder extends RecyclerView.ViewHolder {
        //set view variable
        ImageView postImage;
        TextView postTitle;
        TextView postPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //set view variable
            this.postImage = itemView.findViewById(R.id.postImage);
            this.postTitle = itemView.findViewById(R.id.postTitle);
            this.postPrice =  itemView.findViewById(R.id.postPrice);

            //set onclick listener
            itemView.setOnClickListener(view -> {
                mListener.onItemClick(view, mPostList.get(getAbsoluteAdapterPosition()));
            });
        }

        //declare bind function
        void onBind(PostVO item){
            //set view
            postTitle.setText(item.getName());
            postPrice.setText(item.getPrice());
            Glide.with(itemView).load("http://10.0.2.2/public/images/" + item.getImg())
                    .error(R.drawable.no_image)
                    .into(postImage);
        }
    }
}
