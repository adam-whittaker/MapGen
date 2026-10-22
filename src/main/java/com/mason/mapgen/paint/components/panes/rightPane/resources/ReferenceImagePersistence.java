package com.mason.mapgen.paint.components.panes.rightPane.resources;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class ReferenceImagePersistence{


    public static File chooseLoadFile(){
        JFileChooser fileChooser = new JFileChooser(new File("references"));
        fileChooser.setDialogTitle("Select an image file to load");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image (*.png, *.jpg)", "png", "jpg"));

        int result = fileChooser.showOpenDialog(null);
        if(result == JFileChooser.APPROVE_OPTION){
            return fileChooser.getSelectedFile();
        }
        return null;
    }

    public static void imageReadFailedDialogue(){
        JOptionPane.showMessageDialog(
                null,
                "Image could not be read",
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

}
