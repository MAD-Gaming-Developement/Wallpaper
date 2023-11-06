package wall.paper.hd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {



    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private DatabaseReference reference;
    private ArrayList<String> list;
    private WallpaperAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        reference = database.getReference("Image");


        //reference = FirebaseDatabase.getInstance().getReference().child("Image");


        list = new ArrayList<>();
        getData();
        adapter = new WallpaperAdapter(list, MainActivity.this);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);


        recyclerView.setAdapter(adapter);
    }

    private void getData(){
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressBar.setVisibility(View.GONE);

                for(DataSnapshot shot : snapshot.getChildren()){
                    String data = shot.getValue().toString();
                    list.add(data);
                }

                recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,2));

                progressBar.setVisibility(View.GONE);

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(MainActivity.this,"Error:"+error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });

    }
}