package com.mason.mapgen.paint.components.panes.rightPane.layersPane.layer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class LayerOrder implements Iterable<Integer>{


    private final Object lock;
    private final List<Integer> idOrders;
    private final Map<Integer, Integer> idPositions;
    private int nextID;


    private LayerOrder(List<Integer> idOrders, int nextID){
        this.lock = new Object();
        this.idOrders = idOrders;
        this.nextID = nextID;
        this.idPositions = new ConcurrentHashMap<>();
        for(int n=0; n<idOrders.size(); n++){
            idPositions.put(idOrders.get(n), n);
        }
    }

    LayerOrder(){
        this(new ArrayList<>(), 0);
    }


    @Override
    public Iterator<Integer> iterator(){
        return idsSnapshot().iterator();
    }

    List<Integer> idsSnapshot(){
        synchronized(lock){
            return new ArrayList<>(idOrders);
        }
    }

    int size(){
        synchronized(lock){
            return idOrders.size();
        }
    }

    int reserveID(){
        synchronized(lock){
            return nextID++;
        }
    }

    void publishID(int id){
        synchronized(lock){
            idPositions.put(id, idOrders.size());
            idOrders.add(id);
        }
    }

    void deleteID(int id){
        synchronized(lock){
            int position = idPositions.remove(id);
            idOrders.remove(position);
            reindexFrom(position, idOrders.size() - 1);
        }
    }

    private void reindexFrom(int start, int end){
        synchronized(lock){
            for(int i=start; i<=end; i++){
                idPositions.put(idOrders.get(i), i);
            }
        }
    }

    int getPositionFromID(int id){
        synchronized(lock){
            return idPositions.get(id);
        }
    }

    int moveIDToPosition(int id, int newPosition){
        synchronized(lock){
            int oldPosition = idPositions.get(id);
            if(oldPosition == newPosition){
                return oldPosition;
            }

            idOrders.remove(oldPosition);
            idOrders.add(newPosition, id);

            int start = Math.min(oldPosition, newPosition);
            int end = Math.max(oldPosition, newPosition);

            reindexFrom(start, end);

            return oldPosition;
        }
    }


    public void writeToDataStream(DataOutputStream out) throws IOException{
        List<Integer> ids;
        int next;
        synchronized(lock){
            ids = new ArrayList<>(idOrders);
            next = nextID;
        }
        out.writeInt(next);
        out.writeInt(ids.size());
        for(int id : ids){
            out.writeInt(id);
        }
    }

    public static LayerOrder readFromDataStream(DataInputStream in) throws IOException{
        int nextID = in.readInt();
        int size = in.readInt();
        List<Integer> idOrders = new ArrayList<>();
        for(int n=0; n<size; n++){
            idOrders.add(in.readInt());
        }
        return new LayerOrder(idOrders, nextID);
    }

}
