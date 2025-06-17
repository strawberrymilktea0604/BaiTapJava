package utils;

import core.Pixel;
import core.PixelImage;


public class ImageArrayFactory {
    
    /**
     * Creates new pixel array with borders copied from original
     * @param original Source pixel array
     * @param height Array height
     * @param width Array width
     * @return New array with copied border pixels
     */
    public static Pixel[][] createCopyWithBorders(Pixel[][] original, int height, int width) {
        Pixel[][] newArray = new Pixel[height][width];
        BorderInitializer.copyBorderPixels(original, newArray, height, width);
        return newArray;
    }
}