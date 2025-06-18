package filters.convolution;

import core.Filter;
import core.PixelImage;
import utils.FilterFactory;
import utils.ConvolutionKernel;

public class UnsharpMasking implements Filter {
    
    /**
     * Applies unsharp masking to the image
     * @param image The PixelImage to sharpen
     */
    public void filter(PixelImage image) {
        ConvolutionKernel unsharpKernel = FilterFactory.createUnsharpMaskingKernel();
        image.applyConvolutionFilter(unsharpKernel);
    }
}