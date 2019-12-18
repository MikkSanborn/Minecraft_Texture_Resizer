package main;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import annotations.*;

/**
 * The {@code Main} class of the {@code Minecraft_Texture_Resizer} program.
 * <p>Options that the user may choose to modify are :
 * 	<ul>
 * 		<li>{@link #size} : The size that textures will be resized to. Default: 16, Minimum: 1, Maximum: 32767 (inclusive).</li>
 * 		<li>{@link #useScale} : Whether or not a scaling should be applied to non-square, 16x16 textures.</li>
 * 		<li>{@link #block} : Whether or not to resize block textures.</li>
 * 		<li>{@link #effect} : Whether or not to resize effect textures. (This only includes "dither.png", which is an unused texture.)</li>
 * 		<li>{@link #entity} : Whether or not to resize entity textures.</li>
 * 		<li>{@link #environment} : Whether or not to resize environment textures.</li>
 * 		<li>{@link #font} : Whether or not to resize font textures.</li>
 * 		<li>{@link #gui} : Whether or not to resize gui textures.</li>
 * 		<li>{@link #item} : Whether or not to resize item textures.</li>
 * 		<li>{@link #map} : Whether or not to resize map textures.</li>
 * 		<li>{@link #misc} : Whether or not to resize misc (miscellaneous) textures.</li>
 * 		<li>{@link #mob_effect} : Whether or not to resize mob_effect (status effect icon) textures.</li>
 * 		<li>{@link #models} : Whether or not to resize models (armor) textures.</li>
 * 		<li>{@link #painting} : Whether or not to resize painting textures.</li>
 * 		<li>{@link #particle} : Whether or not to resize particle textures.</li>
 * 		<li>{@link #name_out} : The name that the output resourcepack should assume upon completion. If left blank, will follow the format of "Resized Textures NxN" where 'N' is {@link size}.</li>
 * 		<li>{@link #pack} : The name of the resourcepack to use as a reference for original images.</li>
 * 		<li>{@link #pack_format} : The version that this resourcepack is intended for. Before modifying, fully read the {@link #pack_format} description.</li>
 * 	</ul>
 * </p>
 */
public class Main {
	
	/**
	 * The {@code size} variable represents the size that textures are meant to be resized to. 
	 * 
	 * <p>The {@code size} variable is of the {@link Short} type and expects an integer value between 1 and 32767.</p>
	 * <p>Default value: 16, min value: 1, max value: 32767</p>
	 * <p><strong>NOTE:</strong> Larger numbers run slower and may cause crashes.</p>
	 */
	@ShortRangeDefaultValue(value = 16, minimum = 1, maximum = 32767)
	public static final short size = 16;
	
	/**
	 * The {@code useScale} variable determines if the resizing program should resize images by scale.
	 * <p>If {@code useScale = true}, images will be resized to {@code (}{@link #scale}{@code *w, }{@link #scale}{@code *h)}.</p>
	 * <p>If {@code useScale = false}, images will be resized to {@code (}{@link #size}{@code , }{@link #size}{@code )}.</p>
	 */
	@BooleanDefaultValue(true)
	public static final boolean useScale = true;
	
	/**
	 * The {@code block} variable determines if images in {@code pack/assets/minecraft/textures/block} should be resized.
	 * <hr />
	 * <p>Examples:
	 * 	<li>Dirt</li>
	 * 	<li>Stone</li>
	 * 	<li>Bedrock</li>
	 * </p>
	 */
	@BooleanDefaultValue(true)
	public static final boolean block = true;
	
	/**
	 * The {@code effect} variable determines if images in {@code pack/assets/minecraft/textures/effect} should be resized.
	 * <hr />
	 * <p>This only has "dither"</p>
	 */
	@BooleanDefaultValue(false)
	public static final boolean effect = false;
	
	/**
	 * The {@code entity} variable determines if images in {@code pack/assets/minecraft/textures/entity} should be resized.
	 * <hr />
	 * <p>Examples:
	 * 	<li>Pig</li>
	 * 	<li>Creeper</li>
	 * 	<li>Armor Stand</li>
	 * 	<li>Tile entities, including bells and banners</li>
	 * </p>
	 */
	@BooleanDefaultValue(false)
	public static final boolean entity = false;
	
