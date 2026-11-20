package com.mason.mapgen.paint.components.panes.rightPane.layersPane.layer;

import com.mason.mapgen.paint.components.misc.PaintGrid;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;

public class LayerManager implements Iterable<Layer>{


    private final LayerOrder idOrders;
    private final Map<Integer, Layer> layerCache;
    private final Function<Integer, Layer> layerFactory;


    public LayerManager(PaintGrid grid){
        this(new LayerOrder(), new HashMap<>(), grid);
        createLayer();
    }

    private LayerManager(
            LayerOrder idOrders,
            Map<Integer, Layer> layerCache,
            PaintGrid grid
    ){
        this.idOrders = idOrders;
        this.layerCache = layerCache;
        this.layerFactory = (layerID) -> new Layer(grid, layerID);
    }


    public void createLayer(){
        int id = idOrders.reserveID();
        Layer newLayer = layerFactory.apply(id);
        layerCache.put(id, newLayer);
        idOrders.publishID(id);
    }

    public Layer getLayer(int layerID){
        if(!layerCache.containsKey(layerID)){
            throw new IllegalArgumentException("No such layerID: " + layerID);
        }
        return layerCache.get(layerID);
    }

    public int getLayerPosition(Layer layer){
        int id = layer.getLayerID();
        return idOrders.getPositionFromID(id);
    }

    public int numLayers(){
        return idOrders.size();
    }

    @Override
    public Iterator<Layer> iterator(){
        List<Layer> snapshot = new ArrayList<>();
        for(int id : idOrders.idsSnapshot()){
            Layer layer = layerCache.get(id);
            if(layer != null) snapshot.add(layer);
        }
        return snapshot.iterator();
    }


    public void writeToDataStream(DataOutputStream out) throws IOException{
        idOrders.writeToDataStream(out);
        for(int id : idOrders){
            Layer layer = layerCache.get(id);
            layer.writeToDataStream(out);
        }
    }


    public static LayerManager readFromDataStream(DataInputStream in, PaintGrid grid) throws IOException{
        LayerOrder idOrders = LayerOrder.readFromDataStream(in);
        Map<Integer, Layer> layerCache = new HashMap<>();
        for(int id : idOrders){
            Layer layer = Layer.readFromDataStream(in, id, grid);
            layerCache.put(id, layer);
        }
        return new LayerManager(idOrders, layerCache, grid);
    }

}
