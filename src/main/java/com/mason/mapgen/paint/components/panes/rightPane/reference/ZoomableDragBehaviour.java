package com.mason.mapgen.paint.components.panes.rightPane.reference;

import com.mason.libgui.components.behaviour.camera.Zoom;
import com.mason.libgui.components.behaviour.drag.StandardDragBehaviour;
import com.mason.libgui.core.component.hitbox.HitboxRect;
import com.mason.libgui.core.input.mouse.MouseInputEvent;
import com.mason.libstruct.geo.Coord;

import java.util.function.Consumer;

public class ZoomableDragBehaviour extends StandardDragBehaviour{


    private final Zoom zoom = Zoom.buildZoom(1/8D, 32D, 24, 1);
    private final HitboxRect dragRegion;
    private final Consumer<MouseInputEvent> onClickEvent;


    public ZoomableDragBehaviour(HitboxRect dragRegion, Consumer<MouseInputEvent> onClickEvent){
        super(dragRegion);
        this.dragRegion = dragRegion;
        this.onClickEvent = onClickEvent;
    }


    @Override
    public void onMouseWheel(MouseInputEvent e){
        double oldZoom = getZoom();
        zoom.mouseWheel(e);

        Coord mouse = e.getCoord();

        double currentX = dragRegion.x();
        double currentY = dragRegion.y();

        double newX = mouse.x() - (mouse.x() - currentX) * (getZoom() / oldZoom);
        double newY = mouse.y() - (mouse.y() - currentY) * (getZoom() / oldZoom);

        dragRegion.setCoord(new Coord((int) newX, (int) newY));
    }

    @Override
    public void onMouseClicked(MouseInputEvent e){
        onClickEvent.accept(e);
    }

    public void resetZoom(){
        zoom.setZoom(1);
    }

    public double getZoom(){
        return zoom.getZoom();
    }


}
