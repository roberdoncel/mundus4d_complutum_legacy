package pfc.mundus4d.complutum;



import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;








import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.google.android.vending.expansion.downloader.DownloadProgressInfo;
import com.google.android.vending.expansion.downloader.DownloaderClientMarshaller;
import com.google.android.vending.expansion.downloader.DownloaderServiceMarshaller;
import com.google.android.vending.expansion.downloader.Helpers;
import com.google.android.vending.expansion.downloader.IDownloaderClient;
import com.google.android.vending.expansion.downloader.IDownloaderService;
import com.google.android.vending.expansion.downloader.IStub;
import com.google.android.vending.expansion.downloader.impl.DownloaderService;
import com.metaio.sdk.MetaioDebug;
import com.metaio.tools.io.AssetsManager;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Messenger;
import android.os.storage.OnObbStateChangeListener;
import android.os.storage.StorageManager;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Launcher extends Activity implements IDownloaderClient {

	
	private ExtractorAssets loadAssets;
	private View icoProgreso;
	private int InicioApp = 0;
	private IStub mDownloaderClientStub;
	private IDownloaderService mRemoteService;
	private TextView mensInstalacion;
	private boolean Descargado = false; 
	public final static String PATH_COMUN = "/Android/obb/";
	File ruta = Environment.getExternalStorageDirectory();	
	String packageName = "pfc.mundus4d.complutum";
	public boolean existOBB = false; 
	private String OBB_File = "main.4.pfc.mundus4d.complutum.obb";

	
	private static final int NO_INICIADO = 0;
	private static final int INICIADO_UI = 1;
	private static final int CHECKED_OBB = 2;
	private static final int UNZIPPED_OBB = 3;
	private static final int EXTRAER_ASSETS_E_INCIAR = 4;

	
	private static class XAPKFile { //Estructura con las características del archivo de expansión.
	        public final boolean mIsMain;
	        public final int mFileVersion;
	        public final long mFileSize;

	        XAPKFile(boolean isMain, int fileVersion, long fileSize) {
	            mIsMain = isMain;
	            mFileVersion = fileVersion;
	            mFileSize = fileSize;
	        }
	    }
	   
	   /** Datos del archivo de expansión. */
	    private final XAPKFile[] xAPKS = {
           new XAPKFile(
                   true, // true significa archivo de expansión primario.
                   1, // la versión del apk con la que se ha subido el archivo de expansión.
                   54526983L // longitud del archivo de expansión en bytes.
                   ),           
   };
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launcher);
		
		mensInstalacion = (TextView)findViewById(R.id.estado_instalacion);
		mDownloaderClientStub = DownloaderClientMarshaller.CreateStub(this, Asist_Descarga.class);
		
		//Comprobación de que el sistema ha grabado que la App ha sido anteriormente inicializada y que al pasar
		//de nuevo por aquí lo que queremos es realmente salir de ella. 		
		if((savedInstanceState != null) && (savedInstanceState.getSerializable("ExitApp") != null)) {
			finish();
				
		}else{
			inicioSecuencial(NO_INICIADO);
		}//End if inicio App.
				
			
	}//End onCreate
	
	
	private void inicioSecuencial(int status){
		
		switch(status){
			case NO_INICIADO:
				iniciar_UI();
			break;
			case INICIADO_UI:
				check_OBB();
			break;
			case CHECKED_OBB:
				check_Zip();
			break;
			case UNZIPPED_OBB:
				siZipInstalado();
			break;
			
		}//end switch
		
	}
	private void iniciar_UI(){
		setContentView(R.layout.activity_launcher);
		inicioSecuencial(INICIADO_UI);
	}
	
	/** Método para comprobar si existe el archivo .OBB */
	private void check_OBB(){
		
		File Archivo_Expansion = new File (ruta.toString() + PATH_COMUN + packageName + "/" + OBB_File);
		if (Archivo_Expansion.exists()){
			existOBB = true;
			inicioSecuencial(CHECKED_OBB);
		}else{
			descarga_OBB();
		}
	}
	
	/** Método encargado de comprobar la existencia del archivo de expansión y descargarlo si fuese necesario. */ 
	private void descarga_OBB(){
		//Iniciamos la descarga del fichero de expansión si fuese necesario.		
		if (!expansionFilesDelivered()) {
		
			try{
                Intent launchIntent = Launcher.this
                        .getIntent();
                Intent intentToLaunchThisActivityFromNotification = new Intent(
                        Launcher.this, Launcher.this.getClass());
                intentToLaunchThisActivityFromNotification.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentToLaunchThisActivityFromNotification.setAction(launchIntent.getAction());

                if (launchIntent.getCategories() != null) {
                    for (String category : launchIntent.getCategories()) {
                        intentToLaunchThisActivityFromNotification.addCategory(category);
                    }
                }

                // Build PendingIntent used to open this activity from
                // Notification
                PendingIntent pendingIntent = PendingIntent.getActivity(
                        Launcher.this,
                        0, intentToLaunchThisActivityFromNotification,
                        PendingIntent.FLAG_UPDATE_CURRENT);

				// Start the download service (if required)  
				int startResult = DownloaderClientMarshaller.startDownloadServiceIfRequired(this, pendingIntent, Asist_Descarga.class);
				// If download has started, initialize this activity to show download progress
				
				if (startResult != DownloaderClientMarshaller.NO_DOWNLOAD_REQUIRED) {
				// This is where you do set up to display the download progress (next step)
					mDownloaderClientStub = DownloaderClientMarshaller.CreateStub(this, Asist_Descarga.class);
					return;
				} // If the download wasn't necessary, fall through to start the app
			}catch(NameNotFoundException e){

    		}//end try
			
		}
		if(existOBB)inicioSecuencial(CHECKED_OBB);			
	}
	
	/** Método encargado de descomprimir el archivo de expansión si fuese necesario. */
	private void check_Zip(){
		if(descomprimir_Zip(OBB_File)){
			inicioSecuencial(UNZIPPED_OBB);
		}
	}
	
	
	@Override
	protected void onStart() {
	    if (null != mDownloaderClientStub) {
	        mDownloaderClientStub.connect(this);
	    }
	    super.onStart();
	} 
	
	@Override
	protected void onResume() {
	    if (null != mDownloaderClientStub) {
	        mDownloaderClientStub.connect(this);
	    }
	    super.onResume();
	}
	
	@Override
	protected void onStop() {
	    if (null != mDownloaderClientStub) {
	        mDownloaderClientStub.disconnect(this);
	    }
	    super.onStop();
	}
	


	
	private class ExtractorAssets extends AsyncTask<Integer, Integer, Boolean>{

		@Override
		protected Boolean doInBackground(Integer... arg0) {
			// TODO Auto-generated method stub
			
			try{
				AssetsManager.extractAllAssets(getApplicationContext(), true);
			}catch (IOException e){
				MetaioDebug.printStackTrace(Log.ERROR, e);
      			//Incluir mensaje de alerta al usuario.
				return false;
			}
			return true;
		}//End doInBackground
		
		@Override
		protected void onPostExecute(Boolean Resultado){
			if (Resultado){
				InicioApp = 1;
				Intent IrMenuPrincipal = new Intent(getApplicationContext(), MenuPrincipal.class);
				startActivity(IrMenuPrincipal);
				
			}else{
			//INCLUIR MENSAJE DE ADVERTENCIA AL USUARIO.
			
		}//End if
	}//End onPostExecute
	
	}//End ExtractorAssets
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.launcher, menu);
		return true;
	}
	
	/** Método que se encarga de guardar datos del estado de la actividad.
	 *  En este caso vamos a guardar en el sistema un valor para que nos 
	 *  indique si la actividad ha sido iniciada anteriormente,
	 *  en ese caso al iniciar la actividad de nuevo, se pasará a salir de la misma. */
	@Override
	protected void onSaveInstanceState(Bundle state) {
	    super.onSaveInstanceState(state);
	    state.putSerializable("ExitApp", InicioApp);
	}
	
	
	
	
	/** Método encargado de comprobar si el archivo de expansión está presente y si su tamaño es correcto. */
    boolean expansionFilesDelivered() {
        for (XAPKFile xf : xAPKS) {
            String fileName = Helpers.getExpansionAPKFileName(this, xf.mIsMain, xf.mFileVersion);
            if (!Helpers.doesFileExist(this, fileName, xf.mFileSize, false))
                return false;
        }
        existOBB = true;
        return true;
    }
	
	
	@Override
	public void onServiceConnected(Messenger m) {
		// TODO Auto-generated method stub
	    mRemoteService = DownloaderServiceMarshaller.CreateProxy(m);
	    mRemoteService.onClientUpdated(mDownloaderClientStub.getMessenger());
		
	}
	
	/** Método dónde específicar que hacer en las distintas etapas de la descarga. */
	@Override
	public void onDownloadStateChanged(int newState) {
		// TODO Auto-generated method stub
		switch(newState){
			case IDownloaderClient.STATE_DOWNLOADING:
				//mensInstalacion.setText("Descargando");				
			break;
			case IDownloaderClient.STATE_COMPLETED:
				mensInstalacion.setText("Descarga completa...");
				existOBB = true;
				inicioSecuencial(CHECKED_OBB);
			break;
			case IDownloaderClient.STATE_FAILED_CANCELED:
				mensInstalacion.setText("Descarga cancelada.");	
			break;
			case IDownloaderClient.STATE_IDLE://El sistema está conectado y es seguro empezar la descarga.
			break;
			case IDownloaderClient.STATE_FAILED_UNLICENSED:
				mensInstalacion.setText("Error al comprobar la licencia.");
			break;
			case IDownloaderClient.STATE_PAUSED_NEED_CELLULAR_PERMISSION:
			break;
			case IDownloaderClient.STATE_PAUSED_WIFI_DISABLED_NEED_CELLULAR_PERMISSION:
				mensInstalacion.setText("WIFI NO DISPONIBLE. Se necesita permiso para utilizar la descarga por datos.");
			break;
			case IDownloaderClient.STATE_PAUSED_SDCARD_UNAVAILABLE:
				mensInstalacion.setText("Error en la descarga. No se encuentra disponible la memoria externa.");
			break;
				
		}
		
	}

	@Override
	public void onDownloadProgress(DownloadProgressInfo progress) {
		// TODO Auto-generated method stub
		mensInstalacion.setText( "Descargando contenido... " + Long.toString(progress.mOverallProgress / progress.mOverallTotal * 100) + "%");
	}
	
	
		
	//Si el fichero .obb ha sido instalado.
	public void siZipInstalado (){
		icoProgreso = findViewById(R.id.progressBar1);
	    loadAssets = new ExtractorAssets();
	    loadAssets.execute(0);
	}
	
	/** Método para descomprimir el archivo de expansión. Como argumento se pasa el nombre del archivo. */
	public boolean descomprimir_Zip(String nameExpansionFile){
		
	
		final String  rutaZip = ruta.toString() + PATH_COMUN + packageName + "/" + nameExpansionFile;
		File M4dComplutum_dir =  new File(Environment.getExternalStorageDirectory() + "/Mundus4DComplutum/");
		M4dComplutum_dir.mkdirs();
		String dirOutput = Environment.getExternalStorageDirectory() + "/Mundus4DComplutum/";
		
		
		
	     InputStream is;
	     ZipInputStream zis;
	     try 
	     {
  
	         is = new FileInputStream(rutaZip);
	         zis = new ZipInputStream(new BufferedInputStream(is)); //Objeto Zip creado          
	         ZipEntry ze;//Cada fichero contenido en el Zip
	         byte[] buffer = new byte[1024]; //Un valor aleatorio del tamaño del buffer. Se usa para ir entregando
	         								 //la información en paquetes cada vez que se llena.
	         int count;
	         String filename;
	         while ((ze = zis.getNextEntry()) != null) {

	             filename = ze.getName(); //nombre del fichero contenido en el zip
	             
	             //comprobamos si los ficheros del zip ya existen y si no los descomprimimos.
	             File checkfile = new File(dirOutput + filename);
	             if(!checkfile.exists()){
	             
	            	 // Crear el directorio si no existe para cada fichero del zip.
	            	 if (ze.isDirectory()) {
	            		 File fmd = new File(dirOutput + filename);
	            		 fmd.mkdirs(); //crea el directorio.
	            		 continue;
	            	 }
	             
	            	 FileOutputStream fout = new FileOutputStream(dirOutput + filename); //Fichero final.

	             
	            	 while ((count = zis.read(buffer)) != -1) 
	            	 {
	            		 fout.write(buffer, 0, count);             
	            	 }

	            	 fout.close();               
	            	 zis.closeEntry();
	         }//end checkfile
	      }

	         zis.close();
	 		return true; 
	     } 
	     catch(IOException e)
	     {
	    	 Toast.makeText(getApplicationContext(), "No se han podido extraer los recursos", Toast.LENGTH_SHORT);
	         e.printStackTrace();
	         return false;
	     } 
		

	}

}
