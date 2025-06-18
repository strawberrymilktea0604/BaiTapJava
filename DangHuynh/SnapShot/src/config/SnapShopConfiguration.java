package config;

import core.SnapShop;
import filters.simple.*;
import filters.convolution.*;

public class SnapShopConfiguration {
    
    /**
     * Configures the SnapShop application with all available filters
     * Sets up default filename and registers all implemented filters
     * @param snapShopApp The SnapShop application instance to configure
     */
    public static void configure(SnapShop snapShopApp) {
        // Configure default test image for development and testing
        setDefaultTestImage(snapShopApp);
        
        // Register simple transformation filters (70% requirement)
        registerSimpleTransformationFilters(snapShopApp);
        
        // Register advanced 3x3 convolution filters (100% requirement)
        registerConvolutionFilters(snapShopApp);
    }
    
    /**
     * Sets the default filename for easy testing and development
     * @param app SnapShop application instance
     */
    private static void setDefaultTestImage(SnapShop app) {
        app.setDefaultFilename("billg.jpg");
    }
    
    /**
     * Registers simple pixel transformation filters
     * These filters modify pixels in-place without convolution
     * @param app SnapShop application instance
     */
    private static void registerSimpleTransformationFilters(SnapShop app) {
        app.addFilter(new FlipVertical(), "Vertical Flip");
        app.addFilter(new Negative(), "Color Negative");
    }
    
    /**
     * Registers 3x3 convolution-based filters
     * These filters use weighted averaging with neighboring pixels
     * @param app SnapShop application instance
     */
    private static void registerConvolutionFilters(SnapShop app) {
        app.addFilter(new GaussianBlur(), "Gaussian Blur");
        app.addFilter(new LaplacianFilter(), "Laplacian Edge Detection");
        app.addFilter(new UnsharpMasking(), "Unsharp Mask Sharpen");
        app.addFilter(new Edgy(), "Edge Enhancement");
    }
}
