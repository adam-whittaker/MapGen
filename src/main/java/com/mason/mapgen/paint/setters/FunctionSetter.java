package com.mason.mapgen.paint.setters;

import java.util.function.Function;

public interface FunctionSetter<K, V>{


    void setFunction(Function<K, V> function);


    class FunctionSlot<K, V> implements Function<K, V>, FunctionSetter<K, V>{

        private Function<K, V> function = (obj) -> {
            throw new IllegalStateException("FunctionSlot unset");
        };
        private boolean functionSet = false;


        public void setFunction(Function<K, V> function){
            if(functionSet){
                throw new IllegalStateException("Function is already set");
            }
            this.function = function;
            functionSet = true;
        }

        @Override
        public V apply(K obj){
            return function.apply(obj);
        }

    }

}
