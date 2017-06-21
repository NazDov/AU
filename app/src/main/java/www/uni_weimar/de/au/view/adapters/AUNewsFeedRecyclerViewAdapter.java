package www.uni_weimar.de.au.view.adapters;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.realm.RealmResults;
import www.uni_weimar.de.au.R;
import www.uni_weimar.de.au.models.AUNewsFeed;

/**
 * Created by ndovhuy on 19.06.2017.
 */

public class AUNewsFeedRecyclerViewAdapter extends RecyclerView.Adapter<AUNewsFeedRecyclerViewAdapter.NewsFeedVH> {

    private List<AUNewsFeed> auNewsFeedList;
    private Context context;


    public AUNewsFeedRecyclerViewAdapter(Context context, List<AUNewsFeed> auNewsFeedList) {
        this.auNewsFeedList = auNewsFeedList;
        this.context = context;
    }

    @Override
    public NewsFeedVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.au_single_news_feed_item, parent, false);
        return new NewsFeedVH(view);
    }

    @Override
    public void onBindViewHolder(NewsFeedVH holder, int position) {
        AUNewsFeed auNewsFeedItem = auNewsFeedList.get(position);
        holder.newsFeedTitle.setText(auNewsFeedItem.getTitle());
        holder.newsFeedDescription.setText(auNewsFeedItem.getDesciption());
        Glide.with(context).load(auNewsFeedItem.getImgUrl())
                .thumbnail(0.4f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.newsFeedImage)
                .onLoadFailed(null, context.getResources().getDrawable(R.drawable.news_article));
        SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss");
        String pubDate = auNewsFeedItem.getPubDate();
        try {
            Date dateOfPublication = formatter.parse(pubDate);
            Date currentDate = new Date();
            long duration = currentDate.getTime() - dateOfPublication.getTime();
            long diffInHours = TimeUnit.MILLISECONDS.toHours(duration);
            long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration - TimeUnit.HOURS.toMillis(diffInHours));
            if (diffInHours != 0) {
                holder.newsFeedTimeSince.setText(Long.toString(diffInHours) + " h " + Long.toString(diffInMinutes) + " min ago ");
            } else {
                holder.newsFeedTimeSince.setText(Long.toString(diffInMinutes) + " min ago ");
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int getItemCount() {
        if (auNewsFeedList != null) {
            return auNewsFeedList.size();
        }
        return 0;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    static class NewsFeedVH extends RecyclerView.ViewHolder {

        CardView auNewsFeedCardView;
        TextView newsFeedTitle;
        TextView newsFeedTimeSince;
        ImageView newsFeedImage;
        TextView newsFeedDescription;


        NewsFeedVH(View itemView) {
            super(itemView);
            auNewsFeedCardView = (CardView) itemView.findViewById(R.id.au_news_feed_card_view);
            newsFeedTitle = (TextView) itemView.findViewById(R.id.news_feed_title);
            newsFeedTimeSince = (TextView) itemView.findViewById(R.id.news_feed_time_since);
            newsFeedImage = (ImageView) itemView.findViewById(R.id.news_feed_image);
            newsFeedDescription = (TextView) itemView.findViewById(R.id.news_feed_description);
        }
    }

}
