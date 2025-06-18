package filters.convolution;

import core.Filter;
import core.PixelImage;
import utils.FilterFactory;
import utils.ConvolutionKernel;

public class LaplacianFilter implements Filter {
    
    /**
     * Applies Laplacian edge detection to the image
     * @param image The PixelImage to process
     */
    public void filter(PixelImage image) {
        ConvolutionKernel laplacianKernel = FilterFactory.createLaplacianKernel();
        image.applyConvolutionFilter(laplacianKernel);
    }
}
