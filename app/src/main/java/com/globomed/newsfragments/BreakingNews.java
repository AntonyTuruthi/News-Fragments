package com.globomed.newsfragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BreakingNews extends Fragment {

    View v;
    private RecyclerView mRecyclerView;
    private List<Article> articleList;
    RequestQueue mRequestQueue;


    public BreakingNews() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.breaking_news_fragment, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.breaking_news_recyclerview);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(), articleList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(recyclerViewAdapter);

        breakingNews();

        return v;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initialize the article array list
        articleList = new ArrayList<>();
    }


    //Fetch the breaking news using Volley and add them to the array
    private void breakingNews() {

        String topHeadlinesUrl = "https://gnews.io/api/v4/top-headlines?&lang=en&token=494ecdb8399139e373cc23fe5386817f";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, topHeadlinesUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("articles");

                            for (int i = 0; i < jsonArray.length(); i++){
                                JSONObject article = jsonArray.getJSONObject(i);

                                String title = article.getString("title");
                                String rawdescription = article.getString("description");
                                String description = ( rawdescription.length () > 85 ) ? rawdescription.substring ( 0 , 86 - 1 ).concat ( "…" ): rawdescription;
                                String imageUrl = article.getString("image");
                                String contentUrl = article.getString("url");

                                articleList.add(new Article(imageUrl, title, description, contentUrl));

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mRequestQueue = Volley.newRequestQueue(getContext().getApplicationContext());
        mRequestQueue.add(request);

    }


}

