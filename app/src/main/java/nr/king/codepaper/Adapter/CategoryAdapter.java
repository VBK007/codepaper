package nr.king.codepaper.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import nr.king.codepaper.Common.Common;
import nr.king.codepaper.Model.Category;
import nr.king.codepaper.Model.Menu;
import nr.king.codepaper.R;

public class CategoryAdapter extends  RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    public Context context;
    public List<Category> category;
CategoryAdapter adapter;
    public CategoryAdapter(Context context, List<Category> category) {
        this.context = context;
        this.category = category;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.cardcategory,parent,false);
        return new CategoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        final Category categoy=category.get(position);

        Glide.with(context)
                .load(categoy.getImage())
                .into(holder.imageView);

        holder.textView.setText(categoy.getName().toUpperCase());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Posts");
reference.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {

        Intent intent=new Intent(context, nr.king.codepaper.List.class);
        Common.CATEGORY_NAME=categoy.getName();
        Common.CATEGORY_ID_SELECTED=categoy.getMenuId();

        context.startActivity(intent);





    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});













            }
        });







    }

    @Override
    public int getItemCount() {
        return category.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.catergoryimage);
          textView = itemView.findViewById(R.id.textView);


        }



    }
}
