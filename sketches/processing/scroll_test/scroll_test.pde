
ArrayList boxes;

int side_dim = 45;
int box_spacing = 7;
float move_rate = 0.2;

void setup() {
  size(640, 480);
  rectMode(CENTER);
  
  boxes = new ArrayList();
}

void draw() {
  background(0);
  
  for (int i = 0; i < boxes.size(); ++i) {
    Box b = (Box) boxes.get(i);
    b.draw();
  }
  
  scroll_boxes();
  remove_off_screen();
}

void add_box() {
  if (boxes.size() == 0) {
    boxes.add(new Box(box_spacing + side_dim / 2, 
      box_spacing + side_dim / 2));
      return;
  }
  
  Box last_box = (Box) boxes.get(boxes.size() - 1);
  
  float new_x;
  float new_y;
  
  if (last_box.x() > width - side_dim) {
    new_x = box_spacing + side_dim / 2;
    new_y = last_box.y() + box_spacing + side_dim;
  }
  else {
    new_x = last_box.x() + box_spacing + side_dim;
    new_y = last_box.y();
  }
    
  boxes.add(new Box(new_x, new_y));
}

void scroll_boxes() {
  if (boxes.size() == 0)
    return;
  
  Box last_box = (Box) boxes.get(boxes.size() - 1);
  
  if (last_box.y() < 3 * height / 4)
    return;
  
  for (int i = 0; i < boxes.size(); ++i) {
    Box b = (Box) boxes.get(i);
    b.move();
  }
}

void remove_off_screen() {
  for (int i = 0; i < boxes.size(); ++i) {
    Box b = (Box) boxes.get(i);
    if (b.y() > height + side_dim / 2)
      boxes.remove(i);
  }
}

void keyPressed() {
  if (key == ' ') {
    add_box();
  } 
}
