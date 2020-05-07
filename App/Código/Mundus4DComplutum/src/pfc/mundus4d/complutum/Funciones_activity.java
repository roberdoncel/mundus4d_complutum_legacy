package pfc.mundus4d.complutum;

import android.os.Bundle;
import android.app.Activity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Funciones_activity extends Activity {
	
	private int idEdifInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_funciones);
		
		Bundle idLayout = getIntent().getExtras();
		idEdifInfo = idLayout.getInt("Id");
		CargaLayout(idEdifInfo);
	}
	
	private void CargaLayout(int idEdif){
		
		TextView infoFunciones = (TextView)findViewById(R.id.Funciones);
		
		switch(idEdif){
		case 0:
			infoFunciones.setText("No hay información");					
		break;
		case 1:			
			infoFunciones.setText(Html.fromHtml(getString(R.string.criptoportico_f)));	
		break;
		case 2:

			infoFunciones.setText(Html.fromHtml(getString(R.string.curia_f)));	
		break;
		case 3:
			infoFunciones.setText("No hay información");			
		break;
		case 4:			
			infoFunciones.setText(Html.fromHtml(getString(R.string.basilica_f)));	
		break;
		case 5:
			infoFunciones.setText("No hay información");			
		break;
		case 6:			
			infoFunciones.setText(Html.fromHtml(getString(R.string.termas_sur_f)));	
		break;
		case 7:			
			infoFunciones.setText(Html.fromHtml(getString(R.string.mercado_f)));	
		break;
		case 8:
			infoFunciones.setText("No hay información");			
		break;
		case 9:
			infoFunciones.setText(Html.fromHtml(getString(R.string.criptoportico_f)));				
		break;
		case 10:
			infoFunciones.setText(Html.fromHtml(getString(R.string.curia_f)));	
		break;
		case 11:
			infoFunciones.setText(Html.fromHtml(getString(R.string.basilica_f)));	
		break;
		case 12:
			infoFunciones.setText(Html.fromHtml(getString(R.string.termas_sur_f)));	
		break;
		case 13:
			infoFunciones.setText(Html.fromHtml(getString(R.string.mercado_f)));	
		break;
		case 14:
			infoFunciones.setText("No hay información");
		break;
		
		}//End switch		
		
	}//End CargaLayout
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.funciones_activity, menu);
		return true;
	}
	
	/** Método sobre que hacer cuando se selecciona una opción del menú */
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		
		switch(item.getItemId()){
		case R.id.salir_funciones:
				finish();
			break;
			
		}//End switch
		
		return true;
	}


}
