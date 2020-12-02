package zeek1910.com.myapplication.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import zeek1910.com.myapplication.R;
import zeek1910.com.myapplication.models.LecturerTableItem;

public class TimeTableAdapter extends RecyclerView.Adapter<TimeTableAdapter.TimeTableViewHolder>{

    private List<LecturerTableItem> data;

    public TimeTableAdapter(List<LecturerTableItem> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public TimeTableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.time_table_list_item,parent,false);
        TimeTableViewHolder viewHolder = new TimeTableViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TimeTableViewHolder holder, int position) {
        holder.tvDayName.setText(data.get(position).getDayName());
        holder.tvDayName.setVisibility(View.GONE);
        if(position == 0 || position == 4 || position == 8 || position == 12 || position == 16){
            holder.tvDayName.setVisibility(View.VISIBLE);
        }

        int lessonNumber = data.get(position).getLessonNumber();
        String time = "";
        switch (lessonNumber){
            case 1:
                time = "08:00 - 09:35";
                break;
            case 2:
                time = "09:50 - 11:25";
                break;
            case 3:
                time = "11:55 - 13:30";
                break;
            case 4:
                time = "13:30 - 15:20";
                break;
        }
        holder.tvLessonNumber.setText(time);

        String topLessonName = data.get(position).getTopLessonName();
        String topLessonOwner = data.get(position).getTopLessonGroup();
        String topLessonRoom = data.get(position).getTopLessonRoom();

        String botLessonName = data.get(position).getBotLessonName();
        String botLessonOwner = data.get(position).getBotLessonGroup();
        String botLessonRoom = data.get(position).getBotLessonRoom();

        holder.tvTopLessonName.setText(topLessonName);
        holder.tvTopLessonOwner.setText(topLessonOwner);
        holder.tvTopLessonRoom.setText(topLessonRoom);

        if(topLessonName.equals(botLessonName) && topLessonOwner.equals(botLessonOwner) && topLessonRoom.equals(botLessonRoom)){
            holder.layoutBot.setVisibility(View.GONE);
        }else{
            holder.tvBotLessonName.setText(botLessonName);
            holder.tvBotLessonOwner.setText(botLessonOwner);
            holder.tvBotLessonRoom.setText(botLessonRoom);
            holder.layoutBot.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class TimeTableViewHolder extends RecyclerView.ViewHolder{

        LinearLayout layoutBot;

        TextView tvDayName, tvLessonNumber, tvTopLessonName, tvTopLessonRoom, tvTopLessonOwner, tvBotLessonName, tvBotLessonRoom, tvBotLessonOwner;

        public TimeTableViewHolder(@NonNull View itemView) {
            super(itemView);

            layoutBot = itemView.findViewById(R.id.lessonBot);

            tvDayName = itemView.findViewById(R.id.textViewDayName);
            tvLessonNumber = itemView.findViewById(R.id.textViewLessonNumber);

            tvTopLessonName = itemView.findViewById(R.id.textViewTopLessonName);
            tvTopLessonRoom = itemView.findViewById(R.id.textViewTopRoom);
            tvTopLessonOwner = itemView.findViewById(R.id.textViewTopOwner);

            tvBotLessonName = itemView.findViewById(R.id.textViewBotLessonName);
            tvBotLessonRoom = itemView.findViewById(R.id.textViewBotRoom);
            tvBotLessonOwner = itemView.findViewById(R.id.textViewBotOwner);
        }
    }
}
