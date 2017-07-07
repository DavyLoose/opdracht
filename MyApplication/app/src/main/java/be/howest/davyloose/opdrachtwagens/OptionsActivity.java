package be.howest.davyloose.opdrachtwagens;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import be.howest.davyloose.opdrachtwagens.database.Connector;
import be.howest.davyloose.opdrachtwagens.models.Car;
import be.howest.davyloose.opdrachtwagens.models.Option;

public class OptionsActivity extends AppCompatActivity {

    private Car selectedCar;
    private List<Option> options;
    private Map<String,Spinner> optionSelectors = new HashMap<>();
    private TextView textViewCarName;
    private TextView textViewtotalPrice;
    private Map<String,Double> prices = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        selectedCar = getIntent().getExtras().getParcelable("selectedCar");
        options = new ArrayList<>();

        GetOptions getOptions = new GetOptions();
        getOptions.execute(selectedCar.getId());
        initialiseComponents();

    }

    private void initialiseComponents(){
        textViewCarName = (TextView) this.findViewById(R.id.textViewCarName);
        textViewCarName.setText("car: "+selectedCar.getName());
        textViewtotalPrice = (TextView) this.findViewById(R.id.textViewTotalPrice);
        setPrice();
    }

    private void setPrice(){

        textViewtotalPrice.setText("Price: "+String.valueOf(totalPrice())+"€");
    }

    private double totalPrice(){
        double price = selectedCar.getPrice();
        for (Map.Entry<?, Double> entry : prices.entrySet())
        {
            price = price + entry.getValue();
        }
        return price;
    }




    private class GetOptions extends AsyncTask<Integer,Integer,String> {

        @Override
        protected String doInBackground(Integer... car) {

           Connector databaseConnection = new Connector();
            Map<String, String> map = new HashMap<String, String>();
            map.put("car",car[0].toString());
            return databaseConnection.getconnection("options",map);
        }

        @Override
        protected void onPostExecute(String JSON) {
            try {
                JSONArray jsonObject = new JSONArray(JSON);
                for (int i=0; i<jsonObject.length(); i++) {
                    JSONObject object = jsonObject.getJSONObject(i);
                    Option  option = new Option(Integer.parseInt(object.getString("ID")), parseCarID(object.getString("Car_ID")), object.getString("Option_Name"),Double.parseDouble(object.getString("Price")), object.getString("Categorie_name"));
                    options.add(option);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            generateOptionSelectors();
        }
    }

    private Integer parseCarID(String carID){
        if(carID.equals( "null")){
            return null ;
        }else {
            return Integer.parseInt(carID);
        }
    }

    private void generateOptionSelectors(){
        LinearLayout layout = (LinearLayout) this.findViewById(R.id.LayoutOptions);

        String currentCat = options.get(0).getCategorie();
        Spinner spinner = new Spinner(this);
        List<Option> optionsCurrentCat = new ArrayList<>();
        for (Option option: options){
            if (currentCat.equals(option.getCategorie())){
                optionsCurrentCat.add(option);
            }else {
                addSpinnerToLayout(currentCat,optionsCurrentCat,spinner,layout);
                optionsCurrentCat = new ArrayList<>();
                spinner = new Spinner(this);
                currentCat = option.getCategorie();
                optionsCurrentCat.add(option);
            }
        }
        addSpinnerToLayout(currentCat,optionsCurrentCat,spinner,layout);
    }

    private void addSpinnerToLayout(final String currentCat, List<Option> optionsCurrentCat , Spinner spinner, LinearLayout layout){
        TextView titleView = new TextView(this);
        titleView.setText(currentCat);
        layout.addView(titleView);

        ArrayAdapter<Option> spinnerArrayAdapter = new ArrayAdapter<Option>(this, android.R.layout.simple_spinner_item, optionsCurrentCat);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Option option =(Option) optionSelectors.get(currentCat).getAdapter().getItem(position);
                prices.put(currentCat,option.getPrice());
                setPrice();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        layout.addView(spinner);
        optionSelectors.put(currentCat,spinner);
    }

    public void returnToMain(View view) {
        this.finish();
    }

    public void buyCar(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("You bought a car!")
                .setMessage(getResources().getString(R.string.bought_car)+ " "+getIntent().getStringExtra("brand")+" "+selectedCar.getName())
                .setCancelable(false)
                .setNegativeButton("Close",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        returnToMain(null);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();


    }




}
