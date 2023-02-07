package com.mandiri.newsapi;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.mandiri.newsapi.adapters.Adapter;
import com.mandiri.newsapi.adapters.CategoryAdapter;
import com.mandiri.newsapi.api.ApiClient;
import com.mandiri.newsapi.api.ApiInterface;
import com.mandiri.newsapi.models.Article;
import com.mandiri.newsapi.models.News;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    public static final String API_KEY = "43334906f98b468e837b95e112cb27b0";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<String> kategori;
    private CategoryAdapter adapter;
    private String TAG = CategoryActivity.class.getSimpleName();
    private TextView topHeadline;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RelativeLayout errorLayout;
    private ImageView errorImage;
    private TextView errorTitle, errorMessage;
    private Button btnRetry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);

        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(CategoryActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

        onLoadingSwipeRefresh("");

        errorLayout = findViewById(R.id.errorLayout);
        errorImage = findViewById(R.id.errorImage);
        errorTitle = findViewById(R.id.errorTitle);
        errorMessage = findViewById(R.id.errorMessage);
        btnRetry = findViewById(R.id.btnRetry);
    }

    public void LoadJson(final String keyword) {

        errorLayout.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(true);

        kategori = new ArrayList<>();
        kategori.add("Business");
        kategori.add("Entertainment");
        kategori.add("Health");
        kategori.add("Science");
        kategori.add("Sports");
        kategori.add("Technology");

        adapter = new CategoryAdapter(kategori, CategoryActivity.this);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        initListener();

        swipeRefreshLayout.setRefreshing(false);
    }


    private void initListener() {

        adapter.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                ImageView imageView = view.findViewById(R.id.img);
                Intent intent = new Intent(CategoryActivity.this, MainActivity.class);

                String article = kategori.get(position);
                intent.putExtra("key", article.toLowerCase());

                startActivity(intent);
            }
        });

    }

    @Override
    public void onRefresh() {
        LoadJson("");
    }

    private void onLoadingSwipeRefresh(final String keyword) {

        swipeRefreshLayout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        LoadJson(keyword);
                    }
                }
        );

    }
}
