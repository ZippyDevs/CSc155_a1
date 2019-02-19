#version 430
in vec4 initialColor;
out vec4 color;
void main(void)
{
	//color = vec4(1.0,1.0,1.0,1.0);
 	color = initialColor;
}