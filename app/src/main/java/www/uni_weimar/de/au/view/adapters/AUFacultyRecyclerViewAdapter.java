package www.uni_weimar.de.au.view.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import www.uni_weimar.de.au.R;
import www.uni_weimar.de.au.models.AUFacultyHeader;
import www.uni_weimar.de.au.view.listeners.AUItemChangeListener;

/**
 * Created by nazar on 13.07.17.
 */

public class AUFacultyRecyclerViewAdapter extends RecyclerView.Adapter<AUFacultyRecyclerViewAdapter.FacultyHolder> {

    private Context context;
    private List<AUFacultyHeader> auFaculties;
    private AUItemChangeListener<AUFacultyHeader> auItemChangeListener;

    public AUFacultyRecyclerViewAdapter(Context context, List<AUFacultyHeader> auFacultyHeaderList) {
        this.context = context;
        this.auFaculties = auFacultyHeaderList;
    }


    @Override
    public FacultyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.au_single_course_item, parent, false);
        return new FacultyHolder(view);
    }

    @Override
    public void onBindViewHolder(FacultyHolder holder, int position) {
        holder.auScheduleTextView.setText(auFaculties.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return auFaculties == null ? 0 : auFaculties.size();
    }

    class FacultyHolder extends RecyclerView.ViewHolder {

        private TextView auScheduleTextView;
        private CardView auScheduleCardView;

        FacultyHolder(View itemView) {
            super(itemView);
            auScheduleTextView = (TextView) itemView.findViewById(R.id.au_schedule_name);
            auScheduleCardView = (CardView) itemView.findViewById(R.id.au_schedule_card_view);
            auScheduleCardView.setOnClickListener(v -> {
                if (auItemChangeListener != null) {
                    auItemChangeListener.onChanged(auFaculties.get(getAdapterPosition()));
                }
            });
        }
    }


    public void setAuFacultyHeaders(List<AUFacultyHeader> auFaculties) {
        this.auFaculties = auFaculties;
    }

    public void setOnItemClickListener(AUItemChangeListener<AUFacultyHeader> auItemChangeListener) {
        this.auItemChangeListener = auItemChangeListener;
    }
}
