package com.zack.photoapp.Services;

import com.zack.photoapp.Controllers.WebController;
import org.apache.commons.io.FileUtils;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import org.imgscalr.Scalr.*;

@Service
public class PhotoService {

	private static final Logger LOG = LoggerFactory.getLogger(WebController.class);

	public Collection<File> getAllImageNames(){
		File directory = new File("src/main/resources/static/images/");
		String[] extensions = new String[] { "jpg" };
		return FileUtils.listFiles(directory, extensions, false);
	}

	public Collection<String> getFirst10(){
		Collection<File> all = getAllImageNames();
		Collection<String> first10 = new ArrayList<>();
		String path = "/images/";
		int i = 0;
		for (File file : all) {
			String filename = file.getName();
			first10.add( path + filename );
			i++;
			if (i==10) break;
		}
		return first10;
	}


	public void resizeImages(){
		Collection<File> imagesNames = getAllImageNames();
		for (File imageName : imagesNames){
			try {
				BufferedImage image = ImageIO.read(imageName);
				String newPath = "src/main/resources/static/images/resized" + imageName.getName();
				ImageIO.write(Scalr.resize(image, 1024), "JPG", new File(newPath));
				LOG.info("Resized image: " + imageName);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
