package com.rumblinghacked.lucas.ilovezappos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.inputmethod.EditorInfo;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    //URL is broken up into three pieces so a search result can be used form the text box
    final String URL_Base = "https://api.zappos.com/Search";
    final String URL_Search_Term = "?term=";
    final String URL_API_Key = "&key=b743e26728e16b81da139182bb2094357c31d331";
    //Where user input will be stored
    String Input_Term = "null";
    String url;

    private ArrayList<Product> productList = new ArrayList<>();

    ProductAdapter Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText enterSearch = (EditText)findViewById(R.id.search);
        enterSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    Input_Term = URL_Search_Term + enterSearch.getText().toString();
                    //final URL that is going to get sent for the request
                    url = URL_Base + Input_Term + URL_API_Key;
                    Log.v("Donkey", url);
                    //clears the Array list
                    if(productList.size()>0){
                        for (int x=productList.size(); x>0;x--){
                            productList.remove(x-1);
                        }
                    }
                    getResults();
                }

                return false;
            }
        });


        Input_Term = URL_Search_Term + Input_Term;
        url = URL_Base + Input_Term + URL_API_Key;
        getResults();

    }

    public void getResults(){

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.result_list);

        Adapter = new ProductAdapter(productList);

        recyclerView.setAdapter(Adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);


        //Uses Volley to make a JSONObject request, with a GET method, no Data besides url is getting passed
        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    //retrieves the JSON Array where the information is stored
                    JSONArray results = response.getJSONArray("results");

                    //extracts the information needed for the product
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject Obj = results.getJSONObject(i);
                        String brandName = Obj.getString("brandName");
                        String thubNailURL = Obj.getString("thumbnailImageUrl");
                        String productID = Obj.getString("productId");
                        String originalPrice = Obj.getString("originalPrice");
                        String styleID = Obj.getString("styleId");
                        String colorID = Obj.getString("colorId");
                        String price = Obj.getString("price");
                        String percentOff = Obj.getString("percentOff");
                        String productURL = Obj.getString("productUrl");
                        String productName = Obj.getString("productName");

                        //stores the information extracted using the class Product.java
                        Product products = new Product(brandName, thubNailURL, productID, originalPrice, styleID, colorID, price, percentOff, productURL, productName);

                        //stores information of each product on an array list
                        productList.add(products);

                        Log.v("Yay", "Print From Class: " + products.getBrandName() + products.getOriginalPrie() + products.getProductName());
                    }

                } catch (JSONException e) {
                    Log.v("Yay", "Err");
                }

                Adapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("Yay", "Err: " + error.getLocalizedMessage());
            }
        });

        Volley.newRequestQueue(this).add(jsonRequest);

    }

    public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder>{
        private ArrayList<Product> resultList;

        public ProductAdapter(ArrayList<Product> resultList) {
            this.resultList = resultList;
        }

        @Override
        public void onBindViewHolder(ProductViewHolder holder, int position) {
            Product products = resultList.get(position);
            holder.updateUI(products);
        }

        @Override
        public int getItemCount() {
            return resultList.size();
        }

        @Override
        public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View card = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_product, parent, false);
            return new ProductViewHolder(card);
        }
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        private ImageView lproductIMG;
        private TextView lbrandName;
        private TextView lproductName;
        private TextView lprice;
        private TextView lpercentOff;

        public ProductViewHolder(View itemView) {
            super(itemView);

            lproductIMG = (ImageView)itemView.findViewById(R.id.productImage);
            lbrandName = (TextView)itemView.findViewById(R.id.brandName);
            lproductName = (TextView)itemView.findViewById(R.id.productName);
            lprice = (TextView)itemView.findViewById(R.id.price);
            lpercentOff = (TextView)itemView.findViewById(R.id.percentOff);
        }

        public void updateUI(Product products) {

            Picasso.with(getApplicationContext()).load(products.getThumbnailURL()).into(lproductIMG);
            lbrandName.setText(products.getBrandName());
            lproductName.setText(products.getProductName());
            lprice.setText(products.getPrice());
            lpercentOff.setText(products.getPercentOff());
        }
    }


}
