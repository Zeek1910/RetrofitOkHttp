package zeek1910.com.myapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import zeek1910.com.myapplication.interfaces.OnRecyclerViewItemClickListener;
import zeek1910.com.myapplication.R;
import zeek1910.com.myapplication.models.Group;
import zeek1910.com.myapplication.models.Lecturer;

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.SearchViewHolder> {

    private final OnRecyclerViewItemClickListener itemClick;

    public static final int TYPE_LECTURERS = 1;
    public static final int TYPE_GROUPS = 2;

    List<Lecturer> lecturers;
    List<Group> groups;
    int type;

    public SearchRecyclerViewAdapter(List<Lecturer> lecturers, List<Group> groups, int type, OnRecyclerViewItemClickListener itemClick){
        this.lecturers = lecturers;
        this.groups = groups;
        this.type = type;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_recycler_view_item,parent,false);
        SearchViewHolder viewHolder = new SearchViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        if (type == TYPE_LECTURERS){
            holder.name.setText(lecturers.get(position).getActName());
        }
        if (type == TYPE_GROUPS){
            holder.name.setText(groups.get(position).getName());
        }
    }

    @Override
    public int getItemCount() {
        if (type == TYPE_LECTURERS){
            return lecturers.size();
        }
        if (type == TYPE_GROUPS){
            return groups.size();
        }
        return 0;
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder{
        TextView name;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    itemClick.onItemClick(pos);
                }
            });

            name = itemView.findViewById(R.id.name);
        }


    }
}
