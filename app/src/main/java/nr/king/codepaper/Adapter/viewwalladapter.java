package nr.king.codepaper.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import nr.king.codepaper.R;

public class viewwalladapter extends RecyclerView.Adapter<viewwalladapter.ViewHolder>{

public Context context;
public List<nr.king.codepaper.Model.List> listList;


    public viewwalladapter(Context context, List<nr.king.codepaper.Model.List> listList) {
        this.context = context;
        this.listList = listList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardrecy,parent,false);
        int height=parent.getMeasuredHeight()/2;
        view.setMinimumHeight(height);
        return new viewwalladapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final nr.king.codepaper.Model.List listar=listList.get(position);
        Glide.with(context)
                .load(listar.getImage())
                .into(holder.imageView);





    }

    @Override
    public int getItemCount() {
        return listList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.catergoryimage);

        }
    }
}
