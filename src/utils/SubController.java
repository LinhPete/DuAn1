/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import javax.swing.JFrame;

/**
 *
 * @author ndhlt
 */
public abstract class SubController extends JFrame {
    protected JFrame returnWindow;
    public void setReturnWindow(JFrame mainWindow) {
        if(mainWindow!=null){
            this.returnWindow = mainWindow;
        }
    }
}
