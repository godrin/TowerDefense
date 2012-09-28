attribute float a_distance;
attribute float a_angle;
attribute vec4 a_mycolor;
varying vec4 v_Color;
uniform mat4 world; 
uniform vec4 u_position;
uniform float u_time;
uniform float u_size;
uniform float u_opacity;

void main()
{                            
   //v_Color = vec4(1,0,0,0.5);
   v_Color=a_mycolor;
   v_Color.a*=u_opacity;
   vec4 a_position=u_position;
   float d=(1+sin(u_time+a_distance+a_angle)*0.1)*a_distance;
   vec4 delta=vec4(sin(a_angle),cos(a_angle),0,0)*d*u_size;
   a_position+=delta;
   gl_Position=world*a_position;
}           