package com.mlst.eircomkg;

import android.app.Activity;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class KeygenActivity extends Activity implements OnClickListener {
    
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);

	Button generateButton = (Button) findViewById(R.id.generateButton);
	generateButton.setOnClickListener(this);
	
	TextView wepLabel = (TextView) findViewById(R.id.wepLabel);
	wepLabel.setVisibility(TextView.INVISIBLE);
	
	final TextView key = (TextView) findViewById(R.id.key);
	
	Button copyButton = (Button) findViewById(R.id.copyButton);
	copyButton.setOnClickListener(new OnClickListener(){
	    public void onClick(View v) {
		ClipboardManager clipboard = 
		      (ClipboardManager) getSystemService(CLIPBOARD_SERVICE); 

		 clipboard.setText(key.getText().toString());
		 Toast.makeText(KeygenActivity.this, "Copied", 1000).show();
	    }
	});
	
	
    }

    public void onClick(View arg0) {
	EditText ssid = (EditText) findViewById(R.id.ssid);
	if (ssid.getText() != null) {
	    String ssidText = ssid.getText().toString().trim();
	    // validate if this is a number
	    if (isNumeric(ssidText)) {
		performCalculation(ssidText);
	    } else {
		Toast.makeText(this, "Invalid SSID", 2000).show();
	    }
	}
    }

    private boolean isNumeric(String ssid) {
	try {
	    Integer.parseInt(ssid);
	} catch (Exception e) {
	    return false;
	}

	return true;
    }

    private void performCalculation(String ssid) {
	TextView key = (TextView) findViewById(R.id.key);

	String wepKey;
	try {
	    wepKey = KeyGenerator.generateWEPKey(ssid);
	    if (wepKey != null) {
		TextView wepLabel = (TextView) findViewById(R.id.wepLabel);
		wepLabel.setVisibility(TextView.VISIBLE);
		key.setText(""+wepKey);
	    }
	} catch (Exception e) {
	    Toast.makeText(this, "Unknown error: "+e.getMessage(), 2000);
	}
    }

}