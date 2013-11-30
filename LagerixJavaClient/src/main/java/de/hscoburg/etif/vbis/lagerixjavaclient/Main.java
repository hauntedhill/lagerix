package de.hscoburg.etif.vbis.lagerixjavaclient;

import de.hscoburg.etif.vbis.lagerix.appclient.windows.MainWindow;

/**
 * Enterprise Application Client main class.
 *
 */
public class Main {
    
    public static void main( String[] args ) {
        System.out.println( "Hello World Enterprise Application Client!" );
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                System.out.println( "window" );
                new MainWindow().setVisible(true);
                System.out.println( "window opened" );
            }
        });
    }
}
