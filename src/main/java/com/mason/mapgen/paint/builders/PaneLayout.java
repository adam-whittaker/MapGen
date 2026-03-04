package com.mason.mapgen.paint.builders;

import com.mason.libgui.utils.structures.*;

import java.util.*;

public class PaneLayout{


    private final Box rootBox;
    private final Map<String, Box> namedBoxes;


    public PaneLayout(RectQuery layout){
        rootBox = new Box(layout);
        namedBoxes = new HashMap<>();
    }


    public Coord centre(String address, Size size){
        Box box = findBox(address);
        box.verifyEmpty();
        return box.centreInBox(size);
    }

    private Box findBox(String address){
        if(isNamedAddress(address)){
            return findBoxFromNamedAddress(address);
        }
        if(startsWithNamedAddress(address)){
            return findBoxFromAddressWithNamedStart(address);
        }
        return findBoxFromUnnamedAddress(address, rootBox);
    }

    private boolean isNamedAddress(String address){
        return isRootAddress(address) || namedBoxes.containsKey(address);
    }

    private boolean isRootAddress(String address){
        return address.isEmpty() || address.equalsIgnoreCase("ROOT") || address.equals("*");
    }

    private Box findBoxFromNamedAddress(String address){
        if(isRootAddress(address)){
            return rootBox;
        }
        return namedBoxes.get(address);
    }

    private boolean startsWithNamedAddress(String address){
        if(!address.contains("-")){
            return false;
        }
        String firstSubAddress = getFirstSubAddress(address);
        return isNamedAddress(firstSubAddress);
    }

    private String getFirstSubAddress(String address){
        return address.substring(0, address.indexOf('-'));
    }

    private String getAddressTail(String address){
        return address.substring(address.indexOf('-')+1);
    }


    private Box findBoxFromAddressWithNamedStart(String address){
        String namedStart = getFirstSubAddress(address);
        String addressTail = getAddressTail(address);
        Box startBox = findBoxFromNamedAddress(namedStart);
        return findBoxFromUnnamedAddress(addressTail, startBox);
    }

    private Box findBoxFromUnnamedAddress(String address, Box initialBox){
        String[] subAddresses = address.split("-");
        Box currentBox = initialBox;
        for(String subAddress : subAddresses){
            currentBox = currentBox.getSubBoxAtAddress(subAddress);
        }
        return currentBox;
    }

    public void nameAddress(String address, String name){
        verifyNameSafe(name);
        namedBoxes.put(name, findBox(address));
    }

    private void verifyNameSafe(String name){
        if(name.contains("-")){
            throw new IllegalArgumentException("Given box name: " + name + " not allowed");
        }
        if(namedBoxes.containsKey(name)){
            throw new IllegalArgumentException("Box with name: " + name + " already exists");
        }
    }


    public void divide(String address, int rows, int columns){
        divideBox(findBox(address), rows, columns);
    }

    private void divideBox(Box box, int rows, int columns){
        box.verifyEmpty();
        int x = box.bounds.x();
        int y = box.bounds.y();
        int w = box.bounds.width()/columns;
        int h = box.bounds.height()/rows;
        for(int row=0; row<rows; row++){
            for(int col=0; col<columns; col++){
                box.putSubBox(coordToSubAddress(row, col), new Rect(x+col*w, y+row*h, w, h));
            }
        }
    }

    private String coordToSubAddress(int row, int col){
        return "["+ row + "," + col + "]";
    }


    public void verticalDissect(String address, double proportion){
        Box box = findBox(address);
        box.verifyEmpty();
        int x = box.bounds.x();
        int y = box.bounds.y();
        int w = box.bounds.width();
        int h = box.bounds.height();
        int h1 = (int)(h * proportion);
        int h2 = h - h1;
        box.putSubBox(coordToSubAddress(0, 0), new Rect(x, y, w, h1));
        box.putSubBox(coordToSubAddress(1, 0), new Rect(x, y+h1, w, h2));
    }

    public void horizontalDissect(String address, double proportion){
        Box box = findBox(address);
        box.verifyEmpty();
        int x = box.bounds.x();
        int y = box.bounds.y();
        int w = box.bounds.width();
        int h = box.bounds.height();
        int w1 = (int)(w * proportion);
        int w2 = w - w1;
        box.putSubBox(coordToSubAddress(0, 0), new Rect(x, y, w1, h));
        box.putSubBox(coordToSubAddress(0, 1), new Rect(x+w1, y, w2, h));
    }

    private static final class Box{


        private final RectQuery bounds;
        private final Map<String, Box> subBoxes;


        Box(RectQuery bounds){
            this.bounds = bounds;
            subBoxes = new HashMap<>();
        }


        void verifyEmpty(){
            if(!subBoxes.isEmpty()){
                throw new IllegalStateException("Box must be empty!");
            }
        }

        void putSubBox(String address, RectQuery bounds){
            if(subBoxes.containsKey(address)){
                throw new IllegalStateException("Address: " + address + " already exists!");
            }
            subBoxes.put(address, new Box(bounds));
        }

        Box getSubBoxAtAddress(String address){
            if(!subBoxes.containsKey(address)){
                throw new IllegalArgumentException("Box does not contain address: " + address);
            }
            return subBoxes.get(address);
        }

        Coord centreInBox(Size size){
            verifyFit(size);
            int x = bounds.x() + (bounds.width() - size.width())/2;
            int y = bounds.y() + (bounds.height() - size.height())/2;
            return new Coord(x, y);
        }

        void verifyFit(Size size){
            if(!bounds.getSize().fits(size)){
                throw new IllegalArgumentException("Given size: " + size + " does not fit in box of size " + bounds.getSize());
            }
        }

    }

}
