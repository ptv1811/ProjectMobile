package com.example.login;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListFood extends AppCompatActivity implements FoodAdapter.ItemClickListener {
    FoodAdapter adapter;
    private ArrayList<myItem> listItems=new ArrayList<>();
    int positionClick=-1;
    private User thisuser;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_food);
        final RecyclerView myRecyclerView=(RecyclerView)findViewById(R.id.recycle_view);
        //
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        final String userId=getIntent().getStringExtra("Uid");
        final String email=getIntent().getStringExtra("nusername");
        final String pass=getIntent().getStringExtra("password");
        Log.d("Uid",userId);
        myRef.child("Users/").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if(user==null)
                {
                    Log.d("nana","value");
                    thisuser =new User(email,userId,pass,listItems);
                    myRef.child("Users/").child(userId+"/").setValue(thisuser);
                }
                else
                {
                    Log.d("lala","aa"+user.getEmail());
                    thisuser=user;
                    if(!(user.getListUserItem()==null))
                    {
                        listItems=user.getListUserItem();
                        Log.d("momo",String.valueOf(user.getListUserItem().size()));
                        Log.d("momo1",String.valueOf(listItems.size()));
                        adapter = new FoodAdapter(ListFood.this,listItems);
                        adapter.setClickListener(ListFood.this);
                        myRecyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
//
        Log.d("momo4",String.valueOf(listItems.size()));
        ImageView addingButton=(ImageView) findViewById(R.id.adding_button);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new FoodAdapter(this,listItems);
        adapter.setClickListener(this);
        final Context context=this;
        Log.d("size",String.valueOf(listItems.size()));
        myRecyclerView.setAdapter(adapter);
        addingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context,ItemViewing.class);
                i.putExtra("nameFood","Restaurant name");
                i.putExtra("locationFood","Address");
                i.putExtra("checkinFood","Last check-in");
                i.putExtra("priceFood","Price");
                i.putExtra("phoneFood","Phone number");
                i.putExtra("positionClick","NoPosition");
                startActivityForResult(i,1);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent i=new Intent(this,ItemViewing.class);
        i.putExtra("nameFood",adapter.getItem(position).nameFood);
        i.putExtra("locationFood",adapter.getItem(position).Location);
        i.putExtra("checkinFood",adapter.getItem(position).Checkin);
        i.putExtra("priceFood",adapter.getItem(position).price);
        i.putExtra("phoneFood",adapter.getItem(position).phoneNumber);
        i.putExtra("positionClick",String.valueOf(position));
        positionClick=position;
        startActivityForResult(i,1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that it is the SecondActivity with an OK result
        if (requestCode == 1) {
            Log.d("A","ss");
            if (resultCode == RESULT_OK) {
                Log.d("B","ss");
                // Get String data from Intent
                String returnStringname = data.getStringExtra("nameFoodBack");
                String returnStringLocation=data.getStringExtra("locationFoodBack");
                String returnStringTel=data.getStringExtra("phoneFoodBack");
                String returnStringCheckin=data.getStringExtra("checkinFoodBack");
                String returnStringPrice=data.getStringExtra("priceFoodBack");
                // Set text view with string
                myItem tmp=new myItem(returnStringname,returnStringLocation,returnStringTel,returnStringCheckin,returnStringPrice);
                String checking=data.getStringExtra("positionClickBack");
                String uid=getIntent().getStringExtra("Uid");
                if(checking.equals("NoPosition"))
                {
                    listItems.add(listItems.size(),tmp);
                    Log.d("Hello I'm Here","ss");
                    adapter.notifyItemInserted(listItems.size()-1);
                }
                else
                {
                    Log.d("Check",data.getStringExtra("positionClickBack"));
                    int position=Integer.parseInt(checking);
                    listItems.set(position,tmp);
                    adapter.notifyItemChanged(position);
                }
                thisuser.setList(listItems);
                myRef.child("Users/").child(uid+"/").setValue(thisuser);
            }
            if(resultCode==RESULT_CANCELED)
            {
                Log.d("Cancelled","ss");
            }
        }
    }
    public void setList(ArrayList<myItem> list)
    {
        this.listItems=list;
    }
    public void setUser(User user)
    {
        this.thisuser=user;
    }
}
