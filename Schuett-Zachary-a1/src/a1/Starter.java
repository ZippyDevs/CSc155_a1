
package a1;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.nio.*;
import javax.swing.*;
import static com.jogamp.opengl.GL4.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.GLContext;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.util.*;
import graphicslib3D.GLSLUtils.*;
import graphicslib3D.*;
//TESTED ON WOLFENSTEIN//
public class Starter extends JFrame implements GLEventListener, MouseWheelListener
{	private GLCanvas myCanvas;
	private int rendering_program;
	private int vao[]      = new int[1];
	private GLSLUtils util = new GLSLUtils();
	
	private float x            = 0.0f;
	private float y			   = 0.0f;
	private float inc          = 0.01f;
	private float incx         = 0.0f;
	private float incy         = 0.0f;
	private float color        = 1.0f;
	boolean vertical           = false;
	private float spinning     = 0.0f;
	private float radius       = 0.0f;
	private float triSize      = 0.5f;
	private float sizeChng     = 0.0f;
	
	Action myCommand;

	//start of Starter()
	public Starter(){	
		setTitle("Zachary Schuett  ||  A1");
		setSize(1920,1080);
		myCanvas = new GLCanvas();
		myCanvas.addGLEventListener(this);
		myCanvas.addMouseWheelListener(this);
		getContentPane().add(myCanvas);
		setVisible(true);
		
//----------Animation Handling Method Calls-----------//
					VerticalCommand();
					    SpinCommand();
					    ChangeColor();
					    //ZoomCommand();
//---------------------------------------------------//
					
		FPSAnimator animator = new FPSAnimator(myCanvas, 30);
		animator.start();	
		
		
//---------------------------------------SETUP OF KEYLISTENER//
		JComponent contentPane = (JComponent) this.getContentPane();
		int mapName = JComponent.WHEN_IN_FOCUSED_WINDOW;
		InputMap imap = contentPane.getInputMap(mapName);
		KeyStroke cKey = KeyStroke.getKeyStroke('c');
		imap.put(cKey, "color");
		ActionMap amap = contentPane.getActionMap();
		amap.put("color", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ChangeColor();
			}	
		});
		this.requestFocus();
	}

	public void display(GLAutoDrawable drawable){
		
		GL4 gl = (GL4) GLContext.getCurrentGL();
		gl.glUseProgram(rendering_program);

		float bkg[] = { 0.0f, 0.0f, 0.0f, 1.0f };
		FloatBuffer bkgBuffer = Buffers.newDirectFloatBuffer(bkg);
		gl.glClearBufferfv(GL_COLOR, 0, bkgBuffer);
		
		int offset_loc   = gl.glGetUniformLocation(rendering_program,        "inc");
		int offset_loc_x = gl.glGetUniformLocation(rendering_program,       "incx");
		int offset_loc_y = gl.glGetUniformLocation(rendering_program,       "incy");
		int offset_color = gl.glGetUniformLocation(rendering_program,      "color");
		int offset_spin  = gl.glGetUniformLocation(rendering_program,   "spinning");
		int offset_size_change  = gl.glGetUniformLocation(rendering_program,   "sizeChng");

		gl.glProgramUniform1f(rendering_program, offset_loc,                     x);
		gl.glProgramUniform1f(rendering_program, offset_loc_x,                incx);
		gl.glProgramUniform1f(rendering_program, offset_loc_y,                incy);
		gl.glProgramUniform1f(rendering_program, offset_color,               color);
		gl.glProgramUniform1f(rendering_program, offset_spin,             spinning);
		gl.glProgramUniform1f(rendering_program, offset_size_change,             sizeChng);


		
		gl.glDrawArrays(GL_TRIANGLES,0,3);
		
		
		if(vertical == true && spinning != 1) {
			x += inc;
			if (x > 1.0f) inc  = -0.01f;
			if (x < -1.0f) inc =  0.01f;
		}
		
		if(spinning == 1 && vertical != true) {

			 incx = (float)(Math.cos(radius)*0.5);
			 incy = (float)(Math.sin(radius)*0.5);
			radius += 0.08f;
		}
		if(sizeChng == 1.0) {
			int offset_size  = gl.glGetUniformLocation(rendering_program,   "triSize");
			gl.glProgramUniform1f(rendering_program, offset_size,             triSize);
			gl.glDrawArrays(GL_TRIANGLES, 0, 3);
			sizeChng = 0.0f;
		}
		
		
}
	public void init(GLAutoDrawable drawable){
		GL4 gl            = (GL4) GLContext.getCurrentGL();
		rendering_program = initialShaderProgram();
		gl.glGenVertexArrays(vao.length, vao, 0);
		gl.glBindVertexArray(vao[0]);
	}
	private int initialShaderProgram(){
		
		GL4 gl = (GL4) GLContext.getCurrentGL();
		String vshaderSource[] = util.readShaderSource("vert.shader");
		String fshaderSource[] = util.readShaderSource("frag.shader");
		int lengths[];
			
		int vShader = gl.glCreateShader(GL_VERTEX_SHADER);
		int fShader = gl.glCreateShader(GL_FRAGMENT_SHADER);

		gl.glShaderSource(vShader, vshaderSource.length, vshaderSource, null, 0);
		gl.glShaderSource(fShader, fshaderSource.length, fshaderSource, null, 0);

		gl.glCompileShader(vShader);
		gl.glCompileShader(fShader);

		int vfprogram = gl.glCreateProgram();
		gl.glAttachShader(vfprogram, vShader);
		gl.glAttachShader(vfprogram, fShader);
		gl.glLinkProgram(vfprogram);
		return vfprogram;
	}
	
	
	public static void main(String[] args) { new Starter(); }
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {}
	public void dispose(GLAutoDrawable drawable) {}
	
