
int size_min = 8;
int size_max = 64;

int size_step = 2;

int box_spacing = 7;

PFont font;
PFont label_font;

void setup() {
  size(640, 480);
  background(255);
  noStroke();
  rectMode(CENTER);
  smooth();
  //font = loadFont("Shruti-Bold-48.vlw");
  //font = loadFont("Consolas-48.vlw");
  font = loadFont("ArialMT-64.vlw");
  label_font = loadFont("ArialMT-12.vlw");
  
  create_boxes();
}

void draw() {
  saveFrame("640-480.png");
  exit();
}

void create_boxes() {
  int curr_x = box_spacing + size_min * 2;
  int curr_y = box_spacing + size_min * 2;
  
  for (int curr_size = size_min; curr_size <= size_max; ) {
    Box b = new Box(curr_size, get_text_size(curr_size), "Kings of Leon", 
      curr_x, curr_y);
    b.draw();
    
    curr_x += curr_size / 2 + box_spacing;
    curr_size += size_step;
    curr_x += curr_size / 2;
    
    if (curr_x > width - curr_size) {
       curr_x = box_spacing + curr_size / 2;
       curr_y += curr_size * 2 + box_spacing;
    }
  }
}

int get_text_size(int curr_size) {
  return int(map(curr_size, size_min, size_max, size_min / 5.75, size_max / 5.75)); 
}

