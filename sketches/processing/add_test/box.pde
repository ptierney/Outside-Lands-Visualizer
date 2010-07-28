
enum Type {
  FOOD,
  WINE,
  MUSIC
}

class Box {
  int side_dim_ = 45; // in px
  Type type_; // food, wine, music TODO: make enum
  int index_; // specific musician, restaurant, vinyard
  float glow_counter_;
  float max_glow_ = 100.0;
  float glow_decay_ = 0.95;
  int x_;
  int y_;
  
  Box(Type type_, int index_) {
    this.type_ = type_;
    this.index_ = index_;
    glow_counter_ = 0;
  }
  
  void draw() {
    int fill_h;
    int fill_b;
    
    switch (type) {
      case FOOD:
        fill_h = food_h;
        fill_b = food_b;
      break;
      case WINE:
        fill_h = wine_h;
        fill_b = wine_b;
      break;
      case MUSIC:
        fill_h = music_h;
        fill_b = music_b;
      break;
      default:
        fill_h = 180;
        fill_b = 50;
        println("oh no!");
      break;      
    }
    
    int fill_s = map(glow_counter_, 0, max_glow_, 50, 100);
    
    fill(fill_h, fill_s, fill_b);
    
    pushMatrix();
      translate(x_, y_);
      rect(0, 0, side_dim, side_dim);
    popMatrix();
    
    glow_counter_ *= glow_decay_;
  }
  
  void register_new_box(Types new_type, int new_index) {
    // Check if the new box is the same as this one
    if (new_type == type_) {// && new_index == index_) {
      glow_counter_ = max_glow_;
    }    
  }
  
  int x() {
    return x_;
  }
  
  void set_x(int new_x) {
    x_ = new_x;
  }
  
  void y() {
    return y_;
  }
  
  void set_y(int new_y) {
    y_ = new_y;
  }
  
  Type type() {
    return type_;
  }
  
  int index() {
    return index_;
  }
}
