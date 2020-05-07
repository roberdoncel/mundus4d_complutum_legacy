package pfc.mundus4d.complutum;

import android.os.Bundle;
import android.app.Activity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Sabias extends Activity {
	
	private int idEdifInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_sabias);
		
		Bundle idLayout = getIntent().getExtras();
		idEdifInfo = idLayout.getInt("Id");
		CargaLayout(idEdifInfo);
		
	}
	
private void CargaLayout(int idEdif){
	
		TextView infoCuriosidades = (TextView)findViewById(R.id.Curiosidades);
		
		switch(idEdif){
		case 0:			
			infoCuriosidades.setText(Html.fromHtml(getString(R.string.fachada_monumental_s)));	
		break;
		case 1:			
			infoCuriosidades.setText(Html.fromHtml(getString(R.string.criptoportico_s)));	
		break;
		case 2:
			infoCuriosidades.setText("No hay información");			
		break;
		case 3:
			infoCuriosidades.setText(Html.fromHtml(getString(R.string.decumanoiii_s)));	
		break;
		case 4:			
			infoCuriosidades.setText(Html.fromHtml(getString(R.string.basilica_s)));	
		break;
		case 5:			
			infoCuriosidades.setText(Html.fromHtml(getString(R.string.portico_sur_s)));	
		break;
		case 6:			
			infoCuriosidades.setText(Html.fromHtml(getString(R.string.termas_sur_s)));	
		break;
		case 7:
			infoCuriosidades.setText("No hay información");
		break;
		case 8:			
			infoCuriosidades.setText(Html.fromHtml(getString(R.string.ninfeo_s)));	
		break;
		case 9:
			infoCuriosidades.setText(Html.fromHtml(getString(R.string.criptoportico_s)));
		break;
		case 10:
			infoCuriosidades.setText("No hay información");
		break;
		case 11:
			infoCuriosidades.setText(Html.fromHtml(getString(R.string.basilica_s)));
		break;
		case 12:
			infoCuriosidades.setText(Html.fromHtml(getString(R.string.termas_sur_s)));				
		break;
		case 13:
			infoCuriosidades.setText("No hay información");
		break;
		case 14:
			infoCuriosidades.setText(Html.fromHtml(getString(R.string.ninfeo_s)));	
		break;
		
		}//End switch		
		
	}//End CargaLayout

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sabias, menu);
		return true;
	}

	/** Método sobre que hacer cuando se selecciona una opción del menú */
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		
		switch(item.getItemId()){
		case R.id.salir_sabias:
				finish();
			break;
			
		}//End switch
		
		return true;
	}

}
