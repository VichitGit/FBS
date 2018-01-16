package vichitpov.com.fbs.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import vichitpov.com.fbs.R;
import vichitpov.com.fbs.adapter.BookmarkCategoriesAdapter;
import vichitpov.com.fbs.model.BookmarkModel;

public class BookmarkCategoriesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark_categories);

        RecyclerView recyclerView = findViewById(R.id.recycler_bookmark);
        ImageView imageBack = findViewById(R.id.image_back);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        List<BookmarkModel> bookmarkList = new ArrayList<>();
        bookmarkList.add(new BookmarkModel(R.drawable.ic_about_us, "Computer"));
        bookmarkList.add(new BookmarkModel(R.drawable.ic_about_us, "Phone"));
        bookmarkList.add(new BookmarkModel(R.drawable.ic_about_us, "Car"));
        bookmarkList.add(new BookmarkModel(R.drawable.ic_about_us, "Moto"));
        bookmarkList.add(new BookmarkModel(R.drawable.ic_about_us, "Electronic"));


        BookmarkCategoriesAdapter adapter = new BookmarkCategoriesAdapter(bookmarkList, getApplicationContext());
        recyclerView.setAdapter(adapter);

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}
