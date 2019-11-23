package render;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import vectors.Mat4;
import vectors.Vec2;

public class BackBuffer {

	
	Vec2 size=new Vec2();
	Vec2 clip=new Vec2(.1,50);
	float fov=100;
	
	public Graphics g=null;
	
	BufferedImage image;

	public BackBuffer() {
	
		
	}
	public void setScreen(Vec2 size) {
		this.size=size;
		
		int type = BufferedImage.TYPE_3BYTE_BGR;

		image = new BufferedImage((int)size.x,(int) size.y, type);
		//fov=size.iratio()*100;
		for(int i=0;i<image.getWidth();i++)
			for(int j=0;j<image.getHeight();j++);
				//image.setRGB(i, j, Color.RED.getRGB());


	}
	public Mat4 getProj() {
		float s=(float) (1/(      Math.tan((fov/2)*(Math.PI/180)      )   ));
		float fmn=clip.y-clip.x;
		float aspect=size.ratio();

		return new Mat4(s/aspect,0,0,0,
				        0,s,0,0,
				        0,0,-clip.y/fmn,-1,
				        0,0,-(clip.y*clip.x)/fmn,0);
		
		
//	    float clipSpace = 1 / (clip.x-clip.y);
//	    float aspect=size.ratio();
//	   
//	    float fovAs=(float) (1/Math.tan(.5*fov));
////	    
//	    return new Mat4(
//	    		fovAs/aspect,0,0,0,
//	    		0,fovAs,0,0,
//	    		0,0,clip.y*clipSpace,1,
//	    		0,0,(-clip.y*clip.x)*clipSpace,0
//	    		);
	}
	public void set(int x, int  y, Color red) {
		image.setRGB( x,y , red.getRGB());
		//(int)(((x/2)+.5)*image.getWidth()),(int)( ((y/2)+.5)*image.getHeight() 
		//image.setRGB((int)x, (int)y, red.getRGB());
	}
	
	public void  display() {
		g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(),null);
	}

}
