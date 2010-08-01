
float fall_rate = 7.5;

PFont box_font;
int box_text_size = 12;
PFont trend_font;
int trend_text_size = 24;
ArrayList boxes;

int box_size = 68;
int box_spacing = 7;
int num_cols; // number of columns
int trend_life = 200;
float scale_rate = 0.92; // for trend boxes disappearing
boolean check_for_deletes = false;

color[][] colors;
String[][] labels;

void setup() {
  size(1024, 768);
  rectMode(CENTER);
  textAlign(CENTER);
  noStroke();
  
  box_font = loadFont("HelveticaNeueLT-Roman-12.vlw");
  trend_font = loadFont("HelveticaNeueLT-Roman-24.vlw");
  
  boxes = new ArrayList();
  num_cols = width / (box_size + box_spacing);
  
  colors = new color[3][4];
  
  colors[0][0] = color(101, 169, 224);
  colors[0][1] = color(157, 194, 224);
  colors[0][2] = color(11, 129, 224);
  colors[0][3] = color(67, 154, 224);
  colors[1][0] = color(216, 131, 48);
  colors[1][1] = color(216, 158, 102);
  colors[1][2] = color(216, 186, 156);
  colors[1][3] = color(216, 115, 16);
  colors[2][0] = color(204, 64, 121);
  colors[2][1] = color(204, 115, 151);
  colors[2][2] = color(204, 166, 181);
  colors[2][3] = color(204, 33, 103);
  
  labels = new String[3][4];
  labels[0][0] = "Ridge";
  labels[0][1] = "Bonny Doon";
  labels[0][2] = "Robert Sinskey";
  labels[0][3] = "Peay";
  labels[1][0] = "The Strokes";
  labels[1][1] = "Kings of Leon";
  labels[1][2] = "Pheonix";
  labels[1][3] = "Cat Power";
  labels[2][0] = "Ti Couz";
  labels[2][1] = "Global Gourmet";
  labels[2][2] = "Spicy Pie";
  labels[2][3] = "Mission Minis";
}

void draw() {
  background(0);
  for (int i = 0; i < boxes.size(); ++i) {
    Box b = (Box) boxes.get(i);
    b.draw(); 
  }
  
  if (check_for_deletes) {
    check_deletes();
  }
}

void keyPressed() {
  if (key == ' ') {
    boolean is_trend = int(random(8)) == 0;
    int box_index = is_trend ? int(random(num_cols - 1)) : int(random(num_cols)); 
    int new_box_size = is_trend ? box_size * 2 + box_spacing : box_size;
    float new_y = box_spacing + new_box_size / 2;
    float new_x;
    if (is_trend)
      new_x = box_spacing + new_box_size / 2 + box_index * (box_size + box_spacing);
    else
      new_x = box_spacing + box_size / 2 + box_index * (box_size + box_spacing);  

    boxes.add(new Box(new_x, new_y, int(random(3)), int(random(4)), is_trend));
  }
}

void check_deletes() {
  boolean restart_falling = false;
  
  for (int i = 0; i < boxes.size(); ++i) {
    Box b = (Box) boxes.get(i);
    
    if (b.delete_me()) {
      restart_falling = true;
      boxes.remove(i);
    }
  }
  
  if (restart_falling) {
    for (int i = 0; i < boxes.size(); ++i) {
      Box b = (Box) boxes.get(i);
      b.set_falling(true);
    }
  }
}

void set_fill_from_type(int type, int index) {
  fill(colors[type][index]);
}

String get_box_text(int type, int index) {
  return labels[type][index];
}
 
