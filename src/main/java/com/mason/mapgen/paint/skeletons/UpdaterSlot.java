package com.mason.mapgen.paint.skeletons;

public class UpdaterSlot implements Runnable{


    private Runnable updater = () -> {
        throw new IllegalStateException("Update slot unset");
    };
    private boolean updaterSet = false;


    public void setUpdate(Runnable updater){
        if(updaterSet){
            throw new IllegalStateException("updater is already set");
        }
        this.updater = updater;
        updaterSet = true;
    }

    @Override
    public void run(){
        updater.run();
    }

}
