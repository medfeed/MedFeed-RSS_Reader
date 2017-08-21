package com.cytoscript.www.medfeed;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";
    public TextView textView0,textView1;
    Integer x=0;


    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeLayout;

    private List<RssFeedModel> mFeedModelList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        textView0=(TextView)findViewById(R.id.textView0);
        textView1=(TextView)findViewById(R.id.textView1);
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        if (x==0){
            new FetchFeedTask().execute((Void) null);


        }else {
            new FetchFeedTask1().execute((Void) null);

        }






        textView0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FetchFeedTask().execute((Void) null);
                x=0;



            }
        });
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FetchFeedTask1().execute((Void) null);
                x=1;



            }
        });




        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override

            public void onRefresh() {

                if (x==0){
                    new FetchFeedTask().execute((Void) null);


                }else {
                    new FetchFeedTask1().execute((Void) null);


                }



            }

        });

    }




    public List<RssFeedModel> parseFeed(InputStream inputStream) throws XmlPullParserException, IOException {

        String title = null;

        String link = null;

        String description = null;

        boolean isItem = false;

        List<RssFeedModel> items = new ArrayList<>();



        try {

            XmlPullParser xmlPullParser = Xml.newPullParser();

            xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);

            xmlPullParser.setInput(inputStream, null);



            xmlPullParser.nextTag();

            while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT) {

                int eventType = xmlPullParser.getEventType();



                String name = xmlPullParser.getName();

                if(name == null)

                    continue;



                if(eventType == XmlPullParser.END_TAG) {

                    if(name.equalsIgnoreCase("item")) {

                        isItem = false;

                    }

                    continue;

                }



                if (eventType == XmlPullParser.START_TAG) {

                    if(name.equalsIgnoreCase("item")) {

                        isItem = true;

                        continue;

                    }

                }



                Log.d("MainActivity", "Parsing name ==> " + name);

                String result = "";

                if (xmlPullParser.next() == XmlPullParser.TEXT) {

                    result = xmlPullParser.getText();

                    xmlPullParser.nextTag();

                }



                if (name.equalsIgnoreCase("title")) {

                    title = result;

                } else if (name.equalsIgnoreCase("link")) {

                    link = result;

                } else if (name.equalsIgnoreCase("description")) {

                    description = result;

                }



                if (title != null && link != null && description != null) {

                    if(isItem) {

                        RssFeedModel item = new RssFeedModel(title, link, description);

                        items.add(item);

                    }

                    else {

                    }



                    title = null;

                    link = null;

                    description = null;

                    isItem = false;

                }

            }



            return items;

        } finally {

            inputStream.close();

        }

    }


    private class FetchFeedTask extends AsyncTask<Void, Void, Boolean> {



        private String urlLink;



        @Override

        protected void onPreExecute() {

            mSwipeLayout.setRefreshing(true);



            urlLink ="https://rss.medicalnewstoday.com/featurednews.xml";

        }



        @Override

        protected Boolean doInBackground(Void... voids) {




            try {



                URL url = new URL(urlLink);

                InputStream inputStream = url.openConnection().getInputStream();

                mFeedModelList = parseFeed(inputStream);

                return true;

            } catch (IOException e) {

                Log.e(TAG, "Error", e);

            } catch (XmlPullParserException e) {

                Log.e(TAG, "Error", e);

            }

            return false;

        }



        @Override

        protected void onPostExecute(Boolean success) {

            mSwipeLayout.setRefreshing(false);



            if (success) {

                // Fill RecyclerView

                mRecyclerView.setAdapter(new RssFeedListAdapter(mFeedModelList));

            } else {

                Toast.makeText(MainActivity.this,

                        "Unknown Error",

                        Toast.LENGTH_LONG).show();

            }

        }

    }



    private class FetchFeedTask1 extends AsyncTask<Void, Void, Boolean> {



        private String urlLink;



        @Override

        protected void onPreExecute() {

            mSwipeLayout.setRefreshing(true);



            urlLink ="http://feeds.bbci.co.uk/news/health/rss.xml";

        }



        @Override

        protected Boolean doInBackground(Void... voids) {




            try {



                URL url = new URL(urlLink);

                InputStream inputStream = url.openConnection().getInputStream();

                mFeedModelList = parseFeed(inputStream);

                return true;

            } catch (IOException e) {

                Log.e(TAG, "Error", e);

            } catch (XmlPullParserException e) {

                Log.e(TAG, "Error", e);

            }

            return false;

        }



        @Override

        protected void onPostExecute(Boolean success) {

            mSwipeLayout.setRefreshing(false);



            if (success) {

                // Fill RecyclerView

                mRecyclerView.setAdapter(new RssFeedListAdapter(mFeedModelList));

            } else {

                Toast.makeText(MainActivity.this,

                        "Unknown Error",

                        Toast.LENGTH_LONG).show();

            }

        }

    }





}
