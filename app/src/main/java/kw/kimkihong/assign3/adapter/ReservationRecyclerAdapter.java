package kw.kimkihong.assign3.adapter;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import kw.kimkihong.assign3.R;
import kw.kimkihong.assign3.retrofit.Request;
import kw.kimkihong.assign3.retrofit.rListCallback;
import kw.kimkihong.assign3.vo.ReservationVO;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReservationRecyclerAdapter extends RecyclerView.Adapter<ReservationRecyclerAdapter.ViewHolder> {
    //declare each variable
    private final ArrayList<ReservationVO> mReservationList = new ArrayList<>();
    private ReservationRecyclerAdapter.OnReservationItemClickListener mListener;
    private SharedPreferences preferences;

    //declare generator for shared preference
    public ReservationRecyclerAdapter(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    //make on item click listener
    public void setOnItemClickListener(ReservationRecyclerAdapter.OnReservationItemClickListener listener)
    {
        this.mListener = listener;
    }

    //declare on create view holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //set view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reservation_item, parent, false);
        return new ReservationRecyclerAdapter.ViewHolder(view);
    }

    //declare onbind holder
    @Override
    public void onBindViewHolder(@NonNull ReservationRecyclerAdapter.ViewHolder holder, int position) {
        holder.onBind(mReservationList.get(position));
    }

    //declare count function
    @Override
    public int getItemCount() {
        return mReservationList.size();
    }

    //declare add element function
    public void addReservation(Calendar date) {
        if(mReservationList.size() != 0) {
            mReservationList.clear();
        }
        //call API for new Data
        Request.getInstance().getReservList(this.preferences.getString("id", ""), date, new rListCallback() {
            @Override
            public void onSuccess(List<ReservationVO> retData) {
                mReservationList.addAll(retData);
                notifyDataSetChanged();
            }

            @Override
            public void onError() {

            }
        });
    }

    //declare interface for item click listener
    public interface OnReservationItemClickListener {
        void onItemClick(View view, ReservationVO post);
    }

    //declare viewholder
    class ViewHolder extends RecyclerView.ViewHolder {
        //set view variable
        TextView reservationId;
        TextView reservationStartDate;
        TextView reservationEndDate;
        TextView reservationName;
        TextView reservationCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //set view variable
            this.reservationId = itemView.findViewById(R.id.reservationId);
            this.reservationStartDate = itemView.findViewById(R.id.reservationStartDate);
            this.reservationEndDate =  itemView.findViewById(R.id.reservationEndDate);
            this.reservationName =  itemView.findViewById(R.id.reservationName);
            this.reservationCount =  itemView.findViewById(R.id.reservationCount);

            //set onclick listener
            itemView.setOnClickListener(view -> {
                mListener.onItemClick(view, mReservationList.get(getAbsoluteAdapterPosition()));
            });
        }

        //declare bind function
        void onBind(ReservationVO item){
            //check what kind of user it is and show different data
            if(preferences.getBoolean("isBusiness", false)) {
                reservationName.setText(item.getUserName());
            }
            else {
                ((TextView) itemView.findViewById(R.id.reservationText1)).setText("업소명:");
                reservationName.setText(item.getBusinessName());
            }

            reservationId.setText(String.valueOf(item.getReservationId()));
            reservationStartDate.setText(item.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            reservationEndDate.setText(item.getEndDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            reservationCount.setText(String.valueOf(item.getCount()));
        }
    }
}
