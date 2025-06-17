package core;

public interface Filter {
	/**
	 * Modify the image according to your algorithm
	 * 
	 * @param theImage
	 *            The image to modify
	 */
	void filter(PixelImage theImage);
}
