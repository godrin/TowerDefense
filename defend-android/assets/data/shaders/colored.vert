attribute vec4 a_position;
attribute vec4 a_color;
varying vec4 v_Color;
uniform mat4 world; 


void main()
{                            
   v_Color = a_color;
   
   gl_Position=world*a_position;
}           