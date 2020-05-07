package pfc.mundus4d.complutum;

import android.os.Bundle;
import android.app.Activity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Historia_activity extends Activity {

	private int idEdifInfo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_historia);
		
		Bundle idLayout = getIntent().getExtras();
		idEdifInfo = idLayout.getInt("Id");
		CargaLayout(idEdifInfo);
				
	}//End onCreate
	
	
	private void CargaLayout(int idEdif){
		
		LinearLayout LlInfoHistoria = (LinearLayout)findViewById(R.id.LlInfoHistoria);
		TextView infoHistoria = (TextView)findViewById(R.id.Historia);
		
		switch(idEdif){
		case 0:
			infoHistoria.setText(Html.fromHtml(getString(R.string.fachada_monumental_h)));				
		break;
		case 1:
				
			infoHistoria.setText(Html.fromHtml(getString(R.string.criptoportico_h)));		
		break;
		case 2:
			infoHistoria.setText(Html.fromHtml(getString(R.string.curia_h)));
		break;
		case 3:
			infoHistoria.setText(Html.fromHtml(getString(R.string.decumanoiii_h)));
		break;
		case 4:
			infoHistoria.setText(Html.fromHtml(getString(R.string.basilica_h1)));
		break;
		case 5:	
			infoHistoria.setText(Html.fromHtml(getString(R.string.portico_sur_h)));
		break;
		case 6:			
			infoHistoria.setText(Html.fromHtml(getString(R.string.termas_sur_h)));
		break;
		case 7:
			infoHistoria.setText(Html.fromHtml(getString(R.string.mercado_h)));
		break;
		case 8:	
			infoHistoria.setText(Html.fromHtml(getString(R.string.ninfeo_h)));
		break;
		case 9:
			infoHistoria.setText(Html.fromHtml(getString(R.string.criptoportico_h)));				
		break;
		case 10:
			infoHistoria.setText(Html.fromHtml(getString(R.string.curia_h)));			
		break;
		case 11:
			infoHistoria.setText(Html.fromHtml(getString(R.string.basilica_h1)));			
		break;
		case 12:
			infoHistoria.setText(Html.fromHtml(getString(R.string.termas_sur_h)));
		break;
		case 13:
			infoHistoria.setText(Html.fromHtml(getString(R.string.mercado_h)));
		break;
		case 14:
			infoHistoria.setText(Html.fromHtml(getString(R.string.ninfeo_h)));
		break;
		
		}//End switch		
	}//End CargaLayout

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.historia_activity, menu);
		return true;
	}
	
	/** Método sobre que hacer cuando se selecciona una opción del menú */
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		
		switch(item.getItemId()){
		case R.id.salir_historia:
				finish();
			break;
			
		}//End switch
		
		return true;
	}


}
