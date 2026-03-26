package com.mason.mapgen.paint.skeletons;

import com.mason.mapgen.paint.logic.tools.PaintTool;

import java.util.function.Supplier;

public class PaintToolQuerySlot implements Supplier<PaintTool>{


    private Supplier<PaintTool> query = () -> {
        throw new IllegalStateException("Paint Tool Query unset");
    };
    private boolean querySet = false;


    public void setQuery(Supplier<PaintTool> query){
        if(querySet){
            throw new IllegalStateException("Query already set");
        }
        querySet = true;
        this.query = query;
    }

    @Override
    public PaintTool get(){
        return query.get();
    }

}
