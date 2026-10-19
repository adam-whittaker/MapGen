package com.mason.mapgen.paint.skeletons;

import com.mason.mapgen.paint.logic.tools.PaintTool;

public interface PaintToolQuery{


    PaintTool getPaintTool();


    interface PaintToolQuerySetter{
        void setPaintToolQuery(PaintToolQuery query);
    }


    class PaintToolQuerySlot implements PaintToolQuerySetter, PaintToolQuery{

        private PaintToolQuery query = () -> {
            throw new IllegalStateException("Paint Tool Query unset");
        };
        private boolean querySet = false;

        @Override
        public void setPaintToolQuery(PaintToolQuery query){
            if(querySet){
                throw new IllegalStateException("Query already set");
            }
            querySet = true;
            this.query = query;
        }

        @Override
        public PaintTool getPaintTool(){
            return query.getPaintTool();
        }

    }

}
