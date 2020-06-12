/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myzoo.rssuploader;

import com.dropbox.core.DbxException;
import java.io.IOException;
import org.apache.commons.configuration2.ex.ConfigurationException;

/**
 *
 * @author Fabio-PC
 */
public class Main {
    
    public static String apiKey, xmlPath;
    private static JFrameMain jFrameMain;

    public static void main(String[] args) throws ConfigurationException, DbxException, IOException {
        
        jFrameMain = new JFrameMain();
        jFrameMain.requestFocusInWindow();
        jFrameMain.setVisible(true);
        jFrameMain.toFront();
        
    }
    
}
