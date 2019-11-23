package render;

import java.awt.Color;
import java.io.IOException;

import render.FragmentShader.FragmentData;
import render.Scene.SceneData;
import render.VertexShader.VertexShaderOut;
import vectors.Vec2;
import vectors.Vec3;
import vectors.Vec4;

public class Rasterizer {
	FragmentShader mainFShader=new FragmentShader();
	float ZBuffer[][]=null;
	BackBuffer bb=null;
	
	
	float crossProduct(Vec2 v1,Vec2 v2) 
	{
	    return (v1.x*v2.y) - (v1.y*v2.x);
	}
	public void setUpBuffer(BackBuffer bb) {
		this.bb=bb;
		ZBuffer=new float[(int) bb.size.x][(int) bb.size.y];
		for (int i = 0; i < ZBuffer.length; i++) {
			for (int j = 0; j < ZBuffer[i].length; j++) {
				ZBuffer[i][j]=-Float.MAX_VALUE;
			}
		}
		
	}
//**********DELETE*****
	public void rasterize( Vec4 v1,Vec4 v2,Vec4 v3,VertexShaderOut so[], SceneData sd) {

		
		
		
		Vec2 red=new Vec2(0,1);
		Vec2 blue=new Vec2(1,0);
		Vec2 green=new Vec2(1,1);
		

		
		float maxX = Math.max(v1.x, Math.max(v2.x, v3.x));
		float minX =  Math.min(v1.x, Math.min(v2.x, v3.x));
		float maxY =  Math.max(v1.y, Math.max(v2.y, v3.y));
		float minY =  Math.min(v1.y, Math.min(v2.y, v3.y));
		

		
		maxX= Math.min(1, maxX);
		minX=Math.max(-1,minX);
		maxY= Math.min(1, maxY);
		minY=Math.max(-1, minY);
		
		
		Vec2 vs1 = new Vec2(v2.x - v1.x, v2.y - v1.y);
		Vec2 vs2 = new Vec2(v3.x - v1.x, v3.y - v1.y);
		
		//System.out.println("gay"+minY+","+minY+","+maxY+","+maxX+","+minX+",");

//		if(crossProduct(vs1, vs2)<0) {
//			return;
//		}
		for (float x = minX; x <= maxX; x+=(1f/bb.size.x))
		{
		  for (float y = minY; y <= maxY; y+=(1f/bb.size.y))
		  {

		    	int ix=(int)(((x/2)+.5)*(bb.size.x));
		    	int iy=(int)( ((y/2)+.5)*(bb.size.y));   
		    	
		    	if(ix<0||ix>=bb.image.getWidth()||iy<0||iy>=bb.image.getHeight()) {
		    		continue;
		    	}
		    Vec2 q = new Vec2(x - v1.x, y - v1.y);

		    float s = (float)crossProduct(q, vs2) / crossProduct(vs1, vs2);
		    float t = (float)crossProduct(vs1, q) / crossProduct(vs1, vs2);

		    if ( (s >= 0) && (t >= 0) && (s + t <= 1))
		    {
//		    	float W1=(   (v2.y-v3.y)*(x-v3.x)+(v3.x-v2.x)*(y-v3.y)     )/
//		    			(	 (v2.y-v3.y)*(v1.x-v3.x)+(v3.x-v2.x)*(v1.y-v3.y)	    			);
//		    	
//		    	float W2=(   (v3.y-v1.y)*(x-v3.x)+(v1.x-v3.x)*(y-v3.y)     )/
//		    			(	 (v2.y-v3.y)*(v1.x-v3.x)+(v3.x-v2.x)*(v1.y-v3.y));
//		    	
//		    	float W3=1-W1-W2;
		    	
		    	float depth=Vec3.PCbary(s, t,v1.w,v2.w,v3.w, v1.z*v1.w, v2.z*v2.w, v3.z*v3.w);
		    	//float depth=Vec3.PCbary(s, t,so[0]..w,v2.w,v3.w, v1.z, v2.z, v3.z);
		    	
		    	
		    	Vec3 normal=Vec3.PCbary(s, t,v1.w,v2.w,v3.w, so[0].normal,so[1].normal,so[2].normal);
		    	
		    	float zz=Vec3.bary(s, t, 1/v1.w,               1/v2.w,                 1/v3.w);
		    	
		    	Vec2 uv=Vec3.bary(s,  t, so[0].uv.scale(1/v1.w),so[1].uv.scale(1/v2.w),so[2].uv.scale(1/v3.w)).scale(1/zz);
		    	
		    	
		    	Vec3 col=Vec3.PCbary(s,t,v1.w,v2.w,v3.w,so[0].color,so[1].color,so[2].color);
		    	
		    	
		    	Vec4 viewPos=Vec3.PCbary(s,t,v1.w,v2.w,v3.w,so[0].ViewPos,so[1].ViewPos,so[2].ViewPos);
		    	Vec4 screenPos=Vec3.PCbary(s,t,v1.w,v2.w,v3.w,so[0].screenPos,so[1].screenPos,so[2].screenPos);
		    	
		    	//uv=Vec3.bary(s,t,red,blue,green);
		    	//uv=so[0].uv.scale(W1).add(so[1].uv.scale(W2)).add(so[2].uv.scale(W3));
		    	
		    	
//		    	if(s>t&&s>(1-s-t)) {
//		    		depth=v2.z;
//		    	}else if(t>(1-s-t)) {
//		    		depth=v3.z;
//		    	}else {
//		    		depth=v1.z;
//		    	}
		    	
		    	


		    	if(depth>ZBuffer[ix][iy] ) {
		    		ZBuffer[ix][iy]=depth;
		    	}
		    	else {
		    		continue;
		    	}
		    	

		    	mainFShader.process(new FragmentData(ix, iy, uv, col, normal,screenPos,viewPos, depth, so[0].tid, sd), bb);
		    	
//		    	float dif=normal.dot(new Vec3(0,0,1));
//		    	try {
//
//					//bb.set(ix,iy,Texture.get(so[0].tid).get(uv.x, uv.y).mul(col).toColor());
//		    		//bb.set(x, y, uv.xy0().toFColor());
//		    	} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				
				//§bb.set(ix, iy, Color.WHITE);

		    }

		  }
		}
	}

}
