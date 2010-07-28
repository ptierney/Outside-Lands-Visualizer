
class Box {
  int side_dim_;
  int text_size_;
  String text_;
  int x_;
  int y_;
  
  Box(int side_dim_, int text_size_, String text_, int x_, int y_) {
    this.side_dim_ = side_dim_;
    this.text_size_ = text_size_;
    this.text_ = text_;
    this.x_ = x_;
    this.y_ = y_;
  }
  
  void draw() {
    pushMatrix();
      translate(x_, y_);
      fill(0);
      rect(0, 0, side_dim_, side_dim_);
      textFont(font, text_size_);
      fill(255);
      text(text_, -side_dim_ / 2, text_size_ / 2);
      fill(0);
      textFont(label_font, 12);
      text(side_dim_, 0, side_dim_ / 2 + 12 + box_spacing);
    popMatrix();
  }
  
  
  
}
