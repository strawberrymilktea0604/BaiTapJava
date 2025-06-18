package core;

import java.awt.image.*;
import utils.*;

/**
 * Enhanced PixelImage class with advanced filtering capabilities
 * Provides an interface to a picture as an array of Pixels with convolution support
 * @author strawberrymilktea0604
 * @version 2025-06-17
 */
public class PixelImage {
    private BufferedImage myImage;
    private int width;
    private int height;

    /**
     * Map this PixelImage to a real image
     * @param bi The BufferedImage
     */
    public PixelImage(BufferedImage bi) {
        this.myImage = bi;
        this.width = bi.getWidth();
        this.height = bi.getHeight();
    }

    /**
     * Return the width of the image
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Return the height of the image
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Return the BufferedImage of this PixelImage
     */
    public BufferedImage getImage() {
        return this.myImage;
    }

    /**
     * Return the image's pixel data as an array of Pixels
     * @return The array of pixels [height][width]
     */
    public Pixel[][] getData() {
        Raster r = this.myImage.getRaster();
        Pixel[][] data = new Pixel[r.getHeight()][r.getWidth()];
        int[] samples = new int[3];

        for (int row = 0; row < r.getHeight(); row++) {
            for (int col = 0; col < r.getWidth(); col++) {
                samples = r.getPixel(col, row, samples);
                Pixel newPixel = new Pixel(samples[0], samples[1], samples[2]);
                data[row][col] = newPixel;
            }
        }
        return data;
    }

    /**
     * Set the image's pixel data from an array
     * @param data The pixel array to set
     */
    public void setData(Pixel[][] data) {
        int[] pixelValues = new int[3];
        WritableRaster wr = this.myImage.getRaster();

        if (data.length != wr.getHeight()) {
            throw new IllegalArgumentException("Array height does not match image height");
        } else if (data[0].length != wr.getWidth()) {
            throw new IllegalArgumentException("Array width does not match image width");
        }

        for (int row = 0; row < wr.getHeight(); row++) {
            for (int col = 0; col < wr.getWidth(); col++) {
                pixelValues[0] = data[row][col].red;
                pixelValues[1] = data[row][col].green;
                pixelValues[2] = data[row][col].blue;
                wr.setPixel(col, row, pixelValues);
            }
        }
    }

    /**
     * Applies convolution filter with specified kernel
     * Uses modular approach with helper classes for clean separation of concerns
     * @param kernel The convolution kernel to apply
     */
    public void applyConvolutionFilter(ConvolutionKernel kernel) {
        Pixel[][] originalData = this.getData();
        Pixel[][] processedData = ImageArrayFactory.createCopyWithBorders(originalData, this.getHeight(), this.getWidth());
        
        FilterProcessor processor = new FilterProcessor(originalData, this.getHeight(), this.getWidth());
        processor.processInteriorPixels(processedData, kernel);
        
        this.setData(processedData);
    }
    
    /**
     * Legacy method for backward compatibility with original transformImage
     * Applies 3x3 convolution with integer weights
     * @param weights 3x3 weight matrix
     */
    public void transformImage(int[][] weights) {
        // Calculate total weight for normalization
        int totalWeight = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                totalWeight += weights[i][j];
            }
        }
        
        // Prevent division by zero
        if (totalWeight == 0) {
            totalWeight = 1;
        }
        
        // Create kernel and apply convolution
        ConvolutionKernel kernel = new ConvolutionKernel(weights, totalWeight);
        applyConvolutionFilter(kernel);
    }
}