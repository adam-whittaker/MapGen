package com.mason.mapgen.paint.logic.tools.brush;

import com.mason.mapgen.paint.components.panes.leftPane.BrushColorDisplay;
import org.apache.commons.math3.distribution.BetaDistribution;

import java.awt.*;

import static com.mason.mapgen.core.Utils.lerp;

public class BrushColor{


    private int primaryRed = 200;
    private int primaryGreen = 0;
    private int primaryBlue = 0;
    private int secondaryRed = 0;
    private int secondaryGreen = 0;
    private int secondaryBlue = 200;
    private int alpha = 255;

    private double centre = 0.5;
    private double certainty = 0.5;
    private BetaDistribution distribution;
    private boolean interpolateChannelsIndependently = false;

    private final BrushColorDisplay brushColorDisplay;


    public BrushColor(BrushColorDisplay brushColorDisplay){
        recalculateBetaDistribution();
        this.brushColorDisplay = brushColorDisplay;
        brushColorDisplay.setBrushPeekers(this);
        brushColorDisplay.displayPrimaryColor(new Color(primaryRed, primaryGreen, primaryBlue));
        brushColorDisplay.displaySecondaryColor(new Color(secondaryRed, secondaryGreen, secondaryBlue));
    }

    private void recalculateBetaDistribution(){
        double alpha = 1 + centre * schedule(certainty);
        double beta = 1 + (1-centre) * schedule(certainty);
        distribution = new BetaDistribution(alpha, beta);
    }

    private double schedule(double certainty){
        if(certainty > 0.99){
            return -1;
        }
        double exponent = 1.7 * certainty / Math.pow(1-certainty, 0.3);
        return Math.exp(exponent) - 1;
    }


    public void setCentre(double centre){
        throwIfNotInUnitInterval(centre);
        this.centre = centre;
        recalculateBetaDistribution();
    }

    public double getCentre(){
        return centre;
    }

    public void setAlpha(int alpha){
        this.alpha = alpha;
    }

    public int getAlpha(){
        return alpha;
    }

    private void throwIfNotInUnitInterval(double val){
        if(val < 0 || val > 1){
            throw new IllegalArgumentException("Argument should be between 0 and 1!");
        }
    }

    public void setCertainty(double certainty){
        throwIfNotInUnitInterval(certainty);
        this.certainty = certainty;
        recalculateBetaDistribution();
    }

    public double getCertainty(){
        return certainty;
    }

    public void setChannelIndependence(boolean independence){
        interpolateChannelsIndependently = independence;
    }


    public void setPrimaryColor(Color color){
        primaryRed = color.getRed();
        primaryGreen = color.getGreen();
        primaryBlue = color.getBlue();
        brushColorDisplay.displayPrimaryColor(color);
    }

    public void setSecondaryColor(Color color){
        secondaryRed = color.getRed();
        secondaryGreen = color.getGreen();
        secondaryBlue = color.getBlue();
        brushColorDisplay.displaySecondaryColor(color);
    }


    public Color nextColor(){
        if(interpolateChannelsIndependently){
            return nextColorUsingIndependentInterpolation();
        }else{
            return nextColorUsingLinearInterpolation();
        }
    }

    private Color nextColorUsingIndependentInterpolation(){
        int red = lerp(primaryRed, secondaryRed, nextBeta());
        int green = lerp(primaryGreen, secondaryGreen, nextBeta());
        int blue = lerp(primaryBlue, secondaryBlue, nextBeta());
        return new Color(red, green, blue, alpha);
    }

    private double nextBeta(){
        if(certainty > 0.99){
            return centre;
        }
        return distribution.sample();
    }

    private Color nextColorUsingLinearInterpolation(){
        return getWeightedColor(nextBeta());
    }

    private Color getWeightedColor(double weight){
        int red = lerp(primaryRed, secondaryRed, weight);
        int green = lerp(primaryGreen, secondaryGreen, weight);
        int blue = lerp(primaryBlue, secondaryBlue, weight);
        return new Color(red, green, blue, alpha);
    }

    public Color getAverageColor(){
        return getWeightedColor(centre);
    }

}
