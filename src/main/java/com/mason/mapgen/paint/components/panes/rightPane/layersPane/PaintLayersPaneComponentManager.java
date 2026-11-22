package com.mason.mapgen.paint.components.panes.rightPane.layersPane;

import com.mason.libgui.components.behaviour.camera.ViewportBehaviour;
import com.mason.libgui.components.behaviour.camera.ViewportInputDistributor;
import com.mason.libgui.core.componentManagement.IterableUIComponentContainer;
import com.mason.libgui.core.componentManagement.UIComponentManager;
import com.mason.libgui.core.componentManagement.sourced.SourcedGUIInputDistributor;
import com.mason.libgui.core.componentManagement.sourced.SourcedInteractiveContainer;
import com.mason.libgui.core.input.componentLayer.GUIInputDistributor;
import com.mason.libgui.core.input.mouse.BoundedMouseInputListener;
import com.mason.libstruct.geo.Coord;
import com.mason.libstruct.setters.FunctionSetter;
import com.mason.libstruct.states.intState.IntQuery;
import com.mason.libstruct.states.intState.IntState;
import com.mason.mapgen.paint.components.panes.rightPane.layersPane.layer.Layer;
import com.mason.mapgen.paint.components.panes.rightPane.layersPane.layer.LayerManager;
import com.mason.mapgen.paint.components.panes.rightPane.layersPane.singleLayerPane.SingleLayerPane;

import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Supplier;

public class PaintLayersPaneComponentManager extends UIComponentManager{


    public static final GUIInputDistributor<BoundedMouseInputListener> MOUSE_CAPTURE_IGNORER = new GUIInputDistributor<>(){
        @Override
        public void addKeyListener(KeyListener listener){}
        @Override
        public void removeKeyListener(KeyListener listener){}
        @Override
        public void addMouseInputListener(BoundedMouseInputListener listener){}
        @Override
        public void removeMouseInputListener(BoundedMouseInputListener listener){}
    };

    private final Supplier<LayerManager> layerManagerQuery;
    private final SingleLayerPane.SizeParams singleLayerPaneSizeParams;
    private final IntState currentLayerState;


    private PaintLayersPaneComponentManager(
            Supplier<LayerManager> layerManagerQuery,
            SingleLayerPane.SizeParams singleLayerPaneSizeParams,
            IntState currentLayerState,
            GUIInputDistributor<BoundedMouseInputListener> inputDistributor,
            FunctionSetter<Layer, SingleLayerPane> paneFunctionSetter,
            IterableUIComponentContainer components
    ){
        super(components, inputDistributor);
        this.layerManagerQuery = layerManagerQuery;
        this.singleLayerPaneSizeParams = singleLayerPaneSizeParams;
        this.currentLayerState = currentLayerState;
        paneFunctionSetter.setFunction(this::buildSingleLayerPane);
    }

    public static PaintLayersPaneComponentManager build(
            Supplier<LayerManager> layerManagerQuery,
            SingleLayerPane.SizeParams singleLayerPaneSizeParams,
            IntState currentLayerState,
            ViewportBehaviour viewportBehaviour
    ){
        SingleLayerPaneIterable paneIterable = new SingleLayerPaneIterable(layerManagerQuery);
        FunctionSetter<Layer, SingleLayerPane> paneFactorySetter = paneIterable.getPaneFactorySetter();
        MouseListenerIterable mouseListenerIterable = new MouseListenerIterable(paneIterable);
        SourcedInteractiveContainer container = new SourcedInteractiveContainer(paneIterable);
        GUIInputDistributor<BoundedMouseInputListener> wrapperDistributor = new ViewportInputDistributor(viewportBehaviour);
        BoundedSourcedDistributor sourcedDistributor = new BoundedSourcedDistributor(mouseListenerIterable);
        sourcedDistributor.setInputSource(wrapperDistributor);
        return new PaintLayersPaneComponentManager(
                layerManagerQuery,
                singleLayerPaneSizeParams,
                currentLayerState,
                wrapperDistributor,
                paneFactorySetter,
                container
        );
    }

    private SingleLayerPane buildSingleLayerPane(Layer layer){
        SingleLayerPane pane = SingleLayerPane.buildSingleLayerPane(
                singleLayerPaneSizeParams,
                layer,
                buildLayerPositionQuery(layer),
                currentLayerState
        );
        pane.setInputSource(MOUSE_CAPTURE_IGNORER);
        return pane;
    }

    private IntQuery buildLayerPositionQuery(Layer layer){
        return () -> {
            LayerManager manager = layerManagerQuery.get();
            return manager.getLayerPosition(layer);
        };
    }


    private static class SingleLayerPaneIterable implements Iterable<SingleLayerPane>{


        private final Supplier<LayerManager> layerManagerQuery;
        private final Map<Layer, SingleLayerPane> paneMap;
        private final FunctionSetter.FunctionSlot<Layer, SingleLayerPane> paneFactorySlot = new FunctionSetter.FunctionSlot<>();


        private SingleLayerPaneIterable(Supplier<LayerManager> layerManagerQuery){
            this.layerManagerQuery = layerManagerQuery;
            this.paneMap = new HashMap<>();
        }


        @Override
        public Iterator<SingleLayerPane> iterator(){
            Iterator<Layer> layerIter = layerManagerQuery.get().iterator();
            return new Iterator<>(){

                @Override
                public boolean hasNext(){
                    return layerIter.hasNext();
                }

                @Override
                public SingleLayerPane next(){
                    Layer layer = layerIter.next();
                    if(paneMap.containsKey(layer)){
                        return paneMap.get(layer);
                    }
                    SingleLayerPane pane = paneFactorySlot.apply(layer);
                    paneMap.put(layer, pane);
                    return pane;
                }
            };
        }


        FunctionSetter<Layer, SingleLayerPane> getPaneFactorySetter(){
            return paneFactorySlot;
        }

    }

    private record MouseListenerIterable(Iterable<SingleLayerPane> paneIterable) implements Iterable<BoundedMouseInputListener>{

        @Override
        public Iterator<BoundedMouseInputListener> iterator(){
            Iterator<SingleLayerPane> paneIter = paneIterable.iterator();
            return new Iterator<>(){

                @Override
                public boolean hasNext(){
                    return paneIter.hasNext();
                }

                @Override
                public BoundedMouseInputListener next(){
                    SingleLayerPane pane = paneIter.next();
                    return pane.getInputTranslator();
                }
            };
        }

    }

    private static class BoundedSourcedDistributor extends SourcedGUIInputDistributor implements BoundedMouseInputListener{

        public BoundedSourcedDistributor(Iterable<? extends BoundedMouseInputListener> source){
            super(source);
        }

        @Override
        public boolean withinBounds(Coord coord){
            return true;
        }

        @Override
        public void addKeyListener(KeyListener listener){}

        @Override
        public void removeKeyListener(KeyListener listener){}

        @Override
        public void addMouseInputListener(BoundedMouseInputListener listener){
            super.addMouseInputListener(listener);
        }

    }

}
