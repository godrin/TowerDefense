attribute vec4 a_Position;
attribute vec4 a_Color;
varying vec4 v_Color;
uniform mat4 world; 


void main()
{                            
   v_Color = a_Color;
   
   gl_Position=world*a_Position;
}           