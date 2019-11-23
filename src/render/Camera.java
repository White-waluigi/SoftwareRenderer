package render;

import vectors.Mat4;
import vectors.Vec3;
import vectors.Vec4;

public class Camera {
	final Vec3 pos;
	final float fov;
	final Vec3 rot;
	public Camera(Vec3 pos, float fov, Vec3 rot) {
		super();
		this.pos = pos;
		this.fov = fov;
		this.rot = rot;
	}
	
	public Camera()
	{
		this(Vec3.ZERO,100,Vec3.ZERO);
		
	}

	public Mat4 getView() {
		return Mat4.ID.transpose();
	}
	


}
