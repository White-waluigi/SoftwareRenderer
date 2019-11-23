package render;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import vectors.Vec3;

public class Texture {

	// Loaded Textures
	static List<Texture> textures = new ArrayList<>();
	// Loaded Files
	static Map<String, Texture> loaded = new HashMap<>();

	Raster raster;
	// Dirty glow hack
	public boolean glow = false;
	private int id;

	// Load during Scene setup

	private Texture(String img, int id) throws IOException {

//		 if(!loaded.containsKey(img)) {

		this.id = id;
		File imgPath = new File(img);
		BufferedImage bufferedImage = ImageIO.read(imgPath);

		// loaded.put(img, bufferedImage.getRaster());
//		 }

		raster = bufferedImage.getRaster();
	}

	public Vec3 get(float u, float v) {
		Vec3 a=sample(u,v,0,0);
		Vec3 b=sample(u,v,1,0);
		Vec3 c=sample(u,v,0,1);
		Vec3 d=sample(u,v,1,1);
		
		float plx=(u*raster.getWidth());
		float ply=(v*raster.getHeight());
		
		float lx=plx-((int)plx);
		float ly=ply-((int)ply);
		
		Vec3 o=a.lerp(b,lx);
		Vec3 t=c.lerp(d, lx);
		

		return o.lerp(t, ly);
		
		//return new Vec3(lx,ly,0);
		
	}
	public Vec3 sample(float u, float v,int offsx,int offsy) {
		float[] x = null;
		
		int a=offsx+(int) (u * (raster.getWidth()));
		int b=offsy+(int) (v * (raster.getHeight()));
		
		if (a < 0) {
			a=0;
			//return new Vec3(1, 0, 0);
		}
		if (a >= raster.getWidth()) {
			a=raster.getWidth()-1;
			//return new Vec3(1, 0, 0);
		}
		if (b < 0) {
			b = 0;
			//return new Vec3(1, 0 ,0);
		}
		if (b >= raster.getHeight()) {
			b = raster.getHeight()-1;
			//return new Vec3(1, 0, 0);
		}
		
		
		
		return new Vec3(raster.getPixel(a, b, x))
				.scale(1.f / 256);

	}

	public static Texture get(int id) {
		return textures.get(id);
	}

	public static int load(String texture) throws IOException {
		if(loaded.containsKey(texture)) {
			return loaded.get(texture).id;	
		}
		int id = textures.size();
		textures.add(new Texture(texture, id));
		return id;
	}

}
