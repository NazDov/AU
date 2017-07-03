package www.uni_weimar.de.au.view.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.realm.Realm;
import www.uni_weimar.de.au.R;
import www.uni_weimar.de.au.models.AUNewsFeed;
import www.uni_weimar.de.au.models.AUNewsFeedFavourite;
import www.uni_weimar.de.au.orm.AUNewsFeedFavouriteORM;
import www.uni_weimar.de.au.orm.AUNewsFeedORM;

import static com.bumptech.glide.load.engine.DiskCacheStrategy.RESULT;


public class AUNewsFeedItemDetailedActivity extends AppCompatActivity {

    @InjectView(R.id.auNewsFeedItemDetailedImage)
    ImageView auNewsFeedItemDetailedImageView;
    @InjectView(R.id.auNewsFeedItemDescription)
    TextView auNewsFeedItemDetailedTextView;
    @InjectView(R.id.auNewsFeedCategoryName)
    TextView auNewsFeedCategoryName;
    @InjectView(R.id.auNewsFeedItemAuthor)
    TextView auNewsFeedItemAuthor;
    @InjectView(R.id.auNewsFeedItemPubDate)
    TextView auNewsFeedItemPubDate;
    AUNewsFeedORM auNewsFeedORM;
    AUNewsFeedFavouriteORM auNewsFeedFavouriteORM;
    Realm realm;
    AUNewsFeed auNewsFeedItem;
    @InjectView(R.id.auNewsFeedAddToFavourite)
    FloatingActionButton auNewsFeedLActionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aunews_feed_item_detailed);
        ButterKnife.inject(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String auNewsFeedItemLink = getIntent().getStringExtra("AUNewsFeedItemLink");
        realm = Realm.getDefaultInstance();
        auNewsFeedORM = new AUNewsFeedORM(realm);
        auNewsFeedFavouriteORM = new AUNewsFeedFavouriteORM(realm);
        auNewsFeedItem = auNewsFeedORM.findBy("link", auNewsFeedItemLink);
        getSupportActionBar().setTitle(auNewsFeedItem.getTitle());
        Glide.with(this)
                .load(auNewsFeedItem.getImgUrl())
                .diskCacheStrategy(RESULT)
                .placeholder(R.drawable.news_article)
                .into(auNewsFeedItemDetailedImageView);
        auNewsFeedCategoryName.setText(auNewsFeedItem.getCategory());
        auNewsFeedItemAuthor.setText(auNewsFeedItem.getAuthor());
        auNewsFeedItemPubDate.setText(auNewsFeedItem.getPubDate() != null ? auNewsFeedItem.getPubDate().replace("CEST", "") : "--//--//--");
        auNewsFeedItemDetailedTextView.setText(auNewsFeedItem.getDesciption());
        auNewsFeedItemDetailedTextView.setMovementMethod(new ScrollingMovementMethod());
        if (!auNewsFeedFavouriteORM.hasItem("link", auNewsFeedItemLink)) {
            auNewsFeedLActionBar.setImageResource(R.drawable.unselected_star);
        } else {
            auNewsFeedLActionBar.setImageResource(R.drawable.selected_star);
        }
        auNewsFeedLActionBar.setOnClickListener(v ->
        {
            if (!auNewsFeedFavouriteORM.hasItem("link", auNewsFeedItemLink)) {
                Toast.makeText(getBaseContext(), "adding item to favourites!", Toast.LENGTH_SHORT).show();
                AUNewsFeedFavourite auNewsFeedFavourite = new AUNewsFeedFavourite();
                auNewsFeedFavourite.setLink(auNewsFeedItemLink);
                auNewsFeedFavouriteORM.add(auNewsFeedFavourite);
                auNewsFeedLActionBar.setImageResource(R.drawable.selected_star);
            } else {
                Toast.makeText(getBaseContext(), "removing item to favourites!", Toast.LENGTH_SHORT).show();
                auNewsFeedFavouriteORM.delete("link", auNewsFeedItemLink);
                auNewsFeedLActionBar.setImageResource(R.drawable.unselected_star);
            }

        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
        if (realm != null) realm.close();
        realm = null;
    }
}
