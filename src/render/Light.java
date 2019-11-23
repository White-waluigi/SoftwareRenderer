package render;

import vectors.Vec3;
import vectors.Vec4;

public class Light {


	public Light(Vec3 pos, Vec3 diffuse) {
		super();
		this.pos = pos;
		this.diffuse = diffuse;
	}
	public Vec3 pos;
	public Vec3 diffuse;
}
