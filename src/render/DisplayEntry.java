package render;

import java.awt.Color;

import vectors.Mat4;
import vectors.Vec3;

public class DisplayEntry {

	public TriangleList triangles;
	public Vec3 pos;
	public Vec3 scale;
	public Vec3 rotate;
	public DisplayEntry(TriangleList triangles,  Vec3 pos, Vec3 scale, Vec3 rotate) {
		super();
		this.triangles = triangles;
		this.pos = pos;
		this.scale = scale;
		this.rotate = rotate;
	}
	
	public Mat4 getMat() {
		Mat4 off=new Mat4(1, 0, 0, pos.x,
						  0, 1, 0, pos.y, 
						  0, 0, 1, pos.z,
						  0, 0, 0, 1).transpose();
		Mat4 size=new Mat4(
					scale.x,0,0,0,
					0,scale.y,0,0,
					0,0,scale.z,0,
					0,0,0,1).transpose();
		
		Mat4 turn=Mat4.rotate(rotate);
		
		return off.postMultiply(turn).postMultiply(size);
		
		
	}

}
