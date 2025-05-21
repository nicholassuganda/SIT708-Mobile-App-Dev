package com.tutorial.personalizedlearningexperienceapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.PaymentData;
import com.tutorial.personalizedlearningexperienceapp.databinding.ActivityUpgradeBinding;
import com.tutorial.personalizedlearningexperienceapp.viewmodel.CheckoutViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class UpgradeActivity extends AppCompatActivity {

    private static final int LOAD_PAYMENT_DATA_REQUEST_CODE = 991;

    private CheckoutViewModel model;
    private ActivityUpgradeBinding layoutBinding;
    private View googlePayButton;
    private CardView basicPlan, proPlan, premiumPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize ViewModel
        model = new ViewModelProvider(this).get(CheckoutViewModel.class);

        // Initialize UI
        layoutBinding = ActivityUpgradeBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());

        // Initialize plan cards
        basicPlan = findViewById(R.id.basicPlan);
        proPlan = findViewById(R.id.proPlan);
        premiumPlan = findViewById(R.id.premiumPlan);

        // Set click listeners for each plan
        basicPlan.setOnClickListener(v -> requestPayment(499)); // $4.99 in cents
        proPlan.setOnClickListener(v -> requestPayment(999));   // $9.99 in cents
        premiumPlan.setOnClickListener(v -> requestPayment(1499)); // $14.99 in cents

        // Observe Google Pay availability
        model.canUseGooglePay.observe(this, this::setGooglePayAvailable);

        // Initialize Google Pay button (if you have one in your layout)
        // googlePayButton = findViewById(R.id.googlePayButton);
    }

    private void setGooglePayAvailable(boolean available) {
        if (available) {
            // If you have a dedicated Google Pay button, make it visible
            // googlePayButton.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(this, R.string.googlepay_status_unavailable, Toast.LENGTH_LONG).show();
        }
    }

    public void requestPayment(long priceCents) {
        // Disables the button to prevent multiple clicks
        // if (googlePayButton != null) {
        //     googlePayButton.setClickable(false);
        // }

        // The price provided to the API should include taxes and shipping
        // For demo purposes, we'll just use the plan price
        long shippingCostCents = 0; // No shipping cost for digital products
        long totalPriceCents = priceCents + shippingCostCents;

        final Task<PaymentData> task = model.getLoadPaymentDataTask(totalPriceCents);

        // Shows the payment sheet and forwards the result to the onActivityResult method
        AutoResolveHelper.resolveTask(task, this, LOAD_PAYMENT_DATA_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LOAD_PAYMENT_DATA_REQUEST_CODE) {
            switch (resultCode) {
                case RESULT_OK:
                    PaymentData paymentData = PaymentData.getFromIntent(data);
                    handlePaymentSuccess(paymentData);
                    break;

                case RESULT_CANCELED:
                    Toast.makeText(this, "Payment cancelled", Toast.LENGTH_SHORT).show();
                    break;

                case AutoResolveHelper.RESULT_ERROR:
                    Status status = AutoResolveHelper.getStatusFromIntent(data);
                    handleError(status);
                    break;
            }

            // Re-enable the payment buttons
            basicPlan.setClickable(true);
            proPlan.setClickable(true);
            premiumPlan.setClickable(true);
        }
    }

    private void handlePaymentSuccess(@Nullable PaymentData paymentData) {
        if (paymentData == null) {
            Log.e("Payment", "Null payment data");
            return;
        }

        final String paymentInfo = paymentData.toJson();
        try {
            JSONObject paymentMethodData = new JSONObject(paymentInfo).getJSONObject("paymentMethodData");
            final JSONObject tokenizationData = paymentMethodData.getJSONObject("tokenizationData");
            final String token = tokenizationData.getString("token");

            // For demo purposes, just show a success message
            Toast.makeText(this, "Payment successful! Thank you for your subscription.", Toast.LENGTH_LONG).show();

            // Logging token string
            Log.d("Google Pay token: ", token);

        } catch (JSONException e) {
            Log.e("Payment", "Error parsing payment data", e);
            Toast.makeText(this, "Payment successful but couldn't parse details", Toast.LENGTH_LONG).show();
        }
    }

    private void handleError(@Nullable Status status) {
        String errorString = "Unknown error during payment";
        if (status != null) {
            int statusCode = status.getStatusCode();
            errorString = String.format(Locale.getDefault(), "Error code: %d", statusCode);
        }

        Toast.makeText(this, errorString, Toast.LENGTH_LONG).show();
        Log.e("loadPaymentData failed", errorString);
    }
}