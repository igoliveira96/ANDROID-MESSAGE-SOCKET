package goulart.message.rede;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static android.content.Context.WIFI_SERVICE;

/**
 * Created by Igor on 14/10/2017.
 */

public class PlacaRede {

    /**
     * Adicione a seguinte permissão:
     * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
     *
     * Esta permisão nos dará acesso as configurações da placa de rede - WIFI
     */

    public static String meuIP(Context context){
        /**
         * Este método acessa as informações do WIFI e coleta o IP
         */
        WifiManager wifiMgr = (WifiManager) context.getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        return Formatter.formatIpAddress(ip);
    }

    public static InetAddress enderecoBroadcast() {
        /**
         * Este método irá retornar o endereço de broadcast
         */
        try {
            return InetAddress.getByName("255.255.255.255");
        } catch (UnknownHostException e) {
            return enderecoBroadcast();
        }
    }

}