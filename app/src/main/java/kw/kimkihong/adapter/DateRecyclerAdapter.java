package kw.kimkihong.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import kw.kimkihong.assign3.R;
import kw.kimkihong.vo.DateVO;

import java.util.ArrayList;
import java.util.Calendar;

public class DateRecyclerAdapter extends RecyclerView.Adapter<DateRecyclerAdapter.ViewHolder> {
    private final ArrayList<DateVO> mCalendarList = new ArrayList<>();
    private OnItemClickListener mListener;
    private int clickedPosition;
    private ViewHolder clicked;

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.mListener = listener;
        this.clickedPosition = 0;
    }

    @NonNull
    @Override
    public DateRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DateRecyclerAdapter.ViewHolder holder, int position) {
        holder.onBind(mCalendarList.get(position));
    }

    @Override
    public int getItemCount() {
        return mCalendarList.size();
    }

    public void addDate() {
        for (int i = 0; i < 20; i++) {
            if(this.getItemCount() == 0) {
                this.mCalendarList.add(new DateVO(Calendar.getInstance()));
            }
            else {
                Calendar last = (Calendar) this.mCalendarList.get(this.getItemCount() - 1).getDate().clone();
                last.add(Calendar.DATE, 1);
                this.mCalendarList.add(new DateVO(last));
            }
        }
    }

    public interface OnItemClickListener
    {
        void onItemClick(View view, Calendar date);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout calender;
        TextView date;
        TextView day;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.calender = itemView.findViewById(R.id.calenderItem);
            this.date = itemView.findViewById(R.id.dateItem);
            this.day =  itemView.findViewById(R.id.dayItem);

            itemView.setOnClickListener(view -> {
                int position = getAbsoluteAdapterPosition();
                if(clickedPosition != position) {
                    clicked.calender.setBackgroundResource(R.drawable.calendar_item_background);
                    clicked.date.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.grey));
                    clicked.day.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.grey));
                    notifyItemChanged(clickedPosition);
                    clickedPosition = position;
                    clicked = this;
                    calender.setBackgroundResource(R.drawable.selected_calendar_item_background);
                    date.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.white));
                    day.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.white));
                }
                mListener.onItemClick(view, mCalendarList.get(getAbsoluteAdapterPosition()).getDate());
            });
        }

        void onBind(DateVO item){
            int position = getAbsoluteAdapterPosition();
            if(clicked == null && position == 0) {
                clicked = this;
            }
            if (clickedPosition == position) {
                calender.setBackgroundResource(R.drawable.selected_calendar_item_background);
                date.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.white));
                day.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.white));
            }
            else {
                calender.setBackgroundResource(R.drawable.calendar_item_background);
                date.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.grey));
                day.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.grey));
            }
            day.setText(item.getDay());
            date.setText(item.getNum());

        }
    }
}
