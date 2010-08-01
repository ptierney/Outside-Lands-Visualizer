

int unit_dim = 110;
int h_unit_dim = unit_dim / 2;
int unit_pixels = unit_dim * unit_dim;
PFont text_font;
int text_size;

PImage photo_1;
PImage photo_2;

VoteBox vote_box;

void setup() {
  size(1024, 768);
  rectMode(CENTER);
  
  text_font = loadFont("GillSansMT-14.vlw");
  int text_size = 14;
  
  photo_1 = loadImage("photo_1.png");
  photo_2 = loadImage("photo_3.png");
  
  vote_box = new VoteBox(width / 2, height / 2);
}

void draw() {
  background(0);
  vote_box.draw();
}

void keyPressed() {
  vote_box.update();
}
