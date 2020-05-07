package pfc.mundus4d.complutum;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.Toast;

public class InfoActivity extends TabActivity {

	int idEdif;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info);
		
		//Se recoge los valores pasados por la Activity anterior.
		Bundle paramExtra = getIntent().getExtras();
		idEdif = paramExtra.getInt("IdEdif");
		

		
		switch (idEdif){
			case 0:
				setTitle("FACHADA_MONUMENTAL");
			break;
			case 1:
				setTitle("CRIPTOPORTICO");
			break;
			case 2:
				setTitle("CURIA");
			break;
			case 3:
				setTitle("DECUMANO III");
			break;
			case 4:
				setTitle("BASILICA");
			break;
			case 5:
				setTitle("PORTICO SUR");
			break;
			case 6:
				setTitle("TERMAS SUR");
			break;
			case 7:
				setTitle("MERCADO");
			break;
			case 8:
				setTitle("NINFEO");
			break;
			case 9:
				setTitle("CRIPTOPORTICO");
			break;
			case 10:
				setTitle("CURIA");				
			break;
			case 11:
				setTitle("BASILICA");				
			break;
			case 12:
				setTitle("TERMAS SUR");
			break;
			case 13:
				setTitle("MERCADO");
			break;
			case 14:
				setTitle("NINFEO");			
			break;
					
		}//End switch
		
		
		CrearTabHost(idEdif);
				
	}//End onCreate
	
	
	private void CrearTabHost(int idLayout){
		
		TabHost tabHost = getTabHost();
		Intent intHistoria = new Intent().setClass(this, Historia_activity.class);
		intHistoria.putExtra("Id", idLayout);
	
		
		Intent intFunciones = new Intent().setClass(this, Funciones_activity.class);
		intFunciones.putExtra("Id", idLayout);

		
		Intent intSabias = new Intent().setClass(this, Sabias.class);
		intSabias.putExtra("Id", idLayout);

		
		switch(idEdif){
		case 0://Fachada Monumental
			
			tabHost.addTab(tabHost.newTabSpec("Tab1").setIndicator("Historia", null).setContent(intHistoria));
			tabHost.addTab(tabHost.newTabSpec("Tab3").setIndicator("Curiosidades", null).setContent(intSabias));
			
		break;
		case 1://Criptoportico
			tabHost.addTab(tabHost.newTabSpec("Tab1").setIndicator("Historia", null).setContent(intHistoria));
			tabHost.addTab(tabHost.newTabSpec("Tab2").setIndicator("Funciones", null).setContent(intFunciones));
			tabHost.addTab(tabHost.newTabSpec("Tab3").setIndicator("Curiosidades", null).setContent(intSabias));		
		break;
		case 2://Curia
			tabHost.addTab(tabHost.newTabSpec("Tab1").setIndicator("Historia", null).setContent(intHistoria));
			tabHost.addTab(tabHost.newTabSpec("Tab2").setIndicator("Funciones", null).setContent(intFunciones));

		break;
		case 3://Decumano III
			tabHost.addTab(tabHost.newTabSpec("Tab1").setIndicator("Historia", null).setContent(intHistoria));
			tabHost.addTab(tabHost.newTabSpec("Tab3").setIndicator("Curiosidades", null).setContent(intSabias));
		break;
		case 4://Basilica
			tabHost.addTab(tabHost.newTabSpec("Tab1").setIndicator("Historia", null).setContent(intHistoria));
			tabHost.addTab(tabHost.newTabSpec("Tab2").setIndicator("Funciones", null).setContent(intFunciones));
			tabHost.addTab(tabHost.newTabSpec("Tab3").setIndicator("Curiosidades", null).setContent(intSabias));
		break;
		case 5:	//Portico Sur
			tabHost.addTab(tabHost.newTabSpec("Tab1").setIndicator("Historia", null).setContent(intHistoria));
			tabHost.addTab(tabHost.newTabSpec("Tab3").setIndicator("Curiosidades", null).setContent(intSabias));
		break;
		case 6:	//Termas Sur		
			tabHost.addTab(tabHost.newTabSpec("Tab1").setIndicator("Historia", null).setContent(intHistoria));
			tabHost.addTab(tabHost.newTabSpec("Tab2").setIndicator("Funciones", null).setContent(intFunciones));
			tabHost.addTab(tabHost.newTabSpec("Tab3").setIndicator("Curiosidades", null).setContent(intSabias));
		break;
		case 7: //Mercado 
			tabHost.addTab(tabHost.newTabSpec("Tab1").setIndicator("Historia", null).setContent(intHistoria));
			tabHost.addTab(tabHost.newTabSpec("Tab2").setIndicator("Funciones", null).setContent(intFunciones));
		break;
		case 8:	//Ninfeo
			tabHost.addTab(tabHost.newTabSpec("Tab1").setIndicator("Historia", null).setContent(intHistoria));
			tabHost.addTab(tabHost.newTabSpec("Tab3").setIndicator("Curiosidades", null).setContent(intSabias));
		break;
		case 9:
			tabHost.addTab(tabHost.newTabSpec("Tab1").setIndicator("Historia", null).setContent(intHistoria));
			tabHost.addTab(tabHost.newTabSpec("Tab2").setIndicator("Funciones", null).setContent(intFunciones));
			tabHost.addTab(tabHost.newTabSpec("Tab3").setIndicator("Curiosidades", null).setContent(intSabias));
		break;
		case 10:
			tabHost.addTab(tabHost.newTabSpec("Tab1").setIndicator("Historia", null).setContent(intHistoria));
			tabHost.addTab(tabHost.newTabSpec("Tab2").setIndicator("Funciones", null).setContent(intFunciones));			
		break;
		case 11:
			tabHost.addTab(tabHost.newTabSpec("Tab1").setIndicator("Historia", null).setContent(intHistoria));
			tabHost.addTab(tabHost.newTabSpec("Tab2").setIndicator("Funciones", null).setContent(intFunciones));
			tabHost.addTab(tabHost.newTabSpec("Tab3").setIndicator("Curiosidades", null).setContent(intSabias));			
		break;
		case 12:
			tabHost.addTab(tabHost.newTabSpec("Tab1").setIndicator("Historia", null).setContent(intHistoria));
			tabHost.addTab(tabHost.newTabSpec("Tab2").setIndicator("Funciones", null).setContent(intFunciones));
			tabHost.addTab(tabHost.newTabSpec("Tab3").setIndicator("Curiosidades", null).setContent(intSabias));			
		break;
		case 13:
			tabHost.addTab(tabHost.newTabSpec("Tab1").setIndicator("Historia", null).setContent(intHistoria));
			tabHost.addTab(tabHost.newTabSpec("Tab2").setIndicator("Funciones", null).setContent(intFunciones));
			
		break;
		case 14:
			tabHost.addTab(tabHost.newTabSpec("Tab1").setIndicator("Historia", null).setContent(intHistoria));
			tabHost.addTab(tabHost.newTabSpec("Tab3").setIndicator("Curiosidades", null).setContent(intSabias));			
		break;
		
		}//End switch			
	}//End CrearTabHost

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.info, menu);
		return true;
	}
	
	/** Método sobre que hacer cuando se selecciona una opción del menú */
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		
		switch(item.getItemId()){
			
		case R.id.salirApp:			
			finish();
		break;
			
		}//End switch
		
		return true;
	}
}
