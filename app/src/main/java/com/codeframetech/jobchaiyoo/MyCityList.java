package com.codeframetech.jobchaiyoo;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.v7.app.AppCompatActivity;
import android.widget.SearchView;

import com.codeframetech.jobchaiyoo.R;

public class MyCityList extends AppCompatActivity {
    public static String Pokhara_internship = "https://mydigitions.000webhostapp.com/meetup_table_data/meetup_list.php";
    public static String Pokhara_alljob = "https://mydigitions.000webhostapp.com/jobchaiyo_company_database/jobchaiyo_company_database.php";
    public static String Pokhara_meetup = "https://mydigitions.000webhostapp.com/meetup_table_data/meetup_list.php";
    public static String KTM_internship = "http://mydigitions.000webhostapp.com/internship/internship.php";
    public static String KTM_alljob = "https://mydigitions.000webhostapp.com/jobchaiyo_company_database/jobchaiyo_company_database.php";
    public static String KTM_meetup = "https://mydigitions.000webhostapp.com/meetup_table_data/meetup_list.php";

    ListView listview;
    SearchView searchView;
    String[] city = {"Pokhara", "Dharan", "Biratnagar", "Butwal"};
    ArrayAdapter<String> adapter;
    Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_city_list);
        listview = (ListView) findViewById(R.id.listView);
        searchView = (SearchView) findViewById(R.id.searchView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, city);
        listview.setAdapter(adapter);
        c = this.getApplicationContext();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                adapter.getFilter().filter(text);
                return false;
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String city_selected = adapter.getItem(position);
                assert city_selected != null;
                if (city_selected.equals(city_selected)) {
                    /*// code specific to first list item
                    Toast.makeText(MyCityList.this, "Pokhara Selected", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MyCityList.this, FacebookActivity.class);
                    Bundle extras = new Bundle();
                    extras.putString("JOB", Pokhara_alljob);
                    extras.putString("INTERNSHIP", Pokhara_internship);
                    extras.putString("MEETUP", Pokhara_meetup);
                    intent.putExtras(extras);
                    startActivity(intent);*/


                }

            }


        });
    }

}

