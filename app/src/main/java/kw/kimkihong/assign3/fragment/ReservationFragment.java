package kw.kimkihong.assign3.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import kw.kimkihong.assign3.activity.ReservDetailActivity;
import kw.kimkihong.assign3.adapter.DateRecyclerAdapter;
import kw.kimkihong.assign3.adapter.ReservationRecyclerAdapter;
import kw.kimkihong.assign3.R;
import kw.kimkihong.assign3.vo.ReservationVO;
import org.jetbrains.annotations.NotNull;

import java.util.Calendar;


public class ReservationFragment extends Fragment {
    //declare fragment variable
    private RecyclerView dateRecyclerView;
    private DateRecyclerAdapter dateRecyclerAdapter;

    private RecyclerView reservationRecyclerView;
    private ReservationRecyclerAdapter reservationRecyclerAdapter;

    private Context mContext;
    private Activity mActivity;

    //get context and activity
    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        mContext = context;
        if (context instanceof Activity) {
            mActivity = (Activity) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reservation, container, false);
        //set recycler view
        dateRecyclerView = (RecyclerView) view.findViewById(R.id.reservationCalenderView);
        reservationRecyclerView = (RecyclerView) view.findViewById(R.id.reservationNameView);

        dateRecyclerAdapter = new DateRecyclerAdapter();
        reservationRecyclerAdapter = new ReservationRecyclerAdapter(mContext.getSharedPreferences("user_data", Context.MODE_PRIVATE));

        dateRecyclerView.setAdapter(dateRecyclerAdapter);
        dateRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        reservationRecyclerView.setAdapter(reservationRecyclerAdapter);
        reservationRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));

        dateRecyclerAdapter.addDate();
        dateRecyclerView.post(() -> dateRecyclerAdapter.notifyDataSetChanged());

        reservationRecyclerAdapter.addReservation(Calendar.getInstance());

        dateRecyclerView.addOnScrollListener(new ReservationFragment.dateOnScrollListener());
        dateRecyclerAdapter.setOnItemClickListener(new ReservationFragment.onDateItemClickListener());

        reservationRecyclerAdapter.setOnItemClickListener(new onReservationItemClickListener());

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
            reservationRecyclerAdapter.addReservation(date);
        }
    }

    //goto Detailview activity
    class onReservationItemClickListener implements ReservationRecyclerAdapter.OnReservationItemClickListener {
        @Override
        public void onItemClick(View itemView, ReservationVO post) {
            Intent intent = new Intent(mActivity, ReservDetailActivity.class);
            String[] aa = post.toStringArray();
            intent.putExtra("data", post.toStringArray());
            startActivity(intent);
        }
    }
}