//---------------------------------------------------------------Additional Methods for Handling Events//
	
	//VERTICAL COMMAND CLASS//[will be transferred out at a later date]
	public void VerticalCommand(){
		JButton vertBut = new JButton("Vertical Movement");
		vertBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent v) {
				System.out.println("VERTICAL BRO");
				//Check the boolean value
				if(vertical == true && spinning == 0.0f) {
					vertical = false;
					spinning = 1.0f;
				}else if(vertical == false && spinning == 1.0f ){
					vertical = true;
					spinning = 0.0f;
				}
				System.out.println(vertical);
			}
		});
		JPanel topPanelA = new JPanel();
		this.add(topPanelA, BorderLayout.SOUTH);
		topPanelA.add(vertBut);
	}

	
	//COLOR COMMAND CLASS//[will be transferred out at a later date]
	public void ChangeColor() {
		if(color == 0) {
			color = 1;
		
		}else if(color == 1) {
			color = 0;
		}
	}
	
	//SPIN COMMAND CLASS//[will be transferred out at a later date]
	public void SpinCommand(){
		JButton spinBut = new JButton("Spin     Movement");
		spinBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent s) {
				System.out.println("IM SPINNING");
				//resetTriLoc();
				if(spinning == 0) {
					spinning = 1;
				}else if(spinning == 1) {
					spinning = 0;	
				}
				System.out.println(spinning);
			}
		});
		JPanel topPanelB = new JPanel();
		this.add(topPanelB, BorderLayout.NORTH);
		topPanelB.add(spinBut);
	}
	
	
	/*public void resetTriLoc() {
		incx = 0.0f;
		incy = 0.0f;
	}*/

	@Override
	public void mouseWheelMoved(MouseWheelEvent w) {
		int notches = w.getWheelRotation();

		if(notches < 0) {
			System.out.println("ZOOMING IN");
			triSize -= 0.00001f;
		
		}else if(notches > 0) {
			System.out.println("ZOOMING OUT");
			triSize += 0.00001f;

		}else if(triSize < .00001f) {
			triSize = .00001f;
		}else if(triSize < 0.9f) {
			triSize = 0.9f;
		}
		sizeChng = 1.0f;

	}
	
	
	
	
//----------------------------------------------------------------------------------------End of Additional Methods//
}//end of Starter.java