#version 430

uniform float inc;-
uniform float incx;
uniform float incy;
uniform float color;
uniform float spinning;
uniform float triSize;
uniform float sizeChng;
out vec4 initialColor;
//-----------------------------------------------------------make a switch.  UGLYYYY---//
void main(void){ 
//REGULAR TRIANGLE
	if (gl_VertexID == 0 && color == 0) {
		initialColor = vec4(0.0, 0.0, 1.0, 1.0);
		gl_Position = vec4( 0.25,-0.25+inc, 0.0, 1.0);
	
	}else if (gl_VertexID == 1 && color == 0){
     	initialColor = vec4(0.0 , 0.0, 1.0, 1.0);	
  		 gl_Position = vec4(-0.25,-0.25+inc, 0.0, 1.0);
  	 
  	}else if(gl_VertexID == 2 && color == 0) {
   		initialColor = vec4(0.0 , 0.0, 1.0, 1.0);	
  		gl_Position = vec4( 0.25, 0.25+inc, 0.0, 1.0);
  	}

// GRADIENT TRIANGLE
	if (gl_VertexID == 0 && color == 1) {
		initialColor = vec4(color, 0.0, 0.0, 1.0);
		gl_Position = vec4( 0.25,-0.25+inc, 0.0, 1.0);
	
	}else if (gl_VertexID == 1 && color == 1){
     	initialColor = vec4(0.0 , color, 0.0, 1.0);	
  	 	gl_Position = vec4(-0.25,-0.25+inc, 0.0, 1.0);
  	 
  	}else if(gl_VertexID == 2 && color == 1) {
   		initialColor = vec4(0.0 , 0.0, 1.0, 1.0);	
  		gl_Position = vec4( 0.25, 0.25+inc, 0.0, 1.0);
  	}
  	
	//SPINNING TRIANGLE
	if (gl_VertexID == 0 && color == 0 && spinning == 1) {
		initialColor = vec4(0.0, 0.0, 1.0, 1.0);
		gl_Position = vec4( 0.25+incx,-0.25+incy, 0.0, 1.0);
	
	}else if (gl_VertexID == 1 && color == 0 && spinning == 1){
     	initialColor = vec4(0.0 , 0.0, 1.0, 1.0);	
  		 gl_Position = vec4(-0.25+incx,-0.25+incy, 0.0, 1.0);
  	 
  	}else if(gl_VertexID == 2 && color == 0 && spinning == 1) {
   		initialColor = vec4(0.0 , 0.0, 1.0, 1.0);	
  		gl_Position = vec4( 0.25+incx, 0.25+incy, 0.0, 1.0);
  	}
  	
  	//SPINNING TRIANGLE WITH GRADIENT
  	if (gl_VertexID == 0 && color == 1 && spinning == 1) {
		initialColor = vec4(color, 0.0, 1.0, 1.0);
		gl_Position = vec4( 0.25+incx,-0.25+incy, 0.0, 1.0);
	
	}else if (gl_VertexID == 1 && color == 1 && spinning == 1){
     	initialColor = vec4(0.0 , color, 1.0, 1.0);	
  		 gl_Position = vec4(-0.25+incx,-0.25+incy, 0.0, 1.0);
  	 
  	}else if(gl_VertexID == 2 && color == 1 && spinning == 1) {
   		initialColor = vec4(0.0 , 0.0, 1.0, 1.0);	
  		gl_Position = vec4( 0.25+incx, 0.25+incy, 0.0, 1.0);
  	}
  	
  
	//ZOOMING TRIANGLE
  	 if (gl_VertexID == 0 && sizeChng == 1) {
		initialColor = vec4(color, 0.0, 1.0, 1.0);
		gl_Position = vec4( 0.25+incx+triSize,-0.25+incy-triSize, 0.0+triSize, 1.0);
	
	}else if (gl_VertexID == 1 && sizeChng == 1){
     	initialColor = vec4(0.0 , color, 1.0, 1.0);	
  		 gl_Position = vec4(-0.25+incx-triSize,-0.25+incy-triSize, 0.0+triSize, 1.0);
  	 
  	}else if(gl_VertexID == 2 && sizeChng == 1) {
   		initialColor = vec4(0.0 , 0.0, 1.0, 1.0);	
  		gl_Position = vec4( 0.25+incx+triSize, 0.25+incy+triSize, 0.0+triSize, 1.0);
  	}
  	
}