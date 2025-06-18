package utils;

import core.Pixel;
import core.PixelImage;

public class ColorAccumulator {
    private int redAccumulation;
    private int greenAccumulation;
    private int blueAccumulation;
    
    /**
     * Adds a weighted pixel to the accumulation
     * @param pixel Source pixel
     * @param weight Convolution weight
     */
    public void addWeightedPixel(Pixel pixel, int weight) {
        redAccumulation += pixel.red * weight;
        greenAccumulation += pixel.green * weight;
        blueAccumulation += pixel.blue * weight;
    }
    
    /**
     * Gets the final pixel after normalization and clamping
     * @param divisor Normalization factor
     * @return New pixel with processed colors
     */
    public Pixel getFinalPixel(int divisor) {
        int normalizedRed = ColorUtils.normalizeAndClamp(redAccumulation, divisor);
        int normalizedGreen = ColorUtils.normalizeAndClamp(greenAccumulation, divisor);
        int normalizedBlue = ColorUtils.normalizeAndClamp(blueAccumulation, divisor);
        
        return new Pixel(normalizedRed, normalizedGreen, normalizedBlue);
    }
    
    /**
     * Resets accumulator for new calculation
     */
    public void reset() {
        redAccumulation = greenAccumulation = blueAccumulation = 0;
    }
}