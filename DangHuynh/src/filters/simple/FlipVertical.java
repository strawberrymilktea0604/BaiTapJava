package filters.simple;

import core.Filter;
import core.PixelImage;
import core.Pixel;
import utils.ColorUtils;

public class FlipVertical implements Filter {
    
    /**
     * Applies vertical flip transformation to the image
     * Swaps rows from top and bottom moving toward center
     * @param image The PixelImage object to transform
     */
    public void filter(PixelImage image) {
        Pixel[][] imageData = image.getData();
        int totalHeight = image.getHeight();
        int totalWidth = image.getWidth();
        
        performVerticalFlip(imageData, totalHeight, totalWidth);
        image.setData(imageData);
    }
    
    /**
     * Performs the actual vertical flipping operation
     * @param pixels 2D pixel array to flip
     * @param height Image height
     * @param width Image width
     */
    private void performVerticalFlip(Pixel[][] pixels, int height, int width) {
        Pixel[] temporaryRowStorage = new Pixel[width];
        
        int topRowIndex = 0;
        int bottomRowIndex = height - 1;
        
        // Swap rows from outside toward center
        while (topRowIndex < bottomRowIndex) {
            swapRows(pixels, topRowIndex, bottomRowIndex, temporaryRowStorage, width);
            topRowIndex++;
            bottomRowIndex--;
        }
    }
    
    /**
     * Swaps two rows in the pixel array
     * @param pixels Pixel array
     * @param topRow Index of top row
     * @param bottomRow Index of bottom row
     * @param tempStorage Temporary storage for row data
     * @param width Row width
     */
    private void swapRows(Pixel[][] pixels, int topRow, int bottomRow, 
                         Pixel[] tempStorage, int width) {
        // Store top row in temporary storage
        System.arraycopy(pixels[topRow], 0, tempStorage, 0, width);
        
        // Move bottom row to top position
        System.arraycopy(pixels[bottomRow], 0, pixels[topRow], 0, width);
        
        // Move stored top row to bottom position
        System.arraycopy(tempStorage, 0, pixels[bottomRow], 0, width);
    }
}