package com.mason.mapgen.paint.components.panes.rightPane.referenceButtons;

import com.mason.libgui.components.deco.BasicSliderDeco;
import com.mason.libgui.components.panes.layout.PaneLayout;
import com.mason.libgui.components.sliders.Slider;
import com.mason.libgui.components.sliders.sliderPositionState.*;
import com.mason.libgui.components.sliders.sliderWithIcons.SliderWithIcons;
import com.mason.libgui.components.sliders.sliderWithIcons.SliderWithIconsBuilder;
import com.mason.libgui.components.sliders.sliderWithIcons.SliderWithIconsSkeleton;
import com.mason.libgui.components.toggles.Toggle;
import com.mason.libgui.construction.BuildComponent;
import com.mason.libgui.construction.BuildResource;
import com.mason.libgui.construction.SettableBuildResource;
import com.mason.libgui.core.component.UIComponent;
import com.mason.libgui.core.component.hitbox.HitboxRect;
import com.mason.libstruct.geo.Coord;
import com.mason.libstruct.geo.Rect;
import com.mason.libstruct.geo.Size;
import com.mason.libstruct.interfaces.Movable;
import com.mason.libstruct.interfaces.RectQuery;
import com.mason.libstruct.states.onOff.OnOffState;
import com.mason.libstruct.states.position.PositionQuery;
import com.mason.mapgen.paint.components.panes.rightPane.pane.RightPaintPaneBuildComponent;
import com.mason.mapgen.paint.components.panes.rightPane.reference.*;

import java.util.Set;

import static com.mason.mapgen.paint.components.paintGUIState.PaintGUIStateBuildCollection.PAINT_GUI_PARAMS;
import static com.mason.mapgen.paint.components.panes.rightPane.reference.ReferenceImageAccessSlotsBuildComponent.REFERENCE_IMAGE_UPDATE;

public class ReferenceTweakablesBuildComponent implements BuildComponent{


    public static final ReferenceTweakablesBuildComponent INSTANCE = new ReferenceTweakablesBuildComponent();

    public static final SettableBuildResource<OnOffState> GRAB_REFERENCE_ONLY_STATE = new SettableBuildResource<>("GRAB_REFERENCE_ONLY_STATE");
    public static final SettableBuildResource<PositionQuery> REFERENCE_ALPHA_QUERY = new SettableBuildResource<>("REFERENCE_ALPHA_QUERY");
    public static final SettableBuildResource<UIComponent[]> REFERENCE_TWEAKABLES = new SettableBuildResource<>("REFERENCE_TWEAKABLES");


    private ReferenceTweakablesBuildComponent(){}


    @Override
    public Set<BuildResource<?>> requiredPreBuildResources(){
        return Set.of(
                PAINT_GUI_PARAMS,
                RightPaintPaneBuildComponent.LAYOUT,
                RightPaintPaneBuildComponent.INSTANCE,
                REFERENCE_IMAGE_UPDATE
        );
    }

    @Override
    public Set<BuildResource<?>> suppliedBuildResources(){
        return Set.of(
                GRAB_REFERENCE_ONLY_STATE,
                REFERENCE_ALPHA_QUERY,
                REFERENCE_TWEAKABLES
        );
    }

    @Override
    public void ensureBuilt(){
        ImageUpdate imageUpdate = REFERENCE_IMAGE_UPDATE.get();
        UIComponent[] components = new UIComponent[5];
        REFERENCE_TWEAKABLES.setResource(components);

        Toggle grabReferenceOnlyToggle = new GrabReferenceOnlyToggle(getButtonBoundary("GRAB_REFERENCE_ONLY_TOGGLE"));
        GRAB_REFERENCE_ONLY_STATE.setResource(grabReferenceOnlyToggle);
        components[0] = grabReferenceOnlyToggle;

        components[1] = new ClearReferenceButton(getButtonBoundary("CLEAR_REFERENCE_BUTTON"), imageUpdate::clearImage);
        components[2] = new RecentreReferenceButton(getButtonBoundary("RECENTRE_REFERENCE_BUTTON"), imageUpdate::recentreImage);
        components[3] = new LoadReferenceButton(getButtonBoundary("LOAD_REFERENCE_BUTTON"), imageUpdate::setImage);

        Slider alphaSlider = createAlphaSlider();
        components[4] = alphaSlider;
        REFERENCE_ALPHA_QUERY.setResource(alphaSlider);
    }

    private HitboxRect getButtonBoundary(String name){
        Size size = PAINT_GUI_PARAMS.get().toggleSize();
        PaneLayout layout = RightPaintPaneBuildComponent.LAYOUT.get();
        Coord topLeft = layout.centre(name, size);
        return HitboxRect.build(topLeft, size);
    }

    private Slider createAlphaSlider(){
        PaneLayout layout = RightPaintPaneBuildComponent.LAYOUT.get();
        RectQuery boundary = layout.getBounds("REFERENCE_ALPHA_SLIDER");
        SliderWithIconsBuilder sliderBuilder = new SliderWithIconsBuilder(6, new BasicSliderDeco(), new Size(12, 24));
        SliderWithIconsSkeleton skeleton = sliderBuilder.buildSkeleton(
                boundary,
                "assets/paintIcons/transparent.png",
                "assets/paintIcons/opaque.png",
                createSliderPositionStateFactory(0.7, sliderBuilder));
        return new SliderWithIcons(skeleton);
    }

    private SliderPositionStateFactory createSliderPositionStateFactory(double initialPosition, SliderWithIconsBuilder sliderBuilder){
        return (railClamp) -> {
            RectQuery coordClamp = getCoordClamp(railClamp, sliderBuilder);
            Coord initialCoord = getInitialCoordFromCoordClamp(coordClamp, initialPosition);
            Movable movable = Movable.buildDefaultMovable(initialCoord);
            IntRange range = getIntRange(coordClamp);
            return new BasicSliderPositionState(movable, range);
        };
    }

    private RectQuery getCoordClamp(RectQuery railClamp, SliderWithIconsBuilder sliderBuilder){
        return new Rect(railClamp.x(), railClamp.y(), railClamp.width()-sliderBuilder.getSliderHandleSize().width(), railClamp.height());
    }

    private Coord getInitialCoordFromCoordClamp(RectQuery coordClamp, double initialPosition){
        int x = coordClamp.x() + (int)(coordClamp.width() * initialPosition);
        return new Coord(x, coordClamp.y());
    }

    private IntRange getIntRange(RectQuery coordClamp){
        return new IntRange(coordClamp.x(), coordClamp.x() + coordClamp.width());
    }


    @Override
    public String toString(){
        return "REFERENCE_TWEAKABLES";
    }

}
