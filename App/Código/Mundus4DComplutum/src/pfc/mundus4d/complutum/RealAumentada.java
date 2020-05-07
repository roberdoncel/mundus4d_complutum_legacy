package pfc.mundus4d.complutum;


import java.io.File;

import com.metaio.sdk.ARViewActivity;
import com.metaio.sdk.GestureHandlerAndroid;
import com.metaio.sdk.MetaioDebug;
import com.metaio.sdk.jni.ESCREEN_ROTATION;
import com.metaio.sdk.jni.ETRACKING_STATE;
import com.metaio.sdk.jni.IGeometry;
import com.metaio.sdk.jni.IMetaioSDKCallback;
import com.metaio.sdk.jni.MetaioSDK;
import com.metaio.sdk.jni.Rotation;
import com.metaio.sdk.jni.TrackingValues;
import com.metaio.sdk.jni.TrackingValuesVector;
import com.metaio.sdk.jni.Vector3d;
import com.metaio.tools.io.AssetsManager;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.os.storage.OnObbStateChangeListener;
import android.os.storage.StorageManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

public class RealAumentada extends ARViewActivity implements SensorEventListener {
	
	private SensorManager AdminSensores;
	private Sensor Acelerometro;
	private Sensor SensorCampoMagnetico;

	double Azimut;
	double AzFiltrado = 0;
	private double AzDouble = 0;
	private double AzDoubleProm = 0;
	private double AzGrad = 0; //Azimut expresado en grados.
	private double AzOk = 0;
	double Inclinacion;
	double Roll;
	private int iBufAz = 0; //Utilizado para cargar el buffer del azimut y hacer el promedio.
	private int iBufIncl = 0;
	private double InclDoubleProm;
	private double InclGrad = 0;
	private double InclDouble = 0;
	private double InclOk = 0;
	
	
	//Le decimos que nos introduzca los valores en una matriz:
	private float [] ValAcelerometro = new float[3];
	private float [] ValMagnetico = new float[3];
	public float [] MatrizRotacion = new float [16]; //Esta es la utilizada por OpenGL pero transponiéndola. También se suele usar una de 9.
	public float [] MatrizOrientacion = new float [3];
	private double [] BufferAz = new double [10];
	private double [] BufferIncl = new double [10];
	private float v_campomagnetico = 0;
		
	
	
	private boolean SensoresOn; //Variable para saber si los sensores están activos.
	
	IGeometry Modelo3D;
	private String dirModelo3D;
	private String Modelo3DActual;
	private String MapTrackingRaiz;
	private String MapTrackingActual; //Mapa de seguimiento en uso.
	public String MapTrackingSelected;
	private String Modelo3DSelected;
	private boolean firstLoad;
	int nIntentos = 0;
	int indexMap = 0;
	int MapsProbados = 0;
	private String TipoFichero = ".zip";
	public boolean Status = false; //Interruptor cuando se ha seleccionado el siguiente Map
	int CambioMap = 0; 
	boolean MapaCambiado = false;
	
	//Últimos valores válidos que han funcionado en el tracking
	public String Map_ok;
	public float Es_ok;
	public float Tx_ok;
	public float Ty_ok;
	public float Tz_ok;
	public float Rx_ok;
	public float Ry_ok;
	public float Rz_ok;
	public int indice_ok;
	public float Xmap_ok = 0; 
	public float Ymap_ok = 0;
	public String Map_act = "prueba_portico_sur3_cal";
	public float Es_act = 0;
	public float Tx_act = 0;
	public float Ty_act = 0;
	public float Tz_act = 0;
	public float Rx_act = 0;
	public float Ry_act = 0;
	public float Rz_act = 0;
	public int indice_act = 0;
	public float Xmap_act = 0; 
	public float Ymap_act = 0;

	
	
	public int EdifActivo;
	private boolean sdkPreparado; // Marcador usado para que el programa no intente cargar el mapa tracking
						  // antes de que se haya iniciado el SDK.
	
	
	private AdminMetaioSDK mAdminMetaioSDK;
	private GestureHandlerAndroid AdmGestos;
	public Recorrido_Tracking Tracking;
	
