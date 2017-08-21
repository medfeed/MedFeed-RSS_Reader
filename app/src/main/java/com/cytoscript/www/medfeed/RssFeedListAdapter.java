package com.cytoscript.www.medfeed;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Shamal Gunawardhana on 8/21/2017.
 */


public class RssFeedListAdapter  extends RecyclerView.Adapter<RssFeedListAdapter.FeedModelViewHolder> {

    private List<RssFeedModel> mRssFeedModels;

    public static class FeedModelViewHolder extends RecyclerView.ViewHolder {

        private View rssFeedView;





        public FeedModelViewHolder(View v) {

            super(v);


            rssFeedView = v;




        }

    }



    public RssFeedListAdapter(List<RssFeedModel> rssFeedModels) {


        mRssFeedModels = rssFeedModels;


    }



    @Override

    public FeedModelViewHolder onCreateViewHolder(ViewGroup parent, int type) {



        View v = LayoutInflater.from(parent.getContext())

                .inflate(R.layout.item_rss_feed,parent, false);

        FeedModelViewHolder holder = new FeedModelViewHolder(v);



        return holder;

    }



    @Override


    public void onBindViewHolder(final FeedModelViewHolder holder, final int position) {

        final RssFeedModel rssFeedModel = mRssFeedModels.get(position);

        ((TextView)holder.rssFeedView.findViewById(R.id.titleText)).setText(rssFeedModel.title);

        ((TextView)holder.rssFeedView.findViewById(R.id.descriptionText)).setText(rssFeedModel.description);

        //((TextView)holder.rssFeedView.findViewById(R.id.linkText)).setText(rssFeedModel.link);



        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    //working on it!!!

                }catch (Exception e){
                    Toast.makeText(v.getContext(),rssFeedModel.title, Toast.LENGTH_SHORT).show();

                }

            }
        });

    }



    @Override

    public int getItemCount() {

        return mRssFeedModels.size();

    }
}

