package com.example.asheransari.webpageintent;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onClickOpenWebPageButton(View v)
    {
//        Toast.makeText(this, "TODO: Open a web page when this button is clicked", Toast.LENGTH_SHORT).show();
//        Intent i = new Intent(Intent.ACTION_VIEW,)
        String url = "https://www.udacity.com";
        openWebPage(url);
    }

    public void onClickOpenAddressButton(View v)
    {
        String addressString = "1600 Amphitheatre Parkway, CA";
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("geo").path("0.0").query(addressString);
        Uri addressUri = builder.build();

        Intent intent = new Intent(Intent.ACTION_VIEW);

        intent.setData(addressUri);
        if(intent.resolveActivity(getPackageManager())!=null)
        {
            startActivity(intent );
        }
        Toast.makeText(this, "TODO: Open a map when this button is clicked", Toast.LENGTH_SHORT).show();
    }
    public void onClickShareTextButton(View v)
    {
        Toast.makeText(this, "TODO: Share text when this is clicked", Toast.LENGTH_SHORT).show();
    }

    public void createYourOwn(View v)
    {
        Toast.makeText(this, "TODO: Create Your Own Implicit Intent", Toast.LENGTH_SHORT).show();
    }

    public void openWebPage(String url)
    {
        Uri webpage = Uri.parse(url);
        Intent i = new Intent(Intent.ACTION_VIEW,webpage);
        if (i.resolveActivity(getPackageManager())!= null)
        {
            startActivity(i);
        }
    }

    public void showMap(Uri geoLocation)
    {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(geoLocation);
        if (i.resolveActivity(getPackageManager())!=null)
        {
            startActivity(i);
        }
    }
}
