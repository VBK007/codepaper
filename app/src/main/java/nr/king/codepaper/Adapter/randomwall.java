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

import nr.king.codepaper.Common.Common;
import nr.king.codepaper.R;
import nr.king.codepaper.viewwall;

public class randomwall extends RecyclerView.Adapter<randomwall.ViewHolder> {
public Context context;
//public AdapterHandler adapterHandler;
public List<nr.king.codepaper.Model.List> listList;
nr.king.codepaper.Model.List list;
    public randomwall(Context context, List<nr.king.codepaper.Model.List> listList) {
        this.context = context;
        this.listList = listList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardrecy,parent,false);
        int height=parent.getMeasuredHeight()/2;
        parent.setMinimumHeight(height);
        return new randomwall.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       list=listList.get(position);
        Glide.with(context)
                .load(list.getImage())
                .into(holder.imageView);


        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




            }
        });


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





        public void updateimage(ImageView imageView){
            Common.LIST_WALLPAER=list.getImage();

        }





}
