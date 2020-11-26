package zeek1910.com.myapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import zeek1910.com.myapplication.R;
import zeek1910.com.myapplication.models.TableItem;

public class FullTimeTableRecyclerViewAdapter extends RecyclerView.Adapter<FullTimeTableRecyclerViewAdapter.FullTimeTableViewHolder>{

    private List<TableItem> data;

    public FullTimeTableRecyclerViewAdapter(List<TableItem> data){
        this.data = data;
    }


    @NonNull
    @Override
    public FullTimeTableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.full_time_table_recycler_view_item,parent,false);
        FullTimeTableViewHolder viewHolder = new FullTimeTableViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FullTimeTableViewHolder holder, int position) {
        holder.tvDay.setText(data.get(position).getDayName());
        holder.tvLessonName.setText(data.get(position).getLessonName());
        holder.tvLessonRoom.setText(data.get(position).getLessonRoom());
        holder.tvLessonGroups.setText(data.get(position).getLessonGroup());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class FullTimeTableViewHolder extends RecyclerView.ViewHolder{

        private TextView tvDay, tvLessonName, tvLessonRoom, tvLessonGroups;

        public FullTimeTableViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDay = itemView.findViewById(R.id.textViewDay);
            tvLessonName = itemView.findViewById(R.id.textViewLessonName);
            tvLessonRoom = itemView.findViewById(R.id.textViewRooms);
            tvLessonGroups = itemView.findViewById(R.id.textViewGroups);
        }
    }
}
