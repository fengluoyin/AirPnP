package airpnp.pennapps.com.airpnp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PaymentActivity extends AppCompatActivity {

    private String ownerAccountId;
    private String userAccountId;
    private String userEmail;
    private String ownerEmail;
    private String firstName;
    private String lastName;
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String hours;

    private JSONObject tempJSONObject;
    private JSONArray tempJSONArray;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        String ownerAccountId=getIntent().getStringExtra("ownerAccountId");
        String userAccountId=getIntent().getStringExtra("userAccountId");
        userEmail=getIntent().getStringExtra("userEmail");
        ownerEmail=getIntent().getStringExtra("ownerEmail");
        hours=String.valueOf(getIntent().getIntExtra("hours", 0));
        textView = (TextView)findViewById(R.id.textView1);
        getName(userEmail);
        //getAddress(ownerEmail);
        //setMessageText();
    }

    public void getName(String userEmail)
    {
        RequestQueue queue = Volley.newRequestQueue(PaymentActivity.this);
        String url = "http://li367-204.members.linode.com/getname?email=" + userEmail;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    tempJSONArray = response.getJSONArray("result");
                    tempJSONObject = tempJSONArray.getJSONObject(0);
                    firstName = tempJSONObject.getString("firstname");
                    lastName = tempJSONObject.getString("lastname");
                    String text = textView.getText().toString();
                    text.replace("#firstName", firstName);
                    text.replace("#lastName", lastName);
                    textView.setText(text);
                } catch (JSONException e) {
                    Toast.makeText(PaymentActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PaymentActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(jsonObjectRequest);

        RequestQueue queue1 = Volley.newRequestQueue(PaymentActivity.this);
        String url1 = "http://li367-204.members.linode.com/getparkingdetails?email=" + ownerEmail;
        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.GET, url1, (String) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    tempJSONArray = response.getJSONArray("data");
                    tempJSONObject = tempJSONArray.getJSONObject(0);
                    street = tempJSONObject.getString("street");
                    city = tempJSONObject.getString("city");
                    state = tempJSONObject.getString("state");
                    zipCode = tempJSONObject.getString("zipcode");
                    Toast.makeText(PaymentActivity.this, street + " " + city + " " + state + " " + zipCode, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    Toast.makeText(PaymentActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PaymentActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        queue1.add(jsonObjectRequest1);


        //textView.setText("Good day to you " + firstName + " " + lastName + "!\\nYou have requested a space at " + street + " " + city + " " + state + " " + zipCode + " for " + hours + " hrs.\\n\\nHere is your total for the day:");
        TextView textView1 = (TextView)findViewById(R.id.money_int);
        TextView textView2 = (TextView)findViewById(R.id.money_decimal);
        textView1.setText("" + getIntent().getIntExtra("cost", 25));
        textView2.setText("00");
    }

    public void getAddress(String ownerEmail)
    {
        RequestQueue queue = Volley.newRequestQueue(PaymentActivity.this);
        String url = "http://li367-204.members.linode.com/getparkingdetails?email=" + ownerEmail;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    tempJSONArray = response.getJSONArray("data");
                    tempJSONObject = tempJSONArray.getJSONObject(0);
                    street = tempJSONObject.getString("street");
                    city = tempJSONObject.getString("city");
                    state = tempJSONObject.getString("state");
                    zipCode = tempJSONObject.getString("zipcode");
                } catch (JSONException e) {
                    Toast.makeText(PaymentActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PaymentActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(jsonObjectRequest);
    }

    public void setMessageText()
    {
        TextView textView = (TextView)findViewById(R.id.textView1);
        textView.setText("Good day to you " + firstName + " " + lastName + "!\\nYou have requested a space at " + street + " " + city + " " + state + " " + zipCode + " for " + hours + " hrs.\\n\\nHere is your total for the day:");
        TextView textView1 = (TextView)findViewById(R.id.money_int);
        TextView textView2 = (TextView)findViewById(R.id.money_decimal);
        textView1.setText(getIntent().getStringExtra("cost"));
        textView2.setText("00");
    }

    public void payParking(View view) {
    }
}
