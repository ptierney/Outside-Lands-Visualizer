
class BoxFactory {
  
  BoxFactory() {
    // Nothing here
  }
  
  Box create_box() {
    int ran_index = int(random(num_cols));
    
    int new_x = ran_index * (unit_dim + box_gap) + box_gap + unit_dim / 2;
    int new_y = box_gap + unit_dim / 2;
    
    return new VoteBox(new_x, new_y);
  }
  
}
