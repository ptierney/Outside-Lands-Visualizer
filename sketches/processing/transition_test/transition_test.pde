

int unit_dim = 110;
int h_unit_dim = unit_dim / 2;
int unit_pixels = unit_dim * unit_dim;
int box_gap = 15;

int num_cols;

int fall_speed = 1;

PFont text_font;
int text_size;

PImage photo_1;
PImage photo_2;

VoteBox[] vote_box;
ArrayList vote_boxes;
BoxFactory box_factory;

void setup() {
  size(1024, 768);
  
  num_cols = width / (unit_dim + box_gap);
  
  text_font = loadFont("GillSansMT-18.vlw");
  text_size = 18;
  
  photo_1 = loadImage("photo_1.png");
  photo_2 = loadImage("photo_5.png");
  
  box_factory = new BoxFactory();
  vote_boxes = new ArrayList();
  
}

void draw() {
  background(0);
  for (int i = 0; i < vote_boxes.size(); ++i) {
    VoteBox b = (VoteBox) vote_boxes.get(i);
    b.update();
    b.draw();
  }
}

void keyPressed() {
  if (key == ' ') {
    for (int i = 0; i < vote_boxes.size(); ++i) {
      VoteBox b = (VoteBox) vote_boxes.get(i);
      b.update();
    }
  } else if (key == 'a') {
    vote_boxes.add(box_factory.create_box());
  }
}
