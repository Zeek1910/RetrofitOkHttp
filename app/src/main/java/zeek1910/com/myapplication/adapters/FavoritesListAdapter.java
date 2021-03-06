package zeek1910.com.myapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import zeek1910.com.myapplication.AppSettings;
import zeek1910.com.myapplication.R;
import zeek1910.com.myapplication.activities.TimeTableActivity;

public class FavoritesListAdapter extends RecyclerView.Adapter<FavoritesListAdapter.FavoritesViewHolder> {

    private List<String> data;
    private Context context;
    private AppSettings appSettings;

    public FavoritesListAdapter(Context cntx){
        context = cntx;
        appSettings = new AppSettings(cntx);
        data = appSettings.getFavoritesTimeTables();
    }

    @NonNull
    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorites_fragment_list_item,parent,false);
        return new FavoritesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesViewHolder holder, int position) {
        holder.textView.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void updateAdapter(){
        this.notifyDataSetChanged();
    }



    public class FavoritesViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        ImageButton btnDel;

        public FavoritesViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textView);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, TimeTableActivity.class);
                    intent.putExtra(TimeTableActivity.KEY_FULLNAME, textView.getText().toString());
                    context.startActivity(intent);
                }
            });

            btnDel = itemView.findViewById(R.id.imageButton);
            btnDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    appSettings.removeTimeTableFromFavorites(data.get(pos));
                    data.remove(pos);
                    updateAdapter();
                }
            });
        }
    }
}
