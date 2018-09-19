package net.kibotu.android.recyclerviewpresenter.app;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;

import net.kibotu.android.recyclerviewpresenter.PresenterAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.text.TextUtils.isEmpty;
import static java.text.MessageFormat.format;
import static net.kibotu.android.recyclerviewpresenter.app.FakeDataGenerator.createRandomImageUrl;

/**
 * Created by <a href="https://about.me/janrabe">Jan Rabe</a>.
 */

public class MainActivity extends AppCompatActivity {

    @NonNull
    @BindView(R.id.list)
    RecyclerView list;

    Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        PresenterAdapter<String> adapter = new PresenterAdapter<>();
        list.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        list.setAdapter(adapter);

        adapter.setOnItemClickListener((item, rowView, position) -> toast(format("{0}. {1}", position, item)));

        for (int i = 0; i < 100; ++i) {
            adapter.add(createRandomImageUrl(), PhotoPresenter.class);
            adapter.add(createRandomImageUrl(), LabelPresenter.class);
        }

        // sorting
        // PresenterAdapter.sort(adapter);
        // sort if model doesn't implement Comparable
        // adapter.sortBy((o1, o2) -> o1.compareTo(o2));

//        adapter.update(0, "https://raw.githubusercontent.com/kibotu/RecyclerViewPresenter/master/screenshot.png");

        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


    public void toast(@Nullable final String message) {
        if (isEmpty(message))
            return;

        runOnUiThread(() -> {
            final Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM, 0, 100);
            toast.show();
        });
    }
}
