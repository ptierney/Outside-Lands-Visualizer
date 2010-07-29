
float fall_rate = 2.5;

PFont text_font;
ArrayList boxes;

int box_size = 68;
int box_spacing = 7;
int num_cols; // number of columns
int trend_life = 200;
float scale_rate = 0.92; // for trend boxes disappearing
boolean check_for_deletes = false;

color color_mute = color(204, 204, 204);
color color_1 = color(216, 131, 48);
color color_2 = color(140, 78, 17);
color color_3 = color(61, 140, 17);
color color_4 = color(94, 156, 223);
color color_5 = color(127, 59, 140);

void setup() {
  size(1024, 768);
  rectMode(CENTER);
  noFill();
  
  text_font = loadFont("HelveticaNeueLT-Roman-12.vlw");
  textFont(text_font, 12);
  
  boxes = new ArrayList();
  num_cols = width / (box_size + box_spacing);
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
    boolean is_trend = int(random(10)) == 0;
    int box_index = is_trend ? int(random(num_cols - 1)) : int(random(num_cols)); 
    int new_box_size = is_trend ? box_size * 2 + box_spacing : box_size;
    float new_y = box_spacing + new_box_size / 2;
    float new_x;
    if (is_trend)
      new_x = box_spacing + new_box_size / 2 + box_index * (box_size + box_spacing);
    else
      new_x = box_spacing + box_size / 2 + box_index * (box_size + box_spacing);  

    boxes.add(new Box(new_x, new_y, int(random(5)), is_trend));
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

void set_fill_from_type(int type) {
  switch (type) {
    case 0:
      fill(color_1);
    break;
    case 1:
      fill(color_2);
    break;
    case 2:
      fill(color_3);
    break;
    case 3:
      fill(color_4);
    break;
    case 4:
      fill(color_5);
    break;
  }
}
