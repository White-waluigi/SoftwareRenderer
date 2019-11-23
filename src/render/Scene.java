package render;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;

import render.VertexShader.VertexShaderOut;
import vectors.Vec2;
import vectors.Vec3;
import vectors.Vec4;

public class Scene {
	
	public static class SceneData{
		List<Light> lights=new ArrayList<Light>();	
		Vec3 ambient=new Vec3(0.1,0.1,0.15);
	}
	
	SceneData sd=new SceneData();
	
	public List<DisplayEntry> displayList = new ArrayList<>();

	public Camera camera = new Camera();
	public BackBuffer backBuffer = new BackBuffer();
	public Rasterizer rasterizer = new Rasterizer();

	public Light addLight(Vec3 pos,Vec3 color) {
		sd.lights.add(new Light(camera.getView().transform(pos),color));
		
		return sd.lights.get(sd.lights.size()-1);
		
	}
	
	public DisplayEntry addCube(Vec3 pos, Vec3 scale, Vec3 rot, String string, String string2) {
		TriangleList l = new TriangleList();

		for (int i = -1; i < 2; i += 2) {
			
//			l.add(-1, i, -1, 0, 0);
//			l.add(1, i, -1, 1, 0);
//			l.add(-1, i, 1, 0, 1);
//
//			l.add(1, i, 1, 0, 0);
//			l.add(-1, i, 1, 0, 1);
//			l.add(1, i, -1, 1, 0);

//			l.add(-1, -1, i, 0, 0);
//			l.add(1, -1, i, 1, 0);
//			l.add(-1, 1, i, 0, 1);
//
//			l.add(-1, 1, i, 0, 1);
//			l.add(1, -1, i, 1, 0);
//			l.add(1, 1, i, 0, 0);
//
//			l.add(i, -1, -1, 0, 0);
//			l.add(i, 1, -1, 1, 0);
//			l.add(i, -1, 1, 0, 1);
//
//			l.add(i, 1, -1, 1, 0);
//			l.add(i, -1, 1, 0, 1);
//			l.add(i, 1, 1, 0, 0);

			for (int ii = 0; ii < 3; ii++) {
				Integer[] a = { 1, 1, i };
				Integer[] b = { -1, 1, i };
				Integer[] c = { 1, -1, i };
				Integer[] d = { -1, -1, i };

				Collections.rotate(Arrays.asList(a), ii);
				Collections.rotate(Arrays.asList(b), ii);
				Collections.rotate(Arrays.asList(c), ii);
				Collections.rotate(Arrays.asList(d), ii);

//				l.addTri(c, 0, 1);
//				if(i<0) {
//					l.addTri(a, 0,0);
//				}
//				l.addTri(b, 1, 0);
//				if(i>0) {
//					l.addTri(a, 00,0);
//				}
//
//				
//				l.addTri(b, 1, 0);
//				if(i<0)
//					l.addTri(d,1, 1);
//				l.addTri(c, 0,1);
//				if(i>0)
//					l.addTri(d,1, 1);

				/* 0 */ l.addTri(-1, -1, -1); 
				/* 1 */ l.addTri(+1, -1, -1); 

				/* 2 */ l.addTri(+1, +1, -1);
				/* 3 */ l.addTri(-1, +1, -1);

				/* 4 */ l.addTri(-1, -1, 1);
				/* 5 */ l.addTri(+1, -1, +1);

				/* 6 */ l.addTri(+1, +1, +1);
				/* 7 */ l.addTri(-1, +1, +1);

				l.nextFace(string);
				//top 
				l.addIndex(0, 0, 1, 0, 1, 1);
				l.addIndex(1, 0, 1, 0, 0, 1);
				l.addIndex(2, 0, 1, 0, 0, 0);
				
				l.addIndex(0, 0, 1, 0, 1, 1);
				l.addIndex(2, 0, 1, 0, 0, 0);
				l.addIndex(3, 0, 1, 0, 1, 0);
				
				//bottom
				l.addIndex(7, 0, 0, 1, 0, 0);
				l.addIndex(6, 0, 0, 1, 1, 0);
				l.addIndex(5, 0, 0, 1, 1, 1);
				
				l.addIndex(7, 0, 0, 1, 0, 0);
				l.addIndex(5, 0, 0, 1, 1, 1);
				l.addIndex(4, 0, 0, 1, 0, 1);
				//left
				l.addIndex(0, 1, 0, 0, 0, 1);
				l.addIndex(3, 1, 0, 0, 0, 0);
				l.addIndex(7, 1, 0, 0, 1, 0);
				
				l.addIndex(0, 1, 0, 0, 0, 1);
				l.addIndex(7, 1, 0, 0, 1, 0);
				l.addIndex(4, 1, 0, 0, 1, 1);
				//right
				l.addIndex(2, 0, 1, 1, 1, 0);
				l.addIndex(1, 0, 1, 1, 1, 1);
				l.addIndex(5, 0, 1, 1, 0, 1);

				l.addIndex(2, 0, 1, 1, 1, 0);
				l.addIndex(5, 0, 1, 1, 0, 1);
				l.addIndex(6, 0, 1, 1, 0, 0);
				l.nextFace(string2);

				//front
				l.addIndex(3, 1, 1, 0, 1, 0);
				l.addIndex(2, 1, 1, 0, 1, 1);
				l.addIndex(6, 1, 1, 0, 0, 1);
				
				l.addIndex(3, 1, 1, 0, 1,0);
				l.addIndex(6, 1, 1, 0, 0, 1);
				l.addIndex(7, 1, 1, 0, 0, 0);
				//back
				l.addIndex(1, 1, 0,1, 1, 0);
				l.addIndex(0, 1, 0, 1, 1, 1);
				l.addIndex(4, 1, 0, 1, 0, 1);
				
				l.addIndex(1, 1, 0, 1, 1, 0);
				l.addIndex(4, 1, 0, 1, 0, 1);
				l.addIndex(5, 1, 0, 1, 0, 0);
			}
		}

		DisplayEntry de = new DisplayEntry(l,  pos, scale, rot);

		displayList.add(de);

		return de;

	}