	/**
	 * The {@code environment} variable determines if images in {@code pack/assets/minecraft/textures/environment} should be resized.
	 * <hr />
	 * <p>Examples:
	 * 	<li>Clouds</li>
	 * 	<li>Rain</li>
	 * 	<li>Sun</li>
	 * 	<li>Moon</li>
	 * </p>
	 */
	@BooleanDefaultValue(false)
	public static final boolean environment = false;
	
	/**
	 * The {@code font} variable determines if images in {@code pack/assets/minecraft/textures/font} should be resized.
	 * <hr />
	 * <p><strong>NOTE:</strong> Will resize <em>all unicode characters</em>, often rendering text <em>unreadable</em>.</p>
	 */
	@BooleanDefaultValue(false)
	public static final boolean font = false;
	
	/**
	 * The {@code gui} variable determines if images in {@code pack/assets/minecraft/textures/gui} should be resized.
	 * <hr />
	 * <p><strong>NOTE:</strong> This often makes playing a bit difficult, as certain gui elements become difficult to see/use.
	 * <p>Examples:
	 * 	<li>Inventory</li>
	 * 	<li>Game Menu (pause menu)</li>
	 * 	<li>Title screen</li>
	 * </p>
	 */
	@BooleanDefaultValue(false)
	public static final boolean gui = false;
	
	/**
	 * The {@code item} variable determines if images in {@code pack/assets/minecraft/textures/item} should be resized.
	 * <hr />
	 * <p>Examples:
	 * 	<li>Dirt</li>
	 * 	<li>Swords</li>
	 * 	<li>Food</li>
	 * </p>
	 */
	@BooleanDefaultValue(true)
	public static final boolean item = true;
	
	/**
	 * The {@code map} variable determines if images in {@code pack/assets/minecraft/textures/map} should be resized.
	 * <hr />
	 * <p>Examples:
	 * 	<li>Map background texture</li>
	 * 	<li>Map Icons</li>
	 * </p>
	 */
	@BooleanDefaultValue(false)
	public static final boolean map = false;
	
	/**
	 * The {@code misc} variable determines if images in {@code pack/assets/minecraft/textures/misc} should be resized.
	 * <hr />
	 * <p>Examples:
	 * 	<li>Underwater Texture</li>
	 * 	<li>Pumkpin Overlay</li>
	 * 	<li>Vignette Effect</li>
	 * </p>
	 */
	@BooleanDefaultValue(false)
	public static final boolean misc = false;
	
	/**
	 * The {@code mob_effect} variable determines if images in {@code pack/assets/minecraft/textures/mob_effect} should be resized.
	 * <hr />
	 * <p>Effect icons such as:
	 * 	<li>Regeneration</li>
	 * 	<li>Slowness</li>
	 * 	<li>Haste</li>
	 * </p>
	 */
	@BooleanDefaultValue(false)
	public static final boolean mob_effect = false;
	
	/**
	 * The {@code models} variable determines if images in {@code pack/assets/minecraft/textures/models} should be resized.
	 * <hr />
	 * <p>All armor models (when worn), such as:
	 * 	<li>Leather Armor</li>
	 * 	<li>Iron Armor</li>
	 * 	<li>Chainmail Armor</li>
	 * </p>
	 */
	@BooleanDefaultValue(false)
	public static final boolean models = false; // i.e. armor
	
	/**
	 * The {@code painting} variable determines if images in {@code pack/assets/minecraft/textures/painting} should be resized.
	 * <hr />
	 * <p>Examples:
	 * 	<li>Wanderer</li>
	 * 	<li>Wither</li>
	 * 	<li>Burning Skull</li>
	 * </p>
	 */
	@BooleanDefaultValue(false)
	public static final boolean painting = false;
	
	/**
	 * The {@code particle} variable determines if images in {@code pack/assets/minecraft/textures/particle} should be resized.
	 * <hr />
	 * <p>All particles such as:
	 * 	<li>Sword Sweep</li>
	 * 	<li>Enchanting Table</li>
	 * 	<li>Water Drip</li>
	 * </p>
	 */
	@BooleanDefaultValue(false)
	public static final boolean particle = false;
	
