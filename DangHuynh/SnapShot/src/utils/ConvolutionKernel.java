package utils;

import core.Pixel;
import core.PixelImage;

public class ConvolutionKernel {
    private final int[][] weights;
    private final int divisor;
    
    /**
     * Creates a new convolution kernel
     * @param weights 3x3 matrix of weights
     * @param divisor Normalization factor
     */
    public ConvolutionKernel(int[][] weights, int divisor) {
        validateKernel(weights, divisor);
        this.weights = deepCopyWeights(weights);
        this.divisor = divisor;
    }
    
    /**
     * Get the kernel weights
     * @return Copy of weights matrix
     */
    public int[][] getWeights() {
        return deepCopyWeights(weights);
    }
    
    /**
     * Get the normalization divisor
     * @return The divisor value
     */
    public int getDivisor() {
        return divisor;
    }
    
    /**
     * Validates kernel parameters
     */
    private void validateKernel(int[][] weights, int divisor) {
        if (weights == null || weights.length != 3 || weights[0].length != 3) {
            throw new IllegalArgumentException("Kernel must be 3x3 matrix");
        }
        if (divisor == 0) {
            throw new IllegalArgumentException("Divisor cannot be zero");
        }
    }
    
    /**
     * Creates deep copy of weights array
     */
    private int[][] deepCopyWeights(int[][] original) {
        int[][] copy = new int[3][3];
        for (int i = 0; i < 3; i++) {
            System.arraycopy(original[i], 0, copy[i], 0, 3);
        }
        return copy;
    }
}
