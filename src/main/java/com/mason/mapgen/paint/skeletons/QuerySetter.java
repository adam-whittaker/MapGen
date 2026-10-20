package com.mason.mapgen.paint.skeletons;

import java.util.function.Supplier;

public interface QuerySetter<T>{


    void setQuery(Supplier<T> query);


    class QuerySlot<T> implements QuerySetter<T>, Supplier<T>{

        private Supplier<T> query = () -> {
            throw new IllegalStateException("QuerySlot unset");
        };
        private boolean querySet = false;


        @Override
        public void setQuery(Supplier<T> query){
            if(querySet){
                throw new IllegalStateException("consumer is already set");
            }
            this.query = query;
            querySet = true;
        }

        @Override
        public T get(){
            return query.get();
        }

    }

}
