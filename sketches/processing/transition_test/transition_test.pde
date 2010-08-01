

int unit_dim = 110;
int h_unit_dim = unit_dim / 2;
int unit_pixels = unit_dim * unit_dim;
PFont text_font;
int text_size;

PImage photo_1;
PImage photo_2;

VoteBox[] vote_box;
ArrayList vote_boxes;

PApplet main_sketch;

void setup() {
  size(1024, 768);
  
  main_sketch = this;
  
  text_font = loadFont("GillSansMT-18.vlw");
  text_size = 18;
  
  photo_1 = loadImage("photo_1.png");
  photo_2 = loadImage("photo_5.png");
  
  vote_boxes = new ArrayList();
  
  int num_boxes = 200;
  for (int i = 0; i <= num_boxes; ++i) {
    vote_boxes.add(new VoteBox(map(i, 0, num_boxes, 0, width), height / 2));
  }
  
}

void draw() {
  background(0);
  for (int i = 0; i < vote_boxes.size(); ++i) {
    VoteBox b = (VoteBox) vote_boxes.get(i);
    b.draw();
  }
}

void keyPressed() {
  for (int i = 0; i < vote_boxes.size(); ++i) {
    VoteBox b = (VoteBox) vote_boxes.get(i);
    b.update();
  }
}
