package filters.simple;

import core.Filter;
import core.PixelImage;
import core.Pixel;
import utils.ColorUtils;

public class Negative implements Filter {
    
    /**
     * Applies negative transformation to the entire image
     * @param image The PixelImage to process
     */
    public void filter(PixelImage image) {
        Pixel[][] imagePixels = image.getData();
        
        applyNegativeTransformation(imagePixels, image.getHeight(), image.getWidth());
        image.setData(imagePixels);
    }
    
    /**
     * Processes all pixels in the image for negative transformation
     * @param pixelData 2D pixel array
     * @param height Image height
     * @param width Image width
     */
    private void applyNegativeTransformation(Pixel[][] pixelData, int height, int width) {
        for (int row = 0; row < height; row++) {
            processRowForNegative(pixelData[row], width);
        }
    }
    
    /**
     * Processes a single row of pixels
     * @param rowPixels Array of pixels in the row
     * @param width Number of pixels in the row
     */
    private void processRowForNegative(Pixel[] rowPixels, int width) {
        for (int col = 0; col < width; col++) {
            invertPixelColors(rowPixels[col]);
        }
    }
    
    /**
     * Inverts all color components of a single pixel
     * @param pixel The pixel to invert
     */
    private void invertPixelColors(Pixel pixel) {
        pixel.red = ColorUtils.invertColor(pixel.red);
        pixel.green = ColorUtils.invertColor(pixel.green);
        pixel.blue = ColorUtils.invertColor(pixel.blue);
    }
}