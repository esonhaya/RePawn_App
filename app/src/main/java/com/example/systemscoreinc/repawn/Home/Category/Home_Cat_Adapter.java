package com.example.systemscoreinc.repawn.Home.Category;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.systemscoreinc.repawn.R;

import java.util.List;


/**
 * Adapter used to show a simple grid of products.
 */
public class Home_Cat_Adapter extends RecyclerView.Adapter<Home_Cat_ViewHolder> {
    private List<CategoryList> mDataset;
    private Context Ctx;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder

    // Provide a suitable constructor (depends on the kind of dataset)
    public Home_Cat_Adapter(Context context, List<CategoryList> myDataset) {
        mDataset = myDataset;
        Ctx = context;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public Home_Cat_ViewHolder onCreateViewHolder(ViewGroup parent,
                                                  int viewType) {
        // create a new view
        View layoutView = LayoutInflater.from(Ctx).inflate(R.layout.main_cat, parent, false);
        return new Home_Cat_ViewHolder(layoutView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull Home_Cat_ViewHolder holder, final int position) {

        final CategoryList catlist = mDataset.get(position);
        holder.CatName.setText(catlist.getCat_name());
        // holder.CatCount.setText(catlist.getCat_counts()+" products");
//        holder.PawnedName.setText(ppllist.getPawnedname());
//        holder.RePawnerName.setText(ppllist.getRepawnername());
//        holder.PawnedPrice.setText("₱ "+String.format("%.2f",ppllist.getPawned_price()));
        //   holder.CatName.setText();

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent to_pawned=new Intent(Ctx, Pawned_Info.class);
//                to_pawned.putExtra("pid",ppllist.getPawned_id());
//                to_pawned.putExtra("repid",ppllist.getR_id());
//                to_pawned.putExtra("p_cat",ppllist.getPcat());
//                to_pawned.putExtra("repname",ppllist.getRepawnername());
//                to_pawned.putExtra("pname",ppllist.getPawnedname());
//                to_pawned.putExtra("p_desc",ppllist.getPdes());
//                to_pawned.putExtra("price",ppllist.getPawned_price());
//                Ctx.startActivity(to_pawned);
            }
        });


        //  holder.productTitle.setText(); // Here's your value

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}