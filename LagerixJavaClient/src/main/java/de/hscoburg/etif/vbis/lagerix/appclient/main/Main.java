package de.hscoburg.etif.vbis.lagerix.appclient.main;

import de.hscoburg.etif.vbis.lagerix.appclient.windows.JFrameJavaAppClientMainWindow;
import java.io.File;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Enterprise Application Client main class.
 *
 */
public class Main {
    private static String prefixPath = null;
    
    public static String authConfig = "userMgmtJdbcRealm {\n" +
            "	com.sun.enterprise.security.auth.login.ClientPasswordLoginModule required debug=false; \n" +
            "};\n" +
            "\n" +
            "default {\n" +
            "	com.sun.enterprise.security.auth.login.ClientPasswordLoginModule required debug=false; \n" +
            "};";
    
    public static String serverConfig = "localhost:3700";
    
    public static void main( String[] args ) {
        File fJar = null;
        
        try {
            fJar = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch (URISyntaxException ex) {
        }
        
        if(fJar != null)
        {
            prefixPath = fJar.getParentFile().getPath() + File.separator;
        }
               
        File f = new File(prefixPath + "auth.conf");
        if(!f.exists()) 
        {
            try
            {
                PrintWriter writer = new PrintWriter(prefixPath +"auth.conf", "UTF-8");
                writer.print(authConfig);
                writer.close();
            } catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        
        f = new File(prefixPath + "server.conf");
        if(!f.exists()) 
        {
            try
            {
                PrintWriter writer = new PrintWriter(prefixPath + "server.conf", "UTF-8");
                writer.print(serverConfig);
                writer.close();
            } catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFrameJavaAppClientMainWindow(prefixPath).setVisible(true);
            }
        });
    }
}