	public static final int FACHADA_MONUMENTAL = 0;
	public static final int CRIPTOPORTICO = 1;
	public static final int CURIA = 2;
	public static final int DECUMANOIII = 3;
	public static final int BASILICA = 4;
	public static final int PORTICO_SUR = 5;
	public static final int TERMAS_SUR = 6;
	public static final int MERCADO_SI = 7;
	public static final int NINFEO = 8;
	public static final int CRIPTOPORTICO_INT = 9;
	public static final int CURIA_INT = 10;
	public static final int BASILICA_INT = 11;
	public static final int TERMAS_SUR_INT = 12;
	public static final int MERCADO_SIII = 13;
	public static final int NINFEO_INT = 14;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	//	metaioSDK.setScreenRotation(ESCREEN_ROTATION.ESCREEN_ROTATION_0);
		sdkPreparado = false;
		firstLoad = true;
		EdifActivo = 0;
		MapTrackingRaiz="termas_sur";
		MapTrackingActual = "";
		MapTrackingSelected = "Tracking_basilica_habitacion4";
		Modelo3DActual = "termas_sur_interior.zip";
		Modelo3DSelected ="termas_sur_interior.zip";
		/*  Método para mantener la pantalla activa en todo momento.
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); */
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		
		Tracking = new Recorrido_Tracking();		
		mAdminMetaioSDK = new AdminMetaioSDK(); //Objeto que nos ayudará a manipular los resultados entregados por el tracking.


		
		AdminSensores = (SensorManager)getSystemService(SENSOR_SERVICE);
		Acelerometro = AdminSensores.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		SensorCampoMagnetico = AdminSensores.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		
		//Hacemos que empiece a registrar lecturas a un intervalo que nos permita una cómoda ejecución:
		AdminSensores.registerListener(this, Acelerometro, SensorManager.SENSOR_DELAY_UI);
		AdminSensores.registerListener(this, SensorCampoMagnetico, SensorManager.SENSOR_DELAY_UI);
		SensoresOn = true;
		
		
		
	}//End onCreate
	


	
	/** Este método onTouch no está basado en los métodos nativos de android, sino en los de Metaio  */
	@Override
	public boolean onTouch(View v, MotionEvent event){
		
		super.onTouch(v, event);
		openOptionsMenu();//Abre el menú predefinido

		
		
	/**	Toast.makeText(getApplicationContext(), Tracking.Map + " Ind= " + String.valueOf(Tracking.indice)+ " Es= " +String.valueOf(Tracking.Es)
				+ " Tx= " +String.valueOf(Tracking.Tx)+ " Ty= " + String.valueOf(Tracking.Ty) + " Tz= " + String.valueOf(Tracking.Tz)
				+ " Rx= " + String.valueOf(Tracking.Rx) + " Ry= " + String.valueOf(Tracking.Ry)+ " Rz= " + String.valueOf(Tracking.Rz)  ,Toast.LENGTH_SHORT).show(); 
		
	*/	
		runOnUiThread(new Runnable()
		{
		   @Override
		   public void run() {
		//	   Toast.makeText(getApplicationContext(), MapTrackingSelected, Toast.LENGTH_SHORT).show();
		   }
		}); 
		
		return true;
	}//End onTouch
	

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.real_aumentada, menu);
		return true;
	}
	
	/** Método sobre que hacer cuando se selecciona una opción del menú */
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		
		switch(item.getItemId()){
			
		case R.id.sel_edif:
				SeleccionEdificio();
				
		break;
		case R.id.info:			
			Intent InfoMenu = new Intent(getApplicationContext(), InfoActivity.class);
			InfoMenu.putExtra("IdEdif", EdifActivo);
			startActivity(InfoMenu);
		break;
		case R.id.Salir_realAumentada:
				Reset();
		break;
			
		}//End switch
		
		return true;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}//End onAccuracyChanged

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
			synchronized(this){
			
				switch (event.sensor.getType()){
					case Sensor.TYPE_ACCELEROMETER:
				
						//copia los valores a una matriz indicando origen, pto de origen, destino, posicion en destino, longitud de matriz
						System.arraycopy(event.values, 0, ValAcelerometro, 0, event.values.length);
					break;
					case Sensor.TYPE_MAGNETIC_FIELD:
				
						System.arraycopy(event.values, 0, ValMagnetico, 0, event.values.length);
					break;
				}
			//Los argumentos pasados para crear la matriz de rotación son (Matriz de Rotación destino, Matriz de inclinación dest., Matriz de Acelerometros, Matriz de Valores Magneticos)
			//La función getRotationMatrix nos devolverá un valor de True si se ha podido resolver o False si ha fallado el proceso.
			//Por tanto creamos una variable booleana para comprobar dicho hecho.
			boolean Resuelto = SensorManager.getRotationMatrix(MatrizRotacion, null, ValAcelerometro, ValMagnetico ); 
			
			if (Resuelto){
					SensorManager.getOrientation(MatrizRotacion, MatrizOrientacion);
			//Generamos la matriz de Orientación dando la Matriz de Rotación origen y Matriz Orientación destino
			}
			v_campomagnetico = (float)Math.sqrt(ValMagnetico[0]*ValMagnetico[0] + ValMagnetico[1]*ValMagnetico[1]+ValMagnetico[2]*ValMagnetico[2]);
			if(v_campomagnetico >= 80){
				TextView Az = (TextView) findViewById(R.id.azimut);
				Az.setTextColor(Color.parseColor("#ff0000"));
				Az.setText("El campo magnético está siendo alterado por algún elemento. "
						+ "Aleje el dispositivo de cualquier objeto que pueda alterarlo como "
						+ "por ejemplo, el imán de la funda.");				
			}
			
			//Los siguientes valores son válidos para el móvil en posición paisaje:			
			
			Azimut = CalcAz(iBufAz, MatrizOrientacion[0]); //Rot. alrededor de Z
			Inclinacion = CalcIncl(iBufIncl, MatrizOrientacion[2]); //Rot. alrededor de Y
			AzFiltrado = Filtro_Az(Azimut); //Azimut que puede ser 27,77,127,177,227,277,327,377
			
			
			Roll = Math.toDegrees(MatrizOrientacion[1]); //Rot. alrededor de X
			if (SensoresOn == true){
					if(v_campomagnetico <= 80){
						TextView Az = (TextView) findViewById(R.id.azimut);
						Az.setTextColor(Color.parseColor("#ffffff"));
						Az.setText("Azimut= " + String.format("%.2f", Azimut) + "g"); //El azimut Ok !!
						
					}					
					TextView Incl = (TextView) findViewById(R.id.inclinacion);
					Incl.setTextColor(Color.parseColor("#ffffff"));
					Incl.setText("Inclinación= " + String.format("%.2f", Inclinacion) + "g");
			}

	
		} //End synchronized	
	}//End onSensorChanged
	
	/** Método para calcular el azimut a partir del promedio de varios valores */
	public double CalcAz(int i, float ValAzSensor){ 
		AzGrad = (double)ValAzSensor * 200 / Math.PI;
		
		if(AzGrad >= -100 && AzGrad <= 0){ //&& Si el primer parametro es falso no sigue valorando el segundo.
			AzOk = 100 + AzGrad;			
		}
		if (AzGrad > 0 && AzGrad <= 100){
			AzOk = 100 + AzGrad;
		}
		if (AzGrad > 100 && AzGrad < 200){
			AzOk = AzGrad - 100 + 200;
		}
		if (AzGrad >= -200 && AzGrad < -100){
			AzOk = (-200 - AzGrad) * (-1) + 300; 
		}
		
		
		
		if (i < BufferAz.length - 1){  //Vamos rellenando las posiciones de la matriz.
			BufferAz[i] = AzOk;
			i++; //MUY IMPORTANTE usar i++ y NO i=i++!! 
			iBufAz = i;	
		}
		if(i == BufferAz.length - 1){ //Cuando se ha llenado la última celda se hace el promedio y se vuelve a poner el contador a cero.
			
			BufferAz[BufferAz.length - 1] = AzOk; //Se pone - 1 porque la matriz tiene 10 celdas pero la primera posición se considera 0 y no posición 1.
			for (int b = 0; b< BufferAz.length; b++){ //si no daría error de posición situada fuera de matriz.
			AzDouble = AzDouble + (BufferAz[b]);
			}
			AzDoubleProm = AzDouble / (double)BufferAz.length;	
			
			AzDouble = 0;
			iBufAz = 0;
		} //End if
			
		return AzDoubleProm;
	} //End CalcAz
	
	/** Método para el cálculo de la inclinación */
	public double CalcIncl(int i, float ValInclSensor){
		InclGrad = (double)ValInclSensor * 200 / Math.PI;
				
		if (InclGrad >= -200 && InclGrad <= -100){
			InclOk = InclGrad + 200;
		}
		if (InclGrad >= -100 && InclGrad <= 0){
			InclOk = InclGrad + 200;
		}
		if (InclGrad > 0){
			InclOk = 202;
		}
		
		if (i < BufferIncl.length - 1){
			BufferIncl[i] = InclOk;
			i++;
			iBufIncl = i;
		}
		if (i == BufferIncl.length - 1){
			BufferIncl[BufferIncl.length - 1] = InclOk;
			for (int b = 0; b< BufferIncl.length; b++){
				InclDouble = InclDouble + (BufferIncl[b]);
			}
			InclDoubleProm = InclDouble /(double)BufferIncl.length;
			InclDouble = 0;
			iBufIncl = 0;
		}//End if
		
				
		return InclDoubleProm;	
	}//End CalcInc

	/* Método para cargar el layout deseado*/
	@Override
	protected int getGUILayout() {
		
		// TODO Auto-generated method stub
		return R.layout.realidadaumentada;
		
	}


	//Método válido unicamente cuando se inicia la actividad.
	@Override
	protected void loadContents() {
		// TODO Auto-generated method stub
		
		try{
			
				String dirTrackingMap = AssetsManager.getAssetPath("prueba_portico_sur3_cal.zip");
				boolean result = metaioSDK.setTrackingConfiguration(dirTrackingMap);
				
				dirModelo3D = AssetsManager.getAssetPath(Modelo3DActual);
				Modelo3D = metaioSDK.createGeometry(dirModelo3D);
				Modelo3D.setTranslation(new Vector3d(0f, 0f, 0f));
				Modelo3D.setScale(new Vector3d(165f, 165f, 165f));
				Modelo3D.setRotation(new Rotation(new Vector3d(1.5707963f, -0.00523598f, 0.05061454f)));//Introducir en radianes, 90º, 0º, -59º
				firstLoad = false;
				MetaioDebug.log("Cargado tracking_galletas_chocos");
				
			
			if (Modelo3D != null){
			
			}else{
				MetaioDebug.log("Error al cargar el modelo 3D");
			}
				
		}catch(Exception e){
			e.printStackTrace();
			
		}//End try
		
	}//End loadContents

	@Override
	protected void onGeometryTouched(IGeometry geometry) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	private void CargaContenido(){
		
		if (mSurfaceView != null){	
		mSurfaceView.queueEvent(new Runnable()
		{

			@Override
			public void run() 
			{
				
				if (Modelo3DActual != Modelo3DSelected){					
					dirModelo3D = AssetsManager.getAssetPath(Modelo3DSelected);
					if(Modelo3D != null) metaioSDK.unloadGeometry(Modelo3D);
					Modelo3D = metaioSDK.createGeometry(Environment.getExternalStorageDirectory() + "/Mundus4DComplutum/assets/"+ Modelo3DSelected);
					Modelo3DActual = Modelo3DSelected;
					if(Modelo3D != null){
						Modelo3D.setTranslation(new Vector3d(Tx_act, Ty_act, Tz_act));
						Modelo3D.setScale(new Vector3d(Es_act, Es_act, Es_act));
						Modelo3D.setRotation(new Rotation(new Vector3d(Rx_act, Ry_act, Rz_act)));
					}					
					runOnUiThread(new Runnable()
					{
					   @Override
					   public void run() {
						 if (Modelo3D != null)Toast.makeText(getApplicationContext(), "Se ha cargado el fichero" , Toast.LENGTH_SHORT).show();
						 else Toast.makeText(getApplicationContext(), "No se ha encontrado el fichero" , Toast.LENGTH_SHORT).show();
					   }
					});
				} //end if
				
			}
				
		});
		}
		
	}//End CargarContenido
	
	public void CargaTrackingMap(){
		if(mSurfaceView != null){
			  mSurfaceView.queueEvent(new Runnable()
				{

					@Override
					public void run() 
					{
						if (MapTrackingActual != MapTrackingSelected){		
							
							MapTrackingActual = MapTrackingSelected;

							final String dirTrackingMap = Environment.getExternalStorageDirectory() + "/Mundus4DComplutum/assets/"+ MapTrackingSelected + TipoFichero;  //AssetsManager.getAssetPath(MapTrackingSelected + TipoFichero);
							boolean result = metaioSDK.setTrackingConfiguration(dirTrackingMap);
							if(Modelo3D != null){
								Modelo3D.setTranslation(new Vector3d(Tx_act,Ty_act, Tz_act));
								Modelo3D.setScale(new Vector3d(Es_act, Es_act, Es_act));
								Modelo3D.setRotation(new Rotation(new Vector3d(Rx_act, Ry_act, Rz_act)));
							}
							MapaCambiado = true;
							Status = false;
						} //end if
					}									
				});
		}
	}
	

	@Override
	protected IMetaioSDKCallback getMetaioSDKCallbackHandler() 
	{
		//TODO
		return mAdminMetaioSDK;
	}
	
	

	
	final class AdminMetaioSDK extends IMetaioSDKCallback{
		
	//INCLUIR AQUÍ LO DE LA TOMA DE IMÁGENES, ETC...
		
		@Override
		public void onSDKReady() 
		{
			MetaioDebug.log("The SDK is ready");
			sdkPreparado = true;
			
		}
		
		
		
		/** En este método se añaden todas las funciones relacionadas con el tracking, si ha sido positivo,
		 * 	si ha fallado, sacar información de los valores del tracking, etc... En este caso es usado para
		 * 	saber si el tracking ha fallado y de esa forma pasar a cambiar el 3dmap. */
		@Override
		public void onTrackingEvent(TrackingValuesVector trackingValues){
			//trackingValues.get(0). 0 Hace referencia a la primera posición de la matriz. Es decir en este caso
			//sería el momento en el que el tracking da un resultado positivo en caso de que su valor no sea nulo. 
			MetaioDebug.log("The tracking time is:" + trackingValues.get(0).getTimeElapsed());
			
				
			 super.onTrackingEvent(trackingValues); //No sé muy bien el por qué de esta linea pero...

		//	if(MapTrackingSelected != "Tracking_basilica_habitacion4"){
				
			/** Cada fichero de tracking contenido en el .zip da un objeto TrackingValues. Por tanto, si
			 * existen estos ficheros trackingValues.isEmpty() siempre será falso. Con el getState vemos
			 * si se han encontrado coincidencias. Hay que cambiar el nIntentos como mucho = trackingValues.size()  */ 
	/**	for(int i = 0; i < trackingValues.size();i++){
				 final TrackingValues tv = trackingValues.get(i); //Es necesario que sea del tipo final.
				 final long nFileTracking = trackingValues.size();
				 
				 if(trackingValues.isEmpty() || tv.getState() != ETRACKING_STATE.ETS_TRACKING || tv.getQuality() < 0.6){
					 
					 runOnUiThread(new Runnable()
						{
						   @Override
						   public void run() { //Es necesario incluir el toast aquí porque onTrackingEvent corre bajo OpenGL.					   		
							   	
							   //Número de intentos sin encontrar coincidencias
							   nIntentos++;
							 				
							  if(nIntentos == nFileTracking && mSurfaceView != null){
								  
								  MapsProbados++;
								  if(MapsProbados == 2){
									  if(Tracking.sentido == true){
										  Tracking.sentido = false;
										  Tracking.indice = indice_ok;
										  MapsProbados = 0;
									  }else{
										  Tracking.sentido = true;
										  Tracking.indice = indice_ok;
										  MapsProbados = 0;
									  }
								  }//end MapsProbados
								  switch(EdifActivo){
								  
								  case 0://Fachada Monumental	
										  
									  Tracking.Maps_FM(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);//Cambiar Az
									  MapTrackingSelected = Tracking.Map;
									  
								  break;
								  case 1: //Criptoportico
									  Tracking.Maps_CR(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
									  MapTrackingSelected = Tracking.Map;

								  break;
								  case 2://Curia
									  Tracking.Maps_CU(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
									  MapTrackingSelected = Tracking.Map; 

								  break;
								  case 3:// Decumano
									  Tracking.Maps_DEC(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
									  MapTrackingSelected = Tracking.Map;

								  break;
								  case 4://Basilica
									  Tracking.Maps_BAS(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
									  MapTrackingSelected = Tracking.Map;

								  break;
								  case 5://Portico Sur 
									  Tracking.Maps_PS(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
									  MapTrackingSelected = Tracking.Map;

								  break;
								  case 6://Termas Sur
									  Tracking.Maps_TS(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
									  MapTrackingSelected = Tracking.Map;

								  break;
								  case 7://Mercado
									  Tracking.Maps_MER(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
									  MapTrackingSelected = Tracking.Map;

								  break;
								  case 8://Ninfeo
									  Tracking.Maps_NI(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
									  MapTrackingSelected = Tracking.Map;

								  break;
								  case 9://Criptoportico int
									  Tracking.Maps_CR(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
									  MapTrackingSelected = Tracking.Map;

								  break;
								  case 10://Curia int
									  Tracking.Maps_CU(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
									  MapTrackingSelected = Tracking.Map;

								  break;
								  case 11: // Basilica int
									  Tracking.Maps_BAS(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
									  MapTrackingSelected = Tracking.Map;

								  break;
								  case 12: // Termas sur int
									  Tracking.Maps_TS(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
									  MapTrackingSelected = Tracking.Map;

								  break;
								  case 13: //Mercado SIII
									  Tracking.Maps_MER(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
									  MapTrackingSelected = Tracking.Map;

								  break;
								  case 14: //Ninfeo int
									  Tracking.Maps_NI(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
									  MapTrackingSelected = Tracking.Map;

								  break;
														  
								  }//end switch
								  

								  Status = true;

								  final String finalMapTracking;
									
								 Toast.makeText(getApplicationContext(), "Se va a cambiar el Map a " + MapTrackingSelected, Toast.LENGTH_SHORT).show();

								  if(Status == true && Tracking.reset == false){
									  	Map_act = Tracking.Map;
									  	Es_act = Tracking.Es;
										Tx_act = Tracking.Tx;
										Ty_act = Tracking.Ty;
										Tz_act = Tracking.Tz;
										Rx_act = Tracking.Rx;
										Ry_act = Tracking.Ry;
										Rz_act = Tracking.Rz;
									  
								  
								  mSurfaceView.queueEvent(new Runnable()
									{

										@Override
										public void run() 
										{
											if (MapTrackingActual != Map_act){		
												
												MapTrackingActual = Map_act;

												final String dirTrackingMap = AssetsManager.getAssetPath(Map_act + TipoFichero);
												boolean result = metaioSDK.setTrackingConfiguration(dirTrackingMap);
												if(Modelo3D != null){
													Modelo3D.setTranslation(new Vector3d(Tx_act, Ty_act, Tz_act));
													Modelo3D.setScale(new Vector3d(Es_act, Es_act, Es_act));
													Modelo3D.setRotation(new Rotation(new Vector3d(Rx_act, Ry_act, Rz_act)));
												}
												MapaCambiado = true;
												Status = false;
											} //end if
										}									
									});

								  nIntentos = 0;

								if(MapaCambiado){
									Toast.makeText(getApplicationContext(), Tracking.Map + " Ind= " + String.valueOf(Tracking.indice)+ " Es= " +String.valueOf(Tracking.Es)
											+ " Tx= " +String.valueOf(Tracking.Tx)+ " Ty= " + String.valueOf(Tracking.Ty) + " Tz= " + String.valueOf(Tracking.Tz)
											+ " Rx= " + String.valueOf(Tracking.Rx) + " Ry= " + String.valueOf(Tracking.Ry)+ " Rz= " + String.valueOf(Tracking.Rz)  ,Toast.LENGTH_SHORT).show();
								//	Toast.makeText(getApplicationContext(), "Se ha cambiado el Map a " + MapTrackingSelected, Toast.LENGTH_SHORT).show();
									MapaCambiado = false;
								}
								  }
							  }else{
								  nIntentos = 0;
									Map_ok = Tracking.Map;
									MapTrackingSelected = Tracking.Map;
									Es_ok = Tracking.Es;
									Tx_ok = Tracking.Tx;
									Ty_ok = Tracking.Ty;
									Tz_ok = Tracking.Tz;
									Rx_ok = Tracking.Rx;
									Ry_ok = Tracking.Ry;
									Rz_ok = Tracking.Rz;
									indice_ok = Tracking.indice;
									Xmap_ok = Tracking.Xmap;
									Ymap_ok = Tracking.Ymap;
							  }//end if	
							   	
						    }
						});//end runOnUiThread

				 }//end TrackingValues tv
		}//end for    */
		//	}//end MapTrackingSelected  

		}//onTrackingEvent()
	}//End AdminMetaioSDK
	
	public void Act_parametros(){
		Map_act = Tracking.Map;
		Es_act = Tracking.Es;
		Tx_act = Tracking.Tx;
		Ty_act = Tracking.Ty;
		Tz_act = Tracking.Tz;
		Rx_act = Tracking.Rx;
		Ry_act = Tracking.Ry;
		Rz_act = Tracking.Rz;
		Xmap_ok = Tracking.Xmap;
		Ymap_ok = Tracking.Ymap;
		indice_ok = Tracking.indice;
		
	}
	
	@Override
	protected void onDestroy(){
		synchronized(this){
		AdminSensores.unregisterListener(this, Acelerometro);
		AdminSensores.unregisterListener(this, SensorCampoMagnetico);
		SensoresOn = false;
		
		super.onDestroy();
		mAdminMetaioSDK.delete();
		mAdminMetaioSDK = null;

		}
		
	}//End onDestroy
	
	/** Método que despliega un menú emergente donde elegir el monumento a recrear */
	public void SeleccionEdificio(){
		
		//Opciones que aparecerán
		CharSequence itemsId[] ={"FACHADA MONUMENTAL", "CRIPTOPORTICO", "CURIA", "DECUMANO III", "BASILICA",
									"PORTICO SUR", "TERMAS SUR", "MERCADO", "NINFEO"};
		
		//Configurador del cuadro de dialogo
		AlertDialog.Builder OpcSelEdifDialogConf = new AlertDialog.Builder(RealAumentada.this);
		
		OpcSelEdifDialogConf.setItems(itemsId, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// which empieza por el valor 0. 
				
				if (which == FACHADA_MONUMENTAL){
					EdifActivo = FACHADA_MONUMENTAL;
					Modelo3DSelected = "Fachada_Monumental.zip";
					CargaContenido();
					Reset();					
					
				}else if (which == CRIPTOPORTICO){
					EdifActivo = CRIPTOPORTICO;
					Seleccion_ext_int();
					
					
				}else if (which == CURIA){
					EdifActivo = CURIA;
					Seleccion_ext_int();
					
				}else if (which == DECUMANOIII){					
					EdifActivo = DECUMANOIII;
					Modelo3DSelected = "Decumano_III.zip";
					CargaContenido();
					Reset();
					
				}else if (which == BASILICA){
					EdifActivo = BASILICA;
					Seleccion_ext_int();
					
				}else if (which == PORTICO_SUR){
					EdifActivo = PORTICO_SUR;
					Modelo3DSelected ="Portico_Sur_SIII.zip"; 
					CargaContenido();
					Reset();
					
				}else if (which == TERMAS_SUR){
					EdifActivo = TERMAS_SUR;					
					Seleccion_ext_int();
					
				}else if (which == MERCADO_SI){
					EdifActivo = MERCADO_SI;
					Seleccion_si_siii();
			
				}else {
					EdifActivo = NINFEO;
					Seleccion_ext_int();
				}//End if
				
			}//End onClick
		});//End setItems
		
		//Creamos el dialogo y lo mostramos.
		AlertDialog SelEdifDialog = OpcSelEdifDialogConf.create();
		SelEdifDialog.show();
		
		
	}//End SeleccionEdificio
	
