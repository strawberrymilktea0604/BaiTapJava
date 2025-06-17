package utils;

import core.Pixel;
import core.PixelImage;

public class BorderInitializer {
    
    /**
     * Copies border pixels from source to target array
     * @param source Source pixel array
     * @param target Target pixel array
     * @param height Array height
     * @param width Array width
     */
    public static void copyBorderPixels(Pixel[][] source, Pixel[][] target, int height, int width) {
        copyHorizontalBorders(source, target, height, width);
        copyVerticalBorders(source, target, height, width);
    }
    
    /**
     * Copies top and bottom border rows
     */
    private static void copyHorizontalBorders(Pixel[][] source, Pixel[][] target, int height, int width) {
        for (int col = 0; col < width; col++) {
            target[0][col] = clonePixel(source[0][col]); // Top row
            target[height - 1][col] = clonePixel(source[height - 1][col]); // Bottom row
        }
    }
    
    /**
     * Copies left and right border columns
     */
    private static void copyVerticalBorders(Pixel[][] source, Pixel[][] target, int height, int width) {
        for (int row = 1; row < height - 1; row++) {
            target[row][0] = clonePixel(source[row][0]); // Left column
            target[row][width - 1] = clonePixel(source[row][width - 1]); // Right column
        }
    }
    
    /**
     * Creates a copy of a pixel
     */
    private static Pixel clonePixel(Pixel original) {
        return new Pixel(original.red, original.green, original.blue);
    }
}