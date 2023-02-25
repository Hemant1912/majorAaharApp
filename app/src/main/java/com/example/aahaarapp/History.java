package com.example.aahaarapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class History extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookref = db.collection("user data");
    public static final String TAG = "TAG";
    private TextView textViewData;
    FirebaseAuth fAuth;
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
   private final List<myItems> myitemslist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        fAuth = FirebaseAuth.getInstance();
        //   textViewData=findViewById(R.id.data);
      //  Toast.makeText(History.this, "enter succes", Toast.LENGTH_SHORT).show();



       // loadNotes();

        final RecyclerView recyclerView = findViewById(R.id.historyrecycleview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(History.this));

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
             myitemslist.clear();
                for(DataSnapshot users : snapshot.child("Donor").getChildren()){
               //     Toast.makeText(History.this, "enter succes", Toast.LENGTH_SHORT).show();

                        final String getfullname = users.child("name").getValue(String.class);
                        final String getfoodItem = users.child("Food Item").getValue(String.class);
                     //   final String getphone = users.child("PhoneNo.").getValue(String.class);
                        final String getfulldesc = users.child("Description").getValue(String.class);

                        myItems items = new myItems(getfullname,getfoodItem,getfulldesc);

                        myitemslist.add(items);
                  //      Toast.makeText(History.this, "end Succes", Toast.LENGTH_SHORT).show();

                }

                recyclerView.setAdapter(new recycleviewAdapter(myitemslist,History.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void loadNotes() {
        notebookref.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            String data="";
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());//
                                if (document.contains("name") && document.contains("description") && document.contains("user type") && document.contains("userid")) {

                                    String name = (String) document.get("name");
                                    String type = (String) document.get("user type");
                                    String description = (String) document.get("description");
                                    String Userid = (String) document.get("userid");
                                    String userID = fAuth.getCurrentUser().getUid();
                                    Timestamp ts = (Timestamp) document.get("timestamp");
                                    //String dateandtime=String.valueOf(ts);
                                    String dateandtime=String.valueOf(ts.toDate());
                                    //String dateandtime = ts.toString();

                                    if(Userid.equals(userID)) {
                                        data += "Name: " + name + "\nUser Type: " + type + "\nDescription: " + description + "\nDate & Time: " + dateandtime + "\n\n";
                                        //data += "Name: " + name + "\nUser Type: " + type + "\nDescription: " + description + "\n";
                                    }
                                    textViewData.setText(data);
                                }
                            }
                            //textViewData.setText(data);
                        } else {
                            Log.d(TAG, "Error fetching data: ", task.getException());
                        }
                    }
                });
    }
}

