package com.mason.mapgen.structures.enums;

import javax.swing.*;

public enum DialogOption{

    YES, NO, CANCEL;


    public static DialogOption fromJOptionPaneChoice(int option){
        return switch(option){
            case JOptionPane.YES_OPTION -> YES;
            case JOptionPane.NO_OPTION -> NO;
            case JOptionPane.CANCEL_OPTION -> CANCEL;
            default -> throw new IllegalStateException("Unexpected option id: " + option);
        };
    }

}
