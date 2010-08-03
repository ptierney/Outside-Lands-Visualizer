import processing.opengl.*;


PImage state_1;
PImage state_2;
PGraphics state_3;

float rotate_amount;

float seam;

void setup() {
  size(1024, 768, OPENGL);
  noStroke();
  noFill();
  smooth();
  
  state_1 = loadImage("profile_1-110.png");
  state_2 = loadImage("profile_2-110.png");
  
  state_3 = createGraphics(110, 110, JAVA2D);
  state_3.beginDraw();
  state_3.background(237, 30, 121);
  state_3.endDraw();
  
  rotate_amount = 0;
  seam = 0.01;
}

void draw() {
  background(255);
  pushMatrix();
    translate(width/2, height/2);
    rotateY(rotate_amount);
    beginShape();
      texture(state_1);
      vertex(-100, -100, seam, 0, 0);
      vertex(100, -100, seam, 100, 0);
      vertex(100, 100, seam, 100, 100);
      vertex(-100, 100, seam, 0, 100);
    endShape();
    
    beginShape();
      texture(state_3);
      vertex(-100, -100, -seam, 0, 0);
      vertex(-100, 100, -seam, 0, 100);
      vertex(100, 100, -seam, 100, 100);
      vertex(100, -100, -seam, 100, 0);
    endShape();
    
  popMatrix();
  
  rotate_amount += 0.05;
}
