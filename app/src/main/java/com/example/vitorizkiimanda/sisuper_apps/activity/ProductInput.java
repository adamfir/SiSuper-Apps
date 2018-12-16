package com.example.vitorizkiimanda.sisuper_apps.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.vitorizkiimanda.sisuper_apps.R;

public class ProductInput extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String unitProduct;

    EditText productNames;
    EditText productPrices;

    Boolean cancel = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_input);

        //spinner
        Spinner spinner = (Spinner) findViewById(R.id.input_product_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.unitProduct, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        //init
        productNames = findViewById(R.id.name_input_product);
        productPrices = findViewById(R.id.price_input_product);

        final Button inputProduct = findViewById(R.id.add_input_product);
        inputProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputProduct();
            }
        });

    }

    public void inputProduct() {
        View focusView = null;
        cancel = false;

        String productName = productNames.getText().toString();
        String productPrice = productPrices.getText().toString();

        if (TextUtils.isEmpty(productName)) {
            productNames.setError(getString(R.string.error_field_required));
            focusView = productNames;
            cancel = true;
        }

        if (TextUtils.isEmpty(productPrice)) {
            productPrices.setError(getString(R.string.error_field_required));
            focusView = productPrices;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            Log.d("nama", productName);
            Log.d("price", productPrice);
            Log.d("unit", unitProduct);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        unitProduct = (String) adapterView.getItemAtPosition(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
