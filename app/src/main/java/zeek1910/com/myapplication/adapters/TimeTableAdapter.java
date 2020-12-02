package zeek1910.com.myapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
//        holder.tvDay.setVisibility(View.GONE);
//        holder.tvDay.setText(data.get(position).getDayName());
//        int lessonNumber = data.get(position).getLessonNumber();
//        String lessonName = data.get(position).getLessonName();
//        String lessonGroups = data.get(position).getLessonGroup();
//        String lessonRoom = data.get(position).getLessonRoom();
//        holder.tvLessonName.setText(lessonName);
//        holder.tvLessonGroups.setText(lessonGroups);
//        holder.tvLessonRoom.setText(lessonRoom);
//        if(lessonName.equals("")) {
//            holder.tvLessonName.setText("нету");
//        }
//        if(position == 0 || position == 8 || position == 16 || position == 24 || position == 32){
//            holder.tvDay.setVisibility(View.VISIBLE);
//        }
//
//        if (position % 2 != 0){
//            holder.layout.setBackgroundResource(R.drawable.item_bg_second_week);
//        }else{
//            holder.layout.setBackgroundResource(R.drawable.item_bg_first_week);
//        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class TimeTableViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout layout;

        TextView tvDay, tvLessonName, tvLessonRoom, tvLessonGroups;

        public TimeTableViewHolder(@NonNull View itemView) {
            super(itemView);

            layout = itemView.findViewById(R.id.rootItemLayout);

            tvDay = itemView.findViewById(R.id.textViewDayNasme);
            tvLessonName = itemView.findViewById(R.id.textViewLessonName);
            tvLessonRoom = itemView.findViewById(R.id.textViewRooms);
            tvLessonGroups = itemView.findViewById(R.id.textViewGroups);
        }
    }
}
