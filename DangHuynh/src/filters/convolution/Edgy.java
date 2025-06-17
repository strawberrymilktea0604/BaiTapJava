package filters.convolution;

import core.Filter;
import core.PixelImage;
import utils.FilterFactory;
import utils.ConvolutionKernel;

public class Edgy implements Filter {
    
    /**
     * Applies edge enhancement to the image
     * @param image The PixelImage to enhance
     */
    public void filter(PixelImage image) {
        ConvolutionKernel edgyKernel = FilterFactory.createEdgyKernel();
        image.applyConvolutionFilter(edgyKernel);
    }
}