package pfc.mundus4d.complutum;

import com.google.android.vending.expansion.downloader.DownloaderClientMarshaller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;


/** Clase encargada de monitorizar el estado de la descarga y reiniciarla en caso de que sea necesario. */ 
public class Monitorizar_Descarga extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            DownloaderClientMarshaller.startDownloadServiceIfRequired(context, intent, Asist_Descarga.class);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }       
    }

}
