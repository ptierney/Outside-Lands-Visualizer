

int unit_dim = 110;
int h_unit_dim = unit_dim / 2;
int unit_pixels = unit_dim * unit_dim;
int box_gap = 15;

int num_cols;

int fall_speed = 10;
int scroll_speed = unit_dim + box_gap;
int scroll_thresh;
boolean scrolling;

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
  scroll_thresh = height / 3;
  scrolling = false;
  
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
  
  if (scrolling) {
    delete_bottom_row();
    fall_all();
    scrolling = false;
    // N.B. this should probably still have a callback timer, which checks to make
    // sure that all that boxes are below the thresh after the boxes have stopped falling
    // TODO: implement this
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

void move_boxes() {
  for (int i = 0; i < vote_boxes.size(); ++i) {
    VoteBox b = (VoteBox) vote_boxes.get(i);
    b.set_y(b.y() + scroll_speed);
  }
}

void check_all_scroll() {
  for (int i = 0; i < vote_boxes.size(); ++i) {
    Box b = (Box) vote_boxes.get(i);
    if (check_scroll(b)) {
      scrolling = true;
      return;
    }
  }
  scrolling = false;
}

void delete_bottom_row() {
  for (int i = 0; i < vote_boxes.size(); ) {
    Box b = (Box) vote_boxes.get(i);
    if (b.is_inside(b.x(), height - box_gap - b.side_dim() / 2)) {
      vote_boxes.remove(i);
    } else {
      ++i;
    }
  }
}

void fall_all() {
  for (int i = 0; i < vote_boxes.size(); ++i) {
    Box b = (Box) vote_boxes.get(i);
    b.set_falling(true);
  }
}

boolean check_scroll(Box b) {
  if (b.y() - b.side_dim() / 2 < scroll_thresh)
    return true;
  else
    return false;
}