	/**
	 * The {@code name_out} variable represents the name of the output folder that contains the resized textures.
	 * <p>If {@code name_out} is empty ("") or null (null), then the output folder will follow the name structure "{@code Resized Textures 16x16}", if {@code 16} is the {@link #size} of the output textures.
	 * <hr />
	 * <p><strong>Naming conventions:</strong> Do not use any of the following characters in the {@code name_out} variable's value:
	 * <li>Percent (%)</li>
	 * <li>Ampersand (&)</li>
	 * <li>Asterisk (*)</li>
	 * <li>Angle brackets (<>)</li>
	 * <li>Backslash (\)</li>
	 * <li>Colon (:)</li>
	 * <li>Question mark (?)</li>
	 * <li>Slash (/)</li>
	 * <li>Pipe (|)</li>
	 * <li>Quotation mark (")</li>
	 */
	@StringDefaultValue("")
	public static final String name_out = "";
	
	/**
	 * The {@code pack} variable determines which resourcepack to take as an input from the resources folder.
	 */
	@StringDefaultValue("Default")
	public static final String pack = "Default";
	
	/**
	 * <em>Advanced users only:</em>
	 * <p>The {@code pack_format} variable represents the "pack_format" that the pack.mcmeta file uses.</p>
	 * <hr />
	 * <p>{@code pack_format} value uses by number:
	 * <ul>
	 * 	<li>{@code pack_format} = 1 is used for Java Editions 1.6 - 1.8</li>
	 * 	<li>{@code pack_format} = 2 is used for Java Editions 1.9 - 1.10</li>
	 * 	<li>{@code pack_format} = 3 is used for Java Editions 1.11 - 1.12</li>
	 * 	<li>{@code pack_format} = 4 is used for Java Editions 1.13 - 1.14</li>
	 * 	<li>{@code pack_format} = 5 is used for Java Edition 1.15</li>
	 * </ul>
	 */
	@ShortRangeDefaultValue(value = 4, minimum = 1, maximum = 5)
	public static final short pack_format = 4;
	
	//// ====-====-====-====-====-====-====-====-====-====-====-====-====-====-====-==== ////
	// OTHER REQUISITES, DO NOT TOUCH!
	/**
	 * <strong>DO NOT MODIFY</strong>
	 * <p>The {@code scale} variable is used to sclae the {@code width} and {@code height} of images, if {@link #useScale} is {@code true}.</p>
	 */
	public static final double scale = 16.0/size;
	
	/**
	 * <strong>DO NOT MODIFY</strong>
	 * <p>The {@code timeInit} variable stores the initialization time of the program as a {@code long} type variable.</p>
	 * <p>This is used to calculate the time that the program was active upon termination.</p>
	 */
	public static final long timeInit = System.currentTimeMillis();
	
	/**
	 * <strong>DO NOT MODIFY</strong>
	 * <p>The {@code loc} variable stores the location of this project's source folder (Minecraft_Texture_Resizer) on this device's storage.</p>
	 * <p>This is used to make absolute references to local resources, i.e. taking the input resourcepack, and the output images and resourcepack.</p>
	 */
	public static final String loc = new File("").getAbsolutePath().replace("\\", "/");
	
	/**
	 * <strong>DO NOT MODIFY :</strong> Although this is similar to many of the other optional settings, this must be false due to occasional issues with game crashes.
	 * <p>The {@code colormap} variable determines if images in {@code pack/assets/minecraft/textures/colormap} should be resized.</p>
	 * <hr />
	 * <p>Examples:
	 * 	<ul>
	 * 		<li>name1</li>
	 * 		<li>name2</li>
	 * 	</ul>
	 * </p>
	 */
	public static final boolean colormap = false;
	
