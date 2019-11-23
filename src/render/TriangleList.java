package render;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import vectors.Vec2;
import vectors.Vec3;
import vectors.Vec4;

public class TriangleList{
	
	public static class Index{
		public int id;
		public int tid;
		public Vec3 color;
		public Vec2 uv;
		public Vec2 normal;
	}
	
	
	public List<Vec3> tris=new ArrayList();
	public List<Index> indices=new ArrayList();
	
	
	int currentFace=0;
	

	public void addTri(float x,float y,float z) {
		tris.add(new Vec3(x,y,z));
		//uv.add(new Vec2(u,v));
	}

	public void addTri(Integer[] a) {
		addTri(a[0],a[1],a[2]);
	}
	
	public void addIndex(int id,float cx,float cy,float cz,float u,float v) {
		
		Index in=new Index();
		in.id=id;
		in.color=new Vec3(cx,cy,cz);
		in.uv=new Vec2(u,v);
		in.tid=currentFace;

		
		
		indices.add(in);
	}

	public void nextFace(String texture) {
		try {
			currentFace=Texture.load(texture);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
