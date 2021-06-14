package kw.kimkihong.assign3.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import kw.kimkihong.adapter.DateRecyclerAdapter;
import kw.kimkihong.adapter.PostRecyclerAdapter;
import kw.kimkihong.adapter.ReservationRecyclerAdapter;
import kw.kimkihong.assign3.R;
import kw.kimkihong.vo.PostVO;
import kw.kimkihong.vo.ReservationVO;

import java.util.Calendar;


public class ReservationFragment extends Fragment {
    private RecyclerView dateRecyclerView;
    private DateRecyclerAdapter dateRecyclerAdapter;

    private RecyclerView reservationRecyclerView;
    private ReservationRecyclerAdapter reservationRecyclerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reservation, container, false);
        dateRecyclerView = (RecyclerView) view.findViewById(R.id.reservationCalenderView);
        reservationRecyclerView = (RecyclerView) view.findViewById(R.id.reservationNameView);

        dateRecyclerAdapter = new DateRecyclerAdapter();
        reservationRecyclerAdapter = new ReservationRecyclerAdapter();

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

    class dateOnScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            assert layoutManager != null;
            int totalItemCount = layoutManager.getItemCount();
            int lastVisible = layoutManager.findLastCompletelyVisibleItemPosition();

            if (lastVisible >= totalItemCount - 3) {
                dateRecyclerAdapter.addDate();
                dateRecyclerView.post(() -> dateRecyclerAdapter.notifyDataSetChanged());
            }
        }
    }

    class onDateItemClickListener implements DateRecyclerAdapter.OnItemClickListener {
        @Override
        public void onItemClick(View itemView, Calendar date) {
            reservationRecyclerAdapter.addReservation(date);
        }
    }

    class onReservationItemClickListener implements ReservationRecyclerAdapter.OnReservationItemClickListener {
        @Override
        public void onItemClick(View itemView, ReservationVO post) {

        }
    }
}