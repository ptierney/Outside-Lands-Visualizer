
// side length of square in px
int side_dim = 45;
// spacing between squares in px
int spacing = 7;

void setup() {
  size(640, 480);
  background(0);
  fill(200);
  
  int counter = 0;
  
  int curr_x = spacing;
  while (curr_x + side_dim < width) {
    int curr_y = spacing;
    while (curr_y + side_dim < height) {
      rect(curr_x, curr_y, side_dim, side_dim);
      ++counter;
      curr_y += side_dim + spacing;
    }
    curr_x += side_dim + spacing;
  }
  
  println("Squares on grid = " + counter);
}

void draw() {
 
}
