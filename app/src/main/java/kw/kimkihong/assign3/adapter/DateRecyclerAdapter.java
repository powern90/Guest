package kw.kimkihong.assign3.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import kw.kimkihong.assign3.R;
import kw.kimkihong.assign3.vo.DateVO;

import java.util.ArrayList;
import java.util.Calendar;

public class DateRecyclerAdapter extends RecyclerView.Adapter<DateRecyclerAdapter.ViewHolder> {
    //declare each variable
    private final ArrayList<DateVO> mCalendarList = new ArrayList<>();
    private OnItemClickListener mListener;
    private int clickedPosition;
    private ViewHolder clicked;

    //make on item click listener
    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.mListener = listener;
        this.clickedPosition = 0;
    }

    //declare on create view holder
    @NonNull
    @Override
    public DateRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //set view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_item, parent, false);
        return new ViewHolder(view);
    }

    //declare onbind holder
    @Override
    public void onBindViewHolder(@NonNull DateRecyclerAdapter.ViewHolder holder, int position) {
        holder.onBind(mCalendarList.get(position));
    }

    //declare count function
    @Override
    public int getItemCount() {
        return mCalendarList.size();
    }

    //declare add element function
    public void addDate() {
        for (int i = 0; i < 20; i++) {
            if(this.getItemCount() == 0) {
                this.mCalendarList.add(new DateVO(Calendar.getInstance()));
            }
            else {
                //calculate new element value
                Calendar last = (Calendar) this.mCalendarList.get(this.getItemCount() - 1).getDate().clone();
                last.add(Calendar.DATE, 1);
                this.mCalendarList.add(new DateVO(last));
            }
        }
    }

    //declare interface for item click listener
    public interface OnItemClickListener {
        void onItemClick(View view, Calendar date);
    }

    //declare viewholder
    class ViewHolder extends RecyclerView.ViewHolder {
        //set view variable
        LinearLayout calender;
        TextView date;
        TextView day;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //set view variable
            this.calender = itemView.findViewById(R.id.calenderItem);
            this.date = itemView.findViewById(R.id.dateItem);
            this.day =  itemView.findViewById(R.id.dayItem);

            //set onclick listener
            itemView.setOnClickListener(view -> {
                //get absolute position of element
                int position = getAbsoluteAdapterPosition();
                if(clickedPosition != position) {
                    //set select item and not selected item by position
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

        //declare bind function
        void onBind(DateVO item){
            int position = getAbsoluteAdapterPosition();
            //refresh every data to prevent malfunction
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
