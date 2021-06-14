package kw.kimkihong.assign3.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import kw.kimkihong.assign3.R;
import kw.kimkihong.assign3.vo.ReservationVO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ReservationRecyclerAdapter extends RecyclerView.Adapter<ReservationRecyclerAdapter.ViewHolder> {
    private final ArrayList<ReservationVO> mReservationList = new ArrayList<>();
    private ReservationRecyclerAdapter.OnReservationItemClickListener mListener;

    public void setOnItemClickListener(ReservationRecyclerAdapter.OnReservationItemClickListener listener)
    {
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
        return new ReservationRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationRecyclerAdapter.ViewHolder holder, int position) {
        holder.onBind(mReservationList.get(position));
    }

    @Override
    public int getItemCount() {
        return mReservationList.size();
    }

    public void addReservation(Calendar date) {
        if(mReservationList.size() != 0) {
            mReservationList.clear();
        }
    }

    public interface OnReservationItemClickListener
    {
        void onItemClick(View view, ReservationVO post);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView reservationId;
        TextView reservationStartDate;
        TextView reservationEndDate;
        TextView reservationName;
        TextView reservationCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.reservationId = itemView.findViewById(R.id.reservationId);
            this.reservationStartDate = itemView.findViewById(R.id.reservationStartDate);
            this.reservationEndDate =  itemView.findViewById(R.id.reservationEndDate);
            this.reservationName =  itemView.findViewById(R.id.reservationName);
            this.reservationCount =  itemView.findViewById(R.id.reservationCount);

            itemView.setOnClickListener(view -> {
                mListener.onItemClick(view, mReservationList.get(getAbsoluteAdapterPosition()));
            });
        }

        void onBind(ReservationVO item){
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
            reservationId.setText(item.getReservationID());
            reservationStartDate.setText(df.format(item.getStartDate()));
            reservationEndDate.setText(df.format(item.getEndDate()));
            reservationName.setText(item.getName());
            reservationCount.setText(item.getCount());
        }
    }
}
