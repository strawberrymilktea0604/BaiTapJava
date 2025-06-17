package utils;

import core.Pixel;
import core.PixelImage;

public class FilterFactory {
    
    /**
     * Creates Gaussian blur kernel for smoothing
     * @return Gaussian convolution kernel
     */
    public static ConvolutionKernel createGaussianKernel() {
        int[][] gaussianWeights = {
            {1, 2, 1},
            {2, 4, 2},
            {1, 2, 1}
        };
        return new ConvolutionKernel(gaussianWeights, 16);
    }
    
    /**
     * Creates Laplacian kernel for edge detection
     * @return Laplacian convolution kernel
     */
    public static ConvolutionKernel createLaplacianKernel() {
        int[][] laplacianWeights = {
            {-1, -1, -1},
            {-1,  8, -1},
            {-1, -1, -1}
        };
        return new ConvolutionKernel(laplacianWeights, 1);
    }
    
    /**
     * Creates unsharp masking kernel for sharpening
     * @return Unsharp masking convolution kernel
     */
    public static ConvolutionKernel createUnsharpMaskingKernel() {
        int[][] unsharpWeights = {
            {-1, -2, -1},
            {-2, 28, -2},
            {-1, -2, -1}
        };
        return new ConvolutionKernel(unsharpWeights, 16);
    }
    
    /**
     * Creates edgy kernel for edge enhancement
     * @return Edgy convolution kernel
     */
    public static ConvolutionKernel createEdgyKernel() {
        int[][] edgyWeights = {
            {-1, -1, -1},
            {-1,  9, -1},
            {-1, -1, -1}
        };
        return new ConvolutionKernel(edgyWeights, 1);
    }
}