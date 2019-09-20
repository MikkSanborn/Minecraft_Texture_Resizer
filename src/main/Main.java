package main;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;

import javax.imageio.ImageIO;

public class Main {
	public static final short size = 24; // 1 -> 32767, integer, inclusive. 16 is default.
	public static final boolean blocks = true;
	public static final boolean items = true;
	
	/**
	 * NOTICE: The body of this program was created by pankaj, and modified by Mikk Sanborn. I take no credit for the image resizing aspect of this program.
	 * The original code source: "https://www.journaldev.com/615/java-resize-image"
	 * 
	 * "
	 * This class will resize all the images in a given folder
	 * @author pankaj
	 * "
	 */
	
	/** Some block textures have been removed from the block list, due to them being animated blocks. Animated blocks require a specific format, and have been removed due to this restriction. WARNING: Torches, ladders, vines, and redstone may become invisible.
	 *  Textures removed from resizing program:
	 *  - Prismarine
	 *  - Sea Lantern
	 *  - Fire
	 *  - Magma
	 *  - Water
	 *  - Lava
	 *  - Anvil
	 *  - Stonecutter
	 *  - Seagrass
	 *  - Tall Seagrass
	 *  - Kelp
	 *  - Command Block
	 *  - Chain Command Block
	 *  - Repeating Command Block
	 *  - Campfire
	*/
	
	public static void main(String[] args) throws IOException {
		if (size <= 0) throw new IllegalArgumentException("Value for size " + size + " is invalid. size must be an integer greater than 0. Values above 16 will not look very different.");
		
		// Get the output location
		String locOutBlock = new File("").getAbsolutePath() + "\\src\\out\\Resized Textures " + size + "x" + size + "\\assets\\minecraft\\textures\\block\\";
		String locOutItem = new File("").getAbsolutePath() + "\\src\\out\\Resized Textures " + size + "x" + size + "\\assets\\minecraft\\textures\\item\\";
		if (!new File(locOutBlock).exists()) { // if "Resized Textures WxW" doesn't exist, create it.
			new File(locOutBlock).mkdirs();
		}
		if (!new File(locOutItem).exists()) { // if "Resized Textures WxW" doesn't exist, create it.
			new File(locOutItem).mkdirs();
		}

		// Resize block textures if 'blocks'
		if (blocks) {
			String locInBlock = new File("").getAbsolutePath() + "\\src\\resources\\block\\";
			File folderBlock = new File(locInBlock);
			File[] listOfFilesBlock = folderBlock.listFiles();
			
			System.out.println("Total No of Files:"+listOfFilesBlock.length);
			Image img = null;
			BufferedImage tempPNG = null;
			// BufferedImage tempJPG = null;
			File newFilePNG = null;
			// File newFileJPG = null;
			for (int i = 0; i < listOfFilesBlock.length; i++) {
			      if (listOfFilesBlock[i].isFile()) {
			        System.out.println("File " + listOfFilesBlock[i].getName());
			        img = ImageIO.read(new File(locInBlock+listOfFilesBlock[i].getName()));
			        tempPNG = resizeImage(img, size, size);
			        newFilePNG = new File(locOutBlock+listOfFilesBlock[i].getName());
			        ImageIO.write(tempPNG, "png", newFilePNG);
			      }
			}
		}
		
		// Resize item textures if 'items'
		if (items) {
			String locInItem = new File("").getAbsolutePath() + "\\src\\resources\\item\\";
		    File folderItem = new File(locInItem);
		    File[] listOfFilesItem = folderItem.listFiles();
		    
		    System.out.println("Total No of Files:"+listOfFilesItem.length);
			Image img = null;
			BufferedImage tempPNG = null;
			// BufferedImage tempJPG = null;
			File newFilePNG = null;
			// File newFileJPG = null;
			for (int i = 0; i < listOfFilesItem.length; i++) {
			      if (listOfFilesItem[i].isFile()) {
			        System.out.println("File " + listOfFilesItem[i].getName());
			        img = ImageIO.read(new File(locInItem+listOfFilesItem[i].getName()));
			        tempPNG = resizeImage(img, size, size);
			        newFilePNG = new File(locOutItem+listOfFilesItem[i].getName());
			        ImageIO.write(tempPNG, "png", newFilePNG);
			      }
			}
		}
		
		// pack.mcmeta copy and pack.png copy
		String packIn = new File("").getAbsolutePath() + "\\src\\resources\\";
		String packOut = new File("").getAbsolutePath() + "\\src\\out\\Resized Textures " + size + "x" + size + "\\";
		
		try {
			Files.copy(new File(packIn + "pack.mcmeta").toPath(), new File(packOut+"pack.mcmeta").toPath());
			Files.copy(new File(packIn + "pack.png").toPath(), new File(packOut+"pack.png").toPath());
		} catch (FileAlreadyExistsException e) {
			// Nice!
		}
		
		System.out.println("DONE");
	}

	/**
	 * This function resize the image file and returns the BufferedImage object that can be saved to file system.
	 */
	public static BufferedImage resizeImage(final Image image, int width, int height) {
        final BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
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
}
