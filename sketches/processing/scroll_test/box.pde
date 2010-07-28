
class Box {
  float x_;
  float y_;
  color c_;
  
  Box(float x_, float y_) {
    this.x_ = x_;
    this.y_ = y_;
    c_ = color(int(random(100, 255)), int(random(100, 255)),
      int(random(100, 255)));
  }
  
  void draw() {
    fill(c_);
    
    pushMatrix();
      translate(x_, y_);
      rect(0, 0, side_dim, side_dim);
    popMatrix();
  }
  
  void move() {
    y_ -= move_rate;
  }
  
  float x() {
    return x_;
  }
  
  float y() {
    return y_;
  }
}
