package com.mason.mapgen.paint.logic.tools.brush.settings;

import com.mason.libgui.utils.structures.states.intState.IntQuery;
import com.mason.libgui.utils.structures.states.onOff.OnOffQuery;
import com.mason.libgui.utils.structures.states.position.PositionQuery;
import com.mason.mapgen.core.random.RandomSource;
import com.mason.mapgen.paint.logic.tools.brush.settings.colorState.RGBQuery;
import com.mason.mapgen.paint.components.panes.leftPane.brushSettingsModel.BrushSettingsSkeleton;
import org.apache.commons.math3.distribution.BetaDistribution;

import java.awt.*;

import static com.mason.mapgen.core.Utils.lerp;

public class ColorMixer{


    private final RGBQuery primary;
    private final RGBQuery secondary;

    private final PositionQuery centre;
    private final PositionQuery certainty;
    private final OnOffQuery channelsIndependence;
    private final IntQuery alpha;

    private BetaDistribution distribution;


    public ColorMixer(BrushSettingsSkeleton skeleton){
        this.primary = skeleton.getPrimaryColorState();
        this.secondary = skeleton.getSecondaryColorState();
        this.centre = skeleton.getCentrePositionState();
        this.certainty = skeleton.getCertaintyPositionState();
        this.channelsIndependence = skeleton.getChannelIndependenceState();
        this.alpha = skeleton.getAlphaState();
        skeleton.setColorMixerUpdate(this::recalculateDistribution);
        recalculateDistribution();
    }

    private void recalculateDistribution(){
        double schedule = calcSchedule(certainty.getPosition());
        double alpha = 1 + centre.getPosition() * schedule;
        double beta = 1 + (1-centre.getPosition()) * schedule;
        distribution = RandomSource.createBetaDistribution(alpha, beta);
    }

    private static double calcSchedule(double certainty){
        if(certainty > 0.99){
            return -1;
        }
        double exponent = 1.7 * certainty / Math.pow(1-certainty, 0.3);
        return Math.exp(exponent) - 1;
    }

    private double nextBeta(){
        if(certainty.getPosition() > 0.99){
            return centre.getPosition();
        }
        return distribution.sample();
    }


    public Color nextRandomColor(){
        if(channelsIndependence.isOn()){
            return nextColorUsingIndependentInterpolation();
        }else{
            return nextColorUsingLinearInterpolation();
        }
    }

    private Color nextColorUsingIndependentInterpolation(){
        double[] weights = new double[]{
                nextBeta(),
                nextBeta(),
                nextBeta()
        };
        return getWeightedColor(weights);
    }

    private Color nextColorUsingLinearInterpolation(){
        double weight = nextBeta();
        double[] weights = new double[]{weight, weight, weight};
        return getWeightedColor(weights);
    }

    private Color getWeightedColor(double[] weights){
        int red = lerp(primary.getRed(), secondary.getRed(), weights[0]);
        int green = lerp(primary.getGreen(), secondary.getGreen(), weights[1]);
        int blue = lerp(primary.getBlue(), secondary.getBlue(), weights[2]);
        return new Color(red, green, blue, alpha.getState());
    }

}
