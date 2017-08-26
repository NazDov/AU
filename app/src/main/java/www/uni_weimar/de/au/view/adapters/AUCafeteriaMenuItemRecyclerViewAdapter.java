package www.uni_weimar.de.au.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import www.uni_weimar.de.au.R;
import www.uni_weimar.de.au.models.AUCafeteriaMenuItem;

/**
 * Created by nazar on 06.08.17.
 */

public class AUCafeteriaMenuItemRecyclerViewAdapter extends RecyclerView.Adapter<AUCafeteriaMenuItemRecyclerViewAdapter.CafeteriaMenuProgramViewHolder> {

    private final List<AUCafeteriaMenuItem> auCafeteriaMenuItemList;
    private final Context context;

    public AUCafeteriaMenuItemRecyclerViewAdapter(Context context, List<AUCafeteriaMenuItem> auCafeteriaMenuItemList) {
        this.auCafeteriaMenuItemList = auCafeteriaMenuItemList;
        this.context = context;
    }

    @Override
    public CafeteriaMenuProgramViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.au_cafeteria_menu_single_item, parent, false);
        return new CafeteriaMenuProgramViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(CafeteriaMenuProgramViewHolder holder, int position) {
        AUCafeteriaMenuItem auCafeteriaMenuItem = auCafeteriaMenuItemList.get(position);
        holder.auCafeteriaMenuName.setText(auCafeteriaMenuItem.getAuMenuItemTag());
        holder.auCafeteriaMenuDescription.setText(auCafeteriaMenuItem.getAuMenuItemDescription());

    }

    @Override
    public int getItemCount() {
        return auCafeteriaMenuItemList != null ? auCafeteriaMenuItemList.size() : 0;
    }

    class CafeteriaMenuProgramViewHolder extends RecyclerView.ViewHolder {

        TextView auCafeteriaMenuName;
        TextView auCafeteriaMenuDescription;
        ImageView auCafeteriaMenuLikeBtn;
        boolean isSelected;

        CafeteriaMenuProgramViewHolder(View itemView) {
            super(itemView);
            auCafeteriaMenuName = (TextView) itemView.findViewById(R.id.au_cafeteria_menu_name);
            auCafeteriaMenuDescription = (TextView) itemView.findViewById(R.id.au_cafeteria_menu_description);
            auCafeteriaMenuLikeBtn = (ImageView) itemView.findViewById(R.id.au_cafeteria_item_like);
            auCafeteriaMenuLikeBtn.setOnClickListener(v -> {
                AUCafeteriaMenuItem selectedMenuItem = auCafeteriaMenuItemList.get(getAdapterPosition());
                if (!isSelected) {
                    v.setBackgroundResource(R.drawable.selected_star);
                    isSelected = true;
                } else {
                    v.setBackgroundResource(R.drawable.unselected_star);
                    isSelected = false;
                }
            });
        }
    }
}
