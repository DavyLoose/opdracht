package be.howest.davyloose.opdrachtwagens;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import be.howest.davyloose.opdrachtwagens.database.Connector;
import be.howest.davyloose.opdrachtwagens.models.Brand;
import be.howest.davyloose.opdrachtwagens.models.Car;


public class MainActivity extends AppCompatActivity {
    Connector databaseConnection;
    private List<Brand> brands;
    private List<Car> cars;
    private  Car selectedCar;
    private Brand selectedBrand;


    private Spinner brandSpinner;
    private ListView listCars;
    private Button buttonOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialiseItems();
        brands = new ArrayList<>();
        GetBrands getBrands = new GetBrands();
        getBrands.execute(this);
    }

    private void initialiseItems(){
        listCars = (ListView) this.findViewById(R.id.listCars);
        brandSpinner = (Spinner) this.findViewById(R.id.spinnerBrands);
        buttonOptions = (Button) this.findViewById(R.id.buttonOptions);
        buttonOptions.setEnabled(false);

        brandSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Brand item = (Brand) brandSpinner.getAdapter().getItem(position);
                selectedBrand = item;
                cars = new ArrayList<>();
                GetCars getCars = new GetCars();
                getCars.execute(item.getID());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        listCars.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedCar = (Car) listCars.getAdapter().getItem(position);
                buttonOptions.setEnabled(true);

            }
        });
    }

    public void goToOptions(View view) {
        Intent intent = new Intent(this, OptionsActivity.class);
        intent.putExtra("selectedCar", selectedCar);
        intent.putExtra("brand",selectedBrand.getName());
        this.startActivity(intent);
    }


    private class GetBrands extends AsyncTask<AppCompatActivity,Integer,String>{

        @Override
        protected String doInBackground(AppCompatActivity... params) {
            databaseConnection = new Connector();
           return databaseConnection.getconnection("allbrands", Collections.<String, String>emptyMap());

        }

        @Override
        protected void onPostExecute(String JSON) {
            try {
                JSONArray jsonObject = new JSONArray(JSON);
                for (int i=0; i<jsonObject.length(); i++) {
                    JSONObject object = jsonObject.getJSONObject(i);
                    Brand  brand = new Brand(Integer.parseInt(object.getString("ID")), object.getString("Brand_Name"));
                    brands.add(brand);
                }
                fillInBrandSpinner();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void fillInBrandSpinner(){
        ArrayAdapter<Brand> spinnerArrayAdapter = new ArrayAdapter<Brand>(this, android.R.layout.simple_spinner_item, brands);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        brandSpinner.setAdapter(spinnerArrayAdapter);
    }

    private class GetCars extends AsyncTask<Integer,Integer,String>{

        @Override
        protected String doInBackground(Integer... brand) {

            databaseConnection = new Connector();
            Map <String, String> map = new HashMap<String, String>();
            map.put("brand",brand[0].toString());
            return databaseConnection.getconnection("brand",map);
        }

        @Override
        protected void onPostExecute(String JSON) {
            try {
                JSONArray jsonObject = new JSONArray(JSON);
                for (int i=0; i<jsonObject.length(); i++) {
                    JSONObject object = jsonObject.getJSONObject(i);
                    Car  car = new Car(Integer.parseInt(object.getString("ID")), Integer.parseInt(object.getString("Brand_ID")), object.getString("Car_Name"),Double.parseDouble(object.getString("Price")));
                    cars.add(car);
                }
                fillInListViewCars();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void fillInListViewCars(){
        ArrayAdapter<Car> listArrayAdapter = new ArrayAdapter<Car>(this, android.R.layout.simple_list_item_1, cars);
        listArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        listCars.setAdapter(listArrayAdapter);
    }
}
