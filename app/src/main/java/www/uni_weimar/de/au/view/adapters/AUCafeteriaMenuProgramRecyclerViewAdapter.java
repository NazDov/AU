package www.uni_weimar.de.au.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import www.uni_weimar.de.au.R;

/**
 * Created by nazar on 06.08.17.
 */

public class AUCafeteriaMenuProgramRecyclerViewAdapter extends RecyclerView.Adapter<AUCafeteriaMenuProgramRecyclerViewAdapter.CafeteriaMenuProgramViewHolder> {

    @Override
    public CafeteriaMenuProgramViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.au_cafeteria_single_item, parent, false);
        return new CafeteriaMenuProgramViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(CafeteriaMenuProgramViewHolder holder, int position) {
        holder.auCafeteriaMenuName.setText("Name");
        holder.auCafeteriaMenuDescription.setText("lorem ipsum dolor sit amen");
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    static class CafeteriaMenuProgramViewHolder extends RecyclerView.ViewHolder {

        TextView auCafeteriaMenuName;
        TextView auCafeteriaMenuDescription;

        CafeteriaMenuProgramViewHolder(View itemView) {
            super(itemView);
            auCafeteriaMenuName = (TextView) itemView.findViewById(R.id.au_cafeteria_menu_name);
            auCafeteriaMenuDescription = (TextView) itemView.findViewById(R.id.au_cafeteria_menu_description);
        }
    }
}