	public void render(Graphics _g) {
		float c = 0;
		int cc = 0;
		backBuffer.g = _g;
		VertexShader vert = new VertexShader();

		rasterizer.setUpBuffer(backBuffer);

		for (DisplayEntry e : displayList) {
			vert.setMatrices(e.getMat(), camera.getView(), backBuffer.getProj());
			VertexShaderOut[][] res = vert.process(e.triangles);

			for (int i = 0; i < res.length; i++) {

//				double[] xp = { res[i][0].pos.x, res[i][1].pos.x, res[i][2].pos.x };
//				double[] yp = { res[i][0].pos.y, res[i][1].pos.y, res[i][2].pos.y };
//
//				int[] ixp = new int[xp.length];
//				int[] iyp = new int[yp.length];
//				for (int ii = 0; ii < ixp.length; ii++) {
//					ixp[ii] = (int) ((xp[ii] + .5) * backBuffer.size.x);
//					iyp[ii] = (int) ((yp[ii] + .5) * backBuffer.size.y);
//				}
//

//				rasterizer.rasterize(
//						new Vec4(ixp[0],iyp[0],res[i][0].pos.z,res[i][0].pos.w), 
//						new Vec4(ixp[1],iyp[1],res[i][1].pos.z,res[i][1].pos.w),
//						new Vec4(ixp[2],iyp[2],res[i][2].pos.z,res[i][2].pos.w),res[i]);
//				
				rasterizer.rasterize(res[i][0].screenPos, res[i][1].screenPos, res[i][2].screenPos, res[i], sd);

				// _g.setColor(Vec3.rainbow(c).toColor());
				// _g.fillPolygon(ixp, iyp, ixp.length);
			}

		}
		backBuffer.display();

	}

	public void setScreen(int height, int width) {
		backBuffer.setScreen(new Vec2(width, height));
	}


}
