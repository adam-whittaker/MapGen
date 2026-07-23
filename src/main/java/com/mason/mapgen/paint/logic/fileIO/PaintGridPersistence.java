package com.mason.mapgen.paint.logic.fileIO;

import com.mason.libvoronoi.algorithms.components.ChunkingGrid;
import com.mason.libvoronoi.io.CentroidDataReader;
import com.mason.libvoronoi.io.ChunkingGridPersistence;
import com.mason.mapgen.paint.components.misc.PaintCentroidData;
import com.mason.mapgen.structures.enums.DialogOption;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.io.*;

public class PaintGridPersistence{

    public static void writeToFile(File file, ChunkingGrid<PaintCentroidData> grid) throws IOException{
        ChunkingGridPersistence.writeToFile(file, grid);
    }

    public static void tryWriteToFile(File file, ChunkingGrid<PaintCentroidData> grid){
        try{
            PaintGridPersistence.writeToFile(file, grid);
        }catch(IOException e){
            e.printStackTrace(System.err);
        }
    }

    public static ChunkingGrid<PaintCentroidData> readFromFile(File file) throws IOException{
        CentroidDataReader<PaintCentroidData> centroidReader = PaintCentroidData::readFromDataStream;
        return ChunkingGridPersistence.readFromFile(file, centroidReader);
    }

    public static ChunkingGrid<PaintCentroidData> tryReadFromFile(File file){
        try{
            return readFromFile(file);
        }catch(IOException e){
            e.printStackTrace(System.err);
            return null;
        }
    }

    public static void trySaveAsPNG(BufferedImage image, File file){
        try{
            ImageIO.write(image, "png", file);
        }catch(IOException e){
            e.printStackTrace(System.err);
        }
    }


    public static DialogOption shouldSaveFirst(){
        int confirm = JOptionPane.showConfirmDialog(
                null,
                "Do you want to save first?",
                "Save",
                JOptionPane.YES_NO_CANCEL_OPTION
        );
        return DialogOption.fromJOptionPaneChoice(confirm);
    }

    public static File chooseLoadFile(){
        JFileChooser fileChooser = new JFileChooser(new File("saves"));
        fileChooser.setDialogTitle("Select a .paintchunks file to load");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Paint chunks (*.paintchunks)", "paintchunks"));

        int result = fileChooser.showOpenDialog(null);
        if(result == JFileChooser.APPROVE_OPTION){
            return fileChooser.getSelectedFile();
        }
        return null;
    }

    public static File chooseSaveFile(){
        JFileChooser fileChooser = new JFileChooser(new File("saves"));
        fileChooser.setDialogTitle("Save As...");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Paint chunks (*.paintchunks)", "paintchunks"));

        return ensureExtensionAndPotentialOverwrites(fileChooser, "paintchunks");
    }

    public static File chooseExportFile(){
        JFileChooser fileChooser = new JFileChooser(new File("exports"));
        fileChooser.setDialogTitle("Export As...");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image (*.png)", "png"));

        return ensureExtensionAndPotentialOverwrites(fileChooser, "png");
    }


    private static File ensureExtensionAndPotentialOverwrites(JFileChooser fileChooser, String extension){
        int result = fileChooser.showSaveDialog(null);
        if(result == JFileChooser.APPROVE_OPTION){
            File file = getFileAndEnsureExtension(fileChooser, extension);
            if(!confirmPotentialOverwrite(file)){
                return null;
            }
            return file;
        }
        return null;
    }

    private static boolean confirmPotentialOverwrite(File file){
        if(file.exists()){
            int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "File already exists. Overwrite?",
                    "Confirm Save",
                    JOptionPane.YES_NO_OPTION
            );
            return confirm == JOptionPane.YES_OPTION;
        }
        return true;
    }

    private static File getFileAndEnsureExtension(JFileChooser fileChooser, String extension){
        extension = "." + extension;
        File file = fileChooser.getSelectedFile();
        if(!file.getName().toLowerCase().endsWith(extension)){
            file = new File(file.getParentFile(), file.getName() + extension);
        }
        return file;
    }

}