public void Seleccion_ext_int(){
		
		//Opciones que aparecerán
		CharSequence itemsId[] ={"EXTERIOR", "INTERIOR"};
		
		//Configurador del cuadro de dialogo
		AlertDialog.Builder OpcSelEdifDialogConf = new AlertDialog.Builder(RealAumentada.this);
		
		OpcSelEdifDialogConf.setItems(itemsId, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// which empieza por el valor 0. 
				if (which == 0){//exterior
					switch(EdifActivo){
					case 1://Criptoportico_ext
						EdifActivo = CRIPTOPORTICO;
						Modelo3DSelected = "Criptoportico_ext.zip";
						CargaContenido();
						Reset();
					break;
					case 2://Curia_ext
						EdifActivo = CURIA;
						Modelo3DSelected = "Curia_ext.zip";
						CargaContenido();
						Reset();
					break;
					case 4: //Basilica_ext
						EdifActivo = BASILICA;
						Modelo3DSelected = "Basilica_ext.zip";
						CargaContenido();
						Reset();
					break;
					case 6: //Termas_Sur_ext
						EdifActivo = TERMAS_SUR;
						Modelo3DSelected = "Termas_Sur_ext.zip";
						CargaContenido();
						Reset();
					break;
					case 8://Ninfeo ext
						EdifActivo = NINFEO;
						Modelo3DSelected = "Ninfeo_ext.zip";
						CargaContenido();
						Reset();
					break;					
					}//end switch
					
				}else{//interior
					switch(EdifActivo){
					case 1://Criptoportico_int
						EdifActivo = CRIPTOPORTICO_INT;
						Modelo3DSelected = "Criptoportico_int.zip";
						CargaContenido();
						Reset();
					break;
					case 2://Curia_int
						EdifActivo = CURIA_INT;
						Modelo3DSelected = "Curia_int.zip";
						CargaContenido();
						Reset();
					break;
					case 4: //Basilica_int
						EdifActivo = BASILICA_INT;
						Modelo3DSelected = "Basilica_int.zip";
						CargaContenido();
						Reset();
					break;
					case 6: //Termas_Sur_int
						EdifActivo = TERMAS_SUR_INT;
						Modelo3DSelected = "Termas_Sur_int.zip";
						CargaContenido();
						Reset();
						nIntentos = 0;
					break;
					case 8://Ninfeo int
						EdifActivo = NINFEO_INT;
						Modelo3DSelected = "Ninfeo_int.zip";
						CargaContenido();
						Reset();
					break;					
					}	
				}
				
			}//End onClick
		});//End setItems
		
		//Creamos el dialogo y lo mostramos.
		AlertDialog SelEdifDialog = OpcSelEdifDialogConf.create();
		SelEdifDialog.show();
				
	}//End Seleccion_ext_int

