package kw.kimkihong.assign3.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import kw.kimkihong.assign3.activity.DetailActivity;
import kw.kimkihong.assign3.adapter.DateRecyclerAdapter;
import kw.kimkihong.assign3.adapter.PostRecyclerAdapter;
import kw.kimkihong.assign3.R;
import kw.kimkihong.assign3.vo.PostVO;

import java.util.Calendar;


public class ListFragment extends Fragment {
    //declare fragment variable
    private RecyclerView dateRecyclerView;
    private DateRecyclerAdapter dateRecyclerAdapter;

    private RecyclerView postRecyclerView;
    private PostRecyclerAdapter postRecyclerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        //set recycler view
        dateRecyclerView = (RecyclerView) view.findViewById(R.id.listCalenderView);
        postRecyclerView = (RecyclerView) view.findViewById(R.id.listPostView);

        dateRecyclerAdapter = new DateRecyclerAdapter();
        postRecyclerAdapter = new PostRecyclerAdapter();

        dateRecyclerView.setAdapter(dateRecyclerAdapter);
        dateRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        postRecyclerView.setAdapter(postRecyclerAdapter);
        postRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));

        dateRecyclerAdapter.addDate();
        dateRecyclerView.post(() -> dateRecyclerAdapter.notifyDataSetChanged());

        postRecyclerAdapter.addPost(Calendar.getInstance());

        dateRecyclerView.addOnScrollListener(new dateOnScrollListener());
        dateRecyclerAdapter.setOnItemClickListener(new onDateItemClickListener());

        postRecyclerAdapter.setOnItemClickListener(new onPostItemClickListener());

        return view;
    }

    //declare date scroll listener
    class dateOnScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            assert layoutManager != null;
            int totalItemCount = layoutManager.getItemCount();
            int lastVisible = layoutManager.findLastCompletelyVisibleItemPosition();

            //check how many element left
            if (lastVisible >= totalItemCount - 3) {
                dateRecyclerAdapter.addDate();
                dateRecyclerView.post(() -> dateRecyclerAdapter.notifyDataSetChanged());
            }
        }
    }

    //declare on date click listener
    class onDateItemClickListener implements DateRecyclerAdapter.OnItemClickListener {
        @Override
        //refresh data view
        public void onItemClick(View itemView, Calendar date) {
            postRecyclerAdapter.addPost(date);
        }
    }

    //goto Detailview activity
    class onPostItemClickListener implements PostRecyclerAdapter.OnPostItemClickListener {
        @Override
        public void onItemClick(View itemView, PostVO post) {
            Log.d("Clicked postID", post.getId()+"");
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra("postID", post.getId());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getActivity().startActivity(intent);
        }
    }
}