package utils;

import core.Pixel;
import core.PixelImage;

public class FilterProcessor {
    private final Pixel[][] sourcePixels;
    private final int imageHeight;
    private final int imageWidth;
    
    /**
     * Creates processor for given image data
     * @param sourcePixels Original pixel data
     * @param height Image height
     * @param width Image width
     */
    public FilterProcessor(Pixel[][] sourcePixels, int height, int width) {
        this.sourcePixels = sourcePixels;
        this.imageHeight = height;
        this.imageWidth = width;
    }
    
    /**
     * Processes interior pixels (excluding borders) with convolution
     * @param targetPixels Destination pixel array
     * @param kernel Convolution kernel to apply
     */
    public void processInteriorPixels(Pixel[][] targetPixels, ConvolutionKernel kernel) {
        for (int row = 1; row < imageHeight - 1; row++) {
            for (int col = 1; col < imageWidth - 1; col++) {
                targetPixels[row][col] = computeConvolutionAt(row, col, kernel);
            }
        }
    }
    
    /**
     * Computes convolution result for pixel at specified position
     * @param centerRow Row of center pixel
     * @param centerCol Column of center pixel
     * @param kernel Convolution kernel
     * @return New pixel with convolved values
     */
    private Pixel computeConvolutionAt(int centerRow, int centerCol, ConvolutionKernel kernel) {
        ColorAccumulator accumulator = new ColorAccumulator();
        int[][] weights = kernel.getWeights();
        
        // Apply 3x3 kernel around center pixel
        for (int deltaRow = -1; deltaRow <= 1; deltaRow++) {
            for (int deltaCol = -1; deltaCol <= 1; deltaCol++) {
                int sourceRow = centerRow + deltaRow;
                int sourceCol = centerCol + deltaCol;
                int kernelWeight = weights[deltaRow + 1][deltaCol + 1];
                
                accumulator.addWeightedPixel(sourcePixels[sourceRow][sourceCol], kernelWeight);
            }
        }
        
        return accumulator.getFinalPixel(kernel.getDivisor());
    }
}