package zeek1910.com.myapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import zeek1910.com.myapplication.R;

public class FavoritesFragmentAdapter extends RecyclerView.Adapter<FavoritesFragmentAdapter.FavoritesViewHolder> {

    private List<String> data;

    public FavoritesFragmentAdapter(List<String> data){
        this.data = data;
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



    public class FavoritesViewHolder extends RecyclerView.ViewHolder{

        TextView textView;

        public FavoritesViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textView);
        }
    }
}