	/**
	 * The {@code main} method of the {@code Minecraft_Texture_Resizer} program.
	 * @param args
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
		if (size <= 0) throw new IllegalArgumentException("Value for size " + size + " is invalid. size must be an integer greater than 0. Values above 16 will not look very different."); // Size cannot be 0 or smaller
		
		// Find the output location
		String locOut;
		if (name_out != null && !name_out.equals("")) {
			locOut = loc + "\\src\\out\\" + name_out + "\\";
		} else {
			locOut = loc + "\\src\\out\\Resized Textures " + size + "x" + size + "\\";
		}
		locOut.replace('\\', '/');
		if (!new File(locOut).exists()) { // if the folder named (name_out) doesn't exist in "(loc)/src/out/"
			new File(locOut).mkdirs(); // create it.
		}
		
		// Create all needed lists
		ArrayList<File> source_files = new ArrayList<File>();
		ArrayList<String> imgs_loc = new ArrayList<String>();
		ArrayList<BufferedImage> imgs_in = new ArrayList<BufferedImage>();
		ArrayList<File> unused_files = new ArrayList<File>(); // used for non-image files, i.e. mcmeta files
		
		// Get the file from the root, in this case, (pack)
		source_files.add(new File(loc + "/src/resources/" + pack));
		System.out.println(loc + "/src/resources/" + pack);
		
		// Recursively get all images into imgs_in (BufferedImage format)
		for (int i = 0; i < source_files.size(); i++) { // For each file in (source_files),
			File f = source_files.get(i); // Make an easily referenced variable
			if(f.isDirectory()) { // if (f) is a directory
				String s = f.getAbsolutePath().substring(loc.length()).replace('\\', '/'); // Get the pathname (to s)
				if (!block && s.endsWith("block")) { // If (s) ends with block (is the block directory)
					source_files.remove(i); // Remove it
					i--; // Go back one step to account for list shortening
					System.out.println("Removed block."); // Inform the user
					continue; // Skip every other "if" statement; this has already been removed. Skip to the next iteration of the loop.
				}
				if (!colormap && s.endsWith("colormap")) { // If (s) ends with colormap (is the colormap directory)
					source_files.remove(i); // Remove it
					i--; // Go back one step to account for list shortening
					System.out.println("Removed colormap."); // Inform the user
					continue; // Skip every other "if" statement; this has already been removed. Skip to the next iteration of the loop.
				}
				if (!effect && s.endsWith("effect")) { // If (s) ends with effect (is the effect directory)
					source_files.remove(i); // Remove it
					i--; // Go back one step to account for list shortening
					System.out.println("Removed effect."); // Inform the user
					continue; // Skip every other "if" statement; this has already been removed. Skip to the next iteration of the loop.
				}
				if (!entity && s.endsWith("entity")) { // If (s) ends with entity (is the entity directory)
					source_files.remove(i); // Remove it
					i--; // Go back one step to account for list shortening
					System.out.println("Removed entity."); // Inform the user
					continue; // Skip every other "if" statement; this has already been removed. Skip to the next iteration of the loop.
				}
				if (!environment && s.endsWith("environment")) { // If (s) ends with environment (is the environment directory)
					source_files.remove(i); // Remove it
					i--; // Go back one step to account for list shortening
					System.out.println("Removed environment."); // Inform the user
					continue; // Skip every other "if" statement; this has already been removed. Skip to the next iteration of the loop.
				}
				if (!font && s.endsWith("font")) { // If (s) ends with font (is the font directory)
					source_files.remove(i); // Remove it
					i--; // Go back one step to account for list shortening
					System.out.println("Removed font."); // Inform the user
					continue; // Skip every other "if" statement; this has already been removed. Skip to the next iteration of the loop.
				}
				if (!gui && s.endsWith("gui")) { // If (s) ends with gui (is the gui directory)
					source_files.remove(i); // Remove it
					i--; // Go back one step to account for list shortening
					System.out.println("Removed gui."); // Inform the user
					continue; // Skip every other "if" statement; this has already been removed. Skip to the next iteration of the loop.
				}
				if (!item && s.endsWith("item")) { // If (s) ends with item (is the item directory)
					source_files.remove(i); // Remove it
					i--; // Go back one step to account for list shortening
					System.out.println("Removed item."); // Inform the user
					continue; // Skip every other "if" statement; this has already been removed. Skip to the next iteration of the loop.
				}
				if (!map && s.endsWith("map")) { // If (s) ends with map (is the map directory)
					source_files.remove(i); // Remove it
					i--; // Go back one step to account for list shortening
					System.out.println("Removed map."); // Inform the user
					continue; // Skip every other "if" statement; this has already been removed. Skip to the next iteration of the loop.
				}
				if (!misc && s.endsWith("misc")) { // If (s) ends with misc (is the misc directory)
					source_files.remove(i); // Remove it
					i--; // Go back one step to account for list shortening
					System.out.println("Removed misc."); // Inform the user
					continue; // Skip every other "if" statement; this has already been removed. Skip to the next iteration of the loop.
				}
				if (!mob_effect && s.endsWith("mob_effect")) { // If (s) ends with mob_effect (is the mob_effect directory)
					source_files.remove(i); // Remove it
					i--; // Go back one step to account for list shortening
					System.out.println("Removed mob_effect."); // Inform the user
					continue; // Skip every other "if" statement; this has already been removed. Skip to the next iteration of the loop.
				}
				if (!models && s.endsWith("models")) { // If (s) ends with models (is the models directory)
					source_files.remove(i); // Remove it
					i--; // Go back one step to account for list shortening
					System.out.println("Removed models."); // Inform the user
					continue; // Skip every other "if" statement; this has already been removed. Skip to the next iteration of the loop.
				}
				if (!painting && s.endsWith("painting")) { // If (s) ends with painting (is the painting directory)
					source_files.remove(i); // Remove it
					i--; // Go back one step to account for list shortening
					System.out.println("Removed painting."); // Inform the user
					continue; // Skip every other "if" statement; this has already been removed. Skip to the next iteration of the loop.
				}
				if (!particle && s.endsWith("particle")) { // If (s) ends with particle (is the particle directory)
					source_files.remove(i); // Remove it
					i--; // Go back one step to account for list shortening
					System.out.println("Removed particle."); // Inform the user
					continue; // Skip every other "if" statement; this has already been removed. Skip to the next iteration of the loop.
				}

				appendFileArrayToArrayList(source_files.remove(i).listFiles(), source_files); // Move contents from this directory into (source_files)
				i--; // Go back one step to account for list shortening
				continue; // Skip to the next iteration of the loop.
			} else { // if source_files.get(i) is NOT a directory (i.e. it's a file)
		        try { // Prepare to catch an error when trying to
					BufferedImage img = ImageIO.read(source_files.get(i)); // read this file as an image
					if (img != null) { // If this is indeed an image,
						imgs_in.add(img); // Add this file to (imgs_in), as a BufferedImage type. (Create a BufferedImage from f, and add it to the list)
						imgs_loc.add(source_files.get(i).getAbsolutePath()); // Make sure to add the location of the image in the corresponding location in (imgs_loc)
					} else { // If this is not an image
						unused_files.add(source_files.get(i)); // This file is not an image, keep it for later
						source_files.get(i); // Get rid of this file
					}
					source_files.remove(i); // Get rid of this file from (source_files)
					i--; // Go back one step to account for list shortening
					continue; // Skip to the next iteration of the loop.
				} catch (IOException e) { // In case there was either an (IOException)
					e.printStackTrace();
					i--;
				}
			}
		}
		
		// Create the (count) variable to represent the number of the total of files
		int count = imgs_in.size()+unused_files.size();
		
		System.out.println("Number of images to resize: " + count); // Inform the user about the quantity of images collected
		
		for (int i = 0; i < imgs_in.size(); i++) { // For each image (BufferedImage)
			BufferedImage img_in = imgs_in.get(i); // Make an easily referenceable variable for this file (BufferedImage)
			BufferedImage img_out; // Make a variable to represent the output image (BufferedImage)
	        
	        // Resize the (img_in) and store it to (img_out)
	        if (useScale) { // If (useScale) is true,
		        int w = (int) (img_in.getWidth()/scale); // Create a variable that represents the width of the output image
		        int h = (int) (img_in.getHeight()/scale); // Create a variable that represents the height of the output image
		        img_out = resizeImage(img_in, (w <= 0 ? 1:w), (h <= 0 ? 1:h)); // Set (img_out) to the resized scaled version.
	        } else { // If (useScale) is false,
		        img_out = resizeImage(img_in, size, size); // Set (img_out) to the resized version.
	        }
	        
	        // Prepare image for writing as a file to this device
	        File outputfile = new File(locOut + imgs_loc.get(i).substring(imgs_loc.get(i).replace("\\\\", "/").lastIndexOf(pack)+pack.length())); // Using quadruple backslashes because the replace method needs to escape the backslashes inside the String.
	        String dir = outputfile.getAbsolutePath().substring(0, outputfile.getAbsolutePath().lastIndexOf("\\")); // Using double backslashes because Java reads "\\" as "\" (the character '\' is an escape character).
	        
		    if (!new File(dir).exists()) new File(dir).mkdirs(); // If this is in a null directory (the folder the image will go in does not exist), create the directory
	        ImageIO.write(img_out, "png", outputfile); // Write (img_out) to the disk in the location (outputFile) as a "png" type
	        System.out.printf("%.2f", 100.0*i/count); // Inform the user what percentage of images have been resized
	        System.out.println("% : " + imgs_loc.get(i).substring(imgs_loc.get(i).replace("\\\\", "/").lastIndexOf(pack)+pack.length()).replace("\\\\", "/")); // Inform the user which file has just been created.
		}
		
		for (int i = 0; i < unused_files.size(); i++) {
			File f = unused_files.get(i);
			if (f.getAbsolutePath().endsWith(".png.mcmeta")) {
				BufferedReader r = new BufferedReader(new FileReader(f)); // Make a (BufferdReader) that makes reading text from files easier
				
				ArrayList<String> content = new ArrayList<String>(); // Make a place to store the input text
				
				String s = r.readLine(); // Store the first line
				while (s != null) { // While there is actually text,
					content.add(s); // Add it to the list of current lines
					s = r.readLine(); // then get the next line
				}
				
				r.close(); // Done reading it, close it.
				
				
				File outputfile = new File(locOut + f.getAbsolutePath().substring(f.getAbsolutePath().replace("\\\\", "/").lastIndexOf(pack)+pack.length())); // Using quadruple backslashes because the replace method needs to escape the backslashes inside the String.
		        String dir = outputfile.getAbsolutePath().substring(0, outputfile.getAbsolutePath().lastIndexOf("\\")); // Using double backslashes because Java reads "\\" as "\" (the character '\' is an escape character).
		        
			    if (!new File(dir).exists()) new File(dir).mkdirs(); // If this is in a null directory (the folder the image will go in does not exist), create the directory
		        
				BufferedWriter outputWriter = new BufferedWriter(new FileWriter(outputfile)); // Create a text file writer (BufferedWriter)
				
				for (int j = 0; j < content.size(); j++) { // For each (String) in (content)
					outputWriter.write(content.get(j)); // Write this (String) to [pack.mcmeta]
				}
				
				// Done with the (BufferedWriter), close it.
				outputWriter.flush();
				outputWriter.close();
				
				unused_files.remove(i);
				i--;
				
				// COPY IT
				
				System.out.printf("%.2f", 100.0*(count-unused_files.size())/count); // Inform the user what percentage of images have been resized
		        System.out.println("% : " + f.getAbsolutePath().substring(f.getAbsolutePath().replace("\\\\", "/").lastIndexOf(pack)+pack.length()).replace("\\\\", "/")); // Inform the user which file has just been created
		        
				continue;
			}
		}
		
		// Copy pack.mcmeta
		String[] content = new String[] // Create the list of strings that represent the content of [pack.mcmeta]
				{"{",
				"\t\"pack\": {",
				"\t\t\"pack_format\": " + pack_format + ",",
				"\t\t\"description\": \"" + ("A Resized Resourcepack! Texture Resizer " + size + "x" + size + ", and did" + (useScale ? " ":" not ") + "use scaling.") + "\"",
				"\t}",
				"}"};
		
		BufferedWriter outputWriter = new BufferedWriter(new FileWriter(new File(locOut + "pack.mcmeta"))); // Create a text file writer (BufferedWriter)
		
		for (int i = 0; i < content.length; i++) { // For each (String) in (content)
			outputWriter.write(content[i] + "\r\n"); // Write this (String) to [pack.mcmeta]
		}
		
		// Done with the (BufferedWriter), close it.
		outputWriter.flush();
		outputWriter.close();
		
		// Inform the user of the completion of writing [pack.mcmeta]
		System.out.println("100.00% : \\pack.mcmeta");
		
		// Inform the user of the completion of the program.
		System.out.printf("Operation completed; took %.2f seconds.", ((System.currentTimeMillis() - timeInit)/1000.0));
	}
	
	public static BufferedImage resizeImage(final Image image, int width, int height) {
        final BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setComposite(AlphaComposite.Src);
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.drawImage(image, 0, 0, width, height, null);
        graphics2D.dispose();
        return bufferedImage;
    }
	
	public static void appendFileArrayToArrayList(File[] a, ArrayList<File> al) {
		for (int i = 0; i < a.length; i++)
			al.add(a[i]);
	}
}
