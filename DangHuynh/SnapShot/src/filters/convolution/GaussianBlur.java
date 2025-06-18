package filters.convolution;

import core.Filter;
import core.PixelImage;
import utils.FilterFactory;
import utils.ConvolutionKernel;

public class GaussianBlur implements Filter {
    
    /**
     * Applies Gaussian blur to the image
     * @param image The PixelImage to blur
     */
    public void filter(PixelImage image) {
        ConvolutionKernel gaussianKernel = FilterFactory.createGaussianKernel();
        image.applyConvolutionFilter(gaussianKernel);
    }
}