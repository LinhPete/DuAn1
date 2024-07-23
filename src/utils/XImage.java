/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.awt.Image;
import java.io.File;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 *
 * @author ndhlt
 */
public class XImage {
    public static Image getAppIcon(){
        URL url = XImage.class.getResource("/com/edusys/icon/fpt.png");
        return new ImageIcon(url).getImage();
    }
    
    public static void save(File src){
        File dst = new File("img",src.getName());
        if(!dst.getParentFile().exists()){
            dst.getParentFile().mkdirs();
        }
        XFile.copyPaste(src, dst.getAbsolutePath());
    }
    
    public static ImageIcon read(String fileName){
        File path = new File("img", fileName);
        return new ImageIcon(path.getAbsolutePath());
    }
    
    public static ImageIcon getResized(ImageIcon img, int WIDTH, int HEIGHT){
        Image resize = img.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
        return new ImageIcon(resize);
    }
}
