package com.myzoo.rssuploader;

import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuth;
import com.dropbox.core.TokenAccessType;

import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class ShortLiveTokenAuthorize {
    public DbxAuthFinish authorize(DbxAppInfo appInfo, JFrame frame) throws IOException {
        // Run through Dropbox API authorization process
        DbxRequestConfig requestConfig = new DbxRequestConfig("examples-authorize");
        DbxWebAuth webAuth = new DbxWebAuth(requestConfig, appInfo);

        // TokenAccessType.OFFLINE means refresh_token + access_token. ONLINE means access_token only.
        DbxWebAuth.Request webAuthRequest =  DbxWebAuth.newRequestBuilder()
            .withNoRedirect()
            .withTokenAccessType(TokenAccessType.OFFLINE)
            .build();

        String authorizeUrl = webAuth.authorize(webAuthRequest);
        System.out.println("1. Go to " + authorizeUrl);
        System.out.println("2. Click \"Allow\" (you might have to log in first).");
        System.out.println("3. Copy the authorization code.");
        System.out.print("Enter the authorization code here: ");
        
        JTextArea ta = new JTextArea(10, 100);
        ta.setText("1. Vai all'indirizzo \n\n" + authorizeUrl + "\n\n" +
                    "2. Clicca \"Autorizza\" (potresti doverti loggare prima).\n" +
                    "3. Copia qui sotto il codice di autorizzazione.");
        ta.setWrapStyleWord(true);
        ta.setLineWrap(true);
        ta.setCaretPosition(0);
        ta.setEditable(false);
        
        String s = (String)JOptionPane.showInputDialog(
                    frame,
                    ta,
                    "Richiesta token",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    null);

        //If a string was returned, say so.
        if ((s != null) && (s.length() > 0)) {
            try {
                return webAuth.finishFromCode(s);
            } catch (DbxException ex) {
                System.err.println("Error in DbxWebAuth.authorize: " + ex.getMessage());
                System.exit(1); 
                return null;
            }
        } else {
            System.exit(1); 
        }
        
        return null;
        
    }
}
