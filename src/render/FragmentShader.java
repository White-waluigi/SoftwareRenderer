package render;

import java.awt.Color;

import render.Scene.SceneData;
import render.VertexShader.VertexShaderOut;
import vectors.Vec2;
import vectors.Vec3;
import vectors.Vec4;

public class FragmentShader {

	
	public static class FragmentData{
		//VertexShaderOut vo;
		int x;
		int y;
		Vec2 uv;
		Vec3 color;
		Vec3 normal;
		Vec4 ScreenPos;
		Vec4 ViewPos;

		float depth;
		int tid;
		SceneData sd;
		public FragmentData(int x, int y, Vec2 uv, Vec3 color, Vec3 normal, Vec4 screenPos, Vec4 viewPos, float depth,
				int tid, SceneData sd) {
			super();
			this.x = x;
			this.y = y;
			this.uv = uv;
			this.color = color;
			this.normal = normal;
			ScreenPos = screenPos;
			ViewPos = viewPos;
			this.depth = depth;
			this.tid = tid;
			this.sd = sd;
		}

		
		
	}
	public void process(FragmentData fd, BackBuffer bb) {
		//bb.set(fd.x, fd.y, new Vec3(fd.depth/-99.0f).toFColor());
		//float dif=fd.normal.dot(new Vec3(.5f,-1.3,1.2).normalize());
		Vec3 light=fd.sd.ambient;
		
		for(Light l:fd.sd.lights) {
			Vec3 dist=fd.ViewPos.downgrade().subtract(l.pos);
			
			Vec3 scale=new Vec3(10.0f/(dist).length()).scale(fd.normal.dot(dist.normalize()));
			light=scale.mul(l.diffuse).scale(1).clipm(0).add(light);
			
			
			Vec3 viewDir = (fd.ViewPos.downgrade()).normalize();
			Vec3 LightToObjDir = dist.normalize();
			Vec3 h = LightToObjDir.add(viewDir).normalize();
			
			//Vec3 h = fd.ViewPos.downgrade().add(dist).normalize();
			Vec3 specular =Vec3.ONE.scale( (float) Math.pow(fd.normal.scale(-1).dot(h),200));
			
			light=light.add(specular.scale(10/dist.length()));
			
			//bb.set(fd.x,fd.y,dist.normalize().toColor());
		}
		Vec3 fin=Texture.get(fd.tid).get(fd.uv.x, fd.uv.y).mul(light);
		
		bb.set(fd.x,fd.y, fin.toColor());
		//bb.set(fd.x,fd.y,new Vec3().toColor()  );
		//bb.set(fd.x,fd.y,Texture.get(fd.tid).get(fd.uv.x, fd.uv.y).mul(fd.color).scale(dif).toColor());
	}
}
