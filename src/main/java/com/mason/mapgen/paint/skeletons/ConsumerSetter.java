package com.mason.mapgen.paint.skeletons;

import java.util.function.Consumer;

public interface ConsumerSetter<T>{


    void setConsumer(Consumer<T> consumer);


    class ConsumerSlot<T> implements ConsumerSetter<T>, Consumer<T>{

        private Consumer<T> consumer = (obj) -> {
            throw new IllegalStateException("ConsumerSlot unset");
        };
        private boolean consumerSet = false;


        @Override
        public void setConsumer(Consumer<T> consumer){
            if(consumerSet){
                throw new IllegalStateException("consumer is already set");
            }
            this.consumer = consumer;
            consumerSet = true;
        }

        @Override
        public void accept(T obj){
            consumer.accept(obj);
        }

    }

}
