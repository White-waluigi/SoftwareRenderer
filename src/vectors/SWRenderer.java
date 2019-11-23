package vectors;

import java.awt.Color;
import java.awt.Graphics;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import render.DisplayEntry;
import render.Scene;

public class SWRenderer extends JFrame
{
	public static void main(String[] args)
	{
		new SWRenderer();
	}
	
	
	SWRenderer()
	{
		
		super();
		
		
		Scene s=new Scene();
		DisplayEntry de= s.addCube(new Vec3(0,0,15), Vec3.ONE.scale(1.6f), new Vec3(.5,.3f,.7), "stone.png","stone.png");
		DisplayEntry e=s.addCube(new Vec3(0,-3,8+10), new Vec3(10,1,10), new Vec3(0,0f,0), "sideGrass.png","topGrass.png");
		s.addCube(new Vec3(0,7,8+10), new Vec3(10,1,10), new Vec3(0,0f,0), "sideGrass.png","topGrass.png");
		
		
		//s.addLight(new Vec3(0 ,0,5 ), new Vec3(1,1,1));
		s.addLight(new Vec3(02 ,1,11 ), new Vec3(1.0,.3,1.2));
		s.addLight(new Vec3(-02 ,-.8,14 ), new Vec3(0.2,0.3,.4));
		
		
		setSize(800,750);
		setResizable(true);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		
		JPanel buffer=new JPanel()
		{
	
			float offset=0;
			@Override
			public void paint(Graphics _g)
			{
				
				
				super.paint(_g);
				
				s.setScreen(this.getHeight(),this.getWidth());
				
				s.render(_g);
				
				de.rotate=de.rotate.pp(0,.001);
				de.rotate=de.rotate.pp(1,.04);
				de.rotate=de.rotate.pp(2,.006);
				offset++;
				offset%=500;
				//de.pos=new Vec3(0,0,(offset)+5);
			
				//e.scale=e.scale.scale(1.01f);
				//System.out.println(e.scale.length());
				repaint();
				
			}
		};
		getContentPane().add(buffer);
		
		
		
		
	}
}
