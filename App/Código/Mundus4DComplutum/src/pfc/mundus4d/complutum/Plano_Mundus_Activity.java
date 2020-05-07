package pfc.mundus4d.complutum;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Plano_Mundus_Activity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.plano_mundus_activity);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.plano__mundus_, menu);
		return true;
	}

}
