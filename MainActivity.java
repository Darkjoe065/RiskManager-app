
package com.example.riskmanager;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText balanceInput, stopLossInput;
    Spinner assetSpinner;
    TextView resultView;
    double pipValue = 0.0001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        balanceInput = findViewById(R.id.balanceInput);
        stopLossInput = findViewById(R.id.stopLossInput);
        assetSpinner = findViewById(R.id.assetSpinner);
        resultView = findViewById(R.id.resultView);
        Button calculateBtn = findViewById(R.id.calculateBtn);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.asset_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assetSpinner.setAdapter(adapter);

        assetSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String asset = parent.getItemAtPosition(position).toString();
                pipValue = asset.equals("XAU/USD") ? 0.10 : 0.0001;
            }
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        calculateBtn.setOnClickListener(v -> calculate());
    }

    private void calculate() {
        try {
            double balance = Double.parseDouble(balanceInput.getText().toString());
            double stopLossPips = Double.parseDouble(stopLossInput.getText().toString());

            double riskAmount = balance * 0.01;  // 1% risk
            double lotSize = riskAmount / (stopLossPips * pipValue);

            resultView.setText(String.format("Lot Size: %.2f", lotSize));
        } catch (Exception e) {
            resultView.setText("Please enter valid numbers.");
        }
    }
}
