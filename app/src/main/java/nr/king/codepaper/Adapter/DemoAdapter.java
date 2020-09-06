package nr.king.codepaper.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import nr.king.codepaper.Model.Demo;
import nr.king.codepaper.R;

public class DemoAdapter extends RecyclerView.Adapter<DemoAdapter.ViewHolder> {
public Context context;
public List<Demo> demos;

    public DemoAdapter(Context context, List<Demo> demos) {
        this.context = context;
        this.demos = demos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.demolay,parent,false);
        int height=parent.getMeasuredHeight()/2;
        view.setMinimumHeight(height);

        return new DemoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
Demo demo=demos.get(position);
        Glide.with(context).load(demo.getImage()).into(holder.imageView);
        holder.textView.setText(demo.getSteps());

    }

    @Override
    public int getItemCount() {
        return demos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
public ImageView imageView;
public TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.demoimage);
            textView=itemView.findViewById(R.id.demotext);

        }




    }
}
