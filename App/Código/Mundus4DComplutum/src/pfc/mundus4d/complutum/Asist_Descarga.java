package pfc.mundus4d.complutum;

import com.google.android.vending.expansion.downloader.impl.DownloaderService;

/* Clase que se encarga de descargar los archivos adicionales de la aplicación desde Google Play*/
public class Asist_Descarga extends DownloaderService {
	
    // You must use the public key belonging to your publisher account
    public static final String BASE64_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtRc4syHeyz8C+iDK600EqI9Cl5/QzYUZfZ9w7rG7Z/Hjfq1B3vD2gaZbiEH8/52H8dGdzdsR/RKUpREU1q9UTn9K+PNllrVCHI3D9/RzSZHo3Uis9QEMlyQivk091LLCeGFbAujP+jgwAs4SpO2pWpRMCGnm4EZk5nE9Cp0yrHvGDdo3jvpso1pHEtDobvQ4T1Dpo9uswkp0Ur5cXUCDCwABxlAca6j9pgauW0YzVLOMABC8CXhxTXr+aXm8+53toPFvj3kQr7PDt3EdBuZTVJ0pZIJdAz48MfS5+TA1dG3Om8NTyi/VcptEbVuqgGGpFVGrbs0rE2cgkSBA4VYw9wIDAQAB";
    // You should also modify this salt
    public static final byte[] SALT = new byte[] { 6, 75, 20, -128, 95, -96,
            98, -98, 22, 1, -8, -69, 127, -2, 10, -121, 101, 89, -12, 67
    };
	@Override
	public String getPublicKey() {
		// TODO Auto-generated method stub
		return BASE64_PUBLIC_KEY;
	}

	@Override
	public byte[] getSALT() {
		// TODO Auto-generated method stub
		return SALT;
	}

	@Override
	public String getAlarmReceiverClassName() {
		// TODO Auto-generated method stub
		return Monitorizar_Descarga.class.getName();
	}
	

}
