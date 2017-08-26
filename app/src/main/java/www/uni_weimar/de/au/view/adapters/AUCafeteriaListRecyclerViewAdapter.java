package www.uni_weimar.de.au.view.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import www.uni_weimar.de.au.R;
import www.uni_weimar.de.au.models.AUCafeteria;
import www.uni_weimar.de.au.view.listeners.AUItemChangeListener;

/**
 * Created by ndovhuy on 26.08.2017.
 */

public class AUCafeteriaListRecyclerViewAdapter extends RecyclerView.Adapter<AUCafeteriaListRecyclerViewAdapter.CafeteriaListItemHolder> {

    private List<AUCafeteria> mCafeterias;
    private AUItemChangeListener<AUCafeteria> mItemStateListener;

    public AUCafeteriaListRecyclerViewAdapter(List<AUCafeteria> auCafeterias) {
        this.mCafeterias = auCafeterias;
    }

    @Override
    public CafeteriaListItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.au_cafeteria_list_item, parent, false);
        return new CafeteriaListItemHolder(view);
    }

    @Override
    public void onBindViewHolder(CafeteriaListItemHolder holder, int position) {
        holder.mCafeteriaName.setText(mCafeterias.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mCafeterias != null && !mCafeterias.isEmpty() ? mCafeterias.size() : 0;
    }

    class CafeteriaListItemHolder extends RecyclerView.ViewHolder {
        TextView mCafeteriaName;
        CardView mCafeteriaWrapper;

        CafeteriaListItemHolder(View itemView) {
            super(itemView);
            mCafeteriaWrapper = (CardView) itemView.findViewById(R.id.au_cafeteria_card_view);
            mCafeteriaName = (TextView) itemView.findViewById(R.id.au_cafeteria_name);
            mCafeteriaWrapper.setOnClickListener(v -> {
                if (mItemStateListener != null)
                    mItemStateListener.onChanged(mCafeterias.get(getAdapterPosition()));
            });
        }
    }

    public void setOnItemChangeListener(AUItemChangeListener<AUCafeteria> mItemStateListener) {
        this.mItemStateListener = mItemStateListener;
    }

    public void setCafeterias(List<AUCafeteria> mCafeterias) {
        this.mCafeterias = mCafeterias;
        notifyDataSetChanged();
    }
}
