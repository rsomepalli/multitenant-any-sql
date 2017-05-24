package com.analogyx.image.util;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

public class ImageUtil {

	public BufferedImage resizeImage(final Image image, int width, int height) {
        final BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        final Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setComposite(AlphaComposite.Src);
        //below three lines are for RenderingHints for better image quality at cost of higher processing time
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.drawImage(image, 0, 0, width, height, null);
        graphics2D.dispose();
        return bufferedImage;
    }
	
	public void resizeImage(File input, File outputFolder, List<String> formats, int width, int height) throws IOException{
		BufferedImage im = ImageIO.read(input);
		Image scaledInstance = im.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);
		BufferedImage bi = new BufferedImage(scaledInstance.getWidth(null), scaledInstance.getHeight(null), im.getType());
		Graphics2D g2d = bi.createGraphics();
		g2d.drawImage(scaledInstance, 0,0, null);
		for(String format: formats){
			ImageIO.write(bi, format, new File(outputFolder, input.getName().substring(0, input.getName().indexOf(".")+1)+format)); 
		}
	}
	
	public static void main(String[] args) throws IOException {
		new ImageUtil().resizeImage(new File("/Users/ravi.somepalli/Documents/marketplaceworkspace/marketplace/marketplace-domain/src/main/resources/static/public/images/Pallini.jpg"),
				new File("/Users/ravi.somepalli/Documents/marketplaceworkspace/marketplace/marketplace-domain/src/main/resources/static/public/images/resized"),
				Arrays.asList("jpg", "png"),
				200,
				200
				);
	}
}