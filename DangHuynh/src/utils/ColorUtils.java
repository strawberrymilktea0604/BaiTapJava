package utils;

import core.Pixel;
import core.PixelImage;

public class ColorUtils {
    public static final int MIN_COLOR_VALUE = 0;
    public static final int MAX_COLOR_VALUE = 255;
    
    /**
     * Normalizes accumulated value and clamps to valid color range
     * @param accumulatedValue Sum of weighted values
     * @param divisor Normalization factor
     * @return Clamped color value between 0-255
     */
    public static int normalizeAndClamp(int accumulatedValue, int divisor) {
        int normalizedValue = accumulatedValue / divisor;
        return clampToColorRange(normalizedValue);
    }
    
    /**
     * Clamps value to valid color range [0, 255]
     * @param value Input value
     * @return Clamped value
     */
    public static int clampToColorRange(int value) {
        return Math.max(MIN_COLOR_VALUE, Math.min(MAX_COLOR_VALUE, value));
    }
    
    /**
     * Inverts a color value (for negative filter)
     * @param originalValue Original color value
     * @return Inverted color value
     */
    public static int invertColor(int originalValue) {
        return MAX_COLOR_VALUE - originalValue;
    }
}
