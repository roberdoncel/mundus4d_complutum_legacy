package pfc.mundus4d.complutum;

import java.util.Vector;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MenuPrincipal extends Activity {
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_principal);
		
		final TextView viewEmp = (TextView)findViewById(R.id.Empezar);
		viewEmp.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent Tracking = new Intent(getApplicationContext(), RealAumentada.class);
				startActivity(Tracking);
				
			}
		});
		
		final TextView viewComp = (TextView)findViewById(R.id.Complutum);
		viewComp.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				loadContenidoScrollView(1);
				
			}
		});
		final TextView viewMundus4D = (TextView)findViewById(R.id.Mundus4D);
		viewMundus4D.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				loadContenidoScrollView(2);
				
			}
		});
		final TextView viewInst = (TextView)findViewById(R.id.Instrucciones);
		viewInst.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				loadContenidoScrollView(3);
				
			}
		});
		final TextView viewSalir = (TextView)findViewById(R.id.Salir);
		viewSalir.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FinalizarAplicacion(v);
				
			}
		});
		
		
	}//End onCreate
	
	
	
	/* Método diseñado para que hacer cuando se pulsa el botón empezar */
	public void EmpezarTracking (View view){
		Intent Tracking = new Intent(getApplicationContext(), RealAumentada.class);
		startActivity(Tracking);
	}
	
	public void FinalizarAplicacion(View view){
		synchronized(this){	
		Intent finalizar = new Intent(getApplicationContext(), Launcher.class);
		finalizar.putExtra("Terminar", 1);
		android.os.Process.killProcess(android.os.Process.myPid());
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_principal, menu);
		return true;
	}
	
	/* Método para mostrar contenido dentro de un scrollView */
	public void loadContenidoScrollView(int id){
		
		ScrollView sv = (ScrollView)findViewById(R.id.scrollView1);
		LinearLayout LlSv = (LinearLayout)findViewById(R.id.ScrollLinearLayout);
		ImageView M4DView =(ImageView)findViewById(R.id.imageView1);
		if (M4DView != null){
			M4DView.setVisibility(View.GONE);
		}
	
		
		switch(id){
			case 1:
				LlSv.removeAllViews();
				TextView comp1 = new TextView(this);
				comp1.setText(Html.fromHtml(getString(R.string.complutum_1)));
				TextView comp2 = new TextView(this);
				comp2.setText(Html.fromHtml(getString(R.string.complutum_2)));
				TextView comp_u1 = new TextView(this);
				comp_u1.setText(Html.fromHtml(getString(R.string.complutum_urba1)));
				TextView comp_u2 = new TextView(this);
				comp_u2.setText(Html.fromHtml(getString(R.string.complutum_urba2)));
				TextView comp_u3 = new TextView(this);
				comp_u3.setText(Html.fromHtml(getString(R.string.complutum_urba3)));	
				
				LlSv.addView(comp1);
				LlSv.addView(comp2);
				LlSv.addView(comp_u1);
				LlSv.addView(comp_u2);
				LlSv.addView(comp_u3);		
				
			break;
			case 2:
				LlSv.removeAllViews();
				TextView mundus4d = new TextView(this);
				mundus4d.setText(Html.fromHtml(getString(R.string.Mundus4D)));
				mundus4d.setMovementMethod(LinkMovementMethod.getInstance());
				LlSv.addView(mundus4d);
				
			break;
			case 3:
				LlSv.removeAllViews();
				TextView instrucciones1 = new TextView(this);
				instrucciones1.setText(Html.fromHtml(getString(R.string.Instrucciones1)));
				TextView instrucciones2 = new TextView(this);
				instrucciones2.setText(Html.fromHtml(getString(R.string.Instrucciones2)));
				ImageButton icoPlano = new ImageButton(this);
				icoPlano.setImageResource(R.drawable.plano_mundus);
			    icoPlano.setOnClickListener(new View.OnClickListener() {
			        @Override
			        public void onClick(View v) {
			    		Intent planoMundus = new Intent(getApplicationContext(), Plano_Mundus_Activity.class);
			    		startActivity(planoMundus);		            
			        }
			    });
				TextView instrucciones3 = new TextView(this);
				instrucciones3.setText(Html.fromHtml(getString(R.string.Instrucciones3)));				
				LlSv.addView(instrucciones1);
				LlSv.addView(instrucciones2);
				LlSv.addView(icoPlano);
				LlSv.addView(instrucciones3);
				
				
			break;
		}//End switch
		
	}
	
	
}
