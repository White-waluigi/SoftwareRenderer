package render;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;


import render.TriangleList.Index;
import vectors.Mat4;
import vectors.Vec2;
import vectors.Vec3;
import vectors.Vec4;

public class VertexShader {

	public class VertexShaderOut {
		Vec3 normal;
		Vec4 screenPos;
		Vec3 color;
		Vec2 uv;
		int tid;
		public Vec4 ViewPos;
		
		@Override
		public String toString() {
			return screenPos.toString();
		}
	}

	Mat4 modelViewProj = null;
	Mat4 modelView = null;

	public void setMatrices(Mat4 mat, Mat4 view, Mat4 proj) {

		modelViewProj = proj.postMultiply(view).postMultiply(mat);
		modelView =mat;
	}

	public VertexShaderOut[][] process(TriangleList tris) {

		VertexShaderOut[][] ret = new VertexShaderOut[tris.indices.size() / 3][3];

		Vec4[][] processedTris = new Vec4[tris.tris.size()][2];

		int ctr = 0;
		for (Vec3 t : tris.tris) {
			processedTris[ctr][0] = modelViewProj.transform(t.upgrade(1)).pDiv();
			processedTris[ctr++][1] = modelView.transform(t.upgrade(1));
		}

		for (int i = 0; i < ret.length; i++) {

//			Vec3 a=modelViewProj.transform(tris.tris.get(i*3+0).upgrade(1)).downgrade();
//			Vec3 b=modelViewProj.transform(tris.tris.get(i*3+1).upgrade(1)).downgrade();
//			Vec3 c=modelViewProj.transform(tris.tris.get(i*3+2).upgrade(1)).downgrade();
//			
			Index idx[]=new Index[3];
			for(int r=0;r<3;r++) {
				idx[r]=tris.indices.get(i * 3+r);	
			}
			
			
			
			
			Vec4[][] t = { processedTris[idx[0].id], processedTris[idx[1].id], processedTris[idx[2].id] };

			Vec3 normal = t[1][1].downgrade().subtract(t[0][1].downgrade())
					.cross(t[2][1].downgrade().subtract(t[0][1].downgrade())).normalize();

			for (int ii = 0; ii < ret[i].length; ii++) {

				//Vec4 o = modelViewProj.transform(tris.tris.get(i * 3 + ii).upgrade(1));

				ret[i][ii] = new VertexShaderOut();

				ret[i][ii].screenPos = t[ii][0];
				ret[i][ii].ViewPos = t[ii][1];
				ret[i][ii].normal = normal;
				ret[i][ii].uv = /*tris.uv.get(i * 3 + ii)*/ idx[ii].uv;
				ret[i][ii].color= idx[ii].color;
				ret[i][ii].tid= idx[ii].tid;

				
					
			}

		}
	//	System.out.println(Arrays.deepToString(ret));
//		Arrays.sort(ret, (Comparator<Vec4[]>) (Vec4[] a,Vec4[] b)->{
////			Vec4 o=Vec4.ZERO;
////			Vec4 t=Vec4.ZERO;
////			
////			for(Vec4 e:a)
////				o=o.add(e);
////			
////			for(Vec4 e:b)
////				t=t.add(e);
////			
////			
////			return o.length()-t.length()>0?1:-1;
////				
//			float o=0;
//			float t=0;
//			for(Vec4 g:a)
//				o+=g.z;
//			
//			for(Vec4 g:b)
//				t+=g.z;
//			
//			return (int) (o-t);
//
//		}
//				);
//
		return ret;

	}

}
