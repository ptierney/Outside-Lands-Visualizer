


ArrayList boxes;

int min_bright = 35;

int food_h = 89; int food_s = 50; int food_b = min_bright;
int wine_h = 287; int wine_s = 50; int wine_b = min_bright;
int music_h = 29; int music_s = 50; int music_b = min_bright;

int num_boxes_x;
int side_dim = 45; // in px
int box_spacing = 7;

void setup() {
  size(640, 480);
  colorMode(HSB, 360, 100, 100);
  background(0);
  rectMode(CENTER);
  
  boxes = new ArrayList();
  
  num_boxes_x = width / (side_dim + box_spacing);
}

void draw() {
  for (int i = 0; i < boxes.size(); ++i) { 
    Box b = (Box) boxes.get(i);
    b.draw();
  }
}

void keyPressed() {
  if (key == ' ')
    add_box(); 
}

void add_box() {
  int num_for_pos = boxes.size();
  int height_add = height;
  
  while (num_for_pos >= num_boxes_x) {
    height_add -= box_spacing;
    height_add -= side_dim;
    num_for_pos -= num_boxes_x;
  }
  
  int box_y = height_add - box_spacing - side_dim / 2;
  int box_x = num_for_pos * (side_dim + box_spacing) + box_spacing + side_dim / 2;
  int box_type = int(random(3));
  int box_index = int(random(10));

  for (int i = 0; i < boxes.size(); ++i) { 
    Box b = (Box) boxes.get(i);
    b.register_new_box(box_type, box_index);
  }
  
  boxes.add(new Box(box_type, box_index, box_x, box_y));
}
