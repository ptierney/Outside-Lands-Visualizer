
int box_spacing = 7;

ArrayList boxes;

int food_h = 89; int food_s = 50; int food_b = 86;
int wine_h = 287; int wine_s = 50; int wine_b = 86;
int music_h = 29, int music_s = 50; int music_b = 86;

void setup() {
  size(640, 480);
  colorMode(HSB, 360, 100, 100);
  background(0);
  rectMode(CENTER);
  
  boxes = new ArrayList(); 
}

void draw() {
  
}

void keyPressed() {
  if (key == " ")
    add_box(); 
}

void add_box() {
  
}