public void Seleccion_si_siii(){
	
	//Opciones que aparecerán
	CharSequence itemsId[] ={"SI", "SIII"};
	
	//Configurador del cuadro de dialogo
	AlertDialog.Builder OpcSelEdifDialogConf = new AlertDialog.Builder(RealAumentada.this);
	
	OpcSelEdifDialogConf.setItems(itemsId, new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// which empieza por el valor 0. 
			if (which == 0){//SI
				switch(EdifActivo){
				case 7://Mercado
					EdifActivo = MERCADO_SI;
					Modelo3DSelected = "Mercado_SI.zip";
					CargaContenido();
					Reset();
				break;
				
				}//end switch
				
			}else{//SIII
				switch(EdifActivo){
				case 7://Mercado
					EdifActivo = MERCADO_SIII;
					Modelo3DSelected = "Mercado_SIII.zip";
					CargaContenido();
					Reset();
				break;				
				}	
			}
			
		}//End onClick
	});//End setItems
	
	//Creamos el dialogo y lo mostramos.
	AlertDialog SelEdifDialog = OpcSelEdifDialogConf.create();
	SelEdifDialog.show();
			
}//End Seleccion_ext_int



public void Reset(){
	
	switch(EdifActivo){
	case 0://FM
		if(AzFiltrado == 40){
			Tracking.reset = true;	
			Tracking.indice = 0; 
			Tracking.Maps_FM(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
			MapTrackingSelected = Tracking.Map;
			Act_parametros();
			CargaTrackingMap();
			Tracking.reset = false;	
						
		}else if(AzFiltrado == 90){
			Tracking.reset = true;	
			Tracking.indice = 0; 
			Tracking.Maps_FM(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
			MapTrackingSelected = Tracking.Map;
			Act_parametros();
			CargaTrackingMap();			
			Tracking.reset = false;	
		}
	break;
	case 1://CR 
		if(AzFiltrado == 40){
			Tracking.reset = true;	
			Tracking.indice = 0; 
			Tracking.Maps_CR(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
			MapTrackingSelected = Tracking.Map;
			Act_parametros();
			CargaTrackingMap();
			Tracking.reset = false;	
			
		}else if(AzFiltrado == 90){
			Tracking.reset = true;	
			Tracking.indice = 0; 
			Tracking.Maps_CR(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
			MapTrackingSelected = Tracking.Map;
			Act_parametros();
			CargaTrackingMap();
			Tracking.reset = false;			
		}else if(AzFiltrado == 377){
			Tracking.reset = true;	
			Tracking.indice = 1; 
			Tracking.Maps_CR(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
			MapTrackingSelected = Tracking.Map;
			Act_parametros();
			CargaTrackingMap();
			Tracking.reset = false;	
		}
	break; 
	case 2: //CU
		if(AzFiltrado == 10){
			Tracking.reset = true;	
			Tracking.indice = 0;
			Tracking.Maps_CU(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
			MapTrackingSelected = Tracking.Map;
			Act_parametros();
			CargaTrackingMap();
			Tracking.reset = false;	
			
		}else if(AzFiltrado == 77){
			Tracking.reset = true;	
			Tracking.indice = 0; 
			Tracking.Maps_CU(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
			MapTrackingSelected = Tracking.Map;
			Act_parametros();
			CargaTrackingMap();
			Tracking.reset = false;	
			
		
		}else if(AzFiltrado == 155){
			Tracking.reset = true;	
			Tracking.indice = 0; 
			Tracking.Maps_CU(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
			MapTrackingSelected = Tracking.Map;
			Act_parametros();
			CargaTrackingMap();
			Tracking.reset = false;	
				
		}else if(AzFiltrado == 230){
			Tracking.reset = true;	
			Tracking.indice = 2; 
			Tracking.Maps_CU(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
			MapTrackingSelected = Tracking.Map;
			Act_parametros();
			CargaTrackingMap();
			Tracking.reset = false;				
			
		}else if(AzFiltrado == 360){
			Tracking.reset = true;	
			Tracking.indice = 0; 
			Tracking.Maps_CU(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
			MapTrackingSelected = Tracking.Map;
			Act_parametros();
			CargaTrackingMap();
			Tracking.reset = false;				
		}
	break;
	case 3://DIII
		if(AzFiltrado == 105){
			Tracking.reset = true;	
			Tracking.indice = 0; 
			Tracking.Maps_DEC(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
			MapTrackingSelected = Tracking.Map;
			Act_parametros();
			CargaTrackingMap();
			Tracking.reset = false;	
			
			
		}else if(AzFiltrado == 190){
			Tracking.reset = true;	
			Tracking.indice = 1;
			Tracking.Maps_DEC(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
			MapTrackingSelected = Tracking.Map;
			Act_parametros();
			CargaTrackingMap();
			Tracking.reset = false;	
		
		}else if(AzFiltrado == 230){
			Tracking.reset = true;	
			Tracking.indice = 2; 
			Tracking.Maps_DEC(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
			MapTrackingSelected = Tracking.Map;
			Act_parametros();
			CargaTrackingMap();
			Tracking.reset = false;				
		}
	break;
	case 4://BAS
		if(AzFiltrado == 190){
			Tracking.reset = true;	
			Tracking.indice = 1;
			Tracking.Maps_BAS(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
			MapTrackingSelected = Tracking.Map;
			Act_parametros();
			CargaTrackingMap();
			Tracking.reset = false;
			
		}else if(AzFiltrado == 230){
			Tracking.reset = true;	
			Tracking.indice = 2; 
			Tracking.Maps_BAS(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
			MapTrackingSelected = Tracking.Map;
			Act_parametros();
			CargaTrackingMap();
			Tracking.reset = false;
			
		}else if(AzFiltrado == 241){
			Tracking.reset = true;	
			Tracking.indice = 0; 
			Tracking.Maps_BAS(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
			MapTrackingSelected = Tracking.Map;
			Act_parametros();
			CargaTrackingMap();
			Tracking.reset = false;
			
		}
	break;
	case 5://PS
		if(AzFiltrado == 205){
			Tracking.reset = true;	
			Tracking.indice = 0; 
			Tracking.Maps_PS(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
			MapTrackingSelected = Tracking.Map;
			Act_parametros();
			CargaTrackingMap();
			Tracking.reset = false;
			
			
		}else if(AzFiltrado == 247){
			Tracking.reset = true;	
			Tracking.indice = 1;
			Tracking.Maps_PS(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
			MapTrackingSelected = Tracking.Map;
			Act_parametros();
			CargaTrackingMap();
			Tracking.reset = false;
		}
	break;
	case 6://TS
		if(AzFiltrado == 245){
			Tracking.reset = true;	
			Tracking.indice = 1; 
			Tracking.Maps_TS(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
			MapTrackingSelected = Tracking.Map;
			Act_parametros();
			CargaTrackingMap();
			Tracking.reset = false;
			
		}else if(AzFiltrado == 281){
			Tracking.reset = true;	
			Tracking.indice = 0; 
			Tracking.Maps_TS(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
			MapTrackingSelected = Tracking.Map;
			Act_parametros();
			CargaTrackingMap();
			Tracking.reset = false;			
		
		}else if(AzFiltrado == 10){
			Tracking.reset = true;	
			Tracking.indice = 0; 
			Tracking.Maps_TS(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
			MapTrackingSelected = Tracking.Map;
			Act_parametros();
			CargaTrackingMap();
			Tracking.reset = false;				
		}
	break;
	case 7://M_SI
		if(AzFiltrado == 95){
			Tracking.reset = true;	
			Tracking.indice = 1; 
			Tracking.Maps_MER(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
			MapTrackingSelected = Tracking.Map;
			Act_parametros();
			CargaTrackingMap();
			Tracking.reset = false;				
		
		}else if(AzFiltrado == 153){
			Tracking.reset = true;	
			Tracking.indice = 0; 
			Tracking.Maps_MER(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
			MapTrackingSelected = Tracking.Map;
			Act_parametros();
			CargaTrackingMap();
			Tracking.reset = false;	
			
		}else if(AzFiltrado == 182){
			Tracking.reset = true;	
			Tracking.indice = 1; 
			Tracking.Maps_MER(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
			MapTrackingSelected = Tracking.Map;
			Act_parametros();
			CargaTrackingMap();
			Tracking.reset = false;				
			
		}
	break;
	case 8://NI
		if(AzFiltrado == 100){
			Tracking.reset = true;	
			Tracking.indice = 0; //Criptoportico 23
			Tracking.Maps_NI(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
			MapTrackingSelected = Tracking.Map;
			Act_parametros();
			CargaTrackingMap();
			Tracking.reset = false;	
			
		}else if(AzFiltrado == 360){
			Tracking.reset = true;	
			Tracking.indice = 0; //Criptoportico 28
			Tracking.Maps_NI(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
			MapTrackingSelected = Tracking.Map;
			Act_parametros();
			CargaTrackingMap();
			Tracking.reset = false;			
		}
	break;
	case 9://CR_INT
		if(AzFiltrado == 40){
			Tracking.reset = true;	
			Tracking.indice = 0; 
			Tracking.Maps_CR(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
			MapTrackingSelected = Tracking.Map;
			Act_parametros();
			CargaTrackingMap();
			Tracking.reset = false;	
			
		}else if(AzFiltrado == 90){
			Tracking.reset = true;	
			Tracking.indice = 0; 
			Tracking.Maps_CR(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
			MapTrackingSelected = Tracking.Map;
			Act_parametros();
			CargaTrackingMap();
			Tracking.reset = false;	
			
		}else if(AzFiltrado == 377){
			Tracking.reset = true;	
			Tracking.indice = 1; 
			Tracking.Maps_CR(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
			MapTrackingSelected = Tracking.Map;
			Act_parametros();
			CargaTrackingMap();
			Tracking.reset = false;	
		}
		
	break;
	case 10://CU_INT
		if(AzFiltrado == 10){
			Tracking.reset = true;	
			Tracking.indice = 0;
			Tracking.Maps_CU(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
			MapTrackingSelected = Tracking.Map;
			Act_parametros();
			CargaTrackingMap();
			Tracking.reset = false;	
			
		}else if(AzFiltrado == 77){
			Tracking.reset = true;	
			Tracking.indice = 0; 
			Tracking.Maps_CU(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
			MapTrackingSelected = Tracking.Map;
			Act_parametros();
			CargaTrackingMap();
			Tracking.reset = false;	
			
		
		}else if(AzFiltrado == 155){
			Tracking.reset = true;	
			Tracking.indice = 0; 
			Tracking.Maps_CU(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
			MapTrackingSelected = Tracking.Map;
			Act_parametros();
			CargaTrackingMap();
			Tracking.reset = false;	
				
		}else if(AzFiltrado == 230){
			Tracking.reset = true;	
			Tracking.indice = 2;
			Tracking.Maps_CU(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
			MapTrackingSelected = Tracking.Map;
			Act_parametros();
			CargaTrackingMap();
			Tracking.reset = false;				
			
		}else if(AzFiltrado == 360){
			Tracking.reset = true;	
			Tracking.indice = 0; 
			Tracking.Maps_CU(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
			MapTrackingSelected = Tracking.Map;
			Act_parametros();
			CargaTrackingMap();
			Tracking.reset = false;				
		}
		
	break;
	case 11://BAS_INT
		if(AzFiltrado == 190){
			Tracking.reset = true;	
			Tracking.indice = 1;
			Tracking.Maps_BAS(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
			MapTrackingSelected = Tracking.Map;
			Act_parametros();
			CargaTrackingMap();
			Tracking.reset = false;
			
		}else if(AzFiltrado == 230){
			Tracking.reset = true;	
			Tracking.indice = 2; 
			Tracking.Maps_BAS(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
			MapTrackingSelected = Tracking.Map;
			Act_parametros();
			CargaTrackingMap();
			Tracking.reset = false;
			
		}else if(AzFiltrado == 241){
			Tracking.reset = true;	
			Tracking.indice = 0; 
			Tracking.Maps_BAS(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
			MapTrackingSelected = Tracking.Map;
			Act_parametros();
			CargaTrackingMap();
			Tracking.reset = false;			
		}
		
	break;
	case 12://TS_INT
		if(AzFiltrado == 245){
			Tracking.reset = true;	
			Tracking.indice = 1; 
			Tracking.Maps_TS(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
			MapTrackingSelected = Tracking.Map;
			Act_parametros();
			CargaTrackingMap();
			Tracking.reset = false;
			
		}else if(AzFiltrado == 281){
			Tracking.reset = true;	
			Tracking.indice = 0;
			Tracking.Maps_TS(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
			MapTrackingSelected = Tracking.Map;
			Act_parametros();
			CargaTrackingMap();
			Tracking.reset = false;			
		
		}else if(AzFiltrado == 10){
			Tracking.reset = true;	
			Tracking.indice = 0; 
			Tracking.Maps_TS(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
			MapTrackingSelected = Tracking.Map;
			Act_parametros();
			CargaTrackingMap();
			Tracking.reset = false;				
		}		
	break;
	case 13://M_SIII
		if(AzFiltrado == 95){
			Tracking.reset = true;	
			Tracking.indice = 1; 
			Tracking.Maps_MER(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
			MapTrackingSelected = Tracking.Map;
			Act_parametros();
			CargaTrackingMap();
			Tracking.reset = false;				
		
		}else if(AzFiltrado == 153){
			Tracking.reset = true;	
			Tracking.indice = 0; 
			Tracking.Maps_MER(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
			MapTrackingSelected = Tracking.Map;
			Act_parametros();
			CargaTrackingMap();
			Tracking.reset = false;	
			
		}else if(AzFiltrado == 182){
			Tracking.reset = true;	
			Tracking.indice = 1; 
			Tracking.Maps_MER(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
			MapTrackingSelected = Tracking.Map;
			Act_parametros();
			CargaTrackingMap();
			Tracking.reset = false;				
			
		}

	break;
	case 14://N_INT
		if(AzFiltrado == 100){
			Tracking.reset = true;	
			Tracking.indice = 0; 
			Tracking.Maps_NI(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
			MapTrackingSelected = Tracking.Map;
			Act_parametros();
			CargaTrackingMap();
			Tracking.reset = false;	
			
		}else if(AzFiltrado == 360){
			Tracking.reset = true;	
			Tracking.indice = 0; 
			Tracking.Maps_NI(EdifActivo, AzFiltrado, Xmap_ok, Ymap_ok);
			MapTrackingSelected = Tracking.Map;
			Act_parametros();
			CargaTrackingMap();
			Tracking.reset = false;			
		}
		
	}	
}

private double Filtro_Az(double Az){
	double Azimut = 0;
	switch (EdifActivo){
	case 0://Fachada Monumental
		if(Az >= 65 && Az < 115) Azimut = 90;
		else Azimut = 40;
	break;
	case 1://Criptoportico
		if(Az >= 8.5 && Az < 65) Azimut = 40;
		else if(Az >= 65 && Az < 233) Azimut = 90;
		else Azimut = 377;		
	break;
	case 2://Curia
		if(Az >= 43.5 && Az < 116) Azimut = 77;
		else if(Az >=116 && Az < 192.5) Azimut = 155;
		else if(Az >= 192.5 && Az < 295) Azimut = 230;
		else if(Az >= 295 && Az < 385) Azimut = 360;
		else Azimut = 10;
	break;
	case 3://Decumano
		if(Az >= 147.5 && Az < 210) Azimut = 190;
		else if (Az >= 210 && Az < 367) Azimut = 230;
		else Azimut = 105;
	
	break;
	case 4://Basilica
		if(Az >= 10 && Az < 210) Azimut = 190;
		else Azimut = 230;
	break;
	case 5://Portico Sur
		if(Az >= 26 && Az < 226) Azimut = 205;
		else Azimut = 247;
	break;
	case 6://Termas Sur
		if(Az >= 127 && Az <= 263) Azimut = 245;
		else if (Az >= 263 && Az <= 395) Azimut = 281;
		else Azimut = 10;
	break;
	case 7://Mercado SI
		if(Az >= 124 && Az < 167.5) Azimut = 153;
		else if( Az >=167 && Az < 338)Azimut = 182;
		else Azimut = 95;
	break;
	case 8://Ninfeo
		if(Az >= 30 && Az < 280) Azimut = 100;
		else Azimut = 360;
	break;
	case 9://Criptoportico INT
		if(Az >= 8.5 && Az < 65) Azimut = 40;
		else if(Az >= 65 && Az < 233) Azimut = 90;
		else Azimut = 377;	
	break;
	case 10://Curia INT
		if(Az >= 43.5 && Az < 116) Azimut = 77;
		else if(Az >=116 && Az < 192.5) Azimut = 155;
		else if(Az >= 192.5 && Az < 295) Azimut = 230;
		else if(Az >= 295 && Az < 385) Azimut = 360;
		else Azimut = 10;
	break;
	case 11://Basilica INT
		if(Az >= 10 && Az < 210) Azimut = 190;
		else Azimut = 230;
	break;
	case 12://Termas Sur INT
		if(Az >= 127 && Az <= 263) Azimut = 245;
		else if (Az >= 263 && Az <= 395) Azimut = 281;
		else Azimut = 10;
	break;
	case 13://Mercado SIII
		if(Az >= 124 && Az < 167.5) Azimut = 153;
		else if( Az >=167 && Az < 338)Azimut = 182;
		else Azimut = 95;
	break;
	case 14://Ninfeo INT
		if(Az >= 30 && Az < 280) Azimut = 100;
		else Azimut = 360;
	break;
	}
	
	return Azimut;
}
}//End activity

