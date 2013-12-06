package de.hscoburg.etif.vbis.lagerixjavaclient;

import de.hscoburg.etif.vbis.lagerix.appclient.windows.MainWindow;
import java.io.File;
import java.io.PrintWriter;


/**
 * Enterprise Application Client main class.
 *
 */
public class Main {
    
    public static String authConfig = "userMgmtJdbcRealm {\n" +
            "	com.sun.enterprise.security.auth.login.ClientPasswordLoginModule required debug=false; \n" +
            "};\n" +
            "\n" +
            "default {\n" +
            "	com.sun.enterprise.security.auth.login.ClientPasswordLoginModule required debug=false; \n" +
            "};";
    
    public static String serverConfig = "localhost:3700";
    
    public static void main( String[] args ) {
        File f = new File("auth.conf");
        if(!f.exists()) 
        {
            try
            {
                PrintWriter writer = new PrintWriter("auth.conf", "UTF-8");
                writer.print(authConfig);
                writer.close();
            } catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        
        f = new File("server.conf");
        if(!f.exists()) 
        {
            try
            {
                PrintWriter writer = new PrintWriter("server.conf", "UTF-8");
                writer.print(serverConfig);
                writer.close();
            } catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                System.out.println( "window" );
                new MainWindow().setVisible(true);
                System.out.println( "window opened" );
            }
        });
    }